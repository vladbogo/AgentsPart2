import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class RandomAgent extends Drawable {

	public World m;
	public Pair agentPosition;
	// public int points;
	public int dir;
	Random rand;
	public int scaleFactor = 0;
	// The maximum number of objects the agent can carry.
	public int maxNumberOfObjects = 0;
	public int numberOfObjects;

	HashSet<Pair> pozitii_minerale;

	public boolean lock;

	// int no_crumbs;

	// boolean crumbs_needed = false;

	public RandomAgent(World m, int r, int range, int maxNumberOfObjects,
			Random rand, HashSet<Pair> pozitii_minerale) {
		// Call super constructor to init the drawable object.
		super(m.basePosition, r, range, m.n, Constants.RANDOM_BASE_COLOR,
				Constants.RANDOM_ARROW_COLOR, Constants.RANDOM_RANGE_COLOR);
		// TODO random generate position.
		int poz_i = rand.nextInt(Constants.WORLD_SIZE);
		int poz_j = rand.nextInt(Constants.WORLD_SIZE);
		this.agentPosition = new Pair(poz_i, poz_j);
		this.m = m;
		this.rand = rand;
		dir = Constants.LEFT;
		this.maxNumberOfObjects = maxNumberOfObjects;
		numberOfObjects = 0;
		this.pozitii_minerale = pozitii_minerale;
		lock = false;
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

	public boolean isAtTheBase() {
		return agentPosition.equals(m.basePosition);
	}

	public Pair senseObjects() {
		Pair new_poz = null;

		int x[] = { 1, 1, 1, -1, -1, -1, 0, 0 };
		int y[] = { 0, -1, 1, 0, -1, 1, -1, 1 };

		int max = 0;
		for (int i = 0; i < 8; i++) {
			Pair poz = new Pair(agentPosition.getI() + x[i],
					agentPosition.getJ() + y[i]);
			if (m.isInside(poz) && m.no_Objects(poz) > max) {
				max = m.no_Objects(poz);
				new_poz = poz;
			}

		}
		return new_poz;
	}

	public void move() {
		if (lock && m.no_Objects(agentPosition) == 0) {
			lock = false;
		}
		if (lock) {
			setAgentPos(agentPosition);

			return;
		}
		Pair next;
		if (Constants.VERBOSE)
			System.out.println("Number of objects " + numberOfObjects);
		if (isAtTheBase()) {
			numberOfObjects = 0;
			isFull = false;
			if (Constants.VERBOSE)
				System.out.println("Drop objects at the base");
			// crumbs_needed = false;
		}
		if (m.hasPile(agentPosition) && numberOfObjects != maxNumberOfObjects) {
			// m.pickUpObject(agentPosition);
			pozitii_minerale.add(agentPosition);
			System.out.println(pozitii_minerale);
			lock = true;

			// numberOfObjects++;
			// isFull = true;
		}

		if (lock) {
			setAgentPos(agentPosition);
		} else {
			if ((next = senseObjects()) != null) {
				// TODO: du-te dupa objects

				if (Constants.VERBOSE)
					System.out.println("Go objects");
				setAgentPos(next);
			} else {
				// Random move.
				if (Constants.VERBOSE)
					System.out.println("Random move");
				Pair actualPoz = agentPosition;
				Pair newPoz;
				int newDir;

				while (true) {
					newDir = rand.nextInt(8);
					newPoz = computePoz(actualPoz, newDir);
					if (m.isInside(newPoz))
						break;
				}

				int val = Math.abs(dir - newDir) % 2;

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
		return elem;

	}
}
