package weblaptoponline.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import weblaptoponline.dao.AccountDAO;
import weblaptoponline.dao.AuthorityDAO;
import weblaptoponline.dao.CategoryDAO;
import weblaptoponline.dao.RoleDAO;
import weblaptoponline.entity.Account;
import weblaptoponline.entity.Authority;
import weblaptoponline.entity.Category;
import weblaptoponline.entity.Role;

@Controller
public class AuthorityAdminController {
	@Autowired
	AuthorityDAO adao;

	@RequestMapping({ "/admin/authority", "/admin/authority/index" })
	public String index(Model model) {
		Authority item = new Authority();
		List<Authority> list = adao.findAll();

		model.addAttribute("item", item);
		model.addAttribute("list", list);
		return "admin/authority/index";
	}

	@RequestMapping("/admin/authority/edit/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		Authority item = adao.getById(id);
		List<Authority> list = adao.findAll();

		model.addAttribute("item", item);
		model.addAttribute("list", list);
		return "admin/authority/index";
	}

	@RequestMapping("/admin/authority/delete/{id}")
	public String delete(Model model, @PathVariable("id") Integer id) {
		adao.deleteById(id);
		model.addAttribute("message", "Xóa phân quyền thành công!");
		return "forward:/admin/authority/index";
	}
	
	@RequestMapping("/admin/authority/create")
	public String create(Model model, 
			@ModelAttribute("item") Authority authority) {
		adao.save(authority);
		model.addAttribute("message", "Thêm phân quyền thành công!");
		return "forward:/admin/authority/index";
	}
	@RequestMapping("/admin/authority/update")
	public String update(Model model, 
			@ModelAttribute("item") Authority authority) {
		adao.save(authority);
		model.addAttribute("message", "Cập nhật loại hàng thành công!");
		return "forward:/admin/authority/edit/" + authority.getId();
	}
	
	@Autowired
	RoleDAO rdao;
	@ModelAttribute("roles")
	public List<Role> getroles(){
		return rdao.findAll();
	}
	
	@Autowired
	AccountDAO acdao;
	@ModelAttribute("accounts")
	public List<Account> getAccountsMasster(){
		return acdao.findAllMasters();
	}
}
