package it.laziocrea.jemoloapp.service.mapper;

import it.laziocrea.jemoloapp.domain.*;
import it.laziocrea.jemoloapp.service.dto.CandidatoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Candidato} and its DTO {@link CandidatoDTO}.
 */
@Mapper(componentModel = "spring", uses = {AnagraficaCandidatoMapper.class,StatoRegistrazioneMapper.class,AuthorityMapper.class})
public interface CandidatoMapper extends EntityMapper<CandidatoDTO, Candidato> {
	
    @Mapping(source = "stato.id", target = "statoId")
	@Mapping(source = "anagraficaCandidato", target = "anagraficaCandidato")
	@Mapping(source = "anagraficaCandidato.id", target = "anagraficaCandidatoId")
	CandidatoDTO toDto(Candidato candidato);
	
    @Mapping(target = "anagraficaCandidato", ignore = true)
    @Mapping(source = "statoId", target = "stato")
    Candidato toEntity(CandidatoDTO candidatoDTO);
    
    default Candidato fromId(Long id) {
        if (id == null) {
            return null;
        }
        Candidato candidato = new Candidato();
        candidato.setId(id);
        return candidato;
    }

}
