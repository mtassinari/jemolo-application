package it.laziocrea.jemoloapp.service.dto;
import io.swagger.annotations.ApiModel;
import it.laziocrea.jemoloapp.domain.AmbitoCompetenza;
import it.laziocrea.jemoloapp.domain.AnagraficaCandidato;
import it.laziocrea.jemoloapp.domain.Candidato;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link it.laziocrea.jemoloapp.domain.Competenza} entity.
 */
@ApiModel(description = "Entity Competenza @author Marco Tassinari")
public class CompetenzaDTO implements Serializable {

    private Long id;

    @NotNull
    private String descrizione;

    @NotNull
    private Integer anniEsperianza;

    private String note;

    private Long anagraficaId;
    
    private AnagraficaCandidato anagrafica;

    private Long ambitoCompId;
    
    private AmbitoCompetenza ambitoComp;
    
    private boolean selected;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Integer getAnniEsperianza() {
        return anniEsperianza;
    }

    public void setAnniEsperianza(Integer anniEsperianza) {
        this.anniEsperianza = anniEsperianza;
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

	public Long getAmbitoCompId() {
        return ambitoCompId;
    }

    public void setAmbitoCompId(Long ambitoCompetenzaId) {
        this.ambitoCompId = ambitoCompetenzaId;
    }

    /**
	 * @return the ambitoComp
	 */
	public AmbitoCompetenza getAmbitoComp() {
		return ambitoComp;
	}

	/**
	 * @param ambitoComp the ambitoComp to set
	 */
	public void setAmbitoComp(AmbitoCompetenza ambitoComp) {
		this.ambitoComp = ambitoComp;
	}

	/**
	 * @return the selected
	 */
	public boolean getSelected() {
		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompetenzaDTO competenzaDTO = (CompetenzaDTO) o;
        if (competenzaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), competenzaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompetenzaDTO{" +
            "id=" + getId() +
            ", descrizione='" + getDescrizione() + "'" +
            ", anniEsperianza=" + getAnniEsperianza() +
            ", note='" + getNote() + "'" +
            ", ambitoComp=" + getAmbitoComp() +
            "}";
    }
}
