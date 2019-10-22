package hullframe.test;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.CubicCurve2D;

import javax.swing.JFrame;

public class BezierTest extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	CubicCurve2D cubcurve = new CubicCurve2D.Float(30, 400, 150, 400, 200, 500, 350, 450);
	
	public BezierTest()
	{
		super("Bezier Test");
		setSize(400,550);
	}
	
	public void paint(Graphics g) 
	{
		Graphics2D g2d = (Graphics2D) g;		
		
		g2d.draw(cubcurve);
	}

	public static void main(String[] args) 
	{
	    (new BezierTest()).setVisible(true);
	}
	
}
