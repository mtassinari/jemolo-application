package it.laziocrea.jemoloapp.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Lingua.
 */
@Entity
@Table(name = "lingua")
public class Lingua implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "lingua", nullable = false)
    private String lingua;
    
    @NotNull
    @Column(name = "lingua_ue", nullable = false)
    private Boolean linguaUe;

    @OneToMany(mappedBy = "lingua")
    private Set<CompetenzeLng> competenzeLinguistiches = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLingua() {
        return lingua;
    }

    public Lingua lingua(String lingua) {
        this.lingua = lingua;
        return this;
    }

    public void setLingua(String lingua) {
        this.lingua = lingua;
    }

    public Boolean isLinguaUe() {
        return linguaUe;
    }

    public Lingua linguaUe(Boolean linguaUe) {
        this.linguaUe = linguaUe;
        return this;
    }

    public void setLinguaUe(Boolean linguaUe) {
        this.linguaUe = linguaUe;
    }

    public Set<CompetenzeLng> getCompetenzeLinguistiches() {
        return competenzeLinguistiches;
    }

    public Lingua competenzeLinguistiches(Set<CompetenzeLng> competenzeLngs) {
        this.competenzeLinguistiches = competenzeLngs;
        return this;
    }

    public Lingua addCompetenzeLinguistiche(CompetenzeLng competenzeLng) {
        this.competenzeLinguistiches.add(competenzeLng);
        competenzeLng.setLingua(this);
        return this;
    }

    public Lingua removeCompetenzeLinguistiche(CompetenzeLng competenzeLng) {
        this.competenzeLinguistiches.remove(competenzeLng);
        competenzeLng.setLingua(null);
        return this;
    }

    public void setCompetenzeLinguistiches(Set<CompetenzeLng> competenzeLngs) {
        this.competenzeLinguistiches = competenzeLngs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lingua)) {
            return false;
        }
        return id != null && id.equals(((Lingua) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Lingua{" +
            "id=" + getId() +
            ", lingua='" + getLingua() + "'" +
            ", linguaUe='" + isLinguaUe() + "'" +
            "}";
    }
}
