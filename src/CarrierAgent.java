import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class CarrierAgent extends Drawable {
	public World m;
	public Pair agentPosition;
	public int points;
	public int dir;
	Random rand;
	public int scaleFactor = 0;
	// The maximum number of objects the agent can carry.
	public int maxNumberOfObjects = 0;
	public int numberOfObjects;
	public int indice;

	ArrayList<CarrierAgent> carrier_agents;
	ArrayList<RandomAgent> random_agents;
	HashSet<Pair> pozitii_minerale;

	public Pair target;
	public Pair intention;

	public Pair last_mineral_position;

	public CarrierAgent(World m, int r, int range, int maxNumberOfObjects,
			Random rand, ArrayList<CarrierAgent> carrier_agents,
			ArrayList<RandomAgent> random_agents,
			HashSet<Pair> pozitii_minerale, int indice) {
		super(m.basePosition, r, range, m.n, Constants.RANDOM_CARRIER_COLOR,
				Constants.RANDOM_ARROW_COLOR, Constants.RANDOM_RANGE_COLOR);
		int poz_i = rand.nextInt(Constants.WORLD_SIZE);
		int poz_j = rand.nextInt(Constants.WORLD_SIZE);
		this.agentPosition = new Pair(poz_i, poz_j);
		this.m = m;
		points = 0;
		this.rand = rand;
		dir = Constants.LEFT;
		this.maxNumberOfObjects = maxNumberOfObjects;
		numberOfObjects = 0;
		this.carrier_agents = carrier_agents;
		this.random_agents = random_agents;
		this.indice = indice;
		this.pozitii_minerale = pozitii_minerale;
		target = null;
		intention = null;
		last_mineral_position = null;
	}

	public void setAgentPos(Pair newPoz) {
		agentPosition = newPoz;
		drawingPosition = toDrawingPosition(newPoz);
	}

	public void move() {
		if (target == null) {
			computePozitiiMinerale();
		}
		if (intention == null) {
			intention = last_mineral_position;
		}

		if (target != null && agentPosition.equals(target)
				&& !target.equals(m.basePosition)) {
			numberOfObjects += m.no_Objects(agentPosition);
			isFull = true;
			last_mineral_position = agentPosition;
			System.out.println("Am adunat " + numberOfObjects);
			m.getAllObjects(agentPosition);
			// target = m.basePosition;
			intention = m.basePosition;
			target = null;
		}
		if (agentPosition.equals(m.basePosition)
				&& m.basePosition.equals(intention)) {
			System.out.println("Am lasat obiectele");
			numberOfObjects = 0;
			isFull = false;
			// target = null;
			intention = null;
		}
		if (target != null && !agentPosition.equals(target)) {
			Pair next = nextPositionToTarget(target);
			setAgentPos(next);
		} else if (target == null && intention != null) {
			Pair next = nextPositionToTarget(intention);
			setAgentPos(next);
		} else {
			setAgentPos(agentPosition);
		}
	}

	public int getDistance(Pair poz1, Pair poz2) {
		return Math.abs(poz1.getI() - poz2.getI())
				+ Math.abs(poz1.getJ() - poz2.getJ());
	}

	public void computePozitiiMinerale() {
		if (target != null)
			return;
		Iterator<Pair> it = pozitii_minerale.iterator();
		while (it.hasNext()) {
			Pair poz_mineral = it.next();
			int min_distance = Integer.MAX_VALUE;
			int indice = -1;
			for (int j = 0; j < carrier_agents.size(); j++) {
				int dist = getDistance(poz_mineral,
						carrier_agents.get(j).agentPosition);
				if (dist < min_distance && carrier_agents.get(j).target == null) {
					min_distance = dist;
					indice = j;
				}
			}
			if (indice == this.indice) {
				target = poz_mineral;
				System.out.println("setat target!! " + indice + " " + target);
				if (!pozitii_minerale.remove(poz_mineral))
					System.out.println(":(((");
				;
				break;
			}
		}
	}

	public Pair nextPositionToTarget(Pair target) {
		if (agentPosition.equals(target)) {
			return agentPosition;
		}
		LinkedList<Pair> queue = new LinkedList<>();
		queue.add(agentPosition);
		Pair elem = null, prev;
		HashMap<Pair, Pair> previous = new HashMap<>();
		HashSet<Pair> visited = new HashSet<>();

		while (!(elem = queue.removeFirst()).equals(target)) {
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
