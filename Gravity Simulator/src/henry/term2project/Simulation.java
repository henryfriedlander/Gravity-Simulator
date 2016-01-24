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
	
	public Simulation() {
		planets.add(new Planet(1000,0,0,0,0,this));
		planets.add(new Planet(8,60,0,0,0,this));
		planets.add(new Planet(10,60,30,0,0,this));
		planets.add(new Planet(10,-20,30,0,0,this));
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
