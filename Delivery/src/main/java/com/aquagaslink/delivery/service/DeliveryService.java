package com.aquagaslink.delivery.service;

import com.aquagaslink.delivery.controller.dto.DriverLocationForm;
import com.aquagaslink.delivery.controller.dto.RoutOutput;
import com.aquagaslink.delivery.infrastructure.DeliveryRepository;
import com.aquagaslink.delivery.model.*;
import com.aquagaslink.delivery.queue.DeliveryEventGateway;
import com.aquagaslink.delivery.queue.dto.DeliveryToOrderOut;
import com.aquagaslink.delivery.queue.dto.OrderToDeliveryIn;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.aquagaslink.delivery.model.DeliveryPersonStatus.AVAILABLE;
import static com.aquagaslink.delivery.model.DeliveryPersonStatus.BUSY;
import static com.aquagaslink.delivery.model.DeliveryStatus.IN_PROGRESS;
import static com.aquagaslink.delivery.model.DeliveryStatus.PENDING;

@Service
public class DeliveryService {
    private static final Logger logger = LoggerFactory.getLogger(DeliveryService.class);
    private static final String ORDER_NOT_FOUND = "Order not found";
    private static final String DELIVERY_NOT_FOUND = "delivery not found";

    private static final String DURATION = "duration";
    private static final String DISTANCE = "distance";

    private final DeliveryRepository deliveryRepository;
    private final DeliveryPersonService deliveryPersonService;
    private final DeliveryEventGateway deliveryEventGateway;
    private String apiKey = "AIzaSyAwyKbMBsFNJQFBDFAnhqy1Biu7qrfObP8"; // Substitua pela sua chave de API

