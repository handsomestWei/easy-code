package org.wjy.easycode.demo.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("/api/v1/chinese")
public class RspGarbledChineseController {

    @Value("${msg}")
    private String msg;

    /** @see org.springframework.http.converter.StringHttpMessageConverter#DEFAULT_CHARSET */ // 消息转换后，再将数据放入response body中，默认为ISO_8859_1编码
    @GetMapping("/garbled1")
    public String getMsg1() {
        // 在idea的配置File Encoding中勾选Transparent native-to-ascii conversion后，idea自动将中文转换为unicode编码，但在idea中显示的还是中文方便查看。可用文本编辑器查看，中文其实已经被自动转换为\u开头的unicode编码。
        // 或者使用工具，直接将中文转换为unicode编码，在填入配置文件
        return msg;
    }

    @GetMapping("/garbled2")
    public String getMsg2() throws UnsupportedEncodingException {
        // springboot对于yaml文件是utf-8编码读取
        // springboot对于properties文件默认是iso-8859-1编码读取，因此可以转换为字节数组，再编码
        return new String(msg.getBytes("iso-8859-1"), "GBK");
    }

    @GetMapping("/garbled3")
    public String getMsg3() throws UnsupportedEncodingException {
        // 不能转换为utf-8，因为编码格式不兼容，iso-8859-1是utf-8的字集，utf-8范围更大，不能向上转换
        return new String(msg.getBytes("iso-8859-1"), "utf-8"); // 仍然返回乱码
    }
}
