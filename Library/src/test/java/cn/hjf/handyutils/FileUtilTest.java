package cn.hjf.handyutils;

import junit.framework.Assert;

import org.junit.Test;

import java.io.Serializable;

/**
 * Created by huangjinfu on 2017/6/28.
 */
public class FileUtilTest {

    static class Person implements Serializable{
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

    @Test
    public void save() throws Exception {
        byte[] data = new byte[]{1, 2, 3, 0, 3, 4, 6, 7, 8, 34, 65, 21};
        String path = "D:\\testdata\\d1";
        boolean save = FileUtil.save(path, data);
        Assert.assertEquals(save, true);
    }

    @Test
    public void save1() throws Exception {
        Person person = new Person("tom", 24);
        String path = "D:\\testdata\\d2";
        boolean save = FileUtil.save(path, person);
        Assert.assertEquals(save, true);
    }

    @Test
    public void readBytes() throws Exception {

    }

    @Test
    public void readObject() throws Exception {
        Person person = new Person("tom", 24);
        String path = "D:\\testdata\\d2";
        Object o = FileUtil.readObject(path);
        Assert.assertEquals(person.equals(o), true);
    }

    @Test
    public void objectToByteArray() throws Exception {

    }

    @Test
    public void byteArrayToObject() throws Exception {

    }

    @Test
    public void copy() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void exists() throws Exception {

    }

    @Test
    public void saveAppend() throws Exception {
//         FileUtil.save("E:\\tt\\2", new byte[] {10 , 11, 12});
//         FileUtil.save("E:\\tt\\2", new byte[] {10 , 11, 12}, true);
    }

}