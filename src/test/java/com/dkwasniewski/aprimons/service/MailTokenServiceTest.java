package com.dkwasniewski.aprimons.service;

import com.dkwasniewski.aprimons.model.MailToken;
import com.dkwasniewski.aprimons.model.User;
import com.dkwasniewski.aprimons.repository.MailTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;


class MailTokenServiceTest {
    @Mock
    private MailTokenRepository mailTokenRepository;
    @InjectMocks
    private MailTokenService mailTokenService;
    @Mock
    private MessageSource messageSource;
    @Mock
    private JavaMailSender mailSender;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void testSaveMailToken() {
        User user = new User();
        MailToken mailToken = new MailToken("exampleUUID", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), true, user);

        mailTokenService.save(mailToken);

        verify(mailTokenRepository).save(mailToken);
    }

    @Test
    public void testFindByUUID() {
        String uuid = "exampleUUID";
        User user = new User();
        MailToken expectedMailToken = new MailToken(uuid, LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), true, user);

        expectedMailToken.setUUID(uuid);

        when(mailTokenRepository.findMailTokenByUUID(uuid)).thenReturn(expectedMailToken);

        MailToken actualMailToken = mailTokenService.findByUUID(uuid);

        assertEquals(expectedMailToken, actualMailToken);
    }

    @Test
    public void testSendConfirmationMail() {
        User user = new User();
        user.setEmail("example@example.com");

        when(mailTokenRepository.save(any(MailToken.class))).thenReturn(new MailToken("exampleUUID", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), true, user));
        when(messageSource.getMessage("mail.subject", null, LocaleContextHolder.getLocale())).thenReturn("Confirmation Email");
        when(messageSource.getMessage("mail.text", null, LocaleContextHolder.getLocale())).thenReturn("Please confirm your email: ");

        mailTokenService.sendConfirmationMail(user);

        verify(mailTokenRepository).save(any(MailToken.class));

        verify(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void testConfirmUser() {
        User user = new User();
        MailToken mailToken = new MailToken("exampleUUID", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), true, user);
        mailToken.setActive(false);

        mailTokenService.confirmUser(mailToken);

        assertTrue(mailToken.isActive());

        verify(mailTokenRepository).save(mailToken);
    }
}