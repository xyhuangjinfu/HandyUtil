package cn.hjf.handyutils;

import junit.framework.Assert;

import org.junit.Test;

import java.util.Arrays;

/**
 * Created by huangjinfu on 2017/7/13.
 */
public class PrimitiveUtilTest {
    @Test
    public void toDouble() throws Exception {
        byte[] bytes = new byte[]{(byte) 0x40, (byte) 0xb9, (byte) 0x7c, (byte) 0x78, (byte) 0xf5, (byte) 0xc2, (byte) 0x8f, (byte) 0x5c};
        Assert.assertEquals(PrimitiveUtil.toDouble(PrimitiveUtil.Endian.BIG, bytes), 6524.4725);
    }

    @Test
    public void toByteArray() {
        long l = 123456789123456789l;
        System.out.println(Long.toBinaryString(l));
        byte[] bytes = PrimitiveUtil.toByteArray(PrimitiveUtil.Endian.BIG, l);
        System.out.println(Arrays.toString(bytes));
        Assert.assertEquals(l, PrimitiveUtil.toLong(PrimitiveUtil.Endian.BIG, bytes));
    }

    @Test
    public void shortTest() {
        short s = 23;
        byte[] big = PrimitiveUtil.toByteArray(PrimitiveUtil.Endian.BIG, s);
        Assert.assertEquals(s, PrimitiveUtil.toShort(PrimitiveUtil.Endian.BIG, big));
        byte[] little = PrimitiveUtil.toByteArray(PrimitiveUtil.Endian.LITTLE, s);
        Assert.assertEquals(s, PrimitiveUtil.toShort(PrimitiveUtil.Endian.LITTLE, little));
    }

    @Test
    public void longTest() {
        long l = 123456789123456789l;
        byte[] big = PrimitiveUtil.toByteArray(PrimitiveUtil.Endian.BIG, l);
        Assert.assertEquals(l, PrimitiveUtil.toLong(PrimitiveUtil.Endian.BIG, big));
        byte[] little = PrimitiveUtil.toByteArray(PrimitiveUtil.Endian.LITTLE, l);
        Assert.assertEquals(l, PrimitiveUtil.toLong(PrimitiveUtil.Endian.LITTLE, little));

        long l2 = 987654321987654321l;
        long[] longs = new long[]{l, l2};

        byte[] b = PrimitiveUtil.toByteArray(PrimitiveUtil.Endian.BIG, longs);
        byte[] b1 = new byte[b.length / 2];
        System.arraycopy(b, 0, b1, 0, b1.length);
        Assert.assertEquals(l, PrimitiveUtil.toLong(PrimitiveUtil.Endian.BIG, b1));
        byte[] b2 = new byte[b.length / 2];
        System.arraycopy(b, b1.length, b2, 0, b2.length);
        Assert.assertEquals(l2, PrimitiveUtil.toLong(PrimitiveUtil.Endian.BIG, b2));

        byte[] bb = PrimitiveUtil.toByteArray(PrimitiveUtil.Endian.LITTLE, longs);
        byte[] b3 = new byte[bb.length / 2];
        System.arraycopy(bb, 0, b3, 0, b3.length);
        Assert.assertEquals(l, PrimitiveUtil.toLong(PrimitiveUtil.Endian.LITTLE, b3));
        byte[] b4 = new byte[bb.length / 2];
        System.arraycopy(bb, b3.length, b4, 0, b4.length);
        Assert.assertEquals(l2, PrimitiveUtil.toLong(PrimitiveUtil.Endian.LITTLE, b4));
    }
}