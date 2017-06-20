import java.math.BigInteger;
import java.util.BitSet;
import java.util.Random;

/**
 * Created by workshop on 5/22/2017.
 */

public class BaselineRandomHypergraph  extends HyperGraph {
    float prob;
    BaselineRandomHypergraph(int N){
        super(N);
    }
    BaselineRandomHypergraph(int N, float p){
        super(N);
        this.prob = p;
    }

    @Override
    void generate() {
//        System.out.println("basehg");
        long startTime = System.nanoTime();
        Random rand = new Random();
        BitSet Xi = new BitSet(this.numberOfVertices+1); // initially Xi is [0,n-1] all 0's
        Xi.set(0);
        BitSet integerone = new BitSet(this.numberOfVertices+1);
        integerone.set(0); // integerone = 1
        BigInteger Max = BigInteger.valueOf((long)Math.pow(2,this.numberOfVertices) - 1);

        for(BigInteger counter = BigInteger.ZERO; counter.compareTo(Max)<0;counter = counter.add(BigInteger.ONE)){
            float u = rand.nextFloat();
            if(u<this.prob){
                this.addEdge(Xi);
                this.numOfkhyperedges[Xi.cardinality()]++;
            }
            Xi = addbitset(Xi,integerone,this.numberOfVertices); // next hyperedge to add
//            System.out.println(counter);
        }
        long stopTime = System.nanoTime();
        this.runtime = stopTime - startTime;
    }

    public BitSet addbitset(BitSet a,BitSet b,int len){
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
