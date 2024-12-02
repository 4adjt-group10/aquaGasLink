package com.aquagaslink.delivery.controller;

import com.aquagaslink.delivery.controller.dto.DeliveryPersonDto;
import com.aquagaslink.delivery.controller.dto.DeliveryPersonForm;
import com.aquagaslink.delivery.model.DeliveryPersonStatus;
import com.aquagaslink.delivery.service.DeliveryPersonService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.ASC;

@RestController
@RequestMapping("/delivery-person")
public class DeliveryPersonController {

    private static final String PAGEABLE_DESCRIPTION = """
        Accepts sorting parameters passing them in the URL, such as '?page=2&size=5&sort=createdAt,desc'
         to fetch the third page (page count starts at 0), with 5 records per page, sorted by name in ascending order.
    """;

    private final DeliveryPersonService deliveryPersonService;

    public DeliveryPersonController(DeliveryPersonService deliveryPersonService) {
        this.deliveryPersonService = deliveryPersonService;
    }

    @PostMapping("/create")
    @Operation(summary = "Create new delivery person")
    public ResponseEntity<DeliveryPersonDto> create(@Valid @RequestBody DeliveryPersonForm deliveryPerson) {
        return ResponseEntity.ok(deliveryPersonService.create(deliveryPerson));
    }

    @PostMapping("/update")
    @Operation(summary = "Update delivery person")
    public ResponseEntity<DeliveryPersonDto> update(@Valid @RequestBody DeliveryPersonForm deliveryPerson) {
        return ResponseEntity.ok(deliveryPersonService.update(deliveryPerson));
    }

    @PostMapping("/delete/{id}")
    @Operation(summary = "Delete delivery person by id")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        deliveryPersonService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-all")
    @Operation(summary = "List all delivery person", description = PAGEABLE_DESCRIPTION)
    public ResponseEntity<Iterable<DeliveryPersonDto>> getAll(@Nullable @PageableDefault(size = 5, sort = "name", direction = ASC) Pageable pageable) {
        return ResponseEntity.ok(deliveryPersonService.getAll(pageable));
    }

    @GetMapping("/get-all-by-status")
    @Operation(summary = "List all delivery person by status", description = PAGEABLE_DESCRIPTION)
    public ResponseEntity<Page<DeliveryPersonDto>> getAllByStatus(@RequestParam("status") DeliveryPersonStatus status,
                                                                  @Nullable @PageableDefault(size = 5, sort = "name", direction = ASC) Pageable pageable) {
        return ResponseEntity.ok(deliveryPersonService.getAllByStatus(status, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get delivery person by id")
    public ResponseEntity<DeliveryPersonDto> getDeliveryPersonById(@PathVariable UUID id) {
        return ResponseEntity.ok(deliveryPersonService.getDeliveryPersonById(id));
    }
}
