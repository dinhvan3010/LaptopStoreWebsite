package weblaptoponline.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Roles")
public class Role {
	@Id
	String id;
	String name;
	
	@OneToMany(mappedBy = "role")
	List<Authority> authorities;
}