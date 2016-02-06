package henry.term2project;

public class Kepler {
    final static double pi=Math.PI;
    final static double G_m1_plus_m2 = 4 * pi * pi;
    Simulation s;
    
    
    //Derivative Array For Newton's law of gravitation
    public Kepler(Simulation s){
    	this.s=s;
    }
    public double[] equations(double[]trv){
        double t = trv[0], x = trv[1], y = trv[2], vx = trv[3], vy = trv[4];
        double r = Math.sqrt(x*x + y*y);
        double ax = - G_m1_plus_m2 * (x) / (r*r*r);
        double ay = - G_m1_plus_m2 * (y) / (r*r*r);
        double[]flow=new double[5];
        flow[0] = 1;
        flow[1] = vx;
        flow[2] = vy;
        flow[3] = ax;
        flow[4] = ay;
        return flow;
    }
    public double[] step(double[]x, double dt){
        int n=x.length;
        double[]f=new double[n];
        double[]k1=new double[n];
        double[]k2=new double[n];
        double[]k3=new double[n];
        double[]k4=new double[n];
        double[]x_temp=new double[n];
        f = equations(x);
        for (int i = 0; i < n; i++) {
            k1[i] = dt * f[i];
            x_temp[i] = x[i] + k1[i] / 2;
        }
        f = equations(x_temp);
        for (int i = 0; i < n; i++) {
            k2[i] = dt * f[i];
            x_temp[i] = x[i] + k2[i] / 2;
        }
        f = equations(x_temp);
        for (int i = 0; i < n; i++) {
            k3[i] = dt * f[i];
            x_temp[i] = x[i] + k3[i];
        }
        f = equations(x_temp);
        for (int i = 0; i < n; i++)
            k4[i] = dt * f[i];
        for (int i = 0; i < n; i++)
            x[i] += (k1[i] + 2 * k2[i] + 2 * k3[i] + k4[i]) / 6;
        return x;
    }
    public void integrate(double[]trv,double dt,double t_max, double accuracy){

        while (true) {
        	trv = step(trv, dt);
//            double y_save = trv[2];
//            step(trv, dt);
//            double t = trv[0], x = trv[1], y = trv[2], vx = trv[3], vy = trv[4];
//            if (x > 0 && y * y_save < 0) {
//                step(trv, -y);
//                break;
//            }
        }
    }
}
