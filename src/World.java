import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

public class World {
	public int n;
	public int[][] world;
	public int numberOfObjects;
	private Random rand;

	public World(int n) {
		this.n = n;
		rand = new Random(42);
		numberOfObjects = 0;

		world = new int[n][n];
		genWorld();
	}

	private int getRand(int nr) {
		return rand.nextInt(100000) % nr;
	}

	private boolean hasObstNeigh(int i, int j) {
		if (i - 1 >= 0 && world[i - 1][j] == Constants.obstacle)
			return true;
		if (j - 1 >= 0 && world[i][j - 1] == Constants.obstacle)
			return true;
		if (i - 1 >= 0 && j - 1 >= 0
				&& world[i - 1][j - 1] == Constants.obstacle)
			return true;
		if (i - 1 >= 0 && j + 1 < n && world[i - 1][j + 1] == Constants.obstacle)
			return true;

		return false;
	}

	private void genWorld() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				world[i][j] = 0;
				if (i == 0 && j == 0) {
					world[i][j] = Constants.free;
				} else {
					if (!hasObstNeigh(i, j)) {
						world[i][j] = getRand(3);
						if (world[i][j] == Constants.object)
							numberOfObjects++;
					} else {
						world[i][j] = getRand(2);
						if (world[i][j] == Constants.object)
							numberOfObjects++;
					}
				}
			}
		}
	}
	
	public boolean hasObject(Pair poz) {
		return world[poz.getI()][poz.getJ()] == Constants.object;
	}
	
	public void pickUpObject(Pair poz) {
		if (hasObject(poz)) {
			numberOfObjects--;
			world[poz.getI()][poz.getJ()] = Constants.free;
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
		if (world[poz.getI()][poz.getJ()] == Constants.obstacle)
			return false;
		return true;
	}

	public String toString() {
		String res = "";
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (world[i][j] == Constants.agent)
					res += "A";
				else if (world[i][j] == Constants.free) {
					res += "_";
				} else if (world[i][j] == Constants.object) {
					res += "O";
				} else if (world[i][j] == Constants.obstacle) {
					res += "X";
				}
			}
			res += "\n";
		}
		return res;
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		int scale = Constants.CANVAS_SIZE / n;
		
		for (int i = 0; i < n; i++) {
			int scaledI = i * scale;
			for (int j = 0; j < n; j++) {
				int scaledJ = j * scale;
				g2d.setColor(Constants.EMPTY_SPACE_COLOR);
				g2d.drawRect(scaledI, scaledJ, scale, scale);
				if (world[i][j] == Constants.obstacle) {
					g2d.setColor(Constants.OBSTACLE_COLOR);
					g2d.fillRect(scaledI, scaledJ, scale, scale);
				}
				if (world[i][j] == Constants.object) {
					g2d.setColor(Constants.OBJECT_COLOR);
					g2d.fillRect(scaledI, scaledJ, scale, scale);
				}
			}
		}
	}
}