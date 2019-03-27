package cn.nju.edu.demo.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.nju.edu.model.User;

@RestController
public class UserController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

	@RequestMapping("/api/signup")
    public retUser SignUp(@RequestBody Map<String,Object> mapRead) {
    	int status = 0;
    	//注册，比对数据库是否有信息，没有就添加
    	if(mapRead.size() != 3 || !mapRead.containsKey("userEmail")
    			 || !mapRead.containsKey("userPassword") || !mapRead.containsKey("userName")) {
    		// 输入格式错误
    		status = 2;
    		return new retUser(status, null);
    	} 
		String userEmail = (String)mapRead.get("userEmail");
		String userPassword = (String)mapRead.get("userPassword");
		String userName = (String)mapRead.get("userName");
		try {
			// 已有数据 返回异常状态码1
			jdbcTemplate.queryForMap("select * from user where user_email = ?", userEmail);
			status = 1;
			return new retUser(status, null);
		} catch (Exception e) {
			// 插入数据 返回正常状态码0
			retUser newUser = new retUser(status, new User(userEmail, userPassword, 
					userName, UUID.randomUUID().toString()));
			User userInsert = newUser.getData();
			jdbcTemplate.update("insert into user"
					+ "(user_id, user_email, user_name, user_password, user_token) values (?,?,?,?,?)", 
					userInsert.getUserId(), userEmail, userName,
					userPassword, userInsert.getUserToken());
			return newUser;
		}
    }
    
    @RequestMapping("/api/signin")
    public retUser SignIn(@RequestBody Map<String,Object> mapRead) {
    	int status = 0;
    	// 登录，基本用户信息从数据库录入
		Map<String, Object> mapDb;
    	if(mapRead.size() != 2 || !mapRead.containsKey("userEmail") || !mapRead.containsKey("userPassword")) {
    		// 输入格式错误
    		status = 2;
    		return new retUser(status, null);
    	} 
		String userEmail = (String)mapRead.get("userEmail");
		String userPassword = (String)mapRead.get("userPassword");
		try {
			// 已有用户 返回正常状态码0
			mapDb = jdbcTemplate.queryForMap("select * from user where "
					+ "user_email = ? and user_password = ?", userEmail, userPassword);
			return new retUser(status, new User(userEmail, userPassword, mapDb.get("user_name").toString(),
    				mapDb.get("user_id").toString(), mapDb.get("user_token").toString()));
		} catch (DataAccessException e) {
			status = 1;
			return new retUser(status, null);
		}
    }

    public class retUser{
    	private final int status; 
    	private final User data;
		public retUser(int status, User data) {
			this.status = status;
			this.data = data;
		}			
		public int getStatus() {
			return status;
		}
		public User getData() {
			return data;
		}
    }
}