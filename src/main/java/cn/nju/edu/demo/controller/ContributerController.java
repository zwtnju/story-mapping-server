package cn.nju.edu.demo.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ContributerController {
	// 添加协作者
    @RequestMapping("/api/contributor/create")
//    {
//        "userToken": "ae123asqere21asdsa3",
//        "projectId": 3,
//        "contributorId": 6
//    }
    public retContribute ContributorCreate(@RequestBody Map<String,Object> map) {
    	int status = 0;
    	// 数据库比对 修改status
    	// TODO
    	return new retContribute(status);
    }
    
    // 删除协作者
    @RequestMapping("/api/contributor/delete")
//    {
//        "userToken": "ae123asqere21asdsa3",
//        "projectId": 3,
//        "contributorId": 6
//    }
    public retContribute ContributorDelete(@RequestBody Map<String,Object> map) {
    	int status = 0;
    	// 数据库比对 修改status
    	// TODO
    	return new retContribute(status);
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