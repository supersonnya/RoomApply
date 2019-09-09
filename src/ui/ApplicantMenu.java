package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Applicant;

public class ApplicantMenu extends JFrame {

	JMenuBar menuBar;//菜单条
	JMenu menu;//菜单
	JMenu menuInfo;//修改信息菜单
	JMenu exit;//退出菜单
	JMenuItem item1;//菜单项
//	JMenuItem item2;
	JMenuItem item3;
	JMenuItem item4;
	JMenuItem item5;
	JMenuItem item6;
	JMenuItem item7;
	JPanel jPanel;//中央面板
	
	Applicant stu;//登录用户对象
	
	public ApplicantMenu(String s, Applicant stu) {
		
		this.stu = stu;
		init(s);
		setBounds(400, 100, 600, 600);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	void init(String s) {
		
		setTitle(s);//设置窗口标题
		setLayout(new BorderLayout());
		menuBar = new JMenuBar();//创建菜单条
		menu = new JMenu("菜单");//创建菜单
		menuInfo = new JMenu("修改信息");
		exit = new JMenu("退出系统");
		item1 = new JMenuItem("活动申请");//添加菜单项
//		item2 = new JMenuItem("教室申请");
		item3 = new JMenuItem("已申请活动");
		item4 = new JMenuItem("已申请教室");
		item5 = new JMenuItem("个人信息");
		item6 = new JMenuItem("登录密码");
		item7 = new JMenuItem("退出系统");
		
		jPanel = new JPanel();//初始化中央区面板
		
		//点击进入活动申请页面
		item1.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jPanel.removeAll();//清空中央面板中的组件
				ActApplyPage actApply = new ActApplyPage(stu);
				jPanel.add(actApply, BorderLayout.CENTER);
				validate();//刷新窗口
			}
		});
//		item2.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				jPanel.removeAll();//清空中央面板中的组件
//				RoomApplyPage roomApply = new RoomApplyPage();
//				jPanel.add(roomApply, BorderLayout.CENTER);
//				validate();//刷新窗口
//			}
//		});
		//点击触发事件显示已提交活动列表
		item3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jPanel.removeAll();//清空中央面板中的组件
				ActSubmitListPage submitList = new ActSubmitListPage(stu);
				jPanel.add(submitList, BorderLayout.CENTER);
				validate();//刷新窗口
			}
		});
		//点击触发事件显示已申请的教室列表
		item4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jPanel.removeAll();//清空中央面板中的组件
				AppliedRoomListPage appliedRoom = new AppliedRoomListPage(stu);
				jPanel.add(appliedRoom, BorderLayout.CENTER);
				validate();//刷新窗口
			}
		});
		//点击显示个人信息修改界面
		item5.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jPanel.removeAll();//清空中央面板中的组件
				InfoChangeApply infoChange = new InfoChangeApply(stu);
				jPanel.add(infoChange, BorderLayout.CENTER);
				validate();//刷新窗口
			}
		});
		//点击触发事件显示修改登录密码界面
		item6.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jPanel.removeAll();//清空中央面板中的组件
				PasswordChangeApply pwdChange = new PasswordChangeApply(stu);
				jPanel.add(pwdChange, BorderLayout.CENTER);
				validate();//刷新窗口
			}
		});
		//点击出发退出系统事件
		item7.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int n = JOptionPane.showConfirmDialog(null, "确认退出");
				if(n == JOptionPane.YES_OPTION) {
					dispose();//退出系统
				}else {
					validate();//刷新界面
				}
			}
		});
		menu.add(item1);//将菜单项添加到菜单
//		menu.add(item2);
		menu.add(item3);
		menu.add(item4);
		
		menuInfo.add(item5);
		menuInfo.add(item6);
		
		exit.add(item7);
		//菜单添加到菜单条
		menuBar.add(menu);
		menuBar.add(menuInfo);
		menuBar.add(exit);
		
		setJMenuBar(menuBar);//将菜单条放置到窗口
		add(jPanel, BorderLayout.CENTER);//中央面板添加到center区
	}
}
