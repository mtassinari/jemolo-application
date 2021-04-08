package it.laziocrea.jemoloapp.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;
import it.laziocrea.jemoloapp.domain.*; // for static metamodels
import it.laziocrea.jemoloapp.repository.AmbitoCompetenzaRepository;
import it.laziocrea.jemoloapp.repository.AnagraficaCandidatoRepository;
import it.laziocrea.jemoloapp.repository.LinguaRepository;
import it.laziocrea.jemoloapp.service.dto.AnagraficaCandidatoCriteria;
import it.laziocrea.jemoloapp.service.dto.AnagraficaCandidatoDTO;
import it.laziocrea.jemoloapp.service.mapper.AnagraficaCandidatoMapper;

/**
 * Service for executing complex queries for {@link AnagraficaCandidato} entities in the database.
 * The main input is a {@link AnagraficaCandidatoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AnagraficaCandidatoDTO} or a {@link Page} of {@link AnagraficaCandidatoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AnagraficaCandidatoQueryService extends QueryService<AnagraficaCandidato> {

    private final Logger log = LoggerFactory.getLogger(AnagraficaCandidatoQueryService.class);

    private final AnagraficaCandidatoRepository anagraficaCandidatoRepository;

    private final AnagraficaCandidatoMapper anagraficaCandidatoMapper;
    
    private final AmbitoCompetenzaRepository ambitoCompetenzaRepository;
    
    private final LinguaRepository linguaRepository;

    public AnagraficaCandidatoQueryService(LinguaRepository linguaRepository, AmbitoCompetenzaRepository ambitoCompetenzaRepository, AnagraficaCandidatoRepository anagraficaCandidatoRepository, AnagraficaCandidatoMapper anagraficaCandidatoMapper) {
        this.anagraficaCandidatoRepository = anagraficaCandidatoRepository;
        this.anagraficaCandidatoMapper = anagraficaCandidatoMapper;
        this.ambitoCompetenzaRepository = ambitoCompetenzaRepository;
        this.linguaRepository = linguaRepository;
    }
    
    /**
     * Return a {@link List} of {@link AnagraficaCandidatoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AnagraficaCandidato> findByCriteria2(AnagraficaCandidatoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AnagraficaCandidato> specification = createSpecification(criteria);
        return anagraficaCandidatoRepository.findAll(specification);
    }

    /**
     * Return a {@link List} of {@link AnagraficaCandidatoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AnagraficaCandidatoDTO> findByCriteria(AnagraficaCandidatoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AnagraficaCandidato> specification = createSpecification(criteria);
        return anagraficaCandidatoMapper.toDto(anagraficaCandidatoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AnagraficaCandidatoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AnagraficaCandidatoDTO> findByCriteria(AnagraficaCandidatoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AnagraficaCandidato> specification = createSpecification(criteria);
        return anagraficaCandidatoRepository.findAll(specification, page)
            .map(anagraficaCandidatoMapper::toDto);
    }
    
    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AnagraficaCandidatoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AnagraficaCandidato> specification = createSpecification(criteria);
        return anagraficaCandidatoRepository.count(specification);
    }

    /**
     * Function to convert {@link AnagraficaCandidatoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AnagraficaCandidato> createSpecification(AnagraficaCandidatoCriteria criteria) {
        Specification<AnagraficaCandidato> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AnagraficaCandidato_.id));
            }
            if (criteria.getCognome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCognome(), AnagraficaCandidato_.cognome));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), AnagraficaCandidato_.nome));
            }
            if (criteria.getLuogoNascita() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLuogoNascita(), AnagraficaCandidato_.luogoNascita));
            }
            if (criteria.getDataNascita() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataNascita(), AnagraficaCandidato_.dataNascita));
            }
            if (criteria.getProfessione() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProfessione(), AnagraficaCandidato_.professione));
            }
            if (criteria.getPartitaIva() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPartitaIva(), AnagraficaCandidato_.partitaIva));
            }
            if (criteria.getNumeroTelefonoFisso() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumeroTelefonoFisso(), AnagraficaCandidato_.numeroTelefonoFisso));
            }
            if (criteria.getNumeroTelefonoCellulare() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumeroTelefonoCellulare(), AnagraficaCandidato_.numeroTelefonoCellulare));
            }
            if (criteria.getIndirizzoPec() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIndirizzoPec(), AnagraficaCandidato_.indirizzoPec));
            }
            if (criteria.getIndirizzoResidenza() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIndirizzoResidenza(), AnagraficaCandidato_.indirizzoResidenza));
            }
            if (criteria.getCapResidenza() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCapResidenza(), AnagraficaCandidato_.capResidenza));
            }
            if (criteria.getComuneResidenza() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComuneResidenza(), AnagraficaCandidato_.comuneResidenza));
            }
            if (criteria.getProvinciaResidenza() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProvinciaResidenza(), AnagraficaCandidato_.provinciaResidenza));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), AnagraficaCandidato_.note));
            }
            if (criteria.getCandidatoId() != null) {
                specification = specification.and(buildSpecification(criteria.getCandidatoId(),
                    root -> root.join(AnagraficaCandidato_.candidato, JoinType.LEFT).get(Candidato_.id)));
            }
            if (criteria.getCompetenzeLngId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompetenzeLngId(),
                    root -> root.join(AnagraficaCandidato_.competenzeLngs, JoinType.LEFT).get(CompetenzeLng_.id)));
            }
            if (criteria.getActivated() != null) {
            	log.debug("cerco solo iscritti attivi: {}", criteria.getActivated());
                specification = specification.and(buildSpecification(criteria.getActivated(),
                    root -> root.join(AnagraficaCandidato_.candidato, JoinType.LEFT).get(Candidato_.activated)));
            }
            if (criteria.getLinguaId() != null) {
                if (criteria.getLinguaLivello() != null) {
	               log.debug("linguaID: {} con livello >= a: {}",
	            		   criteria.getLinguaId().getEquals(),
	            		   criteria.getLinguaLivello().getGreaterThanOrEqual());
	               specification = specification.and(
            		   (root, query, builder) -> {
            			   final Join<AnagraficaCandidato,CompetenzeLng> competenzeLngs = root.join(AnagraficaCandidato_.competenzeLngs, JoinType.LEFT);
            			   final Join<CompetenzeLng,Lingua> lingua = competenzeLngs.join(CompetenzeLng_.lingua, JoinType.LEFT);
            			   return builder.and(
   	           		        	builder.greaterThanOrEqualTo(competenzeLngs.get(CompetenzeLng_.livello), criteria.getLinguaLivello().getGreaterThanOrEqual()),
   	           		        	builder.equal(lingua.get(Lingua_.id), criteria.getLinguaId().getEquals())
    					   );
            		   }
        		   );
                } else {
	                specification = specification.and(buildSpecification(criteria.getLinguaId(),
                        root -> root.join(AnagraficaCandidato_.competenzeLngs, JoinType.LEFT).
                        join(CompetenzeLng_.lingua).get(Lingua_.id)));
	                log.debug("linguaID: {}",criteria.getLinguaId().getEquals());
                }
            }
            if (criteria.getTitoloStudioId() != null) {
                specification = specification.and(buildSpecification(criteria.getTitoloStudioId(),
                    root -> root.join(AnagraficaCandidato_.titoloStudios, JoinType.LEFT).get(TitoloStudio_.id)));
            }
            if (criteria.getTipotitolodistudioId() != null) {
                if (criteria.getTitolostudioDal() != null && criteria.getTitolostudioAl() == null) {
                    specification = specification.and(buildSpecification(criteria.getTipotitolodistudioId(),
                            root -> root.join(AnagraficaCandidato_.titoloStudios, JoinType.LEFT).get(TitoloStudio_.tipologia)));
                    specification = specification.and(
                		(root, query, builder) -> {
                			final Join<AnagraficaCandidato, TitoloStudio> titoloStudios = root.join(AnagraficaCandidato_.titoloStudios, JoinType.LEFT);
                			return builder.and(
   	           		        	builder.equal(titoloStudios.get(TitoloStudio_.tipologia), criteria.getTipotitolodistudioId().getEquals()),
   	           		        	builder.greaterThanOrEqualTo(titoloStudios.get(TitoloStudio_.anno), criteria.getTitolostudioDal().getGreaterThanOrEqual())            					
                			);
                		}
            		);
                }
                if (criteria.getTitolostudioAl() != null && criteria.getTitolostudioDal() == null) {
                    specification = specification.and(buildSpecification(criteria.getTipotitolodistudioId(),
                            root -> root.join(AnagraficaCandidato_.titoloStudios, JoinType.LEFT).get(TitoloStudio_.tipologia)));
                    specification = specification.and(buildSpecification(criteria.getTitolostudioAl(),
                            root -> root.join(AnagraficaCandidato_.titoloStudios, JoinType.LEFT).get(TitoloStudio_.anno)));
                }
                if (criteria.getTitolostudioAl() == null && criteria.getTitolostudioDal() == null) {
                    specification = specification.and(buildSpecification(criteria.getTipotitolodistudioId(),
                            root -> root.join(AnagraficaCandidato_.titoloStudios, JoinType.LEFT).get(TitoloStudio_.tipologia)));
                	
                }
            }
            if (criteria.getCurriculumId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurriculumId(),
                    root -> root.join(AnagraficaCandidato_.curricula, JoinType.LEFT).get(Curriculum_.id)));
            }
            if (criteria.getCompetenzaId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompetenzaId(),
                    root -> root.join(AnagraficaCandidato_.competenzas, JoinType.LEFT).get(Competenza_.id)));
            }
            if (criteria.getAreaCompetenzaId() != null) {
            	if (criteria.getAreaCompetenzaDa() == null && criteria.getAreaCompetenzaA() == null) {
            		log.debug("AreaCompetenzaID: {}",criteria.getAreaCompetenzaId().getEquals());
	        		specification = specification.and(buildSpecification(criteria.getAreaCompetenzaId(),
	            		root -> root
						.join(AnagraficaCandidato_.competenzas, JoinType.LEFT)
						.join(Competenza_.ambitoComp, JoinType.LEFT).get(AmbitoCompetenza_.id)
	        		));
            	}
            	if (criteria.getAreaCompetenzaDa() != null && criteria.getAreaCompetenzaA() != null) {
	               	 log.debug("AreaCompetenzaID: {} con anni da: {} a: {}",
               			 criteria.getAreaCompetenzaId().getEquals(),
               			 criteria.getAreaCompetenzaDa().getGreaterThanOrEqual(),
               			 criteria.getAreaCompetenzaA().getLessThanOrEqual());
	               	 specification = specification.and(
	           		    (root, query, builder) -> {
	           		        final Join<AnagraficaCandidato, Competenza> competenzas = root.join(AnagraficaCandidato_.competenzas, JoinType.LEFT);
	           		        final Join<Competenza, AmbitoCompetenza> ambito = competenzas.join(Competenza_.ambitoComp, JoinType.LEFT);
	           		        return builder.and(
	           		        	builder.equal(ambito.get(AmbitoCompetenza_.id), criteria.getAreaCompetenzaId().getEquals()),
	           		        	builder.greaterThanOrEqualTo(competenzas.get(Competenza_.anniEsperianza), criteria.getAreaCompetenzaDa().getGreaterThanOrEqual()),
	           		        	builder.lessThanOrEqualTo(competenzas.get(Competenza_.anniEsperianza), criteria.getAreaCompetenzaA().getLessThanOrEqual())
	           		        );
	           		    }
	           		);           		
            	}
                if (criteria.getAreaCompetenzaDa() != null && criteria.getAreaCompetenzaA() == null) {
                	 log.debug("AreaCompetenzaID: {} con anni da: {}",
            			 criteria.getAreaCompetenzaId().getEquals(),
            			 criteria.getAreaCompetenzaDa().getGreaterThanOrEqual());
                	 specification = specification.and(
            		    (root, query, builder) -> {
            		        final Join<AnagraficaCandidato, Competenza> competenzas = root.join(AnagraficaCandidato_.competenzas, JoinType.LEFT);
            		        final Join<Competenza, AmbitoCompetenza> ambito = competenzas.join(Competenza_.ambitoComp, JoinType.LEFT);
            		        return builder.and(
            		        	builder.equal(ambito.get(AmbitoCompetenza_.id), criteria.getAreaCompetenzaId().getEquals()),
            		        	builder.greaterThanOrEqualTo(competenzas.get(Competenza_.anniEsperianza), criteria.getAreaCompetenzaDa().getGreaterThanOrEqual())
            		        );
            		    }
            		);
                }
                if (criteria.getAreaCompetenzaA() != null && criteria.getAreaCompetenzaDa() == null) {
	               	 log.debug("AreaCompetenzaID: {} con anni a: {}", 
               			 criteria.getAreaCompetenzaId().getEquals(), 
               			 criteria.getAreaCompetenzaA().getLessThanOrEqual());
	               	 specification = specification.and(
	           		    (root, query, builder) -> {
	           		        final Join<AnagraficaCandidato, Competenza> competenzas = root.join(AnagraficaCandidato_.competenzas, JoinType.LEFT);
	           		        final Join<Competenza, AmbitoCompetenza> ambito = competenzas.join(Competenza_.ambitoComp, JoinType.LEFT);
	           		        return builder.and(
	           		        	builder.equal(ambito.get(AmbitoCompetenza_.id), criteria.getAreaCompetenzaId().getEquals()),
	           		        	builder.lessThanOrEqualTo(competenzas.get(Competenza_.anniEsperianza), criteria.getAreaCompetenzaA().getLessThanOrEqual())
	           		        );
	           		    }
	           		);
                }
            }
            if (criteria.getDichiarazioniId() != null) {
                specification = specification.and(buildSpecification(criteria.getDichiarazioniId(),
                    root -> root.join(AnagraficaCandidato_.dichiarazionis, JoinType.LEFT).get(DichiarazioniObligatorie_.id)));
            }
        }
        return specification;
    }
    
    /**
     * Function to convert {@link AnagraficaCandidatoCriteria} to a parametri ricerca
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    public Map<String, String> getSearchCriteria(AnagraficaCandidatoCriteria criteria) {
    	Map<String, String> searchCriteria = new LinkedHashMap<String, String>();
        if (criteria != null) {
            if (criteria.getNome() != null) {
            	searchCriteria.put("Nome", criteria.getNome().getContains());
            }
            if (criteria.getCognome() != null) {
            	searchCriteria.put("Cognome", criteria.getCognome().getContains());
            }
            if (criteria.getTipotitolodistudioId() != null) {
                switch (criteria.getTipotitolodistudioId().getEquals()) {
				case "diploma-laurea":
	            	searchCriteria.put("Titolo di studio", "Diploma di laurea vecchio ordinamento di durata non inferiore a 4 anni");					
					break;
				case "laurea-magistrale":
	            	searchCriteria.put("Titolo di studio", "Laurea magistrale nuovo ordinamento di durata normale di 5 o 6 anni (a ciclo unico)");					
					break;
				case "laurea-specialistica":
	            	searchCriteria.put("Titolo di studio", "Laurea specialistica di durata normale di 2 anni (dopo aver conseguito la laurea di durata di 3 anni)");					
					break;
				default:
					break;
				}
            }
            if (criteria.getTitolostudioDal() != null) {
            	searchCriteria.put("Titolo di studio dall'anno", criteria.getTitolostudioDal().getGreaterThanOrEqual().toString());
            }
            if (criteria.getTitolostudioAl() != null) {
            	searchCriteria.put("Titolo di studio all'anno", criteria.getTitolostudioAl().getLessThanOrEqual().toString());
            }
            if (criteria.getAreaCompetenzaId() != null) {
            	Optional<AmbitoCompetenza> opt = ambitoCompetenzaRepository.findById(criteria.getAreaCompetenzaId().getEquals());
            	if (opt.isPresent())
            	  searchCriteria.put("Area di competenza", opt.get().getDescrizione());
            }
            if (criteria.getAreaCompetenzaDa() != null) {
            	searchCriteria.put("anni esperienza da",criteria.getAreaCompetenzaDa().getGreaterThanOrEqual().toString());
            }
            if (criteria.getAreaCompetenzaA() != null) {
            	searchCriteria.put("anni esperienza a",criteria.getAreaCompetenzaA().getLessThanOrEqual().toString());            	
            }
            if (criteria.getLinguaId() != null) {
            	Optional<Lingua> opt = linguaRepository.findById(criteria.getLinguaId().getEquals());
            	if (opt.isPresent())
            		searchCriteria.put("Lingua", opt.get().getLingua());
            }
            if (criteria.getLinguaLivello() != null) {
            	log.debug("criteria.getLinguaLivello(): "+criteria.getLinguaLivello());
            	switch (criteria.getLinguaLivello().getGreaterThanOrEqual()) {
				case 1: 
	            	searchCriteria.put("Livello", "A1");					
					break;
				case 2: 
	            	searchCriteria.put("Livello", "A2");					
					break;
				case 3: 
	            	searchCriteria.put("Livello", "B1");					
					break;
				case 4: 
	            	searchCriteria.put("Livello", "B2");					
					break;
				case 5: 
	            	searchCriteria.put("Livello", "C1");					
					break;
				case 6: 
	            	searchCriteria.put("Livello", "C2");					
					break;
				default:
					break;
				}
            }
        }
    	return searchCriteria;
    }
}
