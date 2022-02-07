package com.example.demo.AjaxContoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class AjaxController {
    String str;

    @RequestMapping("/a3")
    public void ajax1(String name , HttpServletResponse response) throws IOException {

        str = name + "hello";

        if ("".equals(name)){
            response.getWriter().print("false");
        }else{
            response.getWriter().print(str);
        }
    }

}