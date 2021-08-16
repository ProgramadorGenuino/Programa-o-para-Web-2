/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.loja.controle;

import com.example.loja.modelo.Produto;
import com.example.loja.repositorio.ProdutoRepository;
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
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author wande
 */
@Transactional
@Controller
@RequestMapping("produto")
public class ProdutoControle {
    @Autowired
    ProdutoRepository repository;
    
    @GetMapping("/form")
    public ModelAndView form(Produto produto){
        return new ModelAndView("/produto/form");
    }
    
    @PostMapping("/save")
    public ModelAndView save(@Valid Produto produto, BindingResult result){
        if(result.hasErrors()){
            return form(produto);
        }
        repository.save(produto);
        return new ModelAndView("redirect:/produto/list");
    }
    
    @GetMapping("/list")
    public ModelAndView list(ModelMap model){
        model.addAttribute("produtos", repository.findProducts());
        return new ModelAndView("/produto/list", model);
    }
    
    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id){
        repository.remove(id);
        return new ModelAndView("redirect:/produto/list");
    }
    
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, ModelMap model){
        model.addAttribute("produto", repository.findProduct(id));
        return new ModelAndView("/produto/form", model);
    }
    
    @PostMapping("/update")
    public ModelAndView update(Produto produto){
        repository.update(produto);
        return new ModelAndView("redirect:/produto/list");
    }
}
