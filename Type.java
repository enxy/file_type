package ext;
import java.util.Map;
import java.util.*;

public class Types {
    private Map<String,int[]> signatures;
    private static final int SIZE_OF_BUFFER = 4*1024;
    private static final int SIZE_OF_SIGNATURE = 8;
    private static final int[] jpeg = {0xcf, 0x84, 0x01};
    private static final int[] gif = {0x47, 0x49, 0x46, 0x38, 0x37, 0x61};
    private static final int[] png ={0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A};
    private static final int[] text = {0x46, 0x4F, 0x52, 0x4D, 0x46, 0x54, 0x58, 0x54, 0xEF, 0xBB, 0xBF};
    private static final int[] pdf ={0x25, 0x50, 0x44, 0x46};

    public Set getAvailableExt() {
        return signatures.keySet();
    }

    public static void main(String[] args) throws Exception {

        Types types = new Types();
        Set<String> extSet = types.getAvailableExt();
        System.out.println(extSet);
    }
}
