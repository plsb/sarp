/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sarp.util;

import br.sarp.user.UserDAO;

/**
 *
 * @author Pedro Saraiva
 */
public class Teste {
    
    public static void main(String[] args) {
        HibernateUtil.getSessionFactory().openSession();
        UserDAO uDAO = new UserDAO();
        uDAO.list();
    }
    
}
