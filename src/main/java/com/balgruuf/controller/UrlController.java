package com.balgruuf.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.PropertySource;

import com.balgruuf.controller.base.UrlBaseController;

@RestController
@PropertySource("classpath:${configfile.path}/balgruuf.properties")
@RequestMapping(value="${endpoint.api}", headers = "Accept=application/json")
public class UrlController extends UrlBaseController {

	//OVERRIDE HERE YOUR CUSTOM CONTROLLER

}
