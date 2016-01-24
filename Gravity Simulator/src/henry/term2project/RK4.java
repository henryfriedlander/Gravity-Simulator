package henry.term2project;

public class RK4 {
        public static double[] step(double[]x, double dt){
            int n=x.length;
            double[]f=new double[n];
            double[]k1=new double[n];
            double[]k2=new double[n];
            double[]k3=new double[n];
            double[]k4=new double[n];
            double[]x_temp=new double[n];
            f = Kepler.equations(x);
            for (int i = 0; i < n; i++) {
                k1[i] = dt * f[i];
                x_temp[i] = x[i] + k1[i] / 2;
            }
            f = Kepler.equations(x_temp);
            for (int i = 0; i < n; i++) {
                k2[i] = dt * f[i];
                x_temp[i] = x[i] + k2[i] / 2;
            }
            f = Kepler.equations(x_temp);
            for (int i = 0; i < n; i++) {
                k3[i] = dt * f[i];
                x_temp[i] = x[i] + k3[i];
            }
            f = Kepler.equations(x_temp);
            for (int i = 0; i < n; i++)
                k4[i] = dt * f[i];
            for (int i = 0; i < n; i++)
                x[i] += (k1[i] + 2 * k2[i] + 2 * k3[i] + k4[i]) / 6;
            return x;
        }
}
