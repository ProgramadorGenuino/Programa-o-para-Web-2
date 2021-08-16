/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.loja.security;

import com.example.loja.modelo.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author wande
 */
public class Criptografia {
    
    public String cifrador(String password){
        return passwordEncoder().encode(password);
    }
    
    public Usuario cifrador(Usuario usuario){
        usuario.setPassword(passwordEncoder().encode(usuario.getPassword()));
        return usuario;
    }
    
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
