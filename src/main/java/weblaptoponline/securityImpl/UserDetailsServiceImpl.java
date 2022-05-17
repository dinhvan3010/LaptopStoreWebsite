package weblaptoponline.securityImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import weblaptoponline.dao.AccountDAO;
import weblaptoponline.entity.Account;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	AccountDAO adao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		hàm loadUserByUsername đưa vào username, nếu tồn tài trả về userDetail - interface mô tả mô hình user trong hệ thống security
		try {
			Account account = adao.findById(username).get();
			return new UserDetailsImpl(account);
		} catch (Exception e) {
			throw new UsernameNotFoundException(username + "not found");
		}

	}
}
