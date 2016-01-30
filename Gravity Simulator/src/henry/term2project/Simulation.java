package henry.term2project;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPanel;

/*TODO
asteriods
3D (new classes)
Collisions (distance to radii)
tails of a planet (array on the screen)
add Agent class
add seperate classes
*/


public class Simulation extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static  ArrayList<Agent>planets=new ArrayList<Agent>();

    final static double pi=Math.PI;
    final static double G_m1_plus_m2 = 4 * pi * pi;
    private double dt;
	
	public Simulation() {
		this.dt=0.1;
		double r_aphelion=100,eccentricity=0.0167;
		double a=r_aphelion / (1 + eccentricity);
		double T=Math.pow(a, 1.5);
		double vy = Math.sqrt(G_m1_plus_m2*(2.0 / r_aphelion - 1.0/a));
	planets.add(new Planet(1000,r_aphelion,0,0,vy,this));
	}
	public void takeSteps(){
		for(Agent p:planets){
			p.timeStep(this);
		}
	}
	public ArrayList<Agent> getPlanets(){
		return planets;
	}
	public Point2D.Double getCOM(){
		double x=0;
		double y=0;
		double M=0;//total mass 
		for(Agent p:planets){
			Position pos=p.getPosition();
			x+=p.getMass()*pos.getX();
			y+=p.getMass()*pos.getY();
			M+=p.getMass();
		}
		return (new Point2D.Double(x/M,y/M));
	}
}
