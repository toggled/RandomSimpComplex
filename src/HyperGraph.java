import org.joda.time.DateTime;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by naheed on 5/21/17.
 */
public abstract class HyperGraph  implements WriteHandler {
    int numberOfVertices;
    List<BitSet> hyperedges;
    int numOfkhyperedges[];
    long runtime;
    HashMap <BitSet,Integer> simplextotimemap;
    int filtration;
    BitSet max;
    int maxcardinality = 0;
    String stringrep = null;

    HyperGraph(){
        this.simplextotimemap = new HashMap<>();
        filtration = 1;
    }
    HyperGraph(int N){
        this.simplextotimemap = new HashMap<>();
        this.numberOfVertices = N;
        hyperedges = new ArrayList<>();
        this.numOfkhyperedges = new int[N+1];
        runtime = 0;
        filtration = 1;
    }
    void addEdge(BitSet b){
        this.hyperedges.add(b);
        simplextotimemap.putIfAbsent(b,filtration++);
        if(b.cardinality()>maxcardinality) {
            maxcardinality = b.cardinality();
            max = (BitSet) b.clone();
        }
    }

    public String toString(){
//        System.out.println("The simplicial complex: ");
        if (stringrep == null) {
           stringrep = "";
            for (BitSet b : hyperedges)
                stringrep += (bitsettoString(b) + "/");
        }
        return stringrep;
    }

    String numofkhyperedgesAsString(){
        String repr_numksimp = "";
        for(int i = 1; i<= this.numberOfVertices; i++){
            repr_numksimp+= (Integer.toString(this.numOfkhyperedges[i])+" ");
        }
        return repr_numksimp;
    }

    protected String bitsettoString(BitSet b){
        /**
         * Return comma-separated list of integer-labeled vertices i.e a hyperedge for instance, 1,2,5,8 or 0,3,4,6 etc.
         */
//        String s = "";
//        for(int id = 0; id < this.numberOfVertices; id++)
//            if(b.get(id) == true)
//                s+=(String.valueOf(id)+",");
//        s = s.substring(0,s.length()-1);
//        return s;
        String s = "";
        for(int id = this.numberOfVertices-1; id >-1 ; id--)
            if(b.get(id) == true)
                s+=(String.valueOf(id)+",");
        s = s.substring(0,s.length()-1);
        return s;
    }

//    protected String bitsettobitsequence(BitSet b){
//        /**
//         * Return the bitsequence (0110110 etc) representation of the bitset
//         */
//        StringBuilder s = new StringBuilder();
//        for( int i = b.length(); i >-1;  i-- )
//        {
//            s.append( b.get( i ) == true ? 1: 0 );
//        }
//        return s.toString();
//    }

    int getSize(){
        /**
         * Return the Size of the Hyperedge
         */
        return this.hyperedges.size();
    }
    @Override
    public void Write(String path){
        String toappend = "Hypergraph("+Integer.toString(this.numberOfVertices)+","+Integer.toString(this.hyperedges.size())+")"+new DateTime( GregorianCalendar.getInstance().getTime() ).toString("yyyy-MM-dd HH-mm-ss");


        try {
            Files.write(Paths.get(path+"/"+toappend+".txt"),this.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Write(List<String> linestowrite, Path filepath) {
        try {
            Files.write(filepath, linestowrite, Charset.forName("UTF-8"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    abstract void generate();
}
