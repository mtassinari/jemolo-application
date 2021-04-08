package it.laziocrea.jemoloapp.service.mapper;

import it.laziocrea.jemoloapp.domain.*;
import it.laziocrea.jemoloapp.service.dto.CompetenzeLngDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompetenzeLng} and its DTO {@link CompetenzeLngDTO}.
 */
@Mapper(componentModel = "spring", uses = {LinguaMapper.class, AnagraficaCandidatoMapper.class})
public interface CompetenzeLngMapper extends EntityMapper<CompetenzeLngDTO, CompetenzeLng> {

    @Mapping(source = "lingua.id", target = "linguaId")
    @Mapping(source = "anagrafica.id", target = "anagraficaId")
    CompetenzeLngDTO toDto(CompetenzeLng competenzeLng);

    @Mapping(source = "linguaId", target = "lingua")
    @Mapping(source = "anagraficaId", target = "anagrafica")
    CompetenzeLng toEntity(CompetenzeLngDTO competenzeLngDTO);

    default CompetenzeLng fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompetenzeLng competenzeLng = new CompetenzeLng();
        competenzeLng.setId(id);
        return competenzeLng;
    }
}
