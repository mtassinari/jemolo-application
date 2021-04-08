package it.laziocrea.jemoloapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A DichiarazioniObligatorie.
 */
@Entity
@Table(name = "dichiarazioni_obligatorie")
public class DichiarazioniObligatorie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "stato", nullable = false)
    private Boolean stato;

    @NotNull
    @Column(name = "dichiarazione", nullable = false)
    private String dichiarazione;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties({"dichiarazionis","titoloStudios","competenzas","competenzeLngs","curricula"})
    @JoinColumn(name = "anagrafica_id", nullable = false)
    @JsonIgnore
    private AnagraficaCandidato anagrafica;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("dichiarazioniObligatories")
    private Dichiarazioni dichiarazioni;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isStato() {
        return stato;
    }

    public DichiarazioniObligatorie stato(Boolean stato) {
        this.stato = stato;
        return this;
    }

    public void setStato(Boolean stato) {
        this.stato = stato;
    }

    public String getDichiarazione() {
        return dichiarazione;
    }

    public DichiarazioniObligatorie dichiarazione(String dichiarazione) {
        this.dichiarazione = dichiarazione;
        return this;
    }

    public void setDichiarazione(String dichiarazione) {
        this.dichiarazione = dichiarazione;
    }

    public AnagraficaCandidato getAnagrafica() {
        return anagrafica;
    }

    public DichiarazioniObligatorie anagrafica(AnagraficaCandidato anagraficaCandidato) {
        this.anagrafica = anagraficaCandidato;
        return this;
    }

    public void setAnagrafica(AnagraficaCandidato anagraficaCandidato) {
        this.anagrafica = anagraficaCandidato;
    }

    public Dichiarazioni getDichiarazioni() {
        return dichiarazioni;
    }

    public DichiarazioniObligatorie dichiarazioni(Dichiarazioni dichiarazioni) {
        this.dichiarazioni = dichiarazioni;
        return this;
    }

    public void setDichiarazioni(Dichiarazioni dichiarazioni) {
        this.dichiarazioni = dichiarazioni;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DichiarazioniObligatorie)) {
            return false;
        }
        return id != null && id.equals(((DichiarazioniObligatorie) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DichiarazioniObligatorie{" +
            "id=" + getId() +
            ", stato='" + isStato() + "'" +
            ", dichiarazione='" + getDichiarazione() + "'" +
            "}";
    }
}
