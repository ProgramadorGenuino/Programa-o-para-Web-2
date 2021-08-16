/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.loja.repositorio;

import com.example.loja.modelo.ClientePF;
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
public class ClientePF_Repositorio {
    @PersistenceContext
    private EntityManager em;
    
    public ClientePF findClientPF(Long id){
        return em.find(ClientePF.class, id);
    }
    
    public List<ClientePF> findClientsPF(){
        Query q = em.createQuery("from ClientePF");
        return q.getResultList();
    }
    
    public void save(ClientePF cliente){
        em.persist(cliente);
    }
    
    public void update(ClientePF cliente){
        em.merge(cliente);
    }
    
    public void remove(Long id){
        ClientePF cliente = em.find(ClientePF.class, id);
        em.remove(cliente);
    }
    
    public List<ClientePF> buscarPorNome(String nome){
        Query q = em.createQuery("from ClientePF cliente where cliente.nome like :nome");
        q.setParameter("nome", nome+"%");
        return q.getResultList();
    }
    
    public ClientePF buscarPorUsuario(Long id){
        Query q = em.createQuery("from ClientePF cliente where cliente.usuario.id = :idUsuario");
        q.setParameter("idUsuario", id);
        ClientePF cliente = (ClientePF) q.getSingleResult();
        return cliente;
    }
}
