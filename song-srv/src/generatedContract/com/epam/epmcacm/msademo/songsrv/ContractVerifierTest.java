package com.epam.epmcacm.msademo.songsrv;

import com.epam.epmcacm.msademo.songsrv.SongSrvApplicationTests;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import io.restassured.response.ResponseOptions;

import static org.springframework.cloud.contract.verifier.assertion.SpringCloudContractAssertions.assertThat;
import static org.springframework.cloud.contract.verifier.util.ContractVerifierUtil.*;
import static com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;

@SuppressWarnings("rawtypes")
public class ContractVerifierTest extends SongSrvApplicationTests {

	@Test
	public void validate_getSongMetadata() throws Exception {
		// given:
			MockMvcRequestSpecification request = given();


		// when:
			ResponseOptions response = given().spec(request)
					.get("/api/v1/songs/a8995694-7a86-46d1-82b0-aab47f7b6d9e");

		// then:
			assertThat(response.statusCode()).isEqualTo(200);
			assertThat(response.header("Content-Type")).isEqualTo("application/json");

		// and:
			DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
			assertThatJson(parsedJson).field("['metadata']").field("['xmpDM:audioSampleRate']").isEqualTo("44100");
			assertThatJson(parsedJson).field("['metadata']").field("['channels']").isEqualTo("2");
			assertThatJson(parsedJson).field("['metadata']").field("['xmpDM:audioCompressor']").isEqualTo("MP3");
			assertThatJson(parsedJson).field("['metadata']").field("['xmpDM:audioChannelType']").isEqualTo("Stereo");
			assertThatJson(parsedJson).field("['metadata']").field("['version']").isEqualTo("MPEG 3 Layer III Version 1");
			assertThatJson(parsedJson).field("['metadata']").field("['xmpDM:duration']").isEqualTo("3265.310302734375");
			assertThatJson(parsedJson).field("['metadata']").field("['Content-Type']").isEqualTo("audio/mpeg");
			assertThatJson(parsedJson).field("['metadata']").field("['samplerate']").isEqualTo("44100");
	}

}
