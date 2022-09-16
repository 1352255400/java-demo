package com.mantulife.demo.service.impl;

import com.mantulife.demo.exception.BusinessException;
import com.mantulife.demo.model.enums.ErrorCodeEnum;
import com.mantulife.common.utils.BeanUtils;
import com.mantulife.common.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.transaction.annotation.Transactional;

import com.mantulife.demo.dao.AppDao;
import com.mantulife.demo.model.dto.AppDto;
import com.mantulife.demo.model.vo.AppVo;
import com.mantulife.demo.model.entity.AppEntity;
import com.mantulife.demo.model.query.AppQuery;
import com.mantulife.demo.service.AppService;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service("appService")
public class AppServiceImpl extends ServiceImpl<AppDao, AppEntity> implements AppService {

    //缓存主key
    String cacheKeys = "app";

    @Resource
    private RedisUtil redis;

    @Resource
    private AppDao appDao;


    /**
     * 列表(分页)
     *
     * @author W_wang
     * @email 1352255400@qq.com
     * @date 2022-09-16 11:21:29
     */
    @Override
    //开启缓存
    @Cacheable(cacheNames = "app", keyGenerator = "myKeyGenerator")
    public List<AppVo> getPageList(AppQuery query) {
        //筛选条件
        LambdaQueryWrapper<AppEntity> queryWrapper = new LambdaQueryWrapper<>();
        //获取公共条件
        getQueryWrapper(query, queryWrapper);

        //分页
        long page = query.getPageNumber();
        long limit = query.getPageSize();
        queryWrapper.last("limit " + ((page - 1) * limit) + "," + limit);

        //查询数据库
        List<AppEntity> list = baseMapper.selectList(queryWrapper);

        //数据转换
        return BeanUtils.copyListBean(list, AppVo.class);
    }

    /**
     * 获取分页
     *
     * @author W_wang
     * @email 1352255400@qq.com
     * @date 2022-09-16 11:21:29
     */
    @Override
    //开启缓存
    @Cacheable(cacheNames = "app", keyGenerator = "myKeyGenerator")
    public Long getTotal(AppQuery query) {
        //筛选条件
        LambdaQueryWrapper<AppEntity> queryWrapper = new LambdaQueryWrapper<>();
        //获取公共条件
        getQueryWrapper(query, queryWrapper);

        //获取总数
        return baseMapper.selectCount(queryWrapper).longValue();
    }

    /**
     * 列表（所有）
     *
     * @author W_wang
     * @email 1352255400@qq.com
     * @date 2022-09-16 11:21:29
     */
    @Override
    //开启缓存
    @Cacheable(cacheNames = "app", keyGenerator = "myKeyGenerator")
    public List<AppVo> getAll(AppQuery query) {
        //查询数据库
        LambdaQueryWrapper<AppEntity> queryWrapper = new LambdaQueryWrapper<>();
        //获取公共条件
        getQueryWrapper(query, queryWrapper);

        //查询数据库
        List<AppEntity> list = baseMapper.selectList(queryWrapper);

        //数据转换
        return BeanUtils.copyListBean(list, AppVo.class);
    }

    /**
     * 详情
     *
     * @author W_wang
     * @email 1352255400@qq.com
     * @date 2022-09-16 11:21:29
     */
    @Override
    //开启缓存
    @Cacheable(cacheNames = "app", keyGenerator = "myKeyGenerator")
    public AppVo getInfo(Long id) {
        //查询数据库
        AppEntity info = this.baseMapper.selectById(id);

        //数据转换
        return BeanUtils.copyBean(info, AppVo.class);
    }

    /**
     * 新增
     *
     * @author W_wang
     * @email 1352255400@qq.com
     * @date 2022-09-16 11:21:29
     */
    @Override
    // 清除多个缓存
    // @CacheEvict(cacheNames = {"app", "sysAccount"}, allEntries = true)
    @CacheEvict(cacheNames = "app", allEntries = true)
    @Transactional(rollbackFor = BusinessException.class)
    public Long save(AppDto dto) {
        boolean re = false;
        //基本检查
        this.baseValid(dto);

        //保存数据
        AppEntity entity = BeanUtils.copyBean(dto, AppEntity.class);
        //操作数据库
        entity.setId(null);
        re = super.save(entity);
        if (!re) {
            throw new BusinessException(ErrorCodeEnum.FAIL);
        }
        return entity.getId();
    }

