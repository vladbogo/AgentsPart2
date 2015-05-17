import java.awt.*;
import java.util.Random;

import javax.swing.*;

@SuppressWarnings("serial")
public class Main extends JPanel {

	public static RandomAgent p;
	public static World m;
	public static Random rand;

	public Main() {
		Thread animationThread = new Thread() {
			@Override
			public void run() {
				// TODO: Write program logic.
				while (true) {
					//p.move();

					repaint();
					pause(100);

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

		p.draw(g);
	}

	public static void init() {
		m = new World(Constants.WORLD_SIZE, rand);
		p = new RandomAgent(m, 10, 1, rand);
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
		rand = new Random();
		
		init();

		pause(100);

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