public class Main {

    public static void main(String[] args) {
	// write your code here
//        int N = 30;
//        RandomSimplicialComplex rsc = new RandomSimplicialComplex(N,(float)1/N);
//        rsc.generate();
//        System.out.println(rsc);
//          String pathtowrite = "/Users/naheed/IdeaProjects/TD1/RandomSimpComplex/output";
        String pathtowrite = "C:\\RandomSimpComplex\\randsimpcomp\\RandomSimpComplex\\output";
//        rsc.Write(pathtowrite);
          int N = 5;

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
//        sfa_varyp.runforp(100,new double[]{0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1});
////        sfa_varyp.runforp(100,new double[]{1,0.9,0.8,0.7,0.6,0.5,0.4,0.3,0.2,0.1,0});
//        sfa_varyp.WritevarypSpernSize(pathtowrite);

        String[] type = new String[]{"bottomup","topdown"};
        for(String typ:type) {
            int[] vertices_array = new int[]{5, 10, 15, 20, 25, 30};
            Scalability sb = new Scalability(vertices_array, 0.0001, 1);
            sb.setSimplextype(typ);
            sb.runforN();
            sb.WriteRuntimes(pathtowrite);
        }
    }
}
