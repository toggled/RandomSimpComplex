/**
 * Created by naheed on 5/21/17.
 */
public class Hypergraphtestmain {
    public static void main(String[] args){
//        String pathtowrite = "/Users/naheed/IdeaProjects/TD1/RandomSimpComplex/output";
        String pathtowrite = "C:\\RandomSimpComplex\\randsimpcomp\\RandomSimpComplex\\output";
        int N = 5;
//        RandomHypergraph rsc = new RandomHypergraph(N,(float)0.5);
//        rsc.generate();
//        rsc.Write(pathtowrite);

        Analytics acs = new Analytics(N,(float)1/N,100);
        acs.setSimplextype("hg");
        acs.runuptoTtimes();
        acs.WriteuptoTtimes(pathtowrite);

//        probvaryAnalytics pac = new probvaryAnalytics(N,new double[]{0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1});
//        pac.setSimplextype("hg");
//        pac.runforp(5);
//        pac.Writeprobvaryanalytics(pathtowrite);

//        SpernerFamilyAnalytics sfa = new SpernerFamilyAnalytics(N,(float)0.5);
//        sfa.setSimplextype("hg");
//        sfa.runTtimes(100);
//        System.out.println(sfa.maptoString());

        /**
         * k-subset test , Varying probability analytics
         */
//        SpernerFamilyAnalytics sfa_varyp = new SpernerFamilyAnalytics(N);
//        sfa_varyp.setSimplextype("hg");
//        sfa_varyp.runforp(100,new double[]{0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1});
////        sfa_varyp.runforp(100,new double[]{1,0.9,0.8,0.7,0.6,0.5,0.4,0.3,0.2,0.1,0});
//        sfa_varyp.WritevarypSpernSize(pathtowrite);

        String[] type = new String[]{"baseh","hg"};
        for(String typ:type) {
            int[] vertices_array = new int[]{5, 10, 15, 20, 25};
            Scalability sb = new Scalability(vertices_array, new float[]{0.1f,0.01f,0.001f,0.0001f});
            sb.setSimplextype(typ);
            sb.runforNrunforp(100);
            sb.WriteforNforp(pathtowrite);
        }

        // Baseline hg
//
//        Scalability sb = new Scalability(vertices_array,0.01,10);
//        sb.setSimplextype("basehg");
//        sb.runforN();
//        sb.WriteRuntimes(pathtowrite);
    }
}