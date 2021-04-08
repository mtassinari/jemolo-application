package it.laziocrea.jemoloapp.service.dto;
import io.swagger.annotations.ApiModel;
import it.laziocrea.jemoloapp.domain.AnagraficaCandidato;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link it.laziocrea.jemolorooster.domain.Curriculum} entity.
 */
@ApiModel(description = "Entity Curriculum\n@author Marco Tassinari")
public class CurriculumDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    @NotNull
    private String cv;

    private String note;

    private Long anagraficaId;
    
    private AnagraficaCandidato anagrafica;
    
    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;
    
    @NotNull
    private Long size;

    @NotNull
    private String urlAllegato;

    private String mimeType;
    
    private Long allegatoId;
    
    // private Allegato allegato;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getAnagraficaId() {
        return anagraficaId;
    }

    public void setAnagraficaId(Long anagraficaCandidatoId) {
        this.anagraficaId = anagraficaCandidatoId;
    }

	public AnagraficaCandidato getAnagrafica() {
		return anagrafica;
	}

	public void setAnagrafica(AnagraficaCandidato anagrafica) {
		this.anagrafica = anagrafica;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Instant getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Instant lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getUrlAllegato() {
		return urlAllegato;
	}

	public void setUrlAllegato(String urlAllegato) {
		this.urlAllegato = urlAllegato;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	/**
	 * @return the allegatoId
	 */
	public Long getAllegatoId() {
		return allegatoId;
	}

	/**
	 * @param allegatoId the allegatoId to set
	 */
	public void setAllegatoId(Long allegatoId) {
		this.allegatoId = allegatoId;
	}

	/*
	 * public Allegato getAllegato() { return allegato; }
	 * 
	 * public void setAllegato(Allegato allegato) { this.allegato = allegato; }
	 */

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CurriculumDTO curriculumDTO = (CurriculumDTO) o;
        if (curriculumDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), curriculumDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CurriculumDTO{" +
            "id=" + getId() +
            ", cv='" + getCv() + "'" +
            ", size=" + getSize() +
            ", urlAllegato='" + getUrlAllegato() + "'" +
            ", mimeType='" + getMimeType() + "'" +
            ", note='" + getNote() + "'" +
            ", attach=" + getAllegatoId() +
            ", anagrafica=" + getAnagraficaId() +
            "}";
    }
}
