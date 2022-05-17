package weblaptoponline.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import weblaptoponline.service.ShoppingCartService;

@Controller
public class ShoppingCartController {
	@Autowired
	ShoppingCartService cart;
	@RequestMapping("/cart/view")
	public Object view(Model model) {
		model.addAttribute("cart", cart);
		return "cart/view";
	}
	@ResponseBody
	@RequestMapping("/api/cart/info")
	public Object info() {
		return cart.getInfo();
	}
	
	@ResponseBody
	@RequestMapping("/api/cart/add/{id}")
	public Object add(@PathVariable("id") Integer id) {
		cart.add(id);
		return cart.getInfo();
	}
	@ResponseBody
	@RequestMapping("/api/cart/remove/{id}")
	public Object remove(@PathVariable("id") Integer id) {
		cart.remove(id);
		return cart.getInfo();
	}
	@ResponseBody
	@RequestMapping("/api/cart/update/{id}/{qty}")
	public Object update(@PathVariable("id") Integer id, @PathVariable("qty") Integer qty) {
		cart.update(id, qty);
		return cart.getInfo();
	}
	@ResponseBody
	@RequestMapping("/api/cart/clear")
	public Object clear() {
		cart.clear();
		return cart.getInfo();
	}
}
 