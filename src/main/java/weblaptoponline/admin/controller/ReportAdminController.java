package weblaptoponline.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import weblaptoponline.dao.ReportDAO;
import weblaptoponline.entity.Report;

@Controller
public class ReportAdminController {
	@Autowired
	ReportDAO dao;
	
	@RequestMapping("/admin/report/inventory")
	public String inventory(Model model) {
		List<Report> list = dao.getInventoryData();
		model.addAttribute("list", list);
		return "admin/report/inventory";
	}
	
	@RequestMapping("/admin/report/revenue-by-category")
	public String revenueByCategory(Model model) {
		List<Report> list = dao.getRevenueByCategoryData();
		model.addAttribute("list", list);
		return "admin/report/revenue-by-category";
	}
	
	@RequestMapping("/admin/report/revenue-by-year")
	public String revenueByYear(Model model) {
		List<Report> list = dao.getRevenueByYearData();
		model.addAttribute("list", list);
		return "admin/report/revenue-by-year";
	}
	
	@RequestMapping("/admin/report/revenue-by-vip")
	public String revenueByVip(Model model) {
		List<Report> list = dao.getRevenueByVipData(PageRequest.of(0, 10));
		model.addAttribute("list", list);
		return "admin/report/revenue-by-vip";
	}
	
	@ResponseBody
	@RequestMapping("/api/admin/report/revenue-by-vip")
	public List<Report> revenueByVipApi() {
		List<Report> list = dao.getRevenueByVipData(PageRequest.of(0, 10));
		return list;
	}
	
	@ResponseBody
	@RequestMapping("/api/admin/report/inventory")
	public List<Report> inventory() {
		List<Report> list = dao.getInventoryData();
		return list;
	}
	@ResponseBody
	@RequestMapping("/api/admin/report/revenue-by-year")
	public List<Report> revenueByYear() {
		List<Report> list = dao.getRevenueByYearData();
		return list;
	}
	@ResponseBody
	@RequestMapping("/api/admin/report/revenue-by-category")
	public List<Report> revenueByCategory() {
		List<Report> list = dao.getRevenueByCategoryData();
		return list;
	}
}
