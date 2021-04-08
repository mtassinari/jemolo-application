package it.laziocrea.jemoloapp.service.mapper;

import it.laziocrea.jemoloapp.domain.*;
import it.laziocrea.jemoloapp.service.dto.CompetenzaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Competenza} and its DTO {@link CompetenzaDTO}.
 */
@Mapper(componentModel = "spring", uses = {AnagraficaCandidatoMapper.class, AmbitoCompetenzaMapper.class})
public interface CompetenzaMapper extends EntityMapper<CompetenzaDTO, Competenza> {
    @Mapping(source = "anagrafica.id", target = "anagraficaId")
    @Mapping(source = "anagrafica", target = "anagrafica")
    @Mapping(source = "ambitoComp.id", target = "ambitoCompId")
    @Mapping(source = "ambitoComp", target = "ambitoComp")
    CompetenzaDTO toDto(Competenza competenza);

    @Mapping(source = "anagraficaId", target = "anagrafica")
    @Mapping(source = "ambitoCompId", target = "ambitoComp")
    Competenza toEntity(CompetenzaDTO competenzaDTO);

    default Competenza fromId(Long id) {
        if (id == null) {
            return null;
        }
        Competenza competenza = new Competenza();
        competenza.setId(id);
        return competenza;
    }
}
