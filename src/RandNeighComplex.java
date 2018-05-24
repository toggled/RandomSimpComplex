import org.apache.commons.math3.util.CombinatoricsUtils;
import org.jgraph.graph.DefaultEdge;
import org.jgrapht.Graph;
import org.jgrapht.alg.BronKerboschCliqueFinder;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.graph.SimpleGraph;

import java.util.*;

/**
 * Created by naheed on 7/20/17.
 */
public class RandNeighComplex extends RandomSimplicialComplex{
    BaselineRandomHypergraph container;
    Graph<Integer, DefaultEdge> graph;
    RandNeighComplex(int N, float p) {
        super(N, p);
        container = new BaselineRandomHypergraph(N,p);
        graph = new SimpleGraph<Integer, DefaultEdge>(DefaultEdge.class);
    }
    @Override
    public void generate() {
        genErdosRenyiGraph(this.numberOfVertices,this.prob);
        NeighborIndex nI = new NeighborIndex(this.graph);

        for(Integer i:this.graph.vertexSet()) {

                BitSet simplex = new BitSet(this.numberOfVertices);
                for (Object v : nI.neighborListOf(i))
                    simplex.set((int)v, true);

                System.out.println(simplex.toString());

                container.addEdge(simplex);
                //            this.numOfkSimplices[simplex.cardinality()]++;
        }

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
