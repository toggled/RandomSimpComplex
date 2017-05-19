/**
 * Created by naheed on 5/19/17.
 */

import java.nio.file.Path;
import java.util.List;

public interface WriteHandler {
    void Write(List<String> linestowrite, Path filepath);
    void Write(String filepath);
}
