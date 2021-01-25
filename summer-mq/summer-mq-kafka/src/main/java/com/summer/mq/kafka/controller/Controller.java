package com.summer.mq.kafka.controller;

import com.summer.mq.kafka.model.Bar1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	@Autowired
	KafkaTemplate<String, Object> kafkaTemplate;

	@Value("${kafka.topic.topic1}")
	private String topic1;
	@Value("${kafka.topic.foos}")
	private String foos;
	@Value("${kafka.topic.bars}")
	private String bars;

	@GetMapping(path = "/send/foo/")
	public void sendFoo() {
		this.kafkaTemplate.send("netease-opinion-data-import", "1111");
	}

	@PostMapping(path = "/send/bar/{what}")
	public void sendBar(@PathVariable String what) {
		this.kafkaTemplate.send("bars", new Bar1(what));
	}

	@PostMapping(path = "/send/unknown/{what}")
	public void sendUnknown(@PathVariable String what) {
		this.kafkaTemplate.send("bars", what);
	}

}