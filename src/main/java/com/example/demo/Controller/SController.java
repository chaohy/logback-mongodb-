package com.example.demo.Controller;

import com.example.demo.Exception.ServiceException;
import com.example.demo.Service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SController {

    private static final Logger mongoLog = LoggerFactory.getLogger("MONGODB");
    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/haha")
    public void a(){
        studentService.a(0);
    }

    @RequestMapping(value = "/mm")
    public void b(){
        System.out.println("hello");
        mongoLog.info("1234567");
    }
}
