package edu.osu.cse.misc.graph.equations.impl.plotter.components.tabs;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

public class Overview extends JPanel {

	private DefaultListModel<String> model = new DefaultListModel<>();
	
	private JList<String> list = new JList<String>() {{
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}};
	
	public Overview() {
		addFunction("f(x)=sin(x)");
		addFunction("x(t)=3t");
		addFunction("y(t)=t");
		this.add(list);
	}
	
	public void addFunction(String function) {
		model.addElement(function);
	}
	
	public void removeFunction(String function) {
		for (int i = 0; i < model.size(); i++) {
			if (model.get(i).equals(function)) {
				model.remove(i);
				return;
			}
		}
	}
	
}