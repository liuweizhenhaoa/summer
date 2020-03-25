package com.summer.nlp.hanlp.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {


	@PostMapping(path = "/send/foo/{what}")
	public void sendFoo(@PathVariable String what) {

	}

}