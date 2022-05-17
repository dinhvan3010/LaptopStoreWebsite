package weblaptoponline.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import weblaptoponline.entity.Product;
import weblaptoponline.entity.Report;

public interface ReportDAO extends JpaRepository<Product, Integer>{

	@Query("SELECT p.category.nameVn AS group,"
			+ "sum(p.quantity) AS count,"
			+ "sum(p.unitPrice * p.quantity * (1 - p.discount)) AS sum,"
			+ "min(p.unitPrice) AS min,"
			+ "max(p.unitPrice) AS max,"
			+ "avg(p.unitPrice) AS avg "
			+ "FROM Product p "
			+ "GROUP BY p.category.nameVn")
	List<Report> getInventoryData();

	@Query("SELECT d.product.category.nameVn AS group,"
			+ "sum(d.quantity) AS count,"
			+ "sum(d.unitPrice * d.quantity * (1 - d.discount)) AS sum,"
			+ "min(d.unitPrice) AS min,"
			+ "max(d.unitPrice) AS max,"
			+ "avg(d.unitPrice) AS avg "
			+ "FROM OrderDetail d "
			+ "GROUP BY d.product.category.nameVn")
	List<Report> getRevenueByCategoryData();

	@Query("SELECT year(d.order.orderDate) AS group,"
			+ "sum(d.quantity) AS count,"
			+ "sum(d.unitPrice * d.quantity * (1 - d.discount)) AS sum,"
			+ "min(d.unitPrice) AS min,"
			+ "max(d.unitPrice) AS max,"
			+ "avg(d.unitPrice) AS avg "
			+ "FROM OrderDetail d "
			+ "GROUP BY year(d.order.orderDate) "
			+ "ORDER BY year(d.order.orderDate) ASC  ")
	List<Report> getRevenueByYearData();

	@Query("SELECT d.order.account.username AS group,"
			+ "sum(d.quantity) AS count,"
			+ "sum(d.unitPrice * d.quantity * (1 - d.discount)) AS sum,"
			+ "min(d.unitPrice) AS min,"
			+ "max(d.unitPrice) AS max,"
			+ "avg(d.unitPrice) AS avg "
			+ "FROM OrderDetail d "
			+ "GROUP BY d.order.account.username "
			+ "ORDER BY sum(d.unitPrice * d.quantity * (1 - d.discount)) DESC")
	List<Report> getRevenueByVipData(Pageable pageable);
}
