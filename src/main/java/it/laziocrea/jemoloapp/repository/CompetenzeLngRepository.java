package it.laziocrea.jemoloapp.repository;
import it.laziocrea.jemoloapp.domain.CompetenzeLng;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CompetenzeLng entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetenzeLngRepository extends JpaRepository<CompetenzeLng, Long> {

}
