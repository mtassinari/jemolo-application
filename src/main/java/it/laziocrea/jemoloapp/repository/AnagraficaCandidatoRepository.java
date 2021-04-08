package it.laziocrea.jemoloapp.repository;

import it.laziocrea.jemoloapp.domain.Anagrafica;
import it.laziocrea.jemoloapp.domain.AnagraficaCandidato;

import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AnagraficaCandidato entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnagraficaCandidatoRepository extends JpaRepository<AnagraficaCandidato, Long>,  JpaSpecificationExecutor<AnagraficaCandidato> {
	Optional<AnagraficaCandidato> findOneByIndirizzoPecIgnoreCase(String indirizzoPec);
	Optional<AnagraficaCandidato> findOneByPartitaIvaIgnoreCase(String pIva);
	Page<AnagraficaCandidato> findByNome(String nome, Pageable page);
	@Query("SELECT ac FROM AnagraficaCandidato ac WHERE ac.nome = :nome and ac.cognome = :cognome")
	Page<AnagraficaCandidato> ricercaIscritti(@Param("nome") String nome, @Param("cognome") String cognome, Pageable page);
	// Page<AnagraficaCandidato> findByNomeContainsIgnoreCaseAndCognomeContainsIgnoreCase(String nome, String cognome, Pageable page);
	Anagrafica save(Anagrafica anagrafica);
	List<AnagraficaCandidato> findAllByLastModifiedDateBefore(Instant minus);
	List<AnagraficaCandidato> findAllByLastModifiedDateBeforeAndCandidatoPrimoAvvisoIsFalse(Instant minus);
	List<AnagraficaCandidato> findAllByLastModifiedDateBeforeAndCandidatoSecondoAvvisoIsFalse(Instant minus);
	List<AnagraficaCandidato> findAllByLastModifiedDateBeforeAndCandidatoStatoIdEquals(Instant instant, Long i);
}
