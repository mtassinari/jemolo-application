package it.laziocrea.jemoloapp.service.mapper;

import it.laziocrea.jemoloapp.domain.*;
import it.laziocrea.jemoloapp.service.dto.TitoloStudioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TitoloStudio} and its DTO {@link TitoloStudioDTO}.
 */
@Mapper(componentModel = "spring", uses = {AnagraficaCandidatoMapper.class})
public interface TitoloStudioMapper extends EntityMapper<TitoloStudioDTO, TitoloStudio> {

    @Mapping(source = "anagrafica.id", target = "anagraficaId")
    TitoloStudioDTO toDto(TitoloStudio titoloStudio);

    @Mapping(source = "anagraficaId", target = "anagrafica")
    TitoloStudio toEntity(TitoloStudioDTO titoloStudioDTO);

    default TitoloStudio fromId(Long id) {
        if (id == null) {
            return null;
        }
        TitoloStudio titoloStudio = new TitoloStudio();
        titoloStudio.setId(id);
        return titoloStudio;
    }
}
