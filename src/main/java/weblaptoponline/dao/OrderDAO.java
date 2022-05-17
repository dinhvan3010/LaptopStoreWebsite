package weblaptoponline.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import weblaptoponline.entity.Order;

public interface OrderDAO extends JpaRepository<Order, Long> {
	@Query("SELECT o FROM Order o WHERE o.account.username=?1 ORDER BY o.orderDate DESC")
	List<Order> findByUsername(String username);
	
	@Query("SELECT o FROM Order o WHERE o.statusId=-1")
	List<Order> findOrderCancel();
	
}
