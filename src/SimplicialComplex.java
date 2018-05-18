import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import org.joda.time.DateTime;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
        this.maxcardinality = 0;
    }

    protected void addMaximalSimplex(BitSet simplex){
        simplices.add(simplex);
        this.simplextotimemap.putIfAbsent(simplex,filtration++);
        if(simplex.cardinality()>this.maxcardinality) {
            maxcardinality = simplex.cardinality();
            this.max = (BitSet) simplex.clone();
        }
    }

    public String toString(){
//        System.out.println("The simplicial complex: ");
//        String s = "";
//        for(BitSet b:simplices)
//            s+=(bitsettoString(b)+"\n");
//        return s;

        if (stringrep == null) {
            stringrep = "";
            for (BitSet b : simplices)
                stringrep += (bitsettoString(b) + "/");
        }
        return stringrep.substring(0,stringrep.length()-1);
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
    void clear(){
        simplices.clear();
        numOfkSimplices = null;
    }

    protected String getMD5repr(){
        MessageDigest messageDigest;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(this.toString().getBytes());
            byte[] messageDigestMD5 = messageDigest.digest();

            for (byte bytes : messageDigestMD5) {
                stringBuffer.append(String.format("%02x", bytes & 0xff));
            }

        }catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();

        }
        return stringBuffer.toString();
    }
}
