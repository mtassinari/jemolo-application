package it.laziocrea.jemoloapp.web.rest;

import it.laziocrea.jemoloapp.JemoloApplicationApp;
import it.laziocrea.jemoloapp.domain.AnagraficaCandidato;
import it.laziocrea.jemoloapp.domain.Candidato;
import it.laziocrea.jemoloapp.repository.AnagraficaCandidatoRepository;
import it.laziocrea.jemoloapp.service.AnagraficaCandidatoQueryService;
import it.laziocrea.jemoloapp.service.AnagraficaCandidatoService;
import it.laziocrea.jemoloapp.service.AnagraficaService;
import it.laziocrea.jemoloapp.service.dto.AnagraficaCandidatoDTO;
import it.laziocrea.jemoloapp.service.mapper.AnagraficaCandidatoMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static it.laziocrea.jemoloapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AnagraficaCandidatoResource} REST controller.
 */
@SpringBootTest(classes = JemoloApplicationApp.class)
public class AnagraficaCandidatoResourceIT {

    private static final String DEFAULT_LUOGO_NASCITA = "AAAAAAAAAA";
    private static final String UPDATED_LUOGO_NASCITA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_NASCITA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_NASCITA = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_NASCITA = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_PROFESSIONE = "AAAAAAAAAA";
    private static final String UPDATED_PROFESSIONE = "BBBBBBBBBB";

    private static final String DEFAULT_PARTITA_IVA = "84644562282";
    private static final String UPDATED_PARTITA_IVA = "30521290532";

    private static final String DEFAULT_DATORE_LAVORO = "AAAAAAAAAA";
    private static final String DEFAULT_INDIRIZZO_DATORE_LAVORO = "AAAAAAAAAA";
    private static final String DEFAULT_NUMERO_TELEFONO_FISSO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_TELEFONO_FISSO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_TELEFONO_CELLULARE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_TELEFONO_CELLULARE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_TELEFONO_ALTRO = "AAAAAAAAAA";
    private static final String DEFAULT_INDIRIZZO_PEC = "S@No.MoPHF^";
    private static final String UPDATED_INDIRIZZO_PEC = "D@JV.hAQ";

    private static final Boolean DEFAULT_CITTADINO_UNIONE_EUROPEA = false;
    private static final String DEFAULT_INDIRIZZO_RESIDENZA = "AAAAAAAAAA";
    private static final String UPDATED_INDIRIZZO_RESIDENZA = "BBBBBBBBBB";

    private static final String DEFAULT_CAP_RESIDENZA = "AAAAAAAAAA";
    private static final String UPDATED_CAP_RESIDENZA = "BBBBBBBBBB";

    private static final String DEFAULT_COMUNE_RESIDENZA = "AAAAAAAAAA";
    private static final String UPDATED_COMUNE_RESIDENZA = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCIA_RESIDENZA = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCIA_RESIDENZA = "BBBBBBBBBB";

    private static final String DEFAULT_TITOLO_STUDIO = "AAAAAAAAAA";
    private static final String DEFAULT_TITOLO_STUDIO_TIPOLOGIA = "AAAAAAAAAA";
    private static final String DEFAULT_TITOLO_STUDIO_LUOGO = "AAAAAAAAAA";
    private static final String DEFAULT_TITOLO_STUDIO_ANNO = "AAAAAAAAAA";
    private static final String DEFAULT_TITOLO_STUDIO_VOTO = "AAAAAAAAAA";
    private static final Boolean DEFAULT_SPECIALIZZAZIONE_UNIVERSITARIA = false;
    private static final Boolean DEFAULT_ISCRIZIONE_ALBO_PROFESSIONALE = false;
    private static final Boolean DEFAULT_OPERATORE_AMBITO_TECNICO_PROFESSIONALE = false;
    private static final Boolean DEFAULT_ESPERIENZA_BIENNALE = false;
    private static final String DEFAULT_CV = "AAAAAAAAAA";
    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private AnagraficaCandidatoRepository anagraficaCandidatoRepository;

    @Autowired
    private AnagraficaCandidatoMapper anagraficaCandidatoMapper;

    @Autowired
    private AnagraficaCandidatoService anagraficaCandidatoService;
    
    @Autowired
    private AnagraficaService anagraficaService;
    
