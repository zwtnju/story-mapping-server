package cn.nju.edu.demo.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DbController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @RequestMapping("/user")
    public void getUser(){
    	jdbcTemplate.update("insert into appuser(id,name) values (?,?)", 3, "luck");
    }
    
    @RequestMapping("/user2")
    public String getUser2(){
    		Map<String, Object> map;
			try {
				map = jdbcTemplate.queryForMap("select * from appuser where id = 1");
				return (map.get("id").toString()+"\t"+map.get("name").toString());
			} catch (DataAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "NULL";
			}
    }
    
}