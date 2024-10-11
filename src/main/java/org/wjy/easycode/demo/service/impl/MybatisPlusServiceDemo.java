package org.wjy.easycode.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.wjy.easycode.demo.dao.IDemoDaoService;
import org.wjy.easycode.demo.pojo.entity.DemoEntity;

/**
 * 在业务层使用mybatis-plus IService
 */
@Service
public class MybatisPlusServiceDemo {

    @Resource
    private IDemoDaoService demoDaoService;

    public void execute() {
        // 利用扩充的功能，提供有批量增删改查接口
        List<DemoEntity> entityList = new ArrayList<>();
        demoDaoService.saveBatch(entityList);

        // 调用自定义接口
        demoDaoService.insertByStatus(2);
    }
}
