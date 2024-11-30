//package com.aquagaslink.product.service;
//
//import com.aquagaslink.product.helper.ProductHelper;
//import com.aquagaslink.product.infrastructure.ProductRepository;
//import com.aquagaslink.product.model.ProductModel;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.Optional;
//import java.util.Random;
//import java.util.UUID;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//public class ProductServiceTest {
//
//    @Mock
//    private ProductRepository productRepository;
//
//    private ProductService service;
//
//    @Autowired
//    private ProductHelper helper;
//
//    AutoCloseable mock;
//
//    @BeforeEach
//    void setup(){
//        mock = MockitoAnnotations.openMocks(this);
//        service = new ProductService(productRepository);
//    };
//
//    @AfterEach
//    void tearDown() throws Exception {
//        mock.close();
//    }
//
//    @Test
//    void shouldFindById(){
//        Long id = new Random().nextLong();
//        var product = helper.createProductModel(id);
//
//        when(productRepository.findById(any(Long.class)))
//                .thenReturn(Optional.of(product));
//
//
//        var productRead = service.findById(id);
//
//        assertThat(productRead.id()).isEqualTo(product.getId());
//        assertThat(productRead.name()).isEqualTo(product.getName());
//        assertThat(productRead.description()).isEqualTo(product.getDescription());
//        assertThat(productRead.stock()).isEqualTo(product.getStock());
//        assertThat(productRead.productCode()).isEqualTo(product.getProductCode());
//        verify(productRepository, times(1)).findById(any(Long.class));
//
//    }
//
//    @Test
//    void shouldFindByName(){
//        var name = "nome";
//        var product = helper.createProductModelWithName(name);
//
//        when(productRepository.findByName(any(String.class)))
//                .thenReturn(Optional.of(product));
//
//
//        var productRead = service.findByName(name);
//
//        assertThat(productRead.id()).isEqualTo(product.getId());
//        assertThat(productRead.name()).isEqualTo(product.getName());
//        assertThat(productRead.description()).isEqualTo(product.getDescription());
//        assertThat(productRead.stock()).isEqualTo(product.getStock());
//        assertThat(productRead.productCode()).isEqualTo(product.getProductCode());
//        verify(productRepository, times(1)).findByName(any(String.class));
//
//    }
//
//    @Test
//    void shouldFindByProductCode(){
//        var code = "productCode";
//        var product = helper.createProductModelWithProductCode(code);
//
//        when(productRepository.findByProductCode(any(String.class)))
//                .thenReturn(Optional.of(product));
//
//
//        var productRead = service.findByProductCode(code);
//
//        assertThat(productRead.id()).isEqualTo(product.getId());
//        assertThat(productRead.name()).isEqualTo(product.getName());
//        assertThat(productRead.description()).isEqualTo(product.getDescription());
//        assertThat(productRead.stock()).isEqualTo(product.getStock());
//        assertThat(productRead.productCode()).isEqualTo(product.getProductCode());
//        verify(productRepository, times(1)).findByProductCode(any(String.class));
//
//    }
//
//    @Test
//    void shouldDeleteProductById(){
//        Long id = new Random().nextLong();
//
//        doNothing().when(productRepository).delete(any(ProductModel.class));
//
//        service.deleteById(id);
//
//        verify(productRepository, times(1)).deleteById(any(Long.class));
//
//    }
//
//    @Test
//    void shouldCreateProduct(){
//        Long id = new Random().nextLong();
//        var productModel = helper.createProductModel(id);
//        var product = helper.createProductDto(id);
//        when(productRepository.save(any(ProductModel.class)))
//                .thenReturn(productModel);
//
//        var productCreated = service.registerProduct(product);
//
//        assertThat(productCreated)
//                .isNotNull();
//
//        assertThat(productCreated.id()).isEqualTo(product.id());
//        assertThat(productCreated.name()).isEqualTo(product.name());
//        assertThat(productCreated.description()).isEqualTo(product.description());
//        assertThat(productCreated.stock()).isEqualTo(product.stock());
//        assertThat(productCreated.price()).isEqualTo(product.price());
//        assertThat(productCreated.productCode()).isEqualTo(product.productCode());
//        assertThat(product.id()).isNotNull();
//
//        verify(productRepository,times(1)).save(any(ProductModel.class));
//
//    }
//}
