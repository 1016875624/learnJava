package learnjava.se;

/**
 * 创建对象的几种方式类
 *
 */
public class CreateObjectWays {
    public void useNew() {
        Object object = new Object();
    }

    public void useNewInstance() throws IllegalAccessException, InstantiationException {
        String str = String.class.newInstance();
    }
}
