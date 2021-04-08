package it.laziocrea.jemoloapp.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;
import com.fasterxml.jackson.annotation.JsonIgnore;

import it.laziocrea.jemoloapp.service.util.AttributeEmptyConverter;
import it.laziocrea.jemoloapp.service.util.AttributeEncryptor;

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
public class AnagraficaCandidato extends AbstractAuditingEntity implements Serializable {

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
    @Convert(converter = AttributeEmptyConverter.class)
    private String partitaIva;

    @Column(name = "numero_telefono_fisso")
    @Convert(converter = AttributeEncryptor.class)
    private String numeroTelefonoFisso;

    @Column(name = "numero_telefono_cellulare")
    @Convert(converter = AttributeEncryptor.class)
    private String numeroTelefonoCellulare;

    @Pattern(regexp = "^[A-z0-9\\.\\+_-]+@[A-z0-9\\._-]+\\.[A-z]{2,6}$|")
    @Column(name = "indirizzo_pec", unique = true)
    @Convert(converter = AttributeEncryptor.class)
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
    
    @JsonIgnore
    @OneToOne(optional = false)    
    @NotNull
    @MapsId
    @JoinColumn(name = "id")
    private Candidato candidato;
    
    @NotNull
    @Column(name = "linguaita", nullable = false)
    private String linguaita;
    
    @NotNull
    @Column(name = "linguaitacheck", nullable = false)
    private boolean linguaitacheck;

    @NotNull
    @Column(name = "specializzazione_master_check", nullable = false)
    private boolean specializzazioneMaster;
    
    @NotNull
    @Column(name = "iscrizione_albo_check", nullable = false)
    private boolean iscrizioneAlbo;
    
    @NotNull
    @Column(name = "operatore_campo_check", nullable = false)
    private boolean operatoreCampo;
    
    @NotNull
    @Column(name = "esperienza_biennale_check", nullable = false)
    private boolean esperienzaBiennale;
   
    @OneToMany(mappedBy = "anagrafica",cascade = CascadeType.ALL,orphanRemoval=true)
    private Set<CompetenzeLng> competenzeLngs = new HashSet<>();

    @OneToMany(mappedBy = "anagrafica",cascade = CascadeType.ALL)
    private Set<TitoloStudio> titoloStudios = new HashSet<>();

