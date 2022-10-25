package com.epam.epmcacm.msademo.resourcesrv.service;

import com.epam.epmcacm.msademo.resourcesrv.dto.MetadataDto;
import com.epam.epmcacm.msademo.resourcesrv.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class ResourceProcessorService {

    public static final String ERROR_READ_DOWNLOADED_FILE = "Error read downloaded file";

    public static final String ERROR_PARSE_MP3_METADATA = "Error parse mp3 metadata";

    public MetadataDto getMetadata(MultipartFile multipartFile){
        ContentHandler handler = new DefaultHandler();
        Metadata metadata = new Metadata();
        Parser parser = new Mp3Parser();
        ParseContext parseCtx = new ParseContext();
        InputStream stream;
        try {
            stream = multipartFile.getInputStream();
        } catch (IOException e) {
            log.error(ERROR_READ_DOWNLOADED_FILE);
            throw new BadRequestException(ERROR_READ_DOWNLOADED_FILE, e);
        }
        try {
            parser.parse(stream, handler, metadata, parseCtx);
        } catch (IOException | SAXException | TikaException e) {
            log.error(ERROR_PARSE_MP3_METADATA);
            throw new BadRequestException(ERROR_PARSE_MP3_METADATA, e);
        }

        return new MetadataDto(metadata);
    }

    public MetadataDto getMetadata(byte[] fileContent){
        ContentHandler handler = new DefaultHandler();
        Metadata metadata = new Metadata();
        Parser parser = new Mp3Parser();
        ParseContext parseCtx = new ParseContext();
        InputStream stream = new ByteArrayInputStream(fileContent);
        try {
            parser.parse(stream, handler, metadata, parseCtx);
        } catch (IOException | SAXException | TikaException e) {
            log.error(ERROR_PARSE_MP3_METADATA);
            throw new BadRequestException(ERROR_PARSE_MP3_METADATA, e);
        }

        return new MetadataDto(metadata);
    }
}
