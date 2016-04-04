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
import javax.faces.model.SelectItem;
import javax.swing.JOptionPane;

/**
 *
 * @author Pedro Saraiva
 */
@ManagedBean(name = "bcourse")
@RequestScoped
public class CourseBean {

    private Course course = new Course();
    private List<Course> list;
    private CourseDAO dao = new CourseDAO();
    private FacesContext context = FacesContext.getCurrentInstance();
    private List<SelectItem> coordSelect;
    private List<SelectItem> collegeSelect;

    public FacesContext getContext() {
        return context;
    }

    public void setContext(FacesContext context) {
        this.context = context;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String save() {
        boolean mostraMensagemCamposObrigatorios = false;
        if (course.getDescricao().equals("")) {
            mostraMensagemCamposObrigatorios = true;
        } else if (course.getCollege()==null) {
            mostraMensagemCamposObrigatorios = true;
        } else if (course.getCoordenador()==null) {
            mostraMensagemCamposObrigatorios = true;
        }

        if (mostraMensagemCamposObrigatorios) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Preencha os campos Obrigatórios (*)");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "";
            
        }

        course.setDescricao(course.getDescricao().toUpperCase());
        
        if (course.getId() == 0) {
            
            dao.add(course);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Cadastro Realizado com Sucesso!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
        } else {
            dao.update(course);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Edição Realizada com Sucesso!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        return "/coordpedag/courselist.jsf";
    }

    public String newCourse() {
        course = new Course();
        CollegeDAO collegeDAO = new CollegeDAO();
        if(collegeDAO.list().size()>0){
           course.setCollege(collegeDAO.list().get(0));
        }
        return "/coordpedag/course.jsf";
    }

    public List<Course> getList() {
        list = dao.list();
        return list;
    }

    public String edit() {
        return "/coordpedag/course.jsf";
    }

    public String remove() {
        dao.remove(this.course);
        this.list = dao.list();
        return null;
    }
    
    public List<SelectItem> getCoordSelect() {
        if (this.coordSelect == null) {
            this.coordSelect = new ArrayList<SelectItem>();
            //ContextoBean contextoBean = scs.util.ContextoUtil.getContextoBean();

            PersonDAO dao = new PersonDAO();
            List<Person> categorias = dao.listOrderBy("nome", "", null);
            this.showDataSelectPerson(this.coordSelect, categorias, "");
        }

        return coordSelect;
    }

    private void showDataSelectPerson(List<SelectItem> select, List<Person> itens, String prefixo) {

        SelectItem item = null;
        if (itens != null) {
            for (Person p : itens) {
                item = new SelectItem(p, p.getNome());
                item.setEscape(false);

                select.add(item);

                //this.montaDadosSelect(select, usuario.getNome(), prefixo + "&nbsp;&nbsp;");
            }
        }
    }
    
    public List<SelectItem> getCollegeSelect() {
        if (this.collegeSelect == null) {
            this.collegeSelect = new ArrayList<SelectItem>();
            //ContextoBean contextoBean = scs.util.ContextoUtil.getContextoBean();

            CollegeDAO dao = new CollegeDAO();
            List<College> categorias = dao.list();
            this.showDataSelectCollege(this.collegeSelect, categorias, "");
        }

        return collegeSelect;
    }

    private void showDataSelectCollege(List<SelectItem> select, List<College> itens, String prefixo) {

        SelectItem item = null;
        
        if (itens != null) {
            for (College c : itens) {
                item = new SelectItem(c, c.getDescricao());
                item.setEscape(false);

                select.add(item);

                //this.montaDadosSelect(select, usuario.getNome(), prefixo + "&nbsp;&nbsp;");
            }
        }
    }
    
    
}
