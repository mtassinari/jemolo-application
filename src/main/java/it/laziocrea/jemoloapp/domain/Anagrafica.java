package it.laziocrea.jemoloapp.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Entity AnagraficaCandidato
 * @author Marco Tassinari
 */
@Entity
@Table(name = "anagrafica_candidato")
public class Anagrafica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    @Column(name = "cognome", nullable = false)
    private String cognome;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "luogo_nascita", nullable = false)
    private String luogoNascita;

    @NotNull
    @Column(name = "data_nascita", nullable = false)
    private LocalDate dataNascita;

    @NotNull
    @Column(name = "professione", nullable = false)
    private String professione;

    @Pattern(regexp = "^[0-9]{11}$|")
    @Column(name = "partita_iva", unique = true)
    private String partitaIva;

    @Column(name = "numero_telefono_fisso")
    private String numeroTelefonoFisso;

    @Column(name = "numero_telefono_cellulare")
    private String numeroTelefonoCellulare;

    @Pattern(regexp = "^[A-z0-9\\.\\+_-]+@[A-z0-9\\._-]+\\.[A-z]{2,6}$|")
    @Column(name = "indirizzo_pec", nullable = false, unique = true)
    private String indirizzoPec;

    @NotNull
    @Column(name = "indirizzo_residenza", nullable = false)
    private String indirizzoResidenza;

    @NotNull
    @Column(name = "cap_residenza", nullable = false)
    private String capResidenza;

    @NotNull
    @Column(name = "comune_residenza", nullable = false)
    private String comuneResidenza;

    @NotNull
    @Column(name = "provincia_residenza", nullable = false)
    private String provinciaResidenza;

    @Column(name = "note")
    private String note;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCognome() {
        return cognome;
    }

    public Anagrafica cognome(String cognome) {
        this.cognome = cognome;
        return this;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public Anagrafica nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLuogoNascita() {
        return luogoNascita;
    }

    public Anagrafica luogoNascita(String luogoNascita) {
        this.luogoNascita = luogoNascita;
        return this;
    }

    public void setLuogoNascita(String luogoNascita) {
        this.luogoNascita = luogoNascita;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public Anagrafica dataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
        return this;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getProfessione() {
        return professione;
    }

    public Anagrafica professione(String professione) {
        this.professione = professione;
        return this;
    }

    public void setProfessione(String professione) {
        this.professione = professione;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public Anagrafica partitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
        return this;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public String getNumeroTelefonoFisso() {
        return numeroTelefonoFisso;
    }

    public Anagrafica numeroTelefonoFisso(String numeroTelefonoFisso) {
        this.numeroTelefonoFisso = numeroTelefonoFisso;
        return this;
    }

    public void setNumeroTelefonoFisso(String numeroTelefonoFisso) {
        this.numeroTelefonoFisso = numeroTelefonoFisso;
    }

    public String getNumeroTelefonoCellulare() {
        return numeroTelefonoCellulare;
    }

    public Anagrafica numeroTelefonoCellulare(String numeroTelefonoCellulare) {
        this.numeroTelefonoCellulare = numeroTelefonoCellulare;
        return this;
    }

    public void setNumeroTelefonoCellulare(String numeroTelefonoCellulare) {
        this.numeroTelefonoCellulare = numeroTelefonoCellulare;
    }

    public String getIndirizzoPec() {
        return indirizzoPec;
    }

    public Anagrafica indirizzoPec(String indirizzoPec) {
        this.indirizzoPec = indirizzoPec;
        return this;
    }

    public void setIndirizzoPec(String indirizzoPec) {
        this.indirizzoPec = indirizzoPec;
    }

    public String getIndirizzoResidenza() {
        return indirizzoResidenza;
    }

    public Anagrafica indirizzoResidenza(String indirizzoResidenza) {
        this.indirizzoResidenza = indirizzoResidenza;
        return this;
    }

    public void setIndirizzoResidenza(String indirizzoResidenza) {
        this.indirizzoResidenza = indirizzoResidenza;
    }

    public String getCapResidenza() {
        return capResidenza;
    }

    public Anagrafica capResidenza(String capResidenza) {
        this.capResidenza = capResidenza;
        return this;
    }

    public void setCapResidenza(String capResidenza) {
        this.capResidenza = capResidenza;
    }

    public String getComuneResidenza() {
        return comuneResidenza;
    }

    public Anagrafica comuneResidenza(String comuneResidenza) {
        this.comuneResidenza = comuneResidenza;
        return this;
    }

    public void setComuneResidenza(String comuneResidenza) {
        this.comuneResidenza = comuneResidenza;
    }

    public String getProvinciaResidenza() {
        return provinciaResidenza;
    }

    public Anagrafica provinciaResidenza(String provinciaResidenza) {
        this.provinciaResidenza = provinciaResidenza;
        return this;
    }

    public void setProvinciaResidenza(String provinciaResidenza) {
        this.provinciaResidenza = provinciaResidenza;
    }

    public String getNote() {
        return note;
    }

    public Anagrafica note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Anagrafica)) {
            return false;
        }
        return id != null && id.equals(((Anagrafica) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

	@Override
	public String toString() {
		return "Anagrafica [id=" + id + ", cognome=" + cognome + ", nome=" + nome + ", luogoNascita=" + luogoNascita
				+ ", dataNascita=" + dataNascita + ", professione=" + professione + ", partitaIva=" + partitaIva
				+ ", numeroTelefonoFisso=" + numeroTelefonoFisso + ", numeroTelefonoCellulare="
				+ numeroTelefonoCellulare + ", indirizzoPec=" + indirizzoPec + ", indirizzoResidenza="
				+ indirizzoResidenza + ", capResidenza=" + capResidenza + ", comuneResidenza=" + comuneResidenza
				+ ", provinciaResidenza=" + provinciaResidenza + ", note=" + note + "]";
	}

}
