package it.laziocrea.jemoloapp.web.rest;

import it.laziocrea.jemoloapp.domain.AnagraficaCandidato;
import it.laziocrea.jemoloapp.domain.SearchParam;
import it.laziocrea.jemoloapp.file.helper.ExcelHelper;
import it.laziocrea.jemoloapp.service.AnagraficaCandidatoQueryService;
import it.laziocrea.jemoloapp.service.AnagraficaCandidatoService;
import it.laziocrea.jemoloapp.service.AnagraficaService;
import it.laziocrea.jemoloapp.web.rest.errors.BadRequestAlertException;
import it.laziocrea.jemoloapp.service.dto.AnagraficaCandidatoCriteria;
import it.laziocrea.jemoloapp.service.dto.AnagraficaCandidatoDTO;
import it.laziocrea.jemoloapp.service.dto.AnagraficaDTO;
import it.laziocrea.jemoloapp.service.dto.CandidatoDTO;
import it.laziocrea.jemoloapp.service.impl.AnagraficaCandidatoServiceImpl;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link it.laziocrea.jemoloapp.domain.AnagraficaCandidato}.
 */
@RestController
@RequestMapping("/api")
public class AnagraficaCandidatoResource {

    private final Logger log = LoggerFactory.getLogger(AnagraficaCandidatoResource.class);

    private static final String ENTITY_NAME = "anagraficaCandidato";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnagraficaCandidatoService anagraficaCandidatoService;

    private final AnagraficaService anagraficaService;
    
    private final AnagraficaCandidatoQueryService anagraficaCandidatoQueryService;
    
    public AnagraficaCandidatoResource(AnagraficaService anagraficaService, AnagraficaCandidatoService anagraficaCandidatoService, AnagraficaCandidatoQueryService anagraficaCandidatoQueryService) {
    	this.anagraficaCandidatoQueryService = anagraficaCandidatoQueryService;
    	this.anagraficaCandidatoService = anagraficaCandidatoService;
    	this.anagraficaService = anagraficaService;
    }