    /**
     * 新增全部
     *
     * @author W_wang
     * @email 1352255400@qq.com
     * @date 2021-07-26 10:05:41
     */
    @Override
    @CacheEvict(cacheNames = "app", allEntries = true)
    public boolean saveAll(List<AppEntity> listEntity) {
        return super.saveBatch(listEntity);
    }

    /**
     * 编辑
     *
     * @author W_wang
     * @email 1352255400@qq.com
     * @date 2022-09-16 11:21:29
     */
    @Override
    @CacheEvict(cacheNames = "app", allEntries = true)
    @Transactional(rollbackFor = BusinessException.class)
    public boolean update(AppDto dto) {
        boolean re = false;
        //基本检查
        this.baseValid(dto);

        //检查详情
        AppVo info = getInfo(dto.getId());
        if (ObjectUtil.isEmpty(info)) {
            log.error("详情不存在");
            throw new BusinessException(ErrorCodeEnum.DATA_NOT_EXIST);
        }

        AppEntity entity = BeanUtils.copyBean(dto, AppEntity.class);
        //操作数据库
        re = super.updateById(entity);
        return re;
    }

    /**
     * 删除
     *
     * @author W_wang
     * @email 1352255400@qq.com
     * @date 2022-09-16 11:21:29
     */
    @Override
    @CacheEvict(cacheNames = "app", allEntries = true)
    @Transactional(rollbackFor = BusinessException.class)
    public boolean delete(List<Long> ids) {
        if (ObjectUtil.isNotEmpty(ids)) {
            ids.forEach(id -> {
                AppVo info = getInfo(id);
                if (ObjectUtil.isEmpty(info)) {
                    log.error("详情不存在");
                    throw new BusinessException(ErrorCodeEnum.DATA_NOT_EXIST);
                }
            });
        }

        return removeByIds(ids);
    }


    /**
     * 清空缓存
     *
     * @author W_wang
     * @email 1352255400@qq.com
     * @date 2022-09-16 11:21:29
     */
    @Override
    @CacheEvict(cacheNames = "app", allEntries = true)
    public void clearCache() {
        log.info("清空缓存");
    }


    /**
     * 基本检查
     *
     * @author W_wang
     * @email 1352255400@qq.com
     * @date 2021-06-08 11:02:25
     */
    private boolean baseValid(AppDto dto) {
        log.info(dto.toString());
        /*//检查名称
        LambdaQueryWrapper<AppEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotEmpty(dto.getName()), AppEntity::getName, dto.getName());
        //编辑检查
        queryWrapper.ne(ObjectUtil.isNotEmpty(dto.getId()) && dto.getId() > 0, AppEntity::getId, dto.getId());
        AppEntity one = getOne(queryWrapper);
        if (ObjectUtil.isNotEmpty(one)) {
            throw new BusinessException(ErrorCodeEnum.NAME_IS_EXISTS);
        }*/
        return true;
    }


    /**
     * 查询条件
     *
     * @author W_wang
     * @email 1352255400@qq.com
     * @date 2022-09-16 11:21:29
     */
    private void getQueryWrapper(AppQuery query, LambdaQueryWrapper<AppEntity> queryWrapper) {
        if (ObjectUtil.isNotEmpty(query.getIds())) {
            //只返回选择的数据
            queryWrapper.in(true, AppEntity::getId, query.getIds());
            return;
        }

        //排序
        if (StrUtil.isNotEmpty(query.getOrderType()) && StrUtil.isNotEmpty(query.getOrderField())) {
            if ("desc".equals(query.getOrderType())) {
                switch (query.getOrderField()) {
                    case "id":
                        queryWrapper.orderByDesc(AppEntity::getId);
                        break;
                    case "createTime":
                        queryWrapper.orderByDesc(AppEntity::getCreateTime);
                        break;

                    default:
                        queryWrapper.orderByDesc(AppEntity::getId);
                        break;
                }
            } else {
                switch (query.getOrderField()) {
                    case "id":
                        queryWrapper.orderByAsc(AppEntity::getId);
                        break;
                    case "createTime":
                        queryWrapper.orderByAsc(AppEntity::getCreateTime);
                        break;

                    default:
                        queryWrapper.orderByAsc(AppEntity::getId);
                        break;
                }
            }
        } else {
            queryWrapper.orderByDesc(AppEntity::getId);
        }

        // 其他条件
        queryWrapper.eq(ObjectUtil.isNotEmpty(query.getId()), AppEntity::getId, query.getId());
    }
}