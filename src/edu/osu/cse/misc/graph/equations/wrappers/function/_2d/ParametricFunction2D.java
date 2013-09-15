package edu.osu.cse.misc.graph.equations.wrappers.function._2d;

import edu.osu.cse.misc.graph.equations.wrappers.graph.Coordinate2D;

public class ParametricFunction2D extends AbstractFunction2D {

	private PolynomialFunction2D xT, yT;

	public ParametricFunction2D(String xT, String yT) {
		this(new PolynomialFunction2D(xT), new PolynomialFunction2D(yT));
	}

	public ParametricFunction2D(PolynomialFunction2D xT, PolynomialFunction2D yT) {
		this.xT = xT.overrideParameter("t");
		this.yT = yT.overrideParameter("t");
	}

	@Override
	public Coordinate2D evaluateAt(double value) {
		double x = this.xT.evaluateAt(value).getY();
		double y = this.yT.evaluateAt(value).getY();
		return new Coordinate2D(x, y);
	}

	@Override
	public String getParameter() {
		return "t";
	}
	
	@Override
	public boolean ensureValidity() {
		return xT.ensureValidity() && yT.ensureValidity();
	}

	@Override
	public ParametricFunction2D restrict(double valueMin, double valueMax) {
		return (ParametricFunction2D) super.restrict(valueMin, valueMax);
	}


}