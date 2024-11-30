package com.aquagaslink.client.helper;

import com.aquagaslink.client.controller.clientDTO.ClientDTOForm;

public class ClientHelper {

    public static ClientDTOForm createClient(String cpf){
        return new ClientDTOForm(cpf,"Joao","Joao@teste.com","1198765433","333145660","Av. test","São Paulo", "SP", "123", "Brazil");
    }

    public static ClientDTOForm createClientData(String cpf, String name){
        return new ClientDTOForm(cpf,name,"Joao@teste.com","1198765433","333145660","Av. test","São Paulo", "SP", "123", "Brazil");
    }
}
