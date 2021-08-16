/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.loja.repositorio;

import com.example.loja.modelo.Venda;
import java.io.Serializable;
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
public class VendaRepositorio implements Serializable{
    
    @PersistenceContext
    private EntityManager em;
    
    public Venda findSale(Long id){
       return em.find(Venda.class, id);
    }
    
    public List<Venda> findSales(){
        Query q = em.createQuery("from Venda");
        return q.getResultList();
    }
    
    public void save(Venda venda){
        em.persist(venda);
    }
    
    public void update(Venda venda){
        em.merge(venda);
    }
    
    public void remove(Long id){
        Venda venda = em.find(Venda.class, id);
        em.remove(venda);
    }
    
    public List<Venda> buscarPorData(String data){
        Query q = em.createQuery("from Venda as venda where venda.data = :data", Venda.class);
        q.setParameter("data", data);
        return q.getResultList();
    }
    
    public List<Venda> comprasCliente(Long idcliente){
        Query q = em.createQuery("from Venda as venda where venda.clientePF.id = :idcliente");
        q.setParameter("idcliente", idcliente);
        return q.getResultList();
    }
}
