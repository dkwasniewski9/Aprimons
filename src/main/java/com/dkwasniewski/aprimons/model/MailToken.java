package com.dkwasniewski.aprimons.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MailToken {
    private String UUID;
    private LocalDateTime creationDate;
    private LocalDateTime expiringDate;
    private boolean isActive;
    @DBRef
    private User user;
}
