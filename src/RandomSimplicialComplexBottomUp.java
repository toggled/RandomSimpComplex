import org.apache.commons.math3.util.CombinatoricsUtils;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

/**
 * Created by naheed on 5/20/17.
 */

public class RandomSimplicialComplexBottomUp extends RandomSimplicialComplex {

    BitSet signaturebitset[];
    RandomSimplicialComplexBottomUp(int N, float p) {
        super(N, p);
        signaturebitset = new BitSet[N+1];
        for(int i = 0; i<=N; i++)
            signaturebitset[i] = new BitSet(N);
    }

    @Override
    public void generate() {
//        System.out.println("Bottomup Gen");
        BitSet root = new BitSet(this.numberOfVertices);

        int rank = 1;
        while(rank<=this.numberOfVertices) {
            int taken = 0;
            int trials = 0;
            long numOfk_subsets = CombinatoricsUtils.binomialCoefficient(this.numberOfVertices, rank);
            boolean stop = false;

            root.set(rank-1,true);
            BitSet rootclone = (BitSet) root.clone();

            while (!stop){
                Random ran = new Random();
                float u = ran.nextFloat();
                if (u < this.prob) {
                    if(!this.containsSubsetof(rootclone)) {
                        addMaximalSimplex(rootclone);
                        taken++;
                        signaturebitset[rank].and(rootclone);
                    }
                }


                if (taken + 1 > numOfk_subsets) {
                    stop = true;
                }
                trials++;
                /*
                Gaspers Hack

                y = (~x+1) & x ;
                c = y + x;
                x_next = (((c^x)>>2)>>|y0|) | c;
                */
                if(trials<numOfk_subsets) {
                    BitSet x = (BitSet) rootclone.clone();
                    x.flip(0,this.numberOfVertices);
                    BitSet one = new BitSet(this.numberOfVertices);
                    one.flip(0);
//                    System.out.println(bitsettobitsequence(x)+ " "+bitsettobitsequence(one));
                    BitSet y = addbitset(x,one,this.numberOfVertices+1);
//                    System.out.println("y : "+ bitsettobitsequence(y)+ " rootclone "+bitsettobitsequence(rootclone));
                    y.and(rootclone); // y = (~x+1)&x
                    BitSet y_copy = (BitSet) y.clone();
//                    System.out.println(bitsettobitsequence(y_copy)+"  "+bitsettobitsequence(rootclone)+" "+bitsettobitsequence(y));
                    BitSet c = addbitset(y,rootclone,this.numberOfVertices); // c = y+x
//                    System.out.println(" C "+c);
                    BitSet c_copy = (BitSet)c.clone();
                    c.xor(rootclone);
//                    System.out.println(" C (after C^x) "+c);
                    BitSet x_next = c.get(2, Math.max(2, this.numberOfVertices));
//                    System.out.println("x_next after >>2: "+bitsettobitsequence(x_next));
                    int mod_y0 = y_copy.nextSetBit(0);
//                    System.out.println("|y0| = "+mod_y0);
                    x_next = x_next.get(mod_y0,Math.max(mod_y0, this.numberOfVertices));
//                    System.out.println("x_next after >>y0: "+bitsettobitsequence(x_next));
                    x_next.or(c_copy);
                    rootclone = (BitSet) x_next.clone();
                }
                else stop = true;
            }
            this.numOfkSimplices[rank] = taken;
            rank++;
        }
    }

    private boolean containsSubsetof(BitSet potentialsupset) {
        int rankofthisset = potentialsupset.cardinality();
        for(int i = 1; i<rankofthisset; i++)
            if(isSubset(signaturebitset[i],potentialsupset) && signaturebitset[i].cardinality()!=0)
                return true;
        for (BitSet potentialsubset:simplices ){
            if(isSubset(potentialsubset,potentialsupset))
                return true;
        }
        return false;
    }
}
