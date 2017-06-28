package cn.hjf.handyutils;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 文件系统存储功能
 * Created by huangjinfu on 2016/9/29.
 */

public class FileStorage {

    private static final String TAG = "FileStorage";

    public static boolean save(String absolutePath, byte[] datas) {
        try {
            File destFile = new File(absolutePath);
            if (!destFile.getParentFile().exists()) {
                if (!destFile.getParentFile().mkdirs()) {
                    Log.e(TAG, "create parent dirs fail");
                    return false;
                }
            }
            //容量不足
            if (getExistParent(destFile).getUsableSpace() <= datas.length) {
                Log.e(TAG, "容量不足 : " + destFile.getParentFile().getUsableSpace() + " <= " + datas.length);
                return false;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(destFile);
            fileOutputStream.write(datas);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
            return false;
        }
        return true;
    }

    public static boolean save(String absolutePath, Serializable objectData) {
        //如果待保存的数据为null，并且待保存的path存在，那么就删除该文件
        if (objectData == null) {
            File file = new File(absolutePath);
            if (file.exists()) {
                Log.w(TAG, "data is null, file will be delete : " + absolutePath);
                return file.delete();
            }
            return true;
        }
        try {
            File destFile = new File(absolutePath);
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(destFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(objectData);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static byte[] readBytes(String absolutePath) {
        byte[] datas = null;
        try {
            File destFile = new File(absolutePath);
            FileInputStream fileInputStream = new FileInputStream(destFile);
            datas = new byte[fileInputStream.available()];
            fileInputStream.read(datas);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datas;
    }

    public static Object readObject(String absolutePath) {
        Object object = null;
        try {
            File destFile = new File(absolutePath);
            if (destFile.exists()) {
                FileInputStream fileInputStream = new FileInputStream(destFile);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                object = objectInputStream.readObject();
                objectInputStream.close();
                fileInputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    public static byte[] objectToBytes(Object object) {
        byte[] bytes = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }

    public static Object bytesToObject(byte[] bytes) {
        Object object = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            object = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return object;
    }

    public static boolean copy(String from, String to, boolean cover) {
        File fromFile = new File(from);
        if (!fromFile.exists()) {
            return false;
        }
        File toFile = new File(to);
        if (toFile.exists()) {
            if (cover) {
                toFile.delete();
            } else {
                return false;
            }
        }
        if (!toFile.getParentFile().exists()) {
            toFile.getParentFile().mkdirs();
        }

        try {
            FileInputStream fileInputStream = new FileInputStream(fromFile);
            byte[] fromData = new byte[fileInputStream.available()];
            fileInputStream.read(fromData);
            FileOutputStream fileOutputStream = new FileOutputStream(toFile);
            fileOutputStream.write(fromData);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean delete(String path) {
        File file = new File(path);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    public static boolean exists(String path) {
        File file = new File(path);
        return file.exists();
    }

    private static File getExistParent(File file) {
        File parent = file.getParentFile();
        while (!parent.exists()) {
            parent = parent.getParentFile();
        }
        return parent;
    }
}
