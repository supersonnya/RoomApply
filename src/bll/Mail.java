package bll;

import java.io.Serializable;

public class Mail implements Serializable{

	public static final String enCoding = "UTF-8";//邮件编码
	private String host;//服务器地址
	private String port;//服务器端口号
	private String sender;//发件人邮箱
	private String receiver;//收件人地址
	private String name;//发件人昵称
	private String userName;//账号
	private String passWord;//密码
	private String subject;//邮件主题
	private String message;//内容（支持HTML）

		//get和set方法
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}

	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
