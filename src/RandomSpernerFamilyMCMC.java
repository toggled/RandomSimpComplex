/**
 * Created by naheed on 5/18/18.
 */

import org.apache.commons.math3.util.CombinatoricsUtils;
import java.math.BigInteger;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class RandomSpernerFamilyMCMC {
    int N;
    SpernerFamily sf;
    HashMap <String, Integer> stats = new HashMap<>();

    RandomSpernerFamilyMCMC(int N){
        this.N = N;
        sf = new SpernerFamily(this.N);

        // Initial state X_0
        sf.generate();
        // update the stats table
        updateTable(sf.toCryptoString());
    }

    private void doRandomwalk(double eps){

//        System.out.println("The Hex repr is : \n"+ sf.toCryptoString());

        // compute mixing time upperbound T.
//        BigInteger T = getT(eps);
        long T = getT_long(eps);
        // probability of addition p.
        double add_probability = 1 / (Math.pow(2,N) - 1);

//        while (T.compareTo(BigInteger.ZERO)>0){
        while(T>0){
//            System.out.println(T.toString());

//            System.out.println(T);
            // Generate X(i+1) from X(i)
            // 1. Pick \sigma
            BitSet sigma = this.getRandomBitset(N);

//            System.out.println(sf.bitsettoString(sigma));
            if (new Random().nextDouble()<add_probability) {
                // 2. Attempt Add
                boolean add_status = sf.addMaximalSet(sigma);
//                System.out.println("add "+add_status);
                if (add_status){
                    updateTable(sf.toCryptoString());
                }
//            if (sf.addMaximalSet(sigma)){
//
//            }
            }
            else {
                // 3. Attempt Del
                try {
                    boolean del_status = sf.removeMaximalSet(sigma);
//                    System.out.println("del "+del_status);
                    if (del_status){
                        updateTable(sf.toCryptoString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // Decrement counter
//            T = T.subtract(BigInteger.ONE);
            T--;
        }
    }

    long getT_long(double eps){
        long T = (long) Math.pow(2,N) - 1;
        T = T*T;
        long diam = CombinatoricsUtils.binomialCoefficient(this.N, (int) Math.floor(N/2.0f));
        long lg_diam_by_eps = (long) Math.ceil(Math.log(diam/eps));

        return T*lg_diam_by_eps;
    }

    BigInteger getT(double eps){

        // initialze T = (2^N - 1)
        BigInteger T = BigInteger.ONE.shiftLeft(N).subtract(BigInteger.ONE);
        T = T.pow(2); // T = (2^N - 1)^2 now

        // compute diam = n choose floor(n/2)
        long diam = CombinatoricsUtils.binomialCoefficient(this.N, (int) Math.floor(N/2.0f));
        long lg_diam_by_eps = (long) Math.ceil(Math.log(diam/eps));

        T = T.multiply(BigInteger.valueOf(lg_diam_by_eps));
        return T;
    }

    SpernerFamily getEpsilonUnformSample(double epsilon){
        // run mcmc
        this.doRandomwalk(epsilon);
        return sf;
    }

    BitSet getRandomBitset(int N){
        /**
         * Return a random bitset which is not empty
         */
        BitSet randbs = new BitSet(N);
        do {
            for (int id = 0; id < N; id++) {
                randbs.set(id, new Random().nextBoolean());
            }
        }while (randbs.isEmpty());

        return randbs;
    }
//    boolean isZero(byte[] array){
//        /**
//         * returns true if the bytearray b is all zeros, false otherwise
//         */
//        for (byte b : array) {
//            if (b != 0) {
//                return false;
//            }
//        }
//        return true;
//    }
    void updateTable(String hexstring){
        Integer count = stats.get(hexstring);
        if (count == null) {
            stats.put(hexstring, 1);
        }
        else {
            stats.put(hexstring, count + 1);
        }
    }
    void writetoCSV(String fileName, String delimeter) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName);
            for(String key: this.stats.keySet()){
                String val = String.valueOf(this.stats.get(key));
                fileWriter.append(key+delimeter+val+"\n");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }

        }
    }
}
