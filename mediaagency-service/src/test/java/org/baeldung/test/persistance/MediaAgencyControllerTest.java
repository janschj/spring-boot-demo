package org.baeldung.test.persistance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.baeldung.MediaAgencyServerApplication;
import org.baeldung.presentation.controller.MediaAgencyController;
import org.baeldung.presentation.dto.MediaAgency;
import org.baeldung.service.MediaAgencyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MediaAgencyController.class, secure = false)
public class MediaAgencyControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    TokenStore tokenStoreeMock;
    @MockBean
    MediaAgencyService mediaAgencyServiceMock;
    @Autowired
    ObjectMapper objectMapper;
    
    
    @Test
    public void createAgentSuccessfully() throws Exception {
    	MediaAgency exampleMediaAgency = new MediaAgency(null, "Brandspot", "brandspot@gmail.com");
    	String exampleMediaAgencyJson = objectMapper.writeValueAsString(exampleMediaAgency);

    	Mockito.doAnswer((Answer) invocation -> {
            Object arg0 = invocation.getArgument(0);
            MediaAgency ma = (MediaAgency) arg0;
            assertNull(ma.getId());
            ma.setId("1");
            return null;
        }).when(mediaAgencyServiceMock).createMediaAgency(Mockito.any(MediaAgency.class));
    	         
 		// Send course as body to /students/Student1/courses
 		RequestBuilder requestBuilder = MockMvcRequestBuilders
 				.post("/mediaagencies")
 				.accept(MediaType.APPLICATION_JSON).content(exampleMediaAgencyJson)
 				.contentType(MediaType.APPLICATION_JSON);

 		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

 		MockHttpServletResponse response = result.getResponse();

 		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

 		assertEquals("http://localhost/mediaagencies/1",
 				response.getHeader(HttpHeaders.LOCATION));
    }

    @Test
    public void getAgentSuccessfully() throws Exception {
    	MediaAgency exampleMediaAgency = new MediaAgency("1", "Brandspot", "brandspot@gmail.com");
    	String exampleMediaAgencyJson = objectMapper.writeValueAsString(exampleMediaAgency);

		Mockito.when(
				mediaAgencyServiceMock.getMediaAgency(Mockito.anyString()).get())
		.thenReturn(exampleMediaAgency);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"http://localhost/mediaagencies/1").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

		String expected = objectMapper.writeValueAsString(exampleMediaAgency);

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
    }
    
}