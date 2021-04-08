package it.laziocrea.jemoloapp.service.dto;
import io.swagger.annotations.ApiModel;
import it.laziocrea.jemoloapp.config.Constants;
import it.laziocrea.jemoloapp.domain.AnagraficaCandidato;
import it.laziocrea.jemoloapp.domain.Authority;
import it.laziocrea.jemoloapp.domain.Candidato;
import it.laziocrea.jemoloapp.domain.StatoRegistrazione;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO for the {@link it.laziocrea.jemoloapp.domain.Candidato} entity.
 */
@ApiModel(description = "Entity Candidato @author Marco Tassinari")
public class CandidatoDTO implements Serializable {

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
    @Pattern(regexp = Constants.CODICE_FISCALE_REGEX)
    private String codiceFiscale;

    @NotNull
    @Pattern(regexp = "^[A-z0-9.+_-]+@[A-z0-9._-]+.[A-z]{2,6}$")
    private String email;
    
    @Size(max = 256)
    private String imageUrl;

    private AnagraficaCandidato anagraficaCandidato;
    
    private Long anagraficaCandidatoId;
    
    private Long statoId;
    
    private StatoRegistrazione stato;
    
    private boolean activated = false;
    
    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;
 
    @Size(min = 2, max = 10)
    private String langKey;
    
    private Set<String> authorities;
        
    public CandidatoDTO() {
    	
    }
    
	public CandidatoDTO(Candidato candidato) {
		this.id = candidato.getId();
		this.login = candidato.getLogin();
		this.nominativo = candidato.getNominativo();
		this.nome = candidato.getNome();
		this.cognome = candidato.getCognome();
		this.codiceFiscale = candidato.getCodiceFiscale();
		this.anagraficaCandidatoId = candidato.getAnagraficaCandidato() != null ? candidato
				.getAnagraficaCandidato().getId() : null;
		this.email = candidato.getEmail();
        this.activated = candidato.isActivated();
        this.langKey = candidato.getLangKey();
        this.createdBy = candidato.getCreatedBy();
        this.createdDate = candidato.getCreatedDate();
        this.lastModifiedBy = candidato.getLastModifiedBy();
        this.lastModifiedDate = candidato.getLastModifiedDate();
        this.authorities = candidato.getAuthorities().stream()
                .map(Authority::getName)
                .collect(Collectors.toSet());
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Set<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<String> authorities) {
		this.authorities = authorities;
	}

	public String getNominativo() {
        return this.getNome()+" "+this.getCognome();
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
        return this.codiceFiscale.toUpperCase();
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
    
    public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Long getStatoId() {
		return statoId;
	}

	public void setStatoId(Long statoId) {
		this.statoId = statoId;
	}

	public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
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
    
    public String getLangKey() {
		return langKey;
	}

	public void setLangKey(String langKey) {
		this.langKey = langKey;
	}
	
    public AnagraficaCandidato getAnagraficaCandidato() {
		return anagraficaCandidato;
	}

    public void setAnagraficaCandidato(AnagraficaCandidato anagraficaCandidato) {
		this.anagraficaCandidato = anagraficaCandidato;
	}

    public Long getAnagraficaCandidatoId() {
		return anagraficaCandidatoId;
	}

    public void setAnagraficaCandidatoId(Long anagraficaCandidatoId) {
		this.anagraficaCandidatoId = anagraficaCandidatoId;
	}
    
	/**
	 * @return the stato
	 */
	public StatoRegistrazione getStato() {
		return stato;
	}

	/**
	 * @param stato the stato to set
	 */
	public void setStato(StatoRegistrazione stato) {
		this.stato = stato;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CandidatoDTO candidatoDTO = (CandidatoDTO) o;
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
		return "CandidatoDTO [id=" + id + ", login=" + login + ", nominativo=" + nominativo + ", nome=" + nome
				+ ", cognome=" + cognome + ", codiceFiscale=" + codiceFiscale + ", email=" + email + ", imageUrl="
				+ imageUrl + ", anagraficaCandidato=" + anagraficaCandidato + ", anagraficaCandidatoId="
				+ anagraficaCandidatoId + ", statoId=" + statoId + ", stato=" + stato + ", activated=" + activated
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", lastModifiedBy=" + lastModifiedBy
				+ ", lastModifiedDate=" + lastModifiedDate + ", langKey=" + langKey + ", authorities=" + authorities
				+ "]";
	}
}
