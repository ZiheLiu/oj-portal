package com.ziheliu.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class ZipUtils {
  public static Map<String, byte[]> uncompress(InputStream inputStream) throws IOException {
    ZipInputStream zis = null;

    try {
      zis = new ZipInputStream(inputStream);
      Map<String, byte[]> map = new HashMap<>();
      ZipEntry ze;
      while ((ze = zis.getNextEntry()) != null) {
        if (!ze.isDirectory()) {
          String name = ze.getName();

          ByteArrayOutputStream byteArrayOutputStream = null;
          try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = zis.read(buffer, 0, buffer.length)) > -1) {
              byteArrayOutputStream.write(buffer, 0, length);
            }
            map.put(name, byteArrayOutputStream.toByteArray());
          } finally {
            if (byteArrayOutputStream != null) {
              byteArrayOutputStream.close();
            }
          }
        }
      }

      return map;
    } finally {
      if (zis != null) {
        zis.close();
      }
    }
  }

  public static void write(Map<String, byte[]> files, String targetDirectory) throws IOException {
    for (Map.Entry<String, byte[]> entry : files.entrySet()) {
      File file = new File(targetDirectory + "/" + entry.getKey());
      File parent = file.getParentFile();
      if (!parent.exists()) {
        parent.mkdirs();
      }
      OutputStream fout = null;
      try {
        fout = new FileOutputStream(file);
        fout.write(entry.getValue());
      } finally {
        if (fout != null) {
          fout.close();
        }
      }
    }
  }
}
