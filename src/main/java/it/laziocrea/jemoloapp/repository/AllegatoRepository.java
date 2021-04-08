package it.laziocrea.jemoloapp.repository;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import it.laziocrea.jemoloapp.domain.Allegato;


/**
 * Spring Data  repository for the Allegato entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AllegatoRepository extends JpaRepository<Allegato, Long> {

}
