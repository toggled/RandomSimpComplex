public class Main {

    public static void main(String[] args) {
	// write your code here
                  String pathtowrite = "/Users/naheed/IdeaProjects/TD1/RandomSimpComplex/output";
//        String pathtowrite = "C:\\RandomSimpComplex\\randsimpcomp\\RandomSimpComplex\\output";
        int N = 20;
//        RandomSimplicialComplex rsc = new RandomSimplicialComplex(N,(float)1/N);
//        rsc.generate();
//      AssociatedRandSimplicialComplex arsc = new AssociatedRandSimplicialComplex(N,(float)1/N) ;
//      arsc.generate();
//      arsc.Write(pathtowrite);
//        System.out.println(rsc);

//        rsc.Write(pathtowrite);
//          int N = 5;

//        Analytics acs = new Analytics(N,(float)1/N,100);
//        acs.runuptoTtimes();
//        acs.WriteuptoTtimes(pathtowrite);
//
//        /**
//         * Varying probability analytics
//         */
//        probvaryAnalytics pac = new probvaryAnalytics(N,new double[]{0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1});
//        pac.runforp(100);
//        pac.Writeprobvaryanalytics(pathtowrite);

        /*
        Sperner family size analytics
         */
//        SpernerFamilyAnalytics sfa = new SpernerFamilyAnalytics(N,(float)0.5);
//        sfa.runTtimes(100);
//        System.out.println(sfa.maptoString());

//        SpernerFamilyAnalytics sfa = new SpernerFamilyAnalytics(N,(float)0.0);
//        sfa.runTtimes(100);
//        System.out.println(sfa.maptoString());
        /**
         * Sperner family size , Varying probability analytics
         */
//        SpernerFamilyAnalytics sfa_varyp = new SpernerFamilyAnalytics(N);
//        sfa_varyp.runforp(100,new double[]{0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1});
////        sfa_varyp.runforp(100,new double[]{1,0.9,0.8,0.7,0.6,0.5,0.4,0.3,0.2,0.1,0});
//        sfa_varyp.WritevarypSpernSize(pathtowrite);


        /**
         * Bottom up test
         */
//        RandomSimplicialComplexBottomUp rscbotup = new RandomSimplicialComplexBottomUp(N,(float)0.1);
//        rscbotup.generate();
//        System.out.println(rscbotup);
//        Analytics acs = new Analytics(N,(float)1/N,100);
//        acs.setSimplextype("bottomup");
//        acs.runuptoTtimes();
//        acs.WriteuptoTtimes(pathtowrite);

//        probvaryAnalytics pac = new probvaryAnalytics(N,new double[]{0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1});
//        pac.setSimplextype("bottomup");
//        pac.runforp(100);
//        pac.Writeprobvaryanalytics(pathtowrite);

//        SpernerFamilyAnalytics sfa = new SpernerFamilyAnalytics(N,(float)0.5);
//        sfa.setSimplextype("bottomup");
//        sfa.runTtimes(100);
//        System.out.println(sfa.maptoString());


        /**
         * Sperner family size , Varying probability analytics
         */
//        SpernerFamilyAnalytics sfa_varyp = new SpernerFamilyAnalytics(N);
//        sfa_varyp.setSimplextype("arsc");
//        sfa_varyp.runforp(100,new double[]{0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1});
//        sfa_varyp.WritevarypSpernSize(pathtowrite);

        /**
         * Scalability test for Simplicial complex algorithms
         */

//        String[] type = new String[]{"arsc","bottomup","topdown"};
//        for(String typ:type) {
//            int[] vertices_array = new int[]{5, 10, 15, 20};
//            Scalability sb = new Scalability(vertices_array, new float[]{0.1f,0.01f,0.001f,0.0001f});
//            sb.setSimplextype(typ);
//            sb.runforNrunforp(100);
//            sb.WriteforNforp(pathtowrite);
//        }

/**
 * Sperner family size , Varying probability analytics for all algo
 */
//        String[] type = new String[]{"arsc"};
//        for(String typ:type) {
//            SpernerFamilyAnalytics sfa_varyp = new SpernerFamilyAnalytics(10);
//            sfa_varyp.setSimplextype(typ);
//            sfa_varyp.runforp(10000, new double[]{0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1});
//            //        sfa_varyp.runforp(100,new double[]{1,0.9,0.8,0.7,0.6,0.5,0.4,0.3,0.2,0.1,0});
//            sfa_varyp.WritevarypSpernSize(pathtowrite);
//        }

//                probvaryAnalytics pac = new probvaryAnalytics(N,new double[]{0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1});
//        pac.setSimplextype("arsc");
//        pac.runforp(100);
//        pac.Writeprobvaryanalytics(pathtowrite);


        // Dynamic prob expts

        //Check an instance of the algorithm

//        DynamicprobRandSimpComplex dynarsc = new DynamicprobRandSimpComplex(N,(float)0.1) ;
//        dynarsc.generate();
//        dynarsc.Write(pathtowrite);
//        System.out.println(dynarsc);

        String[] type = new String[]{"arsc","dynasc"};
        for(String typ:type) {
            // length of sperner family vs Number of sperner family
            probvaryAnalytics pac = new probvaryAnalytics(N, new double[]{0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1});
            pac.setSimplextype(typ);
            pac.runforp(100);
            pac.Writeprobvaryanalytics(pathtowrite);


            // number of simplicial complex vs size of the simplex
                SpernerFamilyAnalytics sfa_varyp = new SpernerFamilyAnalytics(N);
                sfa_varyp.setSimplextype(typ);
                //            sfa_varyp.runforp(10000, new double[]{0,0.5,1});
                sfa_varyp.runforp(6000, new double[]{0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1});
                sfa_varyp.WritevarypSpernSize(pathtowrite);


            // Number of simplicial complex vs size of the largest maximal face
            pac = new probvaryAnalytics(N, new double[]{0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1});
            pac.setSimplextype(typ);
            pac.maxKrunforp(1000);
            pac.Writeprobvaryanalytics(pathtowrite);
        }
    }
}
