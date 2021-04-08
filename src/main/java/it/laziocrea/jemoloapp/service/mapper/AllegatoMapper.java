package it.laziocrea.jemoloapp.service.mapper;


import org.mapstruct.*;

import it.laziocrea.jemoloapp.domain.Allegato;
import it.laziocrea.jemoloapp.service.dto.AllegatoDTO;

/**
 * Mapper for the entity {@link Allegato} and its DTO {@link AllegatoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AllegatoMapper extends EntityMapper<AllegatoDTO, Allegato> {


    @Mapping(target = "curriculum", ignore = true)
    Allegato toEntity(AllegatoDTO allegatoDTO);

    default Allegato fromId(Long id) {
        if (id == null) {
            return null;
        }
        Allegato allegato = new Allegato();
        allegato.setId(id);
        return allegato;
    }
}
