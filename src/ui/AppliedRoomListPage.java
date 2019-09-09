package ui;

import java.awt.FlowLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import dcl.DBHelper;
import model.Applicant;
import model.AppliedRoom;
import mycompo.AppliedRoomEditor;
import mycompo.AppliedRoomRenderer;

public class AppliedRoomListPage extends JPanel{
	
	//定义组件变量
	JTable jTable;//表格组件
	Vector<String> name;//表列名
	Vector<Vector<String>> data;//表内容
	Box boxH;//行式盒
	Box boxOne, boxTwo, boxThree;//列式盒
	JLabel title;//标题
	List<AppliedRoom> list;//活动列表，用于接收数据库的活动列表
//	JButton jButton1, jButton2;//翻页按钮
	//构造方法
	public AppliedRoomListPage(Applicant stu) {
		// TODO Auto-generated constructor stub
		setLayout(new FlowLayout());
		init(stu);
	}
	private void init(Applicant stu) {
		// TODO Auto-generated method stub
		boxH = Box.createVerticalBox();//水平盒式布局
		boxOne = Box.createHorizontalBox();//垂直盒式布局
		boxTwo = Box.createHorizontalBox();
		boxThree = Box.createHorizontalBox();

		//初始化控件
		title = new JLabel("已申请教室列表");
		name = new Vector<String>();
		data = new Vector<Vector<String>>();
		name.add("教室编号");
		name.add("使用日期");
		name.add("使用时间");
		name.add("撤销申请");
		
//		jButton1 = new JButton("上一页");
//		jButton2 = new JButton("下一页");
		
		list = new ArrayList<AppliedRoom>();//初始化活动列表
		try {
			//获取活动列表
			list = DBHelper.AppliedRoom(stu);
			//遍历活动列表获取vector所需的
			for (int i = 0; i < list.size(); i++) {

				AppliedRoom room = list.get(i);//用于构造vector数据内容的辅助对象

				//数据表中的每一行对应一个
				Vector<String> vec = new Vector<String>();
				vec.add(room.getBuildingId()+"-"+room.getRoomNum());
				vec.add(room.getNeedDate());
				vec.add(room.getUseTime());
				data.add(vec);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//创建表
		jTable = new JTable(data, name);
		//向表格中添加撤销按钮
		jTable.getColumnModel().getColumn(3).setCellEditor(new AppliedRoomEditor(list, stu));
		jTable.getColumnModel().getColumn(3).setCellRenderer(new AppliedRoomRenderer());
		jTable.setRowSelectionAllowed(false);
		
		boxOne.add(title);
		boxTwo.add(new JScrollPane(jTable));//显示表的列标题
//		boxThree.add(jButton1);
//		boxThree.add(Box.createHorizontalStrut(20));
//		boxThree.add(jButton2);
		
		boxH.add(Box.createVerticalStrut(5));
		boxH.add(boxOne);
		boxH.add(Box.createVerticalStrut(25));
		boxH.add(boxTwo);
//		boxH.add(Box.createVerticalStrut(10));
//		boxH.add(boxThree);
		add(boxH);
	}

}
