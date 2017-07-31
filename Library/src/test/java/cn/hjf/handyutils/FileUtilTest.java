package cn.hjf.handyutils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.Serializable;

/**
 * Created by huangjinfu on 2017/6/28.
 */
public class FileUtilTest {

    static class Person implements Serializable {
        String name;
        int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Person)) {
                return false;
            }
            Person p = (Person) obj;
            return p.age == this.age
                    && p.name == null ? this.name == null : p.name.equals(this.name);
        }
    }

    private static final String testRootPath = new File("").getAbsolutePath() + File.separator + "FileUtilTest" + File.separator;

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
        FileUtil.delete(testRootPath);
    }

    @Test
    public void testExistsAndDelete() {
        String file = testRootPath + "file";
        Assert.assertEquals(FileUtil.delete(file), true);
        Assert.assertEquals(FileUtil.exists(file), false);
        if (FileUtil.save(file, new byte[0])) {
            Assert.assertEquals(FileUtil.exists(file), true);
            Assert.assertEquals(FileUtil.delete(file), true);
        } else {
            Assert.assertEquals(FileUtil.exists(file), false);
        }
    }

    @Test
    public void testObjectAndByteArrayConvert() {
        Person p1 = new Person("hjf", 27);
        byte[] personByte = FileUtil.objectToByteArray(p1);
        Person p2 = (Person) FileUtil.byteArrayToObject(personByte);
        Assert.assertEquals(p1, p2);
    }

    @Test
    public void testSave() {
        //save object
        String file2 = testRootPath + "f2";
        Person hjf = new Person("hjf", 27);
        Assert.assertEquals(FileUtil.save(file2, hjf), true);
        Assert.assertEquals(FileUtil.readObject(file2), hjf);
        //save byte array
        String file1 = testRootPath + "f1";
        byte[] data = "Hello World!".getBytes();
        Assert.assertEquals(FileUtil.save(file1, data), true);
        Assert.assertArrayEquals(data, FileUtil.readBytes(file1));
        //save append
        String file3 = testRootPath + "f3";
        byte[] d1 = "Hello World!".getBytes();
        byte[] d2 = "Hello hjf!".getBytes();
        byte[] d3 = "Hello World!Hello hjf!".getBytes();
        Assert.assertEquals(FileUtil.save(file3, d1, true), true);
        Assert.assertEquals(FileUtil.save(file3, d2, true), true);
        Assert.assertArrayEquals(FileUtil.readBytes(file3), d3);
        Assert.assertEquals(FileUtil.save(file3, d1, false), true);
        Assert.assertArrayEquals(FileUtil.readBytes(file3), d1);
    }

    @Test
    public void testCopy() {
        String src = testRootPath + "src";
        String dest = testRootPath + "dest";
        byte[] srcData = "Hello World!".getBytes();
        byte[] destData = "Hello hjf!".getBytes();
        //cover mode, dest exist
        Assert.assertEquals(FileUtil.save(src, srcData), true);
        Assert.assertEquals(FileUtil.save(dest, destData), true);
        Assert.assertEquals(FileUtil.copy(src, dest, true), true);
        Assert.assertArrayEquals(FileUtil.readBytes(dest), srcData);
        //cover mode, dest not exist
        Assert.assertEquals(FileUtil.delete(dest), true);
        Assert.assertEquals(FileUtil.copy(src, dest, true), true);
        Assert.assertArrayEquals(FileUtil.readBytes(dest), srcData);
        //not cover mode, dest exist
        Assert.assertEquals(FileUtil.save(dest, destData), true);
        Assert.assertEquals(FileUtil.copy(src, dest, false), true);
        Assert.assertArrayEquals(FileUtil.readBytes(dest), destData);
        //not cover mode, dest not exist
        Assert.assertEquals(FileUtil.delete(dest), true);
        Assert.assertEquals(FileUtil.copy(src, dest, false), true);
        Assert.assertArrayEquals(FileUtil.readBytes(dest), srcData);
    }

}