package com.example.demo.Service.impl;

import com.example.demo.Exception.ServiceException;
import com.example.demo.Service.StudentService;
import org.springframework.stereotype.Service;

@Service("studentservice")
public class StudentServiceImpl extends StudentService{


    @Override
    public int a(int b) {
        if(b==0){
            throw new ServiceException("业务出现异常");
        }
        return b;
    }
}
