/**
 * Represents a drawable moving object.
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

public class Drawable {

	// Position of the object relative to the drawing canvas.
	protected Pair drawingPosition;
	// Size of the object.
	protected int r;
	// Sense area.
	protected int range;
	protected Color baseColor, arrowColor, rangeColor;
	// True false if the agent carries objects.
	protected boolean isFull;

	// Scale factor between the real and the drawing space.
	public int scaleFactor = 0;

	/**
	 * Constructor
	 * 
	 * @param p
	 *            Drawing position of the object.
	 * @param r
	 *            Size of the object.
	 * @param range
	 *            Sensing range of the object.
	 * @param worldSize
	 *            Size of the world.
	 * @param baseC
	 *            Base color of the object.
	 * @param arrowC
	 *            Arrow color of the object.
	 * @param rangeC
	 *            Color of the range.
	 */
	public Drawable(Pair p, int r, int range, int worldSize, Color baseC,
			Color arrowC, Color rangeC) {
		scaleFactor = Constants.CANVAS_SIZE / worldSize;

		drawingPosition = toDrawingPosition(p);
		this.r = r;
		this.range = toDrawingRange(range);
		baseColor = baseC;
		arrowColor = arrowC;
		rangeColor = rangeC;
		isFull = false;
	}

	/**
	 * Draw the object
	 * 
	 * @param g
	 *            Graphics context.
	 */
	public void draw(Graphics g) {
		AffineTransform tr = new AffineTransform();
		Graphics2D g2d = (Graphics2D) g;

		tr.translate(drawingPosition.getI(), drawingPosition.getJ());
		g2d.setTransform(tr);

		g2d.setColor(baseColor);
		g2d.fillOval(-r, -r, 2 * r, 2 * r);
		if (isFull) {
			g2d.setColor(Constants.OBJECT_COLOR);
			g2d.fillOval(-r / 2, -r / 2, r, r);
		}

		if (!arrowColor.equals(Constants.NOTVISIBLE_COLOR)) {
			g2d.setColor(rangeColor);
			g2d.drawOval(-range, -range, 2 * range, 2 * range);

		}

		try {
			g2d.setTransform(tr.createInverse());
		} catch (NoninvertibleTransformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Transform the current position to the drawing space.
	 * 
	 * @param p
	 *            Position in actual space.
	 * @return Position in drawing space.
	 */
	protected Pair toDrawingPosition(Pair p) {
		return new Pair(p.getI() * scaleFactor + scaleFactor / 2, p.getJ()
				* scaleFactor + scaleFactor / 2);
	}

	/**
	 * Transform range to drawing space.
	 * 
	 * @param range
	 *            Original range.
	 * @return Range in the drawing space
	 */
	protected int toDrawingRange(int range) {
		return range * scaleFactor + scaleFactor / 2;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

}
