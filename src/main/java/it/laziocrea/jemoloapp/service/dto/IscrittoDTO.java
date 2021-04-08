package it.laziocrea.jemoloapp.service.dto;

import io.swagger.annotations.ApiModel;
import it.laziocrea.jemoloapp.config.Constants;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link it.laziocrea.jemoloapp.domain.Candidato} entity.
 */
@ApiModel(description = "Entity Candidato\n@author Marco Tassinari")
public class IscrittoDTO implements Serializable {

    private Long id;
    
    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    private String nominativo;
    
    @NotNull
    private String nome;

    @NotNull
    private String cognome;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z]{6}[0-9]{2}[abcdehlmprstABCDEHLMPRST]{1}[0-9]{2}([a-zA-Z]{1}[0-9]{3})[a-zA-Z]{1}$")
    private String codiceFiscale;

    @NotNull
    @Pattern(regexp = "^[A-z0-9.+_-]+@[A-z0-9._-]+.[A-z]{2,6}$")
    private String email;

    private Long anagraficaCandidatoId;
    
    private Long statoRegistrazioneId;
    
    private boolean activated = false;
    
    public Long getId() {
        return id;
    }

    public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setId(Long id) {
        this.id = id;
    }

    public String getNominativo() {
		return nominativo;
	}

	public void setNominativo(String nominativo) {
		this.nominativo = nominativo;
	}

	public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
    public Long getAnagraficaCandidatoId() {
        return anagraficaCandidatoId;
    }

    public void setAnagraficaCandidatoId(Long anagraficaCandidatoId) {
        this.anagraficaCandidatoId = anagraficaCandidatoId;
    }

	public Long getStatoRegistrazioneId() {
        return statoRegistrazioneId;
    }

    public void setStatoRegistrazioneId(Long statoRegistrazioneId) {
        this.statoRegistrazioneId = statoRegistrazioneId;
    }

    public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
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

        IscrittoDTO candidatoDTO = (IscrittoDTO) o;
        if (candidatoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), candidatoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "IscrittoDTO [id=" + id + ", login=" + login + ", nominativo=" + nominativo + ", nome=" + nome
				+ ", cognome=" + cognome + ", codiceFiscale=" + codiceFiscale + ", email=" + email
				+ ", anagraficaCandidatoId=" + anagraficaCandidatoId + ", statoRegistrazioneId=" + statoRegistrazioneId
				+ ", activated=" + activated + "]";
	}

}
