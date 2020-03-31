package com.web.react.user;

import java.util.List;

import org.omg.CORBA.UserException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserManager {
	
	public abstract User getUser(User template );
	
	public abstract User getUser(User template , boolean caseSensitive );
	
	public abstract User getUser(String username) throws UsernameNotFoundException;
	
	public abstract User getUser(long userId) throws UsernameNotFoundException;
	
	public abstract User createUser(User newUser) throws UserException;
	
	public abstract void deleteUser(User user) throws UsernameNotFoundException ;
	
	public abstract void deleteUsers(List<User> users) throws UsernameNotFoundException;
	
	public abstract void updateUser(User user)  throws UsernameNotFoundException ;
	
    /**
     * 
     * @param nameOrEmail
     * @return
     */
    public abstract List<User> findUsers(String nameOrEmail);

    /**
     * 
     * @param nameOrEmail
     * @param startIndex
     * @param numResults
     * @return
     */
    public abstract List<User> findUsers(String nameOrEmail, int startIndex, int numResults);

    public abstract int getFoundUserCount(String nameOrEmail);
    
    
    public abstract int getUserCount();
    
    public abstract List<User> getUsers();

    public abstract List<User> getUsers(int startIndex, int numResults);
    
    /**
     * 인자로 전달된 평문 비밀번호를 사용자 정보에 저장된 암호화된 값과 비교 검사하고 그결과를 불값으로 리턴한다.
     * @param user
     * @param password
     * @return
     */
    public abstract boolean verifyPassword(User user, String password);
}
