package org.baeldung.presentation.controller;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.baeldung.presentation.dto.MediaAgency;
import org.baeldung.service.MediaAgencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;



@RestController
public class MediaAgencyController {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    MediaAgencyService mediaAgencyService;

    @RequestMapping(value = "/mediaagencies", method = RequestMethod.POST)
    public ResponseEntity<Void> createMediaAgency(@RequestBody MediaAgency mediaAgency, UriComponentsBuilder ucBuilder) {

        LOG.debug("createMediaAgency() - MediaAgency{}", mediaAgency);
        
        validate(mediaAgency);

        mediaAgencyService.createMediaAgency(mediaAgency);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
				"/{id}").buildAndExpand(mediaAgency.getId()).toUri();

		return ResponseEntity.created(location).build();

    }

    private void validate(MediaAgency mediaAgency) {
//        if (mediaAgency == null) {
 //           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
 //       }
    }
    
    @RequestMapping(value = "/mediaagencies", method = RequestMethod.GET)
    public ResponseEntity<List<MediaAgency>> getMediaAgencies() {
        List<MediaAgency> mediaAgencies;
		try {
			mediaAgencies = mediaAgencyService.getMediaAgencies().get();
		} catch (InterruptedException e) {
            return new ResponseEntity<>(HttpStatus.GATEWAY_TIMEOUT);
		} catch (ExecutionException e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
        LOG.debug("getMediaAgencies() - MediaAgency{}", mediaAgencies);
        return new ResponseEntity<>(mediaAgencies, HttpStatus.OK);
    }

    @RequestMapping(value = "/mediaagencies/{mediaAgencyId}", method = RequestMethod.GET)
    public ResponseEntity<MediaAgency> getMediaAgency(@PathVariable("mediaAgencyId") String mediaAgencyId) {
        LOG.debug("getMediaAgency() - mediaAgencyId{}", mediaAgencyId);
        MediaAgency mediaAgency;
		try {
			mediaAgency = mediaAgencyService.getMediaAgency(mediaAgencyId).get();
	        if (mediaAgency == null) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<>(mediaAgency, HttpStatus.OK);
		} catch (InterruptedException | ExecutionException e) {
            return new ResponseEntity<>(HttpStatus.GATEWAY_TIMEOUT);
		}
    }


}
