package cn.nju.edu.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    
    // 获取某个用户的project列表
	@RequestMapping("/api/project/list")
//    {
//        "userToken": "ae123asqere21asdsa3"
//    }
    public retProjectList ProjectList(@RequestBody Map<String,Object> mapRead) {
    	// 查询数据库中存在的project
    	int status = 0;
    	List<Project> projectList = new ArrayList<Project>();
    	String userToken = (String)mapRead.get("userToken");
    	List<Map<String, Object>> listDb = 
    			jdbcTemplate.queryForList("select project_id from project_user where user_token = ?", userToken);
		if(listDb.isEmpty()) {
			status = 2;
		}
		else {
    		for(Map<String, Object> mapDb : listDb) {
    			String projectId = mapDb.get("project_id").toString();
    			Map<String, Object> mapProject = 
    					jdbcTemplate.queryForMap("select * from project where project_id = ?", projectId);
    			System.out.println(mapProject.get("project_id").toString());
    			System.out.println(mapProject.get("project_title").toString());
    			System.out.println(mapProject.get("project_content").toString());
    			System.out.println(mapProject.get("owner_id").toString());
    			System.out.println(mapProject.get("owner_email").toString());
    			System.out.println(mapProject.get("owner_name").toString());
    			Project newProject = new Project(mapProject.get("project_id").toString(), 
    					mapProject.get("project_title").toString(), mapProject.get("project_content").toString(), 
    					mapProject.get("owner_id").toString(), mapProject.get("owner_email").toString(), 
    					mapProject.get("owner_name").toString());
    			projectList.add(newProject);
    		}
		}		
		return new retProjectList(status, projectList);
    }

    // 某个project的具体内容
	@RequestMapping("/api/project/get")
//    {
//        "userToken": "ae123asqere21asdsa3",
//        "projectId": 3
//    }
    public retProjectDetail ProjectGet(@RequestBody Map<String,Object> mapRead) {
    	// 某个project详细信息
    	int status = 0;
    	String userToken = (String)mapRead.get("userToken");
    	String projectId = Integer.toString((Integer)(mapRead.get("projectId")));
    	try {
    		List<Map<String, Object>> contributorDb = 
    				jdbcTemplate.queryForList("select contributor_id from project_user "
    				+ "where user_token = ? and project_id = ?", userToken, projectId);
    		List<Contributor> contributors = new ArrayList<Contributor>();
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
			ProjectDetail newProjectDetail = new ProjectDetail(mapProject.get("project_id").toString(), 
					mapProject.get("project_title").toString(), mapProject.get("project_content").toString(), 
					mapProject.get("owner_id").toString(), mapProject.get("owner_email").toString(), 
					mapProject.get("owner_name").toString(), contributors, mapProject.get("cards").toString());
    		return new retProjectDetail(status, newProjectDetail);
    	} catch (Exception e) {
			status = 2;
			return new retProjectDetail(status, null);
		}
    }
    
    // 更新project card内容
    @SuppressWarnings("finally")
	@RequestMapping("/api/project/update")
// 	"cards"后的内容不需要解析，就当成一个字符串 request
//    {
//        "userToken": "ae123asqere21asdsa3",
//        "projectId": 3,
//        "projectTitle": "项目题目",
//        "projectContent": "项目说明",
//        "cards": [ 
//            [ { "content": "card1" }, { "content": "card2" }, { "content": "card3" }, { "content": "card4" } ], 
//    		    [ { "content": "card1" }, { "content": "card2" }, { "content": "card3" }, { "content": "card4" } ] 
//    	  ]
//    }
    public retUpdateStatus ContributorUpdate(@RequestBody Map<String,Object> mapRead) {
	  	int status = 0;
	  	String userToken = (String)(mapRead.get("userToken"));
		String projectId = Integer.toString((Integer)(mapRead.get("projectId")));
		try {
			// 修改已有project数据
			jdbcTemplate.queryForMap("select * from project_user where "
					+ "project_id = ? and user_token = ?", projectId, userToken);
			jdbcTemplate.queryForMap("select * from project where project_id = ?", projectId);
			jdbcTemplate.update("update project set cards = ? where project_id = ?", 
					(String)mapRead.get("cards"), projectId);
		
		} catch (DataAccessException e) {
			status = 2;
		} finally {
			return new retUpdateStatus(status);
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
    
    public class retUpdateStatus{
    	private final int status; 
    	private final String data;
		public retUpdateStatus(int status) {
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