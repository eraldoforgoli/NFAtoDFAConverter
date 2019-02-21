package com.View;

import javax.swing.*;
import java.awt.*;

import java.util.List;

public class DrawingPanel extends JPanel {
	private DataCalculator dataCalculator;

	DrawingPanel(String file) {
		dataCalculator = new DataCalculator(file);
		setLayout(null);
		setBackground(Color.WHITE);

		JLabel startingStateLabel = new JLabel("Starting state: " + dataCalculator.getStart());
		startingStateLabel.setFont(new Font("Dialog", 3, 18));

		startingStateLabel.setBounds(10, 10, 140, 20);
		add(startingStateLabel);
	}

	private Color getColor() {
		return Color.BLACK;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		g2.setStroke(new BasicStroke(2));

		Circle[] states = dataCalculator.getCirclesData();

		g2.setColor(Color.BLACK);
		for (Circle c : states) {
			drawCircle(g, c);
		}

		SelfLoop sls[] = dataCalculator.getSelfLoops();
		for (SelfLoop s : sls) {
			drawSelfLoops(g2, s);
		}

		List<Arrow> trans = dataCalculator.getArrowsData();
		for (Arrow a : trans) {
			drawArrow(g2, a);
		}

		for (Arrow a : trans) {
			drawString(g2, a);
		}
	}

	private void drawString(Graphics g, Arrow a) {
		Graphics2D g2 = (Graphics2D) g;
		this.makeLineThicker(g2);
		this.MakeFontItalic(g2);

		int mul;
		if (a.input_symbol.length() % 2 == 0) {
			mul = a.input_symbol.length() / 2;
		} else {
			mul = a.input_symbol.length() / 2 + 1;
		}

		g2.setColor(Color.WHITE);
		g2.fillRect(a.string_pos.x, a.string_pos.y - 10, 10 * mul, 10);

		g2.setColor(getColor());
		g2.drawString(a.input_symbol, a.string_pos.x, a.string_pos.y);
	}

	private void drawSelfLoops(Graphics g, SelfLoop s) {

		Graphics2D g2 = (Graphics2D) g;
		this.makeLineThicker(g2);
		this.MakeFontItalic(g2);

		g2.setColor(getColor());

		g2.drawArc(s.centre.x - s.radius, s.centre.y - s.radius, 2 * s.radius, 2 * s.radius, 112 - s.theta, -224);
		g2.drawLine(s.p1.x, s.p1.y, s.end.x, s.end.y);
		g2.drawLine(s.p2.x, s.p2.y, s.end.x, s.end.y);

		int mul;
		if (s.input_symbol.length() % 2 == 0) {
			mul = s.input_symbol.length() / 2;
		} else {
			mul = s.input_symbol.length() / 2 + 1;
		}

		g2.setColor(Color.WHITE);
		g2.fillRect(s.string_pos.x, s.string_pos.y - 10, 10 * mul, 10);

		g2.setColor(getColor());
		g2.drawString(s.input_symbol, s.string_pos.x, s.string_pos.y);
	}

	private void drawArrow(Graphics g, Arrow a) {

		Graphics2D g2 = (Graphics2D) g;
		this.makeLineThicker(g2);
		this.MakeFontItalic(g2);

		Color c = getColor();
		g2.setColor(c);
		for (int i = 1; i < a.pts.size(); i++) {
			g.drawLine(a.getPointAt(i - 1).x, a.getPointAt(i - 1).y, a.getPointAt(i).x, a.getPointAt(i).y);
		}
		g2.drawLine(a.p1.x, a.p1.y, a.p3.x, a.p3.y);
		g2.drawLine(a.p2.x, a.p2.y, a.p3.x, a.p3.y);
	}

	private void drawCircle(Graphics g, Circle c) {

		Graphics2D g2 = (Graphics2D) g;
		this.makeLineThicker(g2);
		this.MakeFontItalic(g2);

		if (c.isFinal()) {
			g2.drawArc(c.centre.x - Circle.RADIUS + 5, c.centre.y - Circle.RADIUS + 5, Circle.DIAMETER - 10,
					Circle.DIAMETER - 10, 0, 360);
		}

		g2.drawArc(c.centre.x - Circle.RADIUS, c.centre.y - Circle.RADIUS, Circle.DIAMETER, Circle.DIAMETER, 0, 360);
		g2.drawString(c.id + "", c.centre.x - 5, c.centre.y + 5);
	}

	public void makeLineThicker(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		g2.setStroke(new BasicStroke(2));
	}

	public void MakeFontItalic(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Font myFont = new Font("Dialog", 2, 20);
		g2.setFont(myFont);
	}
}
