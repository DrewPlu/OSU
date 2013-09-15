package edu.osu.cse.misc.graph.impl;

import javax.swing.JFrame;

import edu.osu.cse.misc.graph.wrappers.function._2d.AbstractFunction2D;
import edu.osu.cse.misc.graph.wrappers.graph.GraphPanel2D;





public class Testing {

	public static void main(String[] args) {
		testPlotter();
		/*testGraph(
				new ParametricFunction2D("t", "2t"),
				new ParametricFunction2D("2t^2", "t^2")
				);*/
	}

	private static void testGraph(AbstractFunction2D...functions) {
		JFrame frame = new JFrame();

		GraphPanel2D graph = new GraphPanel2D(-10, 10, -10, 5);
		
		for (AbstractFunction2D function : functions) {
			graph.addFunction(function);
		}

		frame.add(graph);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	private static void testPlotter() {
		JFrame frame = new JFrame();
		
		
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}