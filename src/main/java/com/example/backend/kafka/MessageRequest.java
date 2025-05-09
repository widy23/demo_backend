package com.example.backend.kafka;

import lombok.*;

@Data
@RequiredArgsConstructor
@Builder
public class MessageRequest {
    private String username;
    private String idSender;
    private String idReceiver;
    private String message;

    public MessageRequest(String username, String idSender, String idReceiver, String message) {
        this.username = username;
        this.idSender = idSender;
        this.idReceiver = idReceiver;
        this.message = message;
    }
}
