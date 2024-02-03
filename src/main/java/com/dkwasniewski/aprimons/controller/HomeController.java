package com.dkwasniewski.aprimons.controller;

import com.dkwasniewski.aprimons.dto.NewUserDTO;
import com.dkwasniewski.aprimons.model.User;
import com.dkwasniewski.aprimons.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@AllArgsConstructor
@Controller
public class HomeController {
    UserService userService;
    private AuthenticationProvider authenticationManager;
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
        user.setPassword(newUserDTO.getPassword());
        userService.saveUser(user);
        return "redirect:/login";
    }
}
