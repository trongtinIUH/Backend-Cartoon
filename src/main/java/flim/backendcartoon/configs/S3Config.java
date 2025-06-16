package flim.backendcartoon.configs;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {
    private final Dotenv dotenv = Dotenv.load();

    private final String accessKey = dotenv.get("AWS_ACCESS_KEY_ID");
    private final String secretKey = dotenv.get("AWS_SECRET_ACCESS_KEY");
    private final String region = dotenv.get("AWS_REGION");

    @Bean(name = "customS3Client")
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }
}
