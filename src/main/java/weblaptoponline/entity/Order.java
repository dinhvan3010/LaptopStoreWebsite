package weblaptoponline.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "Orders")
public class Order {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name = "orderdate")
	Date orderDate = new Date();
	String recipient;
	String mobile;
	String address;
	double amount;
	
	@Column(name = "statusid")
	Integer statusId;
	String notes;
	
	@ManyToOne
	@JoinColumn(name = "username")
	Account account;
	
	@OneToMany(mappedBy = "order")
	List<OrderDetail> orderDetails;
}