    @OneToMany(mappedBy = "anagrafica",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Curriculum> curricula = new HashSet<>();
    
    @OneToMany(mappedBy = "anagrafica",cascade = CascadeType.ALL,orphanRemoval=true)
    private Set<Competenza> competenzas = new HashSet<>();
    
    @OneToMany(mappedBy = "anagrafica",cascade = CascadeType.ALL,orphanRemoval=true)
    @OrderBy("dichiarazioni.id ASC")
    private Set<DichiarazioniObligatorie> dichiarazionis = new LinkedHashSet<>();
    
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

    public AnagraficaCandidato cognome(String cognome) {
        this.cognome = cognome;
        return this;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public AnagraficaCandidato nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLuogoNascita() {
        return luogoNascita;
    }

    public AnagraficaCandidato luogoNascita(String luogoNascita) {
        this.luogoNascita = luogoNascita;
        return this;
    }

    public void setLuogoNascita(String luogoNascita) {
        this.luogoNascita = luogoNascita;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public AnagraficaCandidato dataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
        return this;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getProfessione() {
        return professione;
    }

    public AnagraficaCandidato professione(String professione) {
        this.professione = professione;
        return this;
    }

    public void setProfessione(String professione) {
        this.professione = professione;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public AnagraficaCandidato partitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
        return this;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public String getNumeroTelefonoFisso() {
        return numeroTelefonoFisso;
    }

    public AnagraficaCandidato numeroTelefonoFisso(String numeroTelefonoFisso) {
        this.numeroTelefonoFisso = numeroTelefonoFisso;
        return this;
    }

    public void setNumeroTelefonoFisso(String numeroTelefonoFisso) {
        this.numeroTelefonoFisso = numeroTelefonoFisso;
    }

    public String getNumeroTelefonoCellulare() {
        return numeroTelefonoCellulare;
    }

    public AnagraficaCandidato numeroTelefonoCellulare(String numeroTelefonoCellulare) {
        this.numeroTelefonoCellulare = numeroTelefonoCellulare;
        return this;
    }

    public void setNumeroTelefonoCellulare(String numeroTelefonoCellulare) {
        this.numeroTelefonoCellulare = numeroTelefonoCellulare;
    }

    public String getIndirizzoPec() {
        return indirizzoPec;
    }

    public AnagraficaCandidato indirizzoPec(String indirizzoPec) {
        this.indirizzoPec = indirizzoPec;
        return this;
    }

    public void setIndirizzoPec(String indirizzoPec) {
        this.indirizzoPec = indirizzoPec;
    }

    public String getIndirizzoResidenza() {
        return indirizzoResidenza;
    }

    public AnagraficaCandidato indirizzoResidenza(String indirizzoResidenza) {
        this.indirizzoResidenza = indirizzoResidenza;
        return this;
    }

    public void setIndirizzoResidenza(String indirizzoResidenza) {
        this.indirizzoResidenza = indirizzoResidenza;
    }

    public String getCapResidenza() {
        return capResidenza;
    }

    public AnagraficaCandidato capResidenza(String capResidenza) {
        this.capResidenza = capResidenza;
        return this;
    }

    public void setCapResidenza(String capResidenza) {
        this.capResidenza = capResidenza;
    }

    public String getComuneResidenza() {
        return comuneResidenza;
    }

    public AnagraficaCandidato comuneResidenza(String comuneResidenza) {
        this.comuneResidenza = comuneResidenza;
        return this;
    }

    public void setComuneResidenza(String comuneResidenza) {
        this.comuneResidenza = comuneResidenza;
    }

    public String getProvinciaResidenza() {
        return provinciaResidenza;
    }

    public AnagraficaCandidato provinciaResidenza(String provinciaResidenza) {
        this.provinciaResidenza = provinciaResidenza;
        return this;
    }

    public void setProvinciaResidenza(String provinciaResidenza) {
        this.provinciaResidenza = provinciaResidenza;
    }

    public String getNote() {
        return note;
    }

    public AnagraficaCandidato note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public AnagraficaCandidato candidato(Candidato candidato) {
        this.candidato = candidato;
        return this;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }
    public Set<CompetenzeLng> getCompetenzeLngs() {
        return competenzeLngs;
    }

    public AnagraficaCandidato competenzeLngs(Set<CompetenzeLng> competenzeLngs) {
        this.competenzeLngs = competenzeLngs;
        return this;
    }

    public AnagraficaCandidato addCompetenzeLng(CompetenzeLng competenzeLng) {
        this.competenzeLngs.add(competenzeLng);
        competenzeLng.setAnagrafica(this);
        return this;
    }

    public AnagraficaCandidato removeCompetenzeLng(CompetenzeLng competenzeLng) {
        this.competenzeLngs.remove(competenzeLng);
        competenzeLng.setAnagrafica(null);
        return this;
    }

    public void setCompetenzeLngs(Set<CompetenzeLng> competenzeLngs) {
        this.competenzeLngs = competenzeLngs;
    }

    public Set<TitoloStudio> getTitoloStudios() {
        return titoloStudios;
    }

    public AnagraficaCandidato titoloStudios(Set<TitoloStudio> titoloStudios) {
        this.titoloStudios = titoloStudios;
        return this;
    }

    public AnagraficaCandidato addTitoloStudio(TitoloStudio titoloStudio) {
        this.titoloStudios.add(titoloStudio);
        titoloStudio.setAnagrafica(this);
        return this;
    }

    public AnagraficaCandidato removeTitoloStudio(TitoloStudio titoloStudio) {
        this.titoloStudios.remove(titoloStudio);
        titoloStudio.setAnagrafica(null);
        return this;
    }

    public void setTitoloStudios(Set<TitoloStudio> titoloStudios) {
        this.titoloStudios = titoloStudios;
    }

    public Set<Curriculum> getCurricula() {
        return curricula;
    }

    public AnagraficaCandidato curricula(Set<Curriculum> curricula) {
        this.curricula = curricula;
        return this;
    }

    public AnagraficaCandidato addCurriculum(Curriculum curriculum) {
        this.curricula.add(curriculum);
        curriculum.setAnagrafica(this);
        return this;
    }

    public AnagraficaCandidato removeCurriculum(Curriculum curriculum) {
        this.curricula.remove(curriculum);
        curriculum.setAnagrafica(null);
        return this;
    }

    public void setCurricula(Set<Curriculum> curricula) {
        this.curricula = curricula;
    }

    public Set<Competenza> getCompetenzas() {
        return competenzas;
    }

    public AnagraficaCandidato competenzas(Set<Competenza> competenzas) {
        this.competenzas = competenzas;
        return this;
    }

    public AnagraficaCandidato addCompetenza(Competenza competenza) {
        this.competenzas.add(competenza);
        competenza.setAnagrafica(this);
        return this;
    }

    public AnagraficaCandidato removeCompetenza(Competenza competenza) {
        this.competenzas.remove(competenza);
        competenza.setAnagrafica(null);
        return this;
    }

    public void setCompetenzas(Set<Competenza> competenzas) {
        this.competenzas = competenzas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    /**
	 * @return the linguaita
	 */
	public String getLinguaita() {
		return linguaita;
	}

	/**
	 * @param linguaita the linguaita to set
	 */
	public void setLinguaita(String linguaita) {
		this.linguaita = linguaita;
	}

	public boolean isLinguaitacheck() {
		return linguaitacheck;
	}

	public void setLinguaitacheck(boolean linguaitacheck) {
		this.linguaitacheck = linguaitacheck;
	}

	public boolean isSpecializzazioneMaster() {
		return specializzazioneMaster;
	}

	public void setSpecializzazioneMaster(boolean specializzazioneMaster) {
		this.specializzazioneMaster = specializzazioneMaster;
	}

	public boolean isIscrizioneAlbo() {
		return iscrizioneAlbo;
	}

	public void setIscrizioneAlbo(boolean iscrizioneAlbo) {
		this.iscrizioneAlbo = iscrizioneAlbo;
	}

	public boolean isOperatoreCampo() {
		return operatoreCampo;
	}

	public void setOperatoreCampo(boolean operatoreCampo) {
		this.operatoreCampo = operatoreCampo;
	}

	public boolean isEsperienzaBiennale() {
		return esperienzaBiennale;
	}

	public void setEsperienzaBiennale(boolean esperienzaBiennale) {
		this.esperienzaBiennale = esperienzaBiennale;
	}

    public Set<DichiarazioniObligatorie> getDichiarazionis() {
        return dichiarazionis;
    }

    public AnagraficaCandidato dichiarazionis(Set<DichiarazioniObligatorie> dichiarazioniObligatories) {
        this.dichiarazionis = dichiarazioniObligatories;
        return this;
    }

    public AnagraficaCandidato addDichiarazioni(DichiarazioniObligatorie dichiarazioniObligatorie) {
        this.dichiarazionis.add(dichiarazioniObligatorie);
        dichiarazioniObligatorie.setAnagrafica(this);
        return this;
    }

    public AnagraficaCandidato removeDichiarazioni(DichiarazioniObligatorie dichiarazioniObligatorie) {
        this.dichiarazionis.remove(dichiarazioniObligatorie);
        dichiarazioniObligatorie.setAnagrafica(null);
        return this;
    }

    public void setDichiarazionis(Set<DichiarazioniObligatorie> dichiarazioniObligatories) {
        this.dichiarazionis = dichiarazioniObligatories;
    }

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnagraficaCandidato)) {
            return false;
        }
        return id != null && id.equals(((AnagraficaCandidato) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

	@Override
	public String toString() {
		return "AnagraficaCandidato [id=" + id + ", cognome=" + cognome + ", nome=" + nome + ", luogoNascita="
				+ luogoNascita + ", dataNascita=" + dataNascita + ", professione=" + professione + ", partitaIva="
				+ partitaIva + ", numeroTelefonoFisso=" + numeroTelefonoFisso + ", numeroTelefonoCellulare="
				+ numeroTelefonoCellulare + ", indirizzoPec=" + indirizzoPec + ", indirizzoResidenza="
				+ indirizzoResidenza + ", capResidenza=" + capResidenza + ", comuneResidenza=" + comuneResidenza
				+ ", provinciaResidenza=" + provinciaResidenza + ", note=" + note + ", candidato=" + candidato
				+ ", linguaita=" + linguaita + ", linguaitacheck=" + linguaitacheck + ", specializzazioneMaster="
				+ specializzazioneMaster + ", iscrizioneAlbo=" + iscrizioneAlbo + ", operatoreCampo=" + operatoreCampo
				+ ", esperienzaBiennale=" + esperienzaBiennale + ", competenzeLngs=" + competenzeLngs
				+ ", titoloStudios=" + titoloStudios + ", curricula=" + curricula + ", dichiarazionis=" + dichiarazionis + ", competenzas=" + competenzas
				+ "]";
	}

}
