import edu.stanford.math.plex4.api.Plex4;
import edu.stanford.math.plex4.homology.barcodes.AnnotatedBarcodeCollection;
import edu.stanford.math.plex4.homology.barcodes.BarcodeCollection;
import edu.stanford.math.plex4.homology.barcodes.Interval;
import edu.stanford.math.plex4.homology.chain_basis.Simplex;
import edu.stanford.math.plex4.homology.interfaces.AbstractPersistenceAlgorithm;
import edu.stanford.math.plex4.homology.interfaces.AbstractPersistenceBasisAlgorithm;
import edu.stanford.math.plex4.streams.impl.ExplicitSimplexStream;
import edu.stanford.math.plex4.visualization.BarcodeVisualizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by naheed on 5/23/17.
 */
public class BarcodeComputer {
    HypergraphtoStream hs;
    SimplicialComplex sc;
    ExplicitSimplexStream stream;
    int maxdimension;
//    BarcodeComputer(HyperGraph h,int maxdim){
//        hs  = new HypergraphtoStream();
//        this.hg = h;
//        stream = new ExplicitSimplexStream();
//        maxdimension = maxdim;
//    }
    BarcodeComputer(SimplicialComplex sc,int maxdim){
        hs  = new HypergraphtoStream();
        this.sc = sc;
        stream = new ExplicitSimplexStream();
        maxdimension = maxdim;
    }

    void compute(){
        hs.initialize_streamfromhypergraph(this.stream,this.sc);
        stream.finalizeStream();
        System.out.println("Size: "+stream.getSize());
        AbstractPersistenceAlgorithm<Simplex> persistence = Plex4.getModularSimplicialAlgorithm(maxdimension, 2);

        BarcodeCollection<Double> circle_intervals
                = persistence.computeIntervals(this.stream); // computing betti intervals

        System.out.println(circle_intervals); // printing betti intervals
        System.out.println("Betti numbers: "+circle_intervals.getBettiNumbers());
        generate_barcode_image(circle_intervals,maxdimension);
//        generate_representative_cycle(this.stream,persistence,circle_intervals);
    }

    private void generate_barcode_image(BarcodeCollection<Double> circle_intervals,int maxdim) {
        String outputdir_path = "/Users/naheed/IdeaProjects/TD1/RandomSimpComplex/output/";
        System.out.println("maxdim: "+maxdim);
        List<Interval<Double>> interv;
        Interval in;
        BufferedImage im;
        for (int i = 0; i < maxdim; i++) {
            interv = circle_intervals.getIntervalsAtDimension(i);
//            System.out.println("printing: ");
//            for(Interval <Double> intr:interv){
//                System.out.println(intr.toString());
//            }
            try {
                im = BarcodeVisualizer.drawBarcode(interv, "dimension: " + i,maxdim); // last argument maximum limit of bar interval
                File outputfile = new File(outputdir_path + i + "_barcode.png");
                ImageIO.write(im, "png", outputfile);
            } catch (IOException ex) {
               ex.printStackTrace();
            }
        }
    }

}
