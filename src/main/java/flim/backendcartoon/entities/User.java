package flim.backendcartoon.entities;


import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.List;

@DynamoDbBean
public class User {

    private  String userId;
    private String soDienThoai;
    private  String userName;
    private  String createdAt;
    private List<String>  movieIds;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("userId")
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }


    @DynamoDbAttribute("soDienThoai")
    public String getSoDienThoai() {
        return soDienThoai;
    }
    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
    @DynamoDbAttribute("userName")
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    @DynamoDbAttribute("createdAt")
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    @DynamoDbAttribute("movieIds")
    public List<String> getMovieIds() {
        return movieIds;
    }
    public void setMovieIds(List<String> movieIds) {
        this.movieIds = movieIds;
    }

}
