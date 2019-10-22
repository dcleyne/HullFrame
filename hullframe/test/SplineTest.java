package hullframe.test;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.JFrame;

import hullframe.drawing.Spline2D;

public class SplineTest extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	Spline2D cubcurve = new Spline2D(new double[] {30, 150, 200, 350}, new double[] {400, 400, 500, 450});
		
	public SplineTest()
	{
		super("Spline Test");
		setSize(400,550);
	}
	
	public void paint(Graphics g) 
	{
		Graphics2D g2d = (Graphics2D) g;		

		Polygon p = new Polygon();

		for (int x = 30; x <= 250; x++)
		{
			double[] point = cubcurve.getPoint(x);
			p.addPoint((int)point[0], (int)point[1]);
		}
			
		g2d.draw(p);
	}

	public static void main(String[] args) 
	{
	    (new SplineTest()).setVisible(true);
	}
	
}
