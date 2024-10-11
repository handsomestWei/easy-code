package org.wjy.easycode.demo.dao;

import org.wjy.easycode.demo.pojo.entity.DemoEntity;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * IService形式，是BaseMapper的大扩充
 */
public interface IDemoDaoService extends IService<DemoEntity> {

    // 自定义查询，可以封装业务逻辑
    int insertByStatus(Integer status);

    DemoEntity selectByName(String name);
}
