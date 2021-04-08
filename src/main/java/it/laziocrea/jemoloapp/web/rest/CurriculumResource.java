package it.laziocrea.jemoloapp.web.rest;

import it.laziocrea.jemoloapp.config.FileStorageProperties;
import it.laziocrea.jemoloapp.domain.Curriculum;
import it.laziocrea.jemoloapp.service.CurriculumService;
import it.laziocrea.jemoloapp.web.rest.errors.BadRequestAlertException;
import it.laziocrea.jemoloapp.web.rest.errors.CurriculumNotFoundException;
import it.laziocrea.jemoloapp.service.dto.CurriculumDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.micrometer.core.annotation.Timed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * REST controller for managing {@link it.laziocrea.jemolorooster.domain.Curriculum}.
 */
@RestController
@RequestMapping("/api")
public class CurriculumResource {

    private final Logger log = LoggerFactory.getLogger(CurriculumResource.class);

    private static final String ENTITY_NAME = "curriculum";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CurriculumService curriculumService;
    private final Path fileStorageLocation;
    
    public CurriculumResource(CurriculumService curriculumService, FileStorageProperties fileStorageProperties) {
        this.curriculumService = curriculumService;
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
    }

    /**
     * {@code POST  /curricula/allselected} : Download selected curriculum.
     *
     * @param curriculumDTO the curriculumDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new curriculumDTO, or with status {@code 400 (Bad Request)} if the curriculum has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping(value="/curricula/allselected", produces="application/zip")
    public Resource  zipCurriculum(@RequestBody List<CurriculumDTO> selectedcv, HttpServletResponse response) throws URISyntaxException, IOException {
        log.debug("REST request to zip and download selected Curriculum : {}", selectedcv.size());
        // createZipFile(selectedcv,response);
    	Date date = new Date();
    	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
    	// String dateFormat = formatter.format(date);
    	String zipFileName = formatter.format(date) + "_CV.zip";
     	response.setHeader("filename", zipFileName);
       
    	ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());
    	FileSystemResource resource = null;
    	for (CurriculumDTO fileName : selectedcv) {
    		// Path targetLocation = this.fileStorageLocation.resolve(fileName.getCv());
    		resource = new FileSystemResource(fileName.getUrlAllegato());
    		// FileSystemResource resource = new FileSystemResource(fileBasePath + fileName);
    		ZipEntry zipEntry = new ZipEntry(resource.getFilename());
    		zipEntry.setSize(resource.contentLength());
    		zipOut.putNextEntry(zipEntry);
    		StreamUtils.copy(resource.getInputStream(), zipOut);
    		zipOut.closeEntry();
    	}
    	zipOut.finish();
    	zipOut.close();
    	response.setStatus(HttpServletResponse.SC_OK);
       	response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + zipFileName + "\"");
    	return resource;
    }
    
    /**
     * {@code POST  /curricula/allselected} : Download selected curriculum.
     *
     * @param curriculumDTO the curriculumDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new curriculumDTO, or with status {@code 400 (Bad Request)} if the curriculum has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */    
    @PostMapping(value="/curricula/allselected2", produces="application/zip")
    public void createZipFile(@RequestBody List<CurriculumDTO> selectedcv, HttpServletResponse response) throws URISyntaxException, IOException {
    	log.debug("REST request to zip and download {} selected Curriculum", selectedcv.size());
    	Date date = new Date();
    	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH_mm_ss");
    	String zipFileName = formatter.format(date) + "_CV.zip";
    	response.setHeader("filename", zipFileName);
        // FileOutputStream fout = new FileOutputStream("C:\\Users\\mtassinari\\uploads\\"+zipFileName);
        // CheckedOutputStream checksum = new CheckedOutputStream(fout, new Adler32());
        ZipOutputStream zout = new ZipOutputStream(response.getOutputStream());
        for (CurriculumDTO fileName : selectedcv) {
        	FileSystemResource resource = new FileSystemResource(fileName.getUrlAllegato());
	        FileInputStream fin = new FileInputStream(fileName.getUrlAllegato());
	        ZipEntry zipEntry = new ZipEntry(resource.getFilename());
	        zout.putNextEntry(zipEntry);
	        int length;
	        byte[] buffer = new byte[1024];
	        while((length = fin.read(buffer)) > 0) {
	           zout.write(buffer, 0, length);
	        }
	
	        zout.closeEntry();
	        fin.close();
        }
        zout.finish();
        zout.close();
    	response.setStatus(HttpServletResponse.SC_OK);
       	response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + zipFileName + "\"");
     }
    
    /**
     * {@code POST  /curricula} : Create a new curriculum.
     *
     * @param curriculumDTO the curriculumDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new curriculumDTO, or with status {@code 400 (Bad Request)} if the curriculum has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/curricula")
    public ResponseEntity<CurriculumDTO> createCurriculum(@Valid @RequestBody CurriculumDTO curriculumDTO) throws URISyntaxException {
        log.debug("REST request to save Curriculum : {}", curriculumDTO);
        if (curriculumDTO.getId() != null) {
            throw new BadRequestAlertException("A new curriculum cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CurriculumDTO result = curriculumService.save(curriculumDTO);
        return ResponseEntity.created(new URI("/api/curricula/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /curricula} : Updates an existing curriculum.
     *
     * @param curriculumDTO the curriculumDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated curriculumDTO,
     * or with status {@code 400 (Bad Request)} if the curriculumDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the curriculumDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/curricula")
    public ResponseEntity<CurriculumDTO> updateCurriculum(@Valid @RequestBody CurriculumDTO curriculumDTO) throws URISyntaxException {
        log.debug("REST request to update Curriculum : {}", curriculumDTO);
        if (curriculumDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CurriculumDTO result = curriculumService.save(curriculumDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, curriculumDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /curricula} : get all the curricula.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of curricula in body.
     */
    @GetMapping("/curricula")
    public ResponseEntity<List<CurriculumDTO>> getAllCurricula(Pageable pageable) {
        log.debug("REST request to get a page of Curricula");
        Page<CurriculumDTO> page = curriculumService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /curricula/:id} : get the "id" curriculum.
     *
     * @param id the id of the curriculumDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the curriculumDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/curricula/{id}")
    public ResponseEntity<CurriculumDTO> getCurriculum(@PathVariable Long id) {
        log.debug("REST request to get Curriculum : {}", id);
        Optional<CurriculumDTO> curriculumDTO = curriculumService.findOne(id);
        return ResponseUtil.wrapOrNotFound(curriculumDTO);
    }
    
    /**
     * {@code GET  /curricula/{id}/$allegato} : get the "id" allegato.
     * 
     * @param id l'id dell'allegato da restituire
     * @return l'{@link ResponseEntity} con staus {@code 200 (OK)} con l'allegato nel body, o status {@code 404 (Not Found)}.
     */
    @GetMapping("/curricula/{id}/$content")
    @Timed
    public ResponseEntity<byte[]> getCurriculumFile(@PathVariable Long id) {
    	log.debug("REST request to get CurriculumFile : {}", id);
       Curriculum curriculum = curriculumService.findOneById(id)
           .orElseThrow(CurriculumNotFoundException::new);

       return ResponseEntity.ok()
           .contentType(MediaType.parseMediaType(curriculum.getMimeType()))
           .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + curriculum.getCv() + "\"")
           .body(curriculum.retrieveAllegato());
    }

    /**
     * {@code DELETE  /curricula/:id} : delete the "id" curriculum.
     *
     * @param id the id of the curriculumDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/curricula/{id}")
    public ResponseEntity<Void> deleteCurriculum(@PathVariable Long id) {
        log.debug("REST request to delete Curriculum : {}", id);
        curriculumService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
