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
	public ArrayList<Agent>planets=new ArrayList<Agent>();

	private static final double G_m1_plus_m2 = 4.0 * Math.PI * Math.PI;
	public Simulation() {
		restart();
	}
	public void restart(){
		double r_a = 100;
		double eccentricity = .3;
		double a = r_a / (1 + eccentricity);
		double vy0 = Math.sqrt(G_m1_plus_m2 * (2.0 / r_a - 1.0 / a));
		

		double r_a1 = 150;
		double eccentricity1 = 0;
		double a1 = r_a / (1 + eccentricity1);
		
		double vy01 = Math.sqrt(G_m1_plus_m2 * (2.0 / r_a - 1.0 / a1));
		planets.removeAll(planets);
		planets.add(new Planet(1000, r_a, 0, 0, vy0, this));
		planets.add(new Planet(1000, r_a1, 0, 0, vy01, this));
	}
	public void takeSteps(int numSteps){
		for(int i=0;i<numSteps;i++){
			takeSteps();
		}
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
	/*
	public void cV(){
		for(Agent p:)
	}
	public void calculateVelocities(){
		for(Agent p1:planets){
			for(Agent p2:planets){
				if(p1!=p2){
					Position pos1=p1.getPosition();
					Position pos2=p2.getPosition();
					Double dist=p1.dist(p2);
					double speed = Math.sqrt((p2.getMass()+p1.getMass()) * (2.0 / r_a - 1.0 / a));
					vx0+=speed*(pos2.getX()-pos1.getX())/;
				}
			}
		}
	}
	*/
}
