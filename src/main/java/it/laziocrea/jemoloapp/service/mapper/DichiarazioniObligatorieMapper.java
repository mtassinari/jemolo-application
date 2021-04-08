package it.laziocrea.jemoloapp.service.mapper;


import it.laziocrea.jemoloapp.domain.*;
import it.laziocrea.jemoloapp.service.dto.DichiarazioniObligatorieDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DichiarazioniObligatorie} and its DTO {@link DichiarazioniObligatorieDTO}.
 */
@Mapper(componentModel = "spring", uses = {AnagraficaCandidatoMapper.class, DichiarazioniMapper.class})
public interface DichiarazioniObligatorieMapper extends EntityMapper<DichiarazioniObligatorieDTO, DichiarazioniObligatorie> {

    @Mapping(source = "anagrafica.id", target = "anagraficaId")
    @Mapping(source = "dichiarazioni.id", target = "dichiarazioniId")
    DichiarazioniObligatorieDTO toDto(DichiarazioniObligatorie dichiarazioniObligatorie);

    @Mapping(source = "anagraficaId", target = "anagrafica")
    @Mapping(source = "dichiarazioniId", target = "dichiarazioni")
    DichiarazioniObligatorie toEntity(DichiarazioniObligatorieDTO dichiarazioniObligatorieDTO);

    default DichiarazioniObligatorie fromId(Long id) {
        if (id == null) {
            return null;
        }
        DichiarazioniObligatorie dichiarazioniObligatorie = new DichiarazioniObligatorie();
        dichiarazioniObligatorie.setId(id);
        return dichiarazioniObligatorie;
    }
}
