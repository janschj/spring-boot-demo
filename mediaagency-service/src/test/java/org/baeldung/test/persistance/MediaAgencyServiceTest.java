package org.baeldung.test.persistance;

import static org.junit.Assert.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

import java.util.Optional;

import org.baeldung.persistence.MediaAgencyEntity;
import org.baeldung.persistence.repository.MediaAgencyRepository;
import org.baeldung.presentation.dto.MediaAgency;
import org.baeldung.service.MediaAgencyService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class MediaAgencyServiceTest {
	private MediaAgencyService mediaAgencyService;
	private MediaAgencyRepository mediaAgencyRepositoryMock;

	@Before
	public void setUp() {
		mediaAgencyRepositoryMock = Mockito.mock(MediaAgencyRepository.class);
		mediaAgencyService = new MediaAgencyService(mediaAgencyRepositoryMock);
	}

	@Test
	public void MediaAgencySuccessfuly() throws Exception {
		MediaAgencyEntity exampleMediaAgencyEntity = new MediaAgencyEntity("1", "Brandspot", "brandspot@gmail.com");
		Mockito.when(mediaAgencyRepositoryMock.findById(Matchers.eq("x")))
				.thenReturn(Optional.ofNullable(exampleMediaAgencyEntity));

		Mockito.doAnswer(returnsFirstArg()).when(mediaAgencyRepositoryMock).save(Matchers.any(MediaAgencyEntity.class));
		mediaAgencyService.createMediaAgency(new MediaAgency(exampleMediaAgencyEntity.getId(),
				exampleMediaAgencyEntity.getName(), exampleMediaAgencyEntity.getEmail()));
		assertEquals(exampleMediaAgencyEntity.getName(), exampleMediaAgencyEntity.getName());
	}

}