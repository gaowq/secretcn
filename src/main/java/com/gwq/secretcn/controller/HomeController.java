package com.gwq.secretcn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {

    @GetMapping("/")
    public String my(HttpServletRequest request) {
        request.setAttribute("key", "游客");
        return "index";
    }

    @ResponseBody
    @PostMapping("/getTranslate")
    public Map getTranslate(String tranStr) {
        Map map = new HashMap();
        map.put("status", 1);
        map.put("msg", convertCn(tranStr));

        return map;
    }


    public String convertCn(String tranStr) {
        if (tranStr == null || tranStr.equals("")) {
            return "";
        }

        //char c = '风';
        String result = "";

        char[] chars = tranStr.toCharArray();

        for (char c : chars) {

            String uncoide = Integer.toHexString(c);
            //System.out.println(c + "的uncoide编码:\t" + uncoide);

            //从uncoide编码转换成10进制
            int x = Integer.parseInt(uncoide, 16);

            if (x < 19968 || x > 40943) {
                result += c;
                continue;
            }

            //从10进制转成uncoide编码
            int y = (x - 19968 + 10488) % 20976 + 19968;
            //System.out.println(y + "转成uncoide编码:\t" + Integer.toHexString(y));

            //"u"+"234";
            result += (char) y;
            //result += ("\\u" + Integer.toHexString(39118));
            //System.out.println(x);
            //return "" + x;
        }

        return result;
    }
}
