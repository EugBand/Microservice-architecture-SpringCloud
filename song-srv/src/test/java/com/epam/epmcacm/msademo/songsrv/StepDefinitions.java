package com.epam.epmcacm.msademo.songsrv;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Scanner;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.github.tomakehurst.wiremock.WireMockServer;
import wiremock.com.fasterxml.jackson.databind.JsonNode;
import wiremock.com.fasterxml.jackson.databind.ObjectMapper;

public class StepDefinitions {

    private static final String id = "a8995694-7a86-46d1-82b0-aab47f7b6d9e";

    private static final String rawMetadata = "{\"metadata\":{\"xmpDM:audioSampleRate\":\"44100\",\"channels\":\"2\",\"xmpDM:audioCompressor\":\"MP3\",\"xmpDM:audioChannelType\":\"Stereo\",\"version\":\"MPEG3LayerIIIVersion1\",\"xmpDM:duration\":\"3265.310302734375\",\"Content-Type\":\"audio/mpeg\",\"samplerate\":\"44100\"}}";
    public static final String URL_TEMPLATE = "/api/v1/songs";
    private static final String APPLICATION_JSON = "application/json";

    public static final String HTTP_LOCALHOST = "http://localhost:";

    public static final String QUERY_PARAM_IDS = "ids";

    private final InputStream jsonInputStream = this.getClass().getClassLoader().getResourceAsStream("metadata.json");
    private final String jsonString = new Scanner(jsonInputStream, "UTF-8").useDelimiter("\\Z").next();

    private final WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    private HttpResponse response;


    @When("uploads metadata on the service")
    public void uploadsMetadataOnTheService()  throws IOException {

        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());
        stubFor(post(urlEqualTo(URL_TEMPLATE))
                .withHeader("content-type", equalTo(APPLICATION_JSON))
                .withRequestBody(equalToJson(rawMetadata))
                .willReturn(aResponse().withBody(id).withStatus(200)));

        HttpPost request = new HttpPost(HTTP_LOCALHOST + wireMockServer.port() + URL_TEMPLATE);
        request.addHeader("content-type", APPLICATION_JSON);
        request.setEntity(new StringEntity(rawMetadata));
        response = httpClient.execute(request);

    }
    @Then("the server should return metadata id and success status")
    public void theServerShouldReturnMetadataIdAndSuccessStatus() {

        assertEquals(200, response.getStatusLine().getStatusCode());
        verify(postRequestedFor(urlEqualTo(URL_TEMPLATE))
                .withHeader("content-type", equalTo(APPLICATION_JSON)));

        wireMockServer.stop();
    }

    @When("gets metadata from the service")
    public void getsMetadataFromTheService() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonMetadata = objectMapper.readTree(jsonString);

        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());
        stubFor(get(urlEqualTo(URL_TEMPLATE + "/" + id))
                .willReturn(aResponse()
                        .withJsonBody(jsonMetadata)
                        .withHeader("content-type", APPLICATION_JSON)
                        .withStatus(200)));

        HttpGet request = new HttpGet(HTTP_LOCALHOST + wireMockServer.port() + URL_TEMPLATE + "/" + id);
        response = httpClient.execute(request);

    }
    @Then("the server should return metadata and success status")
    public void theServerShouldReturnMetadataAndSuccessStatus() throws IOException {

        assertEquals(200, response.getStatusLine().getStatusCode());

        wireMockServer.stop();
    }

    @When("delete metadata from the service")
    public void deleteMetadataFromTheService() throws IOException, URISyntaxException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonMetadata = objectMapper.readTree(jsonString);

        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());
        stubFor(delete(urlEqualTo(URL_TEMPLATE + "?ids=" + id))
                .willReturn(aResponse()
                        .withBody(id)
                        .withStatus(200)));

        HttpDelete request = new HttpDelete(new URIBuilder(HTTP_LOCALHOST + wireMockServer.port() + URL_TEMPLATE)
                .addParameter(QUERY_PARAM_IDS, id)
                .build());
        response = httpClient.execute(request);
    }
    @Then("the server should return deleted ids and success status")
    public void theServerShouldReturnDeletedIdsAndSuccessStatus() {
        assertEquals(200, response.getStatusLine().getStatusCode());

        wireMockServer.stop();
    }

}
