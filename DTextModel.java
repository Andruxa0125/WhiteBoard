import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

public class DTextModel extends DShapeModel {

	private String text;
	private String fontName;
	private int fontStyle;
	private int fontSize;
	private int index;

	public DTextModel() {
		super();
		index = 50;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		this.notifyModelChanged();
	}

	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
		this.notifyModelChanged();
	}

	public int getFontStyle() {
		return fontStyle;
	}

	public void setFontStyle(int fontStyle) {
		this.fontStyle = fontStyle;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int i) {
		index = i;
	}
}
