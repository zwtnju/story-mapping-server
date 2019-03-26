package cn.nju.edu.demo.controller;
//package cn.nju.edu.controller;
//
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import cn.nju.edu.model.Contributor;
//import cn.nju.edu.model.Project;
//import cn.nju.edu.model.ProjectDetail;
//import cn.nju.edu.model.User;
//
//@RestController
//public class ProjectController {
//
//    @RequestMapping("/api/project/list")
////    {
////        "userToken": "ae123asqere21asdsa3"
////    }
//    public retProjectList ProjectList(@RequestBody Map<String,Object> map) {
//    	String userToken = (String)map.get("userToken");
//    	// 查询数据库中存在的project
//    	int status = 0;
//    	if(status == 0) {
//        	return new retProjectList();
//    	} 
//    	return new retProjectList();
//    }
//
//    @RequestMapping("/api/project/get")
////    {
////        "userToken": "ae123asqere21asdsa3",
////        "projectId": 3
////    }
//    public retProjectDetail ProjectGet(@RequestBody Map<String,Object> map) {
//    	// 某个project详细信息
//    	return new retProjectDetail();
//    }
//    
//    
//    
//    public class retProjectList{
//    	private final int status; 
//    	private final Project data;
//		public retProjectList(int status, int projectId, String projectTitle, 
//				String projectContent, int ownerId, String ownerEmail, String ownerName) { 
//			this.status = status;
//			this.data = new Project(projectId, projectTitle, projectContent, ownerId, ownerEmail, ownerName);
//		}
//		public int getStatus() {
//			return status;
//		}
//		public Project getData() {
//			return data;
//		}
//    }
//    
//    public class retProjectDetail{
//    	private final int status; 
//    	private final Project data;
//		public retProjectDetail(int status, int projectId, String projectTitle, 
//				String projectContent, int ownerId, String ownerEmail,
//				String ownerName, List<Contributor> contributors, String projectCards) {
//			this.status = status;
//			this.data = new ProjectDetail(projectId, projectTitle, 
//					projectContent, ownerId, ownerEmail, ownerName, contributors, projectCards);
//		}
//		public int getStatus() {
//			return status;
//		}
//		public Project getData() {
//			return data;
//		}    	
//    }
//    
//}