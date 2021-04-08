package it.laziocrea.jemoloapp.service.dto;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link it.laziocrea.jemoloapp.domain.StatoRegistrazione} entity.
 */
@ApiModel(description = "Entity StatoRegistrazione @author Marco Tassinari")
public class StatoRegistrazioneDTO implements Serializable {

    private Long id;

    @NotNull
    private String descrizione;


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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatoRegistrazioneDTO other = (StatoRegistrazioneDTO) obj;
		if (descrizione == null) {
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StatoRegistrazioneDTO [id=" + id + ", descrizione=" + descrizione + ", getId()=" + getId()
				+ ", getDescrizione()=" + getDescrizione() + "]";
	}
}
