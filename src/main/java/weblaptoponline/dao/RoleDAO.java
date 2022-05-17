package weblaptoponline.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import weblaptoponline.entity.Role;

public interface RoleDAO extends JpaRepository<Role, String>{
}
