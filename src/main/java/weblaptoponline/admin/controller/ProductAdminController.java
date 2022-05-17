package weblaptoponline.admin.controller;

import java.io.File;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import weblaptoponline.dao.CategoryDAO;
import weblaptoponline.dao.ProductDAO;
import weblaptoponline.entity.Category;
import weblaptoponline.entity.Product;
import weblaptoponline.service.UploadService;

@Controller
public class ProductAdminController {
	@Autowired
	ProductDAO pdao;

	@Autowired
	UploadService uploadService;

	@RequestMapping({ "/admin/product", "/admin/product/index" })
	public String index(Model model) {
		Product item = new Product();
		List<Product> list = pdao.findAll();
		model.addAttribute("item", item);
		model.addAttribute("list", list);
		return "admin/product/index";
	}

	@RequestMapping("/admin/product/edit/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		Product item = pdao.getById(id);
		List<Product> list = pdao.findAll();
		model.addAttribute("item", item);
		model.addAttribute("list", list);
		return "admin/product/index";
	}

	@RequestMapping("/admin/product/delete/{id}")
	public String delete(Model model, @PathVariable("id") Integer id) {
		pdao.deleteById(id);
		model.addAttribute("message", "Xóa hàng hóa thành công!");
		return "redirect:/admin/product/index";
	}

	@RequestMapping("/admin/product/create")
	public String create(Model model, @ModelAttribute("item") Product product,
			@RequestPart("image_file") MultipartFile image) {
		try {
			try {
				File file = uploadService.save(image, "/images/items/");
				product.setImage(file.getName());
			} catch (Exception e) {
				product.setImage("new.jpg");
			}
			pdao.save(product);
			model.addAttribute("message", "Thêm sản phẩm thành công!");
			return "redirect:/admin/product/index";
		} catch (Exception e) {
			model.addAttribute("message", "Thêm sản phẩm không thành công!");
			return "redirect:/admin/product/index";
		}
	}
	
	@RequestMapping("/admin/product/update")
	public String update(Model model, @ModelAttribute("item") Product product,
			@RequestPart("image_file") MultipartFile image) {
		try {
			try {
				File file = uploadService.save(image, "/images/items/");
				product.setImage(file.getName());
			} catch (Exception e) {
				product.setImage("new.jpg");
			}
			pdao.save(product);
			model.addAttribute("message", "Cập nhật sản phẩm thành công!");
			return "redirect:/admin/product/edit/" + product.getId();
		} catch (Exception e) {
			model.addAttribute("message", "Cập nhật sản phẩm không thành công!");
			return "redirect:/admin/product/index";
		}
	}
	 @RequestMapping("/search")
	    public String search(Model model,@RequestParam("keyword") String keyword) {
			Product item = new Product();
	        List<Product> list = pdao.findProducts("%"+keyword+"%");
	        model.addAttribute("list", list);
	        model.addAttribute("item", item);
	         
	        return "admin/product/index";
	    }


	@Autowired
	CategoryDAO cdao;

	@ModelAttribute("categories")
	public List<Category> getCategories() {
		return cdao.findAll();
	}
}