package cn.nju.edu.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xiongzehua on 2019/4/6.
 */
@Controller
public class IndexController {
  @RequestMapping("/")
  public String index() {
    return "index.html";
  }
}
