package weblaptoponline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import weblaptoponline.interceptor.AppInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	@Autowired
	AppInterceptor appinterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(appinterceptor)
		.addPathPatterns("/**")
		.excludePathPatterns("/admin/**","/js/**","/css/**","/images/**");
	}
}
