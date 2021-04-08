package it.laziocrea.jemoloapp.web.rest;

import it.laziocrea.jemoloapp.JemoloApplicationApp;
import it.laziocrea.jemoloapp.domain.Candidato;
import it.laziocrea.jemoloapp.repository.CandidatoRepository;
import it.laziocrea.jemoloapp.repository.CustomAuditEventRepository;
import it.laziocrea.jemoloapp.service.CandidatoService;
import it.laziocrea.jemoloapp.service.MailService2;
import it.laziocrea.jemoloapp.service.dto.CandidatoDTO;
import it.laziocrea.jemoloapp.service.mapper.CandidatoMapper;
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
 * Integration tests for the {@link CandidatoResource} REST controller.
 */
@SpringBootTest(classes = JemoloApplicationApp.class)
public class CandidatoResourceIT {

    private static final String DEFAULT_NOMINATIVO = "AAAAAAAAAA";
    private static final String UPDATED_NOMINATIVO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_COGNOME = "AAAAAAAAAA";
    private static final String UPDATED_COGNOME = "BBBBBBBBBB";

    private static final String DEFAULT_CODICE_FISCALE = "eteiod05x46l702d";
    private static final String UPDATED_CODICE_FISCALE = "tensts37p58h086x";

    private static final String DEFAULT_E_MAIL = "b@r.NfRT";
    private static final String UPDATED_E_MAIL = "s@M-.VfDsQu";

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Autowired
    private CandidatoMapper candidatoMapper;

    @Autowired
    private CandidatoService candidatoService;
    @Autowired
    private MailService2 mailService;

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

    private MockMvc restCandidatoMockMvc;

    private Candidato candidato;
    
