package weblaptoponline.securityImpl;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;
import weblaptoponline.entity.Authority;
import weblaptoponline.entity.Account;

public class UserDetailsImpl implements UserDetails {


	@Getter
	@Setter
	private Account account;

	public UserDetailsImpl(Account account) {
		this.account = account;
	}
//điều kiện để login đúng
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<Authority> list = account.getAuthorities();
		if(list == null || list.isEmpty()) {
			return List.of();
		}
		return list.stream().map(a -> a.getRole().getId()) //đổi sang stream map với get role, id
				.map(id -> new SimpleGrantedAuthority("ROLE_" + id))//tạo ra GrantedAuthority
				.toList();
		
	}

	@Override
	public String getPassword() {
		return account.getPassword();
	}

	@Override
	public String getUsername() {
		return account.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {		
		return account.isActivated();
	}

}
