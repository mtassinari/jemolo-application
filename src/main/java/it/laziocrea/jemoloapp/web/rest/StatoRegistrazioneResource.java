package it.laziocrea.jemoloapp.web.rest;

import it.laziocrea.jemoloapp.service.StatoRegistrazioneService;
import it.laziocrea.jemoloapp.web.rest.errors.BadRequestAlertException;
import it.laziocrea.jemoloapp.service.dto.StatoRegistrazioneDTO;

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
 * REST controller for managing {@link it.laziocrea.jemolorooster.domain.StatoRegistrazione}.
 */
@RestController
@RequestMapping("/api")
public class StatoRegistrazioneResource {

    private final Logger log = LoggerFactory.getLogger(StatoRegistrazioneResource.class);

    private static final String ENTITY_NAME = "statoRegistrazione";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatoRegistrazioneService statoRegistrazioneService;

    public StatoRegistrazioneResource(StatoRegistrazioneService statoRegistrazioneService) {
        this.statoRegistrazioneService = statoRegistrazioneService;
    }

    /**
     * {@code POST  /stato-registraziones} : Create a new statoRegistrazione.
     *
     * @param statoRegistrazioneDTO the statoRegistrazioneDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statoRegistrazioneDTO, or with status {@code 400 (Bad Request)} if the statoRegistrazione has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stato-registraziones")
    public ResponseEntity<StatoRegistrazioneDTO> createStatoRegistrazione(@Valid @RequestBody StatoRegistrazioneDTO statoRegistrazioneDTO) throws URISyntaxException {
        log.debug("REST request to save StatoRegistrazione : {}", statoRegistrazioneDTO);
        if (statoRegistrazioneDTO.getId() != null) {
            throw new BadRequestAlertException("A new statoRegistrazione cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StatoRegistrazioneDTO result = statoRegistrazioneService.save(statoRegistrazioneDTO);
        return ResponseEntity.created(new URI("/api/stato-registraziones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stato-registraziones} : Updates an existing statoRegistrazione.
     *
     * @param statoRegistrazioneDTO the statoRegistrazioneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statoRegistrazioneDTO,
     * or with status {@code 400 (Bad Request)} if the statoRegistrazioneDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statoRegistrazioneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stato-registraziones")
    public ResponseEntity<StatoRegistrazioneDTO> updateStatoRegistrazione(@Valid @RequestBody StatoRegistrazioneDTO statoRegistrazioneDTO) throws URISyntaxException {
        log.debug("REST request to update StatoRegistrazione : {}", statoRegistrazioneDTO);
        if (statoRegistrazioneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StatoRegistrazioneDTO result = statoRegistrazioneService.save(statoRegistrazioneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statoRegistrazioneDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stato-registraziones} : get all the statoRegistraziones.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statoRegistraziones in body.
     */
    @GetMapping("/stato-registraziones")
    public List<StatoRegistrazioneDTO> getAllStatoRegistraziones() {
        log.debug("REST request to get all StatoRegistraziones");
        return statoRegistrazioneService.findAll();
    }

    /**
     * {@code GET  /stato-registraziones/:id} : get the "id" statoRegistrazione.
     *
     * @param id the id of the statoRegistrazioneDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statoRegistrazioneDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stato-registraziones/{id}")
    public ResponseEntity<StatoRegistrazioneDTO> getStatoRegistrazione(@PathVariable Long id) {
        log.debug("REST request to get StatoRegistrazione : {}", id);
        Optional<StatoRegistrazioneDTO> statoRegistrazioneDTO = statoRegistrazioneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(statoRegistrazioneDTO);
    }

    /**
     * {@code DELETE  /stato-registraziones/:id} : delete the "id" statoRegistrazione.
     *
     * @param id the id of the statoRegistrazioneDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stato-registraziones/{id}")
    public ResponseEntity<Void> deleteStatoRegistrazione(@PathVariable Long id) {
        log.debug("REST request to delete StatoRegistrazione : {}", id);
        statoRegistrazioneService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
