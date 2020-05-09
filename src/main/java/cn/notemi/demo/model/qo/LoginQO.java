package cn.notemi.demo.model.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @desc 登录QO
 */
@ApiModel("登录QO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginQO {

    @NotBlank
    @ApiModelProperty(value = "账号")
    private String account;

    @NotBlank
    @ApiModelProperty(value = "密码")
    private String pwd;

    @NotEmpty
    @ApiModelProperty(value = "凭证类型")
    private List<String> type;
}