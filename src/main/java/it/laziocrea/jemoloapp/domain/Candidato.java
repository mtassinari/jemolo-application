package it.laziocrea.jemoloapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import it.laziocrea.jemoloapp.config.Constants;
import it.laziocrea.jemoloapp.service.util.AttributeEncryptor;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ColumnTransformer;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity Candidato
 * @author Marco Tassinari
 */
@Entity
@Table(name = "candidato")
public class Candidato extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidatoSequenceGenerator")
    @SequenceGenerator(name = "candidatoSequenceGenerator", sequenceName="candidato_sequence_generator", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "nominativo")
    private String nominativo;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "cognome", nullable = false)
    private String cognome;

    @NotNull
    @Pattern(regexp = Constants.CODICE_FISCALE_REGEX)
    @Column(name = "codice_fiscale", nullable = false, unique = true)
    @Convert(converter = AttributeEncryptor.class)
    private String codiceFiscale;

    @NotNull
    @Pattern(regexp = "^[A-z0-9\\.\\+_-]+@[A-z0-9\\._-]+\\.[A-z]{2,6}$")
    @Column(name = "e_mail", nullable = false, unique = true)
    /*@ColumnTransformer(
            read = "TRIM(CHAR(0) FROM UTF8TOSTRING(DECRYPT('AES', HASH('SHA256', STRINGTOUTF8('secret-key-12345'), 1), e_mail)))",
            write = "ENCRYPT('AES', HASH('SHA256', STRINGTOUTF8('secret-key-12345'), 1), STRINGTOUTF8(?))"
    )*/
    /*@ColumnTransformer(
            read = "PGP_SYM_DECRYPT(e_mail, 'secret-key-12345')",
            write = "PGP_SYM_ENCRYPT (?, 'secret-key-12345')"
    )*/
    @Convert(converter = AttributeEncryptor.class)
    private String email;

    @OneToOne(mappedBy = "candidato")
    @JsonIgnore
    private AnagraficaCandidato anagraficaCandidato;
    
    @JsonIgnore
    @NotNull
	@Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;
    
    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;
    
    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @Size(min = 2, max = 10)
    @Column(name = "lang_key", length = 10)
    private String langKey;
    
    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    private String resetKey;

    @Column(name = "reset_date")
    private Instant resetDate = null;

    @NotNull
    @ManyToOne(optional = false)
    private StatoRegistrazione stato;
    
    @Column(name = "primo_avviso")
    private Boolean primoAvviso = false;
    
    @Column(name = "secondo_avviso")
    private Boolean secondoAvviso = false;
    
    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "candidato_authority",
        joinColumns = {@JoinColumn(name = "candidato_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();
    
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNominativo() {
        return nominativo;
    }

    public Candidato nominativo(String nominativo) {
        this.nominativo = nominativo;
        return this;
    }

    public void setNominativo(String nominativo) {
        this.nominativo = nominativo;
    }

    public String getNome() {
        return nome;
    }

    public Candidato nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public Candidato cognome(String cognome) {
        this.cognome = cognome;
        return this;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public Candidato codiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
        return this;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getEmail() {
        return email;
    }

    public Candidato email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AnagraficaCandidato getAnagraficaCandidato() {
        return anagraficaCandidato;
    }

    public Candidato anagraficaCandidato(AnagraficaCandidato anagraficaCandidato) {
        this.anagraficaCandidato = anagraficaCandidato;
        return this;
    }

    public void setAnagraficaCandidato(AnagraficaCandidato anagraficaCandidato) {
        this.anagraficaCandidato = anagraficaCandidato;
    }
    
    public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Instant getResetDate() {
        return resetDate;
    }

    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }
    
	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}


    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }
    
    public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public StatoRegistrazione getStato() {
		return stato;
	}

	public void setStato(StatoRegistrazione stato) {
		this.stato = stato;
	}
	
    public Candidato stato(StatoRegistrazione stato) {
        this.stato = stato;
        return this;
    }

	public boolean isPrimoAvviso() {
		return primoAvviso;
	}

	public void setPrimoAvviso(boolean primoAvviso) {
		this.primoAvviso = primoAvviso;
	}

	public boolean isSecondoAvviso() {
		return secondoAvviso;
	}

	public void setSecondoAvviso(boolean secondoAvviso) {
		this.secondoAvviso = secondoAvviso;
	}

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Candidato)) {
            return false;
        }
        return id != null && id.equals(((Candidato) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Candidato{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cognome='" + getCognome() + "'" +
            ", codiceFiscale='" + getCodiceFiscale() + "'" +
            ", eMail='" + getEmail() + "'" +
            "}";
    }

}
