package com.mantulife.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mantulife.demo.model.dto.AppDto;
import com.mantulife.demo.model.vo.AppVo;
import com.mantulife.demo.model.entity.AppEntity;
import com.mantulife.demo.model.query.AppQuery;

import java.util.List;

/**
 * @App 应用中心
 *
 * @author W_wang
 * @email 1352255400@qq.com
 * @date 2022-09-16 11:21:29
 */
public interface AppService extends IService<AppEntity> {

    /**
     * 列表(分页)
     *
     * @author W_wang
     * @email 1352255400@qq.com
     * @date 2022-09-16 11:21:29
     */
    List<AppVo> getPageList(AppQuery query);

    /**
     * 获取总数
     *
     * @author W_wang
     * @email 1352255400@qq.com
     * @date 2022-09-16 11:21:29
     */
    Long getTotal(AppQuery query);

    /**
     * 全部
     *
     * @author W_wang
     * @email 1352255400@qq.com
     * @date 2022-09-16 11:21:29
     */
    List<AppVo> getAll(AppQuery query);

    /**
     * 详情
     *
     * @author W_wang
     * @email 1352255400@qq.com
     * @date 2022-09-16 11:21:29
     */
        AppVo getInfo(Long id);

    /**
     * 新增
     *
     * @author W_wang
     * @email 1352255400@qq.com
     * @date 2022-09-16 11:21:29
     */
    Long save(AppDto dto);

    /**
     * 新增批量
     *
     * @author W_wang
     * @email 1352255400@qq.com
     * @date 2021-07-26 10:05:41
     */
    boolean saveAll(List<AppEntity> listEntity);

    /**
     * 编辑
     *
     * @author W_wang
     * @email 1352255400@qq.com
     * @date 2022-09-16 11:21:29
     */
    boolean update(AppDto dto);

    /**
     * 删除
     *
     * @author W_wang
     * @email 1352255400@qq.com
     * @date 2022-09-16 11:21:29
     */
    boolean delete(List<Long> ids);

    /**
     * 清除缓存
     *
     * @author W_wang
     * @email 1352255400@qq.com
     * @date 2022-09-16 11:21:29
     */
    void clearCache();
}

