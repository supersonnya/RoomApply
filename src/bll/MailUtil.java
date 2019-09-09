package bll;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

//邮件发送工具实现类
public class MailUtil {

	public boolean send(Mail mail) {
		//发送email对象
		HtmlEmail email = new HtmlEmail();
		try {
			//smtp发送服务器的名字
			email.setHostName(mail.getHost());
			//
			if(!"".equals(mail.getPort())) {
				email.setSSLOnConnect(true);
				email.setSslSmtpPort(mail.getPort());
			}
			//设置编码字符集
			email.setCharset(Mail.enCoding);
			//收件人的邮箱
			email.addTo(mail.getReceiver());
			//发件人的邮箱
			email.setFrom(mail.getSender(), mail.getName());
			//设置认证信息
			email.setAuthentication(mail.getUserName(), mail.getPassWord());
			//邮件主题
			email.setSubject(mail.getSubject());
			//邮件内容
			email.setMsg(mail.getMessage());
			//发送邮件
			email.send();
			return true;
		}catch(EmailException e) {
			e.printStackTrace();
			return false;
		}
		
	}
}
