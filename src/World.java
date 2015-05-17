/**
 * This class represents the world.
 */
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class World {
	// World dimension.
	public int n;
	// World matrix.
	public int[][] world;
	// Base position.
	public Pair basePosition;
	// Number of piles.
	public int numberOfPiles;
	private Random rand;
	private HashMap<Pair, ArrayList<Pair>> mapPiles;

	/**
	 * Create a world.
	 * 
	 * @param n
	 *            World dimension
	 */
	public World(int n, Random rand) {
		this.n = n;
		this.rand = rand;
		numberOfPiles = 0;
		basePosition = new Pair(rand.nextInt(n), rand.nextInt(n));

		world = new int[n][n];
		world[basePosition.getI()][basePosition.getJ()] = Constants.BASE;
		genWorld();
		mapPiles = new HashMap<>();
	}

	/**
	 * Maps a [0-nr] number to free space, obstacle or pile.
	 * 
	 * @param nr
	 *            Number of params to generate. Can be 2 or 3.
	 * @return
	 */
	private int getRand(int nr) {
		int n = rand.nextInt(nr);
		if (n == 0)
			return Constants.FREE_SPACE;
		if (n == 1)
			return Constants.OBJECT;
		if (n == 2)
			return Constants.OBSTACLE;
		return n;
	}

	/**
	 * Check if any neighbor of (i, j) is an obstacle - the method is used to
	 * make sure that there aren't any unaccessible areas for the world.
	 * 
	 * @param i
	 *            line index
	 * @param j
	 *            column index
	 * @return true/false if a neighbor of (i,j) is an obstacle.
	 */
	private boolean hasObstNeigh(int i, int j) {
		if (i - 1 >= 0 && world[i - 1][j] == Constants.OBSTACLE)
			return true;
		if (j - 1 >= 0 && world[i][j - 1] == Constants.OBSTACLE)
			return true;
		if (i - 1 >= 0 && j - 1 >= 0
				&& world[i - 1][j - 1] == Constants.OBSTACLE)
			return true;
		if (i - 1 >= 0 && j + 1 < n
				&& world[i - 1][j + 1] == Constants.OBSTACLE)
			return true;

		return false;
	}

	/**
	 * Generate a random world.
	 */
	private void genWorld() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (world[i][j] == 0) {
					if (i == 0 && j == 0) {
						world[i][j] = Constants.FREE_SPACE;
					} else {
						if (!hasObstNeigh(i, j)) {
							world[i][j] = getRand(3);
							if (world[i][j] == Constants.OBJECT) {
								world[i][j] = rand
										.nextInt(Constants.MAX_OBJECTS_PER_PILE);
								numberOfPiles++;
							}
						} else {
							world[i][j] = getRand(2);
							if (world[i][j] == Constants.OBJECT) {
								world[i][j] = rand
										.nextInt(Constants.MAX_OBJECTS_PER_PILE);
								numberOfPiles++;
							}
						}
					}
				}
			}
		}
	}

	public boolean hasObject(Pair poz) {
		return world[poz.getI()][poz.getJ()] == Constants.OBJECT;
	}

	public void pickUpObject(Pair poz) {
		if (hasObject(poz)) {
			numberOfPiles--;
			world[poz.getI()][poz.getJ()] = Constants.FREE_SPACE;
		}
	}

	public boolean isOk(Pair poz) {
		if (poz.getI() < 0)
			return false;
		if (poz.getJ() < 0)
			return false;
		if (poz.getI() >= n)
			return false;
		if (poz.getJ() >= n)
			return false;
		if (world[poz.getI()][poz.getJ()] == Constants.OBSTACLE)
			return false;
		return true;
	}

	public String toString() {
		String res = "";
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (world[i][j] == Constants.AGENT)
					res += "A";
				else if (world[i][j] == Constants.FREE_SPACE) {
					res += "_";
				} else if (world[i][j] == Constants.OBJECT) {
					res += "O";
				} else if (world[i][j] == Constants.OBSTACLE) {
					res += "X";
				} else if (world[i][j] == Constants.BASE) {
					res += "B";
				} else {
					res += world[i][j] + "";
				}
			}
			res += "\n";
		}
		return res;
	}

	/**
	 * Checks if value is pile.
	 * 
	 * @param value
	 *            Input value
	 * @return Checks if value is pile.
	 */
	public boolean isPile(int value) {
		return value > 0 && value < Constants.MAX_OBJECTS_PER_PILE;
	}

	/**
	 * Draw the world.
	 * 
	 * @param g
	 *            Graphics context.
	 */
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		int scale = Constants.CANVAS_SIZE / n;

		for (int i = 0; i < n; i++) {
			int scaledI = i * scale;
			for (int j = 0; j < n; j++) {
				int scaledJ = j * scale;
				g2d.setColor(Constants.EMPTY_SPACE_COLOR);
				g2d.drawRect(scaledI, scaledJ, scale, scale);
				if (world[i][j] == Constants.OBSTACLE) {
					g2d.setColor(Constants.OBSTACLE_COLOR);
					g2d.fillOval(scaledI, scaledJ, scale, scale);
				} else {
					if (isPile(world[i][j])) {
						g2d.setColor(Constants.OBJECT_COLOR);
						ArrayList<Pair> pile = mapPiles.get(new Pair(i, j));
						if (pile != null) {
							for (int k = 0; k < pile.size(); k++) {
								g2d.fillOval(pile.get(k).getI(), pile.get(k)
										.getJ(), Constants.OBJECT_SIZE,
										Constants.OBJECT_SIZE);
							}
						} else {
							pile = new ArrayList<>();
							for (int k = 0; k < world[i][j]; k++) {
								int x_offset = rand.nextInt(scale);
								int y_offset = rand.nextInt(scale);
								pile.add(new Pair(scaledI + x_offset, scaledJ
										+ y_offset));
								g2d.fillOval(pile.get(k).getI(), pile.get(k)
										.getJ(), Constants.OBJECT_SIZE,
										Constants.OBJECT_SIZE);
							}
							mapPiles.put(new Pair(i, j), pile);
						}
					}
				}

			}
		}

		g2d.setColor(Constants.BASE_COLOR);
		int base_x = basePosition.getI() * scale - scale / 2;
		int base_y = basePosition.getJ() * scale - scale / 2;
		g2d.fillOval(base_x, base_y, 2 * scale, 2 * scale);
	}
}
