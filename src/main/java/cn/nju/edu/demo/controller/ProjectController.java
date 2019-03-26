package cn.nju.edu.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
    
    @SuppressWarnings({ "null", "finally" })
	@RequestMapping("/api/project/list")
//    {
//        "userToken": "ae123asqere21asdsa3"
//    }
    public retProjectList ProjectList(@RequestBody Map<String,Object> mapRead) {
    	// 查询数据库中存在的project
    	int status = 0;
    	List<Map<String, Object>> listDb;
    	List<Project> projectList = null;
    	String userToken = (String)mapRead.get("userToken");
    	try {
    		listDb = jdbcTemplate.queryForList("select project_id from project_user where user_token = ?", userToken);
    		for(Map<String, Object> mapDb : listDb) {
    			String projectId = mapDb.get("project_id").toString();
    			Map<String, Object> mapProject = 
    					jdbcTemplate.queryForMap("select * from project where project_id = ?", projectId);
    			
    			projectList.add(new Project(mapProject.get("projectId").toString(), 
    					mapProject.get("projectTitle").toString(), mapProject.get("projectContent").toString(), 
    					mapProject.get("ownerId").toString(), mapProject.get("ownerEmail").toString(), 
    					mapProject.get("ownerName").toString()));
    		}
    	} catch (Exception e) {
			e.printStackTrace();
			status = 2;
		} finally {
			return new retProjectList(status, projectList);
		}
		
    }

    @SuppressWarnings("null")
	@RequestMapping("/api/project/get")
//    {
//        "userToken": "ae123asqere21asdsa3",
//        "projectId": 3
//    }
    public retProjectDetail ProjectGet(@RequestBody Map<String,Object> mapRead) {
    	// 某个project详细信息
    	int status = 0;
    	List<Map<String, Object>> contributorDb;
    	List<Contributor> contributors = null;
    	String userToken = (String)mapRead.get("userToken");
    	String projectId = (String)mapRead.get("projectId");
    	try {
    		contributorDb = jdbcTemplate.queryForList("select contributor_id from project_user "
    				+ "where user_token = ? and project_id = ?", userToken, projectId);
    		for(Map<String, Object> mapDb : contributorDb) {
    			String contributorId = mapDb.get("contributor_id").toString();
    			Map<String, Object> mapContributor = 
    					jdbcTemplate.queryForMap("select * from contributor "
    							+ "where contributor_id = ?", contributorId);
    			
    			contributors.add(new Contributor(mapContributor.get("contributor_id").toString(), 
    					mapContributor.get("contributor_email").toString(), 
    					mapContributor.get("contributor_name").toString()));
    		}
		Map<String, Object> mapProject = 
				jdbcTemplate.queryForMap("select * from project where project_id = ?", projectId);
		return new retProjectDetail(status, mapProject.get("projectId").toString(), 
					mapProject.get("projectTitle").toString(), mapProject.get("projectContent").toString(), 
					mapProject.get("ownerId").toString(), mapProject.get("ownerEmail").toString(), 
					mapProject.get("ownerName").toString(), contributors, mapProject.get("cards").toString());
    	} catch (Exception e) {
			e.printStackTrace();
			status = 2;
			return new retProjectDetail(status, "null", "null", "null", "null", "null", "null", contributors, "null");
		}
    }
    
    
    
    public class retProjectList{
    	private final int status; 
    	private final List<Project> data;
		public retProjectList(int status, List<Project> data) { 
			this.status = status;
			this.data = null;		
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
    	private final Project data;
		public retProjectDetail(int status, String projectId, String projectTitle, 
				String projectContent, String ownerId, String ownerEmail,
				String ownerName, List<Contributor> contributors, String projectCards) {
			this.status = status;
			this.data = new ProjectDetail(projectId, projectTitle, 
					projectContent, ownerId, ownerEmail, ownerName, contributors, projectCards);
		}
		public int getStatus() {
			return status;
		}
		public Project getData() {
			return data;
		}    	
    }
    
}