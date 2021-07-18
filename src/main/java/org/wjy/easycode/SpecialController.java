package org.wjy.easycode;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/api/v1/sp") // 控制器要放在主程序XXXApplication.java包外，注解@RestController才能生效
public class SpecialController {

    // 请求路径不是/api/v1/sp/sp，而是/sp
    @GetMapping("/sp")
    public String getMsg() {
        return "bingo";
    }
}
