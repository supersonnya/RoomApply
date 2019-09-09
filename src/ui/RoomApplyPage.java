package ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import dcl.DBHelper;
import model.ActList;
import model.Applicant;
import model.RoomInfo;
import mycompo.ApplyRoomEditor;
import mycompo.ApplyRoomRenderer;

public class RoomApplyPage extends JFrame{
	
	//定义组件变量
	JTable jTable;//表格组件
	Vector<String> name;//表列名
	Vector<Vector<String>> data;//表内容
	Box boxH;//行式盒
	Box boxOne, boxTwo, boxThree;//列式盒
	JLabel title;//标题
//	JButton jButton1, jButton2;//翻页按钮
	JButton close;//关闭页面按钮
	JScrollPane scrollPane;
	List<RoomInfo> list;//活动列表，用于接收数据库的活动列表
	
	//构造方法
	public RoomApplyPage(ActList act, Applicant stu) {
		// TODO Auto-generated constructor stub

		setLayout(new FlowLayout());
		setVisible(true);
		setBounds(200, 150, 1000, 500);
		init(act, stu);//活动记录对象, 和登录申请人对象
	}
	private void init(ActList act, Applicant stu) {
		// TODO Auto-generated method stub
		boxH = Box.createVerticalBox();//水平盒式布局
		boxOne = Box.createHorizontalBox();//垂直盒式布局
		boxTwo = Box.createHorizontalBox();
		boxThree = Box.createHorizontalBox();

		//初始化控件
		title = new JLabel("可申请教室列表");
		
//		jButton1 = new JButton("上一页");
//		jButton2 = new JButton("下一页");
		close = new JButton("关闭页面");
		close.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		
		name = new Vector<String>();//初始化列标题向量
		data = new Vector<Vector<String>>();//初始化内容向量
		name.add("教室编号");//添加列标题
		name.add("日        期");
		name.add("教室类型");
		name.add("容纳人数");
		name.add("8:00-10:00");
		name.add("10:00-12:00");
		name.add("12:00-14:00");
		name.add("14:00-16:00");
		name.add("16:00-18:00");
		name.add("18:00-20:00");
		name.add("20:00-22:00");
		
		list = new ArrayList<RoomInfo>();//初始化活动列表
		try {
			//获取活动列表
			list = DBHelper.RoomInfo();
			//遍历活动列表获取vector所需的
			for (int i = 0; i < list.size(); i++) {

				RoomInfo room = list.get(i);//用于构造vector数据内容的辅助对象

				//数据表中的每一行对应一个
				Vector<String> vec = new Vector<String>();
				vec.add(room.getBuildingID()+"-"+room.getRoomNum());
				vec.add(room.getDateNow());
				vec.add(room.getRoomType());
				vec.add(room.getContain()+"");
				vec.add(room.getTime1());
				vec.add(room.getTime2());
				vec.add(room.getTime3());
				vec.add(room.getTime4());
				vec.add(room.getTime5());
				vec.add(room.getTime6());
				vec.add(room.getTime7());
				data.add(vec);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		jTable = new JTable(data, name);
		for (int i = 4; i < 11; i++) {
			jTable.getColumnModel().getColumn(i).setCellEditor(new ApplyRoomEditor(list, act, stu));
			jTable.getColumnModel().getColumn(i).setCellRenderer(new ApplyRoomRenderer());
			jTable.setRowSelectionAllowed(false);
		}
		
		
		scrollPane = new JScrollPane(jTable) {
			//重写方法设置滚动面板的大小
			@Override
			public Dimension getPreferredSize() {
				// TODO Auto-generated method stub
				return new Dimension(900, 350);
			}
			
		};//jTable作为一个组件视图，在构造方法中完成
//		scrollPane.setSize(900, 450);添加table后使用无效，未添加table时使用无法显示标题
		boxOne.add(title);
		boxTwo.add(scrollPane);//显示表的列标题
		boxThree.add(close);
//		boxThree.add(jButton1);
//		boxThree.add(Box.createHorizontalStrut(20));
//		boxThree.add(jButton2);
		//内层盒添加到外层
		boxH.add(Box.createVerticalStrut(5));
		boxH.add(boxOne);//标题
		boxH.add(Box.createVerticalStrut(25));
		boxH.add(boxTwo);//数据表
		boxH.add(Box.createVerticalStrut(10));
		boxH.add(boxThree);//关闭按钮
		add(boxH);
	}

}
