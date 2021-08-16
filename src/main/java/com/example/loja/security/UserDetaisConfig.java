/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.loja.security;

import com.example.loja.modelo.Usuario;
import com.example.loja.repositorio.Usuario_Repositorio;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author wande
 */
@Transactional
@Repository
public class UserDetaisConfig implements UserDetailsService{
    
    @Autowired
    Usuario_Repositorio repositorio;
        
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = repositorio.buscarUsuario(login);
        
        if(usuario == null)
            throw new UsernameNotFoundException("Usuário não encontrado!");
            
        return new User(usuario.getUsername(), usuario.getPassword(), true, true, true, true, usuario.getAuthorities());    
    }
    
}
