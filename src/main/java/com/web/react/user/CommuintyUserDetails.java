package com.web.react.user;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author donghyuck
 *
 */
public class CommuintyUserDetails extends User {

	@JsonIgnore
	private final com.web.react.user.User communityUser;

	public CommuintyUserDetails(com.web.react.user.User communityUser) {
		super(communityUser.getUsername(), communityUser.getPassword(), communityUser.isEnabled(), true, true, true, AuthorityUtils.NO_AUTHORITIES);
		this.communityUser = communityUser;
	}

	public CommuintyUserDetails(com.web.react.user.User communityUser, List<GrantedAuthority> authorities) {
		super(communityUser.getUsername(), communityUser.getPassword(), communityUser.isEnabled(), true, true, true, authorities);
		this.communityUser = communityUser;
	}

	public boolean isAnonymous() {
		return communityUser.isAnonymous();
	}

	public com.web.react.user.User getUser() {
		return communityUser;
	}
 
	public boolean isAccountNonExpired() {
		return communityUser.isEnabled();
	}
 
	public boolean isAccountNonLocked() {
		return communityUser.isEnabled();
	}
 
	public boolean isCredentialsNonExpired() {
		return true;
	}
 
	public boolean isEnabled() {
		return communityUser.isEnabled();
	}

	public long getUserId() {
		return communityUser.getUserId();
	}

	public long getCreationDate() {
		return communityUser.getCreationDate() != null ? communityUser.getCreationDate().getTime() : -1L;
	}

}
