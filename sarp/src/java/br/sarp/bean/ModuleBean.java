/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sarp.bean;

import br.sarp.persistence.module.Module;
import br.sarp.persistence.module.ModuleDAO;
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
@ManagedBean(name = "bmodule")
@RequestScoped
public class ModuleBean {

    private Module module = new Module();
    private List<Module> list;
    private ModuleDAO dao = new ModuleDAO();
    private FacesContext context = FacesContext.getCurrentInstance();

    private String descricaoPesquisa;

    public FacesContext getContext() {
        return context;
    }

    public void setContext(FacesContext context) {
        this.context = context;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module m) {
        this.module = m;
    }

    public String save() {
        boolean mostraMensagemCamposObrigatorios = false;
        if (module.getDescricao().equals("")) {
            mostraMensagemCamposObrigatorios = true;
        } else if (module.getProfessor().equals("")) {
            mostraMensagemCamposObrigatorios = true;
        } else if (module.getData() == null) {
            mostraMensagemCamposObrigatorios = true;
        } else if (module.getDataFimInformarProfessores() == null) {
            mostraMensagemCamposObrigatorios = true;
        }

        if (mostraMensagemCamposObrigatorios) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Preencha os campos Obrigatórios (*)");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "";
        }

        if (module.getDataFimInformarProfessores().after(module.getData())) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Data limite para informar professores não pode ser maior que a data de acontecimento do módulo!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "";
        }

        module.setDescricao(module.getDescricao().toUpperCase());
        module.setProfessor(module.getProfessor().toUpperCase());
        module.setHabilidades(module.getHabilidades().toUpperCase());
        module.setCompetencias(module.getCompetencias().toUpperCase());

        if (module.getId() == 0) {
            module.setDataCadastro(new Date());
            dao.add(module);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Cadastro Realizado com Sucesso!");
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } else {
            dao.update(module);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Edição Realizada com Sucesso!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        return "/coordpedag/modulelist.jsf";
    }

    public String newModule() {
        module = new Module();
        return "/coordpedag/module.jsf";
    }

    public List<Module> getList() {
        list = dao.list();
        return list;
    }

    public String edit() {
        return "/coordpedag/module.jsf";
    }

    public String remove() {
        dao.remove(this.module);
        this.list = dao.list();
        return null;
    }
}
