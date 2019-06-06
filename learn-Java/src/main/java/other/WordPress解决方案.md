[TOC]

# WordPress解决方案大全

## 解决WordPress要设置ftp的问题

### 问题出现的原因

这个问题是由运行Apache的用户和WordPress文件夹的目录的所有者不同造成的

### 解决方案

1. 修改Apache的配置文件http.conf

```shell
#很多人忘记了Apache的目录在哪里，我们可以使用查找命令查找文件
find / -name "http*"
```

![查找http结果](img/%E6%9F%A5%E6%89%BEhttp%E7%BB%93%E6%9E%9C.png)

2. 修改httpd.conf的内容

```shell
将里面的User apache 改为 User wordpress 目录的用户
Group apache 改为 Group wordress目录的用户
并且为wordpress的目录权限复制为 755 chmod -r 755 ./*
```

![httpd.conf的内容](img/httpd.conf%E7%9A%84%E5%86%85%E5%AE%B9.png)



这样就可以解决这个问题了。

## 解决wordpress上传限制

WordPress上传限制默认为2M,这个特别小，不满足日常使用需求，因此我们需要更改上传的大小

更改方法如下:

**方法一**:修改functions.php的内容

（functions.php在wp-includes文件夹）

 添加一下内容即可将上传的限制修改为64M

```php
@ini_set( 'upload_max_size' , '64M' );

@ini_set( 'post_max_size', '64M');

@ini_set( 'max_execution_time', '300' );
```



**方法二**：修改php.ini的内容

查找`php.ini`所在位置

```shell
find / -name "php.ini"
```

在`php.ini`添加如下代码即可

```ini
upload_max_filesize = 64M

post_max_size = 64M

max_execution_time = 300
```



## wordpress开启代码高亮的方法

安装一个插件**WP Code Highlight.js**就可以自动进行代码高亮，并且在设置里面可以进行更改代码高亮主题,

如下图

![代码高亮插件](img\代码高亮插件.png)

可以点击设置按钮，进入设置页面，在里面可以修改**代码高亮**的颜色主题

![代码高亮设置颜色主题](img\代码高亮设置颜色主题.png)

## wordpress迁移方案

假如我们的文章已经写了很多了，但是服务器快要过期了，又不想要续费，那么此时就要进行迁移WordPress，重新搭建WordPress是一件比较简单的事情，但是将数据复制过去就有点麻烦了，不过也是可以解决的。解决方案如下。

方法如下：

1. 从旧的Wordpress导出xml文件。
2. 在新的空间安装好Wordpress，然后导入xml。
3. 导入xml。
4. 从源站的content/uploads中down下来所有的图片，上传到新的站点。
5. 布置新的站点，更换主题，做googlefont替换成国内的等等。



## WordPress解决文章访问404的问题

我们更新了固定的永久链接的时候，**中文标题**的文章会出现404的问题，我们可以简单的解决这个问题

解决方法如下：

1. 进入管理界面/wordpress/wp-admin/
2. 点击左侧的设置->固定链接

![固定链接设置](img\固定链接设置.png)



3. 然后修改为朴素就可以了



![固定链接修改](img\固定链接修改.png)

