package weblaptoponline.entity;

import javax.persistence.Column;
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
@Table(name = "orderdetails")
public class OrderDetail {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	@Column(name = "unitprice")
	double unitPrice;
	int quantity;
	double discount;
	
	@ManyToOne
	@JoinColumn(name = "orderid")
	Order order;
	
	@ManyToOne
	@JoinColumn(name = "productid")
	Product product;
}