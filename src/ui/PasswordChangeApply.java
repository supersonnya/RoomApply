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
//修改信息类, 修改申请人的登录密码
	public class PasswordChangeApply extends JPanel implements ActionListener{

			//变量定义
			Box boxH;//行式盒
			Box boxOne, boxThree, boxFour, boxFive, boxSix;//列式盒
			JTextField pwdOld, pwdNew, pwdCheck;//内容文本框
			JLabel title, oldL, newL, checkL;//内容标签
			JButton jButton;//提交按钮
			Applicant stu;//申请人对象
			//构造方法
			public PasswordChangeApply(Applicant stu) {
				
				this.stu = stu;
				setLayout(new FlowLayout());//设置当前面板为流式布局
				init(stu);//页面初始化方法
				setBounds(400, 100, 600, 600);
				setVisible(true);
			}
			//自定义页面初始化方法
			void init(Applicant stu) {
				
				boxH = Box.createVerticalBox();//水平盒式布局
				boxOne = Box.createHorizontalBox();//垂直盒式布局
				boxThree = Box.createHorizontalBox();
				boxFour = Box.createHorizontalBox();
				boxFive = Box.createHorizontalBox();
				boxSix = Box.createHorizontalBox();
				
				//初始化文本框和标签控件
				pwdOld = new JTextField(15);
				pwdNew = new JTextField(15);
				pwdCheck = new JTextField(15);
				
				jButton = new JButton("修改");
				//为按钮添加监听
				jButton.addActionListener(this);
				
				title = new JLabel("修改密码");
				oldL = new JLabel("原  密  码");
				newL = new JLabel("新  密  码");
				checkL = new JLabel("确认密码");
				
				boxOne.add(title);//向水平盒式布局添加文本框和标签
				boxFour.add(oldL);
				boxFour.add(Box.createHorizontalStrut(5));
				boxFour.add(pwdOld);
				boxFive.add(newL);
				boxFive.add(Box.createHorizontalStrut(5));
				boxFive.add(pwdNew);
				boxThree.add(checkL);
				boxThree.add(Box.createHorizontalStrut(5));
				boxThree.add(pwdCheck);
				boxSix.add(jButton);
				
				boxH.add(Box.createVerticalStrut(100));//内层盒添加到外层盒
				boxH.add(boxOne);
				boxH.add(Box.createVerticalStrut(30));
				boxH.add(boxFour);
				boxH.add(Box.createVerticalStrut(15));
				boxH.add(boxFive);
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
				String pwdChange = pwdNew.getText();

				//更新信息
				try {
						//判断原密码是否正确
						if(pwdOld.getText().equals(stu.getPassWord())) {
							//判断新密码和确认密码是否一致
							if (pwdNew.getText().equals(pwdCheck.getText())) {
								//操作对象是教务管理员
								DBHelper.ChangeInfo(pwdChange, "applicant", "stuid", stu.getStuID());
								//修改成功提示
								JOptionPane.showMessageDialog(null, "修改成功！", "错误提示", JOptionPane.INFORMATION_MESSAGE);
							} else {
								//提示框，前后密码不一致
								JOptionPane.showMessageDialog(null, "新密码与确认密码不一致！", "消息提示", JOptionPane.WARNING_MESSAGE);
							}
						} else {
							//提示框，原密码错误
							JOptionPane.showMessageDialog(null, "原密码错误！", "错误提示", JOptionPane.WARNING_MESSAGE);
						}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//清空页面信息
				pwdOld.setText("");
				pwdNew.setText("");
				pwdCheck.setText("");
			}
}
