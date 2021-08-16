/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.loja.controle;

import javax.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author wande
 */
@Transactional
@Controller
public class LoginControle {
    
    @GetMapping("/login")
    public String form(){
        return "/autenticacao/login";
    }
    
}
