package weblaptoponline.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import weblaptoponline.entity.OrderDetail;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, Long> {

}