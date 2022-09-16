package com.mantulife.demo.dao;

import com.mantulife.demo.model.entity.AppEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

/**
 * @App 应用中心
 * 
 * @author W_wang
 * @email 1352255400@qq.com
 * @date 2022-09-16 11:21:29
 */
@Mapper
public interface AppDao extends BaseMapper<AppEntity> {

    /**
     * 原生sql
     * @param sql
     * @return
     */
    List<Map<String, Object>> queryBySql(String sql);

}
