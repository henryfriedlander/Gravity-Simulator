package henry.term2project;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Planet implements Agent{
	final double mass;
	Position p;
	Simulation g;
	ArrayList<Point>points=new ArrayList<Point>();

    final static double pi=Math.PI;
    final static double G_m1_plus_m2 = 4 * pi * pi;

	public Planet(double mass, double xPos, double yPos, double xVel, double yVel, Simulation c) {
		this.mass=mass;
		p=new Position(xPos,yPos,xVel,yVel);
		g=c;
		System.out.println(p.toString());
	}
	public double dist(Agent p1){
		return Math.sqrt(Math.pow(p.getX()-p1.getPosition().getX(),2)+Math.pow(p.getY()-p1.getPosition().getY(),2));
	}
	public double dist(Point2D.Double pos){
		return Math.sqrt(Math.pow(p.getX()-pos.x,2)+Math.pow(p.getY()-pos.y,2));
	}
	public ArrayList<Point> getTrail(){
		return points;
	}
	public void addPoint(Point p){
		points.add(p);
	}
    //Derivative Array For Newton's law of gravitation
    public double[] equations(double[]trv){
        double t = trv[0], x = p.getX(), y = p.getY(), vx = p.getxVel(), vy = p.getyVel();
        double r = Math.sqrt(x*x + y*y);
        double ax = - G_m1_plus_m2 * x / (r*r*r);
        double ay = - G_m1_plus_m2 * y / (r*r*r);
        double[]flow=new double[5];
        flow[0] = 1;
        flow[1] = vx;
        flow[2] = vy;
        flow[3] = ax;
        flow[4] = ay;
        return flow;
    }
    @Override
    public void timeStep(Simulation s){
        int n=5;
        double[]f=new double[n];
        double[]k1=new double[n];
        double[]k2=new double[n];
        double[]k3=new double[n];
        double[]k4=new double[n];
        double[]x_temp=new double[n];
        
        double[]x=new double[5];
        double dt=0.1;
        x[0]=0;
        x[1]=p.getX();
        x[2]=p.getY();
        x[3]=p.getxVel();
        x[4]=p.getyVel();
        x = Kepler.step(x, dt);
//        f = equations(x);
//        for (int i = 0; i < n; i++) {
//            k1[i] = dt * f[i];
//            x_temp[i] = x[i] + k1[i] / 2;
//        }
//        f = equations(x_temp);
//        for (int i = 0; i < n; i++) {
//            k2[i] = dt * f[i];
//            x_temp[i] = x[i] + k2[i] / 2;
//        }
//        f = equations(x_temp);
//        for (int i = 0; i < n; i++) {
//            k3[i] = dt * f[i];
//            x_temp[i] = x[i] + k3[i];
//        }
//        f = equations(x_temp);
//        for (int i = 0; i < n; i++)
//            k4[i] = dt * f[i];
//        for (int i = 0; i < n; i++)
//            x[i] += (k1[i] + 2 * k2[i] + 2 * k3[i] + k4[i]) / 6;
//        //x[0]=time???
        p.setX(x[1]);
        p.setY(x[2]);
        p.setxVel(x[3]);
        p.setyVel(x[4]);
    }
	@Override
	public void timeStepCircle(Simulation s){
		double Force=0;
		double speed=0;
		double Fx=0;
		double Fy=0;
		ArrayList<Agent>planets=s.getPlanets();
		p.setX(p.getX()+p.getxVel()/30.0);
		p.setY(p.getY()+p.getyVel()/30.0);
		for(int i=0;i<planets.size()-1;i++){
			Agent p1=planets.get(i);
			if(p1!=this && this!=null){
				Fx+=Constants.G*this.mass*p1.getMass()*(p1.getPosition().getX()-p.getX())/Math.pow(this.dist(p1),3);
				Fy+=Constants.G*this.mass*p1.getMass()*(p1.getPosition().getY()-p.getY())/Math.pow(this.dist(p1),3);
			}else{
				//System.out.println("null");
			}
		}
		Point2D.Double com=s.getCOM();
		System.out.println(Fx+" "+Fy);
		Force=Math.sqrt(Fx*Fx+Fy*Fy);
		double r=this.dist(com); //distance to the com
		System.out.println("dist "+r);
		System.out.println(mass);
		speed=Math.sqrt(Force*r/this.mass);
		p.setxVel(speed*(p.getY()-com.y)/r);
		p.setyVel(speed*(com.x-p.getX())/r);
	}
	@Override
	public Position getPosition() {
		return p;
	}
	@Override
	public double getMass() {
		return mass;
	}
}