package it.laziocrea.jemoloapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.laziocrea.jemoloapp.domain.CurriculumFile;

/**
 * Spring Data  repository for the CurriculumFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CurriculumFileRepository extends JpaRepository<CurriculumFile, Long>{

}
