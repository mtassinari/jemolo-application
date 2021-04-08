package it.laziocrea.jemoloapp.service.mapper;


import it.laziocrea.jemoloapp.domain.*;
import it.laziocrea.jemoloapp.service.dto.DichiarazioniDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dichiarazioni} and its DTO {@link DichiarazioniDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DichiarazioniMapper extends EntityMapper<DichiarazioniDTO, Dichiarazioni> {


    @Mapping(target = "dichiarazioniObligatories", ignore = true)
    @Mapping(target = "removeDichiarazioniObligatorie", ignore = true)
    Dichiarazioni toEntity(DichiarazioniDTO dichiarazioniDTO);

    default Dichiarazioni fromId(Long id) {
        if (id == null) {
            return null;
        }
        Dichiarazioni dichiarazioni = new Dichiarazioni();
        dichiarazioni.setId(id);
        return dichiarazioni;
    }
}
