package weblaptoponline.admin.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import weblaptoponline.dao.AccountDAO;
import weblaptoponline.dao.AuthorityDAO;
import weblaptoponline.dao.RoleDAO;
import weblaptoponline.entity.Account;
import weblaptoponline.entity.Authority;
import weblaptoponline.entity.Role;
import weblaptoponline.service.UploadService;

@Controller
public class MasterAdminController {
	@Autowired
	AccountDAO adao;
	
	@Autowired
	BCryptPasswordEncoder pe;
	
	@Autowired
	UploadService uploadService;

	@RequestMapping({"/admin/master", "/admin/master/index"})
	public String index(Model model) {
		Account item = new Account();
		List<Account> list = adao.findAllMasters();
		
		model.addAttribute("item", item);
		model.addAttribute("list", list);
		return "admin/master/index";
	}
	
	@RequestMapping("/admin/master/edit/{username}")
	public String edit(Model model, 
			@PathVariable("username") String username) {
		Account item = adao.getById(username);
		List<Account> list = adao.findAllMasters();
		
		model.addAttribute("item", item);
		model.addAttribute("list", list);
		return "admin/master/index";
	}
	
	@RequestMapping("/admin/master/delete/{username}")
	public String delete(Model model, 
			@PathVariable("username") String username) {
		adao.deleteById(username);
		model.addAttribute("message", "Xóa tài khoản thành công!");
		return "forward:/admin/master/index";
	}
	
	@RequestMapping("/admin/master/create")
	public String create(Model model, 
			@ModelAttribute("user") Account account,
			@RequestPart("photo_file") MultipartFile photo,
			@RequestParam("newpass") String newpass,
			@RequestParam("roleId") List<String> roleIds) {
		try {
			File file = uploadService.save(photo, "/images/photos/");
			account.setPhoto(file.getName());
		} catch (Exception e) {
			account.setPhoto("new.png");
		}
		account.setPassword(pe.encode(newpass));
		
		adao.save(account);
		this.addAuthorities(account, roleIds); 
		
		model.addAttribute("message", "Thêm tài khoản thành công!");
		return "redirect:/admin/master/index";
	}


	@RequestMapping("/admin/master/update")
	public String update(Model model, 
			@ModelAttribute("user") Account account,
			@RequestPart("photo_file") MultipartFile photo,
			@RequestParam("newpass") String newpass,
			@RequestParam("roleId") List<String> roleIds) {
		try {
			File file = uploadService.save(photo, "/images/photos/");
			account.setPhoto(file.getName());
		} catch (Exception e) {
			account.setPhoto("new.png");
		}
		if(newpass.equals(account.getPassword())) {
			account.setPassword(pe.encode(newpass));
		}
		
		this.removeAuthorities(account.getUsername()); 
		this.addAuthorities(account, roleIds); 
		adao.save(account);
		
		model.addAttribute("message", "Cập nhật tài khoản thành công !");
		return "redirect:/admin/master/edit/" + account.getUsername();
	}
	
	@Autowired
	RoleDAO rdao;
	
	@Autowired
	AuthorityDAO audao;
	
	@ModelAttribute("roles")
	public List<Role> getRoles(){
		return rdao.findAll();
	}
	
	private void addAuthorities(Account account, List<String> roleIds) {
		List<Authority> authorities = roleIds.stream().map(roleId -> {
			Authority authority = new Authority();
			authority.setAccount(account);
			authority.setRole(rdao.getById(roleId));
			return authority;
		}).toList();
		account.setAuthorities(authorities);
		audao.saveAll(authorities);
	}
	
	private void removeAuthorities(String username) {
		audao.deleteAll(adao.getById(username).getAuthorities());
	}
	
}