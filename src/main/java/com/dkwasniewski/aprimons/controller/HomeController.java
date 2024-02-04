package com.dkwasniewski.aprimons.controller;

import com.dkwasniewski.aprimons.dto.NewUserDTO;
import com.dkwasniewski.aprimons.model.MailToken;
import com.dkwasniewski.aprimons.model.User;
import com.dkwasniewski.aprimons.service.MailTokenService;
import com.dkwasniewski.aprimons.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import util.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Controller
public class HomeController {
    private UserService userService;
    private MailTokenService mailTokenService;
    private AuthenticationProvider authenticationManager;
    private PasswordEncoder passwordEncoder;
    private MailSender mailSender;
    @GetMapping("/")
    public String home() {

        return "index";
    }
    @GetMapping("/login")
    public String login() {

        return "login";
    }
    @PostMapping("/login")
    public String postLogin(String username, String password, HttpServletRequest request){
        try{
            if(!userService.find(username).isActive()){
                return "redirect:/login";
            }
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/home";
        }
        catch (Exception e){
            return "redirect:/error";
        }
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("newUserDTO", new NewUserDTO());
        return "register";
    }
    @PostMapping("/register")
    public String registerPost(NewUserDTO newUserDTO){
        if(userService.find(newUserDTO.getUsername()) != null){
            return "redirect:/error";
        }
        User user = new User();
        user.setUsername(newUserDTO.getUsername());
        user.setEmail(newUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));
        user.setRole(Role.user);
        userService.saveUser(user);
        MailToken mailToken = new MailToken();
        mailToken.setUUID(UUID.randomUUID().toString());
        mailToken.setCreationDate(LocalDateTime.now());
        mailToken.setExpiringDate(LocalDateTime.now().plusMinutes(30));
        mailToken.setActive(true);
        mailToken.setUser(user);
        mailTokenService.save(mailToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Aprimons account Registration!");
        mailMessage.setText("To confirm your account, please click this link:"
        +"http://localhost:8080/confirm?token="
                + mailToken.getUUID());
        mailSender.send(mailMessage);
        return "redirect:/login";
    }
    @GetMapping("/confirm")
    public String confirm(@RequestParam String token){
        MailToken mailToken = mailTokenService.findByUUID(token);
        if(mailToken.getExpiringDate().isAfter(LocalDateTime.now())){
            User user = mailToken.getUser();
            user.setActive(true);
            userService.saveUser(user);
            mailToken.setActive(false);
            mailTokenService.save(mailToken);
        }
        return "login";
    }
}
