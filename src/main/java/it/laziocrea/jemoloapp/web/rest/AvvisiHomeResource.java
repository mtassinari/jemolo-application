package it.laziocrea.jemoloapp.web.rest;

import it.laziocrea.jemoloapp.domain.AvvisiHome;
import it.laziocrea.jemoloapp.repository.AvvisiHomeRepository;
import it.laziocrea.jemoloapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link it.laziocrea.jemoloapp.domain.AvvisiHome}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AvvisiHomeResource {

    private final Logger log = LoggerFactory.getLogger(AvvisiHomeResource.class);

    private static final String ENTITY_NAME = "avvisiHome";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AvvisiHomeRepository avvisiHomeRepository;

    public AvvisiHomeResource(AvvisiHomeRepository avvisiHomeRepository) {
        this.avvisiHomeRepository = avvisiHomeRepository;
    }

    /**
     * {@code POST  /avvisi-homes} : Create a new avvisiHome.
     *
     * @param avvisiHome the avvisiHome to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new avvisiHome, or with status {@code 400 (Bad Request)} if the avvisiHome has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/avvisi-homes")
    public ResponseEntity<AvvisiHome> createAvvisiHome(@Valid @RequestBody AvvisiHome avvisiHome) throws URISyntaxException {
        log.debug("REST request to save AvvisiHome : {}", avvisiHome);
        if (avvisiHome.getId() != null) {
            throw new BadRequestAlertException("A new avvisiHome cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AvvisiHome result = avvisiHomeRepository.save(avvisiHome);
        return ResponseEntity.created(new URI("/api/avvisi-homes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /avvisi-homes} : Updates an existing avvisiHome.
     *
     * @param avvisiHome the avvisiHome to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avvisiHome,
     * or with status {@code 400 (Bad Request)} if the avvisiHome is not valid,
     * or with status {@code 500 (Internal Server Error)} if the avvisiHome couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/avvisi-homes")
    public ResponseEntity<AvvisiHome> updateAvvisiHome(@Valid @RequestBody AvvisiHome avvisiHome) throws URISyntaxException {
        log.debug("REST request to update AvvisiHome : {}", avvisiHome);
        if (avvisiHome.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AvvisiHome result = avvisiHomeRepository.save(avvisiHome);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avvisiHome.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /avvisi-homes} : get all the avvisiHomes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of avvisiHomes in body.
     */
    @GetMapping("/avvisi-homes")
    public List<AvvisiHome> getAllAvvisiHomes() {
        log.debug("REST request to get all AvvisiHomes");
        return avvisiHomeRepository.findAll();
    }

    /**
     * {@code GET  /v2/avvisi-homes} : get all the avvisiHomes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of avvisiHomes in body.
     */
    @GetMapping("/v2/avvisi-homes")
    public List<AvvisiHome> getAllIscrittiAvvisiHomes() {
        log.debug("REST request to get all IscrittiAvvisi");
        return avvisiHomeRepository.findByVisibileTrue();
    }
    
    /**
     * {@code GET  /avvisi-homes/:id} : get the "id" avvisiHome.
     *
     * @param id the id of the avvisiHome to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the avvisiHome, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/avvisi-homes/{id}")
    public ResponseEntity<AvvisiHome> getAvvisiHome(@PathVariable Long id) {
        log.debug("REST request to get AvvisiHome : {}", id);
        Optional<AvvisiHome> avvisiHome = avvisiHomeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(avvisiHome);
    }

    /**
     * {@code DELETE  /avvisi-homes/:id} : delete the "id" avvisiHome.
     *
     * @param id the id of the avvisiHome to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/avvisi-homes/{id}")
    public ResponseEntity<Void> deleteAvvisiHome(@PathVariable Long id) {
        log.debug("REST request to delete AvvisiHome : {}", id);
        avvisiHomeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
