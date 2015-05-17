import java.util.Random;

public class RandomAgent extends Drawable {

	public World m;
	public Pair agentPosition;
	public int points;
	public int dir;
	Random rand;
	public int scaleFactor = 0;

	public RandomAgent(World m, int r, int range, Random rand) {
		// Call super constructor to init the drawable object.
		super(m.basePosition, r, range, m.n, Constants.RANDOM_BASE_COLOR,
				Constants.RANDOM_ARROW_COLOR, Constants.RANDOM_RANGE_COLOR);
		// TODO random generate position.
		this.agentPosition = m.basePosition;
		this.m = m;
		points = 0;
		this.rand = rand;
		dir = Constants.LEFT;

	}

	private Pair computePoz(Pair actualPoz, int dir) {
		Pair p = null;

		if (dir == Constants.UP) {
			p = new Pair(actualPoz.getI() - 1, actualPoz.getJ());
		} else if (dir == Constants.DOWN) {
			p = new Pair(actualPoz.getI() + 1, actualPoz.getJ());
		} else if (dir == Constants.LEFT) {
			p = new Pair(actualPoz.getI(), actualPoz.getJ() + 1);
		} else if (dir == Constants.RIGHT) {
			p = new Pair(actualPoz.getI(), actualPoz.getJ() - 1);
		}

		return p;
	}

	public void move() {
		Pair actualPoz = agentPosition;
		Pair newPoz;

		if (m.hasPile(actualPoz)) {
			m.pickUpObject(actualPoz);
			points += Constants.OBJECT_POINTS;
		}
		int newDir;

		while (true) {
			newDir = rand.nextInt(4);
			newPoz = computePoz(actualPoz, newDir);
			if (m.isInside(newPoz))
				break;
		}

		int val = Math.abs(dir - newDir) % 2;

		points -= val * Constants.ACTION_POINTS;

		agentPosition= newPoz;

		// Update drawing
		// this.angle = toDrawingAngle(dir);
		drawingPosition = toDrawingPosition(newPoz);
	}

	public void start() {
		while (m.numberOfPiles > 0) {
			System.out.println("Agent in pozitia: " + agentPosition
					+ " cu puncte: " + points);
			System.out.println(m);
			try {
				Thread.sleep(Constants.SLEEP);
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
				Thread.sleep(Constants.SLEEP);
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