    /**
     * {@code POST  /anagrafica-candidatoes} : Create a new anagraficaCandidato.
     *
     * @param anagraficaCandidatoDTO the anagraficaCandidatoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anagraficaCandidatoDTO, or with status {@code 400 (Bad Request)} if the anagraficaCandidato has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/anagrafica-candidatoes")
    public ResponseEntity<AnagraficaCandidatoDTO> createAnagraficaCandidato(@Valid @RequestBody AnagraficaCandidatoDTO anagraficaCandidatoDTO) throws URISyntaxException {
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
     * {@code PUT  /anagrafica-candidatoes} : Updates an existing anagraficaCandidato.
     *
     * @param anagraficaCandidatoDTO the anagraficaCandidatoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anagraficaCandidatoDTO,
     * or with status {@code 400 (Bad Request)} if the anagraficaCandidatoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anagraficaCandidatoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/anagrafica-candidatoes")
    public ResponseEntity<AnagraficaCandidatoDTO> updateAnagraficaCandidato(@Valid @RequestBody AnagraficaCandidatoDTO anagraficaCandidatoDTO) throws URISyntaxException {
        log.debug("REST request to update AnagraficaCandidato : {}", anagraficaCandidatoDTO);
        if (anagraficaCandidatoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnagraficaCandidatoDTO result = anagraficaCandidatoService.save(anagraficaCandidatoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anagraficaCandidatoDTO.getId().toString()))
            .body(result);
    }
    
    /**
     * {@code PUT  /anagrafica} : Updates an existing anagrafica.
     *
     * @param anagraficaDTO the anagraficaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anagraficaDTO,
     * or with status {@code 400 (Bad Request)} if the anagraficaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anagraficaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/anagrafica")
    public ResponseEntity<AnagraficaDTO> updateAnagrafica(@Valid @RequestBody AnagraficaDTO anagraficaDTO) throws URISyntaxException {
        log.debug("REST request to update Anagrafica : {}", anagraficaDTO);
        if (anagraficaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnagraficaDTO result = anagraficaService.save(anagraficaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anagraficaDTO.getId().toString()))
            .body(result);
    }
    
    /**
     * {@code GET  /ricerca-iscritti} : get all the anagraficaCandidatoes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anagraficaCandidatoes in body.
     */
    @GetMapping("/ricerca-iscritti")
    public ResponseEntity<List<AnagraficaCandidatoDTO>> ricercaIscritti(Pageable pageable, AnagraficaCandidatoCriteria params) {
        log.debug("REST request to get SchedeIscritti by criteria: {}", params);
        Page<AnagraficaCandidatoDTO> page = anagraficaCandidatoQueryService.findByCriteria(params, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    /**
     * {@code GET  /ricerca-iscritti/excel-download} : get all the anagraficaCandidatoes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anagraficaCandidatoes in body.
     */
    @GetMapping(value="/ricerca-iscritti/excel-download", produces="application/vnd.ms-excel")
    public Resource ricercaIscrittiExcel(Pageable pageable, AnagraficaCandidatoCriteria params, HttpServletResponse response) {
        String filename = "risultato_ricerca_roster.xlsx";
        InputStreamResource file = new InputStreamResource(anagraficaCandidatoService.findByCriteriaToExcel(params));
        response.setHeader("filename", filename);
    	response.setStatus(HttpServletResponse.SC_OK);
       	response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
       	response.setContentType(ExcelHelper.TYPE);
    	return file;
    }
    
    /**
     * {@code GET  /ricerca-iscritti} : get all the anagraficaCandidatoes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anagraficaCandidatoes in body.
     */
    @GetMapping("/ricerca-iscritti2")
    public ResponseEntity<List<AnagraficaCandidatoDTO>> ricercaIscritti2(Pageable pageable, SearchParam criteria) {
        log.debug("REST request to get AnagraficaCandidatoes by criteria: {}", criteria);
        Page<AnagraficaCandidatoDTO> page = anagraficaCandidatoService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    /**
     * {@code GET  /anagrafica-candidatoes} : get all the anagraficaCandidatoes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anagraficaCandidatoes in body.
     */
    @GetMapping("/anagrafica-candidatoes")
    public ResponseEntity<List<AnagraficaCandidatoDTO>> getAllAnagraficaCandidatoes(Pageable pageable) {
        log.debug("REST request to get a page of AnagraficaCandidatoes");
        Page<AnagraficaCandidatoDTO> page = anagraficaCandidatoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /anagrafica-candidatoes/:id} : get the "id" anagraficaCandidato.
     *
     * @param id the id of the anagraficaCandidatoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anagraficaCandidatoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ricerca-iscritti/{id}")
    public ResponseEntity<AnagraficaCandidatoDTO> getIscritto(@PathVariable Long id) {
        log.debug("REST request to get AnagraficaCandidato : {}", id);
        Optional<AnagraficaCandidatoDTO> anagraficaCandidatoDTO = anagraficaCandidatoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anagraficaCandidatoDTO);
    }

    /**
     * {@code GET  /anagrafica-candidatoes/:id} : get the "id" anagraficaCandidato.
     *
     * @param id the id of the anagraficaCandidatoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anagraficaCandidatoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/anagrafica-candidatoes/{id}")
    public ResponseEntity<AnagraficaCandidatoDTO> getAnagraficaCandidato(@PathVariable Long id) {
        log.debug("REST request to get AnagraficaCandidato : {}", id);
        Optional<AnagraficaCandidatoDTO> anagraficaCandidatoDTO = anagraficaCandidatoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anagraficaCandidatoDTO);
    }
    
    /**
     * {@code GET  /anagrafica-candidatoes2/:id} : get the "id" anagraficaCandidato.
     *
     * @param id the id of the anagraficaCandidato to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anagraficaCandidato, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/anagrafica-candidatoes2/{id}")
    public ResponseEntity<AnagraficaCandidatoDTO> getAnagraficaCandidato2(@PathVariable Long id) {
        log.debug("REST request to get AnagraficaCandidato : {}", id);
        Optional<AnagraficaCandidatoDTO> anagraficaCandidato = anagraficaCandidatoService.findOne2(id);
        return ResponseUtil.wrapOrNotFound(anagraficaCandidato);
    }

    /**
     * {@code DELETE  /anagrafica-candidatoes/:id} : delete the "id" anagraficaCandidato.
     *
     * @param id the id of the anagraficaCandidatoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/anagrafica-candidatoes/{id}")
    public ResponseEntity<Void> deleteAnagraficaCandidato(@PathVariable Long id) {
        log.debug("REST request to delete AnagraficaCandidato : {}", id);
        anagraficaCandidatoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    /*@GetMapping("/anagrafica-candidato/notifiche")
    public void eseguiNotifiche() {
    	anagraficaCandidatoService.
    }*/
}
