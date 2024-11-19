package com.aquagaslink.product.controller;

import com.aquagaslink.product.controller.dto.ProductCadasterDto;
import com.aquagaslink.product.controller.dto.ProductFormDto;
import com.aquagaslink.product.helper.ProductHelper;
import com.aquagaslink.product.queue.producer.MessageProducer;
import com.aquagaslink.product.service.ProductService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductStockControllerTest {

    @Mock
    private ProductService service;

    @InjectMocks
    private ProductStockController controller;

    @Mock
    private MessageProducer messageProducer;

    @Autowired
    private ProductHelper helper;

    @Test
    public void testRegisterProduct() {

        Long id = new Random().nextLong();
        var productFormDto = helper.createProductDto(id);
        var productCadasterDto = helper.createProductCadasterDto(id);


        when(service.registerProduct(productFormDto)).thenReturn(productCadasterDto);
        ResponseEntity<ProductCadasterDto> response = controller.registerProduct(productFormDto);


        verify(service).registerProduct(productFormDto);
        Assert.assertEquals(ResponseEntity.ok(productCadasterDto), response);
    }

    @Test
    public void testRegisterProducts() {

        var productFormDto1 = helper.createProductDto(new Random().nextLong());
        var productFormDto2 = helper.createProductDto(new Random().nextLong());
        var productCadasterDto1 = helper.createProductCadasterDto(new Random().nextLong());
        var productCadasterDto2 = helper.createProductCadasterDto(new Random().nextLong());
        List<ProductFormDto> productFormDtos = new ArrayList<>();
        productFormDtos.add(productFormDto1);
        productFormDtos.add(productFormDto2);

        List<ProductCadasterDto> productCadasterDtos = new ArrayList<>();
        productCadasterDtos.add(productCadasterDto1);
        productCadasterDtos.add(productCadasterDto2);


        when(service.registerProducts(productFormDtos)).thenReturn(productCadasterDtos);
        ResponseEntity<List<ProductCadasterDto>> response = controller.registerProducts(productFormDtos);


        verify(service).registerProducts(productFormDtos);
        Assert.assertEquals(ResponseEntity.ok(productCadasterDtos), response);
    }

    @Test
    public void testFindById() {

        Long id = new Random().nextLong();
        var productCadasterDto = helper.createProductCadasterDto(id);


        when(service.findById(id)).thenReturn(productCadasterDto);
        ResponseEntity<ProductCadasterDto> response = controller.findById(id);


        verify(service).findById(id);
        Assert.assertEquals(ResponseEntity.ok(productCadasterDto), response);
    }

    @Test
    public void testFindByName() {

        String name = "produto";
        var productCadasterDto = helper.createProductCadasterDto(new Random().nextLong());


        when(service.findByName(name)).thenReturn(productCadasterDto);
        ResponseEntity<ProductCadasterDto> response = controller.findByName(name);


        verify(service).findByName(name);
        Assert.assertEquals(ResponseEntity.ok(productCadasterDto), response);
    }

    @Test
    public void testFindByProductCode() {

        String code = "code";
        var productCadasterDto = helper.createProductCadasterDto(new Random().nextLong());;


        when(service.findByProductCode(code)).thenReturn(productCadasterDto);
        ResponseEntity<ProductCadasterDto> response = controller.findByProductCode(code);


        verify(service).findByProductCode(code);
        Assert.assertEquals(ResponseEntity.ok(productCadasterDto), response);
    }

    @Test
    public void testFindAll() {

        List<ProductCadasterDto> products = new ArrayList<>();
        products.add(helper.createProductCadasterDto(new Random().nextLong()));
        products.add(helper.createProductCadasterDto(new Random().nextLong()));


        when(service.findAll()).thenReturn(products);
        ResponseEntity<List<ProductCadasterDto>> response = controller.findAll();


        verify(service).findAll();
        Assert.assertEquals(ResponseEntity.ok(products), response);
    }

    @Test
    public void testDeleteProduct() {

        Long id = new Random().nextLong();

        controller.deleteProduct(id);

        verify(service).deleteById(id);
    }

    @Test
    public void testConstructorInjection() {
        Assert.assertNotNull(controller.productService);
       Assert. assertNotNull(controller.messageProducer);
    }

    @Test
    public void testTesteMethod() {
        controller.teste();

        verify(messageProducer, times(10)).send(anyString());
    }
}
