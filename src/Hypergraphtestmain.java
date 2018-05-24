/**
 * Created by naheed on 5/21/17.
 */
public class Hypergraphtestmain {
//    public static void main(String[] args){
//        String pathtowrite = "/Users/naheed/IdeaProjects/TD1/RandomSimpComplex/output";
////        String pathtowrite = "C:\\RandomSimpComplex\\randsimpcomp\\RandomSimpComplex\\output";
//        int N = 20;
////        RandomHypergraph rsc = new RandomHypergraph(N,(float)0.5);
////        rsc.generate();
////        rsc.Write(pathtowrite);
//
////        String[] type = new String[]{"baseh","hg"};
////        for(String typ:type) {
////            Analytics acs = new Analytics(N, (float) 0.5, 100);
////            acs.setSimplextype(typ);
////            acs.runuptoTtimes();
////            acs.WriteuptoTtimes(pathtowrite);
////        }
//
////        probvaryAnalytics pac = new probvaryAnalytics(N,new double[]{0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1});
////        pac.setSimplextype("hg");
////        pac.runforp(5);
////        pac.Writeprobvaryanalytics(pathtowrite);
//
////        SpernerFamilyAnalytics sfa = new SpernerFamilyAnalytics(N,(float)0.5);
////        sfa.setSimplextype("hg");
////        sfa.runTtimes(100);
////        sfa.WritevarypSpernSize(pathtowrite);
//
//        /**
//         * k-subset test , Varying probability analytics
//         */
//        SpernerFamilyAnalytics sfa_varyp = new SpernerFamilyAnalytics(N);
//        sfa_varyp.setSimplextype("hg");
//        sfa_varyp.runforp(10000,new double[]{0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1});
////        sfa_varyp.runforp(100,new double[]{1,0.9,0.8,0.7,0.6,0.5,0.4,0.3,0.2,0.1,0});
//        sfa_varyp.WritevarypSpernSize(pathtowrite);
//
//        /**
//         * Scalability test for baseline hypergraph and Z-algorithm
//         */
////        String[] type = new String[]{"baseh","hg"};
////        for(String typ:type) {
////            int[] vertices_array = new int[]{5, 10, 15, 20, 25};
////            Scalability sb = new Scalability(vertices_array, new float[]{0.1f});
////            sb.setSimplextype(typ);
////            sb.runforNrunforp(100);
////            sb.WriteforNforp(pathtowrite);
////        }
////
////        for(String typ:type) {
////            int[] vertices_array = new int[]{10};
////            Scalability sb = new Scalability(vertices_array, new float[]{0,0.1f,0.2f,0.3f,0.4f,0.5f,0.6f,0.7f,0.8f,0.9f,1});
////            sb.setSimplextype(typ);
////            sb.runforNrunforp(100);
////            sb.WriteforNforp(pathtowrite);
////        }
//
////        N = 20;
////        probvaryAnalytics pac = new probvaryAnalytics(N,new double[]{0,0.1f,0.2f,0.3f,0.4f,0.5f,0.6f,0.7f,0.8f,0.9f,1});
////        pac.setSimplextype("hg");
////        pac.runforp(100);
////        pac.Writeprobvaryanalytics(pathtowrite);
//    }
//

    public static void main(String[] args){
        testSperner();
    }

    static void testSperner(){
//        SpernerFamily sf = new SpernerFamily(10);
//        // testing generate() and addition of sets.
//        sf.generate();
//
//        // testing hexrepr of the sf
//        System.out.println("The Hex repr is : \n"+ sf.toCryptoString());
//
//        // testing removal of a maximal set
//        try {
//            if(sf.removeMaximalSet(sf.maximalsets.last()))
//                System.out.println("After removing last element : \n"+ sf.toCryptoString());
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        try {
//            sf.removeMaximalSet(sf.maximalsets.first());
//            System.out.println("After removing first element: \n" + sf.toCryptoString());
//        }catch (Exception e){
//        e.printStackTrace();
//        }

        // test MCMC
        RandomSpernerFamilyMCMC randsf = new RandomSpernerFamilyMCMC(20);
        System.out.println(randsf.sf.toString());
        randsf.getEpsilonUnformSample(0.001);
        System.out.println(randsf.sf.toString());
        randsf.writetoCSV("result.csv",",");
    }
}
