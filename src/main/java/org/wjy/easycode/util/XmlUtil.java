package org.wjy.easycode.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.wjy.easycode.demo.pojo.dto.ServiceCentre;
import org.wjy.easycode.demo.pojo.dto.ServiceCentreRequest;
import org.wjy.easycode.demo.pojo.dto.ServiceResult;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * xml序列化/反序列化工具
 * 
 * @author weijiayu
 * @date 2022/1/19 12:01
 */
public class XmlUtil {

    private static ObjectMapper mapper = new XmlMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .enable(MapperFeature.USE_STD_BEAN_NAMING).enable(SerializationFeature.INDENT_OUTPUT);

    public static String toXml(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    public static String toXmlV2(Map<String, String> params) {
        StringBuilder buf = new StringBuilder();
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        buf.append("<xml>");
        for (String key : keys) {
            buf.append("<").append(key).append(">");
            buf.append("<![CDATA[").append(params.get(key)).append("]]>");
            buf.append("</").append(key).append(">\n");
        }
        buf.append("</xml>");
        return buf.toString();
    }

    public static <T> T toBean(String xml, Class<T> clazz) throws IOException {
        return mapper.readValue(xml, clazz);
    }

    public static <T> T xml2Object(String xmlStr, Class<T> c) {
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            T t = (T)unmarshaller.unmarshal(new StringReader(xmlStr));
            return t;
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String object2Xml(Object object) {
        try {
            StringWriter writer = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshal = context.createMarshaller();
            marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // 格式化输出
            marshal.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");// 编码格式,默认为utf-8
            marshal.setProperty(Marshaller.JAXB_FRAGMENT, false);// 是否省略xml头信息
            marshal.setProperty("jaxb.encoding", "utf-8");
            marshal.marshal(object, writer);
            // <?xml version="1.0" encoding="utf-8" standalone="yes"?>去掉头部的standalone
            return new String(writer.getBuffer()).replace(" standalone=\"yes\"", "");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        ServiceCentre serviceCentre = new ServiceCentre();
        serviceCentre.setServiceId("asd");
        serviceCentre.setSenderId("007");
        ServiceCentreRequest requestDto = new ServiceCentreRequest();
        requestDto.setCsrq("1990-01-01");
        serviceCentre.setServiceCentreRequest(requestDto);

        System.out.println(object2Xml(serviceCentre));

        String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + "<serviceResult>\n"
            + "    <errorCode>000</errorCode>\n" + "    <errorMessage>操作成功</errorMessage>\n" + "    <results>\n"
            + "        <result>\n" + "    <code>1</code>\n" + "<describe>屏蔽标签/人证不一</describe>\n"
            + "<warningcolor>#ff0000/#ffff00</warningcolor>\n" + "        </result>\n" + "    </results>\n"
            + "</serviceResult>";
        System.out.println(xml2Object(xmlStr, ServiceResult.class));
    }
}
