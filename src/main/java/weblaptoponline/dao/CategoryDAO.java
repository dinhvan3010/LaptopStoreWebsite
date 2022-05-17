package weblaptoponline.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import weblaptoponline.entity.Category;

public interface CategoryDAO extends JpaRepository<Category, Integer> {
	
}
