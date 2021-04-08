package it.laziocrea.jemoloapp.service.mapper;

import it.laziocrea.jemoloapp.domain.*;
import it.laziocrea.jemoloapp.service.dto.CandidatoDTO;
import it.laziocrea.jemoloapp.service.dto.IscrittoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Candidato} and its DTO {@link CandidatoDTO}.
 */
@Mapper(componentModel = "spring", uses = {StatoRegistrazioneMapper.class})
public interface IscrittoMapper extends EntityMapper<IscrittoDTO, Candidato> {

    @Mapping(source = "anagraficaCandidato.id", target = "anagraficaCandidatoId")
    @Mapping(source = "stato.id", target = "statoRegistrazioneId")
    IscrittoDTO toDto(Candidato candidato);

    @Mapping(target = "anagraficaCandidato", ignore = true)
    @Mapping(source = "statoRegistrazioneId", target = "stato")
    Candidato toEntity(IscrittoDTO iscrittoDTO);

    default Candidato fromId(Long id) {
        if (id == null) {
            return null;
        }
        Candidato candidato = new Candidato();
        candidato.setId(id);
        return candidato;
    }
}
