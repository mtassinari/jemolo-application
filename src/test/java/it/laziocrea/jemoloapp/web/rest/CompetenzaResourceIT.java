package it.laziocrea.jemoloapp.web.rest;

import it.laziocrea.jemoloapp.JemoloApplicationApp;
import it.laziocrea.jemoloapp.domain.Competenza;
import it.laziocrea.jemoloapp.domain.AmbitoCompetenza;
import it.laziocrea.jemoloapp.domain.AnagraficaCandidato;
import it.laziocrea.jemoloapp.repository.CompetenzaRepository;
import it.laziocrea.jemoloapp.service.CompetenzaService;
import it.laziocrea.jemoloapp.service.dto.CompetenzaDTO;
import it.laziocrea.jemoloapp.service.mapper.CompetenzaMapper;
import it.laziocrea.jemoloapp.web.rest.errors.ExceptionTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
 * Integration tests for the {@link CompetenzaResource} REST controller.
 */
@SpringBootTest(classes = JemoloApplicationApp.class)
public class CompetenzaResourceIT {
    private static final Integer DEFAULT_ANNI = 1;
    private static final Integer UPDATED_ANNI = 2;

    private static final String DEFAULT_DESCRIZIONE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIZIONE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANNI_ESPERIANZA = 1;
    private static final Integer UPDATED_ANNI_ESPERIANZA = 2;
    private static final Integer SMALLER_ANNI_ESPERIANZA = 1 - 1;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private CompetenzaRepository competenzaRepository;

    @Mock
    private CompetenzaRepository competenzaRepositoryMock;

    @Autowired
    private CompetenzaMapper competenzaMapper;

    @Mock
    private CompetenzaService competenzaServiceMock;

    @Autowired
    private CompetenzaService competenzaService;

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

    private MockMvc restCompetenzaMockMvc;

