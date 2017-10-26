import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class DShape implements Serializable, ModelListener {
	DShapeModel model;
	final protected static int KNOBSIZE = 9;

	private Canvas canvas;

	public abstract void draw(Graphics g);

	public ArrayList<Point> getKnobs() {
		ArrayList<Point> points = new ArrayList<Point>();
		Point topLeft = new Point(model.getX(), model.getY());
		Point bottomRight = new Point(model.getX() + model.getWidth(),
				model.getY() + model.getHeight());
		Point bottomLeft = new Point(model.getX(),
				model.getY() + model.getHeight());
		Point topRight = new Point(model.getX() + model.getWidth(), model.getY());
		points.add(topLeft);
		points.add(topRight);
		points.add(bottomLeft);
		points.add(bottomRight);
		return points;
	};

	public void drawKnobs(Graphics g) {
		Graphics2D g2d = (Graphics2D) (g);
		g2d.setColor(Color.BLACK);

		ArrayList<Point> knobs = getKnobs();

		Point topLeft = knobs.get(0);
		Point topRight = knobs.get(1);
		Point bottomLeft = knobs.get(2);
		Point bottomRight = knobs.get(3);

		Rectangle2D topLeftRect;
		topLeftRect = new Rectangle2D.Double(topLeft.getX() - KNOBSIZE / 2,
				topLeft.getY() - KNOBSIZE / 2, KNOBSIZE, 9);

		Rectangle2D topRightRect;
		topRightRect = new Rectangle2D.Double(topRight.getX() - KNOBSIZE / 2,
				topRight.getY() - KNOBSIZE / 2, KNOBSIZE, KNOBSIZE);

		Rectangle2D bottomLeftRect;
		bottomLeftRect = new Rectangle2D.Double(bottomLeft.getX() - KNOBSIZE / 2,
				bottomLeft.getY() - KNOBSIZE / 2, KNOBSIZE, KNOBSIZE);

		Rectangle2D bottomRightRect;
		bottomRightRect = new Rectangle2D.Double(
				bottomRight.getX() - KNOBSIZE / 2,
				bottomRight.getY() - KNOBSIZE / 2, KNOBSIZE, KNOBSIZE);

		g2d.fill(topLeftRect);
		g2d.fill(topRightRect);
		g2d.fill(bottomLeftRect);
		g2d.fill(bottomRightRect);
	}

	@Override
	public void modelChanged(DShapeModel model) {
		this.canvas.repaint();
	};

	public DShapeModel getModel() {
		return this.model;
	};

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public Rectangle getBounds() {
		return model.getBounds();
	}

}
