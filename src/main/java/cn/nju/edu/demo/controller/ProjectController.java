package cn.nju.edu.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.nju.edu.model.Contributor;
import cn.nju.edu.model.Project;
import cn.nju.edu.model.ProjectDetail;

@RestController
public class ProjectController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    // 创建project
   	@RequestMapping("/api/project/create")
    public retStatus ProjectCreate(@RequestBody Map<String,Object> mapRead) {
   	  	int status = 0;
   	   	if(mapRead.size() != 3 || !mapRead.containsKey("userToken") || !mapRead.containsKey("projectTitle")
   	   		 || !mapRead.containsKey("projectContent")) {
       		// 输入格式错误
       		status = 2;
       		return new retStatus(status);
       	}
   	  	
   	  	String userToken = (String)(mapRead.get("userToken"));
   		String projectTitle = (String)(mapRead.get("projectTitle"));
   		String projectContent = (String)(mapRead.get("projectContent"));
		try {
			// 新建project数据
			String projectId = UUID.randomUUID().toString();
			Map<String, Object> mapDb =
					jdbcTemplate.queryForMap("select * from user where user_token = ?", userToken);
			jdbcTemplate.update("insert into project_user"
					+ "(user_token,  project_id,  contributor_id) values (?,?,?)", 
					userToken, projectId, mapDb.get("user_id").toString());
			//  不考虑project会重复的情况 否则需要加上try catch
			jdbcTemplate.update("insert into project"
					+ "(project_id, project_title, project_content, owner_id, "
					+ "owner_email, owner_name, cards) values (?,?,?,?,?,?,?)", 
					projectId, projectTitle, projectContent, mapDb.get("user_id").toString(),
					mapDb.get("user_email").toString(), mapDb.get("user_name").toString(), "null");
			
			return new retStatus(status);
		
		} catch (DataAccessException e) {
			status = 1;
			return new retStatus(status);
		} 	
   	}
    
   	
   	// 删除project
   	@RequestMapping("/api/project/delete")
    public retStatus ProjectDelete(@RequestBody Map<String,Object> mapRead) {
   	  	int status = 0;
   	   	if(mapRead.size() != 2 || !mapRead.containsKey("userToken") || !mapRead.containsKey("projectId")) {
       		// 输入格式错误
       		status = 2;
       		return new retStatus(status);
       	}
   	  	
   	  	String userToken = (String)mapRead.get("userToken");
   		String projectId = (String)mapRead.get("projectId");
		// 删除project数据
		List<Map<String, Object>> listDb = 
				jdbcTemplate.queryForList("select * from project_user where "
				+ "user_token = ? and project_id = ?", userToken, projectId);	
		if(listDb.isEmpty()) {
			status = 1;
			return new retStatus(status);
		}
		else {
			jdbcTemplate.update("delete from project_user where "
					+ "user_token = ? and project_id = ?", userToken, projectId);
			jdbcTemplate.update("delete from project where project_id = ?", projectId);
			
			return new retStatus(status);
		}	
   	}

   	
    // 获取某个用户的project列表
	@RequestMapping("/api/project/list")
    public retProjectList ProjectList(@RequestBody Map<String,Object> mapRead) {
    	// 查询数据库中存在的project
    	int status = 0;
    	if(mapRead.size() != 1 || !mapRead.containsKey("userToken")) {
    		// 输入格式错误
    		status = 2;
    		return new retProjectList(status, null);
    	}
    	
    	String userToken = (String)mapRead.get("userToken");
    	List<Project> projectList = new ArrayList<Project>();
		try {
	    	List<Map<String, Object>> listDb = 
	    			jdbcTemplate.queryForList("select project_id from project_user where user_token = ?", userToken);    			
			if(listDb.isEmpty()) {
				return new retProjectList(status, null);
			}
	    	for(Map<String, Object> mapDb : listDb) {
				String projectId = mapDb.get("project_id").toString();
				Map<String, Object> mapProject = 
						jdbcTemplate.queryForMap("select * from project where project_id = ?", projectId);
				Project newProject = new Project(mapProject.get("project_id").toString(), 
						mapProject.get("project_title").toString(), mapProject.get("project_content").toString(), 
						mapProject.get("owner_id").toString(), mapProject.get("owner_email").toString(), 
						mapProject.get("owner_name").toString());
				projectList.add(newProject);
			}
			return new retProjectList(status, projectList);
		} catch (Exception e) {
			status = 1;
			return new retProjectList(status, projectList);
		}	
    }

    // 某个project的具体内容
	@RequestMapping("/api/project/get")
    public retProjectDetail ProjectGet(@RequestBody Map<String,Object> mapRead) {
    	// 某个project详细信息
    	int status = 0;
    	if(mapRead.size() != 2 || !mapRead.containsKey("userToken") || !mapRead.containsKey("projectId")) {
    		// 输入格式错误
    		status = 2;
    		return new retProjectDetail(status, null);
    	}
    	 	
    	String userToken = (String)mapRead.get("userToken");
		String projectId = (String)mapRead.get("projectId");
		try {
    		List<Map<String, Object>> contributorDb = 
    				jdbcTemplate.queryForList("select contributor_id from project_user "
    				+ "where user_token = ? and project_id = ?", userToken, projectId);
    		List<Contributor> contributors = new ArrayList<Contributor>();
    		for(Map<String, Object> mapDb : contributorDb) {
    			String contributorId = mapDb.get("contributor_id").toString();

    			Map<String, Object> mapContributor = 
    					jdbcTemplate.queryForMap("select * from user "
    							+ "where user_id = ?", contributorId);
    			contributors.add(new Contributor(mapContributor.get("user_id").toString(), 
    					mapContributor.get("user_email").toString(), 
    					mapContributor.get("user_name").toString()));
    		}
			Map<String, Object> mapProject = 
				jdbcTemplate.queryForMap("select * from project where project_id = ?", projectId);
			
			ProjectDetail newProjectDetail = new ProjectDetail(mapProject.get("project_id").toString(), 
					mapProject.get("project_title").toString(), mapProject.get("project_content").toString(), 
					mapProject.get("owner_id").toString(), mapProject.get("owner_email").toString(), 
					mapProject.get("owner_name").toString(), contributors, mapProject.get("cards").toString());
    		return new retProjectDetail(status, newProjectDetail);
    	} catch (Exception e) {
    		e.printStackTrace();
			status = 1;
			return new retProjectDetail(status, null);
		}  	
    }
    
    // 更新project card内容
	@RequestMapping("/api/project/update")
	// 	"cards"后的内容不需要解析，就当成一个字符串 request
    public retStatus ProjectUpdate(@RequestBody Map<String,Object> mapRead) {
	  	int status = 0;
	   	if(mapRead.size() != 5 || !mapRead.containsKey("userToken")  || !mapRead.containsKey("projectId") 
	   		 || !mapRead.containsKey("projectTitle")  || !mapRead.containsKey("projectContent") 
	   		 || !mapRead.containsKey("cards") ) {
    		// 输入格式错误
    		status = 2;
    		return new retStatus(status);
    	}
	  	
	  	String userToken = (String)(mapRead.get("userToken"));;
		String projectId = (String)mapRead.get("projectId");
		String cards = (String)(mapRead.get("cards"));
		try {
			// 修改已有project数据
			jdbcTemplate.queryForMap("select * from project_user where "
					+ "project_id = ? and user_token = ?", projectId, userToken);
			jdbcTemplate.queryForMap("select * from project where project_id = ?", projectId);
			jdbcTemplate.update("update project set cards = ? where project_id = ?", cards, projectId);
			return new retStatus(status);
		
		} catch (DataAccessException e) {
			status = 1;
			return new retStatus(status);
		} 
    }

    public class retProjectList{
    	private final int status; 
    	private final List<Project> data;
		public retProjectList(int status, List<Project> data) { 
			this.status = status;
			this.data = data;		
		}
		public int getStatus() {
			return status;
		}
		public List<Project> getData() {
			return data;
		}
    }
    
    public class retProjectDetail{
    	private final int status; 
    	private final ProjectDetail data;
		public retProjectDetail(int status, ProjectDetail data) {
			this.status = status;
			this.data = data;
		}
		public int getStatus() {
			return status;
		}
		public Project getData() {
			return data;
		}    	
    }
    
    public class retStatus{
    	private final int status; 
    	private final String data;
		public retStatus(int status) {
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