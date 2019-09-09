package mycompo;

import javax.swing.JButton;

public class Button extends JButton{

	private int row;
	private int column;
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}

	public Button() {
		// TODO Auto-generated constructor stub
	}
	public Button(String name) {
		super(name);
	}

}
