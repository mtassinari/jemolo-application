package it.laziocrea.jemoloapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link it.laziocrea.jemoloapp.domain.Dichiarazioni} entity.
 */
public class DichiarazioniDTO implements Serializable {

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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DichiarazioniDTO dichiarazioniDTO = (DichiarazioniDTO) o;
        if (dichiarazioniDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dichiarazioniDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DichiarazioniDTO{" +
            "id=" + getId() +
            ", descrizione='" + getDescrizione() + "'" +
            "}";
    }
}
