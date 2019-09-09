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

import ui.ActCheckPage;
import model.ActList;
import model.TeachingManager;

public class CheckButtonEditor extends AbstractCellEditor implements TableCellEditor{

	private JPanel jPanel;
	private Button button;
	
	public CheckButtonEditor(List<ActList> data, TeachingManager manager) {//���ݻ�����б��ı����ݺ����
		// TODO Auto-generated constructor stub
		initButton(data, manager);
		initPanel();
		jPanel.add(button, BorderLayout.CENTER);
	}

	private void initPanel() {
		// TODO Auto-generated method stub
		jPanel = new JPanel();
		jPanel.setLayout(new BorderLayout());
	}

	private void initButton(List<ActList> data, TeachingManager manager) {
		// TODO Auto-generated method stub
		button = new Button("���");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ActList act = data.get(button.getRow());//���ݰ�ť���ڱ��е�����
				ActCheckPage actCheck = new ActCheckPage(act, manager);//�������ݵ�����ҳ��
				button.setText("�����");
				button.setEnabled(false);
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