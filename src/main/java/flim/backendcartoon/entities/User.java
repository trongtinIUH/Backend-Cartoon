package flim.backendcartoon.entities;


import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.List;

@DynamoDbBean
public class User {

    private  String userId;
    private String phoneNumber;
    private  String userName;
    private  String createdAt;
    private String dob;
    private List<String>  movieIds;
    private Role role;
    private String email;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("userId")
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DynamoDbAttribute("dob")
    public String getDob() {
        return dob;
    }
    public void setDob(String dob) {
        this.dob = dob;
    }

    @DynamoDbAttribute("phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    @DynamoDbAttribute("role")
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    @DynamoDbAttribute("email")
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}
