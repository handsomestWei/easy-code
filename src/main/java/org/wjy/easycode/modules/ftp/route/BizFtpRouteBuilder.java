package org.wjy.easycode.modules.ftp.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.wjy.easycode.modules.ftp.process.BizFileProcess;

import lombok.extern.slf4j.Slf4j;

/**
 * ftp路由，分别下载csv文件和jpg图片。
 * 
 * Apache Camel FTP 配置例：<br/>
 * camel.springboot.main-run-controller=true
 * ftp_biz_file_url=ftp://xxx.xxx.xxx.xxx:21?username=wiseftp&password=wiseftp&filter=#ftpCsvFilter&recursive=true&reconnectDelay=1000&binary=true&passiveMode=true&delete=true&delay=500&noop=true&idempotent=true&ftpClient.controlEncoding=GBK&readLock=rename
 * ftp_biz_file_dir=file:F:\\fff
 * ftp_biz_img_url=ftp://xxx.xxx.xxx.xxx:21?username=wiseftp&password=wiseftp&filter=#ftpImgFilter&recursive=true&reconnectDelay=1000&binary=true&passiveMode=true&delete=true&delay=500&noop=true&idempotent=true&ftpClient.controlEncoding=GBK&readLock=rename
 * ftp_biz_img_dir=file:F:\\fff
 * 
 * @link https://cloud.tencent.com/developer/article/1707868
 * @link https://camel.apache.org/components/4.0.x/ftp-component.html
 * @author weijiayu
 * @date 2023/11/3 10:32
 */
@Component
@Slf4j
public class BizFtpRouteBuilder extends RouteBuilder {

    @Value("${ftp_biz_file_url}")
    private String bizFileServerUrl;

    @Value("${ftp_biz_file_dir}")
    private String bizFileLocalDir;

    @Value("${ftp_biz_img_url}")
    private String bizImgServerUrl;

    @Value("${ftp_biz_img_dir}")
    private String bizImgLocalDir;

    @Autowired
    private BizFileProcess bizFileProcess;

    @Override
    public void configure() throws Exception {
        // 下载csv文件
        from(bizFileServerUrl).to(bizFileLocalDir).process(bizFileProcess).log(LoggingLevel.DEBUG, log,
            "Download biz file ${file:name} complete.");
        // 下载jpg图片
        from(bizImgServerUrl).to(bizImgLocalDir).log(LoggingLevel.DEBUG, log,
            "Download biz img ${file:name} complete.");
    }
}
