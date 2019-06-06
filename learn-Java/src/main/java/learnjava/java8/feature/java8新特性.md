[TOC]
# java8的新特性

## java初始化ArrayList的方式

### **方法一**：利用静态辅助函数进行添加对象

java8在写代码的方面上补充了很多的新特性，添加大量的静态辅助函数add就是其中的一种

如下代码所示，在创建对象的时候进行传递匿名对象即可。

```java
ArrayList<String> strList = new ArrayList<String>() {{
        add("A");
        add("B");
        add("C");
    }};
```

### 方法二：利用Arrays.asList(对象一，对象二)创建list

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;
    private int sex;
    private int age;
    private String city;
}
import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;
//使用静态方法的时候，要导入静态方法 如上面的导入方法
List<User> users = asList(new User("用户1", 0,18, "广东"),new User("用户2",0,20,"北京"));
List<String> places = asList("guangdong", "beijing", "shanghai");
//如果没有上面的导入的话，那么只能用Arrays.asList()方法
```



## 利用集合的特性简化集合的操作

>流(stream)只能遍历一次，使用过一次之后就没有了。与迭代器相似。

### 流的中间操作，终端操作

中间操作返回的是流，终端操作返回的是结果，终端操作完后，流会被释放。

中间操作如下图

![中间操作](img\中间操作.png)

终端操作如下图

![终端操作](img\终端操作.png)

操作大全

![stream操作大全](img\stream操作大全.png)



### 简化集合排序

```java
private ArrayList<User> users = new ArrayList<User>() {{
        add(new User("用户1",0, 18, "广东"));
        add(new User("用户2", 0,19, "北京"));
        add(new User("用户3", 1,35, "广东"));
        add(new User("用户4", 1,16, "北京"));
        add(new User("用户5", 0, 22,"上海"));
        add(new User("用户6", 1, 13,"广东"));
    }};
//用年龄进行排序
//记得要导入静态方法
//import static java.util.Comparator.comparing;
//用年龄进行排序
users.sort(comparing(User::getAge));
//使用 lambda 表达式进行排序
users.sort((u1,u2)->u1.getAge()-u2.getAge());
users.stream().sorted(comparing(User:getAge()))
```

###  将list列表的对象进行分类，也就是转换为map(groupingBy)

```java
//导入静态方法
import static java.util.stream.Collectors.groupingBy;
//对地区进行分类
Map<String, List<User>> collect = users.stream().collect(groupingBy(User::getCity));
System.out.println(collect);
```

运行结果如下：

```
{上海=[User(name=用户5, sex=0, age=22, city=上海)], 广东=[User(name=用户1, sex=0, age=18, city=广东), User(name=用户3, sex=1, age=35, city=广东), User(name=用户6, sex=1, age=13, city=广东)], 北京=[User(name=用户2, sex=0, age=19, city=北京), User(name=用户4, sex=1, age=16, city=北京)]}
```

### 过滤集合的元素

可以使用lambda 表达式进行过滤元素如下代码所示

```java
//inventory是代过滤的list，这里的过滤就是将绿色的保留下来，其他颜色的不保留
//将性别为1的元素给过滤掉，保留性别为0的
List<User> collect = users.stream().filter(u -> u.getSex() == 0).collect(toList());
```

### 将集合的元素映射(map),也就是提取某个字段，将其转换为list

```java
//将用户的名字列表提取出来
List<String> names = users.stream().map(User::getName).collect(toList());
```

### 流stream的截断(limit)

流的截断就是将元素的数量控制在某个范围内

```java
/**
* 流的截断 limit
*/
public void limitFeature() {
    //截断数量为3个
    List<String> names = users.stream().map(User::getName).limit(3).collect(toList());
}
```

### 流的匹配 anyMatch ,allMatch,nonMatch

anyMatch 只要有一个元素符合，则返回真

allMatch 全部元素都符合才返回真

nonMatch 全部元素不符合才返回真

它们接收的参数都是一个 谓词 Predicate

```java
users.stream().anyMatch(u ->u.getAge()>30);
```

## 流的查找 findFirst,findAny

这两个都不具有查找的功能，它们不接受参数，通常与filter一起使用，它们的区别就是顺序，findFirst是按顺序取出第一个，findAny则是随机给一个

```java
Optional<User> first = users.stream().filter(u -> u.getAge() > 10).findFirst();
Optional<User> any = users.stream().filter(u -> u.getAge() > 10).findAny();
```

### 流的切片和跳过，limit ,skip

它们接收的都是long整数，limit是指返回前多少个，skip是指跳过多少个，这两个配合可以实现分页查询。

```java
//获得前5个
Stream<User> limit = users.stream().filter(u -> u.getAge() > 10).limit(5);
//跳过3个后获得5个
Stream<User> skip = users.stream().filter(u -> u.getAge() > 10).skip(3).limit(5);
```

## 归约reduce

归约操作常用于在数据求和，数据累积上，它接受两个参数，第一个是初始值，一般为0，从0开始累加

第二个为BinaryOperator<T> 用lambda表达式为 (a,b)->a+b

```java
int product = numbers.stream(). reduce( 1, (a, b) -> a * b);
```



### forEach操作集合

```java

