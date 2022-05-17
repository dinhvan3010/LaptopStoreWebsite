package weblaptoponline.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import weblaptoponline.dao.ProductDAO;
import weblaptoponline.entity.Product;

@Controller
public class ProductController {
		@Autowired
		ProductDAO pdao;
		
		
		@RequestMapping("/product/list")
		public String index(Model model) {
			List<Product> list=pdao.findAll();
			model.addAttribute("items",list);
			return "product/list";
		}
		@RequestMapping({"/product/sale"})
		public String sale(Model model) {
			List<Product> list=pdao.getProductSaling();
			model.addAttribute("items",list);
			return "/product/list";
		}
		
		@RequestMapping("/product/list/{id}")
		public String index(Model model, @PathVariable("id") Integer id) {
			List<Product> list=pdao.findByCategoryId(id);
			model.addAttribute("items",list);		
			return "product/list";	
		}
		
		@RequestMapping("/product/search")
		public String index(Model model, @RequestParam("keywords") String keywords) {
			List<Product> list=pdao.findBKeywords("%"+keywords+"%");
			model.addAttribute("items",list);
			return "product/list";
		}
		
		@RequestMapping("/product/price")
		public String index1(Model model,  @RequestParam("min") Double min, @RequestParam("max") Double max) {
			List<Product> list=pdao.findByUnitPrice(min,max);
			model.addAttribute("items",list);
			return "product/list";
		}
		@RequestMapping("/product/new")
		public String index2(Model model) {
			List<Product> list=pdao.getProductSoftDay();
			model.addAttribute("items",list);
			return "product/list";
		}
		@RequestMapping("/product/bestseller")
		public String index3(Model model) {
			List<Product> list=pdao.getProductBestSeller(PageRequest.of(0, 10));
			model.addAttribute("items",list);
			return "product/list";
		}

		
		@RequestMapping("/product/detail/{id}")
		public String detail(Model model, @PathVariable("id") Integer id) {	
			Product item = pdao.getById(id);
			model.addAttribute("item", item);
			return "product/detail";
		}
		@RequestMapping("/product/purchased-by/{username}")
		public String purchasedBy(Model model, HttpServletRequest request) {
			String username = request.getRemoteUser();
			List<Product> list = pdao.findByUsername(username);
			model.addAttribute("items", list);
			return "product/list";
		}
}
