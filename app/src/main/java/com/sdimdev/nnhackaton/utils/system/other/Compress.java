package com.sdimdev.nnhackaton.utils.system.other;


import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Util for zip and unzip files
 */
public class Compress {
    private static final String COMPRESS_TAG = "Compress";
    private static final int BUFFER = 2048;

    /**
     * Compress all files to zip-file.
     * @param files - array of files. Folders will be ignored
     * @param zipFile - path to create zip-archive
     * @return true if zip-file successfully created, false - otherwise
     */
    public static boolean zip(Collection<File> files, String zipFile) {
        try {
            ZipOutputStream out = null;
            try {
                // open Zip-stream
                out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
                byte data[] = new byte[BUFFER];

                for(File file : files) {
                    Log.v(COMPRESS_TAG, String.format("Adding: %s", file.toString()));

                    if (file.isDirectory()) {
                        Log.v(COMPRESS_TAG, String.format("File is directory: %s", file.toString()));
                        continue;
                    }

                    if (file.exists() == false) {
                        Log.v(COMPRESS_TAG, String.format("File doesn't exist: %s", file.toString()));
                        continue;
                    }

                    BufferedInputStream origin = null;
                    try  {
                        // open stream for reading
                        origin = new BufferedInputStream(new FileInputStream(file),	BUFFER);

                        // create ZipEntry and add it to Zip-stream
                        ZipEntry entry = new ZipEntry(file.getName());
                        out.putNextEntry(entry);

                        // copy source data to Zip-stream (they putted to open ZipEntry)
                        int count;
                        while ((count = origin.read(data, 0, BUFFER)) != -1) {
                            out.write(data, 0, count);
                        }

                        // close ZipEntry
                        out.closeEntry();
                    }
                    finally
                    {
                        if (origin != null) {
                            origin.close();
                        }
                    }
                }
            }
            finally {
                if (out != null) {
                    out.close();
                }
            }
            return true;
        }
        catch (Exception e) {
            Log.e(COMPRESS_TAG, "zipping failed", e);
            return false;
        }
    }

    /**
     * Unzip files from zip
     * @param zip - path to zip-file
     * @param outputPath - path to create files from zip
     * @return true if zip-file successfully unzipped, false - otherwise
     */
    public static boolean unzip(File zip, File outputPath) {
        try {
            FileInputStream fin = new FileInputStream(zip);
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {
                if (ze.isDirectory() == false) {
                    if (outputPath.exists() == false) {
                        outputPath.mkdir();
                    }

                    File f = new File(outputPath, ze.getName());
                    if (!f.exists()) {
                        f.createNewFile();
                    }
                    FileOutputStream fout = new FileOutputStream(f);

                    byte[] buffer = new byte[4096];
                    int count;
                    while ((count = zin.read(buffer)) != -1) {
                        fout.write(buffer, 0, count);
                    }

                    zin.closeEntry();
                    fout.close();
                }
            }
            zin.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}