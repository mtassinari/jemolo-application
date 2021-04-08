package it.laziocrea.jemoloapp.service.dto;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link it.laziocrea.jemolorooster.domain.CompetenzeLng} entity.
 */
@ApiModel(description = "Entity CompetenzeLinguistiche\n@author Marco Tassinari")
public class CompetenzeLngDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer livello;

    private Long linguaId;

    private Long anagraficaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLivello() {
        return livello;
    }

    public void setLivello(Integer livello) {
        this.livello = livello;
    }

    public Long getLinguaId() {
        return linguaId;
    }

    public void setLinguaId(Long linguaId) {
        this.linguaId = linguaId;
    }

    public Long getAnagraficaId() {
        return anagraficaId;
    }

    public void setAnagraficaId(Long anagraficaCandidatoId) {
        this.anagraficaId = anagraficaCandidatoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompetenzeLngDTO competenzeLngDTO = (CompetenzeLngDTO) o;
        if (competenzeLngDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), competenzeLngDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompetenzeLngDTO{" +
            "id=" + getId() +
            ", livello=" + getLivello() +
            ", lingua=" + getLinguaId() +
            ", anagrafica=" + getAnagraficaId() +
            "}";
    }
}
