package com.example.backend.kafka;

import com.example.backend.repositories.UsersRepository;
import com.example.backend.services.MessageKafkaService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class KafkaController {
    @Autowired
    private KafkaProducerConfig producer;

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private MessageKafkaService service;

    @PostMapping("send/{idReceiver}")
    public ResponseEntity<Object> messageReceive(@PathVariable String idReceiver,
                                                 @NotNull @RequestBody MessageRequest payload,
                                                 @RequestHeader(value = "X-Cliente-ID", required = false) String idSender){
        producer.sendMessage(payload.getUsername(),payload.getIdSender(),idReceiver,payload.getMessage());
        return ResponseEntity.ok("Mensaje enviado exitosamente al vendedor " + idReceiver + ".");
    }

}
