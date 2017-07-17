package cn.hjf.handyutils;

/**
 * 原始数据类型转换工具
 * Created by huangjinfu on 2017/6/29.
 */

public final class PrimitiveUtil {

    public enum Endian {
        BIG,
        LITTLE
    }

    public static byte[] toByteArray(Endian endian, short s) {
        byte[] bytes = new byte[2];
        int bitsOfShort = Short.SIZE / Byte.SIZE;
        for (int i = 0; i < bitsOfShort; i++) {
            int index = endian == Endian.LITTLE ? i : bitsOfShort - 1 - i;
            bytes[index] = (byte) (s & 0x000000FF);
            s = (short) (s >>> Byte.SIZE);
        }
        return bytes;
    }

    public static byte[] toByteArray(Endian endian, long l) {
        int bitsOfLong = Long.SIZE / Byte.SIZE;
        byte[] bytes = new byte[bitsOfLong];
        for (int i = 0; i < bitsOfLong; i++) {
            int index = endian == Endian.LITTLE ? i : bitsOfLong - 1 - i;
            bytes[index] = (byte) (l & 0x000000FF);
            l = l >>> Byte.SIZE;
        }
        return bytes;
    }

    public static byte[] toByteArray(Endian endian, long[] longArray) {
        int bitsOfLong = Long.SIZE / Byte.SIZE;
        byte[] bytes = new byte[longArray.length * bitsOfLong];
        for (int i = 0; i < longArray.length; i++) {
            int index = i * bitsOfLong;
            System.arraycopy(toByteArray(endian, longArray[i]), 0, bytes, index, bitsOfLong);
        }
        return bytes;
    }

    public static int toShort(Endian endian, byte... bytes) {
        int value = 0;
        for (int i = 0; i < bytes.length; i++) {
            int index = endian == Endian.BIG ? i : bytes.length - 1 - i;
            value = (value << Byte.SIZE) | (bytes[index] & 0x000000FF);
        }
        return value;
    }

    public static int toInt(Endian endian, byte... bytes) {
        int value = 0;
        for (int i = 0; i < bytes.length; i++) {
            int index = endian == Endian.BIG ? i : bytes.length - 1 - i;
            value = (value << Byte.SIZE) | (bytes[index] & 0x000000FF);
        }
        return value;
    }

    public static long toLong(Endian endian, byte... bytes) {
        long value = 0;
        for (int i = 0; i < bytes.length; i++) {
            int index = endian == Endian.BIG ? i : bytes.length - 1 - i;
            value = (value << Byte.SIZE) | (bytes[index] & 0x000000FF);
        }
        return value;
    }

    public static double toDouble(Endian endian, byte... bytes) {
        long value = 0;
        for (int i = 0; i < bytes.length; i++) {
            int index = endian == Endian.BIG ? i : bytes.length - 1 - i;
            value = (value << Byte.SIZE) | (bytes[index] & 0x000000FF);
        }
        return Double.longBitsToDouble(value);
    }
}
