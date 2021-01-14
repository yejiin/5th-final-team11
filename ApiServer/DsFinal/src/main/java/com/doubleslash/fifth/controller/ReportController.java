package com.doubleslash.fifth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;

@Api(value = "Report", description = "신고 API")
@Controller
@RequestMapping(value = "/report")
public class ReportController {

}
