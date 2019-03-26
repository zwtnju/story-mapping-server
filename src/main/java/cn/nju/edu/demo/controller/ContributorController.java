package cn.nju.edu.demo.controller;

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
    @SuppressWarnings("finally")
    @RequestMapping("/api/contributor/create")
//    {
//        "userToken": "ae123asqere21asdsa3",
//        "projectId": 3,
//        "contributorId": 6
//    }
    // 多建一个表 关系表
    public retContribute ContributorCreate(@RequestBody Map<String,Object> mapRead) {
    	int status = 0;
    	String userToken = (String)mapRead.get("userToken");
		String projectId = (String)mapRead.get("projectId");
		String contributorId = (String)mapRead.get("contributorId");
		try {
			// 已有该协作者 返回异常状态码2
			jdbcTemplate.queryForList("select * from project_user where "
					+ "user_token = ? and project_id = ? and contributor_id = ?", userToken, projectId, contributorId);
			status = 2;
		} catch (DataAccessException e) {
			// 数据库插入该协作者
			e.printStackTrace();
			jdbcTemplate.update("insert into project_user"
					+ "(user_token,project_id,contributor_id) values (?,?,?)", 
					userToken, projectId, contributorId);
		} finally {
			return new retContribute(status);
		}
    }
    
    // 删除协作者
    @SuppressWarnings("finally")
    @RequestMapping("/api/contributor/delete")
//    {
//        "userToken": "ae123asqere21asdsa3",
//        "projectId": 3,
//        "contributorId": 6
//    }
    public retContribute ContributorDelete(@RequestBody Map<String,Object> mapRead) {
    	int status = 0;
    	String userToken = (String)mapRead.get("userToken");
		String projectId = (String)mapRead.get("projectId");
		String contributorId = (String)mapRead.get("contributorId");
		try {
			// 删除协作者 返回正常
			jdbcTemplate.queryForMap("select * from project_user where "
					+ "user_token = ? and project_id = ? and contributor_id = ?", userToken, projectId, contributorId);			
			jdbcTemplate.update("delete from project_user where "
					+ "user_token = ? and project_id = ? and contributor_id = ?", userToken, projectId, contributorId);
		} catch (DataAccessException e) {
			// 没有该协作者
			status = 2;
		} finally {
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