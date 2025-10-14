package com.microservice.user.producers;

import com.microservice.user.dto.EmailDTO;
import com.microservice.user.models.UserModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {

    final RabbitTemplate rabbitTemplate;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    //

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;


    public void publishMessageEmail(UserModel user)
    {
        var emailDTO = new EmailDTO();
        emailDTO.setUserId(user.getId());
        emailDTO.setEmailTo(user.getEmail());
        emailDTO.setSubject("titulo");
        emailDTO.setText("teste de email");

        rabbitTemplate.convertAndSend("", routingKey, emailDTO);
    }
}
