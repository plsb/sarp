/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sarp.persistence.person;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author pelusb
 */
@Entity
public class Person {
    
    @Id
    @GeneratedValue
    private Integer id;
    
    private String nome;
    
    private String cpf;
    
    private String email;
    
    private boolean ativo;
    
    private boolean eProfessor;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public boolean iseProfessor() {
        return eProfessor;
    }

    public void seteProfessor(boolean eProfessor) {
        this.eProfessor = eProfessor;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.nome);
        hash = 67 * hash + Objects.hashCode(this.cpf);
        hash = 67 * hash + Objects.hashCode(this.email);
        hash = 67 * hash + (this.ativo ? 1 : 0);
        hash = 67 * hash + (this.eProfessor ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.ativo != other.ativo) {
            return false;
        }
        if (this.eProfessor != other.eProfessor) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.cpf, other.cpf)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", email=" + email + ", ativo=" + ativo + ", eProfessor=" + eProfessor + '}';
    }
    
    public String getStringAtivo(){
        if(ativo){
            return "Ativo";
        } 
        return "Inativo";
    }
    
    public String getStringProfessor(){
        if(eProfessor){
            return "Professor";
        } 
        return "";
    }

}
