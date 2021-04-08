package it.laziocrea.jemoloapp.repository;

import it.laziocrea.jemoloapp.domain.Candidato;
import it.laziocrea.jemoloapp.domain.PersistentCandidatoToken;
import it.laziocrea.jemoloapp.domain.PersistentToken;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the {@link PersistentToken} entity.
 */
public interface PersistentCandidatoTokenRepository extends JpaRepository<PersistentCandidatoToken, String> {

    List<PersistentCandidatoToken> findByCandidato(Candidato candidato);

    List<PersistentCandidatoToken> findByTokenDateBefore(LocalDate localDate);

}
