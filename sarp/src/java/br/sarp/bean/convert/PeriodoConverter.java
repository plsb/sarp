/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sarp.bean.convert;

import br.sarp.persistence.college.College;
import br.sarp.persistence.college.CollegeDAO;
import br.sarp.persistence.periodo.Periodo;
import br.sarp.persistence.periodo.PeriodoDAO;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Pedro Saraiva
 */
@FacesConverter(forClass = Periodo.class)
public class PeriodoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext contex, UIComponent componente, String value) {
        if (value != null && value.trim().length() > 0) {
            Integer codigo = Integer.valueOf(value);
            try {
                PeriodoDAO pDAO = new PeriodoDAO();
                return pDAO.checkExists("id", codigo).get(0);
            } catch (Exception e) {
                throw new ConverterException("Não foi possível encontrar o periodo de código " + value + "." + e.getMessage());
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
        if (value != null) {
            Periodo c = (Periodo) value;
            return c.getId().toString();
        }
        return "";
    }

}
