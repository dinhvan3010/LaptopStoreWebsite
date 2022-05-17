package weblaptoponline.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

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
import weblaptoponline.entity.Account;
import weblaptoponline.service.MailService;
import weblaptoponline.service.UploadService;

@Controller
public class AccountController {
	@Autowired
	AccountDAO adao;

	@Autowired
	UploadService uploadService;

	@Autowired
	MailService mailService;

	@Autowired
	BCryptPasswordEncoder pe;

	@RequestMapping("/account/sign-up")
	public String signUpForm(Model model, @ModelAttribute("user") Account account) {
		return "account/sign-up";
	}


	@RequestMapping("/account/sign-up/create")
	public String signUpSave(Model model, 
			@ModelAttribute("user") Account account, 
			@RequestParam("confirm") String confirm,
			@RequestPart("photo_file") MultipartFile photo) {
		// create account
		if(!confirm.equals(account.getPassword())) {
			model.addAttribute("message", "Xác nhận mật khẩu không đúng");
		} else if(adao.existsById(account.getUsername())) {
			model.addAttribute("message", "Tên tài khoản đã tồn tại");
		} else {
			try {
				File file = uploadService.save(photo, "/images/photos/");
				account.setPhoto(file.getName());
			} catch (Exception e) {
				account.setPhoto("new.png");
			}
			account.setPassword(pe.encode(confirm));
			adao.save(account);
			mailService.sendWelcome(account);
			model.addAttribute("message", "Vui lòng check mail để kích hoạt");
		}
		return "account/sign-up";
	}

	@RequestMapping("/account/activate/{username}")
	public String activate(Model model, @PathVariable("username") String username) {
		Account account = adao.getById(username);
		account.setActivated(true);
		adao.save(account);
		model.addAttribute("message", "Tài khoản đã được kích hoạt!");
		return "forward:/security/login/form";
	}

	@RequestMapping("/account/forgot-password")
	public String forgotPasswordForm(Model model) {
		return "account/forgot-password";
	}

	@RequestMapping("/account/retrieve-password")
	public String retrievePassword(Model model, 
			@RequestParam("username") String username,
			@RequestParam("email") String email) {
		try {
			Account account = adao.getById(username);
			if (!account.getEmail().equalsIgnoreCase(email)) {
				model.addAttribute("message", "Sai Email!");
			} else {
				mailService.sendToken(account);
				model.addAttribute("message", "Vui lòng nhập token code!");
				return "account/reset-password";
			}
		} catch (Exception e) {
			model.addAttribute("message", "Sai tên đăng nhập!");
		}
		return "account/forgot-password";
	}

	@RequestMapping("/account/reset-password")
	public String resetPassword(Model model, @RequestParam("username") String username,
			@RequestParam("token") String token, @RequestParam("password") String password,
			@RequestParam("confirm") String confirm) {
		if (!password.equals(confirm)) {
			model.addAttribute("message", "Xác nhận mật khẩu đăng nhập!");
		} else {
			try {
				Account account = adao.getById(username);
				String token2 = Integer.toHexString(account.getPassword().hashCode());
				if (!token2.equals(token)) {
					model.addAttribute("message", "Token không hợp lệ!");
				} else {
					account.setPassword(pe.encode(password));
					adao.save(account);
					model.addAttribute("message", "Mật khẩu đã được khởi tạo!");
					return "forward:/security/login/form";
				}
			} catch (Exception e) {
				model.addAttribute("message", "Sai tên đăng nhập!");
			}
		}
		return "account/reset-password";
	}

	@RequestMapping("/account/change-password")
	public String changePasswordForm(Model model) {
		return "account/change-password";
	}

	@RequestMapping("/account/change-password/update")
	public String changePasswordSave(Model model,

			@RequestParam("username") String username,

			@RequestParam("password") String password,

			@RequestParam("newpass1") String newpass1,

			@RequestParam("newpass2") String newpass2) {
		if (!newpass1.equals(newpass2)) {
			model.addAttribute("message", "Xác nhận mật khẩu không đúng!");
		} else {
			try {
				Account account = adao.getById(username);

				if (!pe.matches(password, account.getPassword())) {
					model.addAttribute("message", "Sai mật khẩu cũ");
				} else {
					account.setPassword(pe.encode(newpass2));
					adao.save(account);
					model.addAttribute("message", "Đổi mật khẩu thành công!");
				}
			} catch (Exception e) {
				model.addAttribute("message", "Sai tên đăng nhập !");
			}
		}
		return "account/change-password";
	}

	@RequestMapping("/account/edit-profile")
	public String editProfileForm(Model model, HttpServletRequest request) {
		String username = request.getRemoteUser();
		Account account = adao.getById(username);
		model.addAttribute("user", account);
		return "account/edit-profile";
	}

	@RequestMapping("/account/edit-profile/update")
	public String editProfileSave(Model model,
			@ModelAttribute("user") Account account,
			@RequestParam("fullname") String fullname,
			@RequestParam("address") String address,
			@RequestParam("mobile") String mobile,
			@RequestParam("email") String email,
			@RequestPart("photo_file") MultipartFile photo) {
		try {
			File file = uploadService.save(photo, "/images/photos/");
			account.setPhoto(file.getName());
		} catch (Exception e) {
		}
		adao.save(account);
		model.addAttribute("message", "Cập nhật tài khoản thành công!");
		return "account/edit-profile";
	}

}
