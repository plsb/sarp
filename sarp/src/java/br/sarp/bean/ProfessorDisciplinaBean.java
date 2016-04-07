/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sarp.bean;

import br.sarp.persistence.course.Course;
import br.sarp.persistence.course.CourseDAO;
import br.sarp.persistence.disciplina.Disciplina;
import br.sarp.persistence.disciplina.DisciplinaDAO;
import br.sarp.persistence.periodo.Periodo;
import br.sarp.persistence.periodo.PeriodoDAO;
import br.sarp.persistence.person.Person;
import br.sarp.persistence.person.PersonDAO;
import br.sarp.persistence.profdisciplina.ProfessorDisciplina;
import br.sarp.persistence.profdisciplina.ProfessorDisciplinaDAO;
import br.sarp.util.UsuarioAtivo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

@ManagedBean(name = "bprofdisciplina")
@ViewScoped
public class ProfessorDisciplinaBean {

    private ProfessorDisciplina profDisciplina = new ProfessorDisciplina();
    private List<SelectItem> periodoSelect;
    private List<SelectItem> pessoaSelect;
    private List<SelectItem> disciplinaSelect;
    private List<ProfessorDisciplina> lista;

    public List<ProfessorDisciplina> getLista() {
        ProfessorDisciplinaDAO pdDAO = new ProfessorDisciplinaDAO();
        if (periodo != null) {
            try {
                CourseDAO cDAO = new CourseDAO();
                Course c = cDAO.checkExists("coordenador", UsuarioAtivo.getUser().getPessoa()).get(0);
                if (c != null) {
                    lista = pdDAO.checkPerioCurso(periodo, c);
                }
            } catch (Exception e) {

            }

        }
        return lista;
    }

    public void setLista(List<ProfessorDisciplina> lista) {
        this.lista = lista;
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

    public List<SelectItem> getDisciplinaSelect() {
        if (this.disciplinaSelect == null) {
            this.disciplinaSelect = new ArrayList<SelectItem>();
            //ContextoBean contextoBean = scs.util.ContextoUtil.getContextoBean();

            DisciplinaDAO dao = new DisciplinaDAO();
            try {
                CourseDAO cDAO = new CourseDAO();
                Course c = cDAO.checkExists("coordenador", UsuarioAtivo.getUser().getPessoa()).get(0);
                List<Disciplina> categorias = dao.listOrderBy("descricao", "curso", c);
                this.showDataSelectDisc(this.disciplinaSelect, categorias, "");
            } catch (Exception e) {

            }

        }

        return disciplinaSelect;
    }

    private void showDataSelectDisc(List<SelectItem> select, List<Disciplina> itens, String prefixo) {

        SelectItem item = null;
        if (itens != null) {
            for (Disciplina p : itens) {
                item = new SelectItem(p, p.getDescricao() + " | " + p.getSemestre().getDescricao() + " | "
                        + p.getCh());
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

            PersonDAO pDAO = new PersonDAO();

            List<Person> categorias = pDAO.checkExists("eProfessor", true);
            Collections.sort(categorias);
            this.showDataSelectPessoa(this.pessoaSelect, categorias, "");
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

    public ProfessorDisciplina getProfDisciplina() {
        return profDisciplina;
    }

    public void setProfDisciplina(ProfessorDisciplina profDisciplina) {
        this.profDisciplina = profDisciplina;
    }

    private boolean visible = false;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    private Periodo periodo;

    public void mudarPeriodo() {
        if (periodo != null) {
            visible = true;
            profDisciplina = new ProfessorDisciplina();
//            professor = new Person();
//            disciplina = new Disciplina();
            profDisciplina.setAtivo(true);
        } else {
            visible = false;
        }

    }

    public String save() {
        boolean mostraMensagem = false;
        if (!edit) {
            professor = profDisciplina.getProfessor();
            disciplina = profDisciplina.getDisciplina();
            
            profDisciplina = new ProfessorDisciplina();
            profDisciplina.setDisciplina(disciplina);
            profDisciplina.setProfessor(professor);
        }
        profDisciplina.setPeriodo(periodo);

        if (profDisciplina.getDisciplina() == null) {
            mostraMensagem = true;
        } else if (profDisciplina.getPeriodo() == null) {
            mostraMensagem = true;
        } else if (profDisciplina.getProfessor() == null) {
            mostraMensagem = true;
        }

        if (mostraMensagem) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Preencha os campos Obrigatórios (*)");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "";
        }

        ProfessorDisciplinaDAO pdDAO = new ProfessorDisciplinaDAO();
        if (profDisciplina.getId() == null) {
            pdDAO.add(profDisciplina);
        } else {
            pdDAO.update(profDisciplina);
        }
        mudarPeriodo();
        edit = false;
        return "";
    }

    public String edit() {
        edit = true;
        return "";
    }

    public String remove() {
        ProfessorDisciplinaDAO pDAO = new ProfessorDisciplinaDAO();
        pDAO.remove(profDisciplina);
        mudarPeriodo();
        return "";
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    private Person professor;
    private Disciplina disciplina;
    private boolean edit;

}
