package it.laziocrea.jemoloapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link it.laziocrea.jemolorooster.domain.Lingua} entity.
 */
public class LinguaDTO implements Serializable {

    private Long id;

    @NotNull
    private String lingua;

    @NotNull
    private Boolean linguaUe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLingua() {
        return lingua;
    }

    public void setLingua(String lingua) {
        this.lingua = lingua;
    }

    public Boolean isLinguaUe() {
        return linguaUe;
    }

    public void setLinguaUe(Boolean linguaUe) {
        this.linguaUe = linguaUe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LinguaDTO linguaDTO = (LinguaDTO) o;
        if (linguaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), linguaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LinguaDTO{" +
            "id=" + getId() +
            ", lingua='" + getLingua() + "'" +
            ", linguaUe='" + isLinguaUe() + "'" +
            "}";
    }
}
