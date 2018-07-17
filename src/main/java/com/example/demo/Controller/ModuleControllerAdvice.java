package com.example.demo.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.Exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice()
public class ModuleControllerAdvice {


    private JSONObject jsonpObject=new JSONObject();

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServiceException.class)
    public JSONObject a(ServiceException e){

        String s="业务异常受理失败原因："+e.getLocalizedMessage();
        jsonpObject.put("message",s);
        jsonpObject.put("code",50000);
        return jsonpObject;
    }


}
