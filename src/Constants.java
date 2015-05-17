import java.awt.Color;

public class Constants {
	public static final Color NOTVISIBLE_COLOR = Color.BLACK;

	// World colors
	public static final Color EMPTY_SPACE_COLOR = Color.DARK_GRAY;
	public static final Color OBSTACLE_COLOR = Color.LIGHT_GRAY;
	public static final Color OBJECT_COLOR = Color.YELLOW;

	// Random agent colors
	public static final Color RANDOM_BASE_COLOR = Color.RED;
	public static final Color RANDOM_ARROW_COLOR = Color.GREEN;
	public static final Color RANDOM_RANGE_COLOR = Color.BLUE;

	// General constants.
	public static final int CANVAS_SIZE = 600;
	public static final String TITLE = "Agents";
	public static final int SLEEP = 1000;

	// World related constants.
	public static final int FREE_SPACE = 0;
	public static final int OBSTACLE = 2;
	public static final int OBJECT = 1;
	public static final int WORLD_SIZE = 20;
	public static final int AGENT = 3;

	// Agent related constants.
	public static final int UP = 0;
	public static final int LEFT = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;

	public static final int OBJECT_POINTS = 100;
	public static final int ACTION_POINTS = 1;

}
