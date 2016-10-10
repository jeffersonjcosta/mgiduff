package br.com.graphs.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class FileUtil {

    public static void download(String filename, byte[] content, HttpServletResponse response) throws IOException {

        response.addHeader("Content-Disposition", "attachment; filename=" + filename);
        response.setContentType("application/octet-stream");
        ServletOutputStream outStream = response.getOutputStream();

        // envia o conteúdo do file para o stream de resposta  
        try {
            outStream.write(content);
            outStream.flush();
        } finally {
            outStream.close();
        }
    }

    public static byte[] read(File file) throws IOException {
        byte[] content = null;
        int fileLength = (int) file.length();
        FileInputStream fileInput = null;

        try {
            fileInput = new FileInputStream(file);

            BufferedInputStream bufferedInput = new BufferedInputStream(fileInput);
            content = new byte[fileLength];
            bufferedInput.read(content, 0, fileLength);
            bufferedInput.close();
        } finally {
            if (fileInput != null) {
                fileInput.close();
            }
        }

        return content;
    }
}
