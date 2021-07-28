package com;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Scanner;

public class Main {
	
	static boolean running = true;

	public static void main(String[] args) {
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		
		wait(2000);
		System.out.println("Hello There buddy");
		Thread input = new Thread() {
			public void run() {
				Scanner scanner = new Scanner(System.in);
				scanner.nextLine();
				running = false;
				try {
					this.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		input.start();
		while (running) {
			pressAndRelease(robot, 100, KeyEvent.VK_SPACE);
			pressAndRelease(robot, 100, KeyEvent.VK_S);
			
		}
	}

	public static void pressAndRelease(Robot robot, int milli, int keycode) {
		robot.keyPress(keycode);
		wait(milli);
		robot.keyRelease(keycode);
		wait(milli);
		System.out.println("YTeah");

	}
	
	
	// puts the program back to sleep for milli millis
	public static void wait(int milli) {
		try {
			Thread.sleep(milli);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
