package it.laziocrea.jemoloapp.web.rest;

import it.laziocrea.jemoloapp.service.TitoloStudioService;
import it.laziocrea.jemoloapp.web.rest.errors.BadRequestAlertException;
import it.laziocrea.jemoloapp.service.dto.TitoloStudioDTO;

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
 * REST controller for managing {@link it.laziocrea.jemolorooster.domain.TitoloStudio}.
 */
@RestController
@RequestMapping("/api")
public class TitoloStudioResource {

    private final Logger log = LoggerFactory.getLogger(TitoloStudioResource.class);

    private static final String ENTITY_NAME = "titoloStudio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TitoloStudioService titoloStudioService;

    public TitoloStudioResource(TitoloStudioService titoloStudioService) {
        this.titoloStudioService = titoloStudioService;
    }

    /**
     * {@code POST  /titolo-studios} : Create a new titoloStudio.
     *
     * @param titoloStudioDTO the titoloStudioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new titoloStudioDTO, or with status {@code 400 (Bad Request)} if the titoloStudio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/titolo-studios")
    public ResponseEntity<TitoloStudioDTO> createTitoloStudio(@Valid @RequestBody TitoloStudioDTO titoloStudioDTO) throws URISyntaxException {
        log.debug("REST request to save TitoloStudio : {}", titoloStudioDTO);
        if (titoloStudioDTO.getId() != null) {
            throw new BadRequestAlertException("A new titoloStudio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TitoloStudioDTO result = titoloStudioService.save(titoloStudioDTO);
        return ResponseEntity.created(new URI("/api/titolo-studios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /titolo-studios} : Updates an existing titoloStudio.
     *
     * @param titoloStudioDTO the titoloStudioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated titoloStudioDTO,
     * or with status {@code 400 (Bad Request)} if the titoloStudioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the titoloStudioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/titolo-studios")
    public ResponseEntity<TitoloStudioDTO> updateTitoloStudio(@Valid @RequestBody TitoloStudioDTO titoloStudioDTO) throws URISyntaxException {
        log.debug("REST request to update TitoloStudio : {}", titoloStudioDTO);
        if (titoloStudioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TitoloStudioDTO result = titoloStudioService.save(titoloStudioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, titoloStudioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /titolo-studios} : get all the titoloStudios.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of titoloStudios in body.
     */
    @GetMapping("/titolo-studios")
    public List<TitoloStudioDTO> getAllTitoloStudios() {
        log.debug("REST request to get all TitoloStudios");
        return titoloStudioService.findAll();
    }

    /**
     * {@code GET  /titolo-studios/:id} : get the "id" titoloStudio.
     *
     * @param id the id of the titoloStudioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the titoloStudioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/titolo-studios/{id}")
    public ResponseEntity<TitoloStudioDTO> getTitoloStudio(@PathVariable Long id) {
        log.debug("REST request to get TitoloStudio : {}", id);
        Optional<TitoloStudioDTO> titoloStudioDTO = titoloStudioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(titoloStudioDTO);
    }

    /**
     * {@code DELETE  /titolo-studios/:id} : delete the "id" titoloStudio.
     *
     * @param id the id of the titoloStudioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/titolo-studios/{id}")
    public ResponseEntity<Void> deleteTitoloStudio(@PathVariable Long id) {
        log.debug("REST request to delete TitoloStudio : {}", id);
        titoloStudioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
