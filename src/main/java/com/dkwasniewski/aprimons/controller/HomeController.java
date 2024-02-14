package com.dkwasniewski.aprimons.controller;

import com.dkwasniewski.aprimons.dto.LoginUserDTO;
import com.dkwasniewski.aprimons.dto.NewUserDTO;
import com.dkwasniewski.aprimons.model.MailToken;
import com.dkwasniewski.aprimons.model.User;
import com.dkwasniewski.aprimons.service.MailTokenService;
import com.dkwasniewski.aprimons.service.PokeballService;
import com.dkwasniewski.aprimons.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import util.Role;

import java.time.LocalDateTime;
import java.util.Locale;

@AllArgsConstructor
@Controller
public class HomeController {
    private UserService userService;
    private PokeballService pokeballService;
    private MailTokenService mailTokenService;
    private AuthenticationProvider authenticationManager;
    private final LocaleResolver localeResolver;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;

    @GetMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        Locale currentLocale = (Locale) request.getSession().getAttribute("locale");
        if (currentLocale == null) {
            Locale defaultLocale = Locale.getDefault();
            localeResolver.setLocale(request, response, defaultLocale);
            request.getSession().setAttribute("locale", defaultLocale);
        }
        model.addAttribute(pokeballService.getAllPokeball());
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginUserDTO", new LoginUserDTO());
        return "login";
    }

    @GetMapping("/language")
    public String changeLanguage(HttpServletRequest request, HttpServletResponse response) {
        Locale currentLocale = (Locale) request.getSession().getAttribute("locale");
        Locale newLocale;
        if (currentLocale == null || currentLocale.getLanguage().equals(Locale.ENGLISH.getLanguage())) {
            newLocale = Locale.forLanguageTag("pl");
        } else {
            newLocale = Locale.ENGLISH;
        }
        localeResolver.setLocale(request, response, newLocale);
        request.getSession().setAttribute("locale", newLocale);
        return "redirect:" + request.getHeader("referer");
    }

    /*@PostMapping("/login")
    public String postLogin(LoginUserDTO loginUserDTO){
            User user = userService.findUserByUsername(loginUserDTO.getUsername());
            if(!user.isActive()){
                throw new NotActivatedException("Account not active", user);
            }
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginUserDTO.getUsername(), loginUserDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/home";
    }*/
    /*@GetMapping("/logout")
    public String logout(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/";
    }*/
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("newUserDTO", new NewUserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(@Valid NewUserDTO newUserDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        if (userService.findUserByUsername(newUserDTO.getUsername()) != null) {
            model.addAttribute("UserExists", true);
            return "register";
        }
        User user = new User(newUserDTO.getUsername(),
                newUserDTO.getEmail(),
                passwordEncoder.encode(newUserDTO.getPassword()),
                Role.USER);
        userService.saveUser(user);
        mailTokenService.sendConfirmationMail(user);
        return "redirect:/login";
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam String token, Model model, HttpServletRequest request) {
        MailToken mailToken = mailTokenService.findByUUID(token);
        if (mailToken.getExpiringDate().isAfter(LocalDateTime.now())) {
            mailTokenService.confirmUser(mailToken);
            User user = mailToken.getUser();
            userService.activateUser(user);
        }
        model.addAttribute("message",
                messageSource.getMessage("user.activation", null, (Locale) request.getSession().getAttribute("locale")));
        return "confirm";
    }
}
