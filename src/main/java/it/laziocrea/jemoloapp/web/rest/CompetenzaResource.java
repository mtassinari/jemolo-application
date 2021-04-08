package it.laziocrea.jemoloapp.web.rest;

import it.laziocrea.jemoloapp.domain.Competenza;
import it.laziocrea.jemoloapp.service.CompetenzaService;
import it.laziocrea.jemoloapp.web.rest.errors.BadRequestAlertException;
import it.laziocrea.jemoloapp.service.dto.CompetenzaDTO;

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
 * REST controller for managing {@link it.laziocrea.jemoloapp.domain.Competenza}.
 */
@RestController
@RequestMapping("/api")
public class CompetenzaResource {

    private final Logger log = LoggerFactory.getLogger(CompetenzaResource.class);

    private static final String ENTITY_NAME = "competenza";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetenzaService competenzaService;

    public CompetenzaResource(CompetenzaService competenzaService) {
        this.competenzaService = competenzaService;
    }

    /**
     * {@code POST  /competenzas} : Create a new competenza.
     *
     * @param competenzaDTO the competenzaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competenzaDTO, or with status {@code 400 (Bad Request)} if the competenza has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/competenzas")
    public ResponseEntity<CompetenzaDTO> createCompetenza(@Valid @RequestBody CompetenzaDTO competenzaDTO) throws URISyntaxException {
        log.debug("REST request to save Competenza : {}", competenzaDTO);
        if (competenzaDTO.getId() != null) {
            throw new BadRequestAlertException("A new competenza cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompetenzaDTO result = competenzaService.save(competenzaDTO);
        return ResponseEntity.created(new URI("/api/competenzas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /competenzas} : Updates an existing competenza.
     *
     * @param competenzaDTO the competenzaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competenzaDTO,
     * or with status {@code 400 (Bad Request)} if the competenzaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competenzaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/competenzas")
    public ResponseEntity<CompetenzaDTO> updateCompetenza(@Valid @RequestBody CompetenzaDTO competenzaDTO) throws URISyntaxException {
        log.debug("REST request to update Competenza : {}", competenzaDTO);
        if (competenzaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompetenzaDTO result = competenzaService.save(competenzaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competenzaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /competenzas} : get all the competenzas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competenzas in body.
     */
    @GetMapping("/competenzas")
    public List<CompetenzaDTO> getAllCompetenzas() {
        log.debug("REST request to get all Competenzas");
        return competenzaService.findAll();
    }

    /**
     * {@code GET  /competenzas/:id} : get the "id" competenza.
     *
     * @param id the id of the competenzaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competenzaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competenzas/{id}")
    public ResponseEntity<CompetenzaDTO> getCompetenza(@PathVariable Long id) {
        log.debug("REST request to get Competenza : {}", id);
        Optional<CompetenzaDTO> competenzaDTO = competenzaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(competenzaDTO);
    }
    
    /**
     * {@code GET  /competenzas/:idAmbito:idCandidato} : get the "id" competenza.
     *
     * @param id the id of the competenzaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competenzaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competenzas/candidato")
    public ResponseEntity<CompetenzaDTO> getCompetenzaCandidato(@RequestParam(name = "idAmbito") Long idAmbito, @RequestParam(name = "idCandidato") Long idCandidato) {
        log.debug("REST request to get Competenza : {}", idAmbito+" : "+idCandidato);
        Optional<CompetenzaDTO> competenzaDTO = competenzaService.findOneByCandidato(idAmbito,idCandidato);
        return ResponseUtil.wrapOrNotFound(competenzaDTO);
    }

    /**
     * {@code DELETE  /competenzas/:id} : delete the "id" competenza.
     *
     * @param id the id of the competenzaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/competenzas/{id}")
    public ResponseEntity<Void> deleteCompetenza(@PathVariable Long id) {
        log.debug("REST request to delete Competenza : {}", id);
        competenzaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
