import java.util.*;

/**
 * Created by naheed on 5/17/17.
 */
public class SimplicialComplex {
    int numberOfVertices;
    ArrayList<BitSet> simplices;
    SimplicialComplex(int N){
        this.numberOfVertices = N;
        simplices = new ArrayList<>();
    }
    private void addMaximalSimplex(BitSet simplex){
        simplices.add(simplex);
    }
    public String toString(){
        String s = "";
        for(BitSet b:simplices)
            s+=(bitsettoString(b)+"\n");
        return s;
    }
    private String bitsettoString(BitSet b){
        /**
         * Return comma-separated list of integer-labeled vertices i.e a hyperedge for instance, 1,2,5,8 or 0,3,4,6 etc.
         */
        String s = "";
        for(int id = 0; id <= this.numberOfVertices+1; id++)
            if(b.get(id) == true)
                s+=(String.valueOf(id)+",");
        return s;
    }

    private String bitsettobitsequence(BitSet b){
        /**
         * Return the bitsequence (0110110 etc) representation of the bitset
         */
        StringBuilder s = new StringBuilder();
        for( int i = b.length(); i >-1;  i-- )
        {
            s.append( b.get( i ) == true ? 1: 0 );
        }
        return s.toString();
    }

}
