package weblaptoponline.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import weblaptoponline.dao.OrderDAO;
import weblaptoponline.entity.Order;

@Controller
public class OrderAdminController {
	@Autowired
	OrderDAO adao;
	
	@RequestMapping({"/admin/order", "/admin/order/index"})
	public String index(Model model) {
		Order item = new Order();
		List<Order> list = adao.findOrderCancel();		
		model.addAttribute("item", item);
		model.addAttribute("list", list);
		return "admin/order/index";
	}
	
	@RequestMapping("/admin/order/edit/{id}")
	public String edit(Model model, @PathVariable("id") Long id) {
		Order item = adao.getById(id);
		List<Order> list = adao.findAll();
		
		model.addAttribute("item", item);
		model.addAttribute("list", list);
		return "admin/order/index";
	}
	
	@RequestMapping("/admin/order/delete/{id}")
	public String delete(Model model, @PathVariable("id") Long id) {
		adao.deleteById(id);
		model.addAttribute("message", "Xóa loại hàng thành công!");
		return "forward:/admin/order/index";
	}
	
	@RequestMapping("/admin/order/create")
	public String create(Model model, 
			@ModelAttribute("item") Order order) {
		adao.save(order);
		model.addAttribute("message", "Thêm loại hàng thành công!");
		return "forward:/admin/order/index";
	}
	
	@RequestMapping("/admin/order/update")
	public String update(Model model, 
			@ModelAttribute("item") Order order) {
		adao.save(order);
		model.addAttribute("message", "Cập nhật loại hàng thành công!");
		return "forward:/admin/order/edit/" + order.getId();
	}
	
	@ModelAttribute("statuses")
	public Map<Integer, String> getStatuses(){
		return Map.of(0, "Đơn hàng mới", 
				1, "Đang liên hệ",
				2, "Đã xác nhận",
				3, "Đang chuyển hàng",
				4, "Đã hoàn thành",
				-1, "Đã hủy");
	}
}