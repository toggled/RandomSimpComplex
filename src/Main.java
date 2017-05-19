public class Main {

    public static void main(String[] args) {
	// write your code here
        int N = 30;
        RandomSimplicialComplex rsc = new RandomSimplicialComplex(N,(float)1/N);
        rsc.generate();
        System.out.println(rsc);
        String pathtowrite = "/Users/naheed/IdeaProjects/TD1/RandomSimpComplex/output";
        rsc.Write(pathtowrite);
    }
}
