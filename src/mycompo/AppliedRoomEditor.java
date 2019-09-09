package mycompo;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import bll.SendMail;
import dcl.DBHelper;
import model.Applicant;
import model.AppliedRoom;
import model.RoomInfo;

public class AppliedRoomEditor extends AbstractCellEditor implements TableCellEditor{

	private JPanel jPanel;
	private Button button;
	List<AppliedRoom> list;
	Applicant stu;
	
	public AppliedRoomEditor(List<AppliedRoom> list, Applicant stu) {
		// TODO Auto-generated constructor stub
		this.list = list;
		this.stu = stu;
		initButton();
		initPanel();
		jPanel.add(button, BorderLayout.CENTER);
	}

	private void initPanel() {
		// TODO Auto-generated method stub
		jPanel = new JPanel();
		jPanel.setLayout(new BorderLayout());
	}

	private void initButton() {
		// TODO Auto-generated method stub
		button = new Button("撤销");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//获取当前行的教室信息
				AppliedRoom room = list.get(button.getRow());
				
				//获取所申请的时间段
				String time1 =null;//存储具体的时间段
				switch(room.getUseTime()) {
				
					case "8:00-10:00" :
						time1 = "time1";
						break;
					case "10:00-12:00" :
						time1 = "time2";
						break;
					case "12:00-14:00" :
						time1 = "time3";
						break;
					case "14:00-16:00" :
						time1 = "time4";
						break;
					case "16:00-18:00" :
						time1 = "time5";
						break;
					case "18:00-20:00" :
						break;
					case "20:00-22:00" :
						time1 = "time7";
						break;
					default :
						break;
				}
				
				
				//更新数据库并通知楼栋负责人
				try {
					//更新roominfo表的时间状态
					DBHelper.UndoRoom(room.getBuildingId(), room.getRoomNum(), room.getNeedDate(), time1);
					
					//向appliedroom表插入一条申请记录
					DBHelper.UndoApplied(room.getId(), room.getActId());
					
					//获得楼栋负责人邮箱地址
					String email = DBHelper.getBuildEmail(room.getBuildingId());
					
					//向楼栋负责人发送邮件
					String message = "您好，申请人"+stu.getStuName()+"，学号为"+stu.getStuID()+"撤销了其申请的教室："+room.getBuildingId()+
							"-"+room.getRoomNum()+"，使用日期："+room.getNeedDate()+"，使用时间："+
							room.getUseTime()+"使用。邮件来自活动教室申请系统！";
					SendMail sendMail = new SendMail();
					sendMail.sendMail(email, message);
					
					//提示消息
					JOptionPane.showMessageDialog(new JFrame().getContentPane(), "申请撤销成功，已通知楼栋负责人！");
					button.setText("已撤销");
					button.setEnabled(false);
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		button.setRow(row);
		button.setColumn(column);
		return jPanel;
	}

}
