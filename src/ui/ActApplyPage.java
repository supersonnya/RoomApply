package ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import bll.SendMail;
import dcl.DBHelper;
import model.Applicant;

//活动申请界面类，加载活动申请页面
public class ActApplyPage extends JPanel implements ActionListener{

	//定义变量
	Box boxH;//行式盒
	Box boxOne, boxTwo, boxThree, boxFour, boxFive, boxSix;//列式盒
	JTextField themeT, containT;//内容文本框
	JTextArea contentT;
	JLabel titleL, themeL, contentL, roomTypeL, containL;//内容标签
	JButton jButton;//提交按钮
	ButtonGroup group;//单选按钮组
	JRadioButton radioO, radioM;//单选按钮
	
	Applicant stu;//登录用户对象
	//构造方法
	public ActApplyPage(Applicant stu) {
		
		this.stu = stu;
		setLayout(new FlowLayout());//设置当前面板为流式布局
		setBounds(400, 100, 600, 600);//设置面板大小
		init();//页面初始化类
	}
	//自定义页面初始化方法
	private void init() {
		//设置每个盒内容的排列方式
		boxH = Box.createVerticalBox();//垂直排列
		boxOne = Box.createHorizontalBox();//水平排列
		boxTwo = Box.createHorizontalBox();
		boxThree = Box.createHorizontalBox();
		boxFour = Box.createHorizontalBox();
		boxFive = Box.createHorizontalBox();
		boxSix = Box.createHorizontalBox();
		//初始化文本框和标签控件
		themeT = new JTextField(20);
		contentT = new JTextArea(3, 20);
		containT = new JTextField(20);
		jButton = new JButton("提交");
		jButton.addActionListener(this);
		titleL = new JLabel("活动申请");//功能页面的小标题
		themeL = new JLabel("活动主题");
		contentL = new JLabel("活动内容");
		roomTypeL = new JLabel("教室类型");
		containL = new JLabel("活动人数");
		group = new ButtonGroup();//单选按钮组，选择教室类型
		radioO = new JRadioButton("普通教室");
		radioM = new JRadioButton("多媒体教室");
		group.add(radioO);//单选按钮归组
		group.add(radioM);
		//添加小标题
		boxOne.add(titleL);
		//添加活动主题标签和文本
		boxTwo.add(themeL);
		boxTwo.add(Box.createHorizontalStrut(5));//水平支撑
		boxTwo.add(themeT);
		//添加活动内容标签和文本
		boxThree.add(contentL);
		boxThree.add(Box.createHorizontalStrut(5));
		boxThree.add(contentT);
		//添加活动教室类型标签和文本
		boxFour.add(roomTypeL);
		boxFour.add(Box.createHorizontalStrut(20));
		boxFour.add(radioO);
		boxFour.add(Box.createHorizontalStrut(40));
		boxFour.add(radioM);
		//添加活动人数标签和文本
		boxFive.add(containL);
		boxFive.add(Box.createHorizontalStrut(5));
		boxFive.add(containT);
		//按钮添加到一个水平盒
		boxSix.add(jButton);
		//内层盒添加到外层盒
		boxH.add(Box.createVerticalStrut(15));//垂直支撑
		boxH.add(boxOne);
		boxH.add(Box.createVerticalStrut(35));
		boxH.add(boxTwo);
		boxH.add(Box.createVerticalStrut(10));
		boxH.add(boxThree);
		boxH.add(Box.createVerticalStrut(10));
		boxH.add(boxFour);
		boxH.add(Box.createVerticalStrut(10));
		boxH.add(boxFive);
		boxH.add(Box.createVerticalStrut(25));
		boxH.add(boxSix);
		//外层盒添加到面板
		add(boxH);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String theme = themeT.getText();
		String content = contentT.getText();
		String roomType = radioO.isSelected() ? "普通教室" : "多媒体教室";
		String contain = containT.getText();
		
		
		//添加一条活动记录
		try {
			//更新数据库
			DBHelper.UpdateList(theme, content, roomType, contain, stu.getStuID());
			
			//获取管理员邮箱
			String email = DBHelper.getEmail();
			
			//向教务管理员发送邮件
			String message = "您好，有申请人发起了新的活动申请，请及时登录活动教室申请系统审核。邮件来自活动教室申请系统！";
			SendMail sendMail = new SendMail();
			sendMail.sendMail(email, message);
			
			//弹出提示框
			JOptionPane.showMessageDialog(new JFrame().getContentPane(), "提交成功，已通知教务管理员处理！", "消息提示", JOptionPane.INFORMATION_MESSAGE);
			
			//清空文本框内容
			themeT.setText("");
			contentT.setText("");
			group.clearSelection();
			containT.setText("");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("error");
		}
		
	}
}
