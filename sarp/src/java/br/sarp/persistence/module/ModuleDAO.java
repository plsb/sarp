/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sarp.persistence.module;

import br.sarp.util.GenericDAO;
import br.sarp.util.HibernateUtil;
import br.sarp.util.UsuarioAtivo;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author pelusb
 */
public class ModuleDAO extends GenericDAO<Module>{
    
    public ModuleDAO() {
        super(Module.class);
    }
    
    public List<Module> list(){
        List<Module> lista = null;
        try {
            this.setSessao(HibernateUtil.getSessionFactory().openSession());
            setTransacao(getSessao().beginTransaction());
            lista = this.getSessao().createCriteria(Module.class).
                    addOrder(Order.desc("data")).list();

        } catch (Throwable e) {
            if (getTransacao().isActive()) {
                getTransacao().rollback();
            }
            System.out.println("Não foi possível listar: " + e.getMessage());
        } finally {
            getSessao().close();
        }
        return lista;
        
    }
    
}
