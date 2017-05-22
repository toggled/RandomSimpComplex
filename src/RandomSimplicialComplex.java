/**
 * Created by naheed on 5/17/17.
 */

import java.util.*;

import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.util.CombinatoricsUtils;

public class RandomSimplicialComplex extends SimplicialComplex implements RandomASCGenerator{
    float prob;
    RandomSimplicialComplex(int N, float p){
        super(N);
        this.prob = p;
    }

    @Override
    public void generate() {
        /**
         * Generate a random Simplicial Complex by traversing the Powerset lattice (under superset relation) top-down
         */
        long startTime = System.nanoTime();
        BitSet root = new BitSet(this.numberOfVertices);
        root.set(0,this.numberOfVertices);
        int rank = this.numberOfVertices;

        while(rank>=1) {
            int taken = 0;
            int trials = 0;
            long numOfk_subsets = CombinatoricsUtils.binomialCoefficient(this.numberOfVertices, rank);
            BitSet rootclone = (BitSet) root.clone();
//            System.out.println("Sk: " +numOfk_subsets);
            boolean stop = false;
            while (!stop){
//                System.out.println("consider : "+this.bitsettobitsequence(rootclone));
                Random ran = new Random();
                float u = ran.nextFloat();
                if (u < this.prob) {
                    if(!this.contains(rootclone)) {
                        addMaximalSimplex(rootclone);
                        taken++;
                    }
                }
                else{
//                    System.out.println("not taken");
                }


                if (taken + (this.numberOfVertices - rank) > numOfk_subsets) {
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
                    BitSet x = new BitSet(this.numberOfVertices);
                    x = (BitSet) rootclone.clone();
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
            rank--;
            root.flip(rank);
        }
        long stopTime = System.nanoTime();
        this.runtime = stopTime - startTime;
    }

    protected boolean isSubset(BitSet a, BitSet b){
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

    boolean contains(BitSet subset){
        /**
         * Returns true if the input simplex is contained in any of the maximal simplices
         */
        for (BitSet potentialsuperset:simplices ){
            if(isSubset(subset,potentialsuperset))
                return true;
        }
        return false;
    }

    BitSet addbitset(BitSet a,BitSet b,int len){
        boolean lastcarry = false;
        BitSet sum = new BitSet(len+1);
        int i;
        //println("len: ",len);
        for(i = 0; i<len; i++){
            sum.set(i, lastcarry ^ a.get(i) ^ b.get(i));
            lastcarry = (a.get(i) && b.get(i)) || (lastcarry && (a.get(i) ^ b.get(i))) ;
        }
        sum.set(i, lastcarry);
//        System.out.println(bitsettobitsequence(a)+" + "+bitsettobitsequence(b)+" = "+bitsettobitsequence(sum));
        return sum;
    }


}
