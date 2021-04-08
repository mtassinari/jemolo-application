package it.laziocrea.jemoloapp.web.rest;

import it.laziocrea.jemoloapp.service.LinguaService;
import it.laziocrea.jemoloapp.web.rest.errors.BadRequestAlertException;
import it.laziocrea.jemoloapp.service.dto.LinguaDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link it.laziocrea.jemolorooster.domain.Lingua}.
 */
@RestController
@RequestMapping("/api")
public class LinguaResource {

    private final Logger log = LoggerFactory.getLogger(LinguaResource.class);

    private static final String ENTITY_NAME = "lingua";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LinguaService linguaService;

    public LinguaResource(LinguaService linguaService) {
        this.linguaService = linguaService;
    }

    /**
     * {@code POST  /linguas} : Create a new lingua.
     *
     * @param linguaDTO the linguaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new linguaDTO, or with status {@code 400 (Bad Request)} if the lingua has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/linguas")
    public ResponseEntity<LinguaDTO> createLingua(@Valid @RequestBody LinguaDTO linguaDTO) throws URISyntaxException {
        log.debug("REST request to save Lingua : {}", linguaDTO);
        if (linguaDTO.getId() != null) {
            throw new BadRequestAlertException("A new lingua cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LinguaDTO result = linguaService.save(linguaDTO);
        return ResponseEntity.created(new URI("/api/linguas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /linguas} : Updates an existing lingua.
     *
     * @param linguaDTO the linguaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated linguaDTO,
     * or with status {@code 400 (Bad Request)} if the linguaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the linguaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/linguas")
    public ResponseEntity<LinguaDTO> updateLingua(@Valid @RequestBody LinguaDTO linguaDTO) throws URISyntaxException {
        log.debug("REST request to update Lingua : {}", linguaDTO);
        if (linguaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LinguaDTO result = linguaService.save(linguaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, linguaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /linguas} : get all the linguas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of linguas in body.
     */
    @GetMapping("/linguas")
    public List<LinguaDTO> getAllLinguas() {
        log.debug("REST request to get all Linguas");
        return linguaService.findAll();
    }

    /**
     * {@code GET  /linguas} : get all the linguas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of linguas in body.
     */
    @GetMapping("/v2/linguas")
    public List<LinguaDTO> getAllLinguasV2() {
        log.debug("REST request to get all Linguas");
        return linguaService.findAll();
    }
    
    /**
     * {@code GET  /linguas} : get all the linguas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of linguas in body.
     */
    @GetMapping("/v2/linguas/ue")
    public List<LinguaDTO> getAllUe() {
        log.debug("REST request to get all Linguas");
        return linguaService.findAllUe();
    }

    /**
     * {@code GET  /linguas/:id} : get the "id" lingua.
     *
     * @param id the id of the linguaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the linguaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/linguas/{id}")
    public ResponseEntity<LinguaDTO> getLingua(@PathVariable Long id) {
        log.debug("REST request to get Lingua : {}", id);
        Optional<LinguaDTO> linguaDTO = linguaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(linguaDTO);
    }

    /**
     * {@code DELETE  /linguas/:id} : delete the "id" lingua.
     *
     * @param id the id of the linguaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/linguas/{id}")
    public ResponseEntity<Void> deleteLingua(@PathVariable Long id) {
        log.debug("REST request to delete Lingua : {}", id);
        linguaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
