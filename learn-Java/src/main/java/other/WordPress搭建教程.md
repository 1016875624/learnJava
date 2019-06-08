[TOC]



# WordPress搭建教程

## 加速yum的下载速度

### centos7版本

```shell
#安装wget
yum install -y wget
#安装yum工具
yum install -y yum-utils
# 删除所有的源
yum clean all
#下载阿里云的源
wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
#生成缓存
yum makecache
```



## Centos 下安装mariadb 教程

```shell
yum install mariadb mariadb-server
systemctl start mariadb   #启动mariadb
systemctl enable mariadb  #设置开机自启动
mysql_secure_installation #设置root密码等相关
mysql -uroot -p           #测试登录
```

## centos7 安装mysql教程

```shell
#下载mysql 源
wget https://dev.mysql.com/get/mysql80-community-release-el7-3.noarch.rpm
#阿里的源
wget https://mirrors.aliyun.com/centos/6.10/sclo/x86_64/rh/rh-mysql57/rh-mysql57-mysql-server-5.7.24-2.el6.x86_64.rpm
#安装源
rpm -Uvh mysql80-community-release-el7-3.noarch.rpm
#切换数据库源
yum-config-manager --disable mysql80-community
#切换成mysql57版本
yum-config-manager --enable mysql57-community

#如果遇到yum-config-manager 命令未找到 就安装 yum-utils
yum install -y yum-utils

#查看现在启用的mysql版本
yum repolist enabled | grep mysql
#安装mysql
yum install -y mysql-community-server
#启动mysql
systemctl start mysqld.service
service mysqld start
#设置mysql开机自启动
systemctl enable mysqld.service
#查找mysql的临时密码
grep 'temporary password' /var/log/mysqld.log

#减少密码安全等级
#最新的mysql的密码要求有字符串大小写有数字，有符号
set global validate_password_policy=0;
#设置密码最短长度为4
set global validate_password_length=4;
#修改root密码
grant all privileges on *.* to root@'%' identified by "root" ;
update user set password = password('root') where user = root;
ALTER USER 'root'@'%' IDENTIFIED BY 'root';
SET PASSWORD = PASSWORD('root');
```

### 解决安装mysql的时候下载速度慢的方法

1. 首先，安装mysql的源

2. 然后再下载阿里的yum源

3. 更新源

如下代码

```shell
#清除自带的yum源
yum clean all
#下载mysql的源
wget https://dev.mysql.com/get/mysql80-community-release-el7-3.noarch.rpm
#下载阿里的yum源
wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
#建立源数据
yum makecache
#愉快的进行安装mysql吧，你会发现速度很快，之前是20k/s ，现在是1-2M/s
```



## 安装php

查看是否已经安装php

```shell
yum list installed | grep php
```

### 安装yum源

```shell
yum install epel-release
rpm -ivh http://rpms.famillecollet.com/enterprise/remi-release-7.rpm
```

使用yum list命令查看可安装的包(Packege)

```shell
yum list --enablerepo=remi --enablerepo=remi-php56 | grep php
```

安装PHP5.6.x

yum源配置好了，下一步就安装PHP5.6。

```
yum install --enablerepo=remi --enablerepo=remi-php56 php php-mysqlnd
```

## 安装httpd

```shell
yum install -y httpd
```

## 下载WordPress

```shell
#下载WordPress
wget https://cn.wordpress.org/wordpress-5.0.3-zh_CN.tar.gz
#解压到Apache的运行路径
tar -zxvf wordpress-5.0.3-zh_CN.tar.gz -C /var/www/html/
#添加用户Apache
useradd Apache
vi /etc/httpd/conf/httpd.conf
#更改所属用户
chown -R Apache: /var/www/html/wordpress
chown -R apache: /var/www/html/wordpress
#赋予权限
chmod -R 777 /var/www/html/wordpress
```

## 关闭防火墙

```shell
#查看防火墙状态
firewall-cmd --state
#停止firewall
systemctl stop firewalld.service
#禁止firewall开机启动
systemctl disable firewalld.service 
```

## 让WordPress支持用MarkDown写文章

安装插件`WP Githuber MD`就可以进行用MarkDown写文章，非常好用

## WordPress 代码高亮

在我们使用markdown写的文章的时候，默认是没有代码高亮的，其他的插件还要使用别人专门的代码编辑器进行编辑代码，才能代码高亮，这样就无法支持MarkDown的文件直接输出了，这样不符合我们的预期，因此这里推荐一个插件，**`WP Code Highlight.js`**这个插件非常的好，直接能识别代码块，然后进行高亮。在设置页面支持选择高亮的样式

还有一个插件也可以**`Prismatic`**



## wordpress上传文件时出现“抱歉，出于安全的考虑，不支持此文件类型。”

修改在WordPress的文件夹下面的`wp-config.php`

添加

```php
define('ALLOW_UNFILTERED_UPLOADS', true);
```

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

