package com.dkwasniewski.aprimons.service;

import com.dkwasniewski.aprimons.model.MailToken;
import com.dkwasniewski.aprimons.repository.MailTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailTokenService {
    @Autowired
    private MailTokenRepository mailTokenRepository;

    public void save(MailToken mailToken){
        mailTokenRepository.save(mailToken);
    }

    public MailToken findByUUID(String UUID){
        return mailTokenRepository.findMailTokenByUUID(UUID);
    }
}
