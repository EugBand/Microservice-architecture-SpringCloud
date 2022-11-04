package com.epam.epmcacm.msademo.apitests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.with;

public class RestAssuredAppTest {

    protected static final String rawMetadata = "{\"metadata\":{\"xmpDM:audioSampleRate\":\"44100\",\"channels\":\"2\",\"xmpDM:audioCompressor\":\"MP3\",\"xmpDM:audioChannelType\":\"Stereo\",\"version\":\"MPEG3LayerIIIVersion1\",\"xmpDM:duration\":\"3265.310302734375\",\"Content-Type\":\"audio/mpeg\",\"samplerate\":\"44100\"}}";

    protected static final String SONG_URL = "/api/v1/songs/";
    protected static final String RESOURCE_URL = "/api/v1/resources";

    protected static final String UUID_REGEXP_MATCHER = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";

    @BeforeEach
    public void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    public void whenRequestAllSongApisThenOK(){
        with().body(rawMetadata);

        String songMetadataId = given().contentType("application/json")
                .body(rawMetadata)
                .when()
                .post(SONG_URL)
                .then()
                .assertThat()
                .statusCode(200)
                .extract().body().asString();
        Assertions.assertTrue(songMetadataId.matches(UUID_REGEXP_MATCHER));

        String metadata = when().get(SONG_URL + songMetadataId)
                .then()
                .assertThat()
                .statusCode(200)
                .extract().body().asString();
        Assertions.assertEquals(metadata, rawMetadata);

        String deletedId = given().param("ids", songMetadataId)
                .when().delete(SONG_URL)
                .then()
                .assertThat()
                .statusCode(200)
                .extract().body().asString();
        Assertions.assertEquals(deletedId.replaceAll("[\\[\\]\"]", ""), songMetadataId);
    }

}
