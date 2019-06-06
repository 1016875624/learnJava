package learnjava.java8.feature;

import learnjava.common.entity.User;
import lombok.Data;

import java.util.*;
import java.util.function.DoubleFunction;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * java8新特性集合类
 * @author 10168
 */
@Data
public class CollectionFeature {
    private ArrayList<User> users = new ArrayList<User>() {{
        add(new User("用户1",0, 18, "广东"));
        add(new User("用户2", 0,19, "北京"));
        add(new User("用户3", 1,35, "广东"));
        add(new User("用户4", 1,16, "北京"));
        add(new User("用户5", 0, 22,"上海"));
        add(new User("用户6", 1, 13,"广东"));
    }};

    /**
     * 创建集合的几种方式
     */
    public void createListMethod() {
        ArrayList<String> strList = new ArrayList<String>() {{
            add("A");
            add("B");
            add("C");
        }};
        List<User> users = asList(new User("用户1", 0,18, "广东"),new User("用户2",0,20,"北京"));
        List<String> places = asList("guangdong", "beijing", "shanghai");
    }

    /**
     * list的排序特性
     */
    public void sortFeature() {
        //用年龄进行排序
        users.sort(comparing(User::getAge));
        //使用 lambda 表达式进行排序
        users.sort((u1,u2)->u1.getAge()-u2.getAge());
    }

    /**
     * 排序的运行
     */
    public void sortFeatureRun() {
        CollectionFeature collectionFeature = new CollectionFeature();
        System.out.println(collectionFeature.users);
        collectionFeature.sortFeature();
        System.out.println(collectionFeature.users);
    }

    /**
     * 对list进行分类
     * 这里将对地区进分类
     */
    public void groupByFeature() {
        Map<String, List<User>> collect = users.stream().collect(groupingBy(User::getCity));
        System.out.println(collect);
    }

    public void filterFeature() {
        //filter(users, (User u) -> u.getSex() == 0);
        List<User> collect = users.stream().filter(u -> u.getSex() == 0).collect(toList());
    }

    /**
     * 映射特性 map
     */
    public void mapFeature() {
        //将用户的名字列表提取出来
        List<String> names = users.stream().map(User::getName).collect(toList());
    }

    /**
     * 流的截断 limit
     */
    public void limitFeature() {
        //截断数量为3个
        List<String> names = users.stream().map(User::getName).limit(3).collect(toList());
    }

    public void streamFeature() {
        String[] arrayOfWords = {" Goodbye", "World"};
        Stream< String > streamOfwords = Arrays.stream( arrayOfWords);
//        words.stream() .map( word -> word.split("")).map( Arrays:: stream).distinct() .collect( toList());
        List<Stream<String>> collect = streamOfwords.map(word -> word.split("")).map(Arrays::stream).distinct().collect(toList());
        Stream<Stream<String>> stream = streamOfwords.map(word -> word.split("")).map(Arrays::stream);
        List<String> stringList = streamOfwords.map(word -> word.split("")).flatMap(Arrays::stream).distinct().collect(toList());
        Stream<String> stringStream = streamOfwords.map(word -> word.split("")).flatMap(Arrays::stream);

        users.stream().anyMatch(u ->u.getAge()>30);
        Optional<User> first = users.stream().filter(u -> u.getAge() > 10).findFirst();
        Optional<User> any = users.stream().filter(u -> u.getAge() > 10).findAny();
        Stream<User> limit = users.stream().filter(u -> u.getAge() > 10).limit(5);
        Stream<User> skip = users.stream().filter(u -> u.getAge() > 10).limit(5).skip(3);
    }

    /**
     * 数学的函数思想
     */
    public void functionThinking() {
        System.out.println(integrate(x->x*x+3,1.0,2.0));
    }

    /**
     * @param f fx函数
     * @param a 下限
     * @param b 上限
     * @return
     */
    public double integrate(DoubleFunction< Double > f, double a, double b) { return (f.apply( a) + f.apply( b)) * (b-a) / 2.0; }


    public static void main(String[] args) {
        CollectionFeature collectionFeature = new CollectionFeature();
        collectionFeature.functionThinking();
        //collectionFeature.groupByFeature();


    }
}
