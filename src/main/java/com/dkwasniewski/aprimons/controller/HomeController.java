package com.dkwasniewski.aprimons.controller;

import com.dkwasniewski.aprimons.dto.NewUserDTO;
import com.dkwasniewski.aprimons.model.MailToken;
import com.dkwasniewski.aprimons.model.User;
import com.dkwasniewski.aprimons.service.MailTokenService;
import com.dkwasniewski.aprimons.service.PokeballService;
import com.dkwasniewski.aprimons.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import util.Role;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@AllArgsConstructor
@Controller
public class HomeController {
    private UserService userService;
    private PokeballService pokeballService;
    private MailTokenService mailTokenService;
    private AuthenticationProvider authenticationManager;
    private PasswordEncoder passwordEncoder;
    private final LocaleResolver localeResolver;
    @GetMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        Locale currentLocale = (Locale) request.getSession().getAttribute("locale");
        if(currentLocale == null) {
            Locale defaultLocale = Locale.getDefault();
            localeResolver.setLocale(request, response, defaultLocale);
            request.getSession().setAttribute("locale", defaultLocale);
        }
        model.addAttribute(pokeballService.getAllPokeball());
        return "index";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/language")
    public String changeLanguage(HttpServletRequest request, HttpServletResponse response) {
        Locale currentLocale = (Locale) request.getSession().getAttribute("locale");
        Locale newLocale;
        if(currentLocale == null || currentLocale.getLanguage().equals(Locale.ENGLISH.getLanguage())) {
            newLocale = Locale.forLanguageTag("pl");
        } else {
            newLocale = Locale.ENGLISH;
        }
        localeResolver.setLocale(request, response, newLocale);
        request.getSession().setAttribute("locale", newLocale);
        return "redirect:" + request.getHeader("referer");
    }
    @GetMapping("/search")
    public String search() {

        return "index";
    }
    @PostMapping("/login")
    public String postLogin(String username, String password, HttpServletRequest request){
        //TODO sprawdzanie hasła
        try{
            if(!userService.findUserByUsername(username).isActive()){
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
    @GetMapping("/logout")
    public String logout(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/";
    }
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("newUserDTO", new NewUserDTO());
        return "register";
    }
    @PostMapping("/register")
    public String registerPost(NewUserDTO newUserDTO){
        if(userService.findUserByUsername(newUserDTO.getUsername()) != null){
            return "redirect:/error";
        }
        User user = new User();
        user.setUsername(newUserDTO.getUsername());
        user.setEmail(newUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));
        user.setRole(Role.USER);
        userService.saveUser(user);
        mailTokenService.sendConfirmationMail(user);
        return "redirect:/login";
    }
    @GetMapping("/confirm")
    public String confirm(@RequestParam String token){
        MailToken mailToken = mailTokenService.findByUUID(token);
        if(mailToken.getExpiringDate().isAfter(LocalDateTime.now())){
            mailTokenService.confirmUser(mailToken);
            User user = mailToken.getUser();
            userService.activateUser(user);
        }
        return "login";
    }
}
