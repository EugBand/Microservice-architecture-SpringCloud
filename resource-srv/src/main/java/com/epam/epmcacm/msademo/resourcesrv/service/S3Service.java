package com.epam.epmcacm.msademo.resourcesrv.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.epam.epmcacm.msademo.resourcesrv.dto.StorageDto;
import com.epam.epmcacm.msademo.resourcesrv.exception.BadRequestException;
import com.epam.epmcacm.msademo.resourcesrv.exception.FileProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;

import static com.epam.epmcacm.msademo.resourcesrv.service.ResourceService.ERROR_DOWNLOADING_MP_3_FILE;

@Service
@Slf4j
public class S3Service {

    public static final String JAVA_IO_TMPDIR = "java.io.tmpdir";

    private final AmazonS3 amazonS3;

    @Autowired
    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    private static final String fileExtension = ".mp3";

    public String upLoadFile(MultipartFile mp3File, String resourceId, String s3BucketName, String path) throws IOException {
        String fullPath = getFilePath(resourceId, path);
        File file = multipartToFile(mp3File, resourceId + fileExtension);
        amazonS3.putObject(s3BucketName, fullPath, file);
        return resourceId;
    }

    public byte[] downLoadFile(String resourceId, String s3BucketName, String path) {
        String fullPath = getFilePath(resourceId, path);
        ObjectListing objectListing = amazonS3.listObjects(s3BucketName);
        if (objectListing.getObjectSummaries().stream().anyMatch(item -> item.getKey().equals(fullPath))) {
            S3Object s3object = amazonS3.getObject(s3BucketName, fullPath);
            try {
                return s3object.getObjectContent().getDelegateStream().readAllBytes();
            } catch (IOException e) {
                log.error("Cant read file: {} from bucket: {}", fullPath, s3BucketName);
                throw new FileProcessingException(ERROR_DOWNLOADING_MP_3_FILE + e.getMessage(), e);
            }
        }
        throw new BadRequestException(String.format("File with id: %s in S3 repo not dound", resourceId));
    }

    public void moveFile(String resourceId, StorageDto oldStorage, StorageDto newStorage){
        String fullOldPath = getFilePath(resourceId, oldStorage.getPath());
        String fullNewPath = getFilePath(resourceId, newStorage.getPath());
        amazonS3.copyObject(oldStorage.getBucketName(), fullOldPath, newStorage.getBucketName(), fullNewPath);
    }


    public String deleteFile(String resourceId, String s3BucketName, String path){
        amazonS3.deleteObject(s3BucketName, getFilePath(resourceId, path));
        return resourceId + fileExtension;
    }

    private String getFilePath(String resourceId, String s3Mp3Folder){
        return s3Mp3Folder + File.separator + resourceId + fileExtension;
    }

//    private String getFullFilePath(String resourceId, String buckedName){
//        return buckedName + File.separator + s3Mp3Folder + File.separator + resourceId + fileExtension;
//    }

    @NotNull
    private File multipartToFile(@NotNull MultipartFile multipart, String fileName)
            throws IOException {
        File convFile = new File(System.getProperty(JAVA_IO_TMPDIR) + File.separator + fileName);
        multipart.transferTo(convFile);
        return convFile;
    }
}
