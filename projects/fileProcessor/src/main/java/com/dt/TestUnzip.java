package com.dt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class TestUnzip {
    public static void main(String[] args) throws IOException {
        String fileZip = "C:\\u01\\app\\do\\error\\13-06-2019\\12-06-2019.zip";
        File destDir = new File("C:\\u01\\app\\do\\unzip\\");
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = newFile(destDir, zipEntry);
            FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }

    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }
        Integer a=0, b=0, c=0 ,d=0;
        if (a ==1 ){
            if (b ==2 ){
                if (c ==3 ) {
                    if (d ==4){

                    }
                } else if (b ==4 ){
                    if (a == 7);
                }
            }
        }

        return destFile;
    }
}
