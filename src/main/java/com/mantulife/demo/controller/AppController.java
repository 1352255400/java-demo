package com.mantulife.demo.controller;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.fastjson.JSON;
import com.mantulife.common.model.dto.DeleteDto;
import com.mantulife.demo.model.enums.ErrorCodeEnum;
import com.mantulife.common.utils.BeanUtils;
import com.mantulife.common.utils.PageUtils;
import com.mantulife.demo.utils.Result;
import com.mantulife.demo.model.dto.AppDto;
import com.mantulife.demo.model.vo.AppVo;
import com.mantulife.demo.model.entity.AppEntity;
import com.mantulife.demo.model.excel.AppExcel;
import com.mantulife.demo.model.query.AppQuery;
import com.mantulife.demo.service.AppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @App 应用中心
 *
 * @author W_wang
 * @email 1352255400@qq.com
 * @date 2022-09-16 11:21:29
 */
@RestController
@RequestMapping("/app")
@Api(tags = {"应用中心"})
public class AppController {

    @Resource
    private AppService service;


    /**
     * 列表
     *
     * @author W_wang
     * @email 1352255400@qq.com
     * @date 2022-09-16 11:21:29
     */
    @GetMapping("/getList")
    @ApiOperation(value = "列表")
    @ApiResponses({
            @ApiResponse(code = 0, message = "success", response = AppVo.class)
    })
    public Result getList(AppQuery query) {
        //是否获取总数
        long page = query.getPageNumber();
        long limit = query.getPageSize();
        Long total = 0L;

        //获取分页列表
        List<AppVo> listDto = service.getPageList(query);
        if (ObjectUtil.isNotEmpty(listDto)) {
            //清空分页条件
            query.setPageSize(null);
            query.setPageNumber(null);
            //获取分页数据
            total = service.getTotal(query);
        }

        //将数据填充到map中
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("list", listDto);
        map.put("pageNation", PageUtils.pageNation(total, page, limit));
        return Result.ok().data(map);
    }


    /**
     * 列表
     *
     * @author W_wang
     * @email 1352255400@qq.com
     * @date 2022-09-16 11:21:29
     */
    @GetMapping("/getAll")
    @ApiOperation(value = "列表-all")
    @ApiResponses({
            @ApiResponse(code = 0, message = "success", response = AppVo.class)
    })
    public Result getAll(AppQuery query) {
        //获取分页列表
        List<AppVo> listDto = service.getAll(query);
        return Result.ok().data("list", listDto);
    }


    /**
     * 详情
     *
     * @author W_wang
     * @email 1352255400@qq.com
     * @date 2022-09-16 11:21:29
     */
    @GetMapping("/info")
    @ApiOperation(value = "详情")
    @ApiResponses({
            @ApiResponse(code = 0, message = "success", response = AppVo.class)
    })
    public Result info(@RequestParam("id") Long id) {
        //获取数据
            AppVo info = service.getInfo(id);
        if (ObjectUtil.isNotEmpty(info)) {
            //
        }
        return Result.ok().data("info", info);
    }

    /**
     * 新增
     *
     * @author W_wang
     * @email 1352255400@qq.com
     * @date 2022-09-16 11:21:29
     */
    @PutMapping("/save")
    @ApiOperation(value = "新增")
    //@Valid 开启验证
    public Result save(@Valid @RequestBody AppDto dto) {
        //操作数据库
        Long id = service.save(dto);
        return Result.ok().data("id", id);
    }

    /**
     * 编辑
     *
     * @author W_wang
     * @email 1352255400@qq.com
     * @date 2022-09-16 11:21:29
     */
    @PutMapping("/update")
    @ApiOperation(value = "编辑")
    public Result update(@Valid @RequestBody AppDto dto) {
        //操作数据库
        boolean re = service.update(dto);

        return re ? Result.ok() : Result.error().code(ErrorCodeEnum.FAIL.code()).message(ErrorCodeEnum.FAIL.msg());
    }

