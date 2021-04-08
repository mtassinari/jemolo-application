package it.laziocrea.jemoloapp.repository;

import it.laziocrea.jemoloapp.domain.Comune;
import it.laziocrea.jemoloapp.domain.IProvinciaList;
import it.laziocrea.jemoloapp.domain.Provincia;
import it.laziocrea.jemoloapp.domain.ProvinciaList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Comune entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComuneRepository extends JpaRepository<Comune, Long> {
	List<Comune> findDBySiglaProvinciaIgnoreCase(String siglaProvincia);
}
