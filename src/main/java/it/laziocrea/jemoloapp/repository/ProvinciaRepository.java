package it.laziocrea.jemoloapp.repository;

import it.laziocrea.jemoloapp.domain.Provincia;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Provincia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {

	List<Provincia> findAllByOrderByNomeAsc();
}
