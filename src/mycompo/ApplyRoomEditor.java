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
import model.ActList;
import model.Applicant;
import model.RoomInfo;

public class ApplyRoomEditor extends AbstractCellEditor implements TableCellEditor{

	private JPanel jPanel;
	private Button button;
	
	public ApplyRoomEditor(List<RoomInfo> list, ActList act, Applicant stu) {
		// TODO Auto-generated constructor stub
		initButton(list, act, stu);
		initPanel();
		jPanel.add(button, BorderLayout.CENTER);
	}

	private void initPanel() {
		// TODO Auto-generated method stub
		jPanel = new JPanel();
		jPanel.setLayout(new BorderLayout());
	}

	private void initButton(List<RoomInfo> list, ActList act, Applicant stu) {
		// TODO Auto-generated method stub
		button = new Button();
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//获取当前行的教室信息
				RoomInfo room = list.get(button.getRow());
				
				//获取所申请的时间段
				String time1 =null;//存储具体的时间段
				String time2 = null;//存储时间段在表中的列标题
				switch(button.getColumn()) {
				
					case 4 :
						time1 = "8:00-10:00";
						time2 = "time1";
						break;
					case 5 :
						time1 = "10:00-12:00";
						time2 = "time2";
						break;
					case 6 :
						time1 = "12:00-14:00";
						time2 = "time3";
						break;
					case 7 :
						time1 = "14:00-16:00";
						time2 = "time4";
						break;
					case 8 :
						time1 = "16:00-18:00";
						time2 = "time5";
						break;
					case 9 :
						time1 = "18:00-20:00";
						time2 = "time6";
						break;
					case 10 :
						time1 = "20:00-22:00";
						time2 = "time7";
						break;
					default :
						break;
				}
				
				//更新数据库并通知楼栋负责人
				try {
					//更新roominfo表的时间状态
					DBHelper.UpdateRoom(room.getBuildingID(), room.getRoomNum(), room.getDateNow(), time2);
					
					//向appliedroom表插入一条申请记录
					DBHelper.UpdateApplied(room.getBuildingID(), room.getRoomNum(), room.getDateNow(), time1, stu.getStuID(), act.getActID());
					
					//获得楼栋负责人邮箱地址
					String email = DBHelper.getBuildEmail(room.getBuildingID());
					
					//向楼栋负责人发送邮件
					String message = "您好，申请人"+stu.getStuName()+"，学号为"+stu.getStuID()+"申请了教室"+room.getBuildingID()+
							"-"+room.getRoomNum()+"，将在日期："+room.getDateNow()+"，时间："+
							time1+"使用，请及时做好准备工作。邮件来自活动教室申请系统！";
					SendMail sendMail = new SendMail();
					sendMail.sendMail(email, message);
					
					//提示消息
					JOptionPane.showMessageDialog(new JFrame().getContentPane(), "申请教室成功，已通知楼栋负责人！");
					button.setText("已申请");
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
		if (value.equals("0")) {
			button.setText("可申请");
		} else {
			button.setText("已申请");
//			button.setEnabled(false);
		}
		button.setRow(row);
		button.setColumn(column);
		return jPanel;
	}

}
