package cn.hjf.handyutils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * A class provide the storage function on file system.
 * Created by huangjinfu on 2016/9/29.
 */

public final class FileUtil {

    private static final String TAG = "HandyUtils-FileStorage";

    /**
     * save data, override the old file if exist.
     *
     * @param absolutePath file path.
     * @param data         data to storage in above path.
     * @return true-save success, false-save failed.
     */
    public static boolean save(String absolutePath, byte[] data) {
        return save(absolutePath, data, false);
    }

    /**
     * save data, client can choose whether or not override the old file if exist.
     *
     * @param absolutePath file path.
     * @param data         data to storage in above path.
     * @param append       true-append save, false-override save.
     * @return true-save success, false-save failed.
     */
    public static boolean save(String absolutePath, byte[] data, boolean append) {
        File destFile = createFile(absolutePath);
        if (destFile == null) {
            return false;
        }
        if (!hasMoreSpace(destFile, data.length)) {
            return false;
        }

        BufferedOutputStream bos = null;
        try {
            FileOutputStream fos = new FileOutputStream(destFile, append);
            bos = new BufferedOutputStream(fos);
            bos.write(data);
            bos.flush();
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                }
            }
        }
        return false;
    }

    /**
     * save serializable object.
     *
     * @param absolutePath file path.
     * @param object       object to storage in above path.
     * @return true-save success, false-save failed.
     */
    public static boolean save(String absolutePath, Serializable object) {
        byte[] objectData = objectToByteArray(object);
        if (objectData == null) {
            return false;
        }
        return save(absolutePath, objectData);
    }

    /**
     * read byte[] from file.
     *
     * @param absolutePath file path from which we read the data.
     * @return file data, or null if file not exist or other error occurs.
     */
    @Nullable
    public static byte[] readBytes(String absolutePath) {
        InputStream is;
        try {
            is = openInputStream(absolutePath);
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.toString());
            return null;
        }

        BufferedInputStream bis = null;
        try {
            byte[] data = new byte[0];
            bis = new BufferedInputStream(is);
            while (bis.available() != 0) {
                byte[] temp = new byte[bis.available()];
                bis.read(temp);
                byte[] newData = new byte[data.length + temp.length];
                System.arraycopy(data, 0, newData, 0, data.length);
                System.arraycopy(temp, 0, newData, data.length, temp.length);
                data = newData;
            }
            return data;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    /**
     * read object from file.
     *
     * @param absolutePath file path from which we read the data.
     * @return file data, or null if file not exist or other error occurs.
     */
    @Nullable
    public static Object readObject(String absolutePath) {
        InputStream is;
        try {
            is = openInputStream(absolutePath);
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.toString());
            return null;
        }

        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(is);
            return ois.readObject();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    /**
     * convert serializable object to byte array.
     *
     * @param object serializable object
     * @return byte array which present this object, or null if object is null or other error occurs.
     */
    @Nullable
    public static byte[] objectToByteArray(Serializable object) {
        if (object == null) {
            return null;
        }

        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            oos.flush();
            return bos.toByteArray();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    /**
     * convert byte array to object
     *
     * @param byteArray byte array which present an object, may generate by {@link FileUtil#objectToByteArray(Serializable)}.
     * @return the object, or null if byte array is null or other error occurs.
     */
    @Nullable
    public static Object byteArrayToObject(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }

        ObjectInputStream ois = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
            ois = new ObjectInputStream(bis);
            return ois.readObject();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    /**
     * copy a file.
     *
     * @param srcPath  the src file path.
     * @param destPath the dest file path.
     * @param cover    true-cover the dest file if exist.
     * @return true-actual do copy in cover mode, or dest file exist when not in cover mode, false-copy failed.
     */
    public static boolean copy(String srcPath, String destPath, boolean cover) {
        //create dest file.
        File srcFile = createFile(srcPath);
        if (srcFile == null) {
            return false;
        }
        //get src file.
        File destFile = createFile(destPath);
        if (destFile == null) {
            return false;
        }
        //not cover mode
        if (!cover) {
            if (destFile.exists()) {
                return true;
            }
        }
        //read data from src file
        byte[] data = readBytes(srcPath);
        if (data == null) {
            Log.e(TAG, "data read from : " + srcPath + ", is null");
            return false;
        }
        //do save
        return save(destPath, data);
    }

    /**
     * delete a file or a directory.
     *
     * @param path file or directory to be deleted.
     * @return true-delete success or file not exist, false-delete failed.
     */
    public static boolean delete(String path) {
        File file = createFile(path);
        if (file == null) {
            return true;
        }
        if (file.isDirectory()) {
            return deleteDir(file);
        }
        if (file.exists()) {
            return file.delete();
        }
        return true;
    }

    /**
     * detect a specific file whether exist.
     *
     * @param path detect path.
     * @return true-exists, false-not exists.
     */
    public static boolean exists(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * ********************************************************************************************************
     * ********************************************************************************************************
     */

    /**
     * create file and it's parents.
     *
     * @param absolutePath
     * @return
     */
    @Nullable
    private static File createFile(String absolutePath) {
        File destFile = new File(absolutePath);
        if (!destFile.getParentFile().exists()) {
            if (!destFile.getParentFile().mkdirs()) {
                Log.e(TAG, "create parent dirs fail, path : " + absolutePath);
                return null;
            }
        }
        return destFile;
    }

    /**
     * calculate remain space whether enough to save the specific file.
     *
     * @param file      file to be saved.
     * @param needSpace data length.
     * @return true-have enough space, false-have't enough space.
     */
    private static boolean hasMoreSpace(File file, long needSpace) {
        File existParent = getExistParent(file);
        if (existParent.getUsableSpace() <= needSpace) {
            Log.e(TAG, "no more space, require : " + needSpace + ", but remain : " + existParent.getUsableSpace());
            return false;
        }
        return true;
    }

    /**
     * find already exist parent along the up direction of the file tree.
     *
     * @param file
     * @return
     */
    private static File getExistParent(File file) {
        File parent = file.getParentFile();
        while (!parent.exists()) {
            parent = parent.getParentFile();
        }
        return parent;
    }

    /**
     * delete a directory.
     *
     * @param dir
     * @return true-delete success, false-delete fail.
     */
    private static boolean deleteDir(File dir) {
        if (dir == null) {
            return false;
        }
        File[] children = dir.listFiles();
        if (children == null) {
            return false;
        }
        boolean deleted = true;
        for (int i = 0; i < children.length; i++) {
            File child = children[i];
            if (child.isDirectory()) {
                deleted &= deleteDir(child);
            } else {
                deleted &= child.delete();
            }
        }
        return deleted & dir.delete();
    }

    /**
     * open a file.
     *
     * @param path file path.
     * @return InputStream object for this file.
     * @throws FileNotFoundException
     */
    @NonNull
    private static InputStream openInputStream(String path) throws FileNotFoundException {
        File file = createFile(path);
        if (file == null) {
            throw new FileNotFoundException(path);
        }
        return new FileInputStream(file);
    }
}
