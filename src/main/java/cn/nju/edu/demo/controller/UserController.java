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
	
    //private final AtomicLong counter = new AtomicLong();
    @SuppressWarnings("finally")
	@RequestMapping("/api/signup")
//    {
//        "userEmail": "test1@nju.com",
//        "userPassword": "qwe123",
//        "userName": "测试用户"
//    }
    public retUser SignUp(@RequestBody Map<String,Object> mapRead) {
    	int status = 0;
    	//注册，比对数据库是否有信息，没有就添加
		retUser newUser = null;
		try {
			String userEmail = (String)mapRead.get("userEmail");
			// 已有数据 返回异常状态码2
			jdbcTemplate.queryForMap("select * from user where user_email = ?", userEmail);
			status = 2;
			newUser = new retUser(status, "null", "null", "null");
		} catch (Exception e) {
			// 插入数据 返回正常状态码0
			e.printStackTrace();
			newUser = new retUser(status, (String)mapRead.get("userEmail"), 
	    			(String)mapRead.get("userPassword"), (String)mapRead.get("userName"));
			User userInsert = newUser.getData();
			jdbcTemplate.update("insert into user"
					+ "(user_id,user_email,user_name,user_password, user_token) values (?,?,?,?,?)", 
					userInsert.getUserId(), userInsert.getUserEmail(), userInsert.getUserName(),
					userInsert.getUserPassword(), userInsert.getUserToken());
		} finally {
			return newUser;
		}
    }
    
    @RequestMapping("/api/signin")
//    {
//        "userEmail": "test1@nju.com",
//        "userPassword": "qwe123"
//    }
    public retUser SignIn(@RequestBody Map<String,Object> mapRead) {
    	int status = 0;
    	// 登录，基本用户信息从数据库录入
		Map<String, Object> mapDb;
		try {
			// 已有用户 返回正常状态码0
			String userEmail = (String)mapRead.get("userEmail");
			mapDb = jdbcTemplate.queryForMap("select * from user where userEmail = ?", userEmail);
			return new retUser(status, mapDb.get("userEmail").toString(), 
    				mapDb.get("userPassword").toString(), mapDb.get("userName").toString());
		} catch (DataAccessException e) {
			e.printStackTrace();
			status = 2;
			return new retUser(status, "null", "null", "null");
		}
	
    }
    
    @RequestMapping("/")
    public String hello(){
        return "Welcome to start SpringBoot!";
    }
    
    public class retUser{
    	private final int status; 
    	private final User data;
		public retUser(int status, String userEmail, String userPassword, String userName) {
			this.status = status;
			if(userEmail == "null") {
				this.data = null;
			}
			else {
				this.data = new User(userEmail, userPassword, userName, UUID.randomUUID().toString());
			}
			
		}
		public int getStatus() {
			return status;
		}
		public User getData() {
			return data;
		}
    }
}