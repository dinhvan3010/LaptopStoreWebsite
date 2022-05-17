package weblaptoponline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
//		sử dụng userDetailsService để làm việc với dữ liệu
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().disable();
		http.csrf().disable();
		
		http.authorizeRequests()
			.antMatchers("/admin/master","/admin/authority").hasRole("ADMIN")
			.antMatchers("/admin/**").hasAnyRole("ADMIN", "SUPER", "STAFF")			
			.antMatchers("/admin/home").permitAll()
			.antMatchers("/order/**", 
					"/account/change-password", 
					"/account/edit-profile").authenticated()
			.anyRequest().permitAll();
		
		http.exceptionHandling()
			.accessDeniedPage("/security/access/denied");
		
		http.formLogin()
			.loginProcessingUrl("/security/login")
			.loginPage("/security/login/form")
			.defaultSuccessUrl("/security/login/success")
			.failureUrl("/security/login/failure");
		
		http.rememberMe()
			.tokenValiditySeconds(10 * 24 * 60 * 60);
		
		http.logout()
			.logoutUrl("/security/logout")
			.logoutSuccessUrl("/security/logout/success")
			.addLogoutHandler(new SecurityContextLogoutHandler());
	}@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/js/**", "/css/**", "/images/**", "/api/**");		
	}
	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
