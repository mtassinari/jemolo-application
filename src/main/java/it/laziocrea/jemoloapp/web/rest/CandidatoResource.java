package it.laziocrea.jemoloapp.web.rest;

import it.laziocrea.jemoloapp.domain.Candidato;
import it.laziocrea.jemoloapp.domain.User;
import it.laziocrea.jemoloapp.file.helper.ExcelHelper;
import it.laziocrea.jemoloapp.repository.CandidatoRepository;
import it.laziocrea.jemoloapp.repository.CustomAuditEventRepository;
import it.laziocrea.jemoloapp.security.SecurityUtils;
import it.laziocrea.jemoloapp.service.CandidatoService;
import it.laziocrea.jemoloapp.service.MailService2;
import it.laziocrea.jemoloapp.web.rest.errors.BadRequestAlertException;
import it.laziocrea.jemoloapp.web.rest.errors.CodiceFiscaleAlreadyUsedException;
import it.laziocrea.jemoloapp.web.rest.errors.EmailAlreadyUsedException;
import it.laziocrea.jemoloapp.web.rest.errors.LoginAlreadyUsedException;
import it.laziocrea.jemoloapp.service.dto.CandidatoDTO;
import it.laziocrea.jemoloapp.service.dto.CurriculumDTO;
import it.laziocrea.jemoloapp.service.dto.IscrittoDTO;
import it.laziocrea.jemoloapp.service.mapper.CandidatoMapper;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link it.laziocrea.jemoloapp.domain.Candidato}.
 */
@RestController
@RequestMapping("/api")
public class CandidatoResource {

    private final Logger log = LoggerFactory.getLogger(CandidatoResource.class);

    private static final String ENTITY_NAME = "candidato";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CandidatoService candidatoService;
    private final CandidatoRepository candidatoRepository;
    private final MailService2 mailService;
    private final CandidatoMapper candidatoMapper;
    private final CustomAuditEventRepository eventRepository;
    
    public CandidatoResource(CustomAuditEventRepository eventRepository, CandidatoMapper candidatoMapper, MailService2 mailService, CandidatoRepository candidatoRepository, CandidatoService candidatoService) {
        this.candidatoService = candidatoService;
        this.candidatoRepository = candidatoRepository;
        this.mailService = mailService;
        this.candidatoMapper = candidatoMapper;
        this.eventRepository = eventRepository;
    }
    
