package it.laziocrea.jemoloapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * Entity Competenza
 * @author Marco Tassinari
 */
@Entity
@Table(name = "competenza")
public class Competenza implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "competenza_id_seq")
    @SequenceGenerator(name = "competenza_id_seq",initialValue = 1, allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "descrizione", nullable = false)
    private String descrizione;

    @NotNull
    @Column(name = "anni_esperianza", nullable = false)
    private Integer anniEsperianza;

    @Column(name = "note")
    private String note;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties({"dichiarazionis","titoloStudios","competenzas","competenzeLngs","curricula"})
    @JoinColumn(name = "anagrafica_id", nullable = false)
    @JsonIgnore
    private AnagraficaCandidato anagrafica;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties({"competenzas","sottoambitos"})
    @JoinColumn(name = "ambito_comp_id", nullable = false)
    private AmbitoCompetenza ambitoComp;

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

    public Competenza descrizione(String descrizione) {
        this.descrizione = descrizione;
        return this;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Integer getAnniEsperianza() {
        return anniEsperianza;
    }

    public Competenza anniEsperianza(Integer anniEsperianza) {
        this.anniEsperianza = anniEsperianza;
        return this;
    }

    public void setAnniEsperianza(Integer anniEsperianza) {
        this.anniEsperianza = anniEsperianza;
    }

    public String getNote() {
        return note;
    }

    public Competenza note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public AnagraficaCandidato getAnagrafica() {
        return anagrafica;
    }

    public Competenza anagrafica(AnagraficaCandidato anagraficaCandidato) {
        this.anagrafica = anagraficaCandidato;
        return this;
    }

    public void setAnagrafica(AnagraficaCandidato anagraficaCandidato) {
        this.anagrafica = anagraficaCandidato;
    }

    public AmbitoCompetenza getAmbitoComp() {
        return ambitoComp;
    }

    public Competenza ambitoComp(AmbitoCompetenza ambitoCompetenza) {
        this.ambitoComp = ambitoCompetenza;
        return this;
    }

    public void setAmbitoComp(AmbitoCompetenza ambitoCompetenza) {
        this.ambitoComp = ambitoCompetenza;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Competenza)) {
            return false;
        }
        return id != null && id.equals(((Competenza) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Competenza{" +
            "id=" + getId() +
            ", descrizione='" + getDescrizione() + "'" +
            ", anniEsperianza=" + getAnniEsperianza() +
            ", note='" + getNote() + "'" +
            "}";
    }
}
