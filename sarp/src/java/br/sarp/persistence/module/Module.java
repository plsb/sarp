
package br.sarp.persistence.module;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Module implements Serializable{
    
    @Id
    @GeneratedValue
    private int id;
    
    private String descricao;
    
    @Temporal(TemporalType.DATE)
    private Date data;
    
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;
    
    @Temporal(TemporalType.DATE)
    private Date dataFimInformarProfessores;
    
    private String professor;
    
    private String competencias;
    
    private String habilidades;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getDataFimInformarProfessores() {
        return dataFimInformarProfessores;
    }

    public void setDataFimInformarProfessores(Date dataFimInformarProfessores) {
        this.dataFimInformarProfessores = dataFimInformarProfessores;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getCompetencias() {
        return competencias;
    }

    public void setCompetencias(String competencias) {
        this.competencias = competencias;
    }

    public String getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(String habilidades) {
        this.habilidades = habilidades;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.id;
        hash = 31 * hash + Objects.hashCode(this.descricao);
        hash = 31 * hash + Objects.hashCode(this.data);
        hash = 31 * hash + Objects.hashCode(this.dataCadastro);
        hash = 31 * hash + Objects.hashCode(this.dataFimInformarProfessores);
        hash = 31 * hash + Objects.hashCode(this.professor);
        hash = 31 * hash + Objects.hashCode(this.competencias);
        hash = 31 * hash + Objects.hashCode(this.habilidades);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Module other = (Module) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.professor, other.professor)) {
            return false;
        }
        if (!Objects.equals(this.competencias, other.competencias)) {
            return false;
        }
        if (!Objects.equals(this.habilidades, other.habilidades)) {
            return false;
        }
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        if (!Objects.equals(this.dataCadastro, other.dataCadastro)) {
            return false;
        }
        if (!Objects.equals(this.dataFimInformarProfessores, other.dataFimInformarProfessores)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Module{" + "id=" + id + ", descricao=" + descricao + ", data=" + data + ", dataCadastro=" + dataCadastro + ", dataFimInformarProfessores=" + dataFimInformarProfessores + ", professor=" + professor + ", competencias=" + competencias + ", habilidades=" + habilidades + '}';
    }
    
}
