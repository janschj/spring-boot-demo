package org.baeldung.test.persistance;


import java.util.Optional;

import org.baeldung.persistence.MediaAgencyEntity;
import org.baeldung.persistence.repository.MediaAgencyRepository;
import org.baeldung.presentation.dto.MediaAgency;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@DataJpaTest
public class MediaAgencyRepositoryTest {
 
    @Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private MediaAgencyRepository mediaAgencyRepository;
 
    @Test
    public void whenFindByName_thenReturnEmployee() {
        // given
    	MediaAgencyEntity brandspot = new MediaAgencyEntity(null, "Brandspot", "email.com");
        entityManager.persist(brandspot);
        entityManager.flush();
     
        // when
        Optional<MediaAgencyEntity> found = mediaAgencyRepository.findById(brandspot.getName());
     
        Assert.assertTrue(found.get().getName().equalsIgnoreCase(brandspot.getName()));
    } 
}