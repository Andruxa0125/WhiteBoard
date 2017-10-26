import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DOval extends DShape {

	public DOval(DShapeModel model) {
		this.model = model;
	}

	public void draw(Graphics g) {
		// super.paintComponent(g); not sure if we need this line at all.
		g.setColor(model.getColor());
		g.fillOval(model.getX(), model.getY(), model.getWidth(),
				model.getHeight());
	}
	
}
