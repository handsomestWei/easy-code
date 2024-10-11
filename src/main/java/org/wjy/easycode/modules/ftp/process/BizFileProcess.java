package org.wjy.easycode.modules.ftp.process;

import java.io.RandomAccessFile;

import javax.annotation.Resource;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.file.GenericFileMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 文件ftp工作流处理器
 * 
 * @author weijiayu
 * @date 2023/11/3 10:47
 */
@Component
public class BizFileProcess extends AbsFileProcess implements Processor {

    @Value("${ftp_biz_file_dir}")
    private String bizFileLocalDir;

    // 自定义文件处理接口实现类
    @Resource
    private FileProcess ctmBizFileService;

    @Override
    public String getCheckPointFileName() {
        return bizFileLocalDir + "biz-checkPoint";
    }

    @Override
    public FileProcess getBizProcess() {
        return (FileProcess)ctmBizFileService;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        // ftp下载文件完成，开始处理
        GenericFileMessage<RandomAccessFile> inMsg = (GenericFileMessage<RandomAccessFile>)exchange.getIn();
        String fileName = inMsg.getGenericFile().getFileName();
        process(bizFileLocalDir + fileName);
    }
}
