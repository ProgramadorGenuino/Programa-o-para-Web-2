/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.loja.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author wande
 */
@Entity
@Table(name = "cliente_pessoafisica")
public class ClientePF extends Cliente implements Serializable{
    @NotBlank
    private String cpf;
    
    @NotBlank
    private String nome;
    

    public ClientePF() {
    }

    public ClientePF(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    
}
