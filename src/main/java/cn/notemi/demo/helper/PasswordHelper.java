package cn.notemi.demo.helper;

import cn.notemi.demo.util.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

/**
 * @desc 密码辅助类
 */
public class PasswordHelper {

	private static final String SECRET_KEY = "PG4j5xJGd9a6g1dx";

	/**
	 * 通过随机盐+秘钥加密
	 */
	public static String encodeBySalt(String noEncodePwd, String salt) {
		if (StringUtil.isEmpty(noEncodePwd) || StringUtil.isEmpty(salt)) {
			return null;
		}

		return DigestUtils.sha256Hex(salt + DigestUtils.md5Hex(noEncodePwd) + DigestUtils.md5Hex(SECRET_KEY));
	}

	/**
	 * 生成随机盐
	 */
	public static String generateRandomSalt() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 判断密码是否为加密密码
	 * 前提用户密码不能超过加密后密码长度，目前是64位
	 * @param pwd 待判定密码
	 * @return 是否是加密密码
	 */
	public static boolean isEncodePwd(String pwd) {
		if (StringUtil.isEmpty(pwd)) {
			return false;
		}
		String noEncodePwd = "What am I not important";
		String encodePwd = encodeBySalt(noEncodePwd, generateRandomSalt());
		assert encodePwd != null;
		return pwd.length() == encodePwd.length();
	}

	public static void main(String[] args) {
		String randomSalt = generateRandomSalt();
		System.out.println(randomSalt);
		System.out.println(encodeBySalt("123123", randomSalt));
	}
}