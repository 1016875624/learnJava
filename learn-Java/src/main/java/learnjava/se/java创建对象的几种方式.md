# java创建对象的几种方式

1. 使用关键字 **new**

```java
Object a=new Object();
```

2. 使用类对象的**New Instance** 方法

```java
String str = String.class.newInstance();
```

3. 使用**clone**方法创建对象

```java
public class CloneExample implements Cloneable 
{ 
    @Override
    protected Object clone() throws CloneNotSupportedException 
    { 
        return super.clone(); 
    } 
    String name = "GeeksForGeeks"; 
  
    public static void main(String[] args) 
    { 
        CloneExample obj1 = new CloneExample(); 
        try
        { 
            CloneExample obj2 = (CloneExample) obj1.clone(); 
            System.out.println(obj2.name); 
        } 
        catch (CloneNotSupportedException e) 
        { 
            e.printStackTrace(); 
        } 
    } 
} 
```

4. 使用**反序列化**的方式，使用反序列化时，jvm不会调用任何的构造函数

```java
// Java program to illustrate creation of Object 
// using Deserialization. 
import java.io.*; 
  
public class DeserializationExample 
{ 
    public static void main(String[] args) 
    { 
        try
        { 
            DeserializationExample d; 
            FileInputStream f = new FileInputStream("file.txt"); 
            ObjectInputStream oos = new ObjectInputStream(f); 
            d = (DeserializationExample)oos.readObject(); 
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        } 
        System.out.println(d.name); 
    } 
} 
```

5. 使用反射的方式获得构造函数，使用构造函数的**newInstance**方法

```java
// Java program to illustrate creation of Object 
// using newInstance() method of Constructor class 
import java.lang.reflect.*; 
  
public class ReflectionExample 
{ 
    private String name; 
    ReflectionExample() 
    { 
    } 
    public void setName(String name) 
    { 
        this.name = name; 
    } 
    public static void main(String[] args) 
    { 
        try
        { 
            Constructor<ReflectionExample> constructor 
                = ReflectionExample.class.getDeclaredConstructor(); 
            ReflectionExample r = constructor.newInstance(); 
            r.setName("GeeksForGeeks"); 
            System.out.println(r.name); 
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        } 
    } 
} 
```

