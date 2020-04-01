package com.web.react.user;

import java.util.List;

import org.omg.CORBA.UserException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CommunityUserManager implements UserManager{

	@Override
	public User getUser(User template) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(User template, boolean caseSensitive) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(long userId) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User createUser(User newUser) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(User user) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUsers(List<User> users) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(User user) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> findUsers(String nameOrEmail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findUsers(String nameOrEmail, int startIndex, int numResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getFoundUserCount(String nameOrEmail) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getUserCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUsers(int startIndex, int numResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean verifyPassword(User user, String password) {
		// TODO Auto-generated method stub
		return false;
	}

}
