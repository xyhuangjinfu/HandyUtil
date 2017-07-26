package cn.hjf.handyutils;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by huangjinfu on 2017/7/13.
 */
public class PrimitiveUtilTest {

    @Test
    public void shortTest() {
        short s = 23;
        byte[] big = PrimitiveUtil.toByteArray(PrimitiveUtil.Endian.BIG, s);
        Assert.assertEquals(s, PrimitiveUtil.toShort(PrimitiveUtil.Endian.BIG, big));
        byte[] little = PrimitiveUtil.toByteArray(PrimitiveUtil.Endian.LITTLE, s);
        Assert.assertEquals(s, PrimitiveUtil.toShort(PrimitiveUtil.Endian.LITTLE, little));
    }

    @Test
    public void intTest() {
        int i = 2345678;
        byte[] big = PrimitiveUtil.toByteArray(PrimitiveUtil.Endian.BIG, i);
        Assert.assertEquals(i, PrimitiveUtil.toInt(PrimitiveUtil.Endian.BIG, big));
        byte[] little = PrimitiveUtil.toByteArray(PrimitiveUtil.Endian.LITTLE, i);
        Assert.assertEquals(i, PrimitiveUtil.toInt(PrimitiveUtil.Endian.LITTLE, little));
    }

    @Test
    public void floatTest() {
        float f = 234.567f;
        byte[] big = PrimitiveUtil.toByteArray(PrimitiveUtil.Endian.BIG, f);
        Assert.assertEquals(f, PrimitiveUtil.toFloat(PrimitiveUtil.Endian.BIG, big));
        byte[] little = PrimitiveUtil.toByteArray(PrimitiveUtil.Endian.LITTLE, f);
        Assert.assertEquals(f, PrimitiveUtil.toFloat(PrimitiveUtil.Endian.LITTLE, little));
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

    @Test
    public void doubleTest() throws Exception {
        double d = 12345.6789;
        double delta = 0.0001;
        byte[] big = PrimitiveUtil.toByteArray(PrimitiveUtil.Endian.BIG, d);
        org.junit.Assert.assertEquals(d, PrimitiveUtil.toDouble(PrimitiveUtil.Endian.BIG, big), delta);
        byte[] little = PrimitiveUtil.toByteArray(PrimitiveUtil.Endian.LITTLE, d);
        org.junit.Assert.assertEquals(d, PrimitiveUtil.toDouble(PrimitiveUtil.Endian.LITTLE, little), delta);
    }

}