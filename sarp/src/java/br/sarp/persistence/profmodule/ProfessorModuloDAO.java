/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sarp.persistence.profmodule;

import br.sarp.persistence.course.Course;
import br.sarp.persistence.disciplina.DisciplinaDAO;
import br.sarp.persistence.module.Module;
import br.sarp.persistence.periodo.Periodo;
import br.sarp.persistence.person.Person;
import br.sarp.persistence.profdisciplina.ProfessorDisciplina;
import br.sarp.util.GenericDAO;
import br.sarp.util.HibernateUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Pedro Saraiva
 */
public class ProfessorModuloDAO extends GenericDAO<ProfessorModulo>{

    public ProfessorModuloDAO() {
        super(ProfessorModulo.class);
    }
    
    public List<ProfessorModulo> checkProfModulo(Module m, Person p) {
        List<ProfessorModulo> lista = new ArrayList<>();
        try {
                       
            setSessao(HibernateUtil.getSessionFactory().openSession());
            lista = getSessao().createCriteria(ProfessorModulo.class)
                    .add(Restrictions.eq("module", m))
                    .add(Restrictions.eq("professor", p))
                    .list();          
            

        } catch (Throwable e) {
            if (getTransacao().isActive()) {
                getTransacao().rollback();
            }
            JOptionPane.showMessageDialog(null, "Não foi possível listar: " + e.getMessage());
        } finally {
            
        }
        
        return lista;

    }
    
    public List<ProfessorModulo> checkModuleCourse(Module m, Course c) {
        List<ProfessorModulo> lista = new ArrayList<>();
        try {
                       
            setSessao(HibernateUtil.getSessionFactory().openSession());
            lista = getSessao().createCriteria(ProfessorModulo.class)
                    .add(Restrictions.eq("module", m))
                    .add(Restrictions.eq("course", c))
                    .list();          
            

        } catch (Throwable e) {
            if (getTransacao().isActive()) {
                getTransacao().rollback();
            }
            JOptionPane.showMessageDialog(null, "Não foi possível listar: " + e.getMessage());
        } finally {
            
        }
        
        return lista;

    }
    
}
