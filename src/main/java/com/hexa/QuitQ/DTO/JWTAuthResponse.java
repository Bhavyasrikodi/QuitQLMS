package com.hexa.QuitQ.DTO;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import com.hexa.QuitQ.enums.UserRole;


public class JWTAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String user_name;
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    private Long id;  // This will hold the customer, seller, or admin ID based on the role

    public JWTAuthResponse() { }

    public JWTAuthResponse(String accessToken, String email, UserRole userRole, String user_name) {
        this.accessToken = accessToken;
        this.user_name = user_name;
        this.email = email;
        this.userRole = userRole;
    }

    // Getters and setters

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
