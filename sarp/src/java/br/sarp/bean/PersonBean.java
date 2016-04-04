/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sarp.bean;

import br.sarp.persistence.module.Module;
import br.sarp.persistence.module.ModuleDAO;
import br.sarp.persistence.person.Person;
import br.sarp.persistence.person.PersonDAO;
import br.sarp.util.Util;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.swing.JOptionPane;

/**
 *
 * @author Pedro Saraiva
 */
@ManagedBean(name = "bperson")
@RequestScoped
public class PersonBean {

    private Person person = new Person();
    private List<Person> list;
    private PersonDAO dao = new PersonDAO();
    private FacesContext context = FacesContext.getCurrentInstance();

    public FacesContext getContext() {
        return context;
    }

    public void setContext(FacesContext context) {
        this.context = context;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String save() {
        boolean mostraMensagemCamposObrigatorios = false;
        if (person.getNome().equals("")) {
            mostraMensagemCamposObrigatorios = true;
        } else if (person.getCpf().equals("")) {
            mostraMensagemCamposObrigatorios = true;
        } else if (person.getEmail().equals("")) {
            mostraMensagemCamposObrigatorios = true;
        }

        if (mostraMensagemCamposObrigatorios) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Preencha os campos Obrigatórios (*)");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "";
        }

        if (!Util.CPF(person.getCpf())) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "CPF Inválido!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "";
        }

        person.setNome(person.getNome().toUpperCase());
        person.setEmail(person.getEmail().toLowerCase());

        if (person.getId() == null) {
            if (dao.checkExists("cpf", person.getCpf()).size() > 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "CPF Já Cadastrado!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return "";
            }

            if (dao.checkExists("email", person.getEmail()).size() > 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Email já Cadastrado!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return "";
            }

            person.setAtivo(true);
            dao.add(person);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Cadastro Realizado com Sucesso!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            dao.update(person);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Edição Realizada com Sucesso!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        return "/coordcurso/personlist.jsf";
    }

    public String newPerson() {
        person = new Person();
        person.setAtivo(true);
        return "/coordcurso/person.jsf";
    }

    public List<Person> getList() {
        list = dao.list();
        return list;
    }

    public String edit() {
        return "/coordcurso/person.jsf";
    }

    public String remove() {
        dao.remove(this.person);
        this.list = dao.list();
        return null;
    }
}
