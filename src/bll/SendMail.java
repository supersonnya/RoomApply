package bll;

public class SendMail {

	public void sendMail(String email, String message) {
		// TODO Auto-generated constructor stub
		Mail mail = new Mail();
		mail.setHost("smtp.126.com"); // 设置邮件服务器
		mail.setPort("465"); // 设置邮件服务器端口号,默认25
		mail.setSender("apply_classroom@126.com"); // 发送人
		mail.setName("活动教室申请系统"); // 发送人昵称
		mail.setReceiver(email); // 接收人
		mail.setUserName("apply_classroom@126.com"); // 登录账号,一般都是和邮箱名一样
		mail.setPassWord("roomapply666"); // QQ邮箱登录第三方客户端时,密码框请输入“授权码”进行验证。其他的密码具体查看邮件服务器的说明
		mail.setSubject("通知邮件");
		mail.setMessage(message);
		MailUtil mailUtil = new MailUtil();
		mailUtil.send(mail);
	}

}
