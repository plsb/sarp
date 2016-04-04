/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sarp.persistence.person;

import br.sarp.user.User;
import br.sarp.user.UserDAO;
import br.sarp.util.GenericDAO;
import br.sarp.util.HibernateUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author pelusb
 */
public class PersonDAO extends GenericDAO<Person> {

    public PersonDAO() {
        super(Person.class);
    }

    public List<Person> checkPermission(String permissao) {
        List<Person> lista = new ArrayList<>();
        List<User> listaAux = null;
        try {
            UserDAO uDAO = new UserDAO();
            listaAux = uDAO.listOrderBy("login", "active", true);
            for(User u : listaAux){
                if(u.getPermission()!=null){
                    if(u.getPermission().contains(permissao)){
                        lista.add(u.getPessoa());
                    }
                }
            }

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