    @Autowired
    private CustomAuditEventRepository eventRepository;
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CandidatoResource candidatoResource = new CandidatoResource(eventRepository,candidatoMapper,mailService,candidatoRepository,candidatoService);
        this.restCandidatoMockMvc = MockMvcBuilders.standaloneSetup(candidatoResource)
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
    public static Candidato createEntity(EntityManager em) {
        Candidato candidato = new Candidato()
            .nominativo(DEFAULT_NOMINATIVO)
            .nome(DEFAULT_NOME)
            .cognome(DEFAULT_COGNOME)
            .codiceFiscale(DEFAULT_CODICE_FISCALE)
            .email(DEFAULT_E_MAIL);
        return candidato;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Candidato createUpdatedEntity(EntityManager em) {
        Candidato candidato = new Candidato()
            .nominativo(UPDATED_NOMINATIVO)
            .nome(UPDATED_NOME)
            .cognome(UPDATED_COGNOME)
            .codiceFiscale(UPDATED_CODICE_FISCALE)
            .email(UPDATED_E_MAIL);
        return candidato;
    }

    @BeforeEach
    public void initTest() {
        candidato = createEntity(em);
    }

    @Test
    @Transactional
    public void createCandidato() throws Exception {
        int databaseSizeBeforeCreate = candidatoRepository.findAll().size();

        // Create the Candidato
        CandidatoDTO candidatoDTO = candidatoMapper.toDto(candidato);
        restCandidatoMockMvc.perform(post("/api/candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatoDTO)))
            .andExpect(status().isCreated());

        // Validate the Candidato in the database
        List<Candidato> candidatoList = candidatoRepository.findAll();
        assertThat(candidatoList).hasSize(databaseSizeBeforeCreate + 1);
        Candidato testCandidato = candidatoList.get(candidatoList.size() - 1);
        assertThat(testCandidato.getNominativo()).isEqualTo(DEFAULT_NOMINATIVO);
        assertThat(testCandidato.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCandidato.getCognome()).isEqualTo(DEFAULT_COGNOME);
        assertThat(testCandidato.getCodiceFiscale()).isEqualTo(DEFAULT_CODICE_FISCALE);
        assertThat(testCandidato.getEmail()).isEqualTo(DEFAULT_E_MAIL);
    }

    @Test
    @Transactional
    public void createCandidatoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = candidatoRepository.findAll().size();

        // Create the Candidato with an existing ID
        candidato.setId(1L);
        CandidatoDTO candidatoDTO = candidatoMapper.toDto(candidato);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandidatoMockMvc.perform(post("/api/candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Candidato in the database
        List<Candidato> candidatoList = candidatoRepository.findAll();
        assertThat(candidatoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidatoRepository.findAll().size();
        // set the field null
        candidato.setNome(null);

        // Create the Candidato, which fails.
        CandidatoDTO candidatoDTO = candidatoMapper.toDto(candidato);

        restCandidatoMockMvc.perform(post("/api/candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatoDTO)))
            .andExpect(status().isBadRequest());

        List<Candidato> candidatoList = candidatoRepository.findAll();
        assertThat(candidatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCognomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidatoRepository.findAll().size();
        // set the field null
        candidato.setCognome(null);

        // Create the Candidato, which fails.
        CandidatoDTO candidatoDTO = candidatoMapper.toDto(candidato);

        restCandidatoMockMvc.perform(post("/api/candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatoDTO)))
            .andExpect(status().isBadRequest());

        List<Candidato> candidatoList = candidatoRepository.findAll();
        assertThat(candidatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodiceFiscaleIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidatoRepository.findAll().size();
        // set the field null
        candidato.setCodiceFiscale(null);

        // Create the Candidato, which fails.
        CandidatoDTO candidatoDTO = candidatoMapper.toDto(candidato);

        restCandidatoMockMvc.perform(post("/api/candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatoDTO)))
            .andExpect(status().isBadRequest());

        List<Candidato> candidatoList = candidatoRepository.findAll();
        assertThat(candidatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkeMailIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidatoRepository.findAll().size();
        // set the field null
        candidato.setEmail(null);

        // Create the Candidato, which fails.
        CandidatoDTO candidatoDTO = candidatoMapper.toDto(candidato);

        restCandidatoMockMvc.perform(post("/api/candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatoDTO)))
            .andExpect(status().isBadRequest());

        List<Candidato> candidatoList = candidatoRepository.findAll();
        assertThat(candidatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCandidatoes() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList
        restCandidatoMockMvc.perform(get("/api/candidatoes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidato.getId().intValue())))
            .andExpect(jsonPath("$.[*].nominativo").value(hasItem(DEFAULT_NOMINATIVO.toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].cognome").value(hasItem(DEFAULT_COGNOME.toString())))
            .andExpect(jsonPath("$.[*].codiceFiscale").value(hasItem(DEFAULT_CODICE_FISCALE.toString())))
            .andExpect(jsonPath("$.[*].eMail").value(hasItem(DEFAULT_E_MAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getCandidato() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get the candidato
        restCandidatoMockMvc.perform(get("/api/candidatoes/{id}", candidato.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(candidato.getId().intValue()))
            .andExpect(jsonPath("$.nominativo").value(DEFAULT_NOMINATIVO.toString()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.cognome").value(DEFAULT_COGNOME.toString()))
            .andExpect(jsonPath("$.codiceFiscale").value(DEFAULT_CODICE_FISCALE.toString()))
            .andExpect(jsonPath("$.eMail").value(DEFAULT_E_MAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCandidato() throws Exception {
        // Get the candidato
        restCandidatoMockMvc.perform(get("/api/candidatoes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandidato() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        int databaseSizeBeforeUpdate = candidatoRepository.findAll().size();

        // Update the candidato
        Candidato updatedCandidato = candidatoRepository.findById(candidato.getId()).get();
        // Disconnect from session so that the updates on updatedCandidato are not directly saved in db
        em.detach(updatedCandidato);
        updatedCandidato
            .nominativo(UPDATED_NOMINATIVO)
            .nome(UPDATED_NOME)
            .cognome(UPDATED_COGNOME)
            .codiceFiscale(UPDATED_CODICE_FISCALE)
            .email(UPDATED_E_MAIL);
        CandidatoDTO candidatoDTO = candidatoMapper.toDto(updatedCandidato);

        restCandidatoMockMvc.perform(put("/api/candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatoDTO)))
            .andExpect(status().isOk());

        // Validate the Candidato in the database
        List<Candidato> candidatoList = candidatoRepository.findAll();
        assertThat(candidatoList).hasSize(databaseSizeBeforeUpdate);
        Candidato testCandidato = candidatoList.get(candidatoList.size() - 1);
        assertThat(testCandidato.getNominativo()).isEqualTo(UPDATED_NOMINATIVO);
        assertThat(testCandidato.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCandidato.getCognome()).isEqualTo(UPDATED_COGNOME);
        assertThat(testCandidato.getCodiceFiscale()).isEqualTo(UPDATED_CODICE_FISCALE);
        assertThat(testCandidato.getEmail()).isEqualTo(UPDATED_E_MAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingCandidato() throws Exception {
        int databaseSizeBeforeUpdate = candidatoRepository.findAll().size();

        // Create the Candidato
        CandidatoDTO candidatoDTO = candidatoMapper.toDto(candidato);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidatoMockMvc.perform(put("/api/candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Candidato in the database
        List<Candidato> candidatoList = candidatoRepository.findAll();
        assertThat(candidatoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCandidato() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        int databaseSizeBeforeDelete = candidatoRepository.findAll().size();

        // Delete the candidato
        restCandidatoMockMvc.perform(delete("/api/candidatoes/{id}", candidato.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Candidato> candidatoList = candidatoRepository.findAll();
        assertThat(candidatoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Candidato.class);
        Candidato candidato1 = new Candidato();
        candidato1.setId(1L);
        Candidato candidato2 = new Candidato();
        candidato2.setId(candidato1.getId());
        assertThat(candidato1).isEqualTo(candidato2);
        candidato2.setId(2L);
        assertThat(candidato1).isNotEqualTo(candidato2);
        candidato1.setId(null);
        assertThat(candidato1).isNotEqualTo(candidato2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CandidatoDTO.class);
        CandidatoDTO candidatoDTO1 = new CandidatoDTO();
        candidatoDTO1.setId(1L);
        CandidatoDTO candidatoDTO2 = new CandidatoDTO();
        assertThat(candidatoDTO1).isNotEqualTo(candidatoDTO2);
        candidatoDTO2.setId(candidatoDTO1.getId());
        assertThat(candidatoDTO1).isEqualTo(candidatoDTO2);
        candidatoDTO2.setId(2L);
        assertThat(candidatoDTO1).isNotEqualTo(candidatoDTO2);
        candidatoDTO1.setId(null);
        assertThat(candidatoDTO1).isNotEqualTo(candidatoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(candidatoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(candidatoMapper.fromId(null)).isNull();
    }
}
