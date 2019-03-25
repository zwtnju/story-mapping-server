package cn.nju.edu.model;

import java.util.UUID;

public class User {
	private final String userEmail;
	private final String userPassword;
    private final String userName;
    private final String userId;
    private final String userToken;
    
    public User(String userEmail, String userPassword, String userName, String userId) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userToken = UUID.randomUUID().toString();
        this.userId = userId;
    }
    
	public String getUserEmail() {
		return userEmail;
	}
	
	public String getUserPassword() {
		return userPassword;
	}
	
	public String getUserName() {
		return userName;
	}

	public String getUserToken() {
		return userToken;
	}

	public String getUserId() {
		return userId;
	}
}
