package it.laziocrea.jemoloapp.repository;
import it.laziocrea.jemoloapp.domain.StatoRegistrazione;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StatoRegistrazione entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatoRegistrazioneRepository extends JpaRepository<StatoRegistrazione, Long> {

}
