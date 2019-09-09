package mycompo;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class SubmitButtonRenderer implements TableCellRenderer{
	
	private JPanel jPanel;
	private Button button;
	
	public SubmitButtonRenderer() {
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
		button = new Button("…Í«Î");
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// TODO Auto-generated method stub
		return jPanel;
	}

}
