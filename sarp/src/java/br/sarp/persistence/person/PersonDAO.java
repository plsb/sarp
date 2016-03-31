/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sarp.persistence.person;

import br.sarp.util.GenericDAO;

/**
 *
 * @author pelusb
 */
public class PersonDAO extends GenericDAO<Person>{
    
    public PersonDAO() {
        super(Person.class);
    }
    
    
    
}
