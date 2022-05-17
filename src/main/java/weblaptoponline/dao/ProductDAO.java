package weblaptoponline.dao;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import weblaptoponline.entity.Product;

public interface ProductDAO extends JpaRepository<Product, Integer> {

	@Query("SELECT p FROM Product p WHERE p.category.id=?1")
	List<Product> findByCategoryId(Integer id);
	
	@Query("SELECT p FROM Product p WHERE p.name LIKE ?1")
	List<Product> findBKeywords(String keywords);
	
	@Query("SELECT p FROM Product p WHERE p.id=?1")
	List<Product> getProductById(Integer id);

	@Query("SELECT DISTINCT d.product FROM OrderDetail d "
			+ " WHERE d.order.account.username=?1")
	List<Product> findByUsername(String username);
	
	@Query("SELECT p  FROM Product p WHERE p.discount != 0")
	List<Product> getProductSaling();
	
	@Query("SELECT p "
			+ "FROM Product p, OrderDetail o "
			+ "WHERE p.id=o.product.id "
			+ "GROUP BY o.product.id, p.id ,p.name,p.image,p.unitPrice, p.discount,p.productDate,p.description,p.likeCount,p.available ,p.quantity,p.category.id "
			+ "ORDER BY COUNT(o.product.id) DESC")
	List<Product> getProductBestSeller(PageRequest of);
	
	@Query("SELECT p FROM Product p WHERE p.unitPrice>?1 and p.unitPrice <?2")
	List<Product> findByUnitPrice(Double min,Double max);
	
	@Query("SELECT p FROM Product p ORDER  BY p.productDate DESC ")
	List<Product> getProductSoftDay();
	
	@Query("SELECT p FROM Product p WHERE p.name LIKE ?1 ")
	List<Product> findProducts(String keyword);

}
