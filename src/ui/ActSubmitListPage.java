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
import model.ActList;
import model.Applicant;
import mycompo.SubmitButtonEditor;
import mycompo.SubmitButtonRenderer;

public class ActSubmitListPage extends JPanel{

		//定义组件变量
		JTable jTable;//表格组件
		Vector<String> name;//表列名
		Vector<Vector<String>> data;//表内容
		Box boxH;//行式盒
		Box boxOne, boxTwo, boxThree;//列式盒
		JLabel title;//标题
		List<ActList> list;//活动列表，用于接收数据库的活动列表
//		JButton jButton1, jButton2;//翻页按钮
		//构造方法
		public ActSubmitListPage(Applicant stu) {
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
//			jButton1 = new JButton("上一页");
//			jButton2 = new JButton("下一页");
			title = new JLabel("已提交活动列表");
			name = new Vector<String>();//初始化列标题向量
			data = new Vector<Vector<String>>();//初始化内容向量
//			name.add("活动编号");//添加列标题
			name.add("活动主题");
			name.add("教室类型");
			name.add("活动人数");
			name.add("活动状态");
			name.add("申请教室");
			
			list = new ArrayList<ActList>();//初始化活动列表
			try {
				//获取活动列表
				list = DBHelper.ActRoom(stu);
				//遍历活动列表获取vector所需的
				for (int i = 0; i < list.size(); i++) {

					ActList act = list.get(i);//用于构造vector数据内容的辅助对象
					//控制列表中不显示已审核的活动项目
//					if(!act.getState().equals("提交")) {
//						continue;
//					}
					
					Vector<String> vec = new Vector<String>();//数据表中的每一行对应一个
					
//					vec.add(rs.getString(1).toString());
					vec.add(act.getTheme());
					vec.add(act.getRoomType());
					vec.add(act.getContain()+"");
					vec.add(act.getState());
					data.add(vec);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			jTable = new JTable(data, name);//初始化表
			//为单元格添加按钮
			jTable.getColumnModel().getColumn(4).setCellEditor(new SubmitButtonEditor(list, stu));
			jTable.getColumnModel().getColumn(4).setCellRenderer(new SubmitButtonRenderer());
			jTable.setRowSelectionAllowed(false);
			
			boxOne.add(title);//显示列表名称
			boxTwo.add(new JScrollPane(jTable));//显示表的列标题
//			boxThree.add(jButton1);
//			boxThree.add(Box.createHorizontalStrut(20));
//			boxThree.add(jButton2);
			//内层盒添加到外层盒
			boxH.add(Box.createVerticalStrut(10));
			boxH.add(boxOne);//标题
			boxH.add(Box.createVerticalStrut(25));
			boxH.add(boxTwo);//数据表
//			boxH.add(Box.createVerticalStrut(10));
//			boxH.add(boxThree);//关闭按钮
			add(boxH);
		}

}
