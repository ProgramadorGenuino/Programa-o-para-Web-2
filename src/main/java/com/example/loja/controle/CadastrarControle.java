/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.loja.controle;

import com.example.loja.modelo.ClientePF;
import com.example.loja.modelo.Usuario;
import com.example.loja.repositorio.ClientePF_Repositorio;
import com.example.loja.repositorio.Usuario_Repositorio;
import com.example.loja.security.Criptografia;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author wande
 */
@Transactional
@Controller 
@RequestMapping("/cadastrar")
public class CadastrarControle {
    
    @Autowired
    Usuario_Repositorio repositorio_usuario;
    
    @Autowired
    ClientePF_Repositorio repositorio_cliente;
    
    @GetMapping("/novo")
    public String form(ClientePF clientePF){
        return "/cadastro/cadastrar";
    }
    
    @PostMapping("/save")
    public String save(@Valid ClientePF clienteCadastro, BindingResult result){
        
        if(result.hasErrors()){
            return form(clienteCadastro);
        }
        
        Criptografia criptografia = new Criptografia();
        repositorio_usuario.save(criptografia.cifrador(clienteCadastro.getUsuario()));
        repositorio_cliente.save(clienteCadastro);
        
        return "/autenticacao/login";
    }
}
