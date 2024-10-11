package org.wjy.easycode.pojo.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Data;

/**
 * @author weijiayu
 * @date 2022/9/13 11:44
 */
@Data
@XmlRootElement(name = "result")
// 指定JAXBContext marshal顺序
@XmlType(propOrder = {"code", "describe", "warningcolor"})
public class ServiceResultDetail {

    private String code;
    private String describe;
    private String warningcolor;
}
