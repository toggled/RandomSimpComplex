import edu.stanford.math.plex4.homology.barcodes.BarcodeCollection;
import java.io.File;

/**
 * Created by naheed on 5/23/17.
 */
public class Barcodetest {
    public static void main(String args[]){
        int N = 20;
        String rootpath = "/Users/naheed/IdeaProjects/TD1/RandomSimpComplex/newout/barcodes/";


//        Assocaited Simplicial Complex
//        String subdir = "arsc"+String.valueOf(N)+"/";
//        for(int i=1; i<=30; i++){
//            AssociatedRandSimplicialComplex arsc = new AssociatedRandSimplicialComplex(N,(float)0.001) ;
//            arsc.generate();
//            BarcodeComputer bc = new BarcodeComputer(arsc,arsc.maxcardinality-1);
//            BarcodeCollection<Double> intervs = bc.getIntervals();
//            File directory = new File(rootpath+subdir+String.valueOf(i)+"/");
//            if (! directory.exists()){
//                directory.mkdirs();
//            }
//
//            bc.WritebarcodesinDir(intervs,bc.maxdimension,rootpath+subdir+String.valueOf(i)+"/");
//        }


        // Clique Complex
//        for(float prob=0.2f; prob<1.0f; prob+=0.1f){
//            String subdir = "clique" + String.valueOf(N) + "/" + String.valueOf(prob) + "/";
//            for (int i = 1; i <= 30; i++) {
//                RandCliqueComplex cq = new RandCliqueComplex(N, prob);
//                cq.generate();
//                BarcodeComputer bc = new BarcodeComputer(cq, cq.maxcardinality - 1);
//                BarcodeCollection<Double> intervs = bc.getIntervals();
//                File directory = new File(rootpath + subdir + String.valueOf(i) + "/");
//                if (!directory.exists()) {
//                    directory.mkdirs();
//                }
//
//                bc.WritebarcodesinDir(intervs, bc.maxdimension, rootpath + subdir + String.valueOf(i) + "/");
//            }
//        }

        // Neighborhood complex

        for(float prob=0.1f; prob<=1.0f; prob+=0.1f){
            String subdir = "neigh" + String.valueOf(N) + "/" + String.valueOf(prob) + "/";
            for (int i = 1; i <= 30; i++) {
                RandNeighComplex nq = new RandNeighComplex(N, prob);
                nq.generate();
                BarcodeComputer bc = new BarcodeComputer(nq, nq.maxcardinality - 1);
                BarcodeCollection<Double> intervs = bc.getIntervals();
                File directory = new File(rootpath + subdir + String.valueOf(i) + "/");
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                bc.WritebarcodesinDir(intervs, bc.maxdimension, rootpath + subdir + String.valueOf(i) + "/");
            }
        }
    }
}
