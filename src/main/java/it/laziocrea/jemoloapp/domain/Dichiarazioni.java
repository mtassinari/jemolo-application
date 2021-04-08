package it.laziocrea.jemoloapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Dichiarazioni.
 */
@Entity
@Table(name = "dichiarazioni")
public class Dichiarazioni implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "descrizione", nullable = false)
    private String descrizione;

    @OneToMany(mappedBy = "dichiarazioni")
    private Set<DichiarazioniObligatorie> dichiarazioniObligatories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public Dichiarazioni descrizione(String descrizione) {
        this.descrizione = descrizione;
        return this;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Set<DichiarazioniObligatorie> getDichiarazioniObligatories() {
        return dichiarazioniObligatories;
    }

    public Dichiarazioni dichiarazioniObligatories(Set<DichiarazioniObligatorie> dichiarazioniObligatories) {
        this.dichiarazioniObligatories = dichiarazioniObligatories;
        return this;
    }

    public Dichiarazioni addDichiarazioniObligatorie(DichiarazioniObligatorie dichiarazioniObligatorie) {
        this.dichiarazioniObligatories.add(dichiarazioniObligatorie);
        dichiarazioniObligatorie.setDichiarazioni(this);
        return this;
    }

    public Dichiarazioni removeDichiarazioniObligatorie(DichiarazioniObligatorie dichiarazioniObligatorie) {
        this.dichiarazioniObligatories.remove(dichiarazioniObligatorie);
        dichiarazioniObligatorie.setDichiarazioni(null);
        return this;
    }

    public void setDichiarazioniObligatories(Set<DichiarazioniObligatorie> dichiarazioniObligatories) {
        this.dichiarazioniObligatories = dichiarazioniObligatories;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dichiarazioni)) {
            return false;
        }
        return id != null && id.equals(((Dichiarazioni) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Dichiarazioni{" +
            "id=" + getId() +
            ", descrizione='" + getDescrizione() + "'" +
            "}";
    }
}
