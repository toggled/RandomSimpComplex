import org.apache.commons.math3.util.CombinatoricsUtils;

import java.math.BigInteger;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Created by naheed on 6/14/17.
 */
public class DynamicprobRandSimpComplex extends RandomSimplicialComplex {
    BaselineRandomHypergraph container;
    DynamicprobRandSimpComplex(int N, float p) {
        super(N, p);
        container = new BaselineRandomHypergraph(N);
    }

    double getprob(int sizeofhyperedge){
        double denom = Math.pow(2,CombinatoricsUtils.binomialCoefficient(sizeofhyperedge, sizeofhyperedge/2)) -1 ;
        return this.prob / denom;
    }

    @Override
    public void generate() {
        // Generate Random Hypergraph first

        Random rand = new Random();
        BitSet Xi = new BitSet(this.numberOfVertices+1); // initially Xi is [0,n-1] all 0's
        Xi.set(0);
        BitSet integerone = new BitSet(this.numberOfVertices+1);
        integerone.set(0); // integerone = 1
        BigInteger Max = BigInteger.valueOf((long)Math.pow(2,this.numberOfVertices) - 1);

        for(BigInteger counter = BigInteger.ZERO; counter.compareTo(Max)<0;counter = counter.add(BigInteger.ONE)){
            float u = rand.nextFloat();
            if(u<getprob(Xi.cardinality())){
                container.addEdge(Xi);
                container.numOfkhyperedges[Xi.cardinality()]++;
            }
            Xi = addbitset(Xi,integerone,this.numberOfVertices); // next hyperedge to add
//            System.out.println(counter);
        }

        // Construct Associated SC
        Collections.sort(container.hyperedges, new Comparator<BitSet>() {
            @Override
            public int compare(BitSet bit2, BitSet bit1)
            {
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


    }
}
