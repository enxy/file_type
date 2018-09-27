package ext;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.*;
import java.lang.IllegalArgumentException;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Type {
    private Map<String,int[]> signatures;
    private static final int SIZE_OF_BUFFER = 4*1024;
    private static final int SIZE_OF_SIGNATURE = 8;
    private static final int[] jpeg = {0xcf, 0x84, 0x01};
    private static final int[] gif = {0x47, 0x49, 0x46, 0x38, 0x37, 0x61};
    private static final int[] png ={0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A};
    private static final int[] text = {0x46, 0x4F, 0x52, 0x4D, 0x46, 0x54, 0x58, 0x54, 0xEF, 0xBB, 0xBF};
    private static final int[] pdf ={0x25, 0x50, 0x44, 0x46};

    public Type() {
        this.signatures = new HashMap<String,int[]>();
        signatures.put("JPG", jpeg);
        signatures.put("GIF", gif);
        signatures.put("TXT", text);
        signatures.put("PNG", png);
        signatures.put("PDF", pdf);
    }

    public Set getAvailableExt() {
        return signatures.keySet();
    }

    public String getFileType(File f) throws IOException {
        byte[] buffer = new byte[SIZE_OF_BUFFER];
        InputStream fileStream = new FileInputStream(f);

        try {
            int content = fileStream.read(buffer, 0, SIZE_OF_BUFFER);
            int a = content;
            String fileType = "";
            for (int x = content; (x < SIZE_OF_SIGNATURE) && (content > 0); x += content) {
                content = fileStream.read(buffer, x, SIZE_OF_BUFFER - x);
            }

            for (Iterator<String> i = signatures.keySet().iterator(); i.hasNext();) {
                String key = i.next();
                if (findMatches(signatures.get(key), buffer, a)) {
                    fileType = key;
                    break;
                }
            }
            return fileType;
        }
        finally {
            fileStream.close();
        }
    }

    private static boolean findMatches(int[] signature, byte[] buffer, int size) {
        boolean exists = true;
        for (int i = 0; i < signature.length; i++) {
            if (signature[i] != (0x00ff & buffer[i])) {
                exists = false;
                break;
            }
        }
        return exists;
    }


    public static void main(String[] args) throws Exception {
        try {
            if (args.length!=1) {
                throw new IllegalArgumentException("You need to give one path to file");
            }
            String filePath = args[0];

            Type type = new Type();
            File f = new File(filePath);
            String realExt = type.getFileType(f);

            int i = filePath.lastIndexOf('.');
            String ext = filePath.substring(i+1);
            Set<String> extSet = type.getAvailableExt();

            if (ext.isEmpty() || !extSet.contains(ext.toUpperCase())) {
                throw new IllegalArgumentException("Unknown file extension. You can use only " + extSet + " files");
            }



        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }
}
