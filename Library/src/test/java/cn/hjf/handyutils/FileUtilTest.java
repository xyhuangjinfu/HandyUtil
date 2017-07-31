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

}