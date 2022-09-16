package com.mantulife.demo.model.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.poi.ss.usermodel.FillPatternType;
import java.io.Serializable;

/**
 * @App 应用中心
 *
 * @author W_wang
 * @email 1352255400@qq.com
 * @date 2022-09-16 11:21:29
 */
@Data
// 头背景设置成白色
@HeadStyle(fillPatternType = FillPatternType.NO_FILL, fillForegroundColor = 10)
// 头字体设置成12
@HeadFontStyle(fontHeightInPoints = 12)
@ContentRowHeight(20)
@HeadRowHeight(20)
@ColumnWidth(18)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppExcel implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @ExcelProperty("管理员主键ID")
    private Long id;

    @ExcelProperty("code")
    private String code;

    @ExcelProperty("应用名称")
    private String name;

    @ExcelProperty("应用logo")
    private String logo;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("应用类型")
    private Boolean type;

    @ExcelProperty("状态：0正常1禁用")
    private Boolean status;

    @ExcelProperty("管理员用户创建时间")
    private String createTime;

    @ExcelProperty("管理员用户最近更新时间")
    private String updateTime;

    
}
