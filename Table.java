import javax.swing.table.AbstractTableModel;

public class Table extends AbstractTableModel implements ModelListener {
	private Canvas canvas;
	public static final int COLS = 4;
	public static final int X_COL = 0;
	public static final int Y_COL = 1;
	public static final int WIDTH_COL = 2;
	public static final int HEIGHT_COL = 3;

	public Table(Canvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public int getRowCount() {
		return canvas.getShapeModelArray().size();
	}

	@Override
	public int getColumnCount() {
		return COLS;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		DShapeModel model = canvas.getShapeModelArray().get(rowIndex);
		if (columnIndex == X_COL)
			return model.getX();
		if (columnIndex == Y_COL)
			return model.getY();
		if (columnIndex == WIDTH_COL)
			return model.getWidth();
		if (columnIndex == HEIGHT_COL)
			return model.getHeight();
		return null;
	}
	
	@Override
	public String getColumnName(int column) {
		if (column == X_COL)
			return "X";
		if (column == Y_COL)
			return "Y";
		if (column == WIDTH_COL)
			return "WIDTH";
		if (column == HEIGHT_COL)
			return "HEIGHT";
		return null;
	}

	@Override
	public void modelChanged(DShapeModel model) {
		for (int i = 0; i < this.canvas.getShapeModelArray().size(); i++) {
			if (model == this.canvas.getShapeModelArray().get(i))
				fireTableRowsUpdated(i, i);
		}
	}
}
