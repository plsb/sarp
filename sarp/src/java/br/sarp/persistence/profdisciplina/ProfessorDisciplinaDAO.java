package br.sarp.persistence.profdisciplina;

import br.sarp.persistence.course.Course;
import br.sarp.persistence.disciplina.DisciplinaDAO;
import br.sarp.persistence.periodo.Periodo;
import br.sarp.persistence.person.Person;
import br.sarp.user.User;
import br.sarp.user.UserDAO;
import br.sarp.util.GenericDAO;
import br.sarp.util.HibernateUtil;
import java.awt.Cursor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;


public class ProfessorDisciplinaDAO extends GenericDAO<ProfessorDisciplina>{
    
    public ProfessorDisciplinaDAO() {
        super(ProfessorDisciplina.class);
    }
    
    public List<ProfessorDisciplina> checkPerioCurso(Periodo p, Course c) {
        List<ProfessorDisciplina> lista = new ArrayList<>();
        try {
            DisciplinaDAO dDAO = new DisciplinaDAO();
            
            setSessao(HibernateUtil.getSessionFactory().openSession());
            lista = getSessao().createCriteria(ProfessorDisciplina.class)
                    .add(Restrictions.eq("periodo", p))
                    .add(Restrictions.in("disciplina", dDAO.checkExists("curso", c)))
                    .list();          
            

        } catch (Throwable e) {
            if (getTransacao().isActive()) {
                getTransacao().rollback();
            }
            JOptionPane.showMessageDialog(null, "Não foi possível listar: " + e.getMessage());
        } finally {
            getSessao().close();
        }
        return lista;

    }
    
    public List<Person> checkProfPeriodo(Periodo p, Course c) {
        List<Person> lista = new ArrayList<>();
        List<ProfessorDisciplina> listaAux = null;
        try {
            DisciplinaDAO dDAO = new DisciplinaDAO();
            
            setSessao(HibernateUtil.getSessionFactory().openSession());
            listaAux = getSessao().createCriteria(ProfessorDisciplina.class)
                    .add(Restrictions.eq("periodo", p))
                    .add(Restrictions.in("disciplina", dDAO.checkExists("curso", c)))
                    .list();          
            
            for (ProfessorDisciplina listaAux1 : listaAux) {
                lista.add(listaAux1.getProfessor());
            }
            
            lista = new ArrayList(new HashSet(lista));
            Collections.sort(lista);
        } catch (Throwable e) {
            if (getTransacao().isActive()) {
                getTransacao().rollback();
            }
            JOptionPane.showMessageDialog(null, "Não foi possível listar: " + e.getMessage());
        } finally {
            
        }
        Collections.sort(lista);
        return lista;

    }
    
}
