package it.laziocrea.jemoloapp.web.rest;

import it.laziocrea.jemoloapp.service.DichiarazioniObligatorieService;
import it.laziocrea.jemoloapp.web.rest.errors.BadRequestAlertException;
import it.laziocrea.jemoloapp.service.dto.DichiarazioniObligatorieDTO;

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
 * REST controller for managing {@link it.laziocrea.jemoloapp.domain.DichiarazioniObligatorie}.
 */
@RestController
@RequestMapping("/api")
public class DichiarazioniObligatorieResource {

    private final Logger log = LoggerFactory.getLogger(DichiarazioniObligatorieResource.class);

    private static final String ENTITY_NAME = "dichiarazioniObligatorie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DichiarazioniObligatorieService dichiarazioniObligatorieService;

    public DichiarazioniObligatorieResource(DichiarazioniObligatorieService dichiarazioniObligatorieService) {
        this.dichiarazioniObligatorieService = dichiarazioniObligatorieService;
    }

    /**
     * {@code POST  /dichiarazioni-obligatories} : Create a new dichiarazioniObligatorie.
     *
     * @param dichiarazioniObligatorieDTO the dichiarazioniObligatorieDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dichiarazioniObligatorieDTO, or with status {@code 400 (Bad Request)} if the dichiarazioniObligatorie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dichiarazioni-obligatories")
    public ResponseEntity<DichiarazioniObligatorieDTO> createDichiarazioniObligatorie(@Valid @RequestBody DichiarazioniObligatorieDTO dichiarazioniObligatorieDTO) throws URISyntaxException {
        log.debug("REST request to save DichiarazioniObligatorie : {}", dichiarazioniObligatorieDTO);
        if (dichiarazioniObligatorieDTO.getId() != null) {
            throw new BadRequestAlertException("A new dichiarazioniObligatorie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DichiarazioniObligatorieDTO result = dichiarazioniObligatorieService.save(dichiarazioniObligatorieDTO);
        return ResponseEntity.created(new URI("/api/dichiarazioni-obligatories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dichiarazioni-obligatories} : Updates an existing dichiarazioniObligatorie.
     *
     * @param dichiarazioniObligatorieDTO the dichiarazioniObligatorieDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dichiarazioniObligatorieDTO,
     * or with status {@code 400 (Bad Request)} if the dichiarazioniObligatorieDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dichiarazioniObligatorieDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dichiarazioni-obligatories")
    public ResponseEntity<DichiarazioniObligatorieDTO> updateDichiarazioniObligatorie(@Valid @RequestBody DichiarazioniObligatorieDTO dichiarazioniObligatorieDTO) throws URISyntaxException {
        log.debug("REST request to update DichiarazioniObligatorie : {}", dichiarazioniObligatorieDTO);
        if (dichiarazioniObligatorieDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DichiarazioniObligatorieDTO result = dichiarazioniObligatorieService.save(dichiarazioniObligatorieDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dichiarazioniObligatorieDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dichiarazioni-obligatories} : get all the dichiarazioniObligatories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dichiarazioniObligatories in body.
     */
    @GetMapping("/dichiarazioni-obligatories")
    public List<DichiarazioniObligatorieDTO> getAllDichiarazioniObligatories() {
        log.debug("REST request to get all DichiarazioniObligatories");
        return dichiarazioniObligatorieService.findAll();
    }

    /**
     * {@code GET  /dichiarazioni-obligatories/:id} : get the "id" dichiarazioniObligatorie.
     *
     * @param id the id of the dichiarazioniObligatorieDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dichiarazioniObligatorieDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dichiarazioni-obligatories/{id}")
    public ResponseEntity<DichiarazioniObligatorieDTO> getDichiarazioniObligatorie(@PathVariable Long id) {
        log.debug("REST request to get DichiarazioniObligatorie : {}", id);
        Optional<DichiarazioniObligatorieDTO> dichiarazioniObligatorieDTO = dichiarazioniObligatorieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dichiarazioniObligatorieDTO);
    }

    /**
     * {@code DELETE  /dichiarazioni-obligatories/:id} : delete the "id" dichiarazioniObligatorie.
     *
     * @param id the id of the dichiarazioniObligatorieDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dichiarazioni-obligatories/{id}")
    public ResponseEntity<Void> deleteDichiarazioniObligatorie(@PathVariable Long id) {
        log.debug("REST request to delete DichiarazioniObligatorie : {}", id);
        dichiarazioniObligatorieService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
