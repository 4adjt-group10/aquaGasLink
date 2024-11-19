package com.aquagaslink.product.infrastructure;

import com.aquagaslink.product.helper.ProductHelper;
import com.aquagaslink.product.model.ProductModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class ProductRepositoryTest {

    @Mock
    private ProductRepository productRepository;

    @Autowired
    private ProductHelper helper;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }


    @Test
    void shouldSaveProduct(){
        Long id = new Random().nextLong();
        var product = helper.createProductModel(id);
        when(productRepository.save(any(ProductModel.class))).thenReturn(product);
        //Act
        var productSaved = productRepository.save(product);
        //validation
        assertThat(productSaved)
                .isNotNull()
                .isEqualTo(product);

        verify(productRepository, times(1)).save(any(ProductModel.class));

    }

    @Test
    void shouldDeleteProduct(){
        Long id = new Random().nextLong();
        var product = helper.createProductModel(id);

        doNothing().when(productRepository).delete(any(ProductModel.class));

        productRepository.delete(product);

        verify(productRepository, times(1)).delete(any(ProductModel.class));

    }


}
