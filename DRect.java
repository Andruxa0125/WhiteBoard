import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DRect extends DShape {
	
	public DRect(DShapeModel model) {
		this.model = model;
	}

	public void draw(Graphics g) {
		g.setColor(model.getColor());
		g.fillRect(model.getX(), model.getY(), model.getWidth(), model.getHeight());
	}

}
