/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.loja.controle;

import com.example.loja.modelo.ClientePF;
import com.example.loja.modelo.ItemVenda;
import com.example.loja.modelo.Usuario;
import com.example.loja.modelo.Venda;
import com.example.loja.repositorio.ClientePF_Repositorio;
import com.example.loja.repositorio.ProdutoRepository;
import com.example.loja.repositorio.Usuario_Repositorio;
import com.example.loja.repositorio.VendaRepositorio;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author wande
 */
@Scope("request")
@Transactional
@Controller
@RequestMapping("venda")
public class VendaControle {
    @Autowired
    VendaRepositorio repositoryVenda;
    
    @Autowired
    Venda venda;
    
    @Autowired
    ProdutoRepository produtoRepository;
    
    @Autowired
    ClientePF_Repositorio clientepfRepository;
    
    @Autowired
    Usuario_Repositorio repositorioUsuario;
    
    @GetMapping("/form")
    public ModelAndView form(Venda venda, ItemVenda itvenda, ClientePF clientePF, ModelMap model){
        model.addAttribute("produtos", produtoRepository.findProducts());
        model.addAttribute("clientesPF", clientepfRepository.findClientsPF());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getPrincipal());
        return new ModelAndView("/venda/form", model);
    }
    
    @ResponseBody
    @GetMapping("/list")
    public ModelAndView list(ModelMap model){
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //verifica se o usuario logado é admin para controle de acesso a lista de clientes.
        if(authentication.getName().equals("wande")){
            model.addAttribute("listaVenda", repositoryVenda.findSales());
        }else{
            Usuario usuarioLogado = repositorioUsuario.buscarUsuario(authentication.getName());
            model.addAttribute("listaVenda", repositoryVenda.comprasCliente(clientepfRepository.buscarPorUsuario(usuarioLogado.getId()).getId()));
        }

        return new ModelAndView("/venda/listSale", model);
    }
    
    @PostMapping("/add")
    public ModelAndView addItem(ItemVenda itvenda, ModelMap modelMap){
    itvenda.setProduto(produtoRepository.findProduct(itvenda.getProduto().getId()));
    itvenda.setVenda(venda);
    venda.getItemVenda().add(itvenda);
    return new ModelAndView("redirect:/venda/form");
    }
    
    @PostMapping("/save")
    public ModelAndView addVenda(ClientePF clientePF, RedirectAttributes attributes){
        //obs: somente admin (wande) pode selecionar cliente para fazer uma venda.
        //pega o nome do usuário logado na sessão.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if(venda.getItemVenda().isEmpty()){
            attributes.addFlashAttribute("erroItem", "Seu carrinho está vazio.");
        }if(clientePF.getId() == null && authentication.getName().equals("wande")){
            attributes.addFlashAttribute("erroCliente", "Insira um cliente!");
        }if(!attributes.getFlashAttributes().isEmpty()){
            return new ModelAndView("redirect:/venda/form");
        }
        //busca na base de dados infomeçãoes do usuario logado.
        Usuario usuarioLogado = repositorioUsuario.buscarUsuario(authentication.getName());
        
        this.venda.setId(null);
        //vereifica se o usuario logado não é o admin
        if(usuarioLogado.getLogin().equals("wande")){
            //seta cliente selecionado no form finalizar compra em venda/form.
            this.venda.setClientePF(clientepfRepository.findClientPF(clientePF.getId()));
        }else{
            //seta cleinte relacionado ao usuario logado na sessão.
            this.venda.setClientePF(clientepfRepository.buscarPorUsuario(usuarioLogado.getId()));
        }
        repositoryVenda.save(this.venda);
        this.venda.getItemVenda().clear();
        return new ModelAndView("redirect:/venda/list");
    }
    
    @GetMapping("/removeitem/{posicao}")
    public ModelAndView removeItemCarrinho(@PathVariable("posicao") int pos){
        venda.getItemVenda().remove(pos);
        return new ModelAndView("redirect:/venda/form");
    }
    
    @PostMapping("/buscadordata")
    public ModelAndView buscarPorData(@RequestParam("dataBusca") String data, ModelMap model, RedirectAttributes attributes){
        if(data.isEmpty()){
            attributes.addFlashAttribute("erroDataVazia", "Informe uma data Válida!");
        }
        if(!attributes.getFlashAttributes().isEmpty()){
            return new ModelAndView("redirect:/venda/list");
        }
        LocalDate formatardata = LocalDate.parse(data);
        String dataFormatada = formatardata.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        model.addAttribute("listaVenda", repositoryVenda.buscarPorData(dataFormatada));
        return new ModelAndView("/venda/listSale", model);
    }
    
    @GetMapping("/consultar/{idvenda}")
    public ModelAndView detalharcompra(@PathVariable("idvenda") Long id, ModelMap model){
        model.addAttribute("venda", repositoryVenda.findSale(id));
        return new ModelAndView("/venda/detalheCompra", model);
    }
    
    @GetMapping("/comprascliente/{idcliente}")
    public ModelAndView clienteCompras(@PathVariable("idcliente") Long id, ModelMap model){
        model.addAttribute("listaVenda", repositoryVenda.comprasCliente(id));
        return new ModelAndView("/venda/listSale", model);
    }
}
