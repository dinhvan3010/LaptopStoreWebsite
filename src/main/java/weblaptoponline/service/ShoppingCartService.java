package weblaptoponline.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import weblaptoponline.dao.ProductDAO;
import weblaptoponline.entity.Product;

@SessionScope
@Service
public class ShoppingCartService {
	@Autowired
	ProductDAO pdao;
	
	Map<Integer, Product> map = new HashMap<>();
	
	public void add(Integer id) {
		if(map.containsKey(id)) {
			Product item = map.get(id);
			item.setQuantity(1 + item.getQuantity());
			
		}else {
			Product item = pdao.findById(id).get();
			item.setQuantity(1);
			map.put(id, item);
		}
		
	}
	public void remove(Integer id) {
		map.remove(id);
		
	}
	public void update(Integer id, Integer qty) {
	Product item = map.get(id);
	item.setQuantity(qty);
	
}
	public Object getInfo() {
		int count = 0;
		double amount = 0;
		for(Product p: map.values()) {
			count += p.getQuantity();
			amount += p.getQuantity()*p.getUnitPrice()*(1-p.getDiscount());
		}
		return Map.of("count",count,"mount",amount);
	}
	public Collection<Product> getItems(){
		return map.values();
	}
	public void clear() {
		map.clear();
		
	}
	
	public Double getAmount() {
		double amount = 0;
		for(Product p: map.values()) {
			amount += p.getQuantity()*p.getUnitPrice()*(1-p.getDiscount());
		}
		return amount; 
	}
	
	
}
