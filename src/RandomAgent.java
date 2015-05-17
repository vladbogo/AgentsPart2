import java.util.Random;

public class RandomAgent extends Drawable {

	public World m;
	public Pair agentPosition;
	public int points;
	public int dir;
	Random rand;
	public int scaleFactor = 0;

	public RandomAgent(World m, Pair initial_pos, int r, int angle, int range) {
		super(initial_pos, r, angle, range, Constants.RANDOM_BASE,
				Constants.RANDOM_ARROW, Constants.RANDOM_RANGE);
		// TODO random generate position.
		this.agentPosition = new Pair(0, 0);
		this.m = m;
		points = 0;
		rand = new Random(42);
		dir = Constants.left;
		scaleFactor = Constants.CANVAS_SIZE / m.n;
	}

	private Pair computePoz(Pair actualPoz, int dir) {
		Pair p = null;

		if (dir == Constants.up) {
			p = new Pair(actualPoz.getI() - 1, actualPoz.getJ());
		} else if (dir == Constants.down) {
			p = new Pair(actualPoz.getI() + 1, actualPoz.getJ());
		} else if (dir == Constants.left) {
			p = new Pair(actualPoz.getI(), actualPoz.getJ() + 1);
		} else if (dir == Constants.right) {
			p = new Pair(actualPoz.getI(), actualPoz.getJ() - 1);
		}

		return p;
	}

	private Pair toDrawingPosition(Pair p) {
		return new Pair(p.getI() * scaleFactor + scaleFactor / 2, p.getJ()
				* scaleFactor + scaleFactor / 2);
	}

	public void move() {
		Pair actualPoz = agentPosition;
		Pair newPoz;

		if (m.hasObject(actualPoz)) {
			m.pickUpObject(actualPoz);
			points += Constants.objectPoints;
		}
		int newDir;

		while (true) {
			newDir = rand.nextInt(4);
			newPoz = computePoz(actualPoz, newDir);
			if (m.isOk(newPoz))
				break;
		}

		int val = Math.abs(dir - newDir) % 2;

		points -= val * Constants.actionPoints;

		agentPosition= newPoz;

		// Update drawing
		// this.angle = toDrawingAngle(dir);
		drawingPosition = toDrawingPosition(newPoz);
	}

	public void start() {
		while (m.numberOfObjects > 0) {
			System.out.println("Agent in pozitia: " + agentPosition
					+ " cu puncte: " + points);
			System.out.println(m);
			try {
				Thread.sleep(Constants.sleep);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			move();
		}

		while (!agentPosition.equals(new Pair(0, 0))) {
			System.out.println("Agent in pozitia: " + agentPosition
					+ " cu puncte: " + points);
			System.out.println(m);
			try {
				Thread.sleep(Constants.sleep);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			move();
		}

		System.out.println("Agent in pozitia: " + agentPosition
				+ " cu puncte: " + points);
	}

}
