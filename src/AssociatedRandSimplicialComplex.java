import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by workshop on 5/22/2017.
 */

public class AssociatedRandSimplicialComplex extends RandomSimplicialComplex {
    RandomHypergraph container;
    AssociatedRandSimplicialComplex(int N, float p) {
        super(N, p);
        container = new RandomHypergraph(N,p);
    }
    public void generate() {
        long startTime = System.nanoTime();
        container.generate();
        Collections.sort(container.hyperedges, new Comparator<BitSet>() {
            @Override
            public int compare(BitSet bit2, BitSet bit1)
            {
                // sort in descending order of bitset cardinality (number of 1's)
                return  bit1.cardinality() - bit2.cardinality();
            }
        });

        for(int i = 0; i<container.hyperedges.size(); i++){
            BitSet potentialsuperset = new BitSet(this.numberOfVertices+1);
            potentialsuperset = (BitSet)container.hyperedges.get(i).clone();
            for(int j = container.hyperedges.size()-1; j>i; j--){ // from last subset to the the i'th one
                BitSet potentialsubset = new BitSet(numberOfVertices+1);
                potentialsubset = (BitSet)container.hyperedges.get(j).clone();
                //println(potentialsubset);
                //println(potentialsuperset);
                if(isSubset(potentialsubset,potentialsuperset)){
                    container.hyperedges.remove(j);
                }
            }
        }

        for(BitSet b:container.hyperedges){
            this.addMaximalSimplex(b);
            this.numOfkSimplices[b.cardinality()]++;
        }

        long stopTime = System.nanoTime();
        this.runtime = stopTime - startTime;
    }
}
