package ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dcl.DBHelper;
import model.Applicant;
import model.TeachingManager;

public class LoginPage extends JFrame implements ActionListener{

	/**
	 * 登录界面类
	 * 1、验证用户的身份，根据用户的不同类型显示不同的登录菜单
	 * 2、根据用户名从系统中获取用户信息并传递
	 */
	private static final long serialVersionUID = 1L;
	Box boxH;//行式盒
	Box boxVOne, boxVTwo, boxVThree;//列式盒
	JTextField textField;//用户名文本框
	JPasswordField passWord;//密码框
	JLabel user;//用户名
	JLabel pwd;//密码
	JButton jButton;//登录按钮
	
	public LoginPage(String s) {
		
		setLayout(new FlowLayout());//设置当前窗口为流式布局
		init(s);
		setBounds(500, 200, 300, 300);//登录窗口
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	void init(String s) {
		
		setTitle(s);
		boxH = Box.createVerticalBox();//垂直盒式布局
		boxVOne = Box.createHorizontalBox();//水平盒式布局
		boxVTwo = Box.createHorizontalBox();
		boxVThree = Box.createHorizontalBox();
		user = new JLabel("用户名");//用户名标签
		textField = new JTextField(10);//用户名文本
		pwd = new JLabel("密    码");//密码标签
		passWord = new JPasswordField(10);//密码框
		jButton = new JButton("登录");//登录按钮
		jButton.addActionListener(this);
		
		boxVOne.add(user);//添加用户标签
		boxVOne.add(Box.createHorizontalStrut(5));//添加水平支撑
		boxVOne.add(textField);//添加用户文本框
		boxVTwo.add(pwd);//添加密码标签
		boxVTwo.add(Box.createHorizontalStrut(5));
		boxVTwo.add(passWord);//添加密码框
		boxVThree.add(jButton);//添加登录按钮
		
		boxH.add(Box.createVerticalStrut(70));
		boxH.add(boxVOne);//将盒式列式布局添加到水平盒式布局
		boxH.add(Box.createVerticalStrut(15));//添加垂直支撑
		boxH.add(boxVTwo);
		boxH.add(Box.createVerticalStrut(20));
		boxH.add(boxVThree);
		add(boxH);
	}

	@Override//登录按钮的监听事件
	public void actionPerformed(ActionEvent e) {
		// 构造登录信息查询语句
		String sql = "select * from login where user='"+textField.getText()+"'";
		try {
			//返回数据
			ResultSet rs = DBHelper.Query(sql);
			
			//对用户身份校验
			while(rs.next()) {
				//获取密码框的内容
				String pass = new String(passWord.getPassword());
				
				if (rs.getString("password").equals(pass)) {//校验密码
					//使用equals方法，使用==时出错
					if(rs.getString("type").equals("stu")) {//判断用户类型
						//构造当前登录用户对象
						Applicant stu = DBHelper.getApplicant(rs.getString("user"));
						JOptionPane.showMessageDialog(null, "登录成功！", "消息提示", JOptionPane.INFORMATION_MESSAGE);
						dispose();//关闭登录窗口
						ApplicantMenu appMenu = new ApplicantMenu("教室申请系统", stu);//进入申请人菜单
						
					}else if(rs.getString("type").equals("teach")) {//判断用户类型
						//构造当前登录用户对象
						TeachingManager manager = DBHelper.getManager(rs.getString("user"));
						JOptionPane.showMessageDialog(null, "登录成功！", "消息提示", JOptionPane.INFORMATION_MESSAGE);
						dispose();//关闭登录窗口
						ManagerMenu manaMenu = new ManagerMenu("教室管理系统", manager);//进入教务管理员菜单
					}	
				} else {
					//错误提示,用户名或密码错误
					JOptionPane.showMessageDialog(null, "用户名或密码错误！", "错误提示", JOptionPane.WARNING_MESSAGE);
				}
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
