package cn.notemi.demo.model.vo;

import cn.notemi.demo.model.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @desc 登录凭证VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginCredentialVO implements Model {

    private static final long serialVersionUID = 5550420394013305835L;

    @ApiModelProperty(value = "凭证ID")
    private Long id;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "账号类型")
    private String type;

}