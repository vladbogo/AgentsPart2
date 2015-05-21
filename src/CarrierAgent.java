import java.awt.Color;
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

	public CarrierAgent(World m, int r, int range, int maxNumberOfObjects,
			Random rand) {
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
	}

	public void move() {

		// agentPosition = newPoz;
		drawingPosition = toDrawingPosition(agentPosition);

	}

}
