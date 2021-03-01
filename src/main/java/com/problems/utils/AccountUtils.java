package com.problems.utils;

import com.appslandia.common.crypto.PasswordDigester;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class AccountUtils {

	public static final int ACCOUNT_PENDING = 0;
	public static final int ACCOUNT_ACTIVE = 1;
	public static final int ACCOUNT_BANNED = 2;

	public static String hashPassword(String password) {
		return getPasswordDigester().digest(password);
	}

	public static boolean verifyPassword(String password, String hash) {
		return getPasswordDigester().verify(password, hash);
	}

	static PasswordDigester getPasswordDigester() {
		return new PasswordDigester();
	}
}
