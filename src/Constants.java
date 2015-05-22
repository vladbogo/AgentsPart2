import java.awt.Color;

public class Constants {
	// General constants.
	public static final int CANVAS_SIZE = 600;
	public static final String TITLE = "Agents";
	public static final int SLEEP = 300;
	public static final Color NOTVISIBLE_COLOR = Color.BLACK;

	// World colors
	public static final Color EMPTY_SPACE_COLOR = Color.DARK_GRAY;
	public static final Color OBSTACLE_COLOR = Color.LIGHT_GRAY;
	public static final Color OBJECT_COLOR = Color.MAGENTA;
	public static final Color BASE_COLOR = Color.YELLOW;
	public static final Color CRUMB_COLOR = Color.GREEN;
	public static final int OBJECT_SIZE = 5;

	// Random agent colors
	public static final Color RANDOM_BASE_COLOR = Color.RED;
	public static final Color RANDOM_ARROW_COLOR = Color.GREEN;
	public static final Color RANDOM_RANGE_COLOR = Color.BLUE;

	public static final Color RANDOM_CARRIER_COLOR = Color.GREEN;

	// World related constants.
	public static final int WORLD_SIZE = 30;
	public static final int BASE = -2;
	public static final int AGENT = 10001;
	public static final int FREE_SPACE = 0;
	public static final int OBSTACLE = -1;
	public static final int OBJECT = -3;
	public static final int MAX_OBJECTS_PER_PILE = 10;

	// Agent related constants.
	public static final int UP = 0;
	public static final int LEFT = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;
	public static final int UPPER_RIGHT = 4;
	public static final int UPPER_LEFT = 5;
	public static final int DOWNER_RIGHT = 6;
	public static final int DOWNER_LEFT = 7;

	public static final int MAX_NUMBER_OF_OBJECTS = 1;
	public static final int AGENT_RADIUS = 10;
	public static final int AGENT_RANGE = 1;

	public static final int OBJECT_POINTS = 100;
	public static final int ACTION_POINTS = 1;

	public static final int NO_SEARCH_AGENTS = 10;
	public static final int NO_CARRIER_AGENTS = 3;

	// Crumbs
	public static final int MAX_CRUMB_INTENSITY = MAX_OBJECTS_PER_PILE;

	public static final boolean VERBOSE = false;

	public static final boolean RANDOM_MOVE_CARRIER = true;

	public static final boolean CARRIER_SPREAD = true;

	public static final boolean CARRIER_RETURN_LAST_PILE = true;
	
	public static final boolean SHOW_GRID = false;
}
