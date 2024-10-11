package org.wjy.easycode.demo.pojo.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 数据库实体类
 */
@TableName("tb_demo") // 声明映射的数据库表名。这样声明可以使class命名不遵循表名的驼峰规则，命名可以和业务关联，让对象用于业务层，填充属性后，无需因为命名的问题再转换为dao层的entity
@Data
@Accessors(chain = true)
public class DemoEntity {

    @TableField(value = "recordId") // 对应的数据库字段为recordId
    private String id;

    @TableField(exist = false) // 声明该字段不是数据库字段，不做转换
    private String name;

    @TableField(select = false) // 不查询该字段
    private String phone;

    private Integer status;

    @TableField(fill = FieldFill.INSERT) // 自动生成时间
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE) // 自动生成时间
    private Date updateTime;
}