    private Competenza competenza;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompetenzaResource competenzaResource = new CompetenzaResource(competenzaService);
        this.restCompetenzaMockMvc = MockMvcBuilders.standaloneSetup(competenzaResource)
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
    public static Competenza createEntity(EntityManager em) {
        Competenza competenza = new Competenza()
            .anniEsperianza(DEFAULT_ANNI);
        // Add required entity
        AnagraficaCandidato anagraficaCandidato;
        if (TestUtil.findAll(em, AnagraficaCandidato.class).isEmpty()) {
            anagraficaCandidato = AnagraficaCandidatoResourceIT.createEntity(em);
            em.persist(anagraficaCandidato);
            em.flush();
        } else {
            anagraficaCandidato = TestUtil.findAll(em, AnagraficaCandidato.class).get(0);
        }
        competenza.setAnagrafica(anagraficaCandidato);
        // Add required entity
        AmbitoCompetenza ambitoCompetenza;
        if (TestUtil.findAll(em, AmbitoCompetenza.class).isEmpty()) {
            ambitoCompetenza = AmbitoCompetenzaResourceIT.createEntity(em);
            em.persist(ambitoCompetenza);
            em.flush();
        } else {
            ambitoCompetenza = TestUtil.findAll(em, AmbitoCompetenza.class).get(0);
        }
        competenza.setAmbitoComp(ambitoCompetenza);
        return competenza;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competenza createUpdatedEntity(EntityManager em) {
        Competenza competenza = new Competenza()
            .anniEsperianza(UPDATED_ANNI);
        // Add required entity
        AnagraficaCandidato anagraficaCandidato;
        if (TestUtil.findAll(em, AnagraficaCandidato.class).isEmpty()) {
            anagraficaCandidato = AnagraficaCandidatoResourceIT.createUpdatedEntity(em);
            em.persist(anagraficaCandidato);
            em.flush();
        } else {
            anagraficaCandidato = TestUtil.findAll(em, AnagraficaCandidato.class).get(0);
        }
        competenza.setAnagrafica(anagraficaCandidato);
        // Add required entity
        AmbitoCompetenza ambitoCompetenza;
        if (TestUtil.findAll(em, AmbitoCompetenza.class).isEmpty()) {
            ambitoCompetenza = AmbitoCompetenzaResourceIT.createUpdatedEntity(em);
            em.persist(ambitoCompetenza);
            em.flush();
        } else {
            ambitoCompetenza = TestUtil.findAll(em, AmbitoCompetenza.class).get(0);
        }
        competenza.setAmbitoComp(ambitoCompetenza);
        return competenza;
    }

    @BeforeEach
    public void initTest() {
        competenza = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompetenza() throws Exception {
        int databaseSizeBeforeCreate = competenzaRepository.findAll().size();

        // Create the Competenza
        CompetenzaDTO competenzaDTO = competenzaMapper.toDto(competenza);
        restCompetenzaMockMvc.perform(post("/api/competenzas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competenzaDTO)))
            .andExpect(status().isCreated());

        // Validate the Competenza in the database
        List<Competenza> competenzaList = competenzaRepository.findAll();
        assertThat(competenzaList).hasSize(databaseSizeBeforeCreate + 1);
        Competenza testCompetenza = competenzaList.get(competenzaList.size() - 1);
        assertThat(testCompetenza.getDescrizione()).isEqualTo(DEFAULT_DESCRIZIONE);
        assertThat(testCompetenza.getAnniEsperianza()).isEqualTo(DEFAULT_ANNI_ESPERIANZA);
        assertThat(testCompetenza.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createCompetenzaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = competenzaRepository.findAll().size();

        // Create the Competenza with an existing ID
        competenza.setId(1L);
        CompetenzaDTO competenzaDTO = competenzaMapper.toDto(competenza);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetenzaMockMvc.perform(post("/api/competenzas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competenzaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Competenza in the database
        List<Competenza> competenzaList = competenzaRepository.findAll();
        assertThat(competenzaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDescrizioneIsRequired() throws Exception {
        int databaseSizeBeforeTest = competenzaRepository.findAll().size();
        // set the field null
        competenza.setDescrizione(null);

        // Create the Competenza, which fails.
        CompetenzaDTO competenzaDTO = competenzaMapper.toDto(competenza);

        restCompetenzaMockMvc.perform(post("/api/competenzas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competenzaDTO)))
            .andExpect(status().isBadRequest());

        List<Competenza> competenzaList = competenzaRepository.findAll();
        assertThat(competenzaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnniEsperianzaIsRequired() throws Exception {
        int databaseSizeBeforeTest = competenzaRepository.findAll().size();
        // set the field null
        competenza.setAnniEsperianza(null);

        // Create the Competenza, which fails.
        CompetenzaDTO competenzaDTO = competenzaMapper.toDto(competenza);

        restCompetenzaMockMvc.perform(post("/api/competenzas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competenzaDTO)))
            .andExpect(status().isBadRequest());

        List<Competenza> competenzaList = competenzaRepository.findAll();
        assertThat(competenzaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompetenzas() throws Exception {
        // Initialize the database
        competenzaRepository.saveAndFlush(competenza);

        // Get all the competenzaList
        restCompetenzaMockMvc.perform(get("/api/competenzas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competenza.getId().intValue())))
            .andExpect(jsonPath("$.[*].descrizione").value(hasItem(DEFAULT_DESCRIZIONE.toString())))
            .andExpect(jsonPath("$.[*].anniEsperianza").value(hasItem(DEFAULT_ANNI_ESPERIANZA)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }
    
    @Test
    @Transactional
    public void getCompetenza() throws Exception {
        // Initialize the database
        competenzaRepository.saveAndFlush(competenza);

        // Get the competenza
        restCompetenzaMockMvc.perform(get("/api/competenzas/{id}", competenza.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(competenza.getId().intValue()))
            .andExpect(jsonPath("$.descrizione").value(DEFAULT_DESCRIZIONE.toString()))
            .andExpect(jsonPath("$.anniEsperianza").value(DEFAULT_ANNI_ESPERIANZA))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompetenza() throws Exception {
        // Get the competenza
        restCompetenzaMockMvc.perform(get("/api/competenzas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompetenza() throws Exception {
        // Initialize the database
        competenzaRepository.saveAndFlush(competenza);

        int databaseSizeBeforeUpdate = competenzaRepository.findAll().size();

        // Update the competenza
        Competenza updatedCompetenza = competenzaRepository.findById(competenza.getId()).get();
        // Disconnect from session so that the updates on updatedCompetenza are not directly saved in db
        em.detach(updatedCompetenza);
        updatedCompetenza
            .descrizione(UPDATED_DESCRIZIONE)
            .anniEsperianza(UPDATED_ANNI_ESPERIANZA)
            .note(UPDATED_NOTE);
        CompetenzaDTO competenzaDTO = competenzaMapper.toDto(updatedCompetenza);

        restCompetenzaMockMvc.perform(put("/api/competenzas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competenzaDTO)))
            .andExpect(status().isOk());

        // Validate the Competenza in the database
        List<Competenza> competenzaList = competenzaRepository.findAll();
        assertThat(competenzaList).hasSize(databaseSizeBeforeUpdate);
        Competenza testCompetenza = competenzaList.get(competenzaList.size() - 1);
        assertThat(testCompetenza.getDescrizione()).isEqualTo(UPDATED_DESCRIZIONE);
        assertThat(testCompetenza.getAnniEsperianza()).isEqualTo(UPDATED_ANNI_ESPERIANZA);
        assertThat(testCompetenza.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingCompetenza() throws Exception {
        int databaseSizeBeforeUpdate = competenzaRepository.findAll().size();

        // Create the Competenza
        CompetenzaDTO competenzaDTO = competenzaMapper.toDto(competenza);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetenzaMockMvc.perform(put("/api/competenzas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competenzaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Competenza in the database
        List<Competenza> competenzaList = competenzaRepository.findAll();
        assertThat(competenzaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompetenza() throws Exception {
        // Initialize the database
        competenzaRepository.saveAndFlush(competenza);

        int databaseSizeBeforeDelete = competenzaRepository.findAll().size();

        // Delete the competenza
        restCompetenzaMockMvc.perform(delete("/api/competenzas/{id}", competenza.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Competenza> competenzaList = competenzaRepository.findAll();
        assertThat(competenzaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Competenza.class);
        Competenza competenza1 = new Competenza();
        competenza1.setId(1L);
        Competenza competenza2 = new Competenza();
        competenza2.setId(competenza1.getId());
        assertThat(competenza1).isEqualTo(competenza2);
        competenza2.setId(2L);
        assertThat(competenza1).isNotEqualTo(competenza2);
        competenza1.setId(null);
        assertThat(competenza1).isNotEqualTo(competenza2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompetenzaDTO.class);
        CompetenzaDTO competenzaDTO1 = new CompetenzaDTO();
        competenzaDTO1.setId(1L);
        CompetenzaDTO competenzaDTO2 = new CompetenzaDTO();
        assertThat(competenzaDTO1).isNotEqualTo(competenzaDTO2);
        competenzaDTO2.setId(competenzaDTO1.getId());
        assertThat(competenzaDTO1).isEqualTo(competenzaDTO2);
        competenzaDTO2.setId(2L);
        assertThat(competenzaDTO1).isNotEqualTo(competenzaDTO2);
        competenzaDTO1.setId(null);
        assertThat(competenzaDTO1).isNotEqualTo(competenzaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(competenzaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(competenzaMapper.fromId(null)).isNull();
    }
}
