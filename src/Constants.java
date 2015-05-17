import java.awt.Color;


public class Constants {
	public static final Color NOTVISIBLE_COLOR = Color.BLACK;
	
	// World size
	public static final int WORLD_SIZE = 50;
	
	// Maze color
	public static final Color EMPTY_SPACE_COLOR = Color.DARK_GRAY;
	public static final Color OBSTACLE_COLOR = Color.LIGHT_GRAY;
	public static final Color OBJECT_COLOR = Color.YELLOW;
		
	// Random agent color
	public static final Color RANDOM_BASE = Color.RED;
	public static final Color RANDOM_ARROW = Color.GREEN;
	public static final Color RANDOM_RANGE = Color.BLUE;
	
	public static final int CANVAS_SIZE = 600;
	public static final String TITLE = "Agents";
	
	public static final int free = 0;
	public static final int obstacle = 2;
	public static final int object = 1;
	public static final int agent = 3;
	
	public static final int up = 0;
	public static final int left = 1;
	public static final int down = 2;
	public static final int right = 3;
	
	public static final int objectPoints = 100;
	public static final int actionPoints = 1;
	
	public static final int sleep = 1000;
}
