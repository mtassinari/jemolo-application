package it.laziocrea.jemoloapp.web.rest;

import it.laziocrea.jemoloapp.domain.Comune;
import it.laziocrea.jemoloapp.domain.Provincia;
import it.laziocrea.jemoloapp.repository.ComuneRepository;
import it.laziocrea.jemoloapp.repository.ProvinciaRepository;
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
 * REST controller for managing {@link it.laziocrea.jemoloapp.domain.Comune}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ComuneResource {

    private final Logger log = LoggerFactory.getLogger(ComuneResource.class);

    private static final String ENTITY_NAME = "comune";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComuneRepository comuneRepository;
    private final ProvinciaRepository provinciaRepository;

    public ComuneResource(ProvinciaRepository provinciaRepository, ComuneRepository comuneRepository) {
        this.comuneRepository = comuneRepository;
        this.provinciaRepository = provinciaRepository;
    }

    /**
     * {@code POST  /comunes} : Create a new comune.
     *
     * @param comune the comune to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new comune, or with status {@code 400 (Bad Request)} if the comune has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comunes")
    public ResponseEntity<Comune> createComune(@Valid @RequestBody Comune comune) throws URISyntaxException {
        log.debug("REST request to save Comune : {}", comune);
        if (comune.getId() != null) {
            throw new BadRequestAlertException("A new comune cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Comune result = comuneRepository.save(comune);
        return ResponseEntity.created(new URI("/api/comunes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comunes} : Updates an existing comune.
     *
     * @param comune the comune to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comune,
     * or with status {@code 400 (Bad Request)} if the comune is not valid,
     * or with status {@code 500 (Internal Server Error)} if the comune couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comunes")
    public ResponseEntity<Comune> updateComune(@Valid @RequestBody Comune comune) throws URISyntaxException {
        log.debug("REST request to update Comune : {}", comune);
        if (comune.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Comune result = comuneRepository.save(comune);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comune.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /comunes} : get all the comunes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comunes in body.
     */
    @GetMapping("/comunes")
    public List<Comune> getAllComunes() {
        log.debug("REST request to get all Comunes");
        return comuneRepository.findAll();
    }
    
    /**
     * {@code GET  /comunes} : get all the comunes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comunes in body.
     */
    @GetMapping("/comuni")
    public List<Comune> getAllComuniProvincia(@RequestParam(value = "siglaProvincia") String siglaProvincia) {
        log.debug("REST request to get all Comuni in provincia");
        return comuneRepository.findDBySiglaProvinciaIgnoreCase(siglaProvincia);
    }
    
    /**
     * {@code GET  /provincie} : get all the comunes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comunes in body.
     */
    @GetMapping("/provincie")
    public List<Provincia> getAllProvincie() {
        log.debug("REST request to get all Provincie");
        return provinciaRepository.findAllByOrderByNomeAsc();
    }

    /**
     * {@code GET  /comunes/:id} : get the "id" comune.
     *
     * @param id the id of the comune to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the comune, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comunes/{id}")
    public ResponseEntity<Comune> getComune(@PathVariable Long id) {
        log.debug("REST request to get Comune : {}", id);
        Optional<Comune> comune = comuneRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(comune);
    }

    /**
     * {@code DELETE  /comunes/:id} : delete the "id" comune.
     *
     * @param id the id of the comune to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comunes/{id}")
    public ResponseEntity<Void> deleteComune(@PathVariable Long id) {
        log.debug("REST request to delete Comune : {}", id);
        comuneRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
