package org.baeldung.persistence.repository;


import java.util.Optional;

import org.baeldung.persistence.MediaAgencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface MediaAgencyRepository extends JpaRepository<MediaAgencyEntity, String> {

	public Optional<MediaAgencyEntity> findByName(String name);
}
