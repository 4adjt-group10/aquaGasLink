package com.aquagaslink.order_management.infrastructure;

import com.aquagaslink.order_management.helper.ClientOrderHelper;
import com.aquagaslink.order_management.model.ClientOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ClientOrderRepositoryTest {

    @Mock
    private ClientOrderRepository clientOrderRepository;

    @Autowired
    private ClientOrderHelper helper;

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
        var id ="order";
        var order = helper.createClientOrder(id);
        when(clientOrderRepository.save(any(ClientOrder.class))).thenReturn(order);
        //Act
        var orderSaved = clientOrderRepository.save(order);
        //validation
        assertThat(orderSaved)
                .isNotNull()
                .isEqualTo(order);

        //verifica que o repository foi chamado ao menos 1 vez para salvar algo
        verify(clientOrderRepository, times(1)).save(any(ClientOrder.class));

    }
}
