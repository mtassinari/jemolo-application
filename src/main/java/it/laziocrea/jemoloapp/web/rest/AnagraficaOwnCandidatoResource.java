package it.laziocrea.jemoloapp.web.rest;

import it.laziocrea.jemoloapp.domain.AnagraficaCandidato;
import it.laziocrea.jemoloapp.domain.Candidato;
import it.laziocrea.jemoloapp.domain.DichiarazioniObligatorie;
import it.laziocrea.jemoloapp.security.SecurityUtils;
import it.laziocrea.jemoloapp.service.AnagraficaCandidatoService;
import it.laziocrea.jemoloapp.service.MailService;
import it.laziocrea.jemoloapp.service.MailService2;
import it.laziocrea.jemoloapp.web.rest.errors.BadRequestAlertException;
import it.laziocrea.jemoloapp.web.rest.errors.BadRequestAlertException2;
import it.laziocrea.jemoloapp.service.dto.AnagraficaCandidatoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.micrometer.core.annotation.Timed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing {@link it.laziocrea.jemoloapp.domain.AnagraficaCandidato}.
 */
@RestController
@RequestMapping("/api")
public class AnagraficaOwnCandidatoResource {

    private final Logger log = LoggerFactory.getLogger(AnagraficaOwnCandidatoResource.class);

    private static final String ENTITY_NAME = "anagraficaCandidato";
    
    private final MailService2 mailService;
    
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnagraficaCandidatoService anagraficaCandidatoService;

    public AnagraficaOwnCandidatoResource(MailService2 mailService, AnagraficaCandidatoService anagraficaCandidatoService) {
        this.anagraficaCandidatoService = anagraficaCandidatoService;
        this.mailService = mailService;
    }

    /**
     * {@code POST  /anagrafica-candidatoes} : Create a new anagraficaCandidato.
     *
     * @param anagraficaCandidatoDTO the anagraficaCandidatoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anagraficaCandidatoDTO, or with status {@code 400 (Bad Request)} if the anagraficaCandidato has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/v2/anagrafica-candidatoes")
    @Timed
    public ResponseEntity<AnagraficaCandidatoDTO> createAnagraficaCandidato(@Valid @RequestPart AnagraficaCandidatoDTO anagraficaCandidatoDTO, @RequestPart List<MultipartFile> files) throws URISyntaxException {
        log.debug("REST request to save AnagraficaCandidato with files: {}", anagraficaCandidatoDTO);
        if (anagraficaCandidatoDTO.getId() != null) {
            throw new BadRequestAlertException("A new anagraficaCandidato cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(anagraficaCandidatoDTO.getCandidatoId())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        AnagraficaCandidatoDTO result = anagraficaCandidatoService.save(anagraficaCandidatoDTO, files);
        mailService.sendSchedaCreationEmail(result);
        return ResponseEntity.created(new URI("/api/own/anagrafica-candidatoes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code POST  /own/anagrafica-candidatoes} : Create a new anagraficaCandidato.
     *
     * @param anagraficaCandidatoDTO the anagraficaCandidatoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anagraficaCandidatoDTO, or with status {@code 400 (Bad Request)} if the anagraficaCandidato has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/own/anagrafica-candidatoes")
    public ResponseEntity<AnagraficaCandidatoDTO> createOwnAnagraficaCandidato(@Valid @RequestBody AnagraficaCandidatoDTO anagraficaCandidatoDTO) throws URISyntaxException {
        log.debug("REST request to save AnagraficaCandidato : {}", anagraficaCandidatoDTO);
        if (anagraficaCandidatoDTO.getId() != null) {
            throw new BadRequestAlertException("A new anagraficaCandidato cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(anagraficaCandidatoDTO.getCandidatoId())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        AnagraficaCandidatoDTO result = anagraficaCandidatoService.save(anagraficaCandidatoDTO);
        return ResponseEntity.created(new URI("/api/anagrafica-candidatoes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /own/anagrafica-candidatoes} : Updates an existing anagraficaCandidato.
     *
     * @param anagraficaCandidatoDTO the anagraficaCandidatoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anagraficaCandidatoDTO,
     * or with status {@code 400 (Bad Request)} if the anagraficaCandidatoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anagraficaCandidatoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/own/anagrafica-candidatoes")
    public ResponseEntity<AnagraficaCandidatoDTO> updateOwnAnagraficaCandidato(@Valid @RequestBody AnagraficaCandidatoDTO anagraficaCandidatoDTO) throws URISyntaxException {
        log.debug("REST request to update AnagraficaCandidato : {}", anagraficaCandidatoDTO);
        if (anagraficaCandidatoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnagraficaCandidatoDTO result = anagraficaCandidatoService.save(anagraficaCandidatoDTO);
        mailService.sendSchedaUpdateEmail(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anagraficaCandidatoDTO.getId().toString()))
            .body(result);
    }
    
    /**
     * {@code PUT  /v2/anagrafica-candidatoes} : Updates an existing anagraficaCandidato.
     *
     * @param anagraficaCandidatoDTO the anagraficaCandidatoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anagraficaCandidatoDTO,
     * or with status {@code 400 (Bad Request)} if the anagraficaCandidatoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anagraficaCandidatoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/v2/anagrafica-candidatoes")
    public ResponseEntity<AnagraficaCandidatoDTO> updateAnagraficaCandidato(@Valid @RequestPart AnagraficaCandidatoDTO anagraficaCandidatoDTO, @RequestPart List<MultipartFile> files) throws URISyntaxException {
        log.debug("REST request to update AnagraficaCandidato with files:"+files.size()+" {}", anagraficaCandidatoDTO);
        if (anagraficaCandidatoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnagraficaCandidatoDTO result = anagraficaCandidatoService.save(anagraficaCandidatoDTO, files);
        mailService.sendSchedaUpdateEmail(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anagraficaCandidatoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /own/anagrafica-candidatoes} : get all the anagraficaCandidatoes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anagraficaCandidatoes in body.
     */
    @GetMapping("/own/anagrafica-candidatoes")
    public ResponseEntity<List<AnagraficaCandidatoDTO>> getAllAnagraficaCandidatoes(Pageable pageable) {
        log.debug("REST request to get a page of AnagraficaCandidatoes");
        Page<AnagraficaCandidatoDTO> page = anagraficaCandidatoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /own/anagrafica-candidatoes/:id} : get the "id" anagraficaCandidato.
     *
     * @param id the id of the anagraficaCandidatoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anagraficaCandidatoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/own/anagrafica-candidatoes/{id}")
    public ResponseEntity<AnagraficaCandidatoDTO> getAnagraficaCandidato(@PathVariable Long id) {
        log.debug("REST request to get AnagraficaCandidato : {}", id);
        Optional<AnagraficaCandidatoDTO> anagraficaCandidatoDTO = anagraficaCandidatoService.findOne(id);
        // Return 404 if the entity is not owned by the connected user
        Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();
        if (anagraficaCandidatoDTO.isPresent() &&
            userLogin.isPresent() &&
            userLogin.get().equals(anagraficaCandidatoDTO.get().getCandidato().getLogin())) {
            return ResponseUtil.wrapOrNotFound(anagraficaCandidatoDTO);
        } else {
            return ResponseEntity.notFound().build();
            // throw new BadRequestAlertException2("Not authorized to view this enthity", ENTITY_NAME, "403");
        }
       
        // return ResponseUtil.wrapOrNotFound(anagraficaCandidatoDTO);
    }
    
