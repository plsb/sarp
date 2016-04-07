/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sarp.bean;

import br.sarp.persistence.course.Course;
import br.sarp.persistence.course.CourseDAO;
import br.sarp.persistence.module.Module;
import br.sarp.persistence.module.ModuleDAO;
import br.sarp.persistence.periodo.Periodo;
import br.sarp.persistence.periodo.PeriodoDAO;
import br.sarp.persistence.person.Person;
import br.sarp.persistence.person.PersonDAO;
import br.sarp.persistence.profdisciplina.ProfessorDisciplinaDAO;
import br.sarp.persistence.profmodule.ProfessorModulo;
import br.sarp.persistence.profmodule.ProfessorModuloDAO;
import br.sarp.util.UsuarioAtivo;
import br.sarp.util.Util;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

@ManagedBean(name = "bindicaprofessores")
@ViewScoped
public class IndicarProfessoresBean {

    private List<SelectItem> periodoSelect;
    private List<SelectItem> pessoaSelect;
    private List<SelectItem> moduloSelect;
    private static Module modulo;

    public Module getModulo() {
        return modulo;
    }

    public void setModulo(Module modulo) {
        this.modulo = modulo;
    }

    public List<SelectItem> getPeriodoSelect() {
        if (this.periodoSelect == null) {
            this.periodoSelect = new ArrayList<SelectItem>();
            //ContextoBean contextoBean = scs.util.ContextoUtil.getContextoBean();

            PeriodoDAO dao = new PeriodoDAO();
            List<Periodo> categorias = dao.listOrderBy("descricao", "", null);
            this.showDataSelectPeriodo(this.periodoSelect, categorias, "");
        }

        return periodoSelect;
    }

    private void showDataSelectPeriodo(List<SelectItem> select, List<Periodo> itens, String prefixo) {

        SelectItem item = null;
        if (itens != null) {
            for (Periodo p : itens) {
                item = new SelectItem(p, p.getDescricao());
                item.setEscape(false);

                select.add(item);

                //this.montaDadosSelect(select, usuario.getNome(), prefixo + "&nbsp;&nbsp;");
            }
        }
    }

    public List<SelectItem> getModuloSelect() {

        if (this.moduloSelect == null) {
            this.moduloSelect = new ArrayList<SelectItem>();
            //ContextoBean contextoBean = scs.util.ContextoUtil.getContextoBean();
            if (periodo != null) {
                ModuleDAO dao = new ModuleDAO();
                List<Module> categorias = dao.listOrderBy("descricao", "periodo", periodo);
                this.showDataSelectModulo(this.moduloSelect, categorias, "");

            }
            if (moduloSelect != null) {
                if (moduloSelect.size() == 0) {
                    moduloSelect = null;
                }
            }
        }

        return moduloSelect;
    }

    private void showDataSelectModulo(List<SelectItem> select, List<Module> itens, String prefixo) {

        SelectItem item = null;
        if (itens != null) {
            for (Module p : itens) {
                item = new SelectItem(p, p.getDescricao() + ", Data: " + p.getData());
                item.setEscape(false);

                select.add(item);

                //this.montaDadosSelect(select, usuario.getNome(), prefixo + "&nbsp;&nbsp;");
            }
        }
    }

    public List<SelectItem> getPessoaSelect() {
        if (this.pessoaSelect == null) {
            this.pessoaSelect = new ArrayList<SelectItem>();
            //ContextoBean contextoBean = scs.util.ContextoUtil.getContextoBean();
            List<Person> categorias = null;

            ProfessorDisciplinaDAO pdDao = new ProfessorDisciplinaDAO();
            try {
                CourseDAO cDAO = new CourseDAO();
                Course c = cDAO.checkExists("coordenador", UsuarioAtivo.getUser().getPessoa()).get(0);
                if (c != null) {
                    categorias = pdDao.checkProfPeriodo(periodo, c);
                }
            } catch (Exception e) {

            }

            this.showDataSelectPessoa(this.pessoaSelect, categorias, "");
        }

        if (pessoaSelect != null) {
            if (pessoaSelect.size() == 0) {
                pessoaSelect = null;
            }
        }

        return pessoaSelect;
    }

    private void showDataSelectPessoa(List<SelectItem> select, List<Person> itens, String prefixo) {

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

    private Periodo periodo;

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    private boolean visibilidadeModulos = false;

    public void mudaModulo() {
        if (periodo != null) {
            visibilidadeModulos = true;
            visibilidadeProfessores = false;
            getModuloSelect();
        } else {
            visibilidadeModulos = false;
        }
    }

    public boolean isVisibilidadeModulos() {
        return visibilidadeModulos;
    }

    public void setVisibilidadeModulos(boolean visibilidadeModulos) {
        this.visibilidadeModulos = visibilidadeModulos;
    }

    private boolean visibilidadeProfessores = false;

    public void mudaProfessores() {
        if (modulo != null) {
            visibilidadeProfessores = true;
        } else {
            visibilidadeProfessores = false;
            getModuloSelect();
        }
    }

    public boolean isVisibilidadeProfessores() {
        return visibilidadeProfessores;
    }

    public void setVisibilidadeProfessores(boolean visibilidadeProfessores) {
        this.visibilidadeProfessores = visibilidadeProfessores;
    }

    private Person pessoa;

    public Person getPessoa() {
        return pessoa;
    }

    public void setPessoa(Person pessoa) {
        this.pessoa = pessoa;
    }

    public String adicionarIndicacao() {
        if (new Date().after(modulo.getDataFimInformarProfessores())) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Data Limite para indicação de Professores neste módulo está encerrada!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
        
        ProfessorModuloDAO pDAO = new ProfessorModuloDAO();
        if(pDAO.checkProfModulo(modulo, pessoa).size()>0){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Professor Já foi indicado pelo curso: "+
                    pDAO.checkProfModulo(modulo, pessoa).get(0).getCourse().getDescricao()+".");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
        ProfessorModulo pm = new ProfessorModulo();

        pm.setModule(modulo);
        pm.setProfessor(pessoa);
        pm.setCourse(Util.retornarCurso());
        pDAO.add(pm);

        pessoa = null;
        return null;
    }

    private List<ProfessorModulo> lista;

    public List<ProfessorModulo> getLista() {
        ProfessorModuloDAO pmDAO = new ProfessorModuloDAO();
        return lista = pmDAO.checkModuleCourse(modulo, Util.retornarCurso());
    }

    public void setLista(List<ProfessorModulo> lista) {
        this.lista = lista;
    }

    private ProfessorModulo professorModulo;

    public ProfessorModulo getProfessorModulo() {
        return professorModulo;
    }

    public void setProfessorModulo(ProfessorModulo professorModulo) {
        this.professorModulo = professorModulo;
    }

    public String remove() {
        if (new Date().after(modulo.getDataFimInformarProfessores())) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Data Limite para indicação de Professores neste módulo está encerrada!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
        ProfessorModuloDAO pmDAO = new ProfessorModuloDAO();
        pmDAO.remove(professorModulo);
        return null;
    }

}
