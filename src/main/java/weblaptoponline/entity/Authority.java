package weblaptoponline.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "authorities")
public class Authority{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer id;
			
	@ManyToOne
	@JoinColumn(name = "username")
	Account account;
	
	@ManyToOne
	@JoinColumn(name = "roleid")
	Role role;
}