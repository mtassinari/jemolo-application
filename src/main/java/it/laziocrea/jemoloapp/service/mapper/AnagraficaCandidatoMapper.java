package it.laziocrea.jemoloapp.service.mapper;

import it.laziocrea.jemoloapp.domain.*;
import it.laziocrea.jemoloapp.service.dto.AnagraficaCandidatoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnagraficaCandidato} and its DTO {@link AnagraficaCandidatoDTO}.
 */
@Mapper(componentModel = "spring", uses = {CandidatoMapper.class})
public interface AnagraficaCandidatoMapper extends EntityMapper<AnagraficaCandidatoDTO, AnagraficaCandidato> {

    @Mapping(source = "candidato.id", target = "candidatoId")
    @Mapping(source = "candidato", target = "candidato")
    AnagraficaCandidatoDTO toDto(AnagraficaCandidato anagraficaCandidato);
    
    @Mapping(source = "candidatoId", target = "candidato")
    // @Mapping(target = "competenzeLngs", ignore = true)
    @Mapping(target = "removeCompetenzeLng", ignore = true)
    // @Mapping(target = "titoloStudios", ignore = true)
    @Mapping(target = "removeTitoloStudio", ignore = true)
    @Mapping(target = "curricula", ignore = true)
    @Mapping(target = "removeCurriculum", ignore = true)
    // @Mapping(target = "competenzas", ignore = true)
    @Mapping(target = "removeCompetenza", ignore = true)
    AnagraficaCandidato toEntity(AnagraficaCandidatoDTO anagraficaCandidatoDTO);

    default AnagraficaCandidato fromId(Long id) {
        if (id == null) {
            return null;
        }
        AnagraficaCandidato anagraficaCandidato = new AnagraficaCandidato();
        anagraficaCandidato.setId(id);
        return anagraficaCandidato;
    }
}
