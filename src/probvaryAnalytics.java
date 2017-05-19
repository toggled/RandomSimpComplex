import org.joda.time.DateTime;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.GregorianCalendar;

/**
 * Created by naheed on 5/19/17.
 */
public class probvaryAnalytics extends Analytics implements WriteHandler {
    float prob[];
    double [][] meanksimplicesperp;
    probvaryAnalytics(int numofvertices,float p[]){
        super(numofvertices);
        prob = new float[p.length];
        prob = Arrays.copyOf(p,p.length);
        meanksimplicesperp = new double[p.length][numofvertices+1];
    }
    void runforp(int T){
        /*
        run T times for each probability value
         */
        for(int i = 0; i<prob.length; i++){
            this.p = prob[i];
            this.Times = T;
            this.runTtimes();
            meanksimplicesperp[i] = Arrays.copyOf(meanksimplices,meanksimplices.length);
        }
    }
    public void Write(String filepath) {
        String toappend = "Analytics("+Integer.toString(this.N)+","+Float.toString(this.p)+")"+new DateTime( GregorianCalendar.getInstance().getTime() ).toString("yyyy-MM-dd HH:mm:ss");
        for(int i = 0; i<=this.N; i ++)
            analytic_repr+=Double.toString(this.meanksimplices[i])+" ";
        try {
            Files.write(Paths.get(filepath+"/"+toappend+".txt"),analytic_repr.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
