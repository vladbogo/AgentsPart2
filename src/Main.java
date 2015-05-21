import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

@SuppressWarnings("serial")
public class Main extends JPanel {

	// agent
	// public static RandomAgent p;
	public static World m;
	public static Random rand;

	static ArrayList<RandomAgent> random_agents;
	static ArrayList<CarrierAgent> carrier_agents;

	static int time;

	public boolean allAgentsEmpty() {
		for (int i = 0; i < random_agents.size(); i++) {
			if (random_agents.get(i).numberOfObjects > 0) {
				return false;
			}
		}
		return true;
	}

	public Main() {
		Thread animationThread = new Thread() {
			@Override
			public void run() {
				// TODO: Write program logic.
				while (m.areMoreMinerals() || !allAgentsEmpty()) {
					// p.move();
					for (int i = 0; i < random_agents.size(); i++) {
						random_agents.get(i).move();
					}

					for (int i = 0; i < carrier_agents.size(); i++) {
						carrier_agents.get(i).move();
					}
					time++;
					repaint();
					pause(Constants.SLEEP);

				}
				if (!(m.areMoreMinerals() || !allAgentsEmpty())) {
					System.out.println("time " + time);
				}

			};
		};
		animationThread.start(); // start the thread to run animation

		setPreferredSize(new Dimension(Constants.CANVAS_SIZE,
				Constants.CANVAS_SIZE));
	}

	/** Custom painting codes on this JPanel */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // paint background

		/* TODO drawings */
		m.draw(g);

		// p.draw(g);
		for (int i = 0; i < random_agents.size(); i++) {
			random_agents.get(i).draw(g);
		}
		for (int i = 0; i < carrier_agents.size(); i++) {
			carrier_agents.get(i).draw(g);
		}
	}

	public static void init() {
		m = new World(Constants.WORLD_SIZE, rand);
		random_agents = new ArrayList<>();
		carrier_agents = new ArrayList<>();
		// p = new RandomAgent(m, Constants.AGENT_RADIUS, Constants.AGENT_RANGE,
		// Constants.MAX_NUMBER_OF_OBJECTS, rand);
		for (int i = 0; i < Constants.NO_SEARCH_AGENTS; i++) {
			RandomAgent ag = new RandomAgent(m, Constants.AGENT_RADIUS,
					Constants.AGENT_RANGE, Constants.MAX_NUMBER_OF_OBJECTS,
					rand);
			random_agents.add(ag);
		}

		for (int i = 0; i < Constants.NO_CARRIER_AGENTS; i++) {
			CarrierAgent ag = new CarrierAgent(m, Constants.AGENT_RADIUS,
					Constants.AGENT_RANGE, Constants.MAX_NUMBER_OF_OBJECTS,
					rand);
			carrier_agents.add(ag);
		}
	}

	public static void pause(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** The entry main() method */
	public static void main(String[] args) {
		time = 0;
		rand = new Random();

		init();

		pause(Constants.SLEEP);

		/* Render the frame */
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame(Constants.TITLE);
				frame.setContentPane(new Main());
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
}