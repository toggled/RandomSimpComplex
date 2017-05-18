/**
 * Created by naheed on 5/17/17.
 */

import java.util.*;

public class RandomSimplicialComplex extends SimplicialComplex implements RandomASCGenerator{
    float prob;
    RandomSimplicialComplex(int N, float p){
        super(N);
        this.prob = p;
    }

    @Override
    public void generate() {
        /**
         * Generate a random Simplicial Complex
         */



    }
    private boolean isSubset(BitSet a, BitSet b){
        /*
        returns True, if a is a subset of b,
                False, otherwise
         */
        if(a.cardinality()>=b.cardinality()) return false; // a shouldn't be larger or equal in cardinality.
        else{
            BitSet a_copy = (BitSet) a.clone();
            a_copy.andNot(b);
            return a_copy.length() == 0;
        }
    }

}
