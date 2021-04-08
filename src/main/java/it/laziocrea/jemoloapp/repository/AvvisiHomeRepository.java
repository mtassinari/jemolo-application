package it.laziocrea.jemoloapp.repository;

import it.laziocrea.jemoloapp.domain.AvvisiHome;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AvvisiHome entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvvisiHomeRepository extends JpaRepository<AvvisiHome, Long> {

	List<AvvisiHome> findByVisibileTrue();
}
