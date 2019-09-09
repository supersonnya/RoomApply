package mycompo;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ApplyRoomRenderer implements TableCellRenderer{
	
	private JPanel jPanel;
	private Button button;
	
	public ApplyRoomRenderer() {
		// TODO Auto-generated constructor stub
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
		button = new Button();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// TODO Auto-generated method stub
		if (value.equals("0")) {
			button.setText("ø……Í«Î");
		} else {
			button.setText("“—…Í«Î");
//			button.setEnabled(false);
		}
		return jPanel;
	}

}
