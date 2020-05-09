package cn.notemi.demo.enums;

/**
 * @desc 接口返回值风格样式枚举类
 */
public enum ApiStyleEnum {
	NONE;

	public static boolean isValid(String name) {
		for (ApiStyleEnum callSource : ApiStyleEnum.values()) {
			if (callSource.name().equals(name)) {
				return true;
			}
		}
		return false;
	}

}