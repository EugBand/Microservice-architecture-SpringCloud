package com.epam.epmcacm.msademo.songsrv;

import com.epam.epmcacm.msademo.songsrv.dto.MetadataDto;
import com.epam.epmcacm.msademo.songsrv.exception.entity.SongMetadata;
import com.epam.epmcacm.msademo.songsrv.repository.SongMetadataRepository;
import com.epam.epmcacm.msademo.songsrv.service.SongMetadataService;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SongMetadataServiceTests {

    @Mock private SongMetadataRepository repository;

    private SongMetadataService service;

    private final SoftAssertions softly = new SoftAssertions();


    private static final String id = "a8995694-7a86-46d1-82b0-aab47f7b6d9e";

    private static final Map<String, String> metadataMap = Map.of(
            "channels", "2",
            "xmpDM:audioCompressor", "MP3",
            "xmpDM:audioChannelType", "Stereo",
            "version", "MPEG 3 Layer III Version 1",
            "xmpDM:duration", "3265.310302734375",
            "Content-Type", "audio/mpeg",
            "samplerate", "44100");

    @BeforeEach
    void setUp() {
        service = new SongMetadataService(repository);
    }

    @Test
    void shouldFetchMetadataAndMapToResponse(){
        //given
        MetadataDto expectedResponse = new MetadataDto(metadataMap);
        Instant time = Instant.now();
        SongMetadata songMetadata = SongMetadata.builder()
                .metadata(expectedResponse.getMetadata())
                .id(id)
                .createdAt(time)
                .build();

        //when
        when(repository.findById(id)).thenReturn(Optional.of(songMetadata));
        MetadataDto actualResponse = service.fetchMetadata(id);

        //then
        softly.assertThat(expectedResponse).isEqualTo(actualResponse);
        softly.assertAll();
    }

    @Test
    void shouldCreateMetadataAndReturnId(){
        //given
        MetadataDto metadataDto = new MetadataDto(metadataMap);
        Instant time = Instant.now();
        SongMetadata songMetadata = SongMetadata.builder()
                .metadata(metadataDto.getMetadata())
                .id(id)
                .createdAt(time)
                .build();

        //when
        when(repository.save(any(SongMetadata.class))).thenReturn(songMetadata);
        String returnedId = service.createMetadata(metadataDto);

        //then
        softly.assertThat(returnedId).isEqualTo(id);
        softly.assertAll();
    }

    @Test
    void shouldDeleteMetadataByIdAndReturnId(){
        //given
        List<String> expectedResponse = List.of(id);
        //when
        List<String> actualResponse = service.deleteMetadatasByIds(expectedResponse);

        //then
        softly.assertThat(actualResponse).isEqualTo(expectedResponse);
        softly.assertAll();
    }

}
