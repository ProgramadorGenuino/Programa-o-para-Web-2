/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.loja.repositorio;

import com.example.loja.modelo.Produto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author wande
 */
@Repository
public class ProdutoRepository {
    
    @PersistenceContext
    private EntityManager emp;
    
    public Produto findProduct(Long id){
       return emp.find(Produto.class, id);
    }
    
    public List<Produto> findProducts(){
        Query q = emp.createQuery("from Produto");
        return q.getResultList();
    }
    
    public void save(Produto produto){
        emp.persist(produto);
    }
    
    public void update(Produto produto){
        emp.merge(produto);
    }
    
    public void remove(Long id){
        Produto produto = emp.find(Produto.class, id);
        emp.remove(produto);
    }
}
