package cn.hjf.handyutils;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by huangjinfu on 2017/7/13.
 */
public class PrimitiveUtilTest {
    @Test
    public void toDouble() throws Exception {
        byte[] bytes = new byte[]{(byte)0x40, (byte) 0xb9, (byte)0x7c, (byte)0x78, (byte)0xf5, (byte)0xc2, (byte)0x8f, (byte)0x5c};
        Assert.assertEquals(PrimitiveUtil.toDouble(PrimitiveUtil.Endian.BIG, bytes), 6524.4725);
    }

}