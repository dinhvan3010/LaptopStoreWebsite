package weblaptoponline.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name="products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer Id;
	
	String name;
	
	String image;
	
	@Column(name="unitprice")
	Double unitPrice;
	
	@Column(name="discount")
	Double discount;
	
	@Column(name="productdate")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	
	Date productDate;

	String description;
	
	@Column(name="likecount")
	Integer likeCount;
	
	Boolean available;
	
	@Column(name="quantity")
	Integer quantity;
	@ManyToOne
	@JoinColumn(name="categoryid")
	Category category;
}
