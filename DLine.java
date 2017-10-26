import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DLine extends DShape {

	DLineModel model;
	
	public DLine(DShapeModel model) {
		this.model = (DLineModel) model;
	}

	public DShapeModel getModel() {
		return model;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(model.getColor());
		g.drawLine(model.getP1X(), model.getP1Y(),
				model.getP2X(), model.getP2Y());
	}

	@Override
	public ArrayList<Point> getKnobs() {
		// TODO Auto-generated method stub
		ArrayList<Point> points = new ArrayList<Point>();
		Point left = model.getP1();
		Point right = model.getP2();
		points.add(left);
		points.add(right);
		return points;
	}

	@Override
	public void drawKnobs(Graphics g) {
		Graphics2D g2d = (Graphics2D) (g);
		g2d.setColor(Color.BLACK);

		ArrayList<Point> knobs = getKnobs();

		Point leftKnob = knobs.get(0);
		Point rightKnob = knobs.get(1);

		Rectangle2D left;
		left = new Rectangle2D.Double(leftKnob.getX() - DShape.KNOBSIZE / 2, leftKnob.getY() - DShape.KNOBSIZE / 2,
				DShape.KNOBSIZE, DShape.KNOBSIZE);

		Rectangle2D right;
		right = new Rectangle2D.Double(rightKnob.getX() - DShape.KNOBSIZE / 2,
				rightKnob.getY() - DShape.KNOBSIZE / 2, DShape.KNOBSIZE, DShape.KNOBSIZE);

		g2d.fill(left);
		g2d.fill(right);
	}
	
	public Rectangle getBounds() {
		return model.getBounds();
	}
}
