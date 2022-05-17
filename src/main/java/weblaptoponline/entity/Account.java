package weblaptoponline.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Accounts")
public class Account {
	@Id
	String username;
	

	String password;
	
	String fullname;
	
	String mobile;
	
	String email;
	
	String address;
	
	String photo = "new.png";
	boolean activated;
	
	@OneToMany(mappedBy = "account")
	List<Order> orders;
	
	@OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
	List<Authority> authorities;
	
	public boolean hasRole(String roleId) {
		if(this.authorities == null) {
			return false;
		}
		return this.authorities.stream()
				.anyMatch(a -> a.getRole().getId().equals(roleId));
	}
}