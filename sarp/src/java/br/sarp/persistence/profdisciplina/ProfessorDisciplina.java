
package br.sarp.persistence.profdisciplina;

import br.sarp.persistence.disciplina.Disciplina;
import br.sarp.persistence.periodo.Periodo;
import br.sarp.persistence.person.Person;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ProfessorDisciplina {
    
    @Id
    @GeneratedValue
    private Integer id;
    
    @ManyToOne
    private Periodo periodo;
    
    @ManyToOne
    private Person professor;
    
    @ManyToOne
    private Disciplina disciplina;
    
    private boolean ativo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Person getProfessor() {
        return professor;
    }

    public void setProfessor(Person professor) {
        this.professor = professor;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.periodo);
        hash = 79 * hash + Objects.hashCode(this.professor);
        hash = 79 * hash + Objects.hashCode(this.disciplina);
        hash = 79 * hash + (this.ativo ? 1 : 0);
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
        final ProfessorDisciplina other = (ProfessorDisciplina) obj;
        if (this.ativo != other.ativo) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.periodo, other.periodo)) {
            return false;
        }
        if (!Objects.equals(this.professor, other.professor)) {
            return false;
        }
        if (!Objects.equals(this.disciplina, other.disciplina)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProfessorDisciplina{" + "id=" + id + ", periodo=" + periodo + ", professor=" + professor + ", disciplina=" + disciplina + ", ativo=" + ativo + '}';
    }
    
    
    
}
