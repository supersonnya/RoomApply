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
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import bll.SendMail;
import dcl.DBHelper;
import model.ActList;
import model.TeachingManager;

public class ActCheckPage extends JFrame implements ActionListener{
	//定义变量
		Box boxH;//行式盒
		Box boxTwo, boxThree, boxFour, boxFive, boxSix;//列式盒
		JTextField themeT, contentT, roomTypeT, containT;//内容标签
		JLabel themeL, contentL, roomTypeL, containL, checkL, remarkL;//内容标题标签
		JTextField remarkT;
		JButton jButton;//提交按钮
		ButtonGroup group;//单选按钮组
		JRadioButton radioAgr, radioDis;//单选按钮
		ActList act;//一条活动记录
		TeachingManager manager;//教务处管理员对象
		//构造方法
		public ActCheckPage(ActList act, TeachingManager manager) {//接受数据行和数据面板参数
			
			this.act = act;//活动对象设为全局变量
			this.manager = manager;//教务处管理员
			setBounds(500, 200, 300, 300);
			setVisible(true);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			setLayout(new FlowLayout());//设置当前面板为流式布局
			
			init();//页面初始化类
		}
		//自定义页面初始化方法
		private void init() {
			//设置盒的内部排列
			boxH = Box.createVerticalBox();//水平盒式布局
			boxTwo = Box.createVerticalBox();//垂直盒式布局
			boxThree = Box.createVerticalBox();
			boxFour = Box.createHorizontalBox();
			boxFive = Box.createHorizontalBox();
			boxSix = Box.createHorizontalBox();
			//初始化文本框和标签控件
			themeT = new JTextField(act.getTheme(), 15);
			contentT = new JTextField(act.getContent(), 15);
			roomTypeT = new JTextField(act.getRoomType(), 15);
			containT = new JTextField(act.getContain()+"", 15);
			//设置显示文本框不可编辑
			themeT.setEditable(false);
			contentT.setEditable(false);
			roomTypeT.setEditable(false);
			containT.setEditable(false);
			
			remarkT = new JTextField();
			jButton = new JButton("提交");
			//添加提交监听事件
			jButton.addActionListener(this);
			//为标签设置文本
			themeL = new JLabel("活动主题");
			contentL = new JLabel("活动内容");
			roomTypeL = new JLabel("教室类型");
			containL = new JLabel("活动人数");
			checkL = new JLabel("审核意见");
			remarkL = new JLabel("备         注");
			//审核意见的单选按钮组
			group = new ButtonGroup();
			radioAgr = new JRadioButton("批准");
			radioAgr.setSelected(true);//设置默认选中按钮
			radioDis = new JRadioButton("拒绝");
			group.add(radioAgr);//单选按钮归组
			group.add(radioDis);
			//向垂直盒式布局添加标签
			boxTwo.add(Box.createVerticalStrut(5));
			boxTwo.add(themeL);
			boxTwo.add(Box.createVerticalStrut(10));
			boxTwo.add(contentL);
			boxTwo.add(Box.createVerticalStrut(10));
			boxTwo.add(roomTypeL);
			boxTwo.add(Box.createVerticalStrut(10));
			boxTwo.add(containL);
			boxTwo.add(Box.createVerticalStrut(10));
			boxTwo.add(checkL);
			boxTwo.add(Box.createVerticalStrut(10));
			boxTwo.add(remarkL);
			//向垂直盒添加文本内容标签
			boxThree.add(themeT);
			//boxTwo.add(Box.createVerticalStrut(10));
			boxThree.add(contentT);
			//boxTwo.add(Box.createVerticalStrut(10));
			boxThree.add(roomTypeT);
			//boxTwo.add(Box.createVerticalStrut(10));
			boxThree.add(containT);
			boxTwo.add(Box.createVerticalStrut(5));
				//	单选按钮添加到一个水平盒
			boxSix.add(radioAgr);
			boxSix.add(Box.createHorizontalStrut(20));
			boxSix.add(radioDis);
			
			boxThree.add(boxSix);
			boxTwo.add(Box.createVerticalStrut(5));
			boxThree.add(remarkT);
			
			//将标签和内容标签添加到一个水平盒
			boxFour.add(boxTwo);
			boxFour.add(Box.createHorizontalStrut(5));//水平支撑
			boxFour.add(boxThree);
			
			//按钮添加到水平盒
			boxFive.add(jButton);
			//内层盒添加到外层盒
			boxH.add(Box.createVerticalStrut(20));
			boxH.add(boxFour);
			boxH.add(Box.createVerticalStrut(13));
			boxH.add(boxFive);
			//外层盒添加到面板
			add(boxH);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//获取审核结果和备注
			String check = radioAgr.isSelected() ? "已批准" : "已拒绝";
			String remark = remarkT.getText();
			
			try {
				//更新数据库中的活动状态和备注信息
				DBHelper.UpdateState(remark, check, manager.getEmployeeID(), act.getActID());
				
				//获取申请人的邮箱地址
				String email = DBHelper.getApplyEmail(act.getApplicant());
				
				//向申请人发送邮件
				String message = "您好，您提交的活动申请已审核，请及时登录活动教室申请系统查看结果。邮件来自活动教室申请系统！";
				SendMail sendMail = new SendMail();
				sendMail.sendMail(email, message);
				
				//更新成功弹出消息提示框
				JOptionPane.showMessageDialog(new JFrame().getContentPane(), "审核完成，已通知申请人！", "提示框", JOptionPane.INFORMATION_MESSAGE);
				dispose();//关闭审核窗口
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
}
