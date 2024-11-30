package com.aquagaslink.product.service;

import com.aquagaslink.product.controller.dto.ProductFormDto;
import com.aquagaslink.product.helper.ProductHelper;
import com.aquagaslink.product.infrastructure.ProductRepository;
import com.aquagaslink.product.model.ProductModel;
import com.aquagaslink.product.queue.ProductEventGateway;
import com.aquagaslink.product.queue.dto.OrderToProductIn;
import com.aquagaslink.product.queue.dto.ProductToOrderOut;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductEventGateway productEventGateway;

    private ProductService service;

    private ProductHelper helper;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        service = new ProductService(productRepository, productEventGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void shouldFindById() {
        UUID id = UUID.randomUUID();
        var product = helper.createProductModel(id);

        when(productRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(product));


        var productRead = service.findById(id);

        //System.out.println(productRead.productCode());
        assertThat(productRead.id()).isEqualTo(product.getId());
        assertThat(productRead.name()).isEqualTo(product.getName());
        assertThat(productRead.description()).isEqualTo(product.getDescription());
        assertThat(productRead.stock()).isEqualTo(product.getStock());
        assertThat(productRead.productCode()).isEqualTo(product.getProductCode());
        verify(productRepository, times(1)).findById(any(UUID.class));

    }

    @Test
    void shouldFindByName() {
        var name = "nome";
        var product = helper.createProductModelWithName(name);

        when(productRepository.findByName(any(String.class)))
                .thenReturn(Optional.of(product));


        var productRead = service.findByName(name);

        //System.out.println(productRead.name());
        assertThat(productRead.id()).isEqualTo(product.getId());
        assertThat(productRead.name()).isEqualTo(product.getName());
        assertThat(productRead.description()).isEqualTo(product.getDescription());
        assertThat(productRead.stock()).isEqualTo(product.getStock());
        assertThat(productRead.productCode()).isEqualTo(product.getProductCode());
        verify(productRepository, times(1)).findByName(any(String.class));

    }

    @Test
    void shouldFindByProductCode() {
        var code = "productCode";
        var product = helper.createProductModelWithProductCode(code);

        when(productRepository.findByProductCode(any(String.class)))
                .thenReturn(Optional.of(product));


        var productRead = service.findByProductCode(code);

        //System.out.println(productRead.name());
        assertThat(productRead.id()).isEqualTo(product.getId());
        assertThat(productRead.name()).isEqualTo(product.getName());
        assertThat(productRead.description()).isEqualTo(product.getDescription());
        assertThat(productRead.stock()).isEqualTo(product.getStock());
        assertThat(productRead.productCode()).isEqualTo(product.getProductCode());
        verify(productRepository, times(1)).findByProductCode(any(String.class));

    }

    @Test
    void shouldDeleteProductById() {
        UUID id = UUID.randomUUID();

        //Define comportamento quando for chamado esse método com o UUID
        doNothing().when(productRepository).delete(any(ProductModel.class));

        service.deleteById(id);

        verify(productRepository, times(1)).deleteById(any(UUID.class));

    }

    @Test
    void shouldCreateProduct() {
        UUID id = UUID.randomUUID();
        var productModel = helper.createProductModel(id);
        var productResponse = helper.createProductCadasterDto(id);
        var product = helper.createProductDto(id);
        when(productRepository.save(any(ProductModel.class)))
                .thenReturn(productModel);

        var productCreated = service.registerProduct(product);

        assertThat(productCreated)
                .isNotNull();

        assertThat(productCreated.id()).isEqualTo(product.id());
        assertThat(productCreated.name()).isEqualTo(product.name());
        assertThat(productCreated.description()).isEqualTo(product.description());
        assertThat(productCreated.stock()).isEqualTo(product.stock());
        assertThat(productCreated.price()).isEqualTo(product.price());
        assertThat(productCreated.productCode()).isEqualTo(product.productCode());
        assertThat(product.id()).isNotNull();

        verify(productRepository, times(1)).save(any(ProductModel.class));

    }

    @Test
    void shouldUpdateProduct() {
        var productDto = helper.createProductDto(UUID.randomUUID());

        UUID id = UUID.randomUUID();
        var product = helper.createProductModel(id);

        when(productRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(product));
        var productModel = helper.createProductModel(id);
        when(productRepository.save(any(ProductModel.class)))
                .thenReturn(productModel);

        var productCreated = service.registerProduct(productDto);

        assertThat(productCreated)
                .isNotNull();


        verify(productRepository, times(1)).save(any(ProductModel.class));

    }

    @Test
    void shouldUpdateProductByname() {
        var productDto = helper.createProductDtoWithoutId();

        UUID id = UUID.randomUUID();
        var product = helper.createProductModel(id);

        when(productRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(product));
        var productModel = helper.createProductModel(id);
        when(productRepository.save(any(ProductModel.class)))
                .thenReturn(productModel);

        var productCreated = service.registerProduct(productDto);

        assertThat(productCreated)
                .isNotNull();


        verify(productRepository, times(1)).save(any(ProductModel.class));

    }

    @Test
    void shouldUpdateProductByCode() {
        var productDto = helper.createProductDtoWithoutId();

        UUID id = UUID.randomUUID();
        var product = helper.createProductModel(id);

        when(productRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(product));
        var productModel = helper.createProductModel(id);
        when(productRepository.save(any(ProductModel.class)))
                .thenReturn(productModel);

        var productCreated = service.registerProduct(productDto);

        assertThat(productCreated)
                .isNotNull();


        verify(productRepository, times(1)).save(any(ProductModel.class));

    }

    @Test
    void shouldFindAll() {
        List<ProductModel> products = Arrays.asList(helper.createProductModel(UUID.randomUUID()), helper.createProductModel(UUID.randomUUID()));
        when(productRepository.findAll()).thenReturn(products);
        var productCadasterDtos = service.findAll();
        assertThat(productCadasterDtos).hasSize(2);
        assertThat(productCadasterDtos.get(0).id()).isEqualTo(products.get(0).getId());
        assertThat(productCadasterDtos.get(1).id()).isEqualTo(products.get(1).getId());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void shouldRegisterProducts() {
        List<ProductFormDto> productFormDtos = Arrays.asList(helper.createProductDto(UUID.randomUUID()), helper.createProductDto(UUID.randomUUID()));
        List<ProductModel> productModels = Arrays.asList(helper.createProductModel(UUID.randomUUID()), helper.createProductModel(UUID.randomUUID()));
        when(productRepository.save(any(ProductModel.class))).thenReturn(productModels.get(0), productModels.get(1));
        var productsRegistered = service.registerProducts(productFormDtos);
        assertThat(productsRegistered).hasSize(2);
        assertThat(productsRegistered.get(0).name()).isEqualTo(productFormDtos.get(0).name());
        assertThat(productsRegistered.get(1).name()).isEqualTo(productFormDtos.get(1).name());
        verify(productRepository, times(2)).save(any(ProductModel.class));
    }

    @Test
    void shouldValidateProductWithSufficientStock() {
        UUID orderId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        OrderToProductIn payload = new OrderToProductIn(orderId, 5, productId);
        ProductModel productModel = helper.createProductModel(productId);
        productModel.setStock(10);

        when(productRepository.findById(productId)).thenReturn(Optional.of(productModel));

        service.validateProduct(payload);

        ArgumentCaptor<ProductToOrderOut> argumentCaptor = ArgumentCaptor.forClass(ProductToOrderOut.class);
        verify(productEventGateway, times(1)).sendOrderEvent(argumentCaptor.capture());

        ProductToOrderOut productToOrderOut = argumentCaptor.getValue();
        assertThat(productToOrderOut.hasError()).isFalse();
    }

    @Test
    void shouldValidateProductWithInsufficientStock() {
        UUID orderId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        OrderToProductIn payload = new OrderToProductIn(orderId, 5, productId);
        ProductModel productModel = helper.createProductModel(productId);
        productModel.setStock(2);

        when(productRepository.findById(productId)).thenReturn(Optional.of(productModel));

        service.validateProduct(payload);

        ArgumentCaptor<ProductToOrderOut> argumentCaptor = ArgumentCaptor.forClass(ProductToOrderOut.class);
        verify(productEventGateway, times(1)).sendOrderEvent(argumentCaptor.capture());

        ProductToOrderOut productToOrderOut = argumentCaptor.getValue();
        assertThat(productToOrderOut.hasError()).isTrue();
    }

    @Test
    void shouldValidateProductNotFound() {
        UUID orderId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        OrderToProductIn payload = new OrderToProductIn(orderId, 5, productId);

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        service.validateProduct(payload);

        ArgumentCaptor<ProductToOrderOut> argumentCaptor = ArgumentCaptor.forClass(ProductToOrderOut.class);
        verify(productEventGateway, times(1)).sendOrderEvent(argumentCaptor.capture());

        ProductToOrderOut productToOrderOut = argumentCaptor.getValue();
        assertThat(productToOrderOut.hasError()).isTrue();
    }

}
