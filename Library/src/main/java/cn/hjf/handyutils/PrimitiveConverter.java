package cn.hjf.handyutils;

/**
 * Created by huangjinfu on 2017/6/29.
 */

public final class PrimitiveConverter {

    public enum Endian {
        BIG,
        LITTLE
    }

    public static byte[] toBytes(Endian endian, short s) {
        byte[] bytes = new byte[2];
        int bitsOfShort = Short.SIZE / Byte.SIZE;
        for (int i = 0; i < bitsOfShort; i++) {
            int index = endian == Endian.LITTLE ? i : bitsOfShort - 1 - i;
            bytes[index] = (byte) (s & 0x000000FF);
            s = (short) (s >>> Byte.SIZE);
        }
        return bytes;
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

//    public static int toUnsignedInt(byte x) {
//        return ((int) x) & 0xff;
//    }
//
//    public static long toUnsignedLong(int data) {
//        return data & 0x00000000FFFFFFFFL;
//    }
//
//    public static byte[] toBytes(double d) {
//        long value = Double.doubleToRawLongBits(d);
//        byte[] byteRet = new byte[8];
//        for (int i = 0; i < 8; i++) {
//            byteRet[i] = (byte) ((value >> 8 * i) & 0xff);
//        }
//        return byteRet;
//    }
//
//    public static int toInt(byte... bytes) {
//        int value = 0;
//        for (int i = 0; i < bytes.length; i++) {
//            value = (value << 8) | bytes[i];
//        }
//        return value;
//    }
//
//    public static double bytes2Double(byte[] arr) {
//        long value = 0;
//        for (int i = 0; i < 8; i++) {
//            value |= ((long) (arr[i] & 0xff)) << (8 * i);
//        }
//        return Double.longBitsToDouble(value);
//    }
}
