# 解决WordPress要设置ftp的问题

## 问题出现的原因

这个问题是由运行Apache的用户和WordPress文件夹的目录的所有者不同造成的

## 解决方案

1. 修改Apache的配置文件http.conf

```shell
#很多人忘记了Apache的目录在哪里，我们可以使用查找命令查找文件
find / -name "http*"
```

![查找http结果](img\查找http结果.png)

2. 修改httpd.conf的内容

```shell
将里面的User apache 改为 User wordpress 目录的用户
Group apache 改为 Group wordress目录的用户
并且为wordpress的目录权限复制为 755 chmod -r 755 ./*
```

![httpd.conf的内容](img\httpd.conf的内容.png)

这样就可以解决这个问题了。

# 解决wordpress上传限制

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

添加如下代码即可

```ini
upload_max_filesize = 64M

post_max_size = 64M

max_execution_time = 300
```



