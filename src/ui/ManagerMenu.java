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

import model.TeachingManager;

public class ManagerMenu extends JFrame{

	JMenuBar menuBar;//菜单条
	JMenu menu;//菜单
	JMenu menuInfo;//修改信息菜单
	JMenu exit;//退出菜单
	JMenuItem item1;//菜单项
	JMenuItem item2;
	JMenuItem item3;
	JMenuItem item4;
	
	TeachingManager manager;//当前登录的教务管理员
	
	JPanel jPanel;//中央面板
	
	public ManagerMenu(String s, TeachingManager manager) {
		
		this.manager = manager;
		init(s);
		setBounds(400, 100, 600, 600);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	void init(String s) {
		
		setTitle(s);//设置窗口标题
		setLayout(new BorderLayout());
		menuBar = new JMenuBar();//创建菜单条
		menu = new JMenu("菜单栏目");//创建菜单
		menuInfo = new JMenu("修改信息");
		exit = new JMenu("退出系统");
		item1 = new JMenuItem("活动申请列表");//添加菜单项
		item2 = new JMenuItem("个人信息");
		item3 = new JMenuItem("登录密码");
		item4 = new JMenuItem("退出系统");

		jPanel = new JPanel();//初始化中央区面板
		
		//待审核活动列表监听事件
		item1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jPanel.removeAll();//清空中央面板中的组件
				ActApplyListPage applyList = new ActApplyListPage(manager);
				jPanel.add(applyList, BorderLayout.CENTER);
				validate();//刷新窗口
			}
		});
		//修改个人信息监听
		item2.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jPanel.removeAll();//清空中央面板中的组件
				InfoChangePage infoChange = new InfoChangePage(manager);
				jPanel.add(infoChange, BorderLayout.CENTER);
				validate();//刷新窗口
			}
		});
		
		//修改登录密码
		item3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jPanel.removeAll();//清空中央面板中的组件
				PasswordChangePage pwdChange = new PasswordChangePage(manager);
				jPanel.add(pwdChange, BorderLayout.CENTER);
				validate();//刷新窗口
			}
		});
		
		//退出系统监听
		item4.addActionListener(new ActionListener() {
			
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
		
		//菜单项添加到菜单
		menu.add(item1);
		menuInfo.add(item2);
		menuInfo.add(item3);
		exit.add(item4);
		//菜单添加到菜单条
		menuBar.add(menu);
		menuBar.add(menuInfo);
		menuBar.add(exit);
		//将菜单条放置到窗口
		setJMenuBar(menuBar);
		add(jPanel, BorderLayout.CENTER);//中央面板添加到center区
	}
}
