package it.laziocrea.jemoloapp.web.rest;

import it.laziocrea.jemoloapp.service.AmbitoCompetenzaService;
import it.laziocrea.jemoloapp.web.rest.errors.BadRequestAlertException;
import it.laziocrea.jemoloapp.service.dto.AmbitoCompetenzaDTO;

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
 * REST controller for managing {@link it.laziocrea.jemolorooster.domain.AmbitoCompetenza}.
 */
@RestController
@RequestMapping("/api")
public class AmbitoCompetenzaResource {

    private final Logger log = LoggerFactory.getLogger(AmbitoCompetenzaResource.class);

    private static final String ENTITY_NAME = "ambitoCompetenza";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AmbitoCompetenzaService ambitoCompetenzaService;

    public AmbitoCompetenzaResource(AmbitoCompetenzaService ambitoCompetenzaService) {
        this.ambitoCompetenzaService = ambitoCompetenzaService;
    }

    /**
     * {@code POST  /ambito-competenzas} : Create a new ambitoCompetenza.
     *
     * @param ambitoCompetenzaDTO the ambitoCompetenzaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ambitoCompetenzaDTO, or with status {@code 400 (Bad Request)} if the ambitoCompetenza has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ambito-competenzas")
    public ResponseEntity<AmbitoCompetenzaDTO> createAmbitoCompetenza(@Valid @RequestBody AmbitoCompetenzaDTO ambitoCompetenzaDTO) throws URISyntaxException {
        log.debug("REST request to save AmbitoCompetenza : {}", ambitoCompetenzaDTO);
        if (ambitoCompetenzaDTO.getId() != null) {
            throw new BadRequestAlertException("A new ambitoCompetenza cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AmbitoCompetenzaDTO result = ambitoCompetenzaService.save(ambitoCompetenzaDTO);
        return ResponseEntity.created(new URI("/api/ambito-competenzas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ambito-competenzas} : Updates an existing ambitoCompetenza.
     *
     * @param ambitoCompetenzaDTO the ambitoCompetenzaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ambitoCompetenzaDTO,
     * or with status {@code 400 (Bad Request)} if the ambitoCompetenzaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ambitoCompetenzaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ambito-competenzas")
    public ResponseEntity<AmbitoCompetenzaDTO> updateAmbitoCompetenza(@Valid @RequestBody AmbitoCompetenzaDTO ambitoCompetenzaDTO) throws URISyntaxException {
        log.debug("REST request to update AmbitoCompetenza : {}", ambitoCompetenzaDTO);
        if (ambitoCompetenzaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AmbitoCompetenzaDTO result = ambitoCompetenzaService.save(ambitoCompetenzaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ambitoCompetenzaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ambito-competenzas} : get all the ambitoCompetenzas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ambitoCompetenzas in body.
     */
    @GetMapping("/ambito-competenzas")
    public List<AmbitoCompetenzaDTO> getAllAmbitoCompetenzas() {
        log.debug("REST request to get all AmbitoCompetenzas");
        return ambitoCompetenzaService.findAll();
    }
    
    /**
     * {@code GET  /ambito-competenzas} : get all the ambitoCompetenzas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ambitoCompetenzas in body.
     */
    @GetMapping("/ambito-competenzas/parent")
    public List<AmbitoCompetenzaDTO> getAllParentAmbitoCompetenzas(@RequestParam(value = "type") String type) {
        log.debug("REST request to get all AmbitoCompetenzas");
        return ambitoCompetenzaService.findAllParent(type);
        /*log.debug("REST request to get all AmbitoCompetenze");
        return ambitoCompetenzaService.findAllAmbiti(type);*/
    }
   
    /**
     * {@code GET  /ambito-competenzas} : get all the ambitoCompetenzas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ambitoCompetenzas in body.
     */
    @GetMapping("/v2/ambito-competenzas/parent")
    public List<AmbitoCompetenzaDTO> getAllParentV2AmbitoCompetenzas(@RequestParam(value = "type") String type) {
        log.debug("REST request to get all AmbitoCompetenzas");
        return ambitoCompetenzaService.findAllParent(type);
        // return ambitoCompetenzaService.findAllAmbiti(type);
    }
    
    /**
     * {@code GET  /ambito-competenzas} : get all the ambitoCompetenzas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ambitoCompetenzas in body.
     */
    @GetMapping("/v2/ambito-competenzas/valid")
    public List<AmbitoCompetenzaDTO> getAllValidAmbitoCompetenzas(@RequestParam(value = "type") String type) {
        log.debug("REST request to get all valid AmbitoCompetenzas");
        return ambitoCompetenzaService.findAllAmbitiValid(type);
    }

    /**
     * {@code GET  /ambito-competenzas/:id} : get the "id" ambitoCompetenza.
     *
     * @param id the id of the ambitoCompetenzaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ambitoCompetenzaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ambito-competenzas/{id}")
    public ResponseEntity<AmbitoCompetenzaDTO> getAmbitoCompetenza(@PathVariable Long id) {
        log.debug("REST request to get AmbitoCompetenza : {}", id);
        Optional<AmbitoCompetenzaDTO> ambitoCompetenzaDTO = ambitoCompetenzaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ambitoCompetenzaDTO);
    }

    /**
     * {@code DELETE  /ambito-competenzas/:id} : delete the "id" ambitoCompetenza.
     *
     * @param id the id of the ambitoCompetenzaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ambito-competenzas/{id}")
    public ResponseEntity<Void> deleteAmbitoCompetenza(@PathVariable Long id) {
        log.debug("REST request to delete AmbitoCompetenza : {}", id);
        ambitoCompetenzaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
