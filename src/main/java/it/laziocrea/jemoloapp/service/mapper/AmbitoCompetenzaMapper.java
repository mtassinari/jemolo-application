package it.laziocrea.jemoloapp.service.mapper;
import it.laziocrea.jemoloapp.domain.*;
import it.laziocrea.jemoloapp.service.dto.AmbitoCompetenzaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AmbitoCompetenza} and its DTO {@link AmbitoCompetenzaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AmbitoCompetenzaMapper extends EntityMapper<AmbitoCompetenzaDTO, AmbitoCompetenza> {

    @Mapping(source = "ambito.id", target = "ambitoId")
    @Mapping(source = "ambito", target = "ambito")
    @Mapping(source = "sottoambitos", target = "sottoambitos")
    AmbitoCompetenzaDTO toDto(AmbitoCompetenza ambitoCompetenza);

    @Mapping(target = "competenzas", ignore = true)
    @Mapping(target = "removeCompetenza", ignore = true)
    @Mapping(target = "sottoambitos", ignore = true)
    @Mapping(target = "removeSottoambito", ignore = true)
    @Mapping(source = "ambitoId", target = "ambito")
    AmbitoCompetenza toEntity(AmbitoCompetenzaDTO ambitoCompetenzaDTO);

    default AmbitoCompetenza fromId(Long id) {
        if (id == null) {
            return null;
        }
        AmbitoCompetenza ambitoCompetenza = new AmbitoCompetenza();
        ambitoCompetenza.setId(id);
        return ambitoCompetenza;
    }
}
