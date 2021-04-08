package it.laziocrea.jemoloapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Comune.
 */
@Entity
@Table(name = "comune")
public class Comune implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "nome_provincia", nullable = false)
    private String nomeProvincia;

    @NotNull
    @Column(name = "sigla_provincia", nullable = false)
    private String siglaProvincia;

    /*@OneToMany(mappedBy = "provincia")
    private Set<Comune> comunes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "comunes", allowSetters = true)
    private Comune provincia;*/

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Comune nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeProvincia() {
        return nomeProvincia;
    }

    public Comune nomeProvincia(String nomeProvincia) {
        this.nomeProvincia = nomeProvincia;
        return this;
    }

    public void setNomeProvincia(String nomeProvincia) {
        this.nomeProvincia = nomeProvincia;
    }

    public String getSiglaProvincia() {
        return siglaProvincia;
    }

    public Comune siglaProvincia(String siglaProvincia) {
        this.siglaProvincia = siglaProvincia;
        return this;
    }

    public void setSiglaProvincia(String siglaProvincia) {
        this.siglaProvincia = siglaProvincia;
    }

    /*public Set<Comune> getComunes() {
        return comunes;
    }

    public Comune comunes(Set<Comune> comunes) {
        this.comunes = comunes;
        return this;
    }

    public Comune addComune(Comune comune) {
        this.comunes.add(comune);
        comune.setProvincia(this);
        return this;
    }

    public Comune removeComune(Comune comune) {
        this.comunes.remove(comune);
        comune.setProvincia(null);
        return this;
    }

    public void setComunes(Set<Comune> comunes) {
        this.comunes = comunes;
    }

    public Comune getProvincia() {
        return provincia;
    }

    public Comune provincia(Comune comune) {
        this.provincia = comune;
        return this;
    }

    public void setProvincia(Comune comune) {
        this.provincia = comune;
    }*/
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comune)) {
            return false;
        }
        return id != null && id.equals(((Comune) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Comune{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", nomeProvincia='" + getNomeProvincia() + "'" +
            ", siglaProvincia='" + getSiglaProvincia() + "'" +
            "}";
    }
}
