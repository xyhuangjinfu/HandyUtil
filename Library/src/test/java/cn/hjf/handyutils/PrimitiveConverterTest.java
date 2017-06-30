package cn.hjf.handyutils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by huangjinfu on 2017/6/30.
 */
public class PrimitiveConverterTest {
    @Test
    public void toBytes() throws Exception {
        short s = 0b00010001_11101110;
        byte[] b = PrimitiveConverter.toBytes(s, PrimitiveConverter.Endian.BIG);
        Assert.assertEquals(b[0] & 0x000000ff, (s & 0x0000ff00) >>> 8);
        Assert.assertEquals(b[1] & 0x000000ff, s & 0x000000ff);
        byte[] l = PrimitiveConverter.toBytes(s, PrimitiveConverter.Endian.LITTLE);
        Assert.assertEquals(l[1] & 0x000000ff, (s & 0x0000ff00) >>> 8);
        Assert.assertEquals(l[0] & 0x000000ff, s & 0x000000ff);
    }

}