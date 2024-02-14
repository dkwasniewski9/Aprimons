package com.dkwasniewski.aprimons.controller;

import com.dkwasniewski.aprimons.exceptions.NotActivatedException;
import com.dkwasniewski.aprimons.exceptions.UserNotFoundException;
import com.dkwasniewski.aprimons.service.MailTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {
    private MailTokenService mailTokenService;
    private MessageSource messageSource;
    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException ex, Model model){
        model.addAttribute("errorMessage", ex.getMessage());
        return "userCollectionNotFoundError";
    }
    @ExceptionHandler(NotActivatedException.class)
    public String handleNotAdctivatedException(NotActivatedException ex, Model model, HttpServletRequest request){
        model.addAttribute("message", ex.getMessage());
        mailTokenService.sendConfirmationMail(ex.getUser());
        model.addAttribute("mailedTo", messageSource.getMessage("mail.sentAgain", null, (Locale) request.getSession().getAttribute("locale")) + ex.getUser().getEmail());
        return "confirm";
    }
}
