package com.youtirsin.blah.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.youtirsin.blah.message.MessageResult;
import com.youtirsin.blah.message.ResultCode;
import com.youtirsin.blah.message.ResultTool;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	

	@Autowired
	public BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		
		return userRepository.findByName(username)
				.orElseThrow(()-> new UsernameNotFoundException(username + "Not found."));
	}
	
	public MessageResult signUp(User user) {
		if (userRepository.findByName(user.getName()).isPresent()) {
			return ResultTool.fail(ResultCode.USER_ACCOUNT_ALREADY_EXIST);
		}
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return ResultTool.success();
	}

	public MessageResult reset(User user) {
		Optional<User> u = userRepository.findByName(user.getName());
		if (!u.isPresent()) {
			return ResultTool.fail(ResultCode.USER_ACCOUNT_NOT_EXIST);
		}
		if (!u.get().getEmail().equals(user.getEmail())) {
			return ResultTool.fail(ResultCode.USER_RESET_VERIFIY_FAIL);
		}
		u.get().setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(u.get());
		return ResultTool.success();
	}
}
