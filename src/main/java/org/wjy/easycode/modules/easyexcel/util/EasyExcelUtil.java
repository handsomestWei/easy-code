package org.wjy.easycode.modules.easyexcel.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import org.springframework.http.HttpStatus;
import org.wjy.easycode.modules.easyexcel.ImageAutoFillMergeCelHandler;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author weijiayu
 * @date 2023/4/1 10:55
 */
public class EasyExcelUtil {

    /**
     * 下载
     */
    public static void rspAttachment(HttpServletResponse response, String tplPath, String fileName, Object data) {
        try {
            // 获取模板文件后缀格式
            String fileSuffix = tplPath.substring(tplPath.lastIndexOf("."));
            // 避免文件名中文乱码
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            // 设置响应头附件
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + fileSuffix);
            response.setCharacterEncoding("UTF-8");
            /**
             * 需要显式设置类型，否则会报工作薄类型错误org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException: The supplied data
             * appears to be in the OLE2 Format. You are calling the part of POI that deals with OOXML (Office Open XML)
             * Documents. You need to call a different part of POI to process this data (eg HSSF instead of XSSF)
             */
            ExcelWriterBuilder builder = EasyExcel.write(response.getOutputStream());
            switch (fileSuffix) {
                case ".xlsx":
                    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    builder = builder.excelType(ExcelTypeEnum.XLSX);
                    break;
                case ".xls":
                    response.setContentType("application/vnd.ms-excel");
                    builder = builder.excelType(ExcelTypeEnum.XLS);
                    break;
                default:
                    // TODO
                    break;
            }
            builder.withTemplate(tplPath).registerWriteHandler(new ImageAutoFillMergeCelHandler()).sheet().doFill(data);

            /*
            response.flushBuffer();
            // 写入数据时自动计算并设置最适合的列宽
            try (ExcelWriter excelWriter = EasyExcel.write(rsp.getOutputStream(), xxx.class)
                    .useDefaultStyle(false).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).build()) {
                WriteSheet writeSheet = EasyExcel.writerSheet("xxx").build();
                excelWriter.write(getData(), writeSheet);
            }
            */
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public static void main(String[] args) throws Exception {
        String path = "F:\\";
        String picPath = "F:\\";
        String templateFileName = path + "tpl.xls";
        String fileName = path + "fill" + System.currentTimeMillis() + ".xls";

        HashMap<String, Object> data = new HashMap<>();
        // 对应模板单元格{name}
        data.put("name", "张三");
        // 填充图片，对应模板单元格{pic}
        String suffix = "jpg";
        File picFile = new File(picPath + "test.jpg");
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        BufferedImage bufferImg = ImageIO.read(picFile);
        ImageIO.write(bufferImg, suffix, byteArrayOut);
        bufferImg.flush();
        data.put("pic", byteArrayOut.toByteArray());

        EasyExcel.write(fileName).withTemplate(templateFileName)
            .registerWriteHandler(new ImageAutoFillMergeCelHandler()).sheet().doFill(data);
    }
}
