/**
 * 
 */
package it.laziocrea.jemoloapp.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import it.laziocrea.jemoloapp.domain.Candidato;
import it.laziocrea.jemoloapp.domain.StatoRegistrazione;

/**
 * Entity StatoRegistrazione
 * @author mtassinari
 *
 */
@Entity
@Table(name = "stato_registrazione")
public class StatoRegistrazione {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
	private Long id;
	
    @NotNull
    @Column(name = "descrizione", nullable = false)
    private String descrizione;
    
    @OneToMany(mappedBy = "stato")
    private Set<Candidato> candidatoes = new HashSet<>();

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the descrizione
	 */
	public String getDescrizione() {
		return descrizione;
	}

	/**
	 * @param descrizione the descrizione to set
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
    public Set<Candidato> getCandidatoes() {
        return candidatoes;
    }

    public StatoRegistrazione candidatoes(Set<Candidato> candidatoes) {
        this.candidatoes = candidatoes;
        return this;
    }

    public StatoRegistrazione addCandidato(Candidato candidato) {
        this.candidatoes.add(candidato);
        candidato.setStato(this);
        return this;
    }

    public StatoRegistrazione removeCandidato(Candidato candidato) {
        this.candidatoes.remove(candidato);
        candidato.setStato(null);
        return this;
    }

    public void setCandidatoes(Set<Candidato> candidatoes) {
        this.candidatoes = candidatoes;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatoRegistrazione other = (StatoRegistrazione) obj;
		if (descrizione == null) {
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StatoRegistrazione [id=" + id + ", descrizione=" + descrizione + ", getId()=" + getId()
				+ ", getDescrizione()=" + getDescrizione() + "]";
	}
	
}
