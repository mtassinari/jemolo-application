package it.laziocrea.jemoloapp.service.impl;

import it.laziocrea.jemoloapp.service.CandidatoService;
import it.laziocrea.jemoloapp.config.Constants;
import it.laziocrea.jemoloapp.domain.Authority;
import it.laziocrea.jemoloapp.domain.Candidato;
import it.laziocrea.jemoloapp.domain.StatoRegistrazione;
import it.laziocrea.jemoloapp.domain.User;
import it.laziocrea.jemoloapp.file.helper.ExcelHelper;
import it.laziocrea.jemoloapp.repository.AuthorityRepository;
import it.laziocrea.jemoloapp.repository.CandidatoRepository;
import it.laziocrea.jemoloapp.repository.StatoRegistrazioneRepository;
import it.laziocrea.jemoloapp.security.AuthoritiesConstants;
import it.laziocrea.jemoloapp.security.SecurityUtils;
import it.laziocrea.jemoloapp.service.dto.CandidatoDTO;
import it.laziocrea.jemoloapp.service.dto.IscrittoDTO;
import it.laziocrea.jemoloapp.service.dto.UserDTO;
import it.laziocrea.jemoloapp.service.mapper.CandidatoMapper;
import it.laziocrea.jemoloapp.service.mapper.IscrittoMapper;
import it.laziocrea.jemoloapp.service.util.RandomUtil;
import it.laziocrea.jemoloapp.web.rest.errors.CodiceFiscaleAlreadyUsedException;
import it.laziocrea.jemoloapp.web.rest.errors.EmailAlreadyUsedException;
import it.laziocrea.jemoloapp.web.rest.errors.InvalidPasswordException;
import it.laziocrea.jemoloapp.web.rest.errors.LoginAlreadyUsedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.time.Instant;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;
import it.laziocrea.jemoloapp.service.util.AttributeEncryptor;
/**
 * Service Implementation for managing {@link Candidato}.
 */
@Service
@Transactional
public class CandidatoServiceImpl implements CandidatoService {

    private final Logger log = LoggerFactory.getLogger(CandidatoServiceImpl.class);

    private final CandidatoRepository candidatoRepository;

    private final CandidatoMapper candidatoMapper;
    
    private final IscrittoMapper iscrittoMapper;
    
    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;
    
    private final StatoRegistrazioneRepository statoRepository;
    
    private final AttributeEncryptor attributeEncryptor;
    
    public CandidatoServiceImpl(AttributeEncryptor attributeEncryptor, StatoRegistrazioneRepository statoRepository, AuthorityRepository authorityRepository, CandidatoRepository candidatoRepository, IscrittoMapper iscrittoMapper, CandidatoMapper candidatoMapper, PasswordEncoder passwordEncoder) {
        this.candidatoRepository = candidatoRepository;
        this.candidatoMapper = candidatoMapper;
        this.iscrittoMapper = iscrittoMapper;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.statoRepository = statoRepository;
        this.attributeEncryptor = attributeEncryptor;
    }

