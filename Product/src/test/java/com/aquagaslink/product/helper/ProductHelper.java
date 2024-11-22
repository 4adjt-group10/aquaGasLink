package com.aquagaslink.product.helper;

import com.aquagaslink.product.controller.dto.ProductCadasterDto;
import com.aquagaslink.product.controller.dto.ProductFormDto;
import com.aquagaslink.product.model.ProductModel;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductHelper {

    public static ProductModel createProductModel(Long id){
        return new ProductModel(id, "produto","descrição",new BigDecimal("10"),10,"code");
    }

    public static ProductModel createProductModelWithName(String name){
        return new ProductModel(name,"descrição",new BigDecimal("10"),10,"code");
    }

    public static ProductModel createProductModelWithProductCode(String productCode){
        return new ProductModel("produto","descrição",new BigDecimal("10"),10,productCode);
    }

    public static ProductFormDto createProductDto(Long id){
        return new ProductFormDto(id,"produto","descrição",new BigDecimal("10"),10,"code");
    }

    public static ProductFormDto createProductDtoWith(String name, String code){
        return new ProductFormDto(null,name,"descrição",new BigDecimal("10"),10,code);
    }

    public static ProductCadasterDto createProductCadasterDto(Long id){
        return new ProductCadasterDto(id,"produto","descrição",new BigDecimal("10"),10,"code");
    }

    public static ProductModel createProductModelWithoutId(){
        return new ProductModel("produto","descrição",new BigDecimal("10"),10,"code");
    }

    //Long id, String name, String description, BigDecimal price, int stock, String productCode
}
