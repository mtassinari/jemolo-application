package it.laziocrea.jemoloapp.service.impl;

import it.laziocrea.jemoloapp.service.AnagraficaCandidatoQueryService;
import it.laziocrea.jemoloapp.service.AnagraficaCandidatoService;
import it.laziocrea.jemoloapp.service.MailService2;
import it.laziocrea.jemoloapp.config.FileStorageProperties;
import it.laziocrea.jemoloapp.domain.AnagraficaCandidato;
import it.laziocrea.jemoloapp.domain.Candidato;
import it.laziocrea.jemoloapp.domain.Competenza;
import it.laziocrea.jemoloapp.domain.CompetenzeLng;
import it.laziocrea.jemoloapp.domain.Curriculum;
import it.laziocrea.jemoloapp.domain.CurriculumFile;
import it.laziocrea.jemoloapp.domain.DichiarazioniObligatorie;
import it.laziocrea.jemoloapp.domain.SearchParam;
import it.laziocrea.jemoloapp.domain.StatoRegistrazione;
import it.laziocrea.jemoloapp.domain.TitoloStudio;
import it.laziocrea.jemoloapp.file.helper.ExcelHelper;
import it.laziocrea.jemoloapp.repository.AnagraficaCandidatoRepository;
import it.laziocrea.jemoloapp.repository.CandidatoRepository;
import it.laziocrea.jemoloapp.service.dto.AnagraficaCandidatoCriteria;
import it.laziocrea.jemoloapp.service.dto.AnagraficaCandidatoDTO;
import it.laziocrea.jemoloapp.service.mapper.AnagraficaCandidatoMapper;
import it.laziocrea.jemoloapp.service.mapper.CurriculumMultipartFilesMapper;
import it.laziocrea.jemoloapp.service.util.AttributeEncryptor;
import it.laziocrea.jemoloapp.web.rest.errors.FileStorageException;
import it.laziocrea.jemoloapp.web.rest.errors.IndirizzoPecAlreadyUsedException;
import it.laziocrea.jemoloapp.web.rest.errors.PartitaIvaAlreadyUsedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing {@link AnagraficaCandidato}.
 */
@Service
@Transactional
public class AnagraficaCandidatoServiceImpl implements AnagraficaCandidatoService {

    private final Logger log = LoggerFactory.getLogger(AnagraficaCandidatoServiceImpl.class);

    private final AnagraficaCandidatoRepository anagraficaCandidatoRepository;

    private final AnagraficaCandidatoMapper anagraficaCandidatoMapper;
    
    private final CurriculumMultipartFilesMapper fileMapper;

    private final CandidatoRepository candidatoRepository;

    private final Path fileStorageLocation;
    
    private final MailService2 mailService;
    
    private final AttributeEncryptor attributeEncryptor;
    
    private final AnagraficaCandidatoQueryService anagraficaCandidatoQueryService;
    
    public AnagraficaCandidatoServiceImpl(AnagraficaCandidatoQueryService anagraficaCandidatoQueryService, AttributeEncryptor attributeEncryptor, MailService2 mailService, FileStorageProperties fileStorageProperties, CurriculumMultipartFilesMapper fileMapper, AnagraficaCandidatoRepository anagraficaCandidatoRepository, AnagraficaCandidatoMapper anagraficaCandidatoMapper, CandidatoRepository candidatoRepository) {
        this.anagraficaCandidatoRepository = anagraficaCandidatoRepository;
        this.anagraficaCandidatoMapper = anagraficaCandidatoMapper;
        this.candidatoRepository = candidatoRepository;
        this.fileMapper = fileMapper;
        this.mailService = mailService;
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
        log.debug(fileStorageProperties.getUploadDir());
        this.attributeEncryptor = attributeEncryptor;
        this.anagraficaCandidatoQueryService = anagraficaCandidatoQueryService;
    }
    
