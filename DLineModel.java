import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

public class DLineModel extends DShapeModel {
	private Point p1;
	private Point p2;

	public DLineModel() {

	}

	/*
	 * Returns the conceptual bounds of the current model using a Rectangle.
	 * Called from DShape and indirectly by Canvas
	 */
	@Override
	public Rectangle getBounds() {
		return new Rectangle(Math.min(p1.x, p2.x), Math.min(p1.y, p2.y),
				Math.abs(p1.x - p2.x), Math.abs(p1.y - p2.y));
	}

	public Point getP1() {
		return p1;
	}

	public int getP1X() {
		return p1.x;
	}

	public void setP1X(int x) {
		p1.x = x;
		this.notifyModelChanged();
	}

	public int getP1Y() {
		return p1.y;
	}

	public void setP1Y(int y) {
		p1.y = y;
		this.notifyModelChanged();
	}

	public void setP1(Point point) {
		this.p1 = point;
		this.notifyModelChanged();
	}

	public Point getP2() {
		return p2;
	}

	public int getP2X() {
		return p2.x;
	}

	public void setP2X(int x) {
		p2.x = x;
		this.notifyModelChanged();
	}

	public int getP2Y() {
		return p2.y;
	}

	public void setP2Y(int y) {
		p2.y = y;
		this.notifyModelChanged();
	}

	public void setP2(Point point) {
		this.p2 = point;
		this.notifyModelChanged();
	}

}