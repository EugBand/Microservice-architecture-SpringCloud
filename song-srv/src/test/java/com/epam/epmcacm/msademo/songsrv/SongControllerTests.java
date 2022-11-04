package com.epam.epmcacm.msademo.songsrv;

import com.epam.epmcacm.msademo.songsrv.controller.SongController;
import com.epam.epmcacm.msademo.songsrv.dto.MetadataDto;
import com.epam.epmcacm.msademo.songsrv.service.SongMetadataService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@AutoConfigureMessageVerifier
public class SongControllerTests {

    private static final String rawMetadata = "{\"metadata\":{\"xmpDM:audioSampleRate\":\"44100\",\"channels\":\"2\",\"xmpDM:audioCompressor\":\"MP3\",\"xmpDM:audioChannelType\":\"Stereo\",\"version\":\"MPEG3LayerIIIVersion1\",\"xmpDM:duration\":\"3265.310302734375\",\"Content-Type\":\"audio/mpeg\",\"samplerate\":\"44100\"}}";

    private static final Map<String, String> metadataMap = Map.of(
            "channels", "2",
            "xmpDM:audioCompressor", "MP3",
            "xmpDM:audioChannelType", "Stereo",
            "version", "MPEG 3 Layer III Version 1",
            "xmpDM:duration", "3265.310302734375",
            "Content-Type", "audio/mpeg",
            "samplerate", "44100");

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    public static final String URL_TEMPLATE = "/api/v1/songs/";

    public static final String UUID_REGEXP_PATTERN = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";

    private MockMvc mockMvc;

    @Autowired private SongController songController;

    @SpyBean
    private SongMetadataService songMetadataService;

    private final SoftAssertions softly = new SoftAssertions();

    @BeforeEach
    public void setup() {
        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(songController);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
        mockMvc = standaloneMockMvcBuilder.build();
    }

    @Test
    public void shouldCreateMetadataWhenValidRequest() throws Exception {

        //when
        final var request = MockMvcRequestBuilders.post(
                        URL_TEMPLATE)
                .header("Content-Type", "application/json")
                .contentType(APPLICATION_JSON_UTF8)
                .content(rawMetadata);

        final var response = mockMvc.perform(request).andReturn();
        final var content = response.getResponse();

        // then
        softly.assertThat(content.getStatus()).isEqualTo(HttpStatus.OK.value());
        softly.assertThat(content.getContentAsString().matches(UUID_REGEXP_PATTERN));
        softly.assertAll();
    }

    @Test
    public void shouldReturnMetadataWhenValidRequest() throws Exception {

        //given
        MetadataDto metadataDto = new MetadataDto(metadataMap);
        String id = songMetadataService.createMetadata(metadataDto);

        //when
        final var response = mockMvc.perform(get(URL_TEMPLATE + id)).andReturn();
        final var content = response.getResponse();

        //then
        softly.assertThat(content.getStatus()).isEqualTo(HttpStatus.OK.value());
        softly.assertThat(content.getContentLength()).isGreaterThan(0);
        softly.assertThat(content.getContentAsString()).isEqualTo(rawMetadata);
        softly.assertAll();
    }

    @Test
    public void shouldDeleteMetadataWhenValidRequest() throws Exception {

        //given
        MetadataDto metadataDto = new MetadataDto(metadataMap);
        String id = songMetadataService.createMetadata(metadataDto);

        //when
        final var response = mockMvc.perform(delete(URL_TEMPLATE).queryParam("ids", id))
                .andReturn();
        final var content = response.getResponse();

        //then
        softly.assertThat(content.getStatus()).isEqualTo(HttpStatus.OK.value());
        softly.assertAll();
    }

}
