package com.app.chat.controller;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

import com.app.chat.constants.KafkaConstants;
import com.app.chat.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChatController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

   
    /* public ChatController(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    } */
    
   
    @PostMapping(value = "/api/send", consumes = "application/json", produces = "application/json")
    public void sendMessage(@RequestBody Message message) 
    {
     message.setTimestamp(LocalDateTime.now().toString());
     try
     {
      kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message.toString()).get();
     }
       catch(InterruptedException | ExecutionException e)
       {
        throw new RuntimeException(e);
       }        
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/group")
    public Message broadcastGroupMessage(@Payload Message message)
    {
      return message;
    }

    @MessageMapping("/newUser")
    @SendTo("/topic/group")
    public Message addUser(@Payload Message message, SimpMessageHeaderAccessor headaAccessor)
    {
      headaAccessor.getSessionAttributes().put("username", message.getSender());
      return message;
    }
    
}
