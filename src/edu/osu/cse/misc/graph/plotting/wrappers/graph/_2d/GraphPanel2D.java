package edu.osu.cse.misc.graph.plotting.wrappers.graph._2d;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.LinkedList;

import edu.osu.cse.misc.graph.plotting.wrappers.function._2d.AbstractFunction2D;
import edu.osu.cse.misc.graph.plotting.wrappers.graph.AbstractGraphPanel;

public class GraphPanel2D extends AbstractGraphPanel {

	private float xMin, xMax;
	private float yMin, yMax;
	private float xInterval = 1f, yInterval = 1f;

	private LinkedList<AbstractFunction2D> functions = new LinkedList<>();

	/**
	 * Constructs a 2D coordinate plane.
	 * 
	 * @param xMin Min x value
	 * @param xMax Max x value
	 * @param yMin Min y value
	 * @param yMax Max y value
	 */
	public GraphPanel2D(float xMin, float xMax, float yMin, float yMax) {
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
	}

	/**
	 * @see {@link #convertToScreen(float, float)}
	 */
	public Point convertToScreen(Coordinate2D coord) {
		return convertToScreen(coord.getX(), coord.getY());
	}

	/**
	 * Converts the given x/y coordinates to the Point relative to the panel holding this Graph.
	 * <br />For example, convertToScreen(0, 0) would return the point of the origin, possibly
	 * the center point, depending on whether the graph is shifted or not.
	 * 
	 * @param x The x-value of the coordinate.
	 * @param y The y-value of the coordinate
	 * @return A Point relative to the panel holding this Graph marking the coordinate.
	 */
	public Point convertToScreen(float x, float y) {
		y = -y; //coord system y-direction is flipped from swing's
		Point center = this.getCenterPoint();
		return new Point((int) (center.x + (x * this.getXInterval())), (int) (center.y + (y * this.getYInterval())));
	}

	/** The sum of the absolute values of xMin and xMax */
	public float getXLength() {
		return Math.abs(this.xMin) + Math.abs(this.xMax);
	}

	/** The sum of the absolute values of yMin and yMax */
	public float getYLength() {
		return Math.abs(this.yMin) + Math.abs(this.yMax);
	}

	public void setXInterval(float xInterval) {
		this.xInterval = xInterval;
	}

	public void setYInterval(float yInterval) {
		this.yInterval = yInterval;
	}

	/**
	 * The ratio of the pixel width of the Graph and the x-axis length.
	 * 
	 * @return (width/length) respectively based on the aforementioned description.
	 */
	public float getXInterval() {
		return this.getPreferredSize().width / this.getXLength();
	}

	/**
	 * The ratio of the pixel height of the Graph and the y-axis length.
	 * 
	 * @return (height/length) respectively based on the aforementioned description.
	 */
	public float getYInterval() {
		return this.getPreferredSize().height / this.getYLength();
	}

	/**
	 * Checks whether the given Coordinate is within the visible bounds of this Graph.
	 * 
	 * @param coord A Coordinate2D object.
	 * @return <tt>true</tt> or <tt>false</tt> whether the point is within the Graph's bounds or not.
	 */
	public boolean contains(Coordinate2D coord) {
		return (coord.getX() >= this.xMin && coord.getX() <= this.xMax) && (coord.getY() >= this.yMin && coord.getY() <= this.yMax);
	}

	@Override
	protected void paintComponent(Graphics g1) {
		super.paintComponent(g1);
		Graphics2D g = (Graphics2D) g1;

		g.setRenderingHint(
		        RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);
		    g.setRenderingHint(
		        RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		    g.setRenderingHint(
		        RenderingHints.KEY_FRACTIONALMETRICS,
		        RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		
		drawGrid(g);
		drawFunctions(g);
	}

	/** Draws the grid lines for this graph */
	private void drawGrid(Graphics2D g) {
		for (float y = 0; y <= this.getPreferredSize().height; y += (this.getYInterval() * this.yInterval)) {
			g.drawLine(0, (int) y, this.getPreferredSize().width, (int) y);
		}
		for (float x = 0; x <= this.getPreferredSize().width; x += (this.getXInterval() * this.xInterval)) {
			g.drawLine((int) x, 0, (int) x, this.getPreferredSize().height);
		}
		Point center = this.getCenterPoint();
		g.setStroke(new BasicStroke(3));
		g.drawLine(0, center.y, this.getPreferredSize().width, center.y);
		g.drawLine(center.x, 0, center.x, this.getPreferredSize().height);
		g.setStroke(new BasicStroke(1.5f));
	}

	/** Draws the functions that are valid on this graph */
	private void drawFunctions(Graphics2D g) {
		for (int i = 0; i < this.functions.size(); i++) {
			g.setColor(COLORS[i % COLORS.length]);
			AbstractFunction2D function = this.functions.get(i);
			if (function.isValid()) {
				Coordinate2D[] points = function.evaluateFrom(this.xMin, this.xMax, 0.05f);
				for (int p = 0; p < points.length; p++) {
					if (p < points.length - 1) {
						Point cur = this.convertToScreen(points[p]);
						Point next = this.convertToScreen(points[p+1]);
						if (this.contains(cur)) {
							g.drawLine((int) cur.getX(), (int) cur.getY(), (int) next.getX(), (int) next.getY());
						}
					}
				}
			}
			else {
				this.functions.remove(function);
			}
		}
	}

	/**
	 * Adds a function to this Graph to be plotted.
	 * 
	 * @param function A 2D function to add.
	 */
	public void addFunction(AbstractFunction2D function) {
		this.functions.add(function);
		this.repaint();
		System.out.println("New size: " + this.functions.size());
	}

	/**
	 * Removes a function from this graph.
	 * 
	 * @param function A 2D function to be removed.
	 */
	public void removeFunction(AbstractFunction2D function) {
		this.functions.remove(function);
		this.repaint();
	}

	public void removeFunction(int index) {
		this.functions.remove(index);
		this.repaint();
	}

}
