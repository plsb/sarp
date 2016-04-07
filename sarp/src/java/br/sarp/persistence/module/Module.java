
package br.sarp.persistence.module;

import br.sarp.persistence.periodo.Periodo;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Module implements Serializable{
    
    @Id
    @GeneratedValue
    private Integer id;
    
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
    
    @ManyToOne
    private Periodo periodo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
    public String toString() {
        return "Module{" + "id=" + id + ", descricao=" + descricao + ", data=" + data + ", dataCadastro=" + dataCadastro + ", dataFimInformarProfessores=" + dataFimInformarProfessores + ", professor=" + professor + ", competencias=" + competencias + ", habilidades=" + habilidades + '}';
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        hash = 83 * hash + Objects.hashCode(this.descricao);
        hash = 83 * hash + Objects.hashCode(this.data);
        hash = 83 * hash + Objects.hashCode(this.dataCadastro);
        hash = 83 * hash + Objects.hashCode(this.dataFimInformarProfessores);
        hash = 83 * hash + Objects.hashCode(this.professor);
        hash = 83 * hash + Objects.hashCode(this.competencias);
        hash = 83 * hash + Objects.hashCode(this.habilidades);
        hash = 83 * hash + Objects.hashCode(this.periodo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Module other = (Module) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
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
        if (!Objects.equals(this.professor, other.professor)) {
            return false;
        }
        if (!Objects.equals(this.competencias, other.competencias)) {
            return false;
        }
        if (!Objects.equals(this.habilidades, other.habilidades)) {
            return false;
        }
        if (!Objects.equals(this.periodo, other.periodo)) {
            return false;
        }
        return true;
    }
    
    
    
}
