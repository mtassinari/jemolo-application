package it.laziocrea.jemoloapp.service.dto;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link it.laziocrea.jemolorooster.domain.TitoloStudio} entity.
 */
@ApiModel(description = "Entity TitoloStudio\n@author Marco Tassinari")
public class TitoloStudioDTO implements Serializable {

    private Long id;

    @NotNull
    private String tipologia;

    @NotNull
    private String descrizione;

    @NotNull
    private String conseguimento;

    @NotNull
    private String sanno;

    @NotNull
    private Integer anno;

    @NotNull
    private String voto;


    private Long anagraficaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getConseguimento() {
        return conseguimento;
    }

    public void setConseguimento(String conseguimento) {
        this.conseguimento = conseguimento;
    }

    public String getSanno() {
        return sanno;
    }

    public void setSanno(String sanno) {
        this.sanno = sanno;
    }

    public Integer getAnno() {
        return anno;
    }

    public void setAnno(Integer anno) {
        this.anno = anno;
    }

    public String getVoto() {
        return voto;
    }

    public void setVoto(String voto) {
        this.voto = voto;
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

        TitoloStudioDTO titoloStudioDTO = (TitoloStudioDTO) o;
        if (titoloStudioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), titoloStudioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TitoloStudioDTO{" +
            "id=" + getId() +
            ", tipologia='" + getTipologia() + "'" +
            ", descrizione='" + getDescrizione() + "'" +
            ", conseguimento='" + getConseguimento() + "'" +
            ", anno='" + getAnno() + "'" +
            ", voto='" + getVoto() + "'" +
            ", anagrafica=" + getAnagraficaId() +
            "}";
    }
}
