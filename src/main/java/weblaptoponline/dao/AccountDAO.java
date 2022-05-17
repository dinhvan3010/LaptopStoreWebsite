package weblaptoponline.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import weblaptoponline.entity.Account;

public interface AccountDAO extends JpaRepository<Account, String> {
	@Query("SELECT a FROM Account a WHERE a.authorities IS NOT EMPTY")
	List<Account> findAllMasters();

	@Query("SELECT a FROM Account a WHERE a.authorities IS EMPTY")
	List<Account> findAllCustomers();

	@Query("SELECT a FROM Account a WHERE a.username LIKE ?1")
	List<Account> finAccount(String name);
}



