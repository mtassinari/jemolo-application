package it.laziocrea.jemoloapp.repository;

import it.laziocrea.jemoloapp.domain.Authority;
import it.laziocrea.jemoloapp.domain.Candidato;
import it.laziocrea.jemoloapp.domain.User;
import it.laziocrea.jemoloapp.service.dto.CandidatoDTO;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the {@Link Candidato} entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, Long> {

	Optional<Candidato> findOneByCodiceFiscaleIgnoreCase(String codiceFiscale);

	Optional<Candidato> findOneByActivationKey(String activationKey);
	
	List<Candidato> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);
	
	Optional<Candidato> findOneByResetKey(String resetKey);
	
	Optional<Candidato> findOneByEmailIgnoreCase(String email);
	
    Optional<Candidato> findOneByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    Optional<Candidato> findOneWithAuthoritiesById(Long id);

    @EntityGraph(attributePaths = "authorities")
    Optional<Candidato> findOneWithAuthoritiesByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    Optional<Candidato> findOneWithAuthoritiesByEmailIgnoreCase(String email);

    Page<Candidato> findAllByLoginNot(Pageable pageable, String login);

	Page<Candidato>  findAllByActivatedIsTrue(Pageable pageable);
	
	Page<Candidato>  findAllByActivatedIsTrueOrderByCognomeAscNomeAsc(Pageable pageable);
}
