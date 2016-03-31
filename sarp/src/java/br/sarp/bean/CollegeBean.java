/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sarp.bean;

import br.sarp.persistence.college.College;
import br.sarp.persistence.college.CollegeDAO;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Pedro Saraiva
 */
@ManagedBean(name = "bcollege")
@RequestScoped
public class CollegeBean {

    private College college = new College();
    private CollegeDAO dao = new CollegeDAO();
    private FacesContext context = FacesContext.getCurrentInstance();
    
    private String descricaoPesquisa;

    public CollegeBean() {
        if(dao.list().size()>0){
            college = dao.list().get(0);
        }
    }
    
    public FacesContext getContext() {
        return context;
    }

    public void setContext(FacesContext context) {
        this.context = context;
    }

    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }

    public String save() {
        if(college.getDescricao().equals("")){
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Preencha os campos Obrigatórios (*)", ""));
            return "";
        } 
        
        college.setDescricao(college.getDescricao().toUpperCase());
                
        if (college.getId() == 0) {
            dao.add(college);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Cadastro Realizado com Sucesso!", ""));

        } else {
            dao.update(college);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Edição Realizada com Sucesso!", ""));

        }

        return "";
    }

}
