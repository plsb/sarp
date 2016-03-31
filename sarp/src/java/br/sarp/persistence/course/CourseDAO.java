/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sarp.persistence.course;

import br.sarp.util.GenericDAO;

/**
 *
 * @author pelusb
 */
public class CourseDAO extends GenericDAO<Course>{
    
    public CourseDAO() {
        super(Course.class);
    }
    
}
