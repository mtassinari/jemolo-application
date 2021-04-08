package it.laziocrea.jemoloapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * Entity CompetenzeLinguistiche\n@author Marco Tassinari
 */
@Entity
@Table(name = "competenze_lng")
public class CompetenzeLng implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "competenze_lng_id_seq")
    @SequenceGenerator(name = "competenze_lng_id_seq",initialValue = 1, allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "livello", nullable = false)
    private Integer livello;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("competenzeLinguistiches")
    private Lingua lingua;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties({"dichiarazionis","titoloStudios","competenzas","competenzeLngs","curricula"})
    @JoinColumn(name = "anagrafica_id", nullable = false)
    @JsonIgnore
    private AnagraficaCandidato anagrafica;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLivello() {
        return livello;
    }

    public CompetenzeLng livello(Integer livello) {
        this.livello = livello;
        return this;
    }

    public void setLivello(Integer livello) {
        this.livello = livello;
    }

    public Lingua getLingua() {
        return lingua;
    }

    public CompetenzeLng lingua(Lingua lingua) {
        this.lingua = lingua;
        return this;
    }

    public void setLingua(Lingua lingua) {
        this.lingua = lingua;
    }

    public AnagraficaCandidato getAnagrafica() {
        return anagrafica;
    }

    public CompetenzeLng anagrafica(AnagraficaCandidato anagraficaCandidato) {
        this.anagrafica = anagraficaCandidato;
        return this;
    }

    public void setAnagrafica(AnagraficaCandidato anagraficaCandidato) {
        this.anagrafica = anagraficaCandidato;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompetenzeLng)) {
            return false;
        }
        return id != null && id.equals(((CompetenzeLng) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CompetenzeLng{" +
            "id=" + getId() +
            ", livello=" + getLivello() +
            "}";
    }
}
