package henry.term2project;

import java.util.ArrayList;

public class Kepler {
    final static double pi=Math.PI;
    final static double G_m1_plus_m2 = 4 * pi * pi;
    Simulation s;
    
    
    //Derivative Array For Newton's law of gravitation
    public Kepler(Simulation s){
    	this.s=s;
    }
    public double[] equations(double[]trv, Planet p){
        double t = trv[0], x = trv[1], y = trv[2], vx = trv[3], vy = trv[4];
        Position pos=p.getPosition();
        double r = Math.sqrt(x*x + y*y);
        double ax = - (G_m1_plus_m2) * (x) / (Math.pow(r, 3));
        double ay = - (G_m1_plus_m2) * (y) / (Math.pow(r, 3));
        /*
        ArrayList<Agent> planets=s.getPlanets();
        for(Agent newP:planets){
			if(newP!=p){
				Position newPos=newP.getPosition();
				ax+=newP.getMass()*(newPos.getX()-pos.getX())/Math.pow(p.dist(newP),3);
				ay+=newP.getMass()*(newPos.getY()-pos.getY())/Math.pow(p.dist(newP),3);
			}
		}
		*/
        double[]flow=new double[5];
        flow[0] = 1;
        flow[1] = vx;
        flow[2] = vy;
        flow[3] = ax;
        flow[4] = ay;
        return flow;
    }
    public double[] step(double[]x, double dt, Planet p1){
        int n=x.length;
        double[]f=new double[n];
        double[]k1=new double[n];
        double[]k2=new double[n];
        double[]k3=new double[n];
        double[]k4=new double[n];
        double[]x_temp=new double[n];
        f = equations(x,p1);
        for (int i = 0; i < n; i++) {
            k1[i] = dt * f[i];
            x_temp[i] = x[i] + k1[i] / 2;
        }
        f = equations(x_temp,p1);
        for (int i = 0; i < n; i++) {
            k2[i] = dt * f[i];
            x_temp[i] = x[i] + k2[i] / 2;
        }
        f = equations(x_temp,p1);
        for (int i = 0; i < n; i++) {
            k3[i] = dt * f[i];
            x_temp[i] = x[i] + k3[i];
        }
        f = equations(x_temp,p1);
        for (int i = 0; i < n; i++)
            k4[i] = dt * f[i];
        for (int i = 0; i < n; i++)
            x[i] += (k1[i] + 2 * k2[i] + 2 * k3[i] + k4[i]) / 6;
        return x;
    }
}
