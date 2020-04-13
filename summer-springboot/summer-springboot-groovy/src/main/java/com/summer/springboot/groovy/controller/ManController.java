package com.summer.springboot.groovy.controller;

import com.summer.springboot.groovy.shell.model.Man;
import com.summer.springboot.groovy.shell.service.impl.ManService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author liuwei
 * @Description //TODO
 * @Date 1$ 1$
 **/
@RestController
public class ManController {
	@Autowired
	private ManService manService;

	@GetMapping("/ok")
	String home() {
		Man man = manService.getInfoByName("mickjoust");
		return "ok ==> groovy "+"name:"+man.getName();
	}
}
