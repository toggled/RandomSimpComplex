/**
 * Created by naheed on 5/21/17.
 */
public class Hypergraphtestmain {
    public static void main(String[] args){
        String pathtowrite = "/Users/naheed/IdeaProjects/TD1/RandomSimpComplex/output";
        int N = 4;
//        RandomHypergraph rsc = new RandomHypergraph(N,(float)1/(N*N));
//        rsc.generate();
//        rsc.Write(pathtowrite);

//        Analytics acs = new Analytics(N,(float)1/N,100);
//        acs.setSimplextype("hg");
//        acs.runuptoTtimes();
//        acs.WriteuptoTtimes(pathtowrite);

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
        SpernerFamilyAnalytics sfa_varyp = new SpernerFamilyAnalytics(N);
        sfa_varyp.setSimplextype("hg");
        sfa_varyp.runforp(1,new double[]{0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1});
//        sfa_varyp.runforp(100,new double[]{1,0.9,0.8,0.7,0.6,0.5,0.4,0.3,0.2,0.1,0});
        sfa_varyp.WritevarypSpernSize(pathtowrite);



    }
}
