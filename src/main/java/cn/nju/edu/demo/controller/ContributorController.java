package cn.nju.edu.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ContributorController {
	
    @Autowired
    private JdbcTemplate jdbcTemplate;
	
	// 添加协作者
    @RequestMapping("/api/contributor/create")
    // 多建一个表 关系表
    public retContribute ContributorCreate(@RequestBody Map<String,Object> mapRead) {
    	int status = 0;
    	if(mapRead.size() != 3 || !mapRead.containsKey("userToken")
    			 || !mapRead.containsKey("projectId")  || !mapRead.containsKey("contributorId") ) {
    		// 输入格式错误
    		status = 2;
    		return new retContribute(status);
    	}
    	
    	String userToken = (String)mapRead.get("userToken");
		String projectId = Integer.toString((Integer)(mapRead.get("projectId")));
		String contributorId = Integer.toString((Integer)(mapRead.get("contributorId")));

		List<Map<String, Object>> listDb = jdbcTemplate.queryForList("select * from project_user where "
				+ "user_token = ? and project_id = ? and contributor_id = ?", userToken, projectId, contributorId);
		if(listDb.isEmpty()) {
			// 数据库插入该协作者
			jdbcTemplate.update("insert into project_user"
					+ "(user_token, project_id, contributor_id) values (?, ?, ?)", 
					userToken, projectId, contributorId);
		} else {
			// 数据库已有该协作者 返回异常状态码1
			status = 1;
		}
		return new retContribute(status);
    }
    
    // 删除协作者
    @RequestMapping("/api/contributor/delete")
    public retContribute ContributorDelete(@RequestBody Map<String,Object> mapRead) {
    	int status = 0;
    	if(mapRead.size() != 3 || !mapRead.containsKey("userToken")
    			|| !mapRead.containsKey("projectId")  || !mapRead.containsKey("contributorId") ) {
    		// 输入格式错误
    		status = 2;
    		return new retContribute(status);
    	}
    	String userToken = (String)mapRead.get("userToken");
		String projectId = Integer.toString((Integer)(mapRead.get("projectId")));
		String contributorId = Integer.toString((Integer)(mapRead.get("contributorId")));
			
		try {
			// 删除协作者 返回正常
			jdbcTemplate.queryForMap("select * from project_user where "
					+ "user_token = ? and project_id = ? and contributor_id = ?", userToken, projectId, contributorId);			
			jdbcTemplate.update("delete from project_user where "
					+ "user_token = ? and project_id = ? and contributor_id = ?", userToken, projectId, contributorId);
			return new retContribute(status);
		} catch (DataAccessException e) {
			// 没有该协作者
			status = 1;
			return new retContribute(status);
		}
    }
    
    public class retContribute{
    	private final int status; 
    	private final String data;

		public retContribute(int status) {
			this.status = status;
			this.data = null;
		}
		public int getStatus() {
			return status;
		}
		public String getData() {
			return data;
		}
    }
}