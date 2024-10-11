package org.wjy.easycode.demo.pojo.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.wjy.easycode.demo.pojo.dto.MapstructDto;
import org.wjy.easycode.demo.pojo.vo.MapstructVo;

/**
 * 对象转换，可用在DDD领域驱动模型编程。能隐藏dao层数据，只将必要字段返回controller
 * 
 * @author weijiayu
 * @date 2023/4/18 15:42
 */
@Mapper(componentModel = "spring")
public interface MapstructMapper {

    // 先编译再运行，class自动生成到target\generated-sources\annotations目录
    // 对象不能使用@data注解，参考https://github.com/mapstruct/mapstruct/issues/2767，要自动生成get和set方法
    MapstructMapper INSTANCE = Mappers.getMapper(MapstructMapper.class);

    MapstructVo toVo(MapstructDto dto);

    MapstructDto toDto(MapstructVo vo);

    // for test
    public static void main(String[] args) {
        MapstructVo vo = new MapstructVo();
        vo.setId("asd");
        vo.setNo(123);

        MapstructDto dto = MapstructMapper.INSTANCE.toDto(vo);
        System.out.println(dto.getId());
    }
}
