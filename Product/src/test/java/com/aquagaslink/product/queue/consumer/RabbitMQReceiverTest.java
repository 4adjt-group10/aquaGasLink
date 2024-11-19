//package com.aquagaslink.product.queue.consumer;
//
//import com.rabbitmq.client.Channel;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.core.MessageProperties;
//
//import java.io.IOException;
//import org.springframework.amqp.rabbit.core.*;
//
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class RabbitMQReceiverTest {
//
//    @Mock
//    private Channel channel;
//
//    @InjectMocks
//    private RabbitMQReceiver rabbitMQReceiver;
//
//    @Test
//    public void testReceiveMessageSuccess() throws IOException {
//        // Given
//        String messageBody = "Test Message";
//        Message message = new Message(messageBody.getBytes(), new MessageProperties());
//
//        // When
//        rabbitMQReceiver.receiveMessage(message, channel, 1L);
//
//        // Then
//        verify(channel).basicAck(1L, false);
//    }
//
//    @Test
//    public void testReceiveMessageFailure() throws IOException {
//        // Given
//        String messageBody = "Test Message";
//        Message message = new Message(messageBody.getBytes(), new MessageProperties());
//        doThrow(new RuntimeException("Test Exception")).when(channel).basicAck(anyLong(), anyBoolean());
//
//        // When
//        rabbitMQReceiver.receiveMessage(message, channel, 1L);
//
//        // Then
//        verify(channel).basicNack(1L, false, false);
//    }
//}
