package it.laziocrea.jemoloapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity AreaCompetenza\n@author Marco Tassinari
 */
@Entity
@Table(name = "ambito_competenza")
public class AmbitoCompetenza implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "descrizione", nullable = false)
    private String descrizione;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "stato")
    private Boolean stato;

    @OneToMany(mappedBy = "ambitoComp")
    // @OrderBy("descrizione ASC")
    private Set<Competenza> competenzas = new HashSet<>();

    @OneToMany(mappedBy = "ambito")
    // @OrderBy("descrizione ASC")
    private Set<AmbitoCompetenza> sottoambitos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("sottoambitos")
    private AmbitoCompetenza ambito;

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

    public AmbitoCompetenza descrizione(String descrizione) {
        this.descrizione = descrizione;
        return this;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getTipo() {
        return tipo;
    }

    public AmbitoCompetenza tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean isStato() {
        return stato;
    }

    public AmbitoCompetenza stato(Boolean stato) {
        this.stato = stato;
        return this;
    }

    public void setStato(Boolean stato) {
        this.stato = stato;
    }

    public Set<Competenza> getCompetenzas() {
        return competenzas;
    }

    public AmbitoCompetenza competenzas(Set<Competenza> competenzas) {
        this.competenzas = competenzas;
        return this;
    }

    public AmbitoCompetenza addCompetenza(Competenza competenza) {
        this.competenzas.add(competenza);
        competenza.setAmbitoComp(this);
        return this;
    }

    public AmbitoCompetenza removeCompetenza(Competenza competenza) {
        this.competenzas.remove(competenza);
        competenza.setAmbitoComp(null);
        return this;
    }

    public void setCompetenzas(Set<Competenza> competenzas) {
        this.competenzas = competenzas;
    }

    public Set<AmbitoCompetenza> getSottoambitos() {
        return sottoambitos;
    }

    public AmbitoCompetenza sottoambitos(Set<AmbitoCompetenza> ambitoCompetenzas) {
        this.sottoambitos = ambitoCompetenzas;
        return this;
    }

    public AmbitoCompetenza addSottoambito(AmbitoCompetenza ambitoCompetenza) {
        this.sottoambitos.add(ambitoCompetenza);
        ambitoCompetenza.setAmbito(this);
        return this;
    }

    public AmbitoCompetenza removeSottoambito(AmbitoCompetenza ambitoCompetenza) {
        this.sottoambitos.remove(ambitoCompetenza);
        ambitoCompetenza.setAmbito(null);
        return this;
    }

    public void setSottoambitos(Set<AmbitoCompetenza> ambitoCompetenzas) {
        this.sottoambitos = ambitoCompetenzas;
    }

    public AmbitoCompetenza getAmbito() {
        return ambito;
    }

    public AmbitoCompetenza ambito(AmbitoCompetenza ambitoCompetenza) {
        this.ambito = ambitoCompetenza;
        return this;
    }

    public void setAmbito(AmbitoCompetenza ambitoCompetenza) {
        this.ambito = ambitoCompetenza;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AmbitoCompetenza)) {
            return false;
        }
        return id != null && id.equals(((AmbitoCompetenza) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AmbitoCompetenza{" +
            "id=" + getId() +
            ", descrizione='" + getDescrizione() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", stato='" + isStato() + "'" +
            "}";
    }
}
