package com;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Farmer implements Runnable {

	private Window window;
	private Thread thread;

	private boolean running = false;
	private boolean startWaitDone = false;

	private int r1, g1, b1;
	private boolean first = true;
	private int counter = 0;

	public Farmer(Window window) {
		this.window = window;
	}

	public void run() {
		// File file = new File("C:\\Users\\rahul\\Desktop\\image.jpg");
		// ImageIO.write(image, "jpg", file);
		window.turnOn();

		startWaitDone = false;
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (Exception E) {
			System.out.println("Hi");
			E.printStackTrace();
		}
		setStatus("5 second stretch before doing your work for you");
		wait(5000);
		setStatus("Waiting...for...fish....");

		startWaitDone = true;

		while (running) {
			BufferedImage image = robot.createScreenCapture(new Rectangle(835, 467, 1, 1));
			Integer num = image.getRGB(0, 0);

			int r = (num & 0xffffff) >> 16;
			int g = (num & 0xffff) >> 8;
			int b = num & 0xff;

			if (first) {
				r1 = r;
				g1 = g;
				b1 = b;
				first = false;
				continue;
			}

			// System.out.println(getPercentageDifference(r, g, b, r1, g1, b1));
			// System.out.printf(" r: 0x%08x g: 0x%08x b: 0x%08x%n", r, g, b);

			double distance = getDistance(r, g, b, r1, g1, b1);

			if (distance > 7) {
				counter++;
				setStatus("Catching Fish, press left click");
				robot.mousePress(MouseEvent.BUTTON1_MASK);
				wait(100);
				setStatus("Catching Fish, release left click");
				robot.mouseRelease(MouseEvent.BUTTON1_MASK);
				wait(3000);
				setStatus("Charge hook, press left click");
				robot.mousePress(MouseEvent.BUTTON1_MASK);
				wait(1150);
				setStatus("Release hook, release left click");
				robot.mouseRelease(MouseEvent.BUTTON1_MASK);
				wait(3000);
				first = true;
				setStatus("Waiting...for...fish....");
			}
			r1 = r;
			g1 = g;
			b1 = b;
		}

		if (counter < 5)
			setStatus("You're slacking, not even 5 catches");
		else
			setStatus("Your hard work paid off");

		window.turnOff();
	}

	private void setStatus(String message) {
		window.setStatus(message + "\nCatches: " + counter);
	}

	private void wait(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private double getDistance(int r1, int g1, int b1, int r2, int g2, int b2) {
		double col1 = (r1 - r2) * (r1 - r2) + (g1 - g2) * (g1 - g2) + (b1 - b2) * (b1 - b2);
		return Math.sqrt(col1);
	}

	public void start() {
		if (window.running()) {
			return;
		}
		window.setStatus("Waiting 5 seconds before starting!");
		thread = new Thread(this, "Farmer");
		running = true;
		thread.start();
	}

	public synchronized void stop() {
		running = false;
	}

	public boolean startWaitDone() {
		return startWaitDone;
	}

}
