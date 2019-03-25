package cn.nju.edu.controller;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.nju.edu.model.Test;
import cn.nju.edu.model.User;

@RestController
public class UserController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/api/signup")
    public retUser SignUp(@RequestBody Map<String,Object> map) {
    	return new retUser((String)map.get("userEmail"), (String)map.get("userPassword"), (String)map.get("userName"));
    }
    
    @RequestMapping("/api/signin")
    public retUser SignIn(@RequestBody Map<String,Object> map) {
    	return new retUser((String)map.get("userEmail"), (String)map.get("userPassword"), (String)map.get("userName"));
    }
    
    public class retUser{
    	private final int status; 
    	private final User data;
		private String userId;
		public retUser(String userEmail, String userPassword, String userName) {
			// 加上数据库的部分 
			this.userId = UUID.randomUUID().toString();
			this.status = 0;
			this.data = new User(userEmail, userPassword, userName, userId);
		}
		public int getStatus() {
			return status;
		}
		public User getData() {
			return data;
		}
    }
    
    @RequestMapping("/")
    public String hello(){
        return "Welcome to start SpringBoot!";
    }
}