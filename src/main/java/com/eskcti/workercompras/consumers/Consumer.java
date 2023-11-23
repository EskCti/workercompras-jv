package com.eskcti.workercompras.consumers;

import com.eskcti.workercompras.models.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class Consumer {

    private final ObjectMapper objectMapper;

    @RabbitListener(queues = {"${queue.name}"})
    public void consumer(@Payload Message message) {
        try {
            Order order = objectMapper.readValue(message.getBody(), Order.class);
            System.out.println("Mensagem recebida no WorkerCompras: " + order);
        } catch (Exception e) {
            System.err.println("Erro ao converter Message para objeto Order: " + e.getMessage());
        }
    }
}
