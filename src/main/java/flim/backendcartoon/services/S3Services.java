package flim.backendcartoon.services;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.UUID;


@Service
public class S3Services {

    private final S3Client s3Client;

    private final Dotenv dotenv = Dotenv.load();

    private final String bucketName = dotenv.get("AWS_S3_BUCKET_NAME");


    public S3Services(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(MultipartFile file, String folder) throws IOException {
        String key = folder + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, software.amazon.awssdk.core.sync.RequestBody.fromInputStream(
                file.getInputStream(), file.getSize()
        ));

        return "https://" + bucketName + ".s3.amazonaws.com/" + key;
    }

    public String uploadVideo(MultipartFile video) throws IOException {
        return uploadFile(video, "videos");
    }

    public String uploadThumbnail(MultipartFile thumbnail) throws IOException {
        return uploadFile(thumbnail, "thumbnails");
    }

}
