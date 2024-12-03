package com.aquagaslink.client.helper;

import com.aquagaslink.client.controller.clientDTO.ClientDTO;
import com.aquagaslink.client.controller.clientDTO.ClientDTOForm;
import com.aquagaslink.client.model.ClientModel;

public class ClientHelper {

    public static ClientDTOForm createClient(String cpf){
        return new ClientDTOForm(cpf,"Joao","Joao@teste.com","1198765433","333145660","Av. test","São Paulo", "SP", "123", "Brazil");
    }

    public static ClientDTOForm createClientData(String cpf, String name){
        return new ClientDTOForm(cpf,name,"Joao@teste.com","1198765433","333145660","Av. test","São Paulo", "SP", "123", "Brazil");
    }

    public static ClientModel createClientModel(String cpf){
        return new ClientModel(cpf,"Joao","Joao@teste.com","1198765433","333145660","Av. test","São Paulo", "SP", "123", "Brazil");
    }

    public static ClientDTO createClientDto(String cpf){
        return new ClientDTO(null,cpf,"Joao","Joao@teste.com","1198765433","Av. test","São Paulo", "SP", "123", "Brazil");
    }
}
