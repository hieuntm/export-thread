package com.project.util;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtils {

    public static String encodeFileToBase64Binary(File file) {
        try {
            byte[] encoder = Base64.encodeBase64(org.apache.commons.io.FileUtils.readFileToByteArray(file));
            return new String(encoder, StandardCharsets.US_ASCII);
        } catch (Exception ex) {
//        }catch (IOException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static void addToZip(File directoryToZip, File file, ZipOutputStream zos) {
        try (FileInputStream fis = new FileInputStream(file)) {
            String zipFilePath = file.getCanonicalPath().substring(directoryToZip.getCanonicalPath().length() + 1,
                    file.getCanonicalPath().length());
            System.out.println("Writing '" + zipFilePath + "' to zip file");
            ZipEntry zipEntry = new ZipEntry(zipFilePath);
            zos.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }
            zos.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeZipFile(File directoryToZip, List<File> fileList) {
        try {
            File path = directoryToZip.getParentFile();
            File zipFile = new File(path, directoryToZip.getName());
            try (FileOutputStream fos = new FileOutputStream(zipFile)) {
                ZipOutputStream zos = new ZipOutputStream(fos);
                for (File file : fileList) {
                    if (!file.isDirectory()) {
                        addToZip(directoryToZip, file, zos);
                    }
                }
                zos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
