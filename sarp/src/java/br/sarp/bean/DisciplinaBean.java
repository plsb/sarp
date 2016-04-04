/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sarp.bean;

import br.sarp.persistence.college.College;
import br.sarp.persistence.college.CollegeDAO;
import br.sarp.persistence.course.Course;
import br.sarp.persistence.course.CourseDAO;
import br.sarp.persistence.disciplina.Disciplina;
import br.sarp.persistence.disciplina.DisciplinaDAO;
import br.sarp.persistence.module.Module;
import br.sarp.persistence.module.ModuleDAO;
import br.sarp.persistence.person.Person;
import br.sarp.persistence.person.PersonDAO;
import br.sarp.persistence.semestre.Semestre;
import br.sarp.persistence.semestre.SemestreDAO;
import br.sarp.user.User;
import br.sarp.util.UsuarioAtivo;
import br.sarp.util.Util;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.swing.JOptionPane;

/**
 *
 * @author Pedro Saraiva
 */
@ManagedBean(name = "bdisciplina")
public class DisciplinaBean {

    private Disciplina disciplina = new Disciplina();
    private List<Disciplina> list;
    private DisciplinaDAO dao = new DisciplinaDAO();

    public String save() {
        try {
            CourseDAO cDAO = new CourseDAO();
            Course c = cDAO.checkExists("coordenador", UsuarioAtivo.getUser().getPessoa()).get(0);
            if (c != null) {
                disciplina.setCurso(c);
            }
        } catch (Exception e) {

        }
        
        boolean mostraMensagemCamposObrigatorios = false;
        if (disciplina.getCurso() == null) {
            mostraMensagemCamposObrigatorios = true;
        } else if (disciplina.getSemestre() == null) {
            mostraMensagemCamposObrigatorios = true;
        } else if (disciplina.getDescricao().equals("")) {
            mostraMensagemCamposObrigatorios = true;
        } else if (disciplina.getCh()==null) {
            mostraMensagemCamposObrigatorios = true;
        }

        if (mostraMensagemCamposObrigatorios) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Preencha os campos Obrigatórios (*)");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "";

        }

        disciplina.setDescricao(disciplina.getDescricao().toUpperCase());

        if (disciplina.getId() == 0) {

            dao.add(disciplina);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Cadastro Realizado com Sucesso!");
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } else {
            dao.update(disciplina);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Edição Realizada com Sucesso!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        return "/coordcurso/disciplinalist.jsf";
    }

    public String newDisciplina() {
        disciplina = new Disciplina();
        try {
            CourseDAO cDAO = new CourseDAO();
            Course c = cDAO.checkExists("coordenador", UsuarioAtivo.getUser().getPessoa()).get(0);
            if (c != null) {
                disciplina.setCurso(c);
            }
        } catch (Exception e) {

        }
        return "/coordcurso/disciplina.jsf";
    }

    public List<Disciplina> getList() {
        try {
            CourseDAO cDAO = new CourseDAO();
            Course c = cDAO.checkExists("coordenador", UsuarioAtivo.getUser().getPessoa()).get(0);
            list = dao.listOrderBy("descricao", "curso", c);
        } catch (Exception e) {
            list = dao.list();
        }
        
        return list;
    }

    public String edit() {
        return "/coordcurso/disciplina.jsf";
    }

    public String remove() {
        dao.remove(this.disciplina);
        this.list = dao.list();
        return null;
    }

    private List<SelectItem> semestreSelect;

    public List<SelectItem> getSemestreSelect() {
        if (this.semestreSelect == null) {
            this.semestreSelect = new ArrayList<SelectItem>();
            //ContextoBean contextoBean = scs.util.ContextoUtil.getContextoBean();

            SemestreDAO dao = new SemestreDAO();
            List<Semestre> categorias = dao.listOrderBy("descricao", "", null);
            this.showDataSelectSemestre(this.semestreSelect, categorias, "");
        }

        return semestreSelect;
    }

    private void showDataSelectSemestre(List<SelectItem> select, List<Semestre> itens, String prefixo) {

        SelectItem item = null;
        if (itens != null) {
            for (Semestre s : itens) {
                item = new SelectItem(s, s.getDescricao());
                item.setEscape(false);

                select.add(item);

                //this.montaDadosSelect(select, usuario.getNome(), prefixo + "&nbsp;&nbsp;");
            }
        }
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

}