    public DeliveryService(DeliveryRepository deliveryRepository, DeliveryPersonService deliveryPersonService, DeliveryEventGateway deliveryEventGateway) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryPersonService = deliveryPersonService;
        this.deliveryEventGateway = deliveryEventGateway;
    }

    public RoutOutput tracking(String orderId, DriverLocationForm driverLocationForm) {
        if (StringUtils.isNotEmpty(driverLocationForm.Latitude())&& StringUtils.isNotEmpty(driverLocationForm.Longitude())) {
            return routeByLatAndLongAndSave(driverLocationForm, orderId);
        } else {
            return routeByAddress(driverLocationForm.address(), orderId);
        }
    }

    private RoutOutput routeByLatAndLongAndSave(DriverLocationForm driverLocationForm, String orderId) {
        var delivery = getDelivery(orderId);
        delivery.setLatitude(driverLocationForm.Latitude());
        delivery.setLongitude(driverLocationForm.Longitude());
        deliveryRepository.save(delivery);
        var address = delivery.getDeliveryClient().getAddress();
        String destination = generateLocationByAddress(address);
        String origin = driverLocationForm.Latitude().concat(",").concat(driverLocationForm.Longitude());
        String url = buildDirectionsUrl(origin, destination);
        return callDirections(url);
    }

    public RoutOutput getTrackingByClient(UUID clientId) {
        Delivery delivery = deliveryRepository.findByClientIdAndStatus(clientId.toString(), IN_PROGRESS)
                .orElseThrow(() -> new EntityNotFoundException(ORDER_NOT_FOUND));
        var clientAddress = delivery.getDeliveryClient().getAddress();
        String destination = generateLocationByAddress(clientAddress);
        if(StringUtils.isBlank(delivery.getLatitude()) || StringUtils.isBlank(delivery.getLongitude())) {
            throw new EntityNotFoundException("Rastreamento ainda não disponível");
        }
        String origin = delivery.getLatitude().concat(",").concat(delivery.getLongitude());
        String url = buildDirectionsUrl(origin, destination);
        return callDirections(url);
    }

    private RoutOutput routeByAddress(Address address, String orderId) {
        var delivery = getDelivery(orderId);
        var clientAddress = delivery.getDeliveryClient().getAddress();
        String destination = generateLocationByAddress(clientAddress);
        String origin = generateLocationByAddress(address);
        String url = buildDirectionsUrl(origin, destination);
        return callDirections(url);
    }

    private Delivery getDelivery(String orderId) {
        return deliveryRepository.findByOrderId(UUID.fromString(orderId))
                .orElseThrow(() -> new EntityNotFoundException(ORDER_NOT_FOUND));
    }

    private String buildDirectionsUrl(String origin, String destination) {
        String encodedOrigin = URLEncoder.encode(origin.trim(), StandardCharsets.UTF_8);
        String encodedDestination = URLEncoder.encode(destination.trim(), StandardCharsets.UTF_8);
        return "https://maps.googleapis.com/maps/api/directions/json?origin=" + encodedOrigin + "&destination=" + encodedDestination + "&key=" + apiKey.trim();
    }

    private RoutOutput callDirections(String url) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                logger.info("Failed to call Google Directions API, response code: " + response.statusCode());
            }
            return parseDirectionsResponse(response.body());
        } catch (IOException | InterruptedException e) {
            logger.error("Error calling Google Directions API", e);
        }
        return null;
    }

    private RoutOutput parseDirectionsResponse(String jsonResponse) {
        JSONObject jsonObj = new JSONObject(jsonResponse);
        JSONArray routesArray = jsonObj.getJSONArray("routes");
        List<RoutOutput.Route> routes = new ArrayList<>();

        for (int i = 0; i < routesArray.length(); i++) {
            JSONObject routeObj = routesArray.getJSONObject(i);
            JSONArray legsArray = routeObj.getJSONArray("legs");
            List<RoutOutput.Leg> legs = new ArrayList<>();

            for (int j = 0; j < legsArray.length(); j++) {
                JSONObject legObj = legsArray.getJSONObject(j);

                RoutOutput.Distance distance = new RoutOutput.Distance(
                        legObj.getJSONObject(DISTANCE).getString("text"),
                        legObj.getJSONObject(DISTANCE).getInt("value")
                );
                RoutOutput.Duration duration = new RoutOutput.Duration(
                        legObj.getJSONObject(DURATION).getString("text"),
                        legObj.getJSONObject(DURATION).getInt("value")
                );

                JSONArray stepsArray = legObj.getJSONArray("steps");
                List<RoutOutput.Step> steps = new ArrayList<>();

                for (int k = 0; k < stepsArray.length(); k++) {
                    JSONObject stepObj = stepsArray.getJSONObject(k);

                    RoutOutput.Distance stepDistance = new RoutOutput.Distance(
                            stepObj.getJSONObject(DISTANCE).getString("text"),
                            stepObj.getJSONObject(DISTANCE).getInt("value")
                    );
                    RoutOutput.Duration stepDuration = new RoutOutput.Duration(
                            stepObj.getJSONObject(DURATION).getString("text"),
                            stepObj.getJSONObject(DURATION).getInt("value")
                    );

                    RoutOutput.Step step = new RoutOutput.Step(
                            stepDistance,
                            stepDuration,
                            stepObj.getString("html_instructions").replaceAll("<[^>]*>", "")
                    );
                    steps.add(step);
                }

                RoutOutput.Leg leg = new RoutOutput.Leg(distance, duration, steps);
                legs.add(leg);
            }

            RoutOutput.Route route = new RoutOutput.Route(legs);
            routes.add(route);
        }

        return new RoutOutput(routes);
    }

    private @NotNull String generateLocationByAddress(Address address) {
        return address.getStreet() + " " +
                address.getNumber() + " " +
                address.getCity() + " " +
                address.getState() + " " +
                address.getCountry();
    }
    private @NotNull String generateLocationByAddress(ClientAddress address) {
        return address.getStreet() + " " +
                address.getClientNumber() + " " +
                address.getClientCity() + " " +
                address.getClientState()+ " " +
                address.getClientCountry();
    }

    public void createDelivery(OrderToDeliveryIn payload) {
        var delivery = new Delivery(payload.orderId(),
                new DeliveryClient(payload.clientId(), payload.clientName(), payload.address()),
                new DeliveryProduct(payload.productName(), payload.productPrice()),
                PENDING);
        deliveryRepository.save(delivery);
    }

    /**
     * This method is scheduled to run every 1 minute, every day.
     * {@code @Scheduled(cron = "0 0/1 * * * *")} configures this scheduling.
     */
     @Scheduled(cron = "0 0/1 * * * *")
     @Async
     public void processDeliveries() {
         deliveryPersonService.findFirstByStatus(AVAILABLE).ifPresent(deliveryPerson -> {
             deliveryRepository.findFirstByStatus(PENDING).ifPresent(delivery -> {
                 delivery.setStatus(IN_PROGRESS);
                 delivery.setDeliveryPerson(deliveryPerson);
                 deliveryPerson.setStatus(BUSY);
                 deliveryRepository.save(delivery);
                 deliveryPersonService.save(deliveryPerson);
             });
         });
     }

     public void finishDeliveryAndSendToOrder(UUID deliveryId, DeliveryFinishStatus status) {
         var delivery = deliveryRepository.findById(deliveryId)
                 .orElseThrow(() -> new EntityNotFoundException(ORDER_NOT_FOUND));
         delivery.setStatus(status.toDeliveryStatus());
         deliveryPersonService.findById(delivery.getDeliveryPerson().getId()).ifPresent(deliveryPerson -> {
             deliveryPerson.setStatus(AVAILABLE);
             deliveryPersonService.save(deliveryPerson);
         });
         deliveryRepository.save(delivery);
         deliveryEventGateway.sendOrderEvent(new DeliveryToOrderOut(delivery.getOrderId(), status));
     }

    public String getDeliveryId(UUID deliveryPersonId) {
        Delivery byDeliveryPersonId = deliveryRepository.findByDeliveryPerson_IdAndStatus(deliveryPersonId, IN_PROGRESS)
                .orElseThrow(() -> new EntityNotFoundException(DELIVERY_NOT_FOUND));
        return byDeliveryPersonId.getId().toString();
    }
}
