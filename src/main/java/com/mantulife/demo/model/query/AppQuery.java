package com.mantulife.demo.model.query;

import io.swagger.annotations.ApiModelProperty;
import com.mantulife.common.model.query.BaseQuery;
import java.io.Serializable;
import lombok.Data;

/**
 * @App 应用中心
 *
 * @author W_wang
 * @email 1352255400@qq.com
 * @date 2022-09-16 11:21:29
 */
@Data
public class AppQuery extends BaseQuery implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "管理员主键ID")
    private Long id;

    @ApiModelProperty(value = "code")
    private String code;

    @ApiModelProperty(value = "应用名称")
    private String name;

    @ApiModelProperty(value = "应用logo")
    private String logo;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "应用类型")
    private Boolean type;

    @ApiModelProperty(value = "状态：0正常1禁用")
    private Boolean status;

    @ApiModelProperty(value = "管理员用户创建时间")
    private String createTime;

    @ApiModelProperty(value = "管理员用户最近更新时间")
    private String updateTime;

    
}
