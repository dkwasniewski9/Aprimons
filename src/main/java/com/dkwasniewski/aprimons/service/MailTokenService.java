package com.dkwasniewski.aprimons.service;

import com.dkwasniewski.aprimons.model.MailToken;
import com.dkwasniewski.aprimons.model.User;
import com.dkwasniewski.aprimons.repository.MailTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MailTokenService {
    private MailTokenRepository mailTokenRepository;

    private final JavaMailSender mailSender;
    private final MessageSource messageSource;
    private final UserService userService;

    public void save(MailToken mailToken){
        mailTokenRepository.save(mailToken);
    }

    public MailToken findByUUID(String UUID){
        return mailTokenRepository.findMailTokenByUUID(UUID);
    }


    public void sendConfirmationMail(User user){
        MailToken mailToken = new MailToken(UUID.randomUUID().toString(), LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), true, user);
        mailTokenRepository.save(mailToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject(messageSource.getMessage("mail.subject", null, LocaleContextHolder.getLocale()));
        mailMessage.setText(messageSource.getMessage("mail.text", null, LocaleContextHolder.getLocale())
                + " http://localhost:8080/confirm?token="
                + mailToken.getUUID());
        mailSender.send(mailMessage);
    }

    public void confirmUser(MailToken mailToken){
        mailToken.setActive(false);
        mailTokenRepository.save(mailToken);
    }
}
