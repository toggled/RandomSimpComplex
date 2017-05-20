public class Main {

    public static void main(String[] args) {
	// write your code here
//        int N = 30;
//        RandomSimplicialComplex rsc = new RandomSimplicialComplex(N,(float)1/N);
//        rsc.generate();
//        System.out.println(rsc);
//        String pathtowrite = "/Users/naheed/IdeaProjects/TD1/RandomSimpComplex/output";
        String pathtowrite = "C:\\RandomSimpComplex\\randsimpcomp\\RandomSimpComplex\\output";
//        rsc.Write(pathtowrite);
        int N = 10;

        //        Analytics acs = new Analytics(N,(float)1/N,100);
//        acs.runuptoTtimes();
//        acs.WriteuptoTtimes(pathtowrite);

        /**
         * Varying probability analytics
         */
        probvaryAnalytics pac = new probvaryAnalytics(N,new double[]{0.1,0.2,0.3,0.4,0.5});
        pac.runforp(100);
        pac.Writeprobvaryanalytics(pathtowrite);
    }
}
