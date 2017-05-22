import java.util.BitSet;
import java.util.Random;

/**
 * Created by naheed on 5/21/17.
 */

public class RandomHypergraph extends HyperGraph {
    float prob;
    RandomHypergraph(int N, float p){
        super(N);
        this.prob = p;
    }

    @Override
    public void generate() {
        // Create an empty hypergraph with this.numberOfVertices vertices
        //for(int i = 0; i<this.numberOfVertices; i++)
        //  this.addvertex(i);

        Random rand = new Random();
        BitSet Xi = new BitSet(this.numberOfVertices+1); // initially Xi is [0,n-1] all 0's
        BitSet one = new BitSet(this.numberOfVertices+1);
        one.set(0,this.numberOfVertices); // a bit set of n 1's i.e [0,n-1] all 1's
        BitSet Xnext = new BitSet(this.numberOfVertices+1);
        do{
            float u = rand.nextFloat();
            int s = (int) (1 + Math.ceil(Math.log(1-u)/Math.log(1-this.prob) - 1));

            BitSet s_inbinary = new BitSet(this.numberOfVertices+1);


            s_inbinary = BitSet.valueOf(new long[]{s});
            //println("geometric dist: ",s, " binray: ",BitSettoString(s_inbinary));
            Xnext = (BitSet)Xi.clone();
            Xnext = addbitset(Xnext,s_inbinary,this.numberOfVertices); // next hyperedge to add
            if(compare(Xnext,one)>=0){ // if Xnext<=one
                /*
                        Instead of storing hyperedges as set of labels,store them as bitset
                      */
                this.hyperedges.add(Xnext);
                this.numOfkhyperedges[Xnext.cardinality()]++;
            }
            Xi = (BitSet)Xnext.clone();
            //V.or(Xnext);
        }while(compare(Xnext,one)>0); // when Xnext<one

    }
    int compare(BitSet lhs, BitSet rhs) {
        // 1 if lhs<rhs, -1 if lhs>rhs, 0 if equal
        if (lhs.equals(rhs)) return 0;
        BitSet xor = (BitSet)lhs.clone();
        xor.xor(rhs);
        int firstDifferent = xor.length()-1;
        if(firstDifferent==-1)
            return 0;

        return rhs.get(firstDifferent) ? 1 : -1;
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
