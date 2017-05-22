import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import org.joda.time.DateTime;

/**
 * Created by naheed on 5/17/17.
 */

public abstract class SimplicialComplex extends HyperGraph implements WriteHandler {

    ArrayList<BitSet> simplices;
    int numOfkSimplices[];
    SimplicialComplex(){
        super();
    }
    SimplicialComplex(int N){
        super(N);
        simplices = new ArrayList<>();
        this.numOfkSimplices = new int[N+1];
    }
    protected void addMaximalSimplex(BitSet simplex){
        simplices.add(simplex);
    }
    public String toString(){
        System.out.println("The simplicial complex: ");
        String s = "";
        for(BitSet b:simplices)
            s+=(bitsettoString(b)+"\n");
        return s;
    }

    public String numOfkSimplicesAsString(){
        /**
         * Return the String representation of numOfkSimplices[] array for analytic purposes
         */
        String repr_numksimp = "";
        for(int i = 1; i<= this.numberOfVertices; i++){
            repr_numksimp+= (Integer.toString(this.numOfkSimplices[i])+" ");
        }
        return repr_numksimp;
    }


    int getSize(){
        /**
         * Return the Size of the "Simplicial complex"/"Sperner family"/Anti-chain
         */
        return this.simplices.size();
    }

    @Override
    public void Write(String path){
        String toappend = "("+Integer.toString(this.numberOfVertices)+","+Integer.toString(this.simplices.size())+")"+new DateTime( GregorianCalendar.getInstance().getTime() ).toString("yyyy-MM-dd HH-mm-ss");


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
