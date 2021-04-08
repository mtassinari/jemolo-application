package it.laziocrea.jemoloapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link it.laziocrea.jemoloapp.domain.DichiarazioniObligatorie} entity.
 */
public class DichiarazioniObligatorieDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean stato;

    @NotNull
    private String dichiarazione;

    private Long anagraficaId;

    private Long dichiarazioniId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isStato() {
        return stato;
    }

    public void setStato(Boolean stato) {
        this.stato = stato;
    }

    public String getDichiarazione() {
        return dichiarazione;
    }

    public void setDichiarazione(String dichiarazione) {
        this.dichiarazione = dichiarazione;
    }

    public Long getAnagraficaId() {
        return anagraficaId;
    }

    public void setAnagraficaId(Long anagraficaCandidatoId) {
        this.anagraficaId = anagraficaCandidatoId;
    }

    public Long getDichiarazioniId() {
        return dichiarazioniId;
    }

    public void setDichiarazioniId(Long dichiarazioniId) {
        this.dichiarazioniId = dichiarazioniId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DichiarazioniObligatorieDTO dichiarazioniObligatorieDTO = (DichiarazioniObligatorieDTO) o;
        if (dichiarazioniObligatorieDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dichiarazioniObligatorieDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DichiarazioniObligatorieDTO{" +
            "id=" + getId() +
            ", stato='" + isStato() + "'" +
            ", dichiarazione='" + getDichiarazione() + "'" +
            ", anagraficaId=" + getAnagraficaId() +
            ", dichiarazioniId=" + getDichiarazioniId() +
            "}";
    }
}
