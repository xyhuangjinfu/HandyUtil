package cn.hjf.handyutils;

/**
 * 原始数据类型转换工具
 * Created by huangjinfu on 2017/6/29.
 */

public final class PrimitiveUtil {

    private static final int BYTE_MASK = 0x000000FF;

    public enum Endian {
        BIG,
        LITTLE
    }

    public static byte[] toByteArray(Endian endian, short sValue) {
        int bitsOfShort = Short.SIZE / Byte.SIZE;
        byte[] bytes = new byte[bitsOfShort];
        for (int i = 0; i < bitsOfShort; i++) {
            int index = endian == Endian.LITTLE ? i : bitsOfShort - 1 - i;
            bytes[index] = (byte) (sValue & BYTE_MASK);
            sValue = (short) (sValue >>> Byte.SIZE);
        }
        return bytes;
    }

    public static byte[] toByteArray(Endian endian, int iValue) {
        int bitsOfInt = Integer.SIZE / Byte.SIZE;
        byte[] bytes = new byte[bitsOfInt];
        for (int i = 0; i < bitsOfInt; i++) {
            int index = endian == Endian.LITTLE ? i : bitsOfInt - 1 - i;
            bytes[index] = (byte) (iValue & BYTE_MASK);
            iValue = iValue >>> Byte.SIZE;
        }
        return bytes;
    }

    public static byte[] toByteArray(Endian endian, float fValue) {
        int iValue = Float.floatToIntBits(fValue);
        return toByteArray(endian, iValue);
    }

    public static byte[] toByteArray(Endian endian, long l) {
        int bitsOfLong = Long.SIZE / Byte.SIZE;
        byte[] bytes = new byte[bitsOfLong];
        for (int i = 0; i < bitsOfLong; i++) {
            int index = endian == Endian.LITTLE ? i : bitsOfLong - 1 - i;
            bytes[index] = (byte) (l & BYTE_MASK);
            l = l >>> Byte.SIZE;
        }
        return bytes;
    }

    public static byte[] toByteArray(Endian endian, double dValue) {
        long lValue = Double.doubleToLongBits(dValue);
        return toByteArray(endian, lValue);
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
            value = (value << Byte.SIZE) | (bytes[index] & BYTE_MASK);
        }
        return value;
    }

    public static int toInt(Endian endian, byte... bytes) {
        int value = 0;
        for (int i = 0; i < bytes.length; i++) {
            int index = endian == Endian.BIG ? i : bytes.length - 1 - i;
            value = (value << Byte.SIZE) | (bytes[index] & BYTE_MASK);
        }
        return value;
    }

    public static float toFloat(Endian endian, byte... bytes) {
        int value = toInt(endian, bytes);
        return Float.intBitsToFloat(value);
    }

    public static long toLong(Endian endian, byte... bytes) {
        long value = 0;
        for (int i = 0; i < bytes.length; i++) {
            int index = endian == Endian.BIG ? i : bytes.length - 1 - i;
            value = (value << Byte.SIZE) | (bytes[index] & BYTE_MASK);
        }
        return value;
    }

    public static double toDouble(Endian endian, byte... bytes) {
        long value = toLong(endian, bytes);
        return Double.longBitsToDouble(value);
    }
}
