package ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dcl.DBHelper;
import model.Applicant;

//修改信息类，生成修改信息的页面布局, 修改申请人的个人信息
public class InfoChangeApply extends JPanel implements ActionListener{

	//变量定义
		Box boxH;//行式盒
		Box boxOne, boxTwo, boxThree, boxFour, boxFive, boxSix;//列式盒
		JTextField email, phone;//内容文本框
		JLabel title, emailL, phoneL;//内容标签
		JButton jButton;//提交按钮
		Applicant stu;//申请人对象
		//构造方法
		public InfoChangeApply(Applicant stu) {
			
			this.stu = stu;
			setLayout(new FlowLayout());//设置当前面板为流式布局
			
			setBounds(400, 100, 600, 600);
			setVisible(true);
			init();//页面初始化方法
		}
		//自定义页面初始化方法
		void init() {
			
			boxH = Box.createVerticalBox();//水平盒式布局
			boxOne = Box.createHorizontalBox();//垂直盒式布局
			boxTwo = Box.createHorizontalBox();
			boxThree = Box.createHorizontalBox();
			boxFour = Box.createHorizontalBox();
			boxFive = Box.createHorizontalBox();
			boxSix = Box.createHorizontalBox();
			
			email = new JTextField();//初始化文本框和标签控件
			phone = new JTextField();
			
			//设置文本框初始个人信息
			email.setText(stu.getEmail());
			phone.setText(stu.getPhone());

			
			jButton = new JButton("修改");
			//为按钮添加监听
			jButton.addActionListener(this);
			
			title = new JLabel("个人信息");
			emailL = new JLabel("电子邮件");
			phoneL = new JLabel("手机号码");
			
			boxOne.add(title);//向水平盒式布局添加文本框和标签
			boxTwo.add(emailL);
			boxTwo.add(Box.createHorizontalStrut(5));//添加水平支撑
			boxTwo.add(email);
			boxThree.add(phoneL);
			boxThree.add(Box.createHorizontalStrut(5));
			boxThree.add(phone);
			boxSix.add(jButton);
			
			boxH.add(Box.createVerticalStrut(100));//内层盒添加到外层盒
			boxH.add(boxOne);
			boxH.add(Box.createVerticalStrut(30));
			boxH.add(boxTwo);
			boxH.add(Box.createVerticalStrut(15));
			boxH.add(boxThree);
			boxH.add(Box.createVerticalStrut(25));
			boxH.add(boxSix);
			add(boxH);//将外层盒加入面板
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//获取更改后的信息
			String emailChange = email.getText();
			String phoneChange = phone.getText();
			//更新信息
			try {
				//操作对象是申请学生
				DBHelper.ChangeInfo(emailChange, phoneChange, "applicant", "stuid", stu.getStuID());
				//修改成功提示
				JOptionPane.showMessageDialog(null, "修改成功！", "错误提示", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//更新页面信息
			email.setText(emailChange);
			phone.setText(phoneChange);
		}
}
