package org.wjy.easycode.pojo.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Data;

/**
 * @author weijiayu
 * @date 2022/9/9 17:47
 */
@Data
@XmlRootElement(name = "request")
// 指定JAXBContext marshal顺序
@XmlType(propOrder = {"sbbm", "xm", "xb", "mz", "csrq", "zzxz", "sfzh", "xsd", "szsj", "zp1", "zp2", "zp3", "jd", "wd"})
public class ServiceCentreRequest {

    private String sbbm = "";
    private String xm = "";
    private String xb = "";
    private String mz = "";
    private String csrq = "";
    private String zzxz = "";
    private String sfzh = "";
    private String xsd = "1";
    private String szsj = "";
    private String zp1 = "";
    private String zp2 = "";
    private String zp3 = "";
    private String jd = "";
    private String wd = "";
}
