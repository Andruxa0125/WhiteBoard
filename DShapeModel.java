import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class DShapeModel implements Serializable {
	//private Rectangle conceptualBounds;
	private int x;
	private int y;
	private int height;
	private int width;
	private ArrayList<ModelListener> listeners = new ArrayList<>();
	private Color col;

	protected DShapeModel() {
		x = 0;
		y = 0;
		width = 0;
		height = 0;
		this.col = Color.GRAY;
	}

	/**
	 * Returns the conceptual bounds of the current model using a Rectangle.
	 * Called from DShape and indirectly by Canvas
	 */
	public Rectangle getBounds() {
		return new Rectangle(this.x, this.y, this.width, this.height);
	}

	/**
	 * Called whenever model changes. For all listeners, notifies that the 
	 * model has changed
	 */
	protected void notifyModelChanged() {
		for (ModelListener listener : listeners) {
			listener.modelChanged(this);
		}
	}

	public void addModelListener(ModelListener listener) {
		this.listeners.add(listener);
	}

	public void removeModelListener(ModelListener listener) {
		this.listeners.remove(listener);
	}
	
	public void deleteAllListeners(){
		this.listeners.clear();
	}

	public void setX(int x) {
		this.x = x;
		this.notifyModelChanged();
	}

	public int getX() {
		return this.x;
	}

	public void setY(int y) {
		this.y = y;
		this.notifyModelChanged();
	}

	public int getY() {
		return this.y;
	}

	public void setHeight(int height) {
		this.height = height;
		this.notifyModelChanged();
	}

	public int getHeight() {
		return this.height;
	}

	public void setWidth(int width) {
		this.width = width;
		this. notifyModelChanged();
	}

	public int getWidth() {
		return this.width;
	}

	public void setColor(Color c) {
		col = c;
		this.notifyModelChanged();
	}

	public Color getColor() {
		return col;
	}

}
