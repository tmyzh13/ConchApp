package com.corelibs.utils.download;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.corelibs.utils.download.AppUtil.sApp;

class StorageUtil {
    private static final String DIR_NAME_APK = "cache_file";

    private StorageUtil() {
    }

    /**
     * 获取 apk 文件夹
     *
     * @return
     */
    static File getApkFileDir() {
        return sApp.getExternalFilesDir(DIR_NAME_APK);
    }

    /**
     * 获取 apk 文件
     *
     * @param fileName
     * @return
     */
    static File getApkFile(String fileName) {
        return new File(getApkFileDir(), fileName);
    }

    /**
     * 保存 apk 文件
     *
     * @param is
     * @param fileName
     * @return
     */
    static File saveApk(InputStream is, String fileName) {
        File file = getApkFile(fileName);

        if (writeFile(file, is)) {
            return file;
        } else {
            return null;
        }
    }

    /**
     * 根据输入流，保存文件
     *
     * @param file
     * @param is
     * @return
     */
    static boolean writeFile(File file, InputStream is) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            byte data[] = new byte[1024];
            int length = -1;
            while ((length = is.read(data)) != -1) {
                os.write(data, 0, length);
            }
            os.flush();
            return true;
        } catch (Exception e) {
            if (file != null && file.exists()) {
                file.deleteOnExit();
            }
            e.printStackTrace();
        } finally {
            closeStream(os);
            closeStream(is);
        }
        return false;
    }

    /**
     * 删除文件或文件夹
     *
     * @param file
     */
    static void deleteFile(File file) {
        try {
            if (file == null || !file.exists()) {
                return;
            }

            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null && files.length > 0) {
                    for (File f : files) {
                        if (f.exists()) {
                            if (f.isDirectory()) {
                                deleteFile(f);
                            } else {
                                f.delete();
                            }
                        }
                    }
                }
            } else {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭流
     *
     * @param closeable
     */
    static void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
