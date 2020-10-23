package com.activiti.demo.controller;


import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


public abstract class ActivitiBaseController {

    public Map<String,Object> getSysUser(@RequestParam("request") HttpServletRequest request){
        Map userMap = new HashMap();
        return null;
    }


}
