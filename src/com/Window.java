package com;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton startButton;
	private JTextArea statusLabel;
	private Farmer farmer;

	private boolean running = false;

	public Window() {
		farmer = new Farmer(this);
		setLayout();
	}

	public void setLayout() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(300, 200);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("UnturnedHaxxx");

		startButton = new JButton("Currently OFF");
		startButton.setFocusable(false);
		startButton.setBounds(20, 60, 240, 80);
		startButton.setBackground(Color.red);
		startButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (!running) {
					farmer.start();

				} else {
					if (!farmer.startWaitDone()) {
						setStatus("Woah woah! A little bit too soon!");
						return;
					}
					farmer.stop();
				}
			}

		});
		contentPane.add(startButton);

		statusLabel = new JTextArea();
		statusLabel.setLineWrap(true);
		statusLabel.setEditable(false);
		statusLabel.setBounds(10, 11, 264, 41);
		contentPane.add(statusLabel);
		setVisible(true);

		setStatus("How use? 1.Hook throw sea 2.start click \n                    3.go unturned 4.u gud");
	}

	public void setStatus(String status) {
		statusLabel.setText(status);
	}

	public void turnOff() {
		startButton.setBackground(Color.RED);
		startButton.setText("Currently OFF");
		running = false;
	}

	public void turnOn() {
		startButton.setBackground(Color.green);
		startButton.setText("Currently ON");
		running = true;
	}
	
	public boolean running() {
		return running;
	}
}
