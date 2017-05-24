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

    double meanruntime;
    double meanksimplices[];
    String analytic_repr = "";
    int interval;
    double meanksimplicesuptoTtimes[][];
    ArrayList<String> stringreprlist;

    int SpernerFamilySize;
    SimplicialComplex sc;
    HyperGraph hg;

    String type=""; //
    Analytics(){}
    Analytics(float p, int Times){
        this.p = p;
        this.Times = Times;
    }
    Analytics(int NumOfVertices, float probability){
        this.N = NumOfVertices;
        this.p = probability;
        meanruntime = 0;
    }

    Analytics(int NumOfVertices){
        this.N = NumOfVertices;
        meanksimplices = new double[this.N+1];
        meanruntime = 0;
    }
    Analytics(int NumberOfVertices, float probability, int numtimestorun){
        this.N = NumberOfVertices;
        this.Times = numtimestorun;
        this.p = probability;
        meanksimplices = new double[this.N+1];
        meanruntime = 0;
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
            SpernerFamilySize = hg.getSize();
            System.out.println(hg.getSize());
            analytic_repr = hg.numofkhyperedgesAsString();
        }
        else if(type.contains("baseh")) {
            hg = new BaselineRandomHypergraph(this.N,this.p);
            hg.generate();
            SpernerFamilySize = hg.getSize();
            analytic_repr = hg.numofkhyperedgesAsString();
        }
        else if(type.contains("arsc")){ // arsc
            sc = new AssociatedRandSimplicialComplex(this.N, this.p);
            sc.generate();
            analytic_repr = sc.numOfkSimplicesAsString();
            SpernerFamilySize = sc.getSize();
            System.out.println( "AssociatedSC: \n "+sc.toString());
            System.out.println(" -- - - - -- - - - - - - -");
        }

    }
    void runTtimes(){
            meanksimplices = new double[this.N+1];
            analytic_repr = "";
            double mean_maxksimplices[] = new double[this.N + 1];
            for (int t = 1; t <= Times; t++) {
//                System.out.println("iteration: "+t);
                if (type.contains("topdown")){
                    sc = new RandomSimplicialComplex(this.N, this.p);
                    sc.generate();
                    for (int i = 1; i <= this.N; i++) {
                        this.meanksimplices[i] += (double) sc.numOfkSimplices[i] / Times;
                    }
                    meanruntime += (double)sc.runtime/Times;
                }
                else if(type.contains("bottomup")) {
                    sc = new RandomSimplicialComplexBottomUp(this.N, this.p);
                    sc.generate();
                    for (int i = 1; i <= this.N; i++) {
                        this.meanksimplices[i] += (double) sc.numOfkSimplices[i] / Times;
                    }
                    meanruntime += (double)sc.runtime/Times;

                }
                else if(type.contains("hg")) {
                    hg = new RandomHypergraph(this.N,this.p);
                    hg.generate();
                    for (int i = 1; i <= this.N; i++) {
                        this.meanksimplices[i] += (double) hg.numOfkhyperedges[i] / Times;
                    }
                    meanruntime += (double)hg.runtime/Times;
                    mean_maxksimplices[(hg.maxcardinality)] += (double)1/Times;
                }
                else if(type.contains("baseh")) {
                    hg = new BaselineRandomHypergraph(this.N,this.p);
                    hg.generate();
                    for (int i = 1; i <= this.N; i++) {
                        this.meanksimplices[i] += (double) hg.numOfkhyperedges[i] / Times;
                    }
                    meanruntime += (double)hg.runtime/Times;
                    mean_maxksimplices[(hg.maxcardinality)] += (double)1/Times;
                }
                else if(type.contains("arsc")) { // arsc
                    System.out.println(this.type + "isarsc");
                    sc = new AssociatedRandSimplicialComplex(this.N, this.p);
                    sc.generate();
                    for (int i = 1; i <= this.N; i++) {
                        this.meanksimplices[i] += (double) sc.numOfkSimplices[i] / Times;
                    }
                    meanruntime += (double) sc.runtime / Times;
                    mean_maxksimplices[(sc.maxcardinality)] += (double) 1 / Times;
                }
            }
            String tempformaxcard = "";
            for(int i = 1; i<=this.N; i ++){
                analytic_repr+=Double.toString(this.meanksimplices[i])+" ";
                tempformaxcard+=Double.toString(mean_maxksimplices[i])+" ";
            }
        stringreprlist = new ArrayList<>();
        String temp = "";
        for(int i = 1; i<=this.N; i ++){
            temp+=(String.valueOf(i)+" ");
        }
        stringreprlist.add(temp);
        stringreprlist.add(tempformaxcard);
    }

    void runuptoTtimes(){
        interval = Times/5;
        meanksimplicesuptoTtimes = new double[5][this.N + 1];
        stringreprlist = new ArrayList<>(5+1);

        /* Adding all poissible k values */
        String temp = "  ";
        for (int i = 1; i <= this.N; i++) {
            temp+=(Integer.valueOf(i)+" ");
        }
        stringreprlist.add(temp);

        for(int T = interval, idx = 0; T <= interval*5; T+=interval, idx++) {
            System.out.println(this.type+" Running "+T +"times");
            this.Times = interval;
            runTtimes();
            meanksimplicesuptoTtimes[idx] = Arrays.copyOf(meanksimplices,meanksimplices.length);
            stringreprlist.add(Integer.valueOf(T)+" "+this.analytic_repr);
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
