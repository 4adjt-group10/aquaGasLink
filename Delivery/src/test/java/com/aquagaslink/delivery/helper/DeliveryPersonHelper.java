package com.aquagaslink.delivery.helper;

import com.aquagaslink.delivery.controller.dto.DeliveryPersonForm;

public class DeliveryPersonHelper {

    public static DeliveryPersonForm createDeliveryPerson(String name, String email, String phone, String plate){
        return new DeliveryPersonForm(name, email, phone, null, plate);
    }
}
