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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by naheed on 5/23/17.
 */
public class BarcodeComputer {
    HypergraphtoStream hs;
    SimplicialComplex sc;
    ExplicitSimplexStream stream;
    int maxdimension;
    int maxfiltration;
    int INF;
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
        maxfiltration = sc.filtration-1;
        INF = maxfiltration+1;
    }

    void compute(){
        hs.initialize_streamfromhypergraph(this.stream,this.sc);
        stream.finalizeStream();

//        System.out.println("The simplices:-");
//        for(Simplex s:this.stream){
//            System.out.println(s.toString());
//        }
//        System.out.println("----------------");
        System.out.println("Size: "+stream.getSize());
        AbstractPersistenceAlgorithm<Simplex> persistence = Plex4.getModularSimplicialAlgorithm(maxdimension, 2);

        BarcodeCollection<Double> circle_intervals
                = persistence.computeIntervals(this.stream); // computing betti intervals

//        System.out.println(circle_intervals); // printing betti intervals
//        System.out.println("Betti numbers: "+circle_intervals.getBettiNumbers());
        generate_barcode_image(circle_intervals,maxdimension);
        Writebarcodes(circle_intervals,maxdimension);
//        generate_representative_cycle(this.stream,persistence,circle_intervals);
    }

    BarcodeCollection<Double> getIntervals(){
        hs.initialize_streamfromhypergraph(this.stream,this.sc);
        stream.finalizeStream();
        System.out.println("Size: "+stream.getSize());
        AbstractPersistenceAlgorithm<Simplex> persistence = Plex4.getModularSimplicialAlgorithm(maxdimension, 2);

        BarcodeCollection<Double> circle_intervals
                = persistence.computeIntervals(this.stream); // computing betti intervals
        return circle_intervals;
    }
    public  void Writebarcodes(BarcodeCollection<Double>circle_int, int maxD){


        List<Interval<Double>> interv;

        String outputdir_path = "/Users/naheed/IdeaProjects/TD1/RandomSimpComplex/output/barcodes/";
        BarcodeWriter writertowritelist = new BarcodeWriter(outputdir_path+"generatedbars");
        List <String> pathtobarfiles = new ArrayList<>();

        for (int i = 0; i < maxD; i++) {

            String barfilename = outputdir_path+String.valueOf(i)+".bar";
            BarcodeWriter bw = new BarcodeWriter(barfilename);


            List <String> Listofbarcodes = new ArrayList<>();


            interv = circle_int.getIntervalsAtDimension(i);
            if(!interv.isEmpty()) {
                pathtobarfiles.add(barfilename);
                for (Interval<Double> intr : interv) {
                        double start = intr.getStart();
                        double end;
                        if(!intr.isRightInfinite())
                            end = intr.getEnd();
                        else
                            end = INF;

                        Listofbarcodes.add(Double.toString(start) + " " + Double.toString(end));
                }
                bw.Write(Listofbarcodes);
            }
        }
        writertowritelist.Write(pathtobarfiles);

        // Write configure file
        BarcodeWriter configwriter = new BarcodeWriter("/Users/naheed/IdeaProjects/TD1/RandomSimpComplex/src/configure");
        boolean areThereInfiniteIntervals = false;
        double infty = INF;
        boolean shallInfiniteBarcodesBeIgnored = false;
        double valueOfInfinity = INF;
        boolean useGridInComputations = false;
        double gridDiameter = 0.01;
        double epsi = 0.000005;
        List <String> conf = new ArrayList<>();
        conf.add(areThereInfiniteIntervals?"1":"0");
        conf.add(String.valueOf(infty));
        conf.add(shallInfiniteBarcodesBeIgnored?"1":"0");
        conf.add(String.valueOf(valueOfInfinity));
        conf.add(useGridInComputations?"1":"0");
        conf.add(String.valueOf(gridDiameter));
        conf.add(String.valueOf(epsi));
        configwriter.Write(conf);
    }


    public  void WritebarcodesinDir(BarcodeCollection<Double>circle_int, int maxD,String outputdir_path){
/***
 * Computes the intervals and store in the folder outputdir_path.
 */

        List<Interval<Double>> interv;
        BarcodeWriter writertowritelist = new BarcodeWriter(outputdir_path+"generatedbars");
        List <String> pathtobarfiles = new ArrayList<>();

        for (int i = 0; i < maxD; i++) {

            String barfilename = outputdir_path+String.valueOf(i)+".bar";
            BarcodeWriter bw = new BarcodeWriter(barfilename);


            List <String> Listofbarcodes = new ArrayList<>();


            interv = circle_int.getIntervalsAtDimension(i);
            if(!interv.isEmpty()) {
                pathtobarfiles.add(barfilename);
                for (Interval<Double> intr : interv) {
                    double start = intr.getStart();
                    double end;
                    if(!intr.isRightInfinite())
                        end = intr.getEnd();
                    else
                        end = INF;

                    Listofbarcodes.add(Double.toString(start) + " " + Double.toString(end));
                }
                bw.Write(Listofbarcodes);
            }
        }
        writertowritelist.Write(pathtobarfiles);

//        // Omit the configuration file for now
//        BarcodeWriter configwriter = new BarcodeWriter("/Users/naheed/IdeaProjects/TD1/RandomSimpComplex/src/configure");
//        boolean areThereInfiniteIntervals = false;
//        double infty = INF;
//        boolean shallInfiniteBarcodesBeIgnored = false;
//        double valueOfInfinity = INF;
//        boolean useGridInComputations = false;
//        double gridDiameter = 0.01;
//        double epsi = 0.000005;
//        List <String> conf = new ArrayList<>();
//        conf.add(areThereInfiniteIntervals?"1":"0");
//        conf.add(String.valueOf(infty));
//        conf.add(shallInfiniteBarcodesBeIgnored?"1":"0");
//        conf.add(String.valueOf(valueOfInfinity));
//        conf.add(useGridInComputations?"1":"0");
//        conf.add(String.valueOf(gridDiameter));
//        conf.add(String.valueOf(epsi));
//        configwriter.Write(conf);
    }


    private void generate_barcode_image(BarcodeCollection<Double> circle_intervals,int maxdim) {
        String outputdir_path = "/Users/naheed/IdeaProjects/TD1/RandomSimpComplex/output/barcodes/";
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
                im = BarcodeVisualizer.drawBarcode(interv, "dimension: " + i,maxfiltration); // last argument maximum limit of bar interval
                File outputfile = new File(outputdir_path + i + "_barcode.png");
                ImageIO.write(im, "png", outputfile);
            } catch (IOException ex) {
               ex.printStackTrace();
            }
        }
    }


}
