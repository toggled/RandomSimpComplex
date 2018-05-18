import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by workshop on 5/22/2017.
 */

public class AssociatedRandSimplicialComplex extends RandomSimplicialComplex {
    RandomHypergraph container;
    AssociatedRandSimplicialComplex(int N, double p) {
        super(N, p);
        container = new RandomHypergraph(N,p);
    }
    public void generate() {
        long startTime = System.nanoTime();
        container.generate();
//        System.out.println("The hypergraph: \n "+container.toString());
        Collections.sort(container.hyperedges, new Comparator<BitSet>() {
            @Override
            public int compare(BitSet lhs, BitSet rhs)
            {
                // sort in descending order of bitset cardinality (number of 1's)
//                return  bit1.cardinality() - bit2.cardinality();
                if (lhs.equals(rhs)) return 0;
                BitSet xor = (BitSet)lhs.clone();
                xor.xor(rhs);
                int firstDifferent = xor.length()-1;
                if(firstDifferent==-1)
                    return 0;

                return rhs.get(firstDifferent) ? 1 : -1;
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
