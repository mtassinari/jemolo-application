package it.laziocrea.jemoloapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A AvvisiHome.
 */
@Entity
@Table(name = "avvisi_home")
public class AvvisiHome implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "titolo", nullable = false)
    private String titolo;

    @NotNull
    @Column(name = "avviso_home", nullable = false)
    private String avvisoHome;

    @NotNull
    @Column(name = "visibile", nullable = false)
    private Boolean visibile;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public AvvisiHome titolo(String titolo) {
        this.titolo = titolo;
        return this;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAvvisoHome() {
        return avvisoHome;
    }

    public AvvisiHome avvisoHome(String avvisoHome) {
        this.avvisoHome = avvisoHome;
        return this;
    }

    public void setAvvisoHome(String avvisoHome) {
        this.avvisoHome = avvisoHome;
    }

    public Boolean isVisibile() {
        return visibile;
    }

    public AvvisiHome visibile(Boolean visibile) {
        this.visibile = visibile;
        return this;
    }

    public void setVisibile(Boolean visibile) {
        this.visibile = visibile;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AvvisiHome)) {
            return false;
        }
        return id != null && id.equals(((AvvisiHome) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AvvisiHome{" +
            "id=" + getId() +
            ", titolo='" + getTitolo() + "'" +
            ", avvisoHome='" + getAvvisoHome() + "'" +
            ", visibile='" + isVisibile() + "'" +
            "}";
    }
}
