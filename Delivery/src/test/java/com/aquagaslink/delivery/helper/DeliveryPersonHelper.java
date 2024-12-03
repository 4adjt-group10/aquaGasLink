package com.aquagaslink.delivery.helper;

import com.aquagaslink.delivery.controller.dto.DeliveryPersonForm;
import com.aquagaslink.delivery.controller.dto.DriverLocationForm;
import com.aquagaslink.delivery.model.ClientAddress;
import com.aquagaslink.delivery.model.DeliveryPersonStatus;

public class DeliveryPersonHelper {

    public static DeliveryPersonForm createDeliveryPerson(String name, String email, String phone, String plate){
        return new DeliveryPersonForm(name, email, phone, null, plate);
    }

    public static DeliveryPersonForm createDeliveryPersonStatus(String name, String email, String phone, String plate, DeliveryPersonStatus status){
        return new DeliveryPersonForm(name, email, phone, status, plate);
    }

    public static DriverLocationForm createDriverLocation(){
        return new DriverLocationForm("45'","45'",
                new ClientAddress("123456-12","Logradouro","1192736454","São Paulo", "SP", "12","Brazil" ));
    }


}
