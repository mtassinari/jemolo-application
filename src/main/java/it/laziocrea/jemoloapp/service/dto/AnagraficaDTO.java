package it.laziocrea.jemoloapp.service.dto;
import io.swagger.annotations.ApiModel;
import it.laziocrea.jemoloapp.domain.Candidato;
import it.laziocrea.jemoloapp.domain.Competenza;
import it.laziocrea.jemoloapp.domain.CompetenzeLng;
import it.laziocrea.jemoloapp.domain.Curriculum;
import it.laziocrea.jemoloapp.domain.DichiarazioniObligatorie;
import it.laziocrea.jemoloapp.domain.TitoloStudio;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link it.laziocrea.jemoloapp.domain.AnagraficaCandidato} entity.
 */
@ApiModel(description = "Entity AnagraficaCandidato @author Marco Tassinari")
public class AnagraficaDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String cognome;
    
    @NotNull
    private String luogoNascita;

    @NotNull
    private LocalDate dataNascita;

    @NotNull
    private String professione;

    @Pattern(regexp = "^[0-9]{11}$|")
    private String partitaIva;

    private String numeroTelefonoFisso;

    private String numeroTelefonoCellulare;

    @Pattern(regexp = "^[A-z0-9\\.\\+_-]+@[A-z0-9\\._-]+\\.[A-z]{2,6}$|")
    private String indirizzoPec;

    @NotNull
    private String indirizzoResidenza;

    @NotNull
    private String capResidenza;

    @NotNull
    private String comuneResidenza;

    @NotNull
    private String provinciaResidenza;

    private String note;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getLuogoNascita() {
        return luogoNascita;
    }

    public void setLuogoNascita(String luogoNascita) {
        this.luogoNascita = luogoNascita;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getProfessione() {
        return professione;
    }

    public void setProfessione(String professione) {
        this.professione = professione;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public String getNumeroTelefonoFisso() {
        return numeroTelefonoFisso;
    }

    public void setNumeroTelefonoFisso(String numeroTelefonoFisso) {
        this.numeroTelefonoFisso = numeroTelefonoFisso;
    }

    public String getNumeroTelefonoCellulare() {
        return numeroTelefonoCellulare;
    }

    public void setNumeroTelefonoCellulare(String numeroTelefonoCellulare) {
        this.numeroTelefonoCellulare = numeroTelefonoCellulare;
    }

    public String getIndirizzoPec() {
        return indirizzoPec;
    }

    public void setIndirizzoPec(String indirizzoPec) {
        this.indirizzoPec = indirizzoPec;
    }

    public String getIndirizzoResidenza() {
        return indirizzoResidenza;
    }

    public void setIndirizzoResidenza(String indirizzoResidenza) {
        this.indirizzoResidenza = indirizzoResidenza;
    }

    public String getCapResidenza() {
        return capResidenza;
    }

    public void setCapResidenza(String capResidenza) {
        this.capResidenza = capResidenza;
    }

    public String getComuneResidenza() {
        return comuneResidenza;
    }

    public void setComuneResidenza(String comuneResidenza) {
        this.comuneResidenza = comuneResidenza;
    }

    public String getProvinciaResidenza() {
        return provinciaResidenza;
    }

    public void setProvinciaResidenza(String provinciaResidenza) {
        this.provinciaResidenza = provinciaResidenza;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AnagraficaDTO anagraficaCandidatoDTO = (AnagraficaDTO) o;
        if (anagraficaCandidatoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), anagraficaCandidatoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "AnagraficaDTO [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", luogoNascita=" + luogoNascita
				+ ", dataNascita=" + dataNascita + ", professione=" + professione + ", partitaIva=" + partitaIva
				+ ", numeroTelefonoFisso=" + numeroTelefonoFisso + ", numeroTelefonoCellulare="
				+ numeroTelefonoCellulare + ", indirizzoPec=" + indirizzoPec + ", indirizzoResidenza="
				+ indirizzoResidenza + ", capResidenza=" + capResidenza + ", comuneResidenza=" + comuneResidenza
				+ ", provinciaResidenza=" + provinciaResidenza + ", note=" + note + "]";
	}
}
