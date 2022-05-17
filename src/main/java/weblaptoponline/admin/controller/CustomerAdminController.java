package weblaptoponline.admin.controller;

import java.io.File;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import weblaptoponline.dao.AccountDAO;
import weblaptoponline.entity.Account;
import weblaptoponline.entity.Product;
import weblaptoponline.service.UploadService;

@Controller
public class CustomerAdminController {
	@Autowired
	AccountDAO adao;

	@Autowired
	BCryptPasswordEncoder pe;

	@Autowired
	UploadService uploadService;

	@RequestMapping({ "/admin/customer", "/admin/customer/index" })
	public String index(Model model) {
		Account item = new Account();
		List<Account> list = adao.findAllCustomers();

		model.addAttribute("item", item);
		model.addAttribute("list", list);
		return "admin/customer/index";
	}

	@RequestMapping("/admin/customer/edit/{username}")
	public String edit(Model model, @PathVariable("username") String username) {
		Account item = adao.getById(username);
		List<Account> list = adao.findAllCustomers();

		model.addAttribute("item", item);
		model.addAttribute("list", list);
		return "admin/customer/index";
	}

	@RequestMapping("/admin/customer/delete/{username}")
	public String delete(Model model, @PathVariable("username") String username) {
		adao.deleteById(username);
		model.addAttribute("message", "Xoá tài khoản thành công!");
		return "forward:/admin/customer/index";
	}

	@RequestMapping("/admin/customer/create")
	public String create(Model model,  @ModelAttribute("user") Account account, 
			@RequestPart("photo_file") MultipartFile photo, 
			@RequestParam("newpass") String newpass) {		
			try {
				File file = uploadService.save(photo, "/images/photos/");
				account.setPhoto(file.getName());
			} catch (Exception e) {
				account.setPhoto("new.png");
			}
			account.setPassword(pe.encode(newpass));
			adao.save(account);
			model.addAttribute("message", "Thêm tài khoản thành công!");
			return "forward:/admin/customer/index";
		
	}

	@RequestMapping("/admin/customer/update")
	public String update(Model model, @Valid @ModelAttribute("user") Account account, BindingResult bindingResult,
			@RequestPart("photo_file") MultipartFile photo, @RequestParam("newpass") String newpass) {
	
			try {
				File file = uploadService.save(photo, "/images/photos/");
				account.setPhoto(file.getName());
			} catch (Exception e) {
				account.setPhoto("new.png");
			}
			if (newpass.equals(account.getPassword())) {
				account.setPassword(pe.encode(newpass));
			}
			adao.save(account);
			model.addAttribute("message", "Cập nhật tài khoản thành công!");
			return "forward:/admin/customer/edit/" + account.getUsername();
		}
	 @RequestMapping("/searchAccount")
	    public String search(Model model,@RequestParam("keyword") String keyword) {
			Account item = new Account();
			List<Account> list = adao.finAccount("%"+keyword+"%");
			model.addAttribute("item", item);
			model.addAttribute("list", list);
			return "admin/customer/index";
	    }
	}
