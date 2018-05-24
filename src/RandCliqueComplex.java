import org.apache.commons.math3.util.CombinatoricsUtils;

import java.util.*;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.graph.*;
import org.jgrapht.*;
import org.jgrapht.alg.BronKerboschCliqueFinder;

/**
 * Created by naheed on 6/23/17.
 */

public class RandCliqueComplex extends RandomSimplicialComplex{
    SimplicialComplex container;
    Graph<Integer, DefaultEdge> graph;
    RandCliqueComplex(int N, float p) {
        super(N, p);
        container = new RandomSimplicialComplex(N,p);
        graph = new SimpleGraph<Integer, DefaultEdge>(DefaultEdge.class);
    }
    @Override
    public void generate() {
        genErdosRenyiGraph(this.numberOfVertices,this.prob);
        BronKerboschCliqueFinder cliqueFinder = new BronKerboschCliqueFinder(graph);
        Collection<HashSet<Integer>> ll = cliqueFinder.getAllMaximalCliques();
        for(HashSet<Integer> clique:ll){
            BitSet simplex = new BitSet(this.numberOfVertices);
            for(Integer v:clique){
                simplex.set(v,true);
            }
//            System.out.println(simplex.toString());
            this.addMaximalSimplex(simplex);
            this.numOfkSimplices[simplex.cardinality()]++;
        }
    }

    void genErdosRenyiGraph(int N,double p){
        BitSet root = new BitSet(this.numberOfVertices);

        // singletons / vertices are included by definition
        BitSet singletons = new BitSet(this.numberOfVertices);
        for (int i = 0; i < this.numberOfVertices; i++) {
            graph.addVertex(i);
        }

        // choose from edges
        int rank = 2;
        while(rank<=2) {
            int taken = 0;
            int trials = 0;
            long numOfk_subsets = CombinatoricsUtils.binomialCoefficient(this.numberOfVertices, rank);
            boolean stop = false;

            root.set(rank-2,true);
            root.set(rank-1,true);
            BitSet rootclone = (BitSet) root.clone();

            while (!stop){
                Random ran = new Random();
                float u = ran.nextFloat();
                if (u < this.prob) {

//                        System.out.println(rootclone.toString());
                    int i = rootclone.nextSetBit(0);
                    int j = rootclone.nextSetBit(i+1);
//                    System.out.println(i+" "+j);
                    graph.addEdge(i,j);

                        taken++;

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

            rank++;
        }

    }
}
