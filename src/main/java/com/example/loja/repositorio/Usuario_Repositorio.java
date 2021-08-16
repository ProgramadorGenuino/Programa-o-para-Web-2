/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.loja.repositorio;

import com.example.loja.modelo.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author wande
 */
@Repository
public class Usuario_Repositorio {
    
    @PersistenceContext
    private EntityManager em;
    
    public Usuario buscarUsuario(String login){
        Query q = em.createQuery("from Usuario as u where u.login = :login");
        q.setParameter("login", login);
        Usuario usuario = (Usuario) q.getSingleResult();
        return usuario;
    }
    
    public void save(Usuario usuario){
        em.persist(usuario);
    }
    
}