```



## 概念合集

### 函数式接口

**函数式接口**指的是只定义一个抽象方法的接口。例如**Comparator**和**Runnable**接口，它们都只有一个抽象方法。

```java
public interface Comparator < T > {int compare( T o1, T o2);}
public interface Runnable{void run();}
public interface Callable < V >{V call();}
public interface Consumer < T >{ void accept( T t); }
public interface Function < T, R >{ R apply( T t); }

```

### 比较器复合

如何逆序的排序呢？

```java
//先进行排序，然后再进行逆序 reversed()操作
inventory.sort( comparing( Apple:: getWeight) .reversed()；
//那么如何设置第二排序条件呢？
//当重量一样的时候，按照国家进行排序
inventory.sort( comparing( Apple:: getWeight) .reversed().thenComparing( Apple:: getCountry));
```

### 谓词复合

谓 词 接 口 包 括 三 个 方 法： `negate` 、`and` 和 `or` ，让 你 可 以 重 用 已 有 的 Predicate 来 创 建 更 复 杂 的 谓 词。 比 如， 你 可 以 使 用 negate 方 法 来 返 回 一 个 Predicate 的 非， 比 如 苹 果 不 是 红 的：

```java
//产 生 现 有 Predicate 对 象 redApple 的 非
Predicate < Apple > notRedApple = redApple.negate(); 
```

你 可 能 想 要 把 两 个 Lambda 用 and 方 法 组 合 起 来， 比 如 一 个 苹 果 既 是 红 色 又 比 较 重：

```java
Predicate < Apple > redAndHeavyApple = 
    redApple.and( a -> a.getWeight() > 150);
```

你 可 以 进 一 步 组 合 谓 词， 表 达 要 么 是 重（ 150 克 以 上） 的 红 苹 果， 要 么 是 绿 苹 果：

```java
Predicate < Apple > redAndHeavyAppleOrGreen = redApple.and( a -> a.getWeight() > 150) .or( a -> "green". equals( a.getColor()));
```

### 函数复合

你 还 可 以 把 Function 接 口 所 代 表 的 Lambda 表 达 式 复 合 起 来。 

Function 接 口 为 此 配 了 **andThen** 和 **compose** 两 个 默 认 方 法， 它 们 都 会 返 回 Function 的 一 个 实 例。

比 如 在 上 一 个 例 子 里 用 **compose** 的 话， 它 将 意 味 着 f( g( x)) ，而 **andThen** 则 意 味 着 g( f( x)) ：

```java
Function < Integer, Integer > f = x -> x + 1; 
Function < Integer, Integer > g = x -> x * 2; 
Function < Integer, Integer > h = f.compose( g); 
Function < Integer, Integer > m = f.andThen( g); 
//m(x)=g(f(x));
//h(x)=f(g(x));
//数 学 上 会 写 作 f( g( x)) 或( f o g)( x) int result = h.apply( 1); ← ─ 这 将 返 回 3
```



### 常用的函数式接口



![函数式接口大全](img/函数式接口大全.png)

使用函数式接口的例子

![使用函数式接口的例子](img\使用函数式接口的例子.png)

## 疑难杂症

这里主要是一些不常用的东西，但是万一有时候要用了，便于快速查找

```java
String[] arrayOfWords = {" Goodbye", "World"};
Stream< String > streamOfwords = Arrays.stream( arrayOfWords);
//这里主要的操作是将字符串的流分离出来 第一个map操作，将返回 stream<string[]> 
//然后进行调用map  arrays的stream()方法，得到的是Stream<Stream<String>>
//如果将map替换为flatMap 得到的就是Stream<String>
Stream<Stream<String>> stream = streamOfwords.map(word -> word.split("")).map(Arrays::stream);
//如果将map替换为flatMap 得到的就是Stream<String> 
//flatMap的操作就是 将Stream<String> 变为 String
Stream<String> stringStream = streamOfwords.map(word -> word.split("")).flatMap(Arrays::stream);
```

> 总之就是，当有用到stream方法的时候，可以用flatMap进行扁平化