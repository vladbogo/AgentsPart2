import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

import javafx.scene.effect.Lighting;

public class RandomAgent extends Drawable {

	public World m;
	public Pair agentPosition;
	public int points;
	public int dir;
	Random rand;
	public int scaleFactor = 0;
	// The maximum number of objects the agent can carry.
	public int maxNumberOfObjects = 0;
	public int numberOfObjects;
	// Search mode.
	public boolean search;

	public RandomAgent(World m, int r, int range, int maxNumberOfObjects,
			Random rand) {
		// Call super constructor to init the drawable object.
		super(m.basePosition, r, range, m.n, Constants.RANDOM_BASE_COLOR,
				Constants.RANDOM_ARROW_COLOR, Constants.RANDOM_RANGE_COLOR);
		// TODO random generate position.
		this.agentPosition = m.basePosition;
		this.m = m;
		points = 0;
		this.rand = rand;
		dir = Constants.LEFT;
		this.maxNumberOfObjects = maxNumberOfObjects;
		numberOfObjects = 0;
		// Enable search mode.
		search = true;
	}

	private Pair computePoz(Pair actualPoz, int dir) {
		Pair p = null;

		if (dir == Constants.UP) {
			p = new Pair(actualPoz.getI() - 1, actualPoz.getJ());
		} else if (dir == Constants.DOWN) {
			p = new Pair(actualPoz.getI() + 1, actualPoz.getJ());
		} else if (dir == Constants.LEFT) {
			p = new Pair(actualPoz.getI(), actualPoz.getJ() - 1);
		} else if (dir == Constants.RIGHT) {
			p = new Pair(actualPoz.getI(), actualPoz.getJ() + 1);
		} else if (dir == Constants.UPPER_LEFT) {
			p = new Pair(actualPoz.getI() - 1, actualPoz.getJ() - 1);
		} else if (dir == Constants.UPPER_RIGHT) {
			p = new Pair(actualPoz.getI() - 1, actualPoz.getJ() + 1);
		} else if (dir == Constants.DOWNER_LEFT) {
			p = new Pair(actualPoz.getI() + 1, actualPoz.getJ() - 1);
		} else if (dir == Constants.DOWNER_RIGHT) {
			p = new Pair(actualPoz.getI() + 1, actualPoz.getJ() + 1);
		}

		return p;
	}

	public void move() {
		if (search) {
			// Random move.
			Pair actualPoz = agentPosition;
			Pair newPoz;

			if (m.hasPile(actualPoz)) {
				m.pickUpObject(actualPoz);

				numberOfObjects++;
				if (numberOfObjects == maxNumberOfObjects) {
					isFull = true;
					search = false;
				}

				points += Constants.OBJECT_POINTS;
			}
			int newDir;

			while (true) {
				newDir = rand.nextInt(8);
				newPoz = computePoz(actualPoz, newDir);
				if (m.isInside(newPoz))
					break;
			}

			int val = Math.abs(dir - newDir) % 2;

			points -= val * Constants.ACTION_POINTS;

			setAgentPos(newPoz);
		} else {
			// TODO: Go to base.
			System.out.println("Going to the base");
			Pair newPoz = nextPositionToBase();
			if (newPoz == null) {
				System.out.println("OOOPS");
			} else {
				setAgentPos(newPoz);
			}
		}
	}

	public void setAgentPos(Pair newPoz) {
		agentPosition = newPoz;
		drawingPosition = toDrawingPosition(newPoz);
	}

	public Pair nextPositionToBase() {
		if (agentPosition.equals(m.basePosition)) {
			return null;
		}
		LinkedList<Pair> queue = new LinkedList<>();
		queue.add(agentPosition);
		Pair elem = null, prev;
		HashMap<Pair, Pair> previous = new HashMap<>();
		HashSet<Pair> visited = new HashSet<>();

		while (!(elem = queue.removeFirst()).equals(m.basePosition)) {
			int x[] = { 1, 1, 1, -1, -1, -1, 0, 0 };
			int y[] = { 0, -1, 1, 0, -1, 1, -1, 1 };
			for (int i = 0; i < 8; i++) {
				Pair new_pos = new Pair(elem.getI() + x[i], elem.getJ() + y[i]);
				if (m.isInside(new_pos) && !visited.contains(new_pos)) {
					queue.add(new_pos);
					visited.add(new_pos);
					previous.put(new_pos, elem);
				}
			}
		}

		while (!(prev = previous.get(elem)).equals(agentPosition)) {
			elem = previous.get(elem);
		}
		System.out.println("return " + elem + " agent " + agentPosition);
		return elem;

	}
}
