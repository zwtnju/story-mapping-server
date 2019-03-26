package cn.nju.edu.demo.controller;


import java.util.List;
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
    
    @RequestMapping("/user1")
    public void getUser(){
    	jdbcTemplate.update("insert into appuser(id,name) values (?,?)", 3, "luck");
    }
    
    @RequestMapping("/user2")
    public String getUser2(){
    		List<Map<String, Object>> map;
			try {
				map = jdbcTemplate.queryForList("select * from appuser where id = 3");
				String ret = "n";
				
				for(Map<String, Object> pairs : map) {
					ret += pairs.get("name").toString();
				}
				return (ret);
			} catch (DataAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "NULL";
			}
    }
    
    @SuppressWarnings("finally")
	@RequestMapping("/user3")
    public String getUser3(){
    	Map<String, Object> map;
		try {
			int id = 1;
			String name = "tom";
			map = jdbcTemplate.queryForMap("select * from appuser where id = ? and name = ?", id, name);
			return (map.get("id").toString()+"\t"+map.get("name").toString());
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "NULL";
		} finally {
			return "finally";
		}
    }
    
    @RequestMapping("/user4")
    public String getUser4(){
		try {
			jdbcTemplate.update("delete from appuser where id = 1");
			return ("111");
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "NULL";
		}
    }
    
}