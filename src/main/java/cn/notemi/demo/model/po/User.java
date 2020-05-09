package cn.notemi.demo.model.po;

import cn.notemi.demo.annotations.EnumValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * @desc 用户PO
 */
@ApiModel("用户PO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table
@Entity
public class User extends BasePO<String> {

	private static final long serialVersionUID = -7491215402569546437L;

	@ApiModelProperty(value = "用户主键")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "SELECT REPLACE(UUID(),'-','')")
	@Length(min=1, max=64)
	private String id;

	@ApiModelProperty(value = "昵称")
	@NotBlank
	@Length(min=1, max=64)
	private String nickname;

	@ApiModelProperty(value = "性别")
	@NotBlank
	@EnumValue(enumClass=UserGenderEnum.class, enumMethod="isValidName")
	private String gender;

	@ApiModelProperty(value = "头像")
	@Length(max=256)
	private String avatar;

	@ApiModelProperty(value = "状态")
	@NotBlank
	@EnumValue(enumClass=UserTypeEnum.class, enumMethod="isValidName")
	private String type;

	@ApiModelProperty(value = "账号状态")
	@EnumValue(enumClass=UserStatusEnum.class, enumMethod="isValidName")
	private String status;

	/**
	 * 用户性别枚举
	 */
	public enum UserGenderEnum {
		/**男*/
		MALE,
		/**女*/
		FEMALE,
		/**未知*/
		UNKNOWN;

		public static boolean isValidName(String name) {
			for (UserGenderEnum userGenderEnum : UserGenderEnum.values()) {
				if (userGenderEnum.name().equals(name)) {
					return true;
				}
			}
			return false;
		}
	}

	/**
	 * 用户类型枚举
	 */
	public enum UserTypeEnum {
		/**普通*/
		NORMAL,
		/**管理员*/
		ADMIN;

		public static boolean isValidName(String name) {
			for (UserTypeEnum userTypeEnum : UserTypeEnum.values()) {
				if (userTypeEnum.name().equals(name)) {
					return true;
				}
			}
			return false;
		}
	}

	/**
	 * 用户状态枚举
	 */
	public enum UserStatusEnum {
		/**启用*/
		ENABLED,
		/**禁用*/
		DISABLED;

		public static boolean isValidName(String name) {
			for (UserStatusEnum userStatusEnum : UserStatusEnum.values()) {
				if (userStatusEnum.name().equals(name)) {
					return true;
				}
			}
			return false;
		}
	}
}