package cn.hjf.handyutils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by huangjinfu on 2017/6/30.
 */
public class PrimitiveConverterTest {
    @Test
    public void toLong() throws Exception {
        byte[] bytes = new byte[]{0b00001111, 0b01010101, 0b00110011, 0b01110111, 0b01100110};
        junit.framework.Assert.assertEquals(
                0b00001111_01010101_00110011_01110111_01100110L,
                PrimitiveUtil.toLong(PrimitiveUtil.Endian.BIG, bytes));
        junit.framework.Assert.assertEquals(
                0b01100110_01110111_00110011_01010101_00001111L,
                PrimitiveUtil.toLong(PrimitiveUtil.Endian.LITTLE, bytes));
    }

    @Test
    public void toBytes() throws Exception {
        short s = 0b00010001_11101110;
        byte[] b = PrimitiveUtil.toBytes(PrimitiveUtil.Endian.BIG, s);
        Assert.assertEquals(b[0] & 0x000000ff, 0b00010001);
        Assert.assertEquals(b[1] & 0x000000ff, 0b11101110);
        byte[] l = PrimitiveUtil.toBytes(PrimitiveUtil.Endian.LITTLE, s);
        Assert.assertEquals(l[0] & 0x000000ff, 0b11101110);
        Assert.assertEquals(l[1] & 0x000000ff, 0b00010001);
    }

    @Test
    public void toInt() throws Exception {
        byte[] bytes = new byte[]{0b00001111, 0b01010101, 0b00110011,};
        junit.framework.Assert.assertEquals(
                0b00001111_01010101_00110011,
                PrimitiveUtil.toInt(PrimitiveUtil.Endian.BIG, bytes));
        junit.framework.Assert.assertEquals(
                0b00110011_01010101_00001111,
                PrimitiveUtil.toInt(PrimitiveUtil.Endian.LITTLE, bytes));

    }

}