    /**
     * Save a anagraficaCandidato.
     *
     * @param anagraficaCandidatoDTO the entity to save.
     * @return the persisted entity.
     */
	@Override
	public AnagraficaCandidatoDTO save(AnagraficaCandidatoDTO anagraficaCandidatoDTO,List<MultipartFile> files) {
        log.debug("Request to save AnagraficaCandidato with files:"+files.size()+" {}", anagraficaCandidatoDTO);
        Optional<AnagraficaCandidato> existingAnagrafica;// = anagraficaCandidatoRepository.findOneByIndirizzoPecIgnoreCase(anagraficaCandidatoDTO.getIndirizzoPec());
        if(anagraficaCandidatoDTO.getIndirizzoPec() != null) {
        	existingAnagrafica = anagraficaCandidatoRepository.findOneByIndirizzoPecIgnoreCase(attributeEncryptor.convertToDatabaseColumn(anagraficaCandidatoDTO.getIndirizzoPec()));
	        if (anagraficaCandidatoDTO.getId() == null && existingAnagrafica.isPresent()) {
	        	log.debug("Nuova anagrafica PEC esistente: {}",existingAnagrafica.get().getNome()+" - "+existingAnagrafica.get().getIndirizzoPec());
	        	throw new IndirizzoPecAlreadyUsedException();
	        } 
	        if (existingAnagrafica.isPresent() && (!existingAnagrafica.get().getId().equals(anagraficaCandidatoDTO.getId()))) {
	        	log.debug("Aggiorna anagrafica PEC esistente: {}",existingAnagrafica.get().getIndirizzoPec());
	        	throw new IndirizzoPecAlreadyUsedException();
	        }
        }
        if (anagraficaCandidatoDTO.getPartitaIva() != null) {
	        existingAnagrafica = anagraficaCandidatoRepository.findOneByPartitaIvaIgnoreCase(anagraficaCandidatoDTO.getPartitaIva());
	        if (anagraficaCandidatoDTO.getId() == null && existingAnagrafica.isPresent()) {
	        	throw new PartitaIvaAlreadyUsedException();
	        } 
	        if (existingAnagrafica.isPresent() && (!existingAnagrafica.get().getId().equals(anagraficaCandidatoDTO.getId()))) {
	        	throw new PartitaIvaAlreadyUsedException();
	        }
        }
        AnagraficaCandidato anagraficaCandidato = anagraficaCandidatoMapper.toEntity(anagraficaCandidatoDTO);
		for (Iterator<CompetenzeLng> iterator =  anagraficaCandidato.getCompetenzeLngs().iterator(); iterator.hasNext();) {
			CompetenzeLng type = (CompetenzeLng) iterator.next();
			type.setAnagrafica(anagraficaCandidato);
		}        
		for (Iterator<Competenza> iterator =  anagraficaCandidato.getCompetenzas().iterator(); iterator.hasNext();) {
			Competenza type = (Competenza) iterator.next();
			type.setAnagrafica(anagraficaCandidato); 
		}
		for (Iterator<TitoloStudio> iterator =  anagraficaCandidato.getTitoloStudios().iterator(); iterator.hasNext();) {
			TitoloStudio type = (TitoloStudio) iterator.next();
			type.setAnagrafica(anagraficaCandidato); 
		}
		for (Iterator<DichiarazioniObligatorie> iterator =  anagraficaCandidato.getDichiarazionis().iterator(); iterator.hasNext();) {
			DichiarazioniObligatorie type = (DichiarazioniObligatorie) iterator.next();
			type.setAnagrafica(anagraficaCandidato); 
		}
        Long candidatoId = anagraficaCandidatoDTO.getCandidatoId();
        candidatoRepository.findById(candidatoId).ifPresent(anagraficaCandidato::candidato);
        if (anagraficaCandidatoDTO.getId() == null) {
        	Set<Curriculum> curricula = fileMapper.multiPartFilesToCvs(files);
        	curricula.forEach(anagraficaCandidato::addCurriculum);
        	//////////////STORE FILE ON SERVER
        	MultipartFile file = files.get(0);
        	Curriculum cv = curricula.iterator().next();
        	CurriculumFile cvFile = new CurriculumFile();
        	String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        	// String fileNameEncrypted = StringUtils.cleanPath("_encrypted_"+file.getOriginalFilename());
        	fileName = candidatoId+"_"+Instant.now().toEpochMilli()+"_"+fileName;
        	// fileNameEncrypted = candidatoId+"_"+Instant.now().toEpochMilli()+"_"+fileNameEncrypted;
            cvFile.setNomeFile(fileName);
            cvFile.setSize(file.getSize());
            cvFile.setMimeType(file.getContentType());
            try {
                // Check if the file's name contains invalid characters
                if(fileName.contains("..")) {
                    throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
                }

                // Copy file to the target location (Replacing existing file with the same name)
                Path targetLocation = this.fileStorageLocation.resolve(fileName);
                // Path targetLocationEncrypted = this.fileStorageLocation.resolve(fileNameEncrypted);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                //crypt file
                /*PdfReader pdfReader = new PdfReader(targetLocation.toString());
                PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(targetLocationEncrypted.toString()));
                pdfStamper.setEncryption("userpass".getBytes(),"ownerpass".getBytes(), 0, PdfWriter.ENCRYPTION_AES_256);
                pdfStamper.close();*/
                cvFile.setUrl(targetLocation.toString());
                cv.setUrlAllegato(targetLocation.toString());
			} catch (IOException /* | DocumentException */ ex) {
            	log.error(ex.getMessage());
                throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
            }
            cv.addCurriculumfile(cvFile);
        	//////////////////////////////////////////
        } else {
        	Set<Curriculum> curricula = anagraficaCandidatoDTO.getCurricula();
        	Curriculum cv = curricula.iterator().next();
        	cv.setAnagrafica(anagraficaCandidato);
        	MultipartFile file = files.get(0);
    		cv.setCv(file.getOriginalFilename());
    		cv.setSize(file.getSize());
    		cv.setMimeType(file.getContentType());
    		cv.setUrlAllegato("not-stored");
    		try {
    			cv.addAllegato(file.getBytes());
			} catch (IOException e) {
				log.error(e.getMessage());
			}
    		CurriculumFile cvFile = new CurriculumFile();
    		//////////////STORE FILE ON SERVER
            // Normalize file name
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String fileNameEncrypted = StringUtils.cleanPath("_encrypted_"+file.getOriginalFilename());
            fileName = candidatoId+"_"+Instant.now().toEpochMilli()+"_"+fileName;
            fileNameEncrypted = candidatoId+"_"+Instant.now().toEpochMilli()+"_"+fileNameEncrypted;
            cvFile.setNomeFile(fileName);
            cvFile.setSize(file.getSize());
            cvFile.setMimeType(file.getContentType());
            try {
                // Check if the file's name contains invalid characters
                if(fileName.contains("..")) {
                    throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
                }

                // Copy file to the target location (Replacing existing file with the same name)
                Path targetLocation = this.fileStorageLocation.resolve(fileName);
                Path targetLocationEncrypted = this.fileStorageLocation.resolve(fileNameEncrypted);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                //crypt file
                /*PdfReader pdfReader = new PdfReader(targetLocation.toString());
                PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(targetLocationEncrypted.toString()));
                pdfStamper.setEncryption("userpass".getBytes(),"ownerpass".getBytes(), 0, PdfWriter.ENCRYPTION_AES_256);
                pdfStamper.close();*/
                cvFile.setUrl(targetLocation.toString());
                cv.setUrlAllegato(targetLocation.toString());
			} catch (IOException /* | DocumentException */ ex) {
            	log.error(ex.getMessage());
                throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
            }
    		//////////////////////////////////////////////////////////////
    		cv.addCurriculumfile(cvFile);
    		anagraficaCandidato.setCurricula(curricula);
        }
        anagraficaCandidato = anagraficaCandidatoRepository.save(anagraficaCandidato);
        return anagraficaCandidatoMapper.toDto(anagraficaCandidato);
	}
	
