package org.wjy.easycode.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjy.springbootdemo.pojo.entity.DemoEntity;

/**
 * IService形式，是BaseMapper的大扩充
 */
public interface IDemoDaoService extends IService<DemoEntity> {

    // 自定义查询，可以封装业务逻辑
    int insertByStatus(Integer status);
}
