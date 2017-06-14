import org.joda.time.DateTime;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;

/**
 * Created by naheed on 5/19/17.
 */
public class probvaryAnalytics extends Analytics{
    /**
     * For a fixed N and for each probability p \in {p_1,p_2,p_3,\dots,p_n}, run T times and collect analytics
     */
    double prob[];
    double [][] meanksimplicesperp;

    probvaryAnalytics(int numofvertices, double[] p){
        super(numofvertices);
        prob = new double[p.length];
        prob = Arrays.copyOf(p,p.length);
        meanksimplicesperp = new double[p.length][numofvertices+1];
        stringreprlist = new ArrayList<>(p.length);
    }
    void runforp(int T){
        /*
        run T times for each probability value
         */
        String preamble = " ";
        for (int i = 0; i <= this.N ; i++) {
            preamble+= (" "+Integer.valueOf(i));
        }
        stringreprlist.add(preamble);

        for(int i = 0; i<prob.length; i++){
            this.p = (float)prob[i];
            this.Times = T;
            this.runTtimes();
            meanksimplicesperp[i] = Arrays.copyOf(meanksimplices,meanksimplices.length);
            stringreprlist.add(Float.toString(this.p)+" "+this.analytic_repr);
        }
    }
    void Writeprobvaryanalytics(String filepath){
        String toappend = type+"ProbVaryAnalytics("+Integer.toString(this.N)+","+Float.toString(this.p)+")"+new DateTime( GregorianCalendar.getInstance().getTime() ).toString("yyyy-MM-dd HH-mm-ss");
        Path p = Paths.get(filepath+"/"+toappend+".txt");
        Write(this.stringreprlist,p);
    }
}