    @Autowired
    private AnagraficaCandidatoQueryService anagraficaCandidatoQueryService;

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

    private MockMvc restAnagraficaCandidatoMockMvc;

    private AnagraficaCandidato anagraficaCandidato;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnagraficaCandidatoResource anagraficaCandidatoResource = new AnagraficaCandidatoResource(anagraficaService, anagraficaCandidatoService, anagraficaCandidatoQueryService);
        this.restAnagraficaCandidatoMockMvc = MockMvcBuilders.standaloneSetup(anagraficaCandidatoResource)
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
    public static AnagraficaCandidato createEntity(EntityManager em) {
        AnagraficaCandidato anagraficaCandidato = new AnagraficaCandidato()
            .luogoNascita(DEFAULT_LUOGO_NASCITA)
            .dataNascita(DEFAULT_DATA_NASCITA)
            .professione(DEFAULT_PROFESSIONE)
            .partitaIva(DEFAULT_PARTITA_IVA)
            .numeroTelefonoFisso(DEFAULT_NUMERO_TELEFONO_FISSO)
            .numeroTelefonoCellulare(DEFAULT_NUMERO_TELEFONO_CELLULARE)
            .indirizzoPec(DEFAULT_INDIRIZZO_PEC)
            .indirizzoResidenza(DEFAULT_INDIRIZZO_RESIDENZA)
            .capResidenza(DEFAULT_CAP_RESIDENZA)
            .comuneResidenza(DEFAULT_COMUNE_RESIDENZA)
            .provinciaResidenza(DEFAULT_PROVINCIA_RESIDENZA)
            .note(DEFAULT_NOTE);
        // Add required entity
        Candidato candidato;
        if (TestUtil.findAll(em, Candidato.class).isEmpty()) {
            candidato = CandidatoResourceIT.createEntity(em);
            em.persist(candidato);
            em.flush();
        } else {
            candidato = TestUtil.findAll(em, Candidato.class).get(0);
        }
        anagraficaCandidato.setCandidato(candidato);
        return anagraficaCandidato;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnagraficaCandidato createUpdatedEntity(EntityManager em) {
        AnagraficaCandidato anagraficaCandidato = new AnagraficaCandidato()
            .luogoNascita(UPDATED_LUOGO_NASCITA)
            .dataNascita(UPDATED_DATA_NASCITA)
            .professione(UPDATED_PROFESSIONE)
            .partitaIva(UPDATED_PARTITA_IVA)
            .numeroTelefonoFisso(UPDATED_NUMERO_TELEFONO_FISSO)
            .numeroTelefonoCellulare(UPDATED_NUMERO_TELEFONO_CELLULARE)
            .indirizzoPec(UPDATED_INDIRIZZO_PEC)
            .indirizzoResidenza(UPDATED_INDIRIZZO_RESIDENZA)
            .capResidenza(UPDATED_CAP_RESIDENZA)
            .comuneResidenza(UPDATED_COMUNE_RESIDENZA)
            .provinciaResidenza(UPDATED_PROVINCIA_RESIDENZA)
            .note(UPDATED_NOTE);
        // Add required entity
        Candidato candidato;
        if (TestUtil.findAll(em, Candidato.class).isEmpty()) {
            candidato = CandidatoResourceIT.createUpdatedEntity(em);
            em.persist(candidato);
            em.flush();
        } else {
            candidato = TestUtil.findAll(em, Candidato.class).get(0);
        }
        anagraficaCandidato.setCandidato(candidato);
        return anagraficaCandidato;
    }

    @BeforeEach
    public void initTest() {
        anagraficaCandidato = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnagraficaCandidato() throws Exception {
        int databaseSizeBeforeCreate = anagraficaCandidatoRepository.findAll().size();

        // Create the AnagraficaCandidato
        AnagraficaCandidatoDTO anagraficaCandidatoDTO = anagraficaCandidatoMapper.toDto(anagraficaCandidato);
        restAnagraficaCandidatoMockMvc.perform(post("/api/anagrafica-candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anagraficaCandidatoDTO)))
            .andExpect(status().isCreated());

        // Validate the AnagraficaCandidato in the database
        List<AnagraficaCandidato> anagraficaCandidatoList = anagraficaCandidatoRepository.findAll();
        assertThat(anagraficaCandidatoList).hasSize(databaseSizeBeforeCreate + 1);
        AnagraficaCandidato testAnagraficaCandidato = anagraficaCandidatoList.get(anagraficaCandidatoList.size() - 1);
        assertThat(testAnagraficaCandidato.getLuogoNascita()).isEqualTo(DEFAULT_LUOGO_NASCITA);
        assertThat(testAnagraficaCandidato.getDataNascita()).isEqualTo(DEFAULT_DATA_NASCITA);
        assertThat(testAnagraficaCandidato.getProfessione()).isEqualTo(DEFAULT_PROFESSIONE);
        assertThat(testAnagraficaCandidato.getPartitaIva()).isEqualTo(DEFAULT_PARTITA_IVA);
        assertThat(testAnagraficaCandidato.getNumeroTelefonoFisso()).isEqualTo(DEFAULT_NUMERO_TELEFONO_FISSO);
        assertThat(testAnagraficaCandidato.getNumeroTelefonoCellulare()).isEqualTo(DEFAULT_NUMERO_TELEFONO_CELLULARE);
        assertThat(testAnagraficaCandidato.getIndirizzoPec()).isEqualTo(DEFAULT_INDIRIZZO_PEC);
        assertThat(testAnagraficaCandidato.getIndirizzoResidenza()).isEqualTo(DEFAULT_INDIRIZZO_RESIDENZA);
        assertThat(testAnagraficaCandidato.getCapResidenza()).isEqualTo(DEFAULT_CAP_RESIDENZA);
        assertThat(testAnagraficaCandidato.getComuneResidenza()).isEqualTo(DEFAULT_COMUNE_RESIDENZA);
        assertThat(testAnagraficaCandidato.getProvinciaResidenza()).isEqualTo(DEFAULT_PROVINCIA_RESIDENZA);
        assertThat(testAnagraficaCandidato.getNote()).isEqualTo(DEFAULT_NOTE);

        // Validate the id for MapsId, the ids must be same
        assertThat(testAnagraficaCandidato.getId()).isEqualTo(testAnagraficaCandidato.getCandidato().getId());
    }

    @Test
    @Transactional
    public void createAnagraficaCandidatoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = anagraficaCandidatoRepository.findAll().size();

        // Create the AnagraficaCandidato with an existing ID
        anagraficaCandidato.setId(1L);
        AnagraficaCandidatoDTO anagraficaCandidatoDTO = anagraficaCandidatoMapper.toDto(anagraficaCandidato);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnagraficaCandidatoMockMvc.perform(post("/api/anagrafica-candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anagraficaCandidatoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnagraficaCandidato in the database
        List<AnagraficaCandidato> anagraficaCandidatoList = anagraficaCandidatoRepository.findAll();
        assertThat(anagraficaCandidatoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void updateAnagraficaCandidatoMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        anagraficaCandidatoRepository.saveAndFlush(anagraficaCandidato);
        int databaseSizeBeforeCreate = anagraficaCandidatoRepository.findAll().size();

        // Add a new parent entity
        Candidato candidato = CandidatoResourceIT.createUpdatedEntity(em);
        em.persist(candidato);
        em.flush();

        // Load the anagraficaCandidato
        AnagraficaCandidato updatedAnagraficaCandidato = anagraficaCandidatoRepository.findById(anagraficaCandidato.getId()).get();
        // Disconnect from session so that the updates on updatedAnagraficaCandidato are not directly saved in db
        em.detach(updatedAnagraficaCandidato);

        // Update the Candidato with new association value
        updatedAnagraficaCandidato.setCandidato(candidato);
        AnagraficaCandidatoDTO updatedAnagraficaCandidatoDTO = anagraficaCandidatoMapper.toDto(updatedAnagraficaCandidato);

        // Update the entity
        restAnagraficaCandidatoMockMvc.perform(put("/api/anagrafica-candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnagraficaCandidatoDTO)))
            .andExpect(status().isOk());

        // Validate the AnagraficaCandidato in the database
        List<AnagraficaCandidato> anagraficaCandidatoList = anagraficaCandidatoRepository.findAll();
        assertThat(anagraficaCandidatoList).hasSize(databaseSizeBeforeCreate);
        anagraficaCandidatoList.get(anagraficaCandidatoList.size() - 1);

        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testAnagraficaCandidato.getId()).isEqualTo(testAnagraficaCandidato.getCandidato().getId());
    }

    @Test
    @Transactional
    public void checkLuogoNascitaIsRequired() throws Exception {
        int databaseSizeBeforeTest = anagraficaCandidatoRepository.findAll().size();
        // set the field null
        anagraficaCandidato.setLuogoNascita(null);

        // Create the AnagraficaCandidato, which fails.
        AnagraficaCandidatoDTO anagraficaCandidatoDTO = anagraficaCandidatoMapper.toDto(anagraficaCandidato);

        restAnagraficaCandidatoMockMvc.perform(post("/api/anagrafica-candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anagraficaCandidatoDTO)))
            .andExpect(status().isBadRequest());

        List<AnagraficaCandidato> anagraficaCandidatoList = anagraficaCandidatoRepository.findAll();
        assertThat(anagraficaCandidatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataNascitaIsRequired() throws Exception {
        int databaseSizeBeforeTest = anagraficaCandidatoRepository.findAll().size();
        // set the field null
        anagraficaCandidato.setDataNascita(null);

        // Create the AnagraficaCandidato, which fails.
        AnagraficaCandidatoDTO anagraficaCandidatoDTO = anagraficaCandidatoMapper.toDto(anagraficaCandidato);

        restAnagraficaCandidatoMockMvc.perform(post("/api/anagrafica-candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anagraficaCandidatoDTO)))
            .andExpect(status().isBadRequest());

        List<AnagraficaCandidato> anagraficaCandidatoList = anagraficaCandidatoRepository.findAll();
        assertThat(anagraficaCandidatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProfessioneIsRequired() throws Exception {
        int databaseSizeBeforeTest = anagraficaCandidatoRepository.findAll().size();
        // set the field null
        anagraficaCandidato.setProfessione(null);

        // Create the AnagraficaCandidato, which fails.
        AnagraficaCandidatoDTO anagraficaCandidatoDTO = anagraficaCandidatoMapper.toDto(anagraficaCandidato);

        restAnagraficaCandidatoMockMvc.perform(post("/api/anagrafica-candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anagraficaCandidatoDTO)))
            .andExpect(status().isBadRequest());

        List<AnagraficaCandidato> anagraficaCandidatoList = anagraficaCandidatoRepository.findAll();
        assertThat(anagraficaCandidatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIndirizzoPecIsRequired() throws Exception {
        int databaseSizeBeforeTest = anagraficaCandidatoRepository.findAll().size();
        // set the field null
        anagraficaCandidato.setIndirizzoPec(null);

        // Create the AnagraficaCandidato, which fails.
        AnagraficaCandidatoDTO anagraficaCandidatoDTO = anagraficaCandidatoMapper.toDto(anagraficaCandidato);

        restAnagraficaCandidatoMockMvc.perform(post("/api/anagrafica-candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anagraficaCandidatoDTO)))
            .andExpect(status().isBadRequest());

        List<AnagraficaCandidato> anagraficaCandidatoList = anagraficaCandidatoRepository.findAll();
        assertThat(anagraficaCandidatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIndirizzoResidenzaIsRequired() throws Exception {
        int databaseSizeBeforeTest = anagraficaCandidatoRepository.findAll().size();
        // set the field null
        anagraficaCandidato.setIndirizzoResidenza(null);

        // Create the AnagraficaCandidato, which fails.
        AnagraficaCandidatoDTO anagraficaCandidatoDTO = anagraficaCandidatoMapper.toDto(anagraficaCandidato);

        restAnagraficaCandidatoMockMvc.perform(post("/api/anagrafica-candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anagraficaCandidatoDTO)))
            .andExpect(status().isBadRequest());

        List<AnagraficaCandidato> anagraficaCandidatoList = anagraficaCandidatoRepository.findAll();
        assertThat(anagraficaCandidatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCapResidenzaIsRequired() throws Exception {
        int databaseSizeBeforeTest = anagraficaCandidatoRepository.findAll().size();
        // set the field null
        anagraficaCandidato.setCapResidenza(null);

        // Create the AnagraficaCandidato, which fails.
        AnagraficaCandidatoDTO anagraficaCandidatoDTO = anagraficaCandidatoMapper.toDto(anagraficaCandidato);

        restAnagraficaCandidatoMockMvc.perform(post("/api/anagrafica-candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anagraficaCandidatoDTO)))
            .andExpect(status().isBadRequest());

        List<AnagraficaCandidato> anagraficaCandidatoList = anagraficaCandidatoRepository.findAll();
        assertThat(anagraficaCandidatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkComuneResidenzaIsRequired() throws Exception {
        int databaseSizeBeforeTest = anagraficaCandidatoRepository.findAll().size();
        // set the field null
        anagraficaCandidato.setComuneResidenza(null);

        // Create the AnagraficaCandidato, which fails.
        AnagraficaCandidatoDTO anagraficaCandidatoDTO = anagraficaCandidatoMapper.toDto(anagraficaCandidato);

        restAnagraficaCandidatoMockMvc.perform(post("/api/anagrafica-candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anagraficaCandidatoDTO)))
            .andExpect(status().isBadRequest());

        List<AnagraficaCandidato> anagraficaCandidatoList = anagraficaCandidatoRepository.findAll();
        assertThat(anagraficaCandidatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProvinciaResidenzaIsRequired() throws Exception {
        int databaseSizeBeforeTest = anagraficaCandidatoRepository.findAll().size();
        // set the field null
        anagraficaCandidato.setProvinciaResidenza(null);

        // Create the AnagraficaCandidato, which fails.
        AnagraficaCandidatoDTO anagraficaCandidatoDTO = anagraficaCandidatoMapper.toDto(anagraficaCandidato);

        restAnagraficaCandidatoMockMvc.perform(post("/api/anagrafica-candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anagraficaCandidatoDTO)))
            .andExpect(status().isBadRequest());

        List<AnagraficaCandidato> anagraficaCandidatoList = anagraficaCandidatoRepository.findAll();
        assertThat(anagraficaCandidatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAnagraficaCandidatoes() throws Exception {
        // Initialize the database
        anagraficaCandidatoRepository.saveAndFlush(anagraficaCandidato);

        // Get all the anagraficaCandidatoList
        restAnagraficaCandidatoMockMvc.perform(get("/api/anagrafica-candidatoes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anagraficaCandidato.getId().intValue())))
            .andExpect(jsonPath("$.[*].luogoNascita").value(hasItem(DEFAULT_LUOGO_NASCITA.toString())))
            .andExpect(jsonPath("$.[*].dataNascita").value(hasItem(DEFAULT_DATA_NASCITA.toString())))
            .andExpect(jsonPath("$.[*].professione").value(hasItem(DEFAULT_PROFESSIONE.toString())))
            .andExpect(jsonPath("$.[*].partitaIva").value(hasItem(DEFAULT_PARTITA_IVA.toString())))
            .andExpect(jsonPath("$.[*].datoreLavoro").value(hasItem(DEFAULT_DATORE_LAVORO.toString())))
            .andExpect(jsonPath("$.[*].indirizzoDatoreLavoro").value(hasItem(DEFAULT_INDIRIZZO_DATORE_LAVORO.toString())))
            .andExpect(jsonPath("$.[*].numeroTelefonoFisso").value(hasItem(DEFAULT_NUMERO_TELEFONO_FISSO.toString())))
            .andExpect(jsonPath("$.[*].numeroTelefonoCellulare").value(hasItem(DEFAULT_NUMERO_TELEFONO_CELLULARE.toString())))
            .andExpect(jsonPath("$.[*].numeroTelefonoAltro").value(hasItem(DEFAULT_NUMERO_TELEFONO_ALTRO.toString())))
            .andExpect(jsonPath("$.[*].indirizzoPec").value(hasItem(DEFAULT_INDIRIZZO_PEC.toString())))
            .andExpect(jsonPath("$.[*].cittadinoUnioneEuropea").value(hasItem(DEFAULT_CITTADINO_UNIONE_EUROPEA.booleanValue())))
            .andExpect(jsonPath("$.[*].indirizzoResidenza").value(hasItem(DEFAULT_INDIRIZZO_RESIDENZA.toString())))
            .andExpect(jsonPath("$.[*].capResidenza").value(hasItem(DEFAULT_CAP_RESIDENZA.toString())))
            .andExpect(jsonPath("$.[*].comuneResidenza").value(hasItem(DEFAULT_COMUNE_RESIDENZA.toString())))
            .andExpect(jsonPath("$.[*].provinciaResidenza").value(hasItem(DEFAULT_PROVINCIA_RESIDENZA.toString())))
            .andExpect(jsonPath("$.[*].titoloStudio").value(hasItem(DEFAULT_TITOLO_STUDIO.toString())))
            .andExpect(jsonPath("$.[*].titoloStudioTipologia").value(hasItem(DEFAULT_TITOLO_STUDIO_TIPOLOGIA.toString())))
            .andExpect(jsonPath("$.[*].titoloStudioLuogo").value(hasItem(DEFAULT_TITOLO_STUDIO_LUOGO.toString())))
            .andExpect(jsonPath("$.[*].titoloStudioAnno").value(hasItem(DEFAULT_TITOLO_STUDIO_ANNO.toString())))
            .andExpect(jsonPath("$.[*].titoloStudioVoto").value(hasItem(DEFAULT_TITOLO_STUDIO_VOTO.toString())))
            .andExpect(jsonPath("$.[*].specializzazioneUniversitaria").value(hasItem(DEFAULT_SPECIALIZZAZIONE_UNIVERSITARIA.booleanValue())))
            .andExpect(jsonPath("$.[*].iscrizioneAlboProfessionale").value(hasItem(DEFAULT_ISCRIZIONE_ALBO_PROFESSIONALE.booleanValue())))
            .andExpect(jsonPath("$.[*].operatoreAmbitoTecnicoProfessionale").value(hasItem(DEFAULT_OPERATORE_AMBITO_TECNICO_PROFESSIONALE.booleanValue())))
            .andExpect(jsonPath("$.[*].esperienzaBiennale").value(hasItem(DEFAULT_ESPERIENZA_BIENNALE.booleanValue())))
            .andExpect(jsonPath("$.[*].cv").value(hasItem(DEFAULT_CV.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }
    
    @Test
    @Transactional
    public void getAnagraficaCandidato() throws Exception {
        // Initialize the database
        anagraficaCandidatoRepository.saveAndFlush(anagraficaCandidato);

        // Get the anagraficaCandidato
        restAnagraficaCandidatoMockMvc.perform(get("/api/anagrafica-candidatoes/{id}", anagraficaCandidato.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(anagraficaCandidato.getId().intValue()))
            .andExpect(jsonPath("$.luogoNascita").value(DEFAULT_LUOGO_NASCITA.toString()))
            .andExpect(jsonPath("$.dataNascita").value(DEFAULT_DATA_NASCITA.toString()))
            .andExpect(jsonPath("$.professione").value(DEFAULT_PROFESSIONE.toString()))
            .andExpect(jsonPath("$.partitaIva").value(DEFAULT_PARTITA_IVA.toString()))
            .andExpect(jsonPath("$.datoreLavoro").value(DEFAULT_DATORE_LAVORO.toString()))
            .andExpect(jsonPath("$.indirizzoDatoreLavoro").value(DEFAULT_INDIRIZZO_DATORE_LAVORO.toString()))
            .andExpect(jsonPath("$.numeroTelefonoFisso").value(DEFAULT_NUMERO_TELEFONO_FISSO.toString()))
            .andExpect(jsonPath("$.numeroTelefonoCellulare").value(DEFAULT_NUMERO_TELEFONO_CELLULARE.toString()))
            .andExpect(jsonPath("$.numeroTelefonoAltro").value(DEFAULT_NUMERO_TELEFONO_ALTRO.toString()))
            .andExpect(jsonPath("$.indirizzoPec").value(DEFAULT_INDIRIZZO_PEC.toString()))
            .andExpect(jsonPath("$.cittadinoUnioneEuropea").value(DEFAULT_CITTADINO_UNIONE_EUROPEA.booleanValue()))
            .andExpect(jsonPath("$.indirizzoResidenza").value(DEFAULT_INDIRIZZO_RESIDENZA.toString()))
            .andExpect(jsonPath("$.capResidenza").value(DEFAULT_CAP_RESIDENZA.toString()))
            .andExpect(jsonPath("$.comuneResidenza").value(DEFAULT_COMUNE_RESIDENZA.toString()))
            .andExpect(jsonPath("$.provinciaResidenza").value(DEFAULT_PROVINCIA_RESIDENZA.toString()))
            .andExpect(jsonPath("$.titoloStudio").value(DEFAULT_TITOLO_STUDIO.toString()))
            .andExpect(jsonPath("$.titoloStudioTipologia").value(DEFAULT_TITOLO_STUDIO_TIPOLOGIA.toString()))
            .andExpect(jsonPath("$.titoloStudioLuogo").value(DEFAULT_TITOLO_STUDIO_LUOGO.toString()))
            .andExpect(jsonPath("$.titoloStudioAnno").value(DEFAULT_TITOLO_STUDIO_ANNO.toString()))
            .andExpect(jsonPath("$.titoloStudioVoto").value(DEFAULT_TITOLO_STUDIO_VOTO.toString()))
            .andExpect(jsonPath("$.specializzazioneUniversitaria").value(DEFAULT_SPECIALIZZAZIONE_UNIVERSITARIA.booleanValue()))
            .andExpect(jsonPath("$.iscrizioneAlboProfessionale").value(DEFAULT_ISCRIZIONE_ALBO_PROFESSIONALE.booleanValue()))
            .andExpect(jsonPath("$.operatoreAmbitoTecnicoProfessionale").value(DEFAULT_OPERATORE_AMBITO_TECNICO_PROFESSIONALE.booleanValue()))
            .andExpect(jsonPath("$.esperienzaBiennale").value(DEFAULT_ESPERIENZA_BIENNALE.booleanValue()))
            .andExpect(jsonPath("$.cv").value(DEFAULT_CV.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAnagraficaCandidato() throws Exception {
        // Get the anagraficaCandidato
        restAnagraficaCandidatoMockMvc.perform(get("/api/anagrafica-candidatoes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnagraficaCandidato() throws Exception {
        // Initialize the database
        anagraficaCandidatoRepository.saveAndFlush(anagraficaCandidato);

        int databaseSizeBeforeUpdate = anagraficaCandidatoRepository.findAll().size();

        // Update the anagraficaCandidato
        AnagraficaCandidato updatedAnagraficaCandidato = anagraficaCandidatoRepository.findById(anagraficaCandidato.getId()).get();
        // Disconnect from session so that the updates on updatedAnagraficaCandidato are not directly saved in db
        em.detach(updatedAnagraficaCandidato);
        updatedAnagraficaCandidato
            .luogoNascita(UPDATED_LUOGO_NASCITA)
            .dataNascita(UPDATED_DATA_NASCITA)
            .professione(UPDATED_PROFESSIONE)
            .partitaIva(UPDATED_PARTITA_IVA)
            .numeroTelefonoFisso(UPDATED_NUMERO_TELEFONO_FISSO)
            .numeroTelefonoCellulare(UPDATED_NUMERO_TELEFONO_CELLULARE)
            .indirizzoPec(UPDATED_INDIRIZZO_PEC)
            .indirizzoResidenza(UPDATED_INDIRIZZO_RESIDENZA)
            .capResidenza(UPDATED_CAP_RESIDENZA)
            .comuneResidenza(UPDATED_COMUNE_RESIDENZA)
            .provinciaResidenza(UPDATED_PROVINCIA_RESIDENZA)
            .note(UPDATED_NOTE);
        AnagraficaCandidatoDTO anagraficaCandidatoDTO = anagraficaCandidatoMapper.toDto(updatedAnagraficaCandidato);

        restAnagraficaCandidatoMockMvc.perform(put("/api/anagrafica-candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anagraficaCandidatoDTO)))
            .andExpect(status().isOk());

        // Validate the AnagraficaCandidato in the database
        List<AnagraficaCandidato> anagraficaCandidatoList = anagraficaCandidatoRepository.findAll();
        assertThat(anagraficaCandidatoList).hasSize(databaseSizeBeforeUpdate);
        AnagraficaCandidato testAnagraficaCandidato = anagraficaCandidatoList.get(anagraficaCandidatoList.size() - 1);
        assertThat(testAnagraficaCandidato.getLuogoNascita()).isEqualTo(UPDATED_LUOGO_NASCITA);
        assertThat(testAnagraficaCandidato.getDataNascita()).isEqualTo(UPDATED_DATA_NASCITA);
        assertThat(testAnagraficaCandidato.getProfessione()).isEqualTo(UPDATED_PROFESSIONE);
        assertThat(testAnagraficaCandidato.getPartitaIva()).isEqualTo(UPDATED_PARTITA_IVA);
        assertThat(testAnagraficaCandidato.getNumeroTelefonoFisso()).isEqualTo(UPDATED_NUMERO_TELEFONO_FISSO);
        assertThat(testAnagraficaCandidato.getNumeroTelefonoCellulare()).isEqualTo(UPDATED_NUMERO_TELEFONO_CELLULARE);
        assertThat(testAnagraficaCandidato.getIndirizzoPec()).isEqualTo(UPDATED_INDIRIZZO_PEC);
        assertThat(testAnagraficaCandidato.getIndirizzoResidenza()).isEqualTo(UPDATED_INDIRIZZO_RESIDENZA);
        assertThat(testAnagraficaCandidato.getCapResidenza()).isEqualTo(UPDATED_CAP_RESIDENZA);
        assertThat(testAnagraficaCandidato.getComuneResidenza()).isEqualTo(UPDATED_COMUNE_RESIDENZA);
        assertThat(testAnagraficaCandidato.getProvinciaResidenza()).isEqualTo(UPDATED_PROVINCIA_RESIDENZA);
        assertThat(testAnagraficaCandidato.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingAnagraficaCandidato() throws Exception {
        int databaseSizeBeforeUpdate = anagraficaCandidatoRepository.findAll().size();

        // Create the AnagraficaCandidato
        AnagraficaCandidatoDTO anagraficaCandidatoDTO = anagraficaCandidatoMapper.toDto(anagraficaCandidato);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnagraficaCandidatoMockMvc.perform(put("/api/anagrafica-candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anagraficaCandidatoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnagraficaCandidato in the database
        List<AnagraficaCandidato> anagraficaCandidatoList = anagraficaCandidatoRepository.findAll();
        assertThat(anagraficaCandidatoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnagraficaCandidato() throws Exception {
        // Initialize the database
        anagraficaCandidatoRepository.saveAndFlush(anagraficaCandidato);

        int databaseSizeBeforeDelete = anagraficaCandidatoRepository.findAll().size();

        // Delete the anagraficaCandidato
        restAnagraficaCandidatoMockMvc.perform(delete("/api/anagrafica-candidatoes/{id}", anagraficaCandidato.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnagraficaCandidato> anagraficaCandidatoList = anagraficaCandidatoRepository.findAll();
        assertThat(anagraficaCandidatoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnagraficaCandidato.class);
        AnagraficaCandidato anagraficaCandidato1 = new AnagraficaCandidato();
        anagraficaCandidato1.setId(1L);
        AnagraficaCandidato anagraficaCandidato2 = new AnagraficaCandidato();
        anagraficaCandidato2.setId(anagraficaCandidato1.getId());
        assertThat(anagraficaCandidato1).isEqualTo(anagraficaCandidato2);
        anagraficaCandidato2.setId(2L);
        assertThat(anagraficaCandidato1).isNotEqualTo(anagraficaCandidato2);
        anagraficaCandidato1.setId(null);
        assertThat(anagraficaCandidato1).isNotEqualTo(anagraficaCandidato2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnagraficaCandidatoDTO.class);
        AnagraficaCandidatoDTO anagraficaCandidatoDTO1 = new AnagraficaCandidatoDTO();
        anagraficaCandidatoDTO1.setId(1L);
        AnagraficaCandidatoDTO anagraficaCandidatoDTO2 = new AnagraficaCandidatoDTO();
        assertThat(anagraficaCandidatoDTO1).isNotEqualTo(anagraficaCandidatoDTO2);
        anagraficaCandidatoDTO2.setId(anagraficaCandidatoDTO1.getId());
        assertThat(anagraficaCandidatoDTO1).isEqualTo(anagraficaCandidatoDTO2);
        anagraficaCandidatoDTO2.setId(2L);
        assertThat(anagraficaCandidatoDTO1).isNotEqualTo(anagraficaCandidatoDTO2);
        anagraficaCandidatoDTO1.setId(null);
        assertThat(anagraficaCandidatoDTO1).isNotEqualTo(anagraficaCandidatoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(anagraficaCandidatoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(anagraficaCandidatoMapper.fromId(null)).isNull();
    }
}