    /**
     * {@code GET  /own/anagrafica-candidatoes/:id} : get the "id" anagraficaCandidato.
     *
     * @param id the id of the anagraficaCandidatoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anagraficaCandidatoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/own/anagrafica-candidatoes2/{id}")
    public ResponseEntity<AnagraficaCandidatoDTO> getAnagraficaCandidato2(@PathVariable Long id) {
        log.debug("REST request to get AnagraficaCandidato : {}", id);
        Optional<AnagraficaCandidatoDTO> anagraficaCandidato = anagraficaCandidatoService.findOne2(id);
        // Return 404 if the entity is not owned by the connected user
        Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();
        if (anagraficaCandidato.isPresent() &&
            userLogin.isPresent() &&
            userLogin.get().equals(anagraficaCandidato.get().getCandidato().getLogin())) {
            return ResponseUtil.wrapOrNotFound(anagraficaCandidato);
        } else {
            return ResponseEntity.notFound().build();
            // throw new BadRequestAlertException2("Not authorized to view this enthity", ENTITY_NAME, "403");
        }
       
        // return ResponseUtil.wrapOrNotFound(anagraficaCandidatoDTO);
    }
    
    /**
     * {@code GET  /own/anagrafica-candidatoes/:id} : get the "id" anagraficaCandidato.
     *
     * @param id the id of the anagraficaCandidatoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anagraficaCandidatoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/own/anagrafica-candidatoes-check/{id}")
    public ResponseEntity<AnagraficaCandidatoDTO> getAnagraficaCandidatoCheck(@PathVariable Long id) {
        log.debug("REST request to get AnagraficaCandidato : {}", id);
        Optional<AnagraficaCandidatoDTO> anagraficaCandidatoDTO = anagraficaCandidatoService.findOne(id);
        // Return 404 if the entity is not owned by the connected user
        Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();
        if (anagraficaCandidatoDTO.isPresent() &&
            userLogin.isPresent() &&
            userLogin.get().equals(anagraficaCandidatoDTO.get().getCandidato().getLogin())) {
            return ResponseUtil.wrapOrNotFound(anagraficaCandidatoDTO);
        } else {
            return ResponseEntity.noContent().build();
            // throw new BadRequestAlertException2("Not authorized to view this enthity", ENTITY_NAME, "403");
        }
       
        // return ResponseUtil.wrapOrNotFound(anagraficaCandidatoDTO);
    }
}