    /**
     * {@code POST  /candidati} : Create a new candidato.
     *
     * @param candidatoDTO the candidatoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new candidatoDTO, or with status {@code 400 (Bad Request)} if the candidato has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/candidati")
    public ResponseEntity<CandidatoDTO> createCandidato(@Valid @RequestBody CandidatoDTO candidatoDTO) throws URISyntaxException {
        log.debug("REST request to save Candidato : {}", candidatoDTO);
        
        if (candidatoDTO.getId() != null) {
            throw new BadRequestAlertException("A new candidato cannot already have an ID", ENTITY_NAME, "idexists");
        } else if (candidatoRepository.findOneByLogin(candidatoDTO.getLogin().toLowerCase()).isPresent()) {
        	throw new LoginAlreadyUsedException();
        } else if (candidatoRepository.findOneByEmailIgnoreCase(candidatoDTO.getEmail()).isPresent()) {
        	throw new EmailAlreadyUsedException();
        } else if (candidatoRepository.findOneByCodiceFiscaleIgnoreCase(candidatoDTO.getCodiceFiscale()).isPresent()) {
        	throw new CodiceFiscaleAlreadyUsedException();
        } else {
        	Candidato result = candidatoService.save(candidatoDTO);
        	mailService.sendCreationEmail(result);
        	CandidatoDTO candidato = candidatoMapper.toDto(result);
        	return ResponseEntity.created(new URI("/api/candidati/" + result.getId()))
    			.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
    			.body(candidato);
        }
    }

    /**
     * {@code PUT  /candidati} : Updates an existing candidato.
     *
     * @param candidatoDTO the candidatoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated candidatoDTO,
     * or with status {@code 400 (Bad Request)} if the candidatoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the candidatoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/candidati")
    public ResponseEntity<CandidatoDTO> updateCandidato(@Valid @RequestBody CandidatoDTO candidatoDTO) throws URISyntaxException {
        log.debug("REST request to update Candidato : {}", candidatoDTO);
        if (candidatoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<Candidato> existingCandidato = candidatoRepository.findOneByEmailIgnoreCase(candidatoDTO.getEmail());
        if (existingCandidato.isPresent() && (!existingCandidato.get().getId().equals(candidatoDTO.getId()))) {
            throw new EmailAlreadyUsedException();
        }
        existingCandidato = candidatoRepository.findOneByLogin(candidatoDTO.getLogin().toLowerCase());
        if (existingCandidato.isPresent() && (!existingCandidato.get().getId().equals(candidatoDTO.getId()))) {
            throw new LoginAlreadyUsedException();
        }        
        existingCandidato = candidatoRepository.findOneByCodiceFiscaleIgnoreCase(candidatoDTO.getCodiceFiscale());
        if (existingCandidato.isPresent() && (!existingCandidato.get().getId().equals(candidatoDTO.getId()))) {
            throw new CodiceFiscaleAlreadyUsedException();
        }
        
        CandidatoDTO result = candidatoService.update(candidatoDTO).get();
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, candidatoDTO.getId().toString()))
            .body(result);
    }
    
    /**
     * {@code PUT  /candidati} : Updates an existing candidato.
     *
     * @param candidatoDTO the candidatoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated candidatoDTO,
     * or with status {@code 400 (Bad Request)} if the candidatoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the candidatoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/candidati/valid")
    public ResponseEntity<CandidatoDTO> updateValid(@Valid @RequestBody IscrittoDTO iscrittoDTO,HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to update Candidato : {}", iscrittoDTO);
        Optional<CandidatoDTO> result = candidatoService.update(iscrittoDTO);
        result.ifPresent(iscritto -> {
        	AuditEvent event = new AuditEvent(SecurityUtils.getCurrentUserLogin().get(), 
        			"ACTIVATION_STATE_MODIFIED",
        			"message=E' stato modificato lo stato di registrazione e attivazione, ",
        			"remoteAddress="+request.getRemoteAddr(),
        			"codice_fiscale=codice fiscale: "+iscrittoDTO.getCodiceFiscale(),
        			"utenza=utenza: "+iscrittoDTO.getLogin(),
        			"stato_attivazione=stato attivazione: "+iscrittoDTO.isActivated(),
        			"stato_registrazione=stato registrazione: "+iscrittoDTO.getStatoRegistrazioneId());
        	eventRepository.add(event);
        });
        /*return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, iscrittoDTO.getId().toString()))
            .body(result);*/
        return ResponseUtil.wrapOrNotFound(result,
                HeaderUtil.createAlert(applicationName, "userManagement.updated", iscrittoDTO.getLogin()));
    }
    
    /**
     * {@code GET  /candidati} : get all the candidati.
     *

     * @param pageable the pagination information.

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of candidati in body.
     */
    @GetMapping("/candidati")
    public ResponseEntity<List<CandidatoDTO>> getAllCandidatoes(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("anagraficacandidato-is-null".equals(filter)) {
            log.debug("REST request to get all Candidatos where anagraficaCandidato is null");
            return new ResponseEntity<>(candidatoService.findAllWhereAnagraficaCandidatoIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of candidati");
        Page<CandidatoDTO> page = candidatoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    /**
     * {@code GET  /iscritti} : get all the candidati.
     *

     * @param pageable the pagination information.

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of candidati in body.
     */
    @GetMapping("/iscrittiall")
    public ResponseEntity<List<IscrittoDTO>> getAllIscritti(Pageable pageable, @RequestParam(required = false) String filter) {
        log.debug("REST request to get a page of iscritti");
        Page<IscrittoDTO> page = candidatoService.findAllIscritti(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    /**
     * {@code GET  /iscritti} : get all the candidati.
     *

     * @param pageable the pagination information.

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of candidati in body.
     */
    @GetMapping("/public/iscrittiall")
    public ResponseEntity<List<IscrittoDTO>> getAllIscrittiPublic(Pageable pageable, @RequestParam(required = false) String filter) {
        log.debug("REST request to get a page of iscritti");
        Page<IscrittoDTO> page = candidatoService.publicFindAllIscrittiAttivi(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    /**
     * 
     * @return
     */
    @GetMapping("/iscrittiall2/excel-download")
    public ResponseEntity<Resource> getFile() {
      String filename = "iscritti_roster.xlsx";
      InputStreamResource file = new InputStreamResource(candidatoService.loadAlliscritti());

      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
          .contentType(MediaType.parseMediaType(ExcelHelper.TYPE))
          .body(file);
    }
    
    /**
     * 
     * @param response
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    @GetMapping(value="/iscrittiall/excel-download", produces="application/vnd.ms-excel")
    public Resource getAllIscrittiExcel(HttpServletResponse response) throws URISyntaxException, IOException {
    // public ResponseEntity<Resource> getAllIscrittiExcel() {
        String filename = "iscritti_roster.xlsx";
        InputStreamResource file = new InputStreamResource(candidatoService.loadAlliscritti());
        response.setHeader("filename", filename);
    	response.setStatus(HttpServletResponse.SC_OK);
       	response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
       	response.setContentType(ExcelHelper.TYPE);
    	return file;
        /*return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);*/
    }
   
    /**
     * {@code GET  /candidati/:id} : get the "id" candidato.
     *
     * @param id the id of the candidatoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the candidatoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/candidati/{id}")
    public ResponseEntity<CandidatoDTO> getCandidato(@PathVariable Long id) {
        log.debug("REST request to get Candidato : {}", id);
        Optional<CandidatoDTO> candidatoDTO = candidatoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(candidatoDTO);
    }

    /**
     * {@code DELETE  /candidati/:id} : delete the "id" candidato.
     *
     * @param id the id of the candidatoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/candidati/{id}")
    public ResponseEntity<Void> deleteCandidato(@PathVariable Long id) {
        log.debug("REST request to delete Candidato : {}", id);
        candidatoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