    /**
     * Save a candidato.
     *
     * @param candidatoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Candidato save(CandidatoDTO candidatoDTO) {
    	Candidato candidato = candidatoMapper.toEntity(candidatoDTO);
        if (candidatoDTO.getLangKey() == null) {
        	candidato.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
        } else {
        	candidato.setLangKey(candidatoDTO.getLangKey());
        }
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        candidato.setPassword(encryptedPassword);
        candidato.setResetKey(RandomUtil.generateResetKey());
        candidato.setResetDate(Instant.now());
        candidato.setActivated(true);
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.CANDIDATO).ifPresent(authorities::add);
        candidato.setAuthorities(authorities);
        candidato = candidatoRepository.save(candidato);
        log.debug("Request to save Candidato : {}", candidatoDTO);
        return candidato;
        //return candidatoMapper.toDto(candidato);
    }

    /**
     * Get all the candidatoes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CandidatoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Candidatoes");
        return candidatoRepository.findAll(pageable)
            .map(candidatoMapper::toDto);
    }

    /**
     * Get all the candidatoes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IscrittoDTO> findAllIscritti(Pageable pageable) {
        log.debug("Request to get all Iscritti");
        return candidatoRepository.findAll(pageable)
            .map(iscrittoMapper::toDto);
    }

    /**
     * Get all the candidatoes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IscrittoDTO> findAllIscrittiAttivi(Pageable pageable) {
        log.debug("Request to get all Iscritti");
        return candidatoRepository.findAllByActivatedIsTrue(pageable)
            .map(iscrittoMapper::toDto);
    }

    /**
     * Get all the candidatoes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IscrittoDTO> publicFindAllIscrittiAttivi(Pageable pageable) {
        log.debug("Request to get all Iscritti");
        return candidatoRepository.findAllByActivatedIsTrueOrderByCognomeAscNomeAsc(pageable)
            .map(iscrittoMapper::toDto);
    }
    
    /**
    *  Get all the candidatoes where AnagraficaCandidato is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<CandidatoDTO> findAllWhereAnagraficaCandidatoIsNull() {
        log.debug("Request to get all candidatoes where AnagraficaCandidato is null");
        return StreamSupport
            .stream(candidatoRepository.findAll().spliterator(), false)
            .filter(candidato -> candidato.getAnagraficaCandidato() == null)
            .map(candidatoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one candidato by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CandidatoDTO> findOne(Long id) {
        log.debug("Request to get Candidato : {}", id);
        return candidatoRepository.findById(id)
            .map(candidatoMapper::toDto);
    }

    /**
     * Delete the candidato by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Candidato : {}", id);
        Optional<Candidato> candidato = candidatoRepository.findById(id);
        candidato.ifPresent(candidatoRepository::delete);
        //candidatoRepository.deleteById(id);
    }
    
    /**
     * Register a Candidato
     * 
     * @param candidatoDTO the candidate to register.
     * @param password
     * @return the registered candidate.
     */
	@Override
	public Candidato registerCandidato(CandidatoDTO candidatoDTO, String password) {
        candidatoRepository.findOneByEmailIgnoreCase(attributeEncryptor.convertToDatabaseColumn(candidatoDTO.getEmail())).ifPresent(existingCandidato -> {
        	log.debug("existingEmailCandidato>>>>>>>>: {}", existingCandidato.getEmail());
            boolean removed = removeNonActivatedCandidato(existingCandidato);
            if (!removed) {
                throw new EmailAlreadyUsedException();
            }
        });
        candidatoRepository.findOneByLogin(candidatoDTO.getLogin()).ifPresent(existingCandidato -> {
        	log.debug("existingLoginCandidato>>>>>>>>: {}", existingCandidato.getLogin());
            boolean removed = removeNonActivatedCandidato(existingCandidato);
            if (!removed) {
                throw new LoginAlreadyUsedException();
            }
        });
        candidatoRepository.findOneByCodiceFiscaleIgnoreCase(attributeEncryptor.convertToDatabaseColumn(candidatoDTO.getCodiceFiscale())).ifPresent(existingCandidato -> {
        	log.debug("cfexistingCandidato>>>>>>>>: {}", existingCandidato.getCodiceFiscale());
        	throw new CodiceFiscaleAlreadyUsedException();
        });
        /*Optional<Candidato> cfexistingCandidato = candidatoRepository.findOneByCodiceFiscaleIgnoreCase(attributeEncryptor.convertToDatabaseColumn(candidatoDTO.getCodiceFiscale()));
        log.debug("cfexistingCandidato>>>>>>>>: {}", cfexistingCandidato.isPresent());
        if (cfexistingCandidato.isPresent()) {
            throw new CodiceFiscaleAlreadyUsedException();
        };*/
        String encryptedPassword = passwordEncoder.encode(password);
        // new candidato is not active
        // candidatoDTO.setStatoId(new Long(0));
        candidatoDTO.setStatoId(Long.valueOf(0));
        candidatoDTO.setActivated(false);
        candidatoDTO.setCodiceFiscale(candidatoDTO.getCodiceFiscale().toUpperCase());
        candidatoDTO.setEmail(candidatoDTO.getEmail().toLowerCase());
        Candidato newCandidato = candidatoMapper.toEntity(candidatoDTO);
        // new user gets initially a generated password
        newCandidato.setPassword(encryptedPassword);
        // new user gets registration key
        newCandidato.setActivationKey(RandomUtil.generateActivationKey());
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.CANDIDATO).ifPresent(authorities::add);
        newCandidato.setAuthorities(authorities);
        candidatoRepository.save(newCandidato);
        log.debug("Created Information for Iscritto: {}", newCandidato);
        return newCandidato;
	}
	
    private boolean removeNonActivatedCandidato(Candidato existingCandidato){
        if (existingCandidato.isActivated() || existingCandidato.getStato().getId() != 0) {
             return false;
        }
        candidatoRepository.delete(existingCandidato);
        candidatoRepository.flush();
        return true;
    }
    
    @Override
    public Optional<Candidato> activateCandidatoRegistration(String key) {
        log.debug("Activating candidato for activation key {}", key);
        return candidatoRepository.findOneByActivationKey(key)
            .map(candidato -> {
            	CandidatoDTO dto = candidatoMapper.toDto(candidato);        	
                // activate given user for the registration key.
            	// dto.setStatoId(new Long(1));
            	dto.setStatoId(Long.valueOf(1));
            	Candidato entity = candidatoMapper.toEntity(dto);
            	candidato.setStato(entity.getStato());
                candidato.setActivated(true);
                candidato.setActivationKey(null);
                log.debug("Activated candidato: {}", candidato);
                return candidato;
            });
    }
    
    public Optional<Candidato> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        return candidatoRepository.findOneByResetKey(key)
            .filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400)))
            .map(user -> {
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setResetKey(null);
                user.setResetDate(null);
                return user;
            });
    }
    
    /**
     * Update basic information (nome, cognome, email, language) for the current candidato.
     *
     * @param nome 		nome of candidato.
     * @param cognome  	cognome of the candidato.
     * @param email     email id of candidato.
     * @param langKey   language key.
     * @param imageUrl  image URL of candidato.
     */
    @Override
    public void updateUser(String nome, String cognome, String email, String langKey, String imageUrl) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(candidatoRepository::findOneByLogin)
            .ifPresent(user -> {
                user.setNome(nome);
                user.setCognome(cognome);
                user.setEmail(email.toLowerCase());
                user.setLangKey(langKey);
                user.setImageUrl(imageUrl);
                log.debug("Changed Information for Candidato: {}", user);
            });
    }
    
    /**
     * Update all information for a specific candidato, and return the modified candidato.
     *
     * @param candidatoDTO candidato to update.
     * @return updated candidato.
     */
	@Override
	public Optional<CandidatoDTO> update(CandidatoDTO candidatoDTO) {
		log.debug("Statoregistrazione {} ",candidatoDTO.getStatoId());
        return Optional.of(candidatoRepository
                .findById(candidatoDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(candidato -> {
                    candidato.setNome(candidatoDTO.getNome());
                    candidato.setCognome(candidatoDTO.getCognome());
                    candidato.setEmail(candidatoDTO.getEmail().toLowerCase());
                    candidato.setCodiceFiscale(candidatoDTO.getCodiceFiscale());
                    StatoRegistrazione stato = candidato.getStato();
                    stato = statoRepository.getOne(candidatoDTO.getStatoId());
                    candidato.setStato(stato);
                    if (candidatoDTO.getStatoId() == 0)
                    	candidato.setLogin(candidatoDTO.getLogin());
                    log.debug("Changed Information for Candidato: {}", candidato);
                    return candidato;
                })
                .map(CandidatoDTO::new);
	}
	
	@Override
	public Optional<CandidatoDTO> update(@Valid IscrittoDTO iscrittoDTO) {
        return Optional.of(candidatoRepository
                .findById(iscrittoDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(candidato -> {
                    candidato.setActivated(iscrittoDTO.isActivated());
                    StatoRegistrazione stato = candidato.getStato();
                    stato.removeCandidato(candidato);
                    stato = new StatoRegistrazione();
                    stato.setId(iscrittoDTO.getStatoRegistrazioneId());
                    /*if(iscrittoDTO.isActivated())
                    	stato.setId(new Long(1));
                    else
                    	stato.setId(new Long(2));*/
                    candidato.setStato(stato);
                    log.debug("Changed Information for Iscritto: {}", candidato);
                    return candidato;
                })
                .map(CandidatoDTO::new);
	}
	
	/**
	 * 
	 */
	@Override
    public Optional<Candidato> requestPasswordReset(String email) {
        return candidatoRepository.findOneByEmailIgnoreCase(email)
            .filter(Candidato::isActivated)
            .map(user -> {
                user.setResetKey(RandomUtil.generateResetKey());
                user.setResetDate(Instant.now());
                return user;
            });
    }
	
	/**
	 * 
	 * @param codiceFiscale
	 * @return
	 */
	@Override
    public Optional<Candidato> requestPasswordReset2(String codiceFiscale) {
		codiceFiscale = attributeEncryptor.convertToDatabaseColumn(codiceFiscale.toUpperCase());
        return candidatoRepository.findOneByCodiceFiscaleIgnoreCase(codiceFiscale)
            .filter(Candidato::isActivated)
            .map(user -> {
                user.setResetKey(RandomUtil.generateResetKey());
                user.setResetDate(Instant.now());
                return user;
            });
    }
	
	/**
	 * 
	 * @param currentClearTextPassword
	 * @param newPassword
	 */
	@Override
    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(candidatoRepository::findOneByLogin)
            .ifPresent(candidato -> {
                String currentEncryptedPassword = candidato.getPassword();
                if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                    throw new InvalidPasswordException();
                }
                String encryptedPassword = passwordEncoder.encode(newPassword);
                candidato.setPassword(encryptedPassword);
                log.debug("Changed password for Candiato: {}", candidato);
            });
    }
	
    @Transactional(readOnly = true)
    @Override
    public Optional<Candidato> getCandidatoWithAuthorities() {
    	// log.debug("SecurityUtils.getCurrentUserLogin(): "+ SecurityUtils.getCurrentUserLogin());
        return SecurityUtils.getCurrentUserLogin().flatMap(candidatoRepository::findOneWithAuthoritiesByLogin);
    }
    
	/**
	 * 
	 */
	@Override
	public void deleteAccount(String codiceFiscale) {
        SecurityUtils.getCurrentUserLogin()
        .flatMap(candidatoRepository::findOneByLogin)
        .ifPresent(user -> {
        	StatoRegistrazione stato = new StatoRegistrazione();
        	stato.setId(Long.valueOf(3));
        	user.setStato(stato);
            user.setActivated(Boolean.valueOf(false));
            log.info("Account cancellated: {}", user);
        });
	}

	@Override
	public ByteArrayInputStream loadAlliscritti() {
		List<Candidato> iscritti = candidatoRepository.findAll();
		ByteArrayInputStream inputSstram = ExcelHelper.tutorialsToExcel(iscritti);
		return inputSstram;
	}

}
