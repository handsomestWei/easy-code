package org.wjy.easycode.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wjy.easycode.pojo.entity.DemoEntity;

/**
 * mybatis-plus IService使用，以继承的形式扩展功能，提供通用接口
 */
@Service
public class DemoDaoServiceImpl extends ServiceImpl<DemoMapper, DemoEntity> implements IDemoDaoService {

    @Transactional(rollbackFor = Exception.class) // 开启事务，且只要发生异常就回滚事务
    @Override
    public int insertByStatus(Integer status) {
        if (status != null && status > 1) {
            DemoEntity entity = new DemoEntity().setStatus(status);
            return this.baseMapper.insert(entity);
        } else {
            return 0;
        }
    }
}
