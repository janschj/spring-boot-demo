package org.baeldung.service;

import org.baeldung.persistence.MediaAgencyEntity;
import org.baeldung.persistence.repository.MediaAgencyRepository;
import org.baeldung.presentation.dto.MediaAgency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MediaAgencyService {

	MediaAgencyRepository mediaAgencyRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	public MediaAgencyService(MediaAgencyRepository mediaAgencyRepository) {
		this.mediaAgencyRepository = mediaAgencyRepository;
	}

	@Transactional
	@Async
	public void createMediaAgency(MediaAgency mediaAgency) {
		MediaAgencyEntity entity = toEntity.apply(mediaAgency);
		mediaAgencyRepository.save(entity);
		mediaAgency.setId(entity.getId());
	}

	@Transactional(readOnly = true)
	@Async
	public CompletableFuture<List<MediaAgency>> getMediaAgencies() {
		List<MediaAgencyEntity> mediaAgencies = mediaAgencyRepository.findAll();
		return CompletableFuture.completedFuture(toDtos.apply(mediaAgencies));
	}

	@Transactional(readOnly = true)
	@Async
	public CompletableFuture<MediaAgency> getMediaAgency(String mediaAgencyId) {
		Optional<MediaAgencyEntity> e = mediaAgencyRepository.findById(mediaAgencyId);
		MediaAgency agency = null;
		if (e.isPresent()) {
			agency = toDto.apply(e.get());
		}
		return CompletableFuture.completedFuture(agency);
	}

	private Function<MediaAgency, MediaAgencyEntity> toEntity = dto -> {
		return new MediaAgencyEntity(dto.getId(), dto.getName(), dto.getEmail());
	};

	private Function<MediaAgencyEntity, MediaAgency> toDto = e -> {
		return new MediaAgency(e.getId(), e.getName(), e.getEmail());
	};

	private Function<List<MediaAgencyEntity>, List<MediaAgency>> toDtos = es -> {
		List<MediaAgency> transactionsIds = es.stream().map(e -> toDto.apply(e)).collect(Collectors.toList());
		return transactionsIds;
	};
}
