/**
 * Created by naheed on 5/17/18.
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.TreeSet;
import java.util.BitSet;
import java.util.Comparator;



public class SpernerFamily extends HyperGraph{
    double probablity;

    TreeSet<BitSet> maximalsets = new TreeSet<BitSet>(new Comparator<BitSet>() {
        @Override
        public int compare(BitSet lhs, BitSet rhs)
        {
            /***
             * Compare two bitset.
             * If the value represented by lhs is greater than that by rhs (lhs>rhs), returns -1
             * If lhs<rhs, returns 1
             * returns 0 if lhs = rhs
             */
            if (lhs.equals(rhs)) return 0;
            BitSet xor = (BitSet)lhs.clone();
            xor.xor(rhs);
            int firstDifferent = xor.length()-1;
            if(firstDifferent==-1)
                return 0;

            return rhs.get(firstDifferent) ? 1 : -1;
        }
    });

    int numOfkSets[];
    private String hexrepr= "";

    SpernerFamily(int N) {
        /**
         * Constructor to generate a sperner family over N vertices and random probability within (0,1)
         */
        super(N);
        this.numOfkSets = new int[N+1];
        this.probablity = Math.random();
    }

    SpernerFamily(int N, float p){
        /**
         * Constructor to generate a sperner family over N vertices and probability p.
         */
        super(N);
        this.numOfkSets = new int[N+1];
        this.probablity = p;
    }



    protected boolean addMaximalSet(BitSet set){
        /**
         * Adds a set to the sperner family . Returns true if succeeds, false if it fails (
         * when the set is already in the sperner family)
         */

        if (this.contains(set)){
            return false;
        }

//      If this set is not maximal in "maximalsets".
        if (!this.isMaximal(set)){
            return false;
        }

//         Compute signature of the given element (set)
        String set_crypto_repr = this.getCryptorepr(this.bitsettoString(set));

//        Insert the given element in Treeset
        this.maximalsets.add(set);

//        Find the least element in the Treeset strictly greater than the given element.
        BitSet greaterthanThis = this.maximalsets.higher(set);


        if (greaterthanThis !=null) { // when there exists an element greater than this
            String substringtoFind = this.bitsettoString(greaterthanThis);

            //  Find the index of the first occurance of the substring = the signature of "greaterthanThis".
            int idx_end_firstsubstr = this.hexrepr.indexOf(this.getCryptorepr(substringtoFind));

            // insert before substringtoFind since when we traverse the treeset in ascending order the given bitset will
            // be traversed before the bitset greatherthanThis.
            hexrepr = hexrepr.substring(0,idx_end_firstsubstr) +
                    set_crypto_repr +
                    hexrepr.substring(idx_end_firstsubstr,hexrepr.length());
        }
        else{ // this element is the greatest element

            // since the greatest bitset will be traversed at the end of the treeset.
            hexrepr =  hexrepr + set_crypto_repr ;
        }


        if(set.cardinality()>this.maxcardinality) {
            maxcardinality = set.cardinality();
            this.max = (BitSet) set.clone();
        }
        // Update the array which tracks the cardinality of the k-sets.
        this.numOfkSets[set.cardinality()]++;

        return true;
    }

    protected boolean removeMaximalSet(BitSet set) throws Exception {
        /**
         * Removes a set from the sperner family if it exists. Returns true if succeeds, false if the remove fails (
         * when the set is not in the sperner family)
         */

        if (!this.contains(set)){
            return false;
        }

//        Compute signature of the set to remove (needed to remove its signature string from hexrepr.
        String set_crypto_repr = this.getCryptorepr(this.bitsettoString(set));

//        Remove the bitSet from the treeset
        this.maximalsets.remove(set);


        // replace remove the hexstring corresponding to the bitSet from hexrepr.
        this.hexrepr = this.hexrepr.replace(set_crypto_repr,"");

        // If even after removal the signature still contains the sets hexstring, there is a collision between
        // hash values of the bitsets.
        if (this.hexrepr.contains(set_crypto_repr)){
            throw new Exception();
        }

        return true;
    }

    @Override
    void generate() {
        /**
         * Generate a sperner family from an associated simplicial complex of a random hypergraph.
         * Think of it as an initial Sperner family.
         */
        AssociatedRandSimplicialComplex container = new AssociatedRandSimplicialComplex(this.numberOfVertices,probablity);
        container.generate();
        for(BitSet b:container.simplices){
            this.addMaximalSet(b);
//            System.out.println(this.hexrepr);
//            System.out.println(this.toString());
        }
//        md5repr = container.getMD5repr();
//        System.out.println(this.md5repr + " \n"+ container.toString());

        // Save memory by clearing the container once all the sets are generated and added to the maximalsets variable.
        container.clear();

    }

    protected boolean isSubset(BitSet a, BitSet b){
        /*
        returns True, if a is a subset of b,
                False, otherwise
         */
        if(a.cardinality()>=b.cardinality()) return false; // a shouldn't be larger or equal in cardinality.
        else{
            BitSet a_copy = (BitSet) a.clone();
            a_copy.andNot(b);
            return a_copy.length() == 0;
        }
    }

    boolean isMaximal(BitSet set){
        /**
         * Returns false if the "set" is a subset of some set in "maximalsets"
         */
        for (BitSet potentialsuperset:this.maximalsets ){
            if(isSubset(set,potentialsuperset))
                return false;
        }
        return true;
    }


    public String toString(){
        /**
         * Returns human readable string representation of the sperner family, where sets are seperated by '/'
         */
        String stringrep = "";
        for(BitSet bs: this.maximalsets){
            stringrep+= (bitsettoString(bs)+"/");
        }
        if (stringrep.length()>0)
            return stringrep.substring(0,stringrep.length()-1);
        else
            return "Empty sperner family";
    }

    String toCryptoString(){
        /**
         * Returns a unique Hex String representation of this Sperner family.
         */
        if (this.hexrepr.isEmpty()){
            return "EmptySF";
        }
        return this.hexrepr;
    }


    private boolean contains(BitSet set){
        /**
         * returns true if this sperner family contains the set, false otherwise
         */
        if (this.maximalsets.contains(set)){
            return true;
        }
        return false;
    }

    private String getCryptorepr(String data){
        /***
         * Returns cryptographic representation of the string: data as a hexstring
         */
        return this.getMD5repr(data);
    }

    private String getSHA1repr(String data){
        /***
         * Returns the SHA1 representation of the sting: data.
         */
        MessageDigest messageDigest;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(data.getBytes());
            byte[] messageDigestsha1 = messageDigest.digest();

            for (byte bytes : messageDigestsha1) {
                stringBuffer.append(String.format("%02x", bytes & 0xff));
            }

        }catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();

        }
        return stringBuffer.toString();
    }

    private String getMD5repr(String data){
        /***
         * Returns the MD5 representation of the sting: data.
         */
        MessageDigest messageDigest;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            messageDigest = MessageDigest.getInstance("md5");
            messageDigest.update(data.getBytes());
            byte[] messageDigestsha1 = messageDigest.digest();

            for (byte bytes : messageDigestsha1) {
                stringBuffer.append(String.format("%02x", bytes & 0xff));
            }

        }catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();

        }
        return stringBuffer.toString();
    }

}
