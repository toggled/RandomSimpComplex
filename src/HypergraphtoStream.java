/**
 * Created by naheed on 5/23/17.
 */

import edu.stanford.math.plex4.homology.chain_basis.Simplex;
import edu.stanford.math.plex4.streams.impl.ExplicitSimplexStream;

import java.io.*;
import java.util.*;


public class HypergraphtoStream {
    int numberOfVertices;
    public void initialize_streamfromhypergraph(ExplicitSimplexStream stream, SimplicialComplex sc) {
        /*
        Read all the cliques from a file line by line and add it to the simplical complex
        */

        numberOfVertices = sc.numberOfVertices;

        for(int i = 0; i<sc.numberOfVertices; i++)
            stream.addVertex(i,0);

        for(BitSet b:sc.simplices){
            String simplex_asstr = sc.bitsettoString(b);
            String [] sp = simplex_asstr.split(",");
            int elem[] = new int[sp.length];
            for (int i = 0; i < sp.length; i++) {
                elem[i] = Integer.valueOf(sp[i]);
            }

            if(elem.length>1) {
                int filtrationofb = sc.simplextotimemap.get(b);

                for(ArrayList<Integer> subst:subsets(elem)){
                    Integer [] subst_asarray = subst.toArray(new Integer[subst.size()]);
                    int[] intArray = Arrays.stream(subst_asarray).mapToInt(Integer::intValue).toArray();
                    if(subst.size() > 1 && !stream.containsElement(new Simplex(intArray))){

                        stream.addElement(intArray, filtrationofb);
                        System.out.println("Adding: "+subst);
                    }
                }
                //stream.addElement(elem, filtrationofb);
            }

//            for (int i = 0; i < elem.length; i++) {
//                System.out.print(elem[i]+" ");
//            }
//            System.out.println("");
            //System.out.println("hk: "+(kclosure-1));
        }

    }
    public ArrayList<ArrayList<Integer>> subsets(int[] S) {
        if (S == null)
            return null;

        Arrays.sort(S);

        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

        for (int i = 0; i < S.length; i++) {
            ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();

            //get sets that are already in result
            for (ArrayList<Integer> a : result) {
                temp.add(new ArrayList<Integer>(a));
            }

            //add S[i] to existing sets
            for (ArrayList<Integer> a : temp) {
                a.add(S[i]);
            }

            //add S[i] only as a set
            ArrayList<Integer> single = new ArrayList<Integer>();
            single.add(S[i]);
            temp.add(single);

            result.addAll(temp);
        }

        //add empty set
        result.add(new ArrayList<Integer>());

        return result;
    }

}
