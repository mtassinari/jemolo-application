package it.laziocrea.jemoloapp.web.rest;

import it.laziocrea.jemoloapp.JemoloApplicationApp;
import it.laziocrea.jemoloapp.domain.AmbitoCompetenza;
import it.laziocrea.jemoloapp.repository.AmbitoCompetenzaRepository;
import it.laziocrea.jemoloapp.service.AmbitoCompetenzaService;
import it.laziocrea.jemoloapp.service.dto.AmbitoCompetenzaDTO;
import it.laziocrea.jemoloapp.service.mapper.AmbitoCompetenzaMapper;
import it.laziocrea.jemoloapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static it.laziocrea.jemoloapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AmbitoCompetenzaResource} REST controller.
 */
@SpringBootTest(classes = JemoloApplicationApp.class)
public class AmbitoCompetenzaResourceIT {

    private static final String DEFAULT_DESCRIZIONE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIZIONE = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    @Autowired
    private AmbitoCompetenzaRepository ambitoCompetenzaRepository;

    @Autowired
    private AmbitoCompetenzaMapper ambitoCompetenzaMapper;

    @Autowired
    private AmbitoCompetenzaService ambitoCompetenzaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAmbitoCompetenzaMockMvc;

    private AmbitoCompetenza ambitoCompetenza;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AmbitoCompetenzaResource ambitoCompetenzaResource = new AmbitoCompetenzaResource(ambitoCompetenzaService);
        this.restAmbitoCompetenzaMockMvc = MockMvcBuilders.standaloneSetup(ambitoCompetenzaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AmbitoCompetenza createEntity(EntityManager em) {
        AmbitoCompetenza ambitoCompetenza = new AmbitoCompetenza()
            .descrizione(DEFAULT_DESCRIZIONE)
            .tipo(DEFAULT_TIPO);
        return ambitoCompetenza;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AmbitoCompetenza createUpdatedEntity(EntityManager em) {
        AmbitoCompetenza ambitoCompetenza = new AmbitoCompetenza()
            .descrizione(UPDATED_DESCRIZIONE)
            .tipo(UPDATED_TIPO);
        return ambitoCompetenza;
    }

    @BeforeEach
    public void initTest() {
        ambitoCompetenza = createEntity(em);
    }

    @Test
    @Transactional
    public void createAmbitoCompetenza() throws Exception {
        int databaseSizeBeforeCreate = ambitoCompetenzaRepository.findAll().size();

        // Create the AmbitoCompetenza
        AmbitoCompetenzaDTO ambitoCompetenzaDTO = ambitoCompetenzaMapper.toDto(ambitoCompetenza);
        restAmbitoCompetenzaMockMvc.perform(post("/api/ambito-competenzas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ambitoCompetenzaDTO)))
            .andExpect(status().isCreated());

        // Validate the AmbitoCompetenza in the database
        List<AmbitoCompetenza> ambitoCompetenzaList = ambitoCompetenzaRepository.findAll();
        assertThat(ambitoCompetenzaList).hasSize(databaseSizeBeforeCreate + 1);
        AmbitoCompetenza testAmbitoCompetenza = ambitoCompetenzaList.get(ambitoCompetenzaList.size() - 1);
        assertThat(testAmbitoCompetenza.getDescrizione()).isEqualTo(DEFAULT_DESCRIZIONE);
        assertThat(testAmbitoCompetenza.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    public void createAmbitoCompetenzaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ambitoCompetenzaRepository.findAll().size();

        // Create the AmbitoCompetenza with an existing ID
        ambitoCompetenza.setId(1L);
        AmbitoCompetenzaDTO ambitoCompetenzaDTO = ambitoCompetenzaMapper.toDto(ambitoCompetenza);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmbitoCompetenzaMockMvc.perform(post("/api/ambito-competenzas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ambitoCompetenzaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AmbitoCompetenza in the database
        List<AmbitoCompetenza> ambitoCompetenzaList = ambitoCompetenzaRepository.findAll();
        assertThat(ambitoCompetenzaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDescrizioneIsRequired() throws Exception {
        int databaseSizeBeforeTest = ambitoCompetenzaRepository.findAll().size();
        // set the field null
        ambitoCompetenza.setDescrizione(null);

        // Create the AmbitoCompetenza, which fails.
        AmbitoCompetenzaDTO ambitoCompetenzaDTO = ambitoCompetenzaMapper.toDto(ambitoCompetenza);

        restAmbitoCompetenzaMockMvc.perform(post("/api/ambito-competenzas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ambitoCompetenzaDTO)))
            .andExpect(status().isBadRequest());

        List<AmbitoCompetenza> ambitoCompetenzaList = ambitoCompetenzaRepository.findAll();
        assertThat(ambitoCompetenzaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAmbitoCompetenzas() throws Exception {
        // Initialize the database
        ambitoCompetenzaRepository.saveAndFlush(ambitoCompetenza);

        // Get all the ambitoCompetenzaList
        restAmbitoCompetenzaMockMvc.perform(get("/api/ambito-competenzas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ambitoCompetenza.getId().intValue())))
            .andExpect(jsonPath("$.[*].descrizione").value(hasItem(DEFAULT_DESCRIZIONE)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)));
    }
    
    @Test
    @Transactional
    public void getAmbitoCompetenza() throws Exception {
        // Initialize the database
        ambitoCompetenzaRepository.saveAndFlush(ambitoCompetenza);

        // Get the ambitoCompetenza
        restAmbitoCompetenzaMockMvc.perform(get("/api/ambito-competenzas/{id}", ambitoCompetenza.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ambitoCompetenza.getId().intValue()))
            .andExpect(jsonPath("$.descrizione").value(DEFAULT_DESCRIZIONE))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO));
    }

    @Test
    @Transactional
    public void getNonExistingAmbitoCompetenza() throws Exception {
        // Get the ambitoCompetenza
        restAmbitoCompetenzaMockMvc.perform(get("/api/ambito-competenzas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAmbitoCompetenza() throws Exception {
        // Initialize the database
        ambitoCompetenzaRepository.saveAndFlush(ambitoCompetenza);

        int databaseSizeBeforeUpdate = ambitoCompetenzaRepository.findAll().size();

        // Update the ambitoCompetenza
        AmbitoCompetenza updatedAmbitoCompetenza = ambitoCompetenzaRepository.findById(ambitoCompetenza.getId()).get();
        // Disconnect from session so that the updates on updatedAmbitoCompetenza are not directly saved in db
        em.detach(updatedAmbitoCompetenza);
        updatedAmbitoCompetenza
            .descrizione(UPDATED_DESCRIZIONE)
            .tipo(UPDATED_TIPO);
        AmbitoCompetenzaDTO ambitoCompetenzaDTO = ambitoCompetenzaMapper.toDto(updatedAmbitoCompetenza);

        restAmbitoCompetenzaMockMvc.perform(put("/api/ambito-competenzas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ambitoCompetenzaDTO)))
            .andExpect(status().isOk());

        // Validate the AmbitoCompetenza in the database
        List<AmbitoCompetenza> ambitoCompetenzaList = ambitoCompetenzaRepository.findAll();
        assertThat(ambitoCompetenzaList).hasSize(databaseSizeBeforeUpdate);
        AmbitoCompetenza testAmbitoCompetenza = ambitoCompetenzaList.get(ambitoCompetenzaList.size() - 1);
        assertThat(testAmbitoCompetenza.getDescrizione()).isEqualTo(UPDATED_DESCRIZIONE);
        assertThat(testAmbitoCompetenza.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void updateNonExistingAmbitoCompetenza() throws Exception {
        int databaseSizeBeforeUpdate = ambitoCompetenzaRepository.findAll().size();

        // Create the AmbitoCompetenza
        AmbitoCompetenzaDTO ambitoCompetenzaDTO = ambitoCompetenzaMapper.toDto(ambitoCompetenza);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmbitoCompetenzaMockMvc.perform(put("/api/ambito-competenzas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ambitoCompetenzaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AmbitoCompetenza in the database
        List<AmbitoCompetenza> ambitoCompetenzaList = ambitoCompetenzaRepository.findAll();
        assertThat(ambitoCompetenzaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAmbitoCompetenza() throws Exception {
        // Initialize the database
        ambitoCompetenzaRepository.saveAndFlush(ambitoCompetenza);

        int databaseSizeBeforeDelete = ambitoCompetenzaRepository.findAll().size();

        // Delete the ambitoCompetenza
        restAmbitoCompetenzaMockMvc.perform(delete("/api/ambito-competenzas/{id}", ambitoCompetenza.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AmbitoCompetenza> ambitoCompetenzaList = ambitoCompetenzaRepository.findAll();
        assertThat(ambitoCompetenzaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmbitoCompetenza.class);
        AmbitoCompetenza ambitoCompetenza1 = new AmbitoCompetenza();
        ambitoCompetenza1.setId(1L);
        AmbitoCompetenza ambitoCompetenza2 = new AmbitoCompetenza();
        ambitoCompetenza2.setId(ambitoCompetenza1.getId());
        assertThat(ambitoCompetenza1).isEqualTo(ambitoCompetenza2);
        ambitoCompetenza2.setId(2L);
        assertThat(ambitoCompetenza1).isNotEqualTo(ambitoCompetenza2);
        ambitoCompetenza1.setId(null);
        assertThat(ambitoCompetenza1).isNotEqualTo(ambitoCompetenza2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmbitoCompetenzaDTO.class);
        AmbitoCompetenzaDTO ambitoCompetenzaDTO1 = new AmbitoCompetenzaDTO();
        ambitoCompetenzaDTO1.setId(1L);
        AmbitoCompetenzaDTO ambitoCompetenzaDTO2 = new AmbitoCompetenzaDTO();
        assertThat(ambitoCompetenzaDTO1).isNotEqualTo(ambitoCompetenzaDTO2);
        ambitoCompetenzaDTO2.setId(ambitoCompetenzaDTO1.getId());
        assertThat(ambitoCompetenzaDTO1).isEqualTo(ambitoCompetenzaDTO2);
        ambitoCompetenzaDTO2.setId(2L);
        assertThat(ambitoCompetenzaDTO1).isNotEqualTo(ambitoCompetenzaDTO2);
        ambitoCompetenzaDTO1.setId(null);
        assertThat(ambitoCompetenzaDTO1).isNotEqualTo(ambitoCompetenzaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ambitoCompetenzaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ambitoCompetenzaMapper.fromId(null)).isNull();
    }
}