    /**
     * 删除
     *
     * @author W_wang
     * @email 1352255400@qq.com
     * @date 2022-09-16 11:21:29
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "删除")
    public Result delete(@RequestBody DeleteDto dto) {
        //操作数据库
        boolean re = service.delete(dto.getIds());

        return re ? Result.ok() : Result.error().code(ErrorCodeEnum.FAIL.code()).message(ErrorCodeEnum.FAIL.msg());
    }

    /**
     * 导出
     *
     * @author W_wang
     * @email 1352255400@qq.com
     * @date 2022-09-16 11:21:29
     */
    @GetMapping("export")
    @ApiOperation(value = "导出")
    public void export(AppQuery query, HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        //https://www.yuque.com/easyexcel/doc/read  easyexcel 文件
        try {
            String excelName = "list";//模板名称
            //初始化Excel下载信息
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode(excelName, "UTF-8").replace("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

            //查询数据
            List<AppVo> all = service.getAll(query);

            //处理数据
            List<AppExcel> list = new ArrayList<>();
            if (ObjectUtil.isNotEmpty(all)) {
                //处理数据
                List<AppExcel> finalList = list;
                all.forEach(info -> {
                        AppExcel excel = new AppExcel();
                    BeanUtils.copyBean(info, excel);
                    finalList.add(excel);
                });
            }

            //单个sheet 这里需要设置不关闭流
            EasyExcelFactory
                    .write(response.getOutputStream(), AppExcel.class)
                    .autoCloseStream(Boolean.FALSE)
                    .sheet(excelName) //sheet 名称
                    .doWrite(list);//填充数据

        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = new HashMap<>();
            map.put("code", "1000");
            map.put("status", "failure");
            map.put("msg", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
        }
    }

    /**
     * 导入
     *
     * @author W_wang
     * @email 1352255400@qq.com
     * @date 2022-09-16 11:21:29
     */
    //上传头像的方法
    @PostMapping("import")
    @ApiOperation(value = "导入Excel")
    public Result importExcle(MultipartFile file) {
        //实现excel读操作
        List<AppEntity> entityList = new ArrayList<>();
        // 读取部分sheet
        try {
            List<AppExcel> list = EasyExcelFactory
                    .read(file.getInputStream())
                    .sheet(0) //读取每个sheet的内容
                    .head(AppExcel.class) //指定头部
                    .doReadSync();

            if (ObjectUtil.isNotEmpty(list)) {
                // 获取所有数据-去重
                // UserVo userInfo = userUtil.getCacheUser();
                    AppQuery query = new AppQuery();
                // query.setCompanyId(userInfo.getCompanyId());
                List<AppVo> all = service.getAll(query);
                List<Long> idList = all.stream().map(AppVo::getId).collect(Collectors.toList());
                list.forEach(info -> {
                    // 数据库去重
                    Long id = info.getId();
                    if (idList.contains(id)) {
                        return;
                    }
                    idList.add(id);

                    // 初始化用户信息 TODO Excel 内部去重
                        AppEntity entity = new AppEntity();
                    BeanUtils.copyBean(info, entity);
                    entityList.add(entity);
                });
                if (ObjectUtil.isNotEmpty(entityList)) {
                    // 写入数据库
                    //service.saveAll(entityList);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error().message(e.getMessage());
        }

        //返回上传到oss的路径
        return Result.ok().data("list", entityList);
    }

    /**
     * 导入模板
     *
     * @author W_wang
     * @email 1352255400@qq.com
     * @date 2021-04-13 16:59:13
     */
    @GetMapping("template")
    @ApiOperation(value = "导入模板")
    public void template(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        //https://www.yuque.com/easyexcel/doc/read  easyexcel 文件
        try {
            String excelName = "template";//模板名称
            //初始化Excel下载信息
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode(excelName, "UTF-8").replace("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            //单个sheet 这里需要设置不关闭流
            EasyExcelFactory
                    .write(response.getOutputStream(), AppExcel.class)
                    .autoCloseStream(Boolean.FALSE)
                    .sheet(excelName) //sheet 名称
                    .doWrite(null);//填充数据
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = new HashMap<>();
            map.put("code", "1000");
            map.put("status", "failure");
            map.put("msg", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
        }
    }

}
