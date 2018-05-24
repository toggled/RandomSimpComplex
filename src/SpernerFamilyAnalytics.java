import org.joda.time.DateTime;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by naheed on 5/20/17.
 */
public class SpernerFamilyAnalytics extends Analytics {

    Map<Integer,Double> map = new TreeMap<>();
    String spernerfamily_sizes= "";
    String spernerfamily_percentage = "";
    ArrayList<String> probvarystrlist;
    Map <Integer,Double> arrayofMaps[]; // to store map variable for each probability


    SpernerFamilyAnalytics(int NumOfVertices){
        super(NumOfVertices);
    }

    SpernerFamilyAnalytics(int NumOfVertices, float probability) {
        super(NumOfVertices, probability);
    }

    void runTtimes(int T){
        /**
         * Run Delta(n,p) , T times and log size of the simplices.
         */
        probvarystrlist = new ArrayList<>();
        for (int i = 1; i<= T ; i ++){
            this.run_once();

            Double val;
//            System.out.println(this.SpernerFamilySize);

            if( (val = map.putIfAbsent(this.SpernerFamilySize,(double)1)) !=null ){
                map.put(this.SpernerFamilySize,val + ((double)1));
            }
        }
//        System.out.println(maptoString());
//        probvarystrlist.add(maptoString());
    }

    void runforp(int T, double[] prob){
        probvarystrlist = new ArrayList<>(prob.length);
        arrayofMaps = new Map [prob.length];

        for(int i = 0; i<prob.length; i++) {
            System.out.println(this.type+"-running for: "+prob[i]);
            this.p = (float) prob[i];
            map = new TreeMap<>();
            spernerfamily_sizes= "";
            spernerfamily_percentage = "";
            runTtimes(T);
            for(Map.Entry<Integer, Double> entry : map.entrySet()){
                // filling from Bottom up
                for(int j = i-1; j>=0; j--){
                    if(!arrayofMaps[j].containsKey(entry.getKey())) // fill up the missing size as 0.0
                        arrayofMaps[j].put(entry.getKey(),0.0);
                }
            }
            arrayofMaps[i] = map;
        }
        // Fill up from top-down
        for(int i=0; i<prob.length-1; i++){
            for (Integer key:arrayofMaps[i].keySet()){
                for(int j=i+1;j<prob.length; j++){
                    if(!arrayofMaps[j].containsKey(key))
                        arrayofMaps[j].put(key,0.0);
                }
            }
        }

        probvarystrlist.add(" "+getkeyasString(arrayofMaps[0]));
        for(int i = 0; i<prob.length; i++) {
            String tempstrrep = Double.toString(prob[i])+" ";
            tempstrrep+=getvalueasString(arrayofMaps[i]);
            probvarystrlist.add(tempstrrep);
        }
    }

    String maptoString(){

        for (Map.Entry<Integer, Double> entry : map.entrySet()) {
            spernerfamily_sizes+=(entry.getKey().toString()+" ");
            spernerfamily_percentage+=(entry.getValue().toString()+" ");
        }
        return spernerfamily_sizes+"\n"+spernerfamily_percentage;
    }

    String getkeyasString(Map <Integer,Double> tmap){
        String keystr = "";
        for (Map.Entry<Integer, Double> entry : tmap.entrySet()) {
            keystr+=(Integer.valueOf(entry.getKey())+" ");
        }
        return keystr;
    }

    String getvalueasString(Map <Integer,Double> tmap){
        String valstr = "";
        for (Map.Entry<Integer, Double> entry : tmap.entrySet()) {
            valstr+=(Double.valueOf(entry.getValue())+" ");
        }
        return valstr;
    }
    public void WritevarypSpernSize(String filepath){
        String toappend = this.type+"SpernerFamilyAnalytics("+Integer.toString(this.N)+")"+new DateTime( GregorianCalendar.getInstance().getTime() ).toString("yyyy-MM-dd HH-mm-ss");
        Path path = Paths.get(filepath+"/"+toappend+".txt");
        Write(this.probvarystrlist,path);
    }
}
