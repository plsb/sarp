package br.sarp.bean;

import br.sarp.persistence.course.CourseDAO;
import br.sarp.persistence.person.Person;
import br.sarp.persistence.person.PersonDAO;
import br.sarp.user.User;
import br.sarp.user.UserDAO;
import br.sarp.util.UsuarioAtivo;
import br.sarp.util.Util;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

@ManagedBean(name = "bdefineuser")
@SessionScoped
public class DefineUserBean {

    private List<SelectItem> personSelect;
    private Person pessoaSelecionada;
    private User usuario;
    private String senha;
    private String confirmarSenha;
    private FacesContext context = FacesContext.getCurrentInstance();

    private void checaUsuario() {
        UserDAO uDAO = new UserDAO();
        if (uDAO.checkExists("pessoa", pessoaSelecionada).size() > 0) {
            usuario = uDAO.checkExists("pessoa", pessoaSelecionada).get(0);
            if(usuario.getPermission()!=null){
                ccurso = usuario.getPermission().contains("ROLE_CORD");
                professor = usuario.getPermission().contains("ROLE_PROF");
                cpedagogica = usuario.getPermission().contains("ROLE_CPEDAG");               
            }
        } else {
            usuario = new User();
            usuario.setActive(true);
            ccurso = false;
            cpedagogica = false;
            professor = false;
        }
        senha = "";
        confirmarSenha = "";
    }

    public List<SelectItem> getPersonSelect() {
        if (this.personSelect == null) {
            this.personSelect = new ArrayList<SelectItem>();
            //ContextoBean contextoBean = scs.util.ContextoUtil.getContextoBean();

            PersonDAO dao = new PersonDAO();
            List<Person> categorias = dao.listOrderBy("nome", "ativo", true);
            this.showDataSelectPerson(this.personSelect, categorias, "");
        }

        return personSelect;
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

    public Person getPessoaSelecionada() {
        return pessoaSelecionada;
    }

    public void setPessoaSelecionada(Person pessoaSelecionada) {
        this.pessoaSelecionada = pessoaSelecionada;
    }

    public String proximo() {
        if (pessoaSelecionada == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Preencha os campos Obrigatórios (*)");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "";
        }
        checaUsuario();
        return "/coordcurso/defineuser.jsf";
    }

    public String cancelar() {
        pessoaSelecionada = null;

        return "/coordcurso/selectperson.jsf";
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String save() {
        boolean camposObrigatorios = false;
        if (usuario.getLogin().equals("")) {
            camposObrigatorios = true;
        } else if (senha.equals("")) {
            camposObrigatorios = true;
        } else if (confirmarSenha.equals("")) {
            camposObrigatorios = true;
        }
        if (camposObrigatorios) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Preencha os campos Obrigatórios (*)");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "";
        }

        if (!senha.equals(confirmarSenha)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Senha confirmada incorretamente!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "";
        }
        usuario.setPassword(Util.md5(senha));
        usuario.setPessoa(pessoaSelecionada);
        List<String> permissoes = new ArrayList<>();
        if (professor) {
            permissoes.add("ROLE_PROF");
        }
        if (ccurso) {
            CourseDAO cDAO = new CourseDAO();
            if(cDAO.checkExists("coordenador", usuario.getPessoa()).size()==0){
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "A Pessoa informada não é coordenador de nenhum curso!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "";
            }
            permissoes.add("ROLE_CORD");
        }
        if (cpedagogica) {
            permissoes.add("ROLE_CPEDAG");
        }
        usuario.setPermission(permissoes);
        usuario.setName("");

        UserDAO uDAO = new UserDAO();
        if (usuario.getId() == null) {
            usuario.setActive(true);
            if (uDAO.checkExists("login", usuario.getLogin()).size() > 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Usuário já utilizado, informe outro!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return "";
            }
            uDAO.add(usuario);
        } else {
            uDAO.update(usuario);
        }
        pessoaSelecionada = null;
        return "/coordcurso/selectperson.jsf";
    }

    private boolean professor, ccurso, cpedagogica;

    public boolean isProfessor() {
        return professor;
    }

    public void setProfessor(boolean professor) {
        this.professor = professor;
    }

    public boolean isCcurso() {
        return ccurso;
    }

    public void setCcurso(boolean ccurso) {
        this.ccurso = ccurso;
    }

    public boolean isCpedagogica() {
        return cpedagogica;
    }

    public void setCpedagogica(boolean cpedagogica) {
        this.cpedagogica = cpedagogica;
    }

}
