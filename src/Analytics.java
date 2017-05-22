import org.joda.time.DateTime;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by naheed on 5/19/17.
 */
public class Analytics implements WriteHandler{
    int N, Times;
    float p;
    double meanksimplices[];
    String analytic_repr = "";
    int interval;
    double meanksimplicesuptoTtimes[][];
    ArrayList<String> stringreprlist;

    int SpernerFamilySize;
    SimplicialComplex sc;
    HyperGraph hg;

    String type=""; //

    Analytics(int NumOfVertices, float probability){
        this.N = NumOfVertices;
        this.p = probability;
    }

    Analytics(int NumOfVertices){
        this.N = NumOfVertices;
        meanksimplices = new double[this.N+1];
    }
    Analytics(int NumberOfVertices, float probability, int numtimestorun){
        this.N = NumberOfVertices;
        this.Times = numtimestorun;
        this.p = probability;
        meanksimplices = new double[this.N+1];
    }

    void setSimplextype(String type){
        this.type = type;
    }
    void run_once(){
        analytic_repr = "";
        if(type.contains("topdown")) {
            sc = new RandomSimplicialComplex(this.N, this.p);
            sc.generate();
            analytic_repr = sc.numOfkSimplicesAsString();
            SpernerFamilySize = sc.getSize();
        }
        else if(type.contains("bottomup")) {
            sc = new RandomSimplicialComplexBottomUp(this.N, this.p);
            sc.generate();
            analytic_repr = sc.numOfkSimplicesAsString();
            SpernerFamilySize = sc.getSize();
        }
        else if(type.contains("hg")) {
            hg = new RandomHypergraph(this.N,this.p);
            hg.generate();
            analytic_repr = hg.numofkhyperedgesAsString();
        }


    }
    void runTtimes(){
            meanksimplices = new double[this.N+1];
            analytic_repr = "";
            for (int t = 1; t <= Times; t++) {
                if (type.contains("topdown")){
                    sc = new RandomSimplicialComplex(this.N, this.p);
                    sc.generate();
                    for (int i = 1; i <= this.N; i++) {
                        this.meanksimplices[i] += (double) sc.numOfkSimplices[i] / Times;
                    }
                }
                else if(type.contains("bottomup")) {
                    sc = new RandomSimplicialComplexBottomUp(this.N, this.p);
                    sc.generate();
                    for (int i = 1; i <= this.N; i++) {
                        this.meanksimplices[i] += (double) sc.numOfkSimplices[i] / Times;
                    }
                }
                else if(type.contains("hg")) {
                    hg = new RandomHypergraph(this.N,this.p);
                    hg.generate();
                    for (int i = 1; i <= this.N; i++) {
                        this.meanksimplices[i] += (double) hg.numOfkhyperedges[i] / Times;
                    }
                }

            }
            for(int i = 1; i<=this.N; i ++)
                analytic_repr+=Double.toString(this.meanksimplices[i])+" ";
    }

    void runuptoTtimes(){
        interval = Times/5;
        meanksimplicesuptoTtimes = new double[5][this.N + 1];
        stringreprlist = new ArrayList<>(5);
        for(int T = interval, idx = 0; T <= interval*5; T+=interval, idx++) {
            this.Times = interval;
            runTtimes();
            meanksimplicesuptoTtimes[idx] = Arrays.copyOf(meanksimplices,meanksimplices.length);
            stringreprlist.add(this.analytic_repr);
        }
    }

    @Override
    public void Write(String filepath) {
        String toappend = type+"Analytics("+Integer.toString(this.N)+","+Float.toString(this.p)+")"+new DateTime( GregorianCalendar.getInstance().getTime() ).toString("yyyy-MM-dd HH-mm-ss");

        try {
            Files.write(Paths.get(filepath+"/"+toappend+".txt"),analytic_repr.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void WriteuptoTtimes(String filepath){
        String toappend = type+"Analytics("+Integer.toString(this.N)+","+Float.toString(this.p)+")"+new DateTime( GregorianCalendar.getInstance().getTime() ).toString("yyyy-MM-dd HH-mm-ss");
        Path p = Paths.get(filepath+"/"+toappend+".txt");
        Write(this.stringreprlist,p);
    }

    @Override
    public void Write(List<String> linestowrite, Path filepath) {
        try {
            Files.write(filepath, linestowrite, Charset.forName("UTF-8"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
