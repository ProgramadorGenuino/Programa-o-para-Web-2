/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.loja.controle;

import com.example.loja.modelo.ClientePF;
import com.example.loja.repositorio.ClientePF_Repositorio;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author wande
 */
@Transactional
@Controller
@RequestMapping("clientepf")
public class ClientePFControle {
    @Autowired
    ClientePF_Repositorio repository;
    
    @GetMapping("/form")
    public ModelAndView form(ClientePF clientepf){
        return new ModelAndView("/cliente/form");
    }
    
    @GetMapping("/list")
    public ModelAndView list(ModelMap model){
        model.addAttribute("listaCliente", repository.findClientsPF());
        return new ModelAndView("/cliente/list", model);
    }
    
    @PostMapping("/save")
    public ModelAndView save(@Valid ClientePF clientepf, BindingResult result){
        if(result.hasErrors()){
            return form(clientepf);
        }
        repository.save(clientepf);
        return new ModelAndView("redirect:/clientepf/list");
    }
    
    @PostMapping("/update")
    public ModelAndView update(ClientePF clientepf){
        repository.update(clientepf);
        return new ModelAndView("redirect:/clientepf/list");
    }
    
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, ModelMap model){
        model.addAttribute("clientePF", repository.findClientPF(id));
        return new ModelAndView("/cliente/form", model);
    }
    
    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id){
        repository.remove(id);
        return new ModelAndView("redirect:/clientepf/list");
    }
    
    @PostMapping("/buscarcliente")
    public ModelAndView buscarPorNome(@RequestParam("nomeCliente") String nome, ModelMap model){
        model.addAttribute("listaCliente", repository.buscarPorNome(nome));
        return new ModelAndView("/cliente/list", model);
    }
}
