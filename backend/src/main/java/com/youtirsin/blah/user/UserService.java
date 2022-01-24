package com.youtirsin.blah.user;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
	
	public void signUp(User user) throws Exception {
		// throws exception if user already exists
		try {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			user.setDescription("介绍一下自己吧:)");
			userRepository.save(user);			
		}catch (Exception e) {
			throw new Exception();
		}
	}

	public void reset(User user){
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	
	public User getUserWithName(String name) throws Exception { 
		// throws exception that the user does not exist
		Optional<User> user = userRepository.findByName(name);
		if (!user.isPresent()) {
			throw new Exception();
		}
		return user.get();
	}

	public void updateUser(User user) {
		userRepository.save(user);
	}

	public ArrayList<String> searchUsers(String keyword) {
		
		Iterable<User> searched = userRepository.findAll();
		ArrayList<String> res = new ArrayList<>();
		for (User user : searched) {
			String name = user.getName();
			if (name.contains(keyword)) {
				res.add(name);
			}
		}
		return res;
	}
}
