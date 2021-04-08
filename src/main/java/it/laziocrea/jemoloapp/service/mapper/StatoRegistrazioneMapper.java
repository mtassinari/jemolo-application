package it.laziocrea.jemoloapp.service.mapper;

import it.laziocrea.jemoloapp.domain.*;
import it.laziocrea.jemoloapp.service.dto.StatoRegistrazioneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StatoRegistrazione} and its DTO {@link StatoRegistrazioneDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StatoRegistrazioneMapper extends EntityMapper<StatoRegistrazioneDTO, StatoRegistrazione> {


    @Mapping(target = "candidatoes", ignore = true)
    @Mapping(target = "removeCandidato", ignore = true)
	StatoRegistrazione toEntity(StatoRegistrazioneDTO statoRegistrazioneDTO);

    default StatoRegistrazione fromId(Long id) {
        if (id == null) {
            return null;
        }
        StatoRegistrazione statoRegistrazione = new StatoRegistrazione();
        statoRegistrazione.setId(id);
        return statoRegistrazione;
    }
}
