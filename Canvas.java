import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Painter;
import java.awt.Rectangle;

public class Canvas extends JPanel {
	private ArrayList<DShape> shapesArray = new ArrayList<>();
	private Table shapeTable = new Table(this);
	private ArrayList<Point> selectedKnobs = new ArrayList<>();
	private DShape selectedShape;
	// Below two variables keep track of the currently selected x and y to compare
	// with the new that is made when mouse is dragged
	private int selectedX;
	private int selectedY;

	private Point movingKnob;
	private Point anchorKnob;

	public Canvas(WhiteBoard whiteboard) {

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				selectedX = e.getX();
				selectedY = e.getY();
				

				if (selectedShape != null) {
					ArrayList<Point> knobPoints = selectedShape.getKnobs();
<<<<<<< HEAD
					if (knobPoints.size() == 4) { // moving all rectangle bounds
						for (Point point : knobPoints) {
							if ((e.getX() <= (point.getX() + DShape.KNOBSIZE / 2))
									&& (e.getX() >= (point.getX() - DShape.KNOBSIZE / 2))
									&& (e.getY() <= (point.getY() + DShape.KNOBSIZE / 2))
									&& (e.getY() >= (point.getY() - DShape.KNOBSIZE / 2))) {
								movingKnob = point;

								if (knobPoints.get(0).equals(movingKnob)) {
									anchorKnob = knobPoints.get(3);
									System.out.println("Knob 0");
								} else if (knobPoints.get(1).equals(movingKnob)) {
									anchorKnob = knobPoints.get(2);
									System.out.println("Knob 1");
								} else if (knobPoints.get(2).equals(movingKnob)) {
									anchorKnob = knobPoints.get(1);
									System.out.println("Knob 2");
								} else if (knobPoints.get(3).equals(movingKnob)) {
									anchorKnob = knobPoints.get(0);
									System.out.println("Knob 3");
								}
								
								else { // moving DLine
									for (Point point2 : knobPoints) {

										if ((e.getX() <= (point2.x + DShape.KNOBSIZE / 2))
												&& (e.getX() >= (point2.x - DShape.KNOBSIZE / 2))
												&& (e.getY() <= (point2.y + DShape.KNOBSIZE / 2))
												&& (e.getY() >= (point2.y - DShape.KNOBSIZE / 2))) {
											movingKnob = point2;
										}
										if (knobPoints.get(0).equals(movingKnob)) {
											anchorKnob = knobPoints.get(1);
										} else if (knobPoints.get(1).equals(movingKnob)) {
											anchorKnob = knobPoints.get(0);
										}
									}

								}
								return;
=======
					// moving all
					// rectangle bounds

					for (Point point : knobPoints) {
						if ((e.getX() <= (point.getX() + DShape.KNOBSIZE / 2))
								&& (e.getX() >= (point.getX() - DShape.KNOBSIZE / 2))
								&& (e.getY() <= (point.getY() + DShape.KNOBSIZE / 2))
								&& (e.getY() >= (point.getY() - DShape.KNOBSIZE / 2))) {
							movingKnob = point;

							if (selectedShape instanceof DLine) {
								if (knobPoints.get(0).equals(movingKnob)) {
									anchorKnob = knobPoints.get(1);
									System.out.println("Knob 0");
								} else if (knobPoints.get(1).equals(movingKnob)) {
									anchorKnob = knobPoints.get(0);
									System.out.println("Knob 1");
								}
							}

							else {
								if (knobPoints.get(0).equals(movingKnob)) {
									anchorKnob = knobPoints.get(3);
									System.out.println("Knob 0");
								} else if (knobPoints.get(1).equals(movingKnob)) {
									anchorKnob = knobPoints.get(2);
									System.out.println("Knob 1");
								} else if (knobPoints.get(2).equals(movingKnob)) {
									anchorKnob = knobPoints.get(1);
									System.out.println("Knob 2");
								} else if (knobPoints.get(3).equals(movingKnob)) {
									anchorKnob = knobPoints.get(0);
									System.out.println("Knob 3");
								}

>>>>>>> 1624bbd928fa1c71e941cf7115f3d6561a109eb3
							}

							return;
						}

					}
				}

				anchorKnob = null;
				setSelectedShape(null);

				whiteboard.disableTextButtons();

				for (int i = shapesArray.size() - 1; i >= 0; i--) {
					DShape shape = shapesArray.get(i);
					Rectangle bounds = shape.getBounds();
<<<<<<< HEAD
=======

					
					
>>>>>>> 1624bbd928fa1c71e941cf7115f3d6561a109eb3
					if (bounds.contains(selectedX, selectedY)) {
						setSelectedShape(shape);
						selectedShape.drawKnobs(getGraphics());
						
						if (selectedShape instanceof DText) {
							whiteboard.enableTextButtons();
							
							
							DTextModel model = (DTextModel) selectedShape.getModel();
							
							whiteboard.fontSelect.setSelectedIndex(model.getIndex());
							whiteboard.inputField.setText(model.getText());
						}
						
						return;
					}
				}
			}
		});

		this.addMouseMotionListener(new MouseMotionListener() {
			@Override

			public void mouseDragged(MouseEvent e) {
				if (selectedShape == null)
					return;

				DShapeModel selectedModel = selectedShape.getModel();
				int originalX = selectedModel.getX();
				int originalY = selectedModel.getY();
				int xMoved = e.getX() - selectedX;
				int yMoved = e.getY() - selectedY;
				
				if (anchorKnob != null) {
					Point point = e.getPoint();
					ArrayList<Point> knobPoints = selectedShape.getKnobs();
					int dx = (int) (point.x - anchorKnob.getX());
					int dy = (int) (point.y - anchorKnob.getY());
					int currentX = (int) selectedModel.getX();
					int currentY = (int) selectedModel.getY();

					if (anchorKnob.equals(knobPoints.get(0))) {
						resize(point, selectedModel, currentX, currentY, dx, dy);
					} else if (anchorKnob.equals(knobPoints.get(1))) {
						resize(point, selectedModel, point.x, currentY, -dx, dy);
					} else if (anchorKnob.equals(knobPoints.get(2))) {
						resize(point, selectedModel, currentX, point.y, dx, -dy);
					} else if (anchorKnob.equals(knobPoints.get(3))) {
						resize(point, selectedModel, point.x, point.y, -dx, -dy);
					}
				}
				else {
					
					if (selectedShape instanceof DLine) {
						DLineModel model = (DLineModel) selectedModel;
						
						Point start = model.getP1();
						Point end = model.getP2();
						
						
						model.setP1X(start.x + xMoved);
						model.setP1Y(start.y + yMoved);
						model.setP2X(end.x + xMoved);
						model.setP2Y(end.y + yMoved);
					}
					
					else {
						selectedModel.setX(originalX + xMoved);
						selectedModel.setY(originalY + yMoved);
					}
				}
				selectedX = e.getX();
				selectedY = e.getY();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// Empty because no need to implement
			}
		});
	}

