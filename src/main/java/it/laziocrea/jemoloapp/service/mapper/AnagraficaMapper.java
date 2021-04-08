package it.laziocrea.jemoloapp.service.mapper;

import it.laziocrea.jemoloapp.domain.*;
import it.laziocrea.jemoloapp.service.dto.AnagraficaCandidatoDTO;
import it.laziocrea.jemoloapp.service.dto.AnagraficaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnagraficaCandidato} and its DTO {@link AnagraficaCandidatoDTO}.
 */
@Mapper(componentModel = "spring")
public interface AnagraficaMapper extends EntityMapper<AnagraficaDTO, Anagrafica> {

    AnagraficaDTO toDto(Anagrafica anagrafica);
    
    Anagrafica toEntity(AnagraficaDTO anagraficaDTO);

    default Anagrafica fromId(Long id) {
        if (id == null) {
            return null;
        }
        Anagrafica anagrafica = new Anagrafica();
        anagrafica.setId(id);
        return anagrafica;
    }
}
