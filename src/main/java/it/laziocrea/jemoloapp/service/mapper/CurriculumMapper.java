package it.laziocrea.jemoloapp.service.mapper;

import it.laziocrea.jemoloapp.domain.*;
import it.laziocrea.jemoloapp.service.dto.CurriculumDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Curriculum} and its DTO {@link CurriculumDTO}.
 */
@Mapper(componentModel = "spring", uses = {AllegatoMapper.class, AnagraficaCandidatoMapper.class})
public interface CurriculumMapper extends EntityMapper<CurriculumDTO, Curriculum> {

    // @Mapping(source = "allegato.id", target = "allegatoId")
    @Mapping(source = "anagrafica.id", target = "anagraficaId")
    @Mapping(source = "anagrafica", target = "anagrafica")
    // @Mapping(source = "allegato", target = "allegato")
	CurriculumDTO toDto(Curriculum curriculum);

    // @Mapping(source = "allegatoId", target = "allegato")
    @Mapping(source = "anagraficaId", target = "anagrafica")
    Curriculum toEntity(CurriculumDTO curriculumDTO);

    default Curriculum fromId(Long id) {
        if (id == null) {
            return null;
        }
        Curriculum curriculum = new Curriculum();
        curriculum.setId(id);
        return curriculum;
    }
}
