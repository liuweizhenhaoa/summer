package com.summer.springboot.session.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {


    @RequestMapping(value = "/sessionInfo", method = RequestMethod.GET)
    public Map<String, String> addSession (HttpServletRequest request){
        String sessionId = request.getSession().getId();
        String requestURI = request.getRequestURI();
        Map<String, String> sessionInfoMap = new HashMap<>(2);
        sessionInfoMap.put("sessionId", sessionId);
        sessionInfoMap.put("requestURI", requestURI);
        return sessionInfoMap;
    }

    @RequestMapping("/putIntoSession")
    public String putIntoSession(HttpServletRequest request, String username) {
        request.getSession().setAttribute("name",  "leo");
        return "ok";
    }

    @RequestMapping("/getFromSession")
    public String getFromSession(HttpServletRequest request, Model model){
        String name = (String) request.getSession().getAttribute("name");
        return name;
    }
}
