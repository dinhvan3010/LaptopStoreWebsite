package weblaptoponline.service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import weblaptoponline.entity.Account;

@Service
public class MailService {
	@Autowired
	JavaMailSender mailSender;

	public void send(String email, String subject, String text) {
		try {
			MimeMessage mail = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mail, "utf-8");
			
			helper.setTo(email);
			helper.setSubject(subject);
			helper.setText(text, true);
			
			InternetAddress from = new InternetAddress("poly@gmail.com", "GEARHUB");
			helper.setFrom(from);
			helper.setReplyTo(from);
			
			mailSender.send(mail);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Unabled to send to " + email);
		}
	}
	
	public void sendWelcome(Account account) {
		String email = account.getEmail();
		String subject = "Welcome to GEARHUB";
		 String text = "<h3 style=\"color:Tomato;\">Chào mừng "+ account.getUsername()  +  " đến với GEARHUB</h3>"
	                +"Vui lòng chọn đường dẫn bên dưới đễ kích hoạt tài khoản :"
				 +"http://localhost:8080/account/activate/" + account.getUsername()
				+"<img src='https://image.winudf.com/v2/image1/Y29tLmFqaW5mby53bG9zX3NjcmVlbl84XzE2Mjk2OTEyMTdfMDE4/screen-8.jpg?h=355&fakeurl=1&type=.jpg'>";
		 
		this.send(email, subject, text);
	}
	
	public void sendToken(Account account) {
		String email = account.getEmail();
		String subject = "Reset Password ";
		String text = "Mã Token của bạn là : " + Integer.toHexString(account.getPassword().hashCode())
		+"<img src='https://eco-smart.biz/wp-content/uploads/2021/01/cyber-security-la-gi-dinh-nghia-chuc-nang-tam-quan-trong-2.jpg'>";
		this.send(email, subject, text);
	}
	
	public void sendOrder(Account account, long orderId) {
		String email = account.getEmail();
		String subject = "Your Order";
		String text = "<h3 style=\"color:Tomato;\">Chào  "+ account.getUsername()  +  " ,</h3>"
				 +"<h4>Bạn vừa đặt thành công 1 đơn hàng, vui lòng vào đường link bên dưới để xem chi tiết đơn hàng :</h4>"
				+ "http://localhost:8080/order/detail/" + orderId
				+"<img src='https://www.english-learning.net/wp-content/uploads/2018/03/Thank-you.jpg'>";
				
		this.send(email, subject, text);
	}
}