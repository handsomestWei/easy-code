package org.wjy.easycode.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.wjy.easycode.demo.pojo.entity.DemoEntity;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * mapper形式，包含基本方法
 */
public interface DemoMapper extends BaseMapper<DemoEntity> {

    @Select("SELECT * FROM `tb_demo` where id = #{id} limit 100") // 自定义sql并传参
    List<DemoEntity> selectList(@Param("id") String id);
}
