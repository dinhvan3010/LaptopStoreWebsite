package weblaptoponline.admin.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import weblaptoponline.dao.CategoryDAO;
import weblaptoponline.entity.Category;

@Controller
public class CategoryAdminController {
	@Autowired
	CategoryDAO adao;

	@RequestMapping({ "/admin/category", "/admin/category/index" })
	public String index(Model model) {
		Category item = new Category();
		List<Category> list = adao.findAll();

		model.addAttribute("item", item);
		model.addAttribute("list", list);
		return "admin/category/index";
	}

	@RequestMapping("/admin/category/edit/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		Category item = adao.getById(id);
		List<Category> list = adao.findAll();

		model.addAttribute("item", item);
		model.addAttribute("list", list);
		return "admin/category/index";
	}

	@RequestMapping("/admin/category/delete/{id}")
	public String delete(Model model, @PathVariable("id") Integer id) {	
		adao.deleteById(id);
		model.addAttribute("message", "Xóa loại hàng thành công!");
		return "forward:/admin/category/index";
	}

	@RequestMapping("/admin/category/create")
	public String create(  @ModelAttribute("category") Category category ,Model model) {
		adao.save(category);
		model.addAttribute("message", "Thêm loai hàng thành công!");
		return "forward:/admin/category/index";
	}

	@RequestMapping("/admin/category/update")
	public String update(Model model, @Valid @ModelAttribute("item") Category category, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("message", "Cập nhật loại hàng không thành công");
			return "forward:/admin/category/index";
		}
		adao.save(category);
		model.addAttribute("message", "Cập nhật loại hàng thành công");
		return "forward:/admin/category/edit/" + category.getId();
	}
}