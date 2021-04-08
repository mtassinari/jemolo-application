package it.laziocrea.jemoloapp.service.mapper;

import it.laziocrea.jemoloapp.domain.*;
import it.laziocrea.jemoloapp.service.dto.LinguaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Lingua} and its DTO {@link LinguaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LinguaMapper extends EntityMapper<LinguaDTO, Lingua> {


    @Mapping(target = "competenzeLinguistiches", ignore = true)
    @Mapping(target = "removeCompetenzeLinguistiche", ignore = true)
    Lingua toEntity(LinguaDTO linguaDTO);

    default Lingua fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lingua lingua = new Lingua();
        lingua.setId(id);
        return lingua;
    }
}
