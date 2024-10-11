package org.wjy.easycode.demo.pojo.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author weijiayu
 * @date 2022/9/9 17:46
 */
@XmlRootElement(name = "serviceCentre")
// 指定JAXBContext marshal顺序
@XmlType(propOrder = {"senderId", "serviceId", "userName", "passWord", "serviceCentreRequest"})
public class ServiceCentre {

    private String senderId = "";

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    // 使用lombok的@data无效。需要定义get方法+@XmlElement
    @XmlElement(name = "request")
    public ServiceCentreRequest getServiceCentreRequest() {
        return serviceCentreRequest;
    }

    public void setServiceCentreRequest(ServiceCentreRequest serviceCentreRequest) {
        this.serviceCentreRequest = serviceCentreRequest;
    }

    private String serviceId = "";
    private String userName = "";
    private String passWord = "";
    private ServiceCentreRequest serviceCentreRequest;
}
