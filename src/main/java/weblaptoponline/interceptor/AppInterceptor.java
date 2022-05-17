package weblaptoponline.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import weblaptoponline.dao.CategoryDAO;
import weblaptoponline.entity.Category;

@Service
public class AppInterceptor implements HandlerInterceptor {
	@Autowired
	CategoryDAO cdao;
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		List<Category> list = cdao.findAll();
		request.setAttribute("categories", list);
	}
	
	
}
