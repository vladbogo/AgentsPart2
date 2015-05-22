import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import javax.swing.*;

@SuppressWarnings("serial")
public class Main extends JPanel {

	// agent
	public static World m;
	public static Random rand;

	static ArrayList<RandomAgent> random_agents;
	static ArrayList<CarrierAgent> carrier_agents;
	static HashSet<Pair> pozitii_minerale;

	static int time;

	public boolean allAgentsEmpty() {
		for (int i = 0; i < random_agents.size(); i++) {
			if (random_agents.get(i).numberOfObjects > 0) {
				return false;
			}
		}
		for (int i = 0; i < carrier_agents.size(); i++) {
			if (carrier_agents.get(i).numberOfObjects > 0) {
				return false;
			}
		}
		return true;
	}

	public static void makeCarrierSpread() {
		// se duc pe verticala
		int first_half = carrier_agents.size() / 2;
		// se duc pe orizontala
		int second_half = carrier_agents.size() - first_half;
		int pasi_verticala, pasi_orizontala;
		if (first_half != 0) {
			pasi_verticala = Constants.WORLD_SIZE / first_half;
			if (pasi_verticala == 0)
				pasi_verticala = 1;
		} else
			pasi_verticala = 0;
		if (second_half != 0) {
			pasi_orizontala = Constants.WORLD_SIZE / second_half;
			if (pasi_orizontala == 0) {
				pasi_orizontala = 1;
			}
		} else
			pasi_orizontala = 0;

		int pas_curent = 0;
		// verticala
		for (int i = 0; i < first_half; i++) {
			int poz_j = m.basePosition.getJ();
			int poz_i = pas_curent;
			pas_curent += pasi_verticala;
			Pair poz = new Pair(poz_i, poz_j);
			if (m.isInside(poz))
				carrier_agents.get(i).intention = poz;
		}
		pas_curent = 0;
		for (int i = first_half; i < carrier_agents.size(); i++) {
			int poz_i = m.basePosition.getI();
			int poz_j = pas_curent;
			pas_curent += pasi_orizontala;
			Pair poz = new Pair(poz_i, poz_j);
			if (m.isInside(poz)) {
				carrier_agents.get(i).intention = poz;
			}
		}
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

				System.out.println("time " + time);

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
		pozitii_minerale = new HashSet<>();
		// p = new RandomAgent(m, Constants.AGENT_RADIUS, Constants.AGENT_RANGE,
		// Constants.MAX_NUMBER_OF_OBJECTS, rand);
		for (int i = 0; i < Constants.NO_SEARCH_AGENTS; i++) {
			RandomAgent ag = new RandomAgent(m, Constants.AGENT_RADIUS,
					Constants.AGENT_RANGE, Constants.MAX_NUMBER_OF_OBJECTS,
					rand, pozitii_minerale);
			random_agents.add(ag);
		}

		for (int i = 0; i < Constants.NO_CARRIER_AGENTS; i++) {
			CarrierAgent ag = new CarrierAgent(m, Constants.AGENT_RADIUS,
					Constants.AGENT_RANGE, Constants.MAX_NUMBER_OF_OBJECTS,
					rand, carrier_agents, random_agents, pozitii_minerale, i);
			carrier_agents.add(ag);
		}
		if (Constants.CARRIER_SPREAD)
			makeCarrierSpread();
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