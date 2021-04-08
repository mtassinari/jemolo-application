package it.laziocrea.jemoloapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BooleanFilter;

public class AnagraficaCandidatoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter cognome;

    private StringFilter nome;

    private StringFilter luogoNascita;

    private LocalDateFilter dataNascita;

    private StringFilter codiceFiscale;

    private StringFilter professione;

    private StringFilter partitaIva;

    private StringFilter numeroTelefonoFisso;

    private StringFilter numeroTelefonoCellulare;

    private StringFilter eMail;

    private StringFilter indirizzoPec;

    private StringFilter indirizzoResidenza;

    private StringFilter capResidenza;

    private StringFilter comuneResidenza;

    private StringFilter provinciaResidenza;

    private StringFilter note;

    private LongFilter candidatoId;

    private LongFilter competenzeLngId;
    
    private LongFilter linguaId;
    
    private IntegerFilter linguaLivello;
    
    private LongFilter titoloStudioId;
    
    private StringFilter tipotitolodistudioId;
    
    private IntegerFilter titolostudioDal;
    
    private IntegerFilter titolostudioAl;
    
    private LongFilter areaCompetenzaId;
    
    private IntegerFilter areaCompetenzaDa;
    
    private IntegerFilter areaCompetenzaA;

    private LongFilter curriculumId;

    private LongFilter competenzaId;

    private LongFilter dichiarazioniId;
    
    private BooleanFilter activated;

    public AnagraficaCandidatoCriteria() {
    }

    public AnagraficaCandidatoCriteria(AnagraficaCandidatoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.cognome = other.cognome == null ? null : other.cognome.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.luogoNascita = other.luogoNascita == null ? null : other.luogoNascita.copy();
        this.dataNascita = other.dataNascita == null ? null : other.dataNascita.copy();
        this.codiceFiscale = other.codiceFiscale == null ? null : other.codiceFiscale.copy();
        this.professione = other.professione == null ? null : other.professione.copy();
        this.partitaIva = other.partitaIva == null ? null : other.partitaIva.copy();
        this.numeroTelefonoFisso = other.numeroTelefonoFisso == null ? null : other.numeroTelefonoFisso.copy();
        this.numeroTelefonoCellulare = other.numeroTelefonoCellulare == null ? null : other.numeroTelefonoCellulare.copy();
        this.eMail = other.eMail == null ? null : other.eMail.copy();
        this.indirizzoPec = other.indirizzoPec == null ? null : other.indirizzoPec.copy();
        this.indirizzoResidenza = other.indirizzoResidenza == null ? null : other.indirizzoResidenza.copy();
        this.capResidenza = other.capResidenza == null ? null : other.capResidenza.copy();
        this.comuneResidenza = other.comuneResidenza == null ? null : other.comuneResidenza.copy();
        this.provinciaResidenza = other.provinciaResidenza == null ? null : other.provinciaResidenza.copy();
        this.note = other.note == null ? null : other.note.copy();
        this.candidatoId = other.candidatoId == null ? null : other.candidatoId.copy();
        this.competenzeLngId = other.competenzeLngId == null ? null : other.competenzeLngId.copy();
        this.linguaId = other.linguaId == null ? null : other.linguaId.copy();
        this.titoloStudioId = other.titoloStudioId == null ? null : other.titoloStudioId.copy();
        this.tipotitolodistudioId = other.tipotitolodistudioId == null ? null : other.tipotitolodistudioId.copy();
        this.curriculumId = other.curriculumId == null ? null : other.curriculumId.copy();
        this.competenzaId = other.competenzaId == null ? null : other.competenzaId.copy();
        this.areaCompetenzaId = other.areaCompetenzaId == null ? null : other.areaCompetenzaId.copy();
        this.areaCompetenzaDa = other.areaCompetenzaDa == null ? null : other.areaCompetenzaDa.copy();
        this.areaCompetenzaA = other.areaCompetenzaA == null ? null : other.areaCompetenzaA.copy();
        this.dichiarazioniId = other.dichiarazioniId == null ? null : other.dichiarazioniId.copy();
        this.activated = other.activated == null ? null : other.activated.copy();
    }

    @Override
    public AnagraficaCandidatoCriteria copy() {
        return new AnagraficaCandidatoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCognome() {
        return cognome;
    }

    public void setCognome(StringFilter cognome) {
        this.cognome = cognome;
    }

    public StringFilter getNome() {
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public StringFilter getLuogoNascita() {
        return luogoNascita;
    }

    public void setLuogoNascita(StringFilter luogoNascita) {
        this.luogoNascita = luogoNascita;
    }

    public LocalDateFilter getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDateFilter dataNascita) {
        this.dataNascita = dataNascita;
    }

    public StringFilter getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(StringFilter codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public StringFilter getProfessione() {
        return professione;
    }

    public void setProfessione(StringFilter professione) {
        this.professione = professione;
    }

    public StringFilter getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(StringFilter partitaIva) {
        this.partitaIva = partitaIva;
    }

    public StringFilter getNumeroTelefonoFisso() {
        return numeroTelefonoFisso;
    }

    public void setNumeroTelefonoFisso(StringFilter numeroTelefonoFisso) {
        this.numeroTelefonoFisso = numeroTelefonoFisso;
    }

    public StringFilter getNumeroTelefonoCellulare() {
        return numeroTelefonoCellulare;
    }

    public void setNumeroTelefonoCellulare(StringFilter numeroTelefonoCellulare) {
        this.numeroTelefonoCellulare = numeroTelefonoCellulare;
    }

    public StringFilter geteMail() {
        return eMail;
    }

    public void seteMail(StringFilter eMail) {
        this.eMail = eMail;
    }

    public StringFilter getIndirizzoPec() {
        return indirizzoPec;
    }

    public void setIndirizzoPec(StringFilter indirizzoPec) {
        this.indirizzoPec = indirizzoPec;
    }

    public StringFilter getIndirizzoResidenza() {
        return indirizzoResidenza;
    }

    public void setIndirizzoResidenza(StringFilter indirizzoResidenza) {
        this.indirizzoResidenza = indirizzoResidenza;
    }

    public StringFilter getCapResidenza() {
        return capResidenza;
    }

    public void setCapResidenza(StringFilter capResidenza) {
        this.capResidenza = capResidenza;
    }

    public StringFilter getComuneResidenza() {
        return comuneResidenza;
    }

    public void setComuneResidenza(StringFilter comuneResidenza) {
        this.comuneResidenza = comuneResidenza;
    }

    public StringFilter getProvinciaResidenza() {
        return provinciaResidenza;
    }

    public void setProvinciaResidenza(StringFilter provinciaResidenza) {
        this.provinciaResidenza = provinciaResidenza;
    }

    public StringFilter getNote() {
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
    }

    public LongFilter getCandidatoId() {
        return candidatoId;
    }

    public void setCandidatoId(LongFilter candidatoId) {
        this.candidatoId = candidatoId;
    }

    public LongFilter getCompetenzeLngId() {
        return competenzeLngId;
    }

    public void setCompetenzeLngId(LongFilter competenzeLngId) {
        this.competenzeLngId = competenzeLngId;
    }

    public LongFilter getLinguaId() {
		return linguaId;
	}

	public void setLinguaId(LongFilter linguaId) {
		this.linguaId = linguaId;
	}

	public IntegerFilter getLinguaLivello() {
		return linguaLivello;
	}

	public void setLinguaLivello(IntegerFilter linguaLivello) {
		this.linguaLivello = linguaLivello;
	}

	public LongFilter getTitoloStudioId() {
        return titoloStudioId;
    }

    public void setTitoloStudioId(LongFilter titoloStudioId) {
        this.titoloStudioId = titoloStudioId;
    }

    public StringFilter getTipotitolodistudioId() {
		return tipotitolodistudioId;
	}

	public void setTipotitolodistudioId(StringFilter tipotitolodistudioId) {
		this.tipotitolodistudioId = tipotitolodistudioId;
	}

	public IntegerFilter getTitolostudioDal() {
		return titolostudioDal;
	}

	public void setTitolostudioDal(IntegerFilter titolostudioDal) {
		this.titolostudioDal = titolostudioDal;
	}

	public IntegerFilter getTitolostudioAl() {
		return titolostudioAl;
	}

	public void setTitolostudioAl(IntegerFilter titolostudioAl) {
		this.titolostudioAl = titolostudioAl;
	}

	public LongFilter getCurriculumId() {
        return curriculumId;
    }

    public void setCurriculumId(LongFilter curriculumId) {
        this.curriculumId = curriculumId;
    }

    public LongFilter getCompetenzaId() {
        return competenzaId;
    }

    public void setCompetenzaId(LongFilter competenzaId) {
        this.competenzaId = competenzaId;
    }

    public LongFilter getAreaCompetenzaId() {
		return areaCompetenzaId;
	}

	public IntegerFilter getAreaCompetenzaDa() {
		return areaCompetenzaDa;
	}

	public void setAreaCompetenzaDa(IntegerFilter areaCompetenzaDa) {
		this.areaCompetenzaDa = areaCompetenzaDa;
	}

	public IntegerFilter getAreaCompetenzaA() {
		return areaCompetenzaA;
	}

	public void setAreaCompetenzaA(IntegerFilter areaCompetenzaA) {
		this.areaCompetenzaA = areaCompetenzaA;
	}

	public void setAreaCompetenzaId(LongFilter areaCompetenzaId) {
		this.areaCompetenzaId = areaCompetenzaId;
	}

	public LongFilter getDichiarazioniId() {
        return dichiarazioniId;
    }

    public void setDichiarazioniId(LongFilter dichiarazioniId) {
        this.dichiarazioniId = dichiarazioniId;
    }


    public BooleanFilter getActivated() {
		return activated;
	}

	public void setActivated(BooleanFilter activated) {
		this.activated = activated;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AnagraficaCandidatoCriteria that = (AnagraficaCandidatoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(cognome, that.cognome) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(luogoNascita, that.luogoNascita) &&
            Objects.equals(dataNascita, that.dataNascita) &&
            Objects.equals(codiceFiscale, that.codiceFiscale) &&
            Objects.equals(professione, that.professione) &&
            Objects.equals(partitaIva, that.partitaIva) &&
            Objects.equals(numeroTelefonoFisso, that.numeroTelefonoFisso) &&
            Objects.equals(numeroTelefonoCellulare, that.numeroTelefonoCellulare) &&
            Objects.equals(eMail, that.eMail) &&
            Objects.equals(indirizzoPec, that.indirizzoPec) &&
            Objects.equals(indirizzoResidenza, that.indirizzoResidenza) &&
            Objects.equals(capResidenza, that.capResidenza) &&
            Objects.equals(comuneResidenza, that.comuneResidenza) &&
            Objects.equals(provinciaResidenza, that.provinciaResidenza) &&
            Objects.equals(note, that.note) &&
            Objects.equals(candidatoId, that.candidatoId) &&
            Objects.equals(competenzeLngId, that.competenzeLngId) &&
            Objects.equals(linguaId, that.linguaId) &&
            Objects.equals(linguaLivello, that.linguaLivello) &&
            Objects.equals(titoloStudioId, that.titoloStudioId) &&
            Objects.equals(tipotitolodistudioId, that.tipotitolodistudioId) &&
            Objects.equals(titolostudioDal, that.titolostudioDal) &&
            Objects.equals(titolostudioAl, that.titolostudioAl) &&
            Objects.equals(curriculumId, that.curriculumId) &&
            Objects.equals(competenzaId, that.competenzaId) &&
            Objects.equals(areaCompetenzaId, that.areaCompetenzaId) &&
            Objects.equals(dichiarazioniId, that.dichiarazioniId) &&
            Objects.equals(activated, that.activated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        cognome,
        nome,
        luogoNascita,
        dataNascita,
        codiceFiscale,
        professione,
        partitaIva,
        numeroTelefonoFisso,
        numeroTelefonoCellulare,
        eMail,
        indirizzoPec,
        indirizzoResidenza,
        capResidenza,
        comuneResidenza,
        provinciaResidenza,
        note,
        candidatoId,
        competenzeLngId,
        linguaId,
        linguaLivello,
        titoloStudioId,
        tipotitolodistudioId,
        titolostudioDal,
        titolostudioAl,
        curriculumId,
        competenzaId,
        areaCompetenzaId,
        areaCompetenzaDa,
        areaCompetenzaA,
        dichiarazioniId,
        activated
        );
    }

    @Override
    public String toString() {
        return "AnagraficaCandidatoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (cognome != null ? "cognome=" + cognome + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (luogoNascita != null ? "luogoNascita=" + luogoNascita + ", " : "") +
                (dataNascita != null ? "dataNascita=" + dataNascita + ", " : "") +
                (codiceFiscale != null ? "codiceFiscale=" + codiceFiscale + ", " : "") +
                (professione != null ? "professione=" + professione + ", " : "") +
                (partitaIva != null ? "partitaIva=" + partitaIva + ", " : "") +
                (numeroTelefonoFisso != null ? "numeroTelefonoFisso=" + numeroTelefonoFisso + ", " : "") +
                (numeroTelefonoCellulare != null ? "numeroTelefonoCellulare=" + numeroTelefonoCellulare + ", " : "") +
                (eMail != null ? "eMail=" + eMail + ", " : "") +
                (indirizzoPec != null ? "indirizzoPec=" + indirizzoPec + ", " : "") +
                (indirizzoResidenza != null ? "indirizzoResidenza=" + indirizzoResidenza + ", " : "") +
                (capResidenza != null ? "capResidenza=" + capResidenza + ", " : "") +
                (comuneResidenza != null ? "comuneResidenza=" + comuneResidenza + ", " : "") +
                (provinciaResidenza != null ? "provinciaResidenza=" + provinciaResidenza + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
                (candidatoId != null ? "candidatoId=" + candidatoId + ", " : "") +
                (competenzeLngId != null ? "competenzeLngId=" + competenzeLngId + ", " : "") +
                (linguaId != null ? "linguaId=" + linguaId + ", " : "") +
                (linguaLivello != null ? "linguaLivello=" + linguaLivello + ", " : "") +
                (titoloStudioId != null ? "titoloStudioId=" + titoloStudioId + ", " : "") +
                (tipotitolodistudioId != null ? "tipotitolodistudioId=" + tipotitolodistudioId + ", " : "") +
                (titolostudioDal != null ? "titolostudioDal=" + titolostudioDal + ", " : "") +
                (titolostudioAl != null ? "titolostudioAl=" + titolostudioAl + ", " : "") +
                (curriculumId != null ? "curriculumId=" + curriculumId + ", " : "") +
                (competenzaId != null ? "competenzaId=" + competenzaId + ", " : "") +
                (areaCompetenzaId != null ? "areaCompetenzaId=" + areaCompetenzaId + ", " : "") +
                (areaCompetenzaDa != null ? "areaCompetenzaDa=" + areaCompetenzaDa + ", " : "") +
                (areaCompetenzaA != null ? "areaCompetenzaA=" + areaCompetenzaA + ", " : "") +
                (dichiarazioniId != null ? "dichiarazioniId=" + dichiarazioniId + ", " : "") +
                (activated != null ? "activated=" + activated + ", " : "") +
            "}";
    }
}
