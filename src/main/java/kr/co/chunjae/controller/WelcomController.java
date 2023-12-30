package kr.co.chunjae.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WelcomController {
    // 기본이 Get이라 method 작성 안해도 상관없음.
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String welcome(Model model){
        model.addAttribute("greeting", "Welcome to Book Market");
        model.addAttribute("strapline", "Welcome to Web Shopping Mall!");
        // JSP 이름이 welcome이 되어야함.
        // return이 string일 경우 jsp와 이름 동일해야함.
        // 주소창에 /home 입력시 -> welcome.jsp를 호출.
        return "welcome";
    }
}
