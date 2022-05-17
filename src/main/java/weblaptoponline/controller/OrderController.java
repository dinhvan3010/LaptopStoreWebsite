package weblaptoponline.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import weblaptoponline.dao.AccountDAO;
import weblaptoponline.dao.OrderDAO;
import weblaptoponline.dao.OrderDetailDAO;
import weblaptoponline.entity.Account;
import weblaptoponline.entity.Order;
import weblaptoponline.entity.OrderDetail;
import weblaptoponline.service.MailService;
import weblaptoponline.service.ShoppingCartService;

@Controller
public class OrderController {
	@Autowired
	AccountDAO adao;
	
	@Autowired
	OrderDAO odao;
	
	@Autowired
	OrderDetailDAO oddao;
	
	@Autowired
	MailService mailService;
	
	@Autowired
	ShoppingCartService cart;
	
	@RequestMapping("/order/checkout")
	public String checkout(Model model) {
		model.addAttribute("cart", cart);
		return "order/checkout";
	}
	
	@RequestMapping("/order/purchase")
	public String purchase(Model model, @ModelAttribute("order") Order order,HttpServletRequest request) {
		String username = request.getRemoteUser();
		Account account = adao.getById(username);
		order.setOrderDate(new Date());
		order.setAmount(cart.getAmount());
		order.setStatusId(0);
		order.setAccount(account);
		odao.save(order);
		
		cart.getItems().forEach(p -> {
			OrderDetail d = new OrderDetail();
			d.setOrder(order);
			d.setProduct(p);
			d.setDiscount(p.getDiscount());
			d.setQuantity(p.getQuantity());
			d.setUnitPrice(p.getUnitPrice());
			
			oddao.save(d);
			
		});
		cart.clear();
		mailService.sendOrder(account, order.getId());
		model.addAttribute("message", "Đặt hành thành công");
		return "redirect:/order/detail/" + order.getId();
	}

	@RequestMapping("/order/list")
	public String list(Model model, HttpServletRequest request) {
		String username = request.getRemoteUser();
		List<Order> list = odao.findByUsername(username);
		model.addAttribute("orders", list);
		return "order/list";
	}
	
	
	
	@RequestMapping("/order/detail/{id}")
	public String detail(Model model, @PathVariable("id") Long id) {
		Order order = odao.getById(id);
		model.addAttribute("order", order);
		return "order/detail";
	}
	
	@RequestMapping("/order/cancel/{id}")
	public String cancel(Model model, @PathVariable("id") Long id) {
		Order order = odao.getById(id);
		order.setStatusId(-1);
		odao.save(order);
		model.addAttribute("message", "Đơn hàng đã được hủy");
		return "redirect:/order/detail/" + id;
	}
	@RequestMapping("/order/items")
	public String items(HttpServletRequest request) {
		String username = request.getRemoteUser();
		return "redirect:/product/purchased-by/" + username;
	}
	
	@ModelAttribute("statuses")
	public Map<Integer, String> getStatuses(){
		return Map.of(0, "Đơn hàng mới", 
				1, "Đang liên lạc", 
				2, "Đang xác nhận", 
				3, "Đang chuyển hàng", 
				4, "Đã hoàn thành",
				-1, "Bán hàng");
	}
		
}