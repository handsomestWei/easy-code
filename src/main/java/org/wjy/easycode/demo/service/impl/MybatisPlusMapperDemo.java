package org.wjy.easycode.demo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.wjy.easycode.demo.dao.DemoMapper;
import org.wjy.easycode.demo.pojo.entity.DemoEntity;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * 在业务层使用mybatis-plus mapper，以组合的形式
 */
@Slf4j
@Service
public class MybatisPlusMapperDemo {

    @Resource
    private DemoMapper demoMapper;

    @DS("ds2") // 动态切换数据源。默认为primary声明的
    // @DS // 若不指定数据源名称，则使用负载均衡模式，每次路由到不同数据源
    public void execute(String param) {
        // 构造条件对象，可用于增删改查
        QueryWrapper<DemoEntity> wrapper = new QueryWrapper<>();
        // 参数不为空，则拼接条件
        wrapper.eq(!StringUtils.isBlank(param), "column_name", param);
        // 链式调用，默认是sql条件and
        wrapper.ge("status", 0).le("status", 6);
        // limit用法，使用last方法在sql末尾拼接自定义sql，且不做校验
        wrapper.last("limit 10");

        // 分页查询
        Page<DemoEntity> page = new Page<>(1, 10);
        IPage<DemoEntity> iPage = demoMapper.selectPage(page, wrapper);
        log.debug("总页数：" + iPage.getPages());
        log.debug("总记录数：" + iPage.getTotal());
        iPage.getRecords().forEach(System.out::println);

        // 更新
        DemoEntity updEntity = new DemoEntity();
        // 要更新指定字段，则new对象后赋值
        updEntity.setPhone("123").setStatus(1);
        demoMapper.update(updEntity, wrapper);

    }
}
