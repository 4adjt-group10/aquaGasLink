package com.aquagaslink.product.controller;

import com.aquagaslink.product.controller.dto.ProductCadasterDto;
import com.aquagaslink.product.controller.dto.ProductFormDto;
import com.aquagaslink.product.queue.ProductEventGateway;
import com.aquagaslink.product.queue.producer.ProductEventgatewayWithStreamBridge;
import com.aquagaslink.product.service.ProductService;
import io.netty.incubator.codec.quic.QuicPathEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductStockControllerTest {

    @Mock
    private ProductService productService;


    @InjectMocks
    private ProductStockController productStockController;

    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
        productStockController = new ProductStockController(productService);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }


    @Test
    void testRegisterProduct() {
        ProductFormDto productFormDto = new ProductFormDto(UUID.randomUUID(), "ProductName", "Description", new BigDecimal("10.0"), 100, "P123");
        ProductCadasterDto productCadasterDto = new ProductCadasterDto(UUID.randomUUID(), "ProductName", "Description", new BigDecimal("10.0"), 100, "P123");

        when(productService.registerProduct(any(ProductFormDto.class))).thenReturn(productCadasterDto);

        ResponseEntity<ProductCadasterDto> response = productStockController.registerProduct(productFormDto);

        assertEquals(ResponseEntity.ok(productCadasterDto), response);
        verify(productService, times(1)).registerProduct(any(ProductFormDto.class));
    }

    @Test
    void testRegisterProducts() {
        ProductFormDto productFormDto1 = new ProductFormDto(UUID.randomUUID(), "ProductName1", "Description1", new BigDecimal("10.0"), 100, "P123");
        ProductFormDto productFormDto2 = new ProductFormDto(UUID.randomUUID(), "ProductName2", "Description2", new BigDecimal("20.0"), 200, "P456");
        List<ProductFormDto> productFormDtos = Arrays.asList(productFormDto1, productFormDto2);

        ProductCadasterDto productCadasterDto1 = new ProductCadasterDto(UUID.randomUUID(), "ProductName1", "Description1", new BigDecimal("10.0"), 100, "P123");
        ProductCadasterDto productCadasterDto2 = new ProductCadasterDto(UUID.randomUUID(), "ProductName2", "Description2", new BigDecimal("20.0"), 200, "P456");
        List<ProductCadasterDto> productCadasterDtos = Arrays.asList(productCadasterDto1, productCadasterDto2);

        when(productService.registerProducts(anyList())).thenReturn(productCadasterDtos);

        ResponseEntity<List<ProductCadasterDto>> response = productStockController.registerProducts(productFormDtos);

        assertEquals(ResponseEntity.ok(productCadasterDtos), response);
        verify(productService, times(1)).registerProducts(anyList());
    }

    @Test
    void testFindById() {
        UUID id = UUID.randomUUID();
        ProductCadasterDto productCadasterDto = new ProductCadasterDto(id, "ProductName", "Description", new BigDecimal("10.0"), 100, "P123");

        when(productService.findById(any(UUID.class))).thenReturn(productCadasterDto);

        ResponseEntity<ProductCadasterDto> response = productStockController.findById(id);

        assertEquals(ResponseEntity.ok(productCadasterDto), response);
        verify(productService, times(1)).findById(any(UUID.class));
    }

    @Test
    void testFindByName() {
        String name = "ProductName";
        ProductCadasterDto productCadasterDto = new ProductCadasterDto(UUID.randomUUID(), name, "Description", new BigDecimal("10.0"), 100, "P123");

        when(productService.findByName(any(String.class))).thenReturn(productCadasterDto);

        ResponseEntity<ProductCadasterDto> response = productStockController.findByName(name);

        assertEquals(ResponseEntity.ok(productCadasterDto), response);
        verify(productService, times(1)).findByName(any(String.class));
    }

    @Test
    void testFindByProductCode() {
        String productCode = "P123";
        ProductCadasterDto productCadasterDto = new ProductCadasterDto(UUID.randomUUID(), "ProductName", "Description", new BigDecimal("10.0"), 100, productCode);

        when(productService.findByProductCode(any(String.class))).thenReturn(productCadasterDto);

        ResponseEntity<ProductCadasterDto> response = productStockController.findByProductCode(productCode);

        assertEquals(ResponseEntity.ok(productCadasterDto), response);
        verify(productService, times(1)).findByProductCode(any(String.class));
    }

    @Test
    void testFindAll() {
        ProductCadasterDto productCadasterDto1 = new ProductCadasterDto(UUID.randomUUID(), "ProductName1", "Description1", new BigDecimal("10.0"), 100, "P123");
        ProductCadasterDto productCadasterDto2 = new ProductCadasterDto(UUID.randomUUID(), "ProductName2", "Description2", new BigDecimal("20.0"), 200, "P456");
        List<ProductCadasterDto> productCadasterDtos = Arrays.asList(productCadasterDto1, productCadasterDto2);

        when(productService.findAll()).thenReturn(productCadasterDtos);

        ResponseEntity<List<ProductCadasterDto>> response = productStockController.findAll();

        assertEquals(ResponseEntity.ok(productCadasterDtos), response);
        verify(productService, times(1)).findAll();
    }

    @Test
    void testDeleteProduct() {
        UUID id = UUID.randomUUID();

        doNothing().when(productService).deleteById(any(UUID.class));

        productStockController.deleteProduct(id);

        verify(productService, times(1)).deleteById(any(UUID.class));
    }
}
