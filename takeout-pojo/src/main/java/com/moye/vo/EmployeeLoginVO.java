package com.moye.vo;

//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@ApiModel(description = "员工登录返回的数据格式")
@Schema(description = "员工登录返回的数据格式")
public class EmployeeLoginVO implements Serializable {

//    @ApiModelProperty("主键值")
    @Schema(name = "主键值")
    private Long id;

//    @ApiModelProperty("用户名")
    @Schema(name = "用户名")
    private String userName;

//    @ApiModelProperty("姓名")
    @Schema(name = "姓名")
    private String name;

//    @ApiModelProperty("jwt令牌")
    @Schema(name = "jwt令牌")
    private String token;

}
