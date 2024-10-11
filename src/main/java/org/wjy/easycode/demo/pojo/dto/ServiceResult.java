package org.wjy.easycode.demo.pojo.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.ToString;

/**
 * @author weijiayu
 * @date 2022/9/13 11:39
 */
@ToString
@XmlRootElement(name = "serviceResult")
// 指定JAXBContext marshal顺序
@XmlType(propOrder = {"errorCode", "errorMessage", "serviceResultDetails"})
public class ServiceResult {

    private String errorCode;

    private String errorMessage;

    private List<ServiceResultDetail> serviceResultDetails;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    // 对于List等组合类型，使用lombok的@data无效。需要定义get方法+@XmlElementWrapper+@XmlElement
    @XmlElementWrapper(name = "results")
    @XmlElement(name = "result")
    public List<ServiceResultDetail> getServiceResultDetails() {
        return serviceResultDetails;
    }

    public void setServiceResultDetails(List<ServiceResultDetail> serviceResultDetails) {
        this.serviceResultDetails = serviceResultDetails;
    }
}
