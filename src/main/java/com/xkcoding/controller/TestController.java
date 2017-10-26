package com.xkcoding.controller;

import com.xkcoding.common.JsonData;
import com.xkcoding.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
	@GetMapping("/hello.json")
	public JsonData hello() {
		log.info("hello");
		throw new PermissionException("test exception");
		//throw new RuntimeException();
		//return JsonData.success("hello, permission");
	}
}
