package com.dkwasniewski.aprimons.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;

@Data
public class MailToken {
    private LocalDateTime creationDate;
    private LocalDateTime expiringDate;
    private String UUID;
    private boolean isActive;
    @DBRef
    private User user;
}
