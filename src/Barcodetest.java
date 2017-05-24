/**
 * Created by naheed on 5/23/17.
 */
public class Barcodetest {
    public static void main(String args[]){
        int N = 15;
        AssociatedRandSimplicialComplex arsc = new AssociatedRandSimplicialComplex(N,(float)0.00001) ;
        arsc.generate();
        BarcodeComputer bc = new BarcodeComputer(arsc,arsc.maxcardinality-1);
        bc.compute();
    }
}
