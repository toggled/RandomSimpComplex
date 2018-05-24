import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by naheed on 7/29/17.
 */
public class BarcodeWriter{
    Path pathtowrite;
    BarcodeWriter(String path){
        pathtowrite = Paths.get(path);
    }
    Path getpath(){
        return pathtowrite;
    }

    public void Write(List<String> linestowrite) {
        try {
            Files.write(this.pathtowrite, linestowrite, Charset.forName("UTF-8"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
