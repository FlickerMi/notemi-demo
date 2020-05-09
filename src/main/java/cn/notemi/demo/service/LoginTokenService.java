package cn.notemi.demo.service;

import cn.notemi.demo.model.bo.LoginToken;

/**
 * @desc 用户登录TOKEN服务
 */
public interface LoginTokenService {

	/**
	 *
	 * Add login token.
	 *
	 * @param loginToken the login token
	 * @return the login token
	 */
	LoginToken add(LoginToken loginToken);

	/**
	 *
	 * Delete by id.
	 *
	 * @param id the id
	 */
	void deleteById(String id);

	/**
	 *
	 * Get by id login token.
	 *
	 * @param id the id
	 * @return the login token
	 */

	LoginToken getById(String id);

	/**
	 *
	 * Ttl long.
	 *
	 * @param id the id
	 * @return the long
	 */
	long ttl(String id);

}