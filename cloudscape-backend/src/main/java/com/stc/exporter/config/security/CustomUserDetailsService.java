package com.stc.exporter.config.security;

import com.stc.exporter.app.user.dao.UserRegistrationDao;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRegistrationDao userRegistrationDao;

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CustomUserDetails users = userRegistrationDao.findUserByUserEmail(username);
		if(users == null){
			logger.error("Username not found: " + username);
			throw new UsernameNotFoundException("could not found user..!!");
		}
		logger.info("User Authenticated Successfully..!!!");

		return users;
	}

	// 사용자 ID로 사용자 조회
	public CustomUserDetails loadUserByUserIdx (int userIdx) throws UsernameNotFoundException {
		CustomUserDetails users = userRegistrationDao.findUserByUserIdx(userIdx);
		if(users == null){
			logger.error("Username not found: " + userIdx);
			throw new UsernameNotFoundException("could not found user..!!");
		}
		logger.info("User Authenticated Successfully..!!!");

		return users;
	}

}
