package flim.backendcartoon.entities;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class Episode {
    private String movieId;         // Partition Key
    private String episodeId;       // Sort Key hoặc unique ID
    private Integer episodeNumber;
    private String title;
    private String videoUrl;
    private String createdAt;


    @DynamoDbPartitionKey
    @DynamoDbAttribute("movieId")
    public String getMovieId() {
        return movieId;
    }
    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
    @DynamoDbSortKey
    @DynamoDbAttribute("episodeId")
    public String getEpisodeId() {
        return episodeId;
    }
    public void setEpisodeId(String episodeId) {
        this.episodeId = episodeId;
    }
    @DynamoDbAttribute("episodeNumber")
    public Integer getEpisodeNumber() {
        return episodeNumber;
    }
    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }
    @DynamoDbAttribute("title")
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    @DynamoDbAttribute("videoUrl")
    public String getVideoUrl() {
        return videoUrl;
    }
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
    @DynamoDbAttribute("createdAt")
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