	public void resize(Point point, DShapeModel model, int x, int y, int width,
			int height) {
		if (model instanceof DTextModel) {
			if (width < 0) {
				model.setX(model.getX());
				model.setWidth(0);
			}
			else {
				model.setX(x);
				model.setWidth(width);
			}
			if (height < 0) {
				model.setY(model.getY());
				model.setHeight(0);
			}
			else {
				model.setY(y);
				model.setHeight(height);
			}

		}
		else {

			if (width < 0) {
				model.setX(x - Math.abs(width));
				model.setWidth(Math.abs((int) (anchorKnob.getX() - point.x)));
			}
			else {
				model.setX(x);
				model.setWidth(width);
			}
			if (height < 0) {
				model.setY(y - Math.abs(height));
				model.setHeight(Math.abs((int) (anchorKnob.getY() - point.y)));
			}
			else {
				model.setY(y);
				model.setHeight(height);
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (DShape shape : shapesArray) {
			shape.draw(g);
		}
		if (selectedShape != null)
			selectedShape.drawKnobs(g);
	}

	public void addShape(DShapeModel shapeModel) {
		if (shapeModel instanceof DRectModel) {
			DRect rect = new DRect(shapeModel);
			this.setSelectedShape(rect);
		}

		if (shapeModel instanceof DOvalModel) {
			DOval oval = new DOval(shapeModel);
			this.setSelectedShape(oval);
		}

		if (shapeModel instanceof DLineModel) {
			DLine line = new DLine(shapeModel);
			this.setSelectedShape(line);
		}

		if (shapeModel instanceof DTextModel) {
			DText text = new DText(shapeModel);
			this.setSelectedShape(text);
		}
		// Shape now listens to its model for changes
		shapesArray.add(this.getSelectedShape());
		this.selectedShape.getModel().addModelListener(selectedShape);
		this.selectedShape.getModel().addModelListener(shapeTable);
		this.selectedShape.setCanvas(this);
		repaint();
		shapeTable.fireTableDataChanged();
	}

	void makeThisArrayCurrent(ArrayList<DShapeModel> ar) {
		for (DShapeModel m : ar) {
			this.addShape(m);
		}
	}

	public void setSelectedShape(DShape shape) {
		this.selectedShape = shape;
		repaint();

		if (shape == null)
			System.out.println("No current selected shape");
		else
			System.out.println("A new shape has been selected");

	}

	public DShape getSelectedShape() {
		return selectedShape;
	}

	public void deleteSelectedShape() {
		if (this.selectedShape == null)
			return;
		this.selectedShape.getModel().deleteAllListeners();
		this.shapesArray.remove(selectedShape);
		this.setSelectedShape(null);
		repaint();
		shapeTable.fireTableDataChanged();
	}

	public void sendSelectedToFront() {
		this.shapesArray.remove(this.selectedShape);
		this.shapesArray.add(this.selectedShape);
		repaint();
		shapeTable.fireTableDataChanged();
	}

	public void sendSelectedToBack() {
		this.shapesArray.remove(this.selectedShape);
		this.shapesArray.add(0, this.selectedShape);
		repaint();
		shapeTable.fireTableDataChanged();
	}

	// CHANGED
	public ArrayList<DShapeModel> getShapeModelArray() 
	{
		ArrayList<DShapeModel> tempArray = new ArrayList<DShapeModel>();
		for (DShape d : shapesArray) {
			tempArray.add(d.getModel());
		}
		return tempArray;
	}
	
	/**
	 * Used so that Whiteboard can utilize Table Model
	 * @return
	 */
	public Table getTable(){
		return this.shapeTable;
	}

	void clean() {
		for (int i = 0; i < shapesArray.size(); i++) {
			shapesArray.remove(i);
		}
	}
}
