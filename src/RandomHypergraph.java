import java.math.BigInteger;
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
        long startTime = System.nanoTime();
        Random rand = new Random();
        BitSet Xi = new BitSet(this.numberOfVertices+1); // initially Xi is [0,n-1] all 0's
        BitSet one = new BitSet(this.numberOfVertices+1);
        one.set(0,this.numberOfVertices); // a bit set of n 1's i.e [0,n-1] all 1's
        BitSet Xnext = new BitSet(this.numberOfVertices+1);
        do{
            float u = rand.nextFloat();
            int s;
            if(Math.abs(this.prob) < 0.0000000001)
                s = Integer.MAX_VALUE;
            else if(Math.abs(this.prob - 1.0) <0.000000001)
                s = 1;
            else
                s = (int) (1 + Math.ceil(Math.log(1-u)/Math.log(1-this.prob) - 1));

            BitSet s_inbinary = new BitSet(this.numberOfVertices+1);


            s_inbinary = BitSet.valueOf(new long[]{s});
//            System.out.println("geometric dist: "+s);
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
        long stopTime = System.nanoTime();
        this.runtime = stopTime - startTime;
    }


//    @Override
//    public void generate() {
//        // Create an empty hypergraph with this.numberOfVertices vertices
//        //for(int i = 0; i<this.numberOfVertices; i++)
//        //  this.addvertex(i);
//        long startTime = System.nanoTime();
//        Random rand = new Random();
//        BitSet Xi = new BitSet(this.numberOfVertices+1); // initially Xi is [0,n-1] all 0's
//        BitSet one = new BitSet(this.numberOfVertices+1);
//        one.set(0,this.numberOfVertices); // a bit set of n 1's i.e [0,n-1] all 1's
//        BitSet Xnext = new BitSet(this.numberOfVertices+1);
//        BigInteger Max = BigInteger.valueOf((long)Math.pow(2,this.numberOfVertices) - 1);
//
//        for(BigInteger counter = BigInteger.ZERO; counter.compareTo(Max)<0; counter.add(BigInteger.ONE)){
//
//            float u = rand.nextFloat();
//            int s;
//            if(Math.abs(this.prob) < 0.0000000001)
//                s = Integer.MAX_VALUE;
//            else if(Math.abs(this.prob - 1.0) <0.000000001)
//                s = 1;
//            else
//                s = (int) (1 + Math.ceil(Math.log(1-u)/Math.log(1-this.prob) - 1));
//
//            BitSet s_inbinary = new BitSet(this.numberOfVertices+1);
//
//            s_inbinary = BitSet.valueOf(new long[]{s});
//            BigInteger dif = new BigInteger(s_inbinary.toByteArray());
//            counter = counter.add(dif);
////            System.out.println("geometric dist: "+s);
//            Xnext = (BitSet)Xi.clone();
//            Xnext = addbitset(Xnext,s_inbinary,this.numberOfVertices); // next hyperedge to add
//            if(counter.compareTo(Max)<=0){ // if Xnext<=one
//                /*
//                        Instead of storing hyperedges as set of labels,store them as bitset
//                      */
//                this.hyperedges.add(Xnext);
//                this.numOfkhyperedges[Xnext.cardinality()]++;
//            }
//            Xi = (BitSet)Xnext.clone();
//            //V.or(Xnext);
//        }
//        long stopTime = System.nanoTime();
//        this.runtime = stopTime - startTime;
//    }

    /* Some stuff*/
//    @Override
//    public void generate() {
//        // Create an empty hypergraph with this.numberOfVertices vertices
//        //for(int i = 0; i<this.numberOfVertices; i++)
//        //  this.addvertex(i);
//        long startTime = System.nanoTime();
//        Random rand = new Random();
//        BitSet Xi = new BitSet(this.numberOfVertices+1); // initially Xi is [0,n-1] all 0's
//        BitSet one = new BitSet(this.numberOfVertices+1);
//        one.set(0,this.numberOfVertices); // a bit set of n 1's i.e [0,n-1] all 1's
//        BitSet Xnext = new BitSet(this.numberOfVertices+1);
//        do{
//            float u = rand.nextFloat();
//            long s;
//            if(Math.abs(this.prob) < 0.0000000001)
//                s = Integer.MAX_VALUE;
//            else if(Math.abs(this.prob - 1.0) <0.000000001)
//                s = 1;
//            else
//                s = (int) (1 + Math.ceil(Math.log(1-u)/Math.log(1-this.prob) - 1));
//
//            boolean stop = false;
//            long count = 0;
//            while(!stop) {
//                BitSet s_inbinary = new BitSet(this.numberOfVertices + 1);
//
//
//                s_inbinary = BitSet.valueOf(new long[]{s});
//                //            System.out.println("geometric dist: "+s);
//                Xnext = (BitSet) Xi.clone();
////                Xnext = addbitset(Xnext, s_inbinary, this.numberOfVertices); // next hyperedge to add
//                Xnext = nextBitSet(Xnext);
////                if (compare(Xnext, one) >= 0) { // if Xnext<=one
////                    /*
////                            Instead of storing hyperedges as set of labels,store them as bitset
////                          */
////                    this.hyperedges.add(Xnext);
////                    this.numOfkhyperedges[Xnext.cardinality()]++;
////                }
//                Xi = (BitSet) Xnext.clone();
//                count++;
//            }
//            //V.or(Xnext);
//        }while(compare(Xnext,one)>0); // when Xnext<one
//        long stopTime = System.nanoTime();
//        this.runtime = stopTime - startTime;
//    }

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
    public BitSet nextBitSet(BitSet rootclone){
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
            return x_next;
    }
}