    /**
     * Save a anagraficaCandidato.
     *
     * @param anagraficaCandidatoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AnagraficaCandidatoDTO save(AnagraficaCandidatoDTO anagraficaCandidatoDTO) {
        log.debug("Request to save AnagraficaCandidato : {}", anagraficaCandidatoDTO);
        Optional<AnagraficaCandidato> existingAnagrafica;// = anagraficaCandidatoRepository.findOneByIndirizzoPecIgnoreCase(anagraficaCandidatoDTO.getIndirizzoPec());
        if(anagraficaCandidatoDTO.getIndirizzoPec() != null && anagraficaCandidatoDTO.getIndirizzoPec() != "") {
	        existingAnagrafica = anagraficaCandidatoRepository.findOneByIndirizzoPecIgnoreCase(attributeEncryptor.convertToDatabaseColumn(anagraficaCandidatoDTO.getIndirizzoPec()));
	        if (anagraficaCandidatoDTO.getId() == null && existingAnagrafica.isPresent()) {
	        	throw new IndirizzoPecAlreadyUsedException();
	        } 
	        if (existingAnagrafica.isPresent() && (!existingAnagrafica.get().getId().equals(anagraficaCandidatoDTO.getId()))) {
	        	throw new IndirizzoPecAlreadyUsedException();
	        }
        }
        if (anagraficaCandidatoDTO.getPartitaIva() != null && anagraficaCandidatoDTO.getPartitaIva() != "") {
	        existingAnagrafica = anagraficaCandidatoRepository.findOneByPartitaIvaIgnoreCase(anagraficaCandidatoDTO.getPartitaIva());
	        if (anagraficaCandidatoDTO.getId() == null && existingAnagrafica.isPresent()) {
	        	throw new PartitaIvaAlreadyUsedException();
	        } 
	        if (existingAnagrafica.isPresent() && (!existingAnagrafica.get().getId().equals(anagraficaCandidatoDTO.getId()))) {
	        	throw new PartitaIvaAlreadyUsedException();
	        }
        }
        AnagraficaCandidato anagraficaCandidato = anagraficaCandidatoMapper.toEntity(anagraficaCandidatoDTO);
		for (Iterator<CompetenzeLng> iterator =  anagraficaCandidato.getCompetenzeLngs().iterator(); iterator.hasNext();) {
			CompetenzeLng type = (CompetenzeLng) iterator.next();
			type.setAnagrafica(anagraficaCandidato);
	        log.debug("lingua: "+type.getLingua());
	        log.debug("livello: "+type.getLivello());
		}        
		for (Iterator<Competenza> iterator =  anagraficaCandidato.getCompetenzas().iterator(); iterator.hasNext();) {
			Competenza type = (Competenza) iterator.next();
			type.setAnagrafica(anagraficaCandidato); 
		}
		for (Iterator<TitoloStudio> iterator =  anagraficaCandidato.getTitoloStudios().iterator(); iterator.hasNext();) {
			TitoloStudio type = (TitoloStudio) iterator.next();
			type.setAnagrafica(anagraficaCandidato); 
		}
		for (Iterator<DichiarazioniObligatorie> iterator =  anagraficaCandidato.getDichiarazionis().iterator(); iterator.hasNext();) {
			DichiarazioniObligatorie type = (DichiarazioniObligatorie) iterator.next();
			type.setAnagrafica(anagraficaCandidato); 
		}
        Long candidatoId = anagraficaCandidatoDTO.getCandidatoId();
        candidatoRepository.findById(candidatoId).ifPresent(anagraficaCandidato::candidato);
        anagraficaCandidato = anagraficaCandidatoRepository.save(anagraficaCandidato);
        return anagraficaCandidatoMapper.toDto(anagraficaCandidato);
    }
    
    /**
     * Get all the anagraficaCandidatoes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AnagraficaCandidatoDTO> ricercaIscritti(String nome, String cognome,Pageable pageable) {
        log.debug("Request to get iscritti: "+nome+cognome);
        return anagraficaCandidatoRepository.ricercaIscritti(nome, cognome, pageable)
            .map(anagraficaCandidatoMapper::toDto);
    }
    
    /**
     * Get all the anagraficaCandidatoes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AnagraficaCandidatoDTO> findByNome(String nome, String cognome,Pageable pageable) {
        log.debug("Request to get all AnagraficaCandidatoes: "+nome+cognome);
        return anagraficaCandidatoRepository.findByNome(nome, pageable)
            .map(anagraficaCandidatoMapper::toDto);
    }

    /**
     * Get all the anagraficaCandidatoes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AnagraficaCandidatoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnagraficaCandidatoes");
        return anagraficaCandidatoRepository.findAll(pageable)
            .map(anagraficaCandidatoMapper::toDto);
    }

    /**
     * Get one anagraficaCandidato by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AnagraficaCandidatoDTO> findOne(Long id) {
        log.debug("Request to get AnagraficaCandidato : {}", id);
        return anagraficaCandidatoRepository.findById(id)
            .map(anagraficaCandidatoMapper::toDto);
    }
    
    /**
     * Get one anagraficaCandidato by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AnagraficaCandidatoDTO> findOne2(Long id) {
        log.debug("Request to get AnagraficaCandidato : {}", id);
        return anagraficaCandidatoRepository.findById(id).map(this::toDto);
    }

    /**
     * Delete the anagraficaCandidato by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnagraficaCandidato : {}", id);
        anagraficaCandidatoRepository.deleteById(id);
    }

	@Override
	public Page<AnagraficaCandidatoDTO> findByCriteria(SearchParam criteria, Pageable pageable) {
        log.debug("Request to get iscritti: "+criteria.getNome()+criteria.getCognome());
        return anagraficaCandidatoRepository.ricercaIscritti(criteria.getNome(), criteria.getCognome(), pageable)
            .map(anagraficaCandidatoMapper::toDto);
	}

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    // @Scheduled(cron = "* 0/5 * * * ?")
    public void emailNotActiveUsers() {
    	Instant emailTime = ZonedDateTime.now().minusMonths(35).toInstant();
    	log.debug("Check for inactive users one month before: {}",emailTime);
    	anagraficaCandidatoRepository
            .findAllByLastModifiedDateBeforeAndCandidatoPrimoAvvisoIsFalse(emailTime)
            .forEach(scheda -> {
                Candidato candidato = scheda.getCandidato();
                candidato.setPrimoAvviso(true);
                log.debug("Invio email un mese prima utente {}", candidato.getNome());
                mailService.sendOneMonthEmail(candidato);
            });
    }
    
    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    // @Scheduled(cron = "* 0/5 * * * ?")
    public void emailNotActiveUsers2() {
    	Instant emailTime = ZonedDateTime.now().minusYears(3).plusDays(15).toInstant();
    	log.debug("Check for inactive users 15 days before: {}",emailTime);
    	anagraficaCandidatoRepository
            .findAllByLastModifiedDateBeforeAndCandidatoSecondoAvvisoIsFalse(emailTime)
            .forEach(scheda -> {
                Candidato candidato = scheda.getCandidato();
                candidato.setSecondoAvviso(true);
                log.debug("Invio email 15 giorni prima utente {}", candidato.getNome());
                mailService.sendHalfMonthEmail(candidato);
        });
    }
    
    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    // @Scheduled(cron = "* 0/5 * * * ?")
    public void removeNotActiveUsers() {
    	Instant emailTime = ZonedDateTime.now().minusYears(3).toInstant();
    	log.debug("Check for inactive users 3 years before: {}",emailTime);
    	anagraficaCandidatoRepository
            .findAllByLastModifiedDateBeforeAndCandidatoStatoIdEquals(ZonedDateTime.now().minusYears(3).toInstant(),new Long(1))
            .forEach(scheda -> {
                Candidato candidato = scheda.getCandidato();
                log.debug("Set status inactive for user {}", candidato.getNome());
                StatoRegistrazione stato = candidato.getStato();
                stato.removeCandidato(candidato);
                stato = new StatoRegistrazione();
                stato.setId(new Long(2));
                candidato.setStato(stato);
                candidato.setActivated(false);
            });
    }

	@Override
	public ByteArrayInputStream findByCriteriaToExcel(AnagraficaCandidatoCriteria criteria) {
		List<AnagraficaCandidato> risultatoRicerca = anagraficaCandidatoQueryService.findByCriteria2(criteria);
		Map<String, String> searchParam = anagraficaCandidatoQueryService.getSearchCriteria(criteria);
		ByteArrayInputStream inputSstram = ExcelHelper.risultatoRicercaToExcel(risultatoRicerca,searchParam);
		return inputSstram;
	}
	
    public AnagraficaCandidatoDTO toDto(AnagraficaCandidato anagraficaCandidato) {
    	log.debug("Mapping AnagraficaCandidato > AnagraficaCandidatoDTO");
        if ( anagraficaCandidato == null ) {
            return null;
        }

        AnagraficaCandidatoDTO anagraficaCandidatoDTO = new AnagraficaCandidatoDTO();

        anagraficaCandidatoDTO.setCandidato( anagraficaCandidato.getCandidato() );
        anagraficaCandidatoDTO.setCandidatoId( anagraficaCandidatoCandidatoId( anagraficaCandidato ) );
        anagraficaCandidatoDTO.setId( anagraficaCandidato.getId() );
        anagraficaCandidatoDTO.setNome( anagraficaCandidato.getNome() );
        anagraficaCandidatoDTO.setCognome( anagraficaCandidato.getCognome() );
        anagraficaCandidatoDTO.setLuogoNascita( anagraficaCandidato.getLuogoNascita() );
        anagraficaCandidatoDTO.setDataNascita( anagraficaCandidato.getDataNascita() );
        anagraficaCandidatoDTO.setProfessione( anagraficaCandidato.getProfessione() );
        anagraficaCandidatoDTO.setPartitaIva( anagraficaCandidato.getPartitaIva() );
        anagraficaCandidatoDTO.setNumeroTelefonoFisso( anagraficaCandidato.getNumeroTelefonoFisso() );
        anagraficaCandidatoDTO.setNumeroTelefonoCellulare( anagraficaCandidato.getNumeroTelefonoCellulare() );
        anagraficaCandidatoDTO.setIndirizzoPec( anagraficaCandidato.getIndirizzoPec() );
        anagraficaCandidatoDTO.setIndirizzoResidenza( anagraficaCandidato.getIndirizzoResidenza() );
        anagraficaCandidatoDTO.setCapResidenza( anagraficaCandidato.getCapResidenza() );
        anagraficaCandidatoDTO.setComuneResidenza( anagraficaCandidato.getComuneResidenza() );
        anagraficaCandidatoDTO.setProvinciaResidenza( anagraficaCandidato.getProvinciaResidenza() );
        anagraficaCandidatoDTO.setNote( anagraficaCandidato.getNote() );
        Set<Competenza> set = anagraficaCandidato.getCompetenzas();
        if ( set != null ) {
            anagraficaCandidatoDTO.setCompetenzas( new HashSet<Competenza>( set ) );
        }
        Set<TitoloStudio> set1 = anagraficaCandidato.getTitoloStudios();
        if ( set1 != null ) {
            anagraficaCandidatoDTO.setTitoloStudios( new HashSet<TitoloStudio>( set1 ) );
        }
        Set<CompetenzeLng> set2 = anagraficaCandidato.getCompetenzeLngs();
        if ( set2 != null ) {
            anagraficaCandidatoDTO.setCompetenzeLngs( new HashSet<CompetenzeLng>( set2 ) );
        }
        Set<Curriculum> set3 = anagraficaCandidato.getCurricula();
        if ( set3 != null ) {
            anagraficaCandidatoDTO.setCurricula( new HashSet<Curriculum>( set3 ) );
        }
        anagraficaCandidatoDTO.setLinguaita( anagraficaCandidato.getLinguaita() );
        anagraficaCandidatoDTO.setLinguaitacheck( anagraficaCandidato.isLinguaitacheck() );
        anagraficaCandidatoDTO.setSpecializzazioneMaster( anagraficaCandidato.isSpecializzazioneMaster() );
        anagraficaCandidatoDTO.setIscrizioneAlbo( anagraficaCandidato.isIscrizioneAlbo() );
        anagraficaCandidatoDTO.setOperatoreCampo( anagraficaCandidato.isOperatoreCampo() );
        anagraficaCandidatoDTO.setEsperienzaBiennale( anagraficaCandidato.isEsperienzaBiennale() );
        Set<DichiarazioniObligatorie> set4 = anagraficaCandidato.getDichiarazionis();
        if ( set4 != null ) {
            anagraficaCandidatoDTO.setDichiarazionis( new LinkedHashSet<DichiarazioniObligatorie>(set4) );
        }

        return anagraficaCandidatoDTO;
    }
    
    private Long anagraficaCandidatoCandidatoId(AnagraficaCandidato anagraficaCandidato) {
        if ( anagraficaCandidato == null ) {
            return null;
        }
        Candidato candidato = anagraficaCandidato.getCandidato();
        if ( candidato == null ) {
            return null;
        }
        Long id = candidato.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

}
