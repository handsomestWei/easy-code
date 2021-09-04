package org.wjy.easycode.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.wjy.easycode.pojo.entity.DemoEntity;

import java.util.List;

/**
 * mapper形式，包含基本方法
 */
public interface DemoMapper extends BaseMapper<DemoEntity> {

    @Select("SELECT * FROM `tb_demo` where id = #{id} limit 100") // 自定义sql并传参
    List<DemoEntity> selectList(@Param("id") String id);
}
