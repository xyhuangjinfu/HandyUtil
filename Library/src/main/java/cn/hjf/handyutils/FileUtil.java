package cn.hjf.handyutils;

import android.support.annotation.Nullable;
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

public final class FileUtil {

    private static final String TAG = "HandyUtils-FileStorage";

    /**
     * 保存数据
     *
     * @param absolutePath
     * @param datas
     * @return
     */
    public static boolean save(String absolutePath, byte[] datas) {
        File destFile = createFile(absolutePath);
        if (destFile == null) {
            return false;
        }
        if (!hasMoreSpace(destFile, datas.length)) {
            return false;
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(destFile);
            fileOutputStream.write(datas);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }
        return true;
    }

    /**
     * 保存数据
     *
     * @param absolutePath
     * @param object
     * @return
     */
    public static boolean save(String absolutePath, Serializable object) {
        byte[] objectData = objectToByteArray(object);
        if (objectData == null) {
            return false;
        }
        return save(absolutePath, objectData);
    }

    /**
     * 读取数据
     *
     * @param absolutePath
     * @return
     */
    @Nullable
    public static byte[] readBytes(String absolutePath) {
        byte[] data = null;
        try {
            File destFile = new File(absolutePath);
            FileInputStream fileInputStream = new FileInputStream(destFile);
            data = new byte[fileInputStream.available()];
            fileInputStream.read(data);
            fileInputStream.close();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return data;
    }

    /**
     * 读取数据
     *
     * @param absolutePath
     * @return
     */
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
            Log.e(TAG, e.toString());
        }
        return object;
    }

    /**
     * Object转换成byte数组
     *
     * @param object
     * @return
     */
    @Nullable
    public static byte[] objectToByteArray(Serializable object) {
        if (object == null) {
            return null;
        }

        byte[] bytes = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException e) {
            System.out.println(e);
            Log.e(TAG, e.toString());
        }
        return bytes;
    }

    /**
     * byte数组转换成Object
     *
     * @param byteArray
     * @return
     */
    @Nullable
    public static Object byteArrayToObject(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }

        Object object = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
            ObjectInputStream ois = new ObjectInputStream(bis);
            object = ois.readObject();
            ois.close();
            bis.close();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return object;
    }

    /**
     * 复制文件
     *
     * @param fromPath
     * @param toPath
     * @param cover    true-真实进行了复制。false-复制失败或者非覆盖模式下，文件已存在
     * @return
     */
    public static boolean copy(String fromPath, String toPath, boolean cover) {
        //创建目标文件
        File toFile = createFile(toPath);
        if (toFile == null) {
            return false;
        }
        //创建源文件
        File fromFile = createFile(fromPath);
        if (fromFile == null) {
            return false;
        }
        //覆盖模式下，删除已有目标文件
        if (cover) {
            if (toFile.exists() && !toFile.delete()) {
                Log.e(TAG, "cover mode, cannot delete dest file : " + toPath);
                return false;
            }
        } else {
            //不覆盖，文件已存在
            if (toFile.exists()) {
                return false;
            }
        }
        //从源文件读取数据
        byte[] data = readBytes(fromPath);
        if (data == null) {
            Log.e(TAG, "data read from : " + fromPath + ", is null");
            return false;
        }
        //写入目标文件
        return save(toPath, data);
    }

    /**
     * 删除该路径的文件
     *
     * @param path
     * @return
     */
    public static boolean delete(String path) {
        File file = new File(path);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 该路径对应的文件是否存在
     *
     * @param path
     * @return
     */
    public static boolean exists(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * 创建文件和上级目录
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
     * 计算是否有足够的空间
     *
     * @param file
     * @param needSpace
     * @return
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
     * 向上寻找已经存在的 parent file
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
}
