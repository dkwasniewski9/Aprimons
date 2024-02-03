package com.dkwasniewski.aprimons.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@AllArgsConstructor
@Controller
public class HomeController {
    UserService userService;
    private AuthenticationProvider authenticationManager;
    @GetMapping("/")
    public String greeting() {
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
            HttpSession session = request.getSession();
            session.setAttribute("loggedUser", authentication.getName());
            Authentication authentication2 = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("Principal: " + authentication2.getPrincipal());
            System.out.println("Authorities: " + authentication2.getAuthorities());
            return "redirect:/home";
        }
        catch (Exception e){
            return "redirect:/error";
        }
    }
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public String manager() {
        return "manager";
    }

    @GetMapping("/employee")
    public String employee() {
        return "employee";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
