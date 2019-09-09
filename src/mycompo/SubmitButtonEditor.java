package mycompo;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import model.ActList;
import model.Applicant;
import ui.RoomApplyPage;

public class SubmitButtonEditor extends AbstractCellEditor implements TableCellEditor{

	private JPanel jPanel;
	private Button button;
	
	public SubmitButtonEditor(List<ActList> data, Applicant stu) {
		// TODO Auto-generated constructor stub
		initButton(data, stu);
		initPanel();
		jPanel.add(button, BorderLayout.CENTER);
	}

	private void initPanel() {
		// TODO Auto-generated method stub
		jPanel = new JPanel();
		jPanel.setLayout(new BorderLayout());
	}

	private void initButton(List<ActList> data, Applicant stu) {
		// TODO Auto-generated method stub
		button = new Button("申请");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ActList act = data.get(button.getRow());//传递按钮所在表行的数据
				RoomApplyPage roomApply = new RoomApplyPage(act, stu);
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
