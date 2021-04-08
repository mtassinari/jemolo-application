package it.laziocrea.jemoloapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * Entity TitoloStudio\n@author Marco Tassinari
 */
@Entity
@Table(name = "titolo_studio")
public class TitoloStudio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "tipologia", nullable = false)
    private String tipologia;

    @NotNull
    @Column(name = "descrizione", nullable = false)
    private String descrizione;

    @NotNull
    @Column(name = "conseguimento", nullable = false)
    private String conseguimento;

    @NotNull
    @Column(name = "s_anno", nullable = false)
    private String sanno;
    
    @NotNull
    @Column(name = "anno", nullable = false)
    private Integer anno;

    @NotNull
    @Column(name = "voto", nullable = false)
    private String voto;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties({"titoloStudios","competenzas","competenzeLngs","curricula"})
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

    public String getTipologia() {
        return tipologia;
    }

    public TitoloStudio tipologia(String tipologia) {
        this.tipologia = tipologia;
        return this;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public TitoloStudio descrizione(String descrizione) {
        this.descrizione = descrizione;
        return this;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getConseguimento() {
        return conseguimento;
    }

    public TitoloStudio conseguimento(String conseguimento) {
        this.conseguimento = conseguimento;
        return this;
    }

    public void setConseguimento(String conseguimento) {
        this.conseguimento = conseguimento;
    }

    public String getSanno() {
        return sanno;
    }

    public TitoloStudio sanno(String sanno) {
        this.sanno = sanno;
        return this;
    }

    public void setSanno(String sanno) {
        this.sanno = sanno;
    }

    public Integer getAnno() {
        return anno;
    }

    public TitoloStudio anno(Integer anno) {
        this.anno = anno;
        return this;
    }

    public void setAnno(Integer anno) {
        this.anno = anno;
    }

    public String getVoto() {
        return voto;
    }

    public TitoloStudio voto(String voto) {
        this.voto = voto;
        return this;
    }

    public void setVoto(String voto) {
        this.voto = voto;
    }

    public AnagraficaCandidato getAnagrafica() {
        return anagrafica;
    }

    public TitoloStudio anagrafica(AnagraficaCandidato anagraficaCandidato) {
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
        if (!(o instanceof TitoloStudio)) {
            return false;
        }
        return id != null && id.equals(((TitoloStudio) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TitoloStudio{" +
            "id=" + getId() +
            ", tipologia='" + getTipologia() + "'" +
            ", descrizione='" + getDescrizione() + "'" +
            ", conseguimento='" + getConseguimento() + "'" +
            ", anno='" + getAnno() + "'" +
            ", voto='" + getVoto() + "'" +
            "}";
    }
}
