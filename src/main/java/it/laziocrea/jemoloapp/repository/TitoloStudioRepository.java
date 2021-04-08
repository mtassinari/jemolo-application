package it.laziocrea.jemoloapp.repository;
import it.laziocrea.jemoloapp.domain.TitoloStudio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TitoloStudio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TitoloStudioRepository extends JpaRepository<TitoloStudio, Long> {

}
