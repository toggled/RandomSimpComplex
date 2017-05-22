import org.joda.time.DateTime;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;

/**
 * Created by workshop on 5/22/2017.
 */

public class Scalability extends Analytics {
    int [] NArray;
    double [] averageruntimearray;
    String avgrntime_str;
    float [] parray;
    ArrayList <String> rep;
    Scalability(int [] N, double p, int t){
        super((float)p,t);
        this.NArray = Arrays.copyOf(N,N.length);
        averageruntimearray = new double[N.length];
    }
    Scalability(int [] N){
        super();
        this.NArray = Arrays.copyOf(N,N.length);
        this.parray = new float[N.length];
        for(int i = 0; i<NArray.length; i++)
            parray[i] = (float)1/(float)Math.pow(NArray[i],3);
    }
    Scalability(int [] N, float [] pa){
        super();
        this.NArray = Arrays.copyOf(N,N.length);
        this.parray = Arrays.copyOf(pa,pa.length);
    }
    void runforN(){
        String preamble = "";
        avgrntime_str = "";
        int cnt = 0;
        for(Integer n:this.NArray){
            this.N = n;
            runTtimes();
            averageruntimearray[cnt] = this.meanruntime;
            preamble+=(Integer.valueOf(n)+" ");
            avgrntime_str+=(Double.toString(this.meanruntime)+" ");
        }
        avgrntime_str = preamble+"\n"+avgrntime_str;
    }
    void runforNrunforp(int t){
        rep = new ArrayList<>();
        String preamble = "  ";
        for(Integer n:this.NArray)
            preamble+=(Integer.valueOf(n)+" ");
        rep.add(preamble);


        for(int i = 0; i<parray.length; i++){
            String appendp = (Float.toString(parray[i])+ " ");
            avgrntime_str = appendp;
            averageruntimearray = new double[NArray.length];
            for(int j = 0; j<NArray.length; j++){
                this.N = NArray[j];
                this.p = parray[i];
                this.Times = t;
                runTtimes();
                avgrntime_str+=(Double.toString(this.meanruntime)+" ");
            }
            rep.add(avgrntime_str);
        }
    }

    public void WriteRuntimes(String filepath){
        String toappend = this.type+"_ScalabalityAnalytics("+Integer.toString(this.N)+")"+new DateTime( GregorianCalendar.getInstance().getTime() ).toString("yyyy-MM-dd HH-mm-ss");
        Path path = Paths.get(filepath+"/"+toappend+".txt");
        ArrayList<String>listtowrite = new ArrayList<>();
        listtowrite.add(this.avgrntime_str);
        Write(listtowrite,path);
    }
    public void WriteforNforp(String filepath){
        String toappend = this.type+"_ScalabalityAnalytics("+Integer.toString(this.N)+")"+new DateTime( GregorianCalendar.getInstance().getTime() ).toString("yyyy-MM-dd HH-mm-ss");
        Path path = Paths.get(filepath+"/"+toappend+".txt");
        Write(rep,path);
    }
}
