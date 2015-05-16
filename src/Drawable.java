import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

public class Drawable {

	protected Pair position;
	protected int r, range;
	protected double angle;
	protected Color baseColor, arrowColor, rangeColor;

	public Drawable(Pair p, int r, int angle, int range, Color baseC,
			Color arrowC, Color rangeC) {
		position = p;
		this.r = r;
		this.range = range;
		this.angle = Math.toRadians(angle);
		baseColor = baseC;
		arrowColor = arrowC;
		rangeColor = rangeC;
	}

	public void draw(Graphics g) {
		AffineTransform tr = new AffineTransform();
		Graphics2D g2d = (Graphics2D) g;

		tr.translate(position.getI(), position.getJ());
		tr.rotate(angle);
		g2d.setTransform(tr);

		g2d.setColor(baseColor);
		g2d.fillOval(-r, -r, 2 * r, 2 * r);

		if (!arrowColor.equals(Constants.NOTVISIBLE_COLOR)) {
			g2d.setColor(rangeColor);
			g2d.drawOval(-range, -range, 2 * range, 2 * range);

			g2d.setColor(arrowColor);
			tr.translate(r, 0);
			tr.rotate(Math.toRadians(45));
			g2d.setTransform(tr);
			g2d.fillRect(-r / 4, -r / 4, r / 2, r / 2);
		}

		try {
			g2d.setTransform(tr.createInverse());
		} catch (NoninvertibleTransformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void moveDrawing(double viteza) {
		double sin = Math.sin(angle);
		double cos = Math.cos(angle);

		int x = position.getI();
		int y = position.getJ();
		if (x + cos * viteza < 0)
			x = Constants.CANVAS_SIZE;
		else if (x + cos * viteza > Constants.CANVAS_SIZE)
			x = 0;
		else
			x += cos * viteza;
		if (y + sin * viteza < 0)
			y = Constants.CANVAS_SIZE;
		else if (y + sin * viteza > Constants.CANVAS_SIZE)
			y = 0;
		else
			y += sin * viteza;
		position.setI(x);
		position.setJ(y);
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

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}
}
