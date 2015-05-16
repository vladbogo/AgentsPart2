import java.awt.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class Main extends JPanel {

	public static RandomAgent p;
	public static Maze m;

	public Main() {
		Thread animationThread = new Thread() {
			@Override
			public void run() {
				// TODO: Write program logic.
				while (true) {
					p.move();

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
		m = new Maze(50);
		p = new RandomAgent(m, new Pair(0, 0), 10, 270, 3);
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