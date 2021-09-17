<div style="width: 100%; text-align: center; font-size: 2rem; font-weight: 900"><font>代码人生ReadMe</font></div>



> 文档说明：该文档，将作为rollin个人经验技术成长文档，同时也作为个人项目配置指引文档
>
> 创建时间：2021-01-07 14:42:17
>
> 集成音乐后台 2021-01-11 13:51:10

### 1.框架说明

> 1.maven-client -->  公共依赖配置pox( 添加 jar包 依赖) <br><br>
&nbsp;&nbsp;&nbsp;&nbsp; _org.springframework.cloud - **spring-cloud-dependencies**_ <br>
&nbsp;&nbsp;&nbsp;&nbsp; _org.springframework.boot - spring-boot-starter-web_ <br>
&nbsp;&nbsp;&nbsp;&nbsp; _org.springframework.boot - spring-boot-starter-test_ <br>
&nbsp;&nbsp;&nbsp;&nbsp; _org.projectlombok - lombok_ <br>
&nbsp;&nbsp;&nbsp;&nbsp; _com.alibaba - fastjson_ <br>
> 
> 2.maven-web --> web依赖（例如：）
> 
> 3.framework-core --> 公共类 



### 2.插件说明

#### 1.plantuml (画图软件)

&nbsp;&nbsp;&nbsp;&nbsp;指导文件：http://plantuml.com/zh/guide 

#### 2.Lombok 

1.@Data = getter/setter + toString() + equals() + hashCode() 
2.@AllArgsConstructor(access = AccessLevel.PRIVATE)、@NoArgsConstructor
3.@Slf4j (打日志时尽量别用字符串拼接，不够直观且效率低，应该使用{}占位符，用逗号隔开) 

```js
log.info("请求数据：\r\n{}", FormatUtil.formatJson(sessionStream));
```

### 3.证书相关

#### 1.加密策略（RSA）
1.随机生成AES密钥key -- aesKey
2.AES加密明文 -- AES (message) = byte[]
3.RSA公钥加密aesKey -- RSA (aesKey) = byte[]
4.拼接：RSA(aesKey) + AES(message) = byte[] 

#### 2.生成证书命令

```js
keytool -genkey -alias RECORD_ACCOUNT -keyalg RSA -keystore record_account.jks -keysize 2048 -sigalg sha256withrsa -validity 36500 -storepass ra123456 -keypass ra123456 -dname "CN=RECORD_ACCOUNT,OU=LifeCode,O=LifeCode,L=GuangDong,ST=ShenZhen,C=ZH"
```

### 4 问题集锦

#### 1.请求时间问题

​	未指定时区时，默认指定时区非中国时区，导致插入数据时间不对，解决方案，在配置mysql url 地方添加：serverTimezone=Asia/Shanghai

```yaml
datasource:
	driver-class-name: com.mysql.cj.jdbc.Driver #mysql驱动
	url: jdbc:mysql://localhost:3306/record_account?zeroDateTimeBehavior=convertToNull&tinyInt1isBit=false&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai
	username: root
	password: root123456
```


#### 2.提交Git代码时报错
##### 报错内容：

```js
fatal: unable to access 'https://github.com/luolin2611/life-code/': LibreSSL SSL_connect: SSL_ERROR_SYSCALL in connection to github.com:443
```
##### 解决方案：

1. 找到代理的端口号：
   
   ![img_1](folder/image/img_1.png)
   
2. 执行命令：

   ```
   git config --global --add remote.origin.proxy "127.0.0.1:7890"
   ```

#### 3.本地打包正常，服务器乱码
>说明：注意你的服务器x编码方式，linux系统的服务器编码默认是utf-8，对于是windows的服务器默认不是utf-8。所以在启动的时候需要设置编码方式。<br/>
>输入命令：$ java -Dfile.encoding=utf-8 -jar xxx.jar
#### 4.idea 有时提示找不到类或者符号的解决
>解决方案：清除缓存即可
> ![img_2.png](folder/image/img_2.png)



### 5.技术杂货间

#### 1.生成banner
> 地址： https://www.bootschool.net/ascii
> 选项:  3d-ascii
#### 2.打包相关
> mvn clean package -P sit1
#### 3.soar举例
```java
  if (dayRecordAccountObjectList.size() <= 0) {
    return list;
  }

  Collection.isEmpty() should be used to test for emptiness
  
  if (dayRecordAccountObjectList.isEmpty()) {
    return list;
  }
```
#### 4.jdk 1.8 新增 Java Lambda 表达式 （List 可以使用 stream()、filter 对内容进行操作）
```
  Double sum = obj.getDayRecordAccountObjects().stream().filter(dayRecordAccountObjectFilter -> "0".equals(dayRecordAccountObjectFilter.getClassifyType())).collect(Collectors.summingDouble(DayRecordAccountObject::getBillMoney));
```
#### 5.遍历list之后将list中对象根据某个元素重新组合
```
  参考BillServiceImpl.class --> processDayRecordAccountList()
```
#### 6.遍历List时，使用 i < list.size() 这样会增加每次每次都去计算list.size() 解决方案如下。
```
for (int i = 0, size = dayRecordAccountObjectList.size(); i < size; i++) {

}
```

#### 7.使用StringUtils 以及 CollectionUtils

##### 1.分别导入maven依赖

```xml
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
    <version>3.12.0</version>
</dependency>

<dependency>
    <groupId>commons-collections</groupId>
    <artifactId>commons-collections</artifactId>
    <version>3.2.2</version>
</dependency>
```

##### 2.常用语法

###### 1.StringUtils

```java
// StringUtils.isBlank(null)      = true
// StringUtils.isBlank("")        = true
// StringUtils.isBlank(" ")       = false
// StringUtils.isBlank("bob")     = false
// StringUtils.isBlank("  bob  ") = false
StringUtils.isEmpty(final CharSequence cs);

// StringUtils.isBlank(null)      = true
// StringUtils.isBlank("")        = true
// StringUtils.isBlank(" ")       = true
// StringUtils.isBlank("bob")     = false
// StringUtils.isBlank("  bob  ") = false
StringUtils.isBlank(final CharSequence cs);

// 查看多个字符串中是否有空串 ==> 直接检查 （“”， null）
StringUtils.isNoneEmpty(final CharSequence... css);

// 查看多个字符串中是否有空串 ==> 只检查 （“”， “  ”， null）
StringUtils.isNoneBlank(final CharSequence... css);
```

###### 2.CollectionUtils

```
CollectionUtils.isEmpty();
CollectionUtils.isNotEmpty();
```

#### 8.使用StringBuilder 的技巧

利用构建者方法去使用

```java
String userDetail = new StringBuilder()
                .append("rollin")
                .append(26)
                .append("只要自己不尴尬，尴尬的是别人。")
                .toString();
```

### 6.SQL 技术沉淀

#### 1.使用分组group by (有‘每’字就可以考虑分组)

##### 1.1 例如：查询某年每月的数据

```sql
SELECT SUM(bill_money) , MONTH(update_time) FROM record_account WHERE classify_type = '0' AND YEAR(update_time) = '2021' GROUP BY MONTH(update_time) ORDER BY MONTH(update_time) DESC
```

##### 1.2 sql 执行书序：温(when)哥(group by)华(having)白(order by)领(limit)



### 7.开发工具的使用

#### 1.Idea 快捷键

以下文章是根据Idea使用Elipse(macOS)的模式配置。

##### 1.1 自动生成变量名快捷键

​	在IDEA界面依次点击File- ->Settings- ->Keymap,在输入框中输入introduce variable

![image-20210720074548464](folder/image/image-20210720074548464.png)



### 8.Mac操作习惯

#### 1.触摸板常见设置

![image-20210720075210481](folder/image/image-20210720075210481.png)

![image-20210720075357150](folder/image/image-20210720075357150.png)

![image-20210720075716244](folder/image/image-20210720075716244.png)



### 类、接口和类图

#### 1.类
类（Class）是指具有相同属性、方法和关系的对象的抽象，它封装了数据和行为，是面向对象程序设计（OOP）的基础，具有封装性、继承性和多态性等三大特性。在 UML 中，类使用包含类名、属性和操作且带有分隔线的矩形来表示。

##### (1) 类名（Name）是一个字符串，例如，Student。

##### (2) 属性（Attribute）是指类的特性，即类的成员变量。UML 按以下格式表示：

```
[可见性]属性名:类型[=默认值]
```

例如：-name:String

注意：“可见性”表示该属性对类外的元素是否可见，包括公有（Public）、私有（Private）、受保护

（Protected）和朋友（Friendly）4 种，在类图中分别用符号+、-、#、~表示。

##### (3) 操作（Operations）是类的任意一个实例对象都可以使用的行为，是类的成员方法。UML 按以下格式表示：

```
[可见性]名称(参数列表)[:返回类型]
```

例如：+display():void。 

![image-20210905152331001](folder/image/image-20210905152331001.png)

<div style="width: 100%; text-align: center; font-size: 1rem">学生类的UML 类图<font></font></div>

#### 2.接口

接口（Interface）是一种特殊的类，它具有类的结构但不可被实例化，只可以被子类实现。它包含抽象操作，但不包含属性。它描述了类或组件对外可见的动作。在 UML 中，接口使用一个带有名称的

小圆圈来进行表示。

![image-20210905154215104](folder/image/image-20210905154215104.png)

#### 3.类图

**类图（ClassDiagram）是用来显示系统中的类、接口、协作以及它们之间的静态结构和关系的一种静态模型**。它主要用于描述软件系统的结构化设计，帮助人们简化对软件系统的理解，它是系统分

析与设计阶段的重要产物，也是系统编码与测试的重要模型依据。

类图中的类可以通过某种编程 语言直接实现。类图在软件系统开发的整个生命周期都是有效的，它是面向对象系统的建模中最常见的图。

如下图所示是“计算长方形和圆形的周长与面积”的类图，图形接口有计算面积和周长的抽象方法，长方形和圆形实现这两个方法供访问类调用。

![image-20210905155848707](folder/image/image-20210905155848707.png)

<div style="width: 100%; text-align: center; font-size: 1rem">“计算长方形和圆形的周长与面积”的类图<font></font></div>

### 类之间的关系

在软件系统中，类不是孤立存在的，类与类之间存在各种关系。

根据类与类之间的耦合度从弱到强排列，UML 中的类图有以下几种关系：依赖关系、关联关系、聚合关系、组合关系、泛化关系和实现关系。其中泛化和实现的耦合度相等，它们是最强的。

#### 1.依赖关系

依赖（Dependency）关系是一种使用关系，它是对象之间耦合度最弱的一种关联方式，是临时性的关联。在代码中，某个类的方法通过局部变量、方法的参数或者对静态方法的调用来访问另一个类（被依赖类）中的某些方法来完成一些职责。

在 UML 类图中，依赖关系使用带箭头的虚线来表示，箭头从使用类指向被依赖的类。如图所示是人与手机的关系图，人通过手机的语音传送方法打电话。

![image-20210905201441155](folder/image/image-20210905201441155.png)

#### 2.关联关系

关联（Association）关系是对象之间的一种引用关系，用于表示一类对象与另一类对象之间的联系，如老师和学生、师傅和徒弟、丈夫和妻子等。**关联关系是类与类之间最常用的一种关系，分为一般关联关系、聚合关系和组合关系。**我们先介绍一般关联。

**关联可以是双向的，也可以是单向的。在 UML 类图中，双向的关联可以用带两个箭头或者没有箭头的实线来表示，单向的关联用带一个箭头的实线来表示，箭头从使用类指向被关联的类。也可以在关联线的两端标注角色名，代表两种不同的角色。**

**在代码中通常将一个类的对象作为另一个类的成员变量来实现关联关系。**

如图所示是老师和学生的关系图，每个老师可以教多个学生，每个学生也可向多个老师学，他们是双向关联。

![image-20210905202205427](folder/image/image-20210905202205427.png)

#### 3.聚合关系

**聚合（ Aggregation ）关系是关联关系的一种，是强关联关系，是整体和部分之间的关系，是 has-a 的关系。**

聚合关系也是通过成员对象来实现的，其中成员对象是整体对象的一部分，但是成员对象可以脱离整体

对象而独立存在。例如，学校与老师的关系，学校包含老师，但如果学校停办了，老师依然存在。

**在 UML 类图中，聚合关系可以用带空心菱形的实线来表示，菱形指向整体。**

如图所示是大学和教师的关系图。

![image-20210905202631326](folder/image/image-20210905202631326.png)

#### 4.组合关系

**组合（Composition）关系也是关联关系的一种，也表示类之间的整体与部分的关系，但它是一种更强烈的聚合关系，是contains-a关系。**

在组合关系中，整体对象可以控制部分对象的生命周期，一旦整体对象不存在，部分对象也将不存在，

部分对象不能脱离整体对象而存在。例如，头和嘴的关系，没有了头，嘴也就不存在了。

在 UML 类图中，组合关系用带实心菱形的实线来表示，菱形指向整体。如图所示是头和嘴的关系图。

![image-20210905202952854](folder/image/image-20210905202952854.png)

#### 5.泛化关系

**泛化（Generalization）关系是对象之间耦合度最大的一种关系，表示一般与特殊的关系，是父类与子类之间的关系，是一种继承关系，是** **is-a** **的关系。**

**在 UML 类图中，泛化关系用带空心三角箭头的实线来表示，箭头从子类指向父类。**在代码实现时，使用面向对象的继承机制来实现泛化关系。例如，Student 类和 Teacher 类都是 

Person 类的子类，其类图如图所示。

![image-20210905203528955](folder/image/image-20210905203528955.png)

#### 6.实现关系

**实现（Realization）关系是接口与实现类之间的关系。在这种关系中，类实现了接口，类中的操作实现了接口中所声明的所有的抽象操作。**

**在** **UML** **类图中，实现关系使用带空心三角箭头的虚线来表示，箭头从实现类指向接口。**例如，汽车和船实现了交通工具，其类图如图所示。

![image-20210905204017206](folder/image/image-20210905204017206.png)

### 面向对象设计原则

#### 概述

##### 参考链接

> https://www.bilibili.com/video/BV1E7411A7mk?from=search&seid=1244811038631909112&spm_id_from=333.337.0.0

##### 软件的可维护性和可复用性

知名软件大师Robert C.Martin认为一个可维护性(Maintainability)较低的软件设计，通常由于如下4个原因造成:

* 过于僵化(Rigidity):设计难以修改
* 过于脆弱(Fragility): 设计易遭到破坏

* 牢固性(Immobility):复用率低

* 黏度过高(Viscosity):难以做正确事情

##### 软件的可维护性和可复用性

软件工程和建模大师PeterCoad认为，一个好的系统设计应该具备如下三个性质:

* 可扩展性(Extensibility)
* 灵活性(Flexibility)
* 可插入性(Pluggability)

##### 软件的可维护性和可复用性

* <font style="color: red;">软件的复用</font>(Reuse)或重用拥有众多优点，如可以提高软件的开发效率，提高软件质量，节约开发成本，<font style="color: red;">恰当的复用还可以改善系统的可维护性</font>。
* 面向对象设计复用的目标在于<font style="color: red;">实现支持可维护性的复用。</font>
* 在面向对象的设计里面，<font style="color: red;">可维护性复用都是以面向对象设计原则为基础的</font>，这些设计原则首先都是复用的原则，遵循这些设计原则可以有效地提高系统的复用性，同时提高系统的可维护性。
* 面向对象设计原则和设计模式也是对系统进行合理重构的指南针，<font style="color: red;">重构(Refactoring)是在不改变软件现有功能的基础上，通过调整程序代码改善软件的质量、性能使其程序的设计模式和架构更趋合理，提高软件的扩展性和维护性。</font>

##### 面向对象设计原则简介

常用的面向对象设计原则包括7个,这些原则并不是孤立存在的,它们相互依赖,相互补充。

|                    设计原则名称                     |                         设计原则简介                         | 重要性 |
| :-------------------------------------------------: | :----------------------------------------------------------: | :----- |
| 单一职责原则 (Single Responsibility Principle, SRP) |         类的职责要单一,不能将太多的职责放在一个类中          | ★★★★☆  |
|        开闭原则 (Open-Closed Principle, OCP)        | 软件实体对扩展是开放的,但对修改是关闭的,即在不修改一 个软件实体的基础上去扩展其功能 | ★★★★★  |
|  里氏替换原则 (Liskov Substitution Principle, LSP)  | 在软件系统中,一个可以接受基类对象的地方必然可以接受一个子类对象 | ★★★★☆  |
|  依赖倒置原则 (Depedecy Inversion Principle, DIP)   |            要针对抽象层编程,而不要针对具体类编程             | ★★★★★  |
| 接口隔高原则 (Interface Segregation Principle, ISP) |            使用多个专门的接口来取代一个统一的接口            | ★★☆☆☆  |
|     合成复用原则 (Comotite euse Principle,CRP)      | 在系统中应该尽量多使用组合和聚合关联关系,尽量少使用甚 至不使用继承关系 | ★★★★☆  |
|          迪米特法则 (Law of Demeter, LoD)           | 一个软件实体对其他实体的引用越少越好,或者说如果两个类 不必彼此直接速信,那么这两个类就不应当发生直接的相互作 用,而是通过引入一个第三者发生间接交互 | ★★★☆☆  |

#### 单一职责原则

##### 单一职责原则定义

* 单一职责原则(Single Responsibility Principle,SRP)定义如下:
  一个对象应该只包含单一的职责，并且该职责被完整地封装在一个类中。
* 其英文定义为:
  Every object should have a single responsibility, and that responsibility should be entirely encapsulated by the class.
* 另一种定义方式如下:
  就一个类而言，应该<font style="color: red;">仅有一个引起它变化的原因。</font>
* 其英文定义为:
  There should never be more than one reason for a class to change.

##### 单一职责原则分析

* <font style="color: red;">一个类(或者大到模块，小到方法)承担的职责越多，它被复用的可能性越小</font>，而且如果一个类承担的职责过多，就相当于将这些职责耦合在一起，当其中一个职责变化时，可能会影响其他职责的运作。
* 类的职责主要包括两个方面:<font style="color: red;">数据职责和行为职责，数据职责通过其属性来体现，而行为职责通过其方法来体现。</font>
* 单一职责原则是实现<font style="color: red;">高内聚、低耦合</font>的指导方针，在很多代码重松手法中都能找到它的存在，它是最简单但又最难运用的原则，需要设计人员发现类的不同职责并将其分离，而发现类的多重职责需要设计人员具有较强的分析设计能力和相关重构经验。

##### 单一职责实例

###### 实例说明

* 某个基于Java的C/S系统的“登录功能”，通过如下登录类（Login）实现:

  ![image-20210907075112400](folder/image/image-20210907075112400.png)

* 现使用单一职责原则对其重构

![image-20210908064518312](folder/image/image-20210908064518312.png)

#### 开闭原则
##### 开闭原则定义

开闭原则(Open-Closed Principle,OCP)定义如下:

* 一个软件实体应当<font style="color: red;">对扩展开放，对修改关闭</font>。也就是说在设计一个模块的时候，应当使这个模块可以在不被修改的前提下被扩展，即实现在不修改源代码的情况下改变这个模块的行为。

其英文定义为:

* Software entities should be open for extension, but closed for modification.

##### 开闭原则分析

* 开闭原则由Bertrand Meyer于1988年提出，它是面向对象设计中最重要的原则之一。
* 在开闭原则的定义中，<font style="color: red;">软件实体可以指一个软件模块、一个由多个类组成的局部结构或一个独立的类</font>。
* <font style="color: red;">抽象化</font>是开闭原则的关键。
* 开闭原则还可以通过一个更加具体的“<font style="color: red;">对可变性封装原则</font>”来描述，对可变性封装原则(Principle of Encapsulation of Variation,EVP)要求找到系统的可变因素并将其封装起来。

##### 开闭原则实例
###### 实例一

* 某图形界面系统提供了各种不同形状的按钮，客户端代码可针对这些按钮进行编程，用户可能会改变需求要求使用不同的按钮，原始设计方案如图所示:

![image-20210908071103127](/Volumes/FILE_WORK/work/Code/SpringBoot/life-code/folder/image/image-20210908071103127.png)

* 现对该系统进行重构，使之满足开闭原则的要求（如图将可变的内容，放在配置xml文件中）。

![image-20210909074259495](folder/image/image-20210909074259495.png)

###### 实例二

![image-20210911222020349](folder/image/image-20210911222020349.png)

* 存在问题

  1.Client和Server都是具体类;如果Client对象想使用其他

  2.Server对象，则需要将Cient类中所有使用Server类的地方都进行修改。

* 改造后

![image-20210911222806727](/Volumes/FILE_WORK/work/Code/SpringBoot/life-code/folder/image/image-20210911222806727.png)

说明：<font style="color: red;">多基于接口编程，接口是稳定的，实体类是不稳定的。</font>

​	1.Client类使用 ClientInterface抽象类;

​	2.Client类的对象却使用 Server类的派生类的对象:

​	3.如果 Client对象想使用一个不同的服务器类,则只需要从ClientInterface类派生个新的类。无需对 Cient类做任何改动

#### 里氏替换原则

##### 里氏替换原则定义

里氏替换原则(Liskov Substitution PrincipleLSP)有两种定义方式，

* 第一种定义方式相对严格，其定义如下:
  如果对每一个类型为S的对象o1，都有类型为T的对象o2，使得以T定义的所有程序P在所有的对象o1都替换成o2时，<font style="color: red;">程序P的行为没有变化</font>，那么类型S是类型T的子类型。

  其英文定义为:
  If for each object o1 of type s there is an object o2 of type Tsuch that for all programs P defined in terms of T, the behavior of Pis unchanged when o1 is substituted for o2 then S is a subtype of T.

* 第二种更容易理解的定义方式如下:
  <font style="color: red;">所有引用基类(父类)的地方必须能透明地使用其子类的对象。</font>

  其英文定义为:
  Functions that use pointers or references to base classes must be able to use objects of derived classes without knowing it.

##### 里氏替换原则分析

* 里氏替换原则可以通俗表述为:在软件中<font style="color: #ff5223;">如果能够使用基类对象，那么一定能够使用其子类对象。</font>把基类都替换成它的子类，<font style="color: red;">程序将不会产生任何错误和异常</font>，反过来则不成立，如果一个软件实体使

  用的是一个子类的话那么它不一定能够使用基类。

* 里氏替换原则是实现开闭原则的重要方式之一，由于使用基类对象的地方都可以使用子类对象，因此<font style="color: red;">在程序中尽量使用基类类型来对对象进行定义，而在运行时再确定其子类类型，用子类对象来替换父类对象</font>。

##### 里氏替换原则实例

###### 实例一

* 某系统需要实现对重要数据(如用户密码)的加密处理，在数据操作类(DataOperator)中需要调用加密类中定义的加密算法，系统提供了两个不同的加密类，CipherA和CipherB，它们实现不同的加密方法，在DataOperator中可以选择其中的一个实现加密操作。如图所示:

![image-20210912115742367](/Volumes/FILE_WORK/work/Code/SpringBoot/life-code/folder/image/image-20210912115742367.png)


* 1) 如果需要更换一个加密算法类或者增加并使用一个新的加密算法类，如将CipherA改为CipherB，则需要修改客户类Client和数据操作类DataOperator的源代码，违背了开闭原则。
  2) 现使用里氏替换原则对其进行重构，使得系统可以灵活扩展，符合开闭原则。

![image-20210912162700831](folder/image/image-20210912162700831.png)

如何代码里面有大量的if - else 或者 case 语句的时候，可以考虑使用里氏替换原则了。

###### 实例二

![image-20210912163737544](folder/image/image-20210912163737544.png)

如果要增加一个不需要工资的VolunteerEmployee，CalcPay() 如何计算？

```java
// 方式一，意义不大
public VolunteerEmployee extends Employee {
		public void calcPay() {
				return 0;
		}
}

// 方式二、
public VolunteerEmployee extends Employee {
		public void calcPay() {
				throw new UnpaybleEmployeeException();
		}
}
```

* 每次调用calcPay方法将会抛出一个UnpayableEmployeeException异常，因此，要么异常必须被捕获，要么被调用者说明，
  因此，在一个派生类上的约束已经影响到了基类用户。

```java
for(int i=0; i<employees.size(); i++) {
		Employee e = employees.get(i);
		totalPay += e.calcPay();
}
```

为了使用合法，我们将这个调用放到try/catch中 (此方式不妥)

```java
for(int i=0; i<employees.size(); i++) {
		Employee e = employees.get(i);
		try {
				totalPay += e.calcPay();
		} catch(UnpaybleEmployeeException e) {
				
		}
}
```

或者使用另一种方式，看起来简洁，其实也不妥（此种方式更糟糕， 因为直接暴露了子类，而里氏替换原则重要的一点是，子类替换基类，<font style="color: red;">程序的行为不发生变化</font>，而此处需要单独处理已经变化）

```java
for(int i=0; i<employees.size(); i++) {
		Employee e = employees.get(i);
		if(!(e intanceof VolunteerEmployee)) {
				totalPay += e.calcPay();
		}
}
```

* 上述方式更糟糕!因为原来基于Employee基类的代码，现在必须明确要引用它的一个子类。
* 根源就在于违背了LSP，因为VolunteerEmployee不能替代 Employee类
* 只要当你调用一个派生类上的方法时造成了非法使用，你就在违反了LSP原则:如果你使用了一个退化的派生类的方法(什么也没实现或者像是上面的return 0和异常)，你也正在违反LSP原则。此时，说明该方法对派生类是无意义的。
* IS-A(继承)是关于行为的!

综上的处，此处的VolunteerEmployee继承是没有意义的。

###### 实例三

![image-20210912174751919](folder/image/image-20210912174751919.png)

​	如果程序遵循里氏替换原则，继承就能成为降低复杂度的一个强大工具，因为它能让程序员关注于对象的一般特性而不必担心细节。如果程序员必须要不
新地思考不同派生类的实现在语义上的差异，继承就只会增加复杂度了。

#### 依赖倒置原则

##### 依赖倒置原则定义

* 依赖倒置原则(Dependence Inversion PrincipleDIP)的定义如下:

  1.高层模块不应该依赖低层模块，它们都应该依赖抽象。

  2.抽象<font style="color: red;">不应该依赖于细节</font>，细节应该<font style="color: red;">依赖于抽象</font>。

* 其英文定义为:
  High level modules should not depend upon low level modules, both should depend upon abstractions. Abstractions should not depend upon details, details should depend upon abstractions.
  
* 另一种表述为:
  要<font style="color: red;">针对接口编程，不要针对实现编程</font>。
  
* 其英文定义为:
  Program to an interface, not an implementation.

![image-20210914075455766](folder/image/image-20210914075455766.png)

* 结构良好的的面向对象架构都具有清晰的层次定义， 每个层次通过一个定义良好的、受控的接口向外提供一组内聚的服务。
* 高层Policy Layer->低层Mechanism Layer->低层Utility Layer，<font style="color: red;">看起来似乎正确。</font>
* Policy Layer对于依赖的2个layer的<font style="color: red;">改动都是敏感的</font>。

##### 依赖置原则分析

* 简单来说，依赖倒置原则就是指: <font style="color: red;">代码要依赖于抽象的类，而不要依赖于具体的类;要针对接口或抽象类编程而不是针对具体类编程。</font>

> 更好的描述是:**不要依赖那些容易变化的具体类**。如果你要继承一个类，从一个抽象类继承吧。如果你要持有一个类的引用，从一个抽象的类引用吧。如果你要调用一个函数，从一个抽象的函数调用吧。

* 实现开闭原则的关键是抽象化，并且从抽象化导出具体化实现，如果说<font style="color: red;">开闭原则是面向对象设计的目标的话那么依赖倒置原则就是面向对象设计的主要手段。</font>

* 依赖倒置原则的常用实现方式之一是<font style="color: red;">在代码中使用抽象类，而将具体类放在配置文件中。</font>

  1.<font style="color: red;">“将抽象放进代码，将细节放进元数据”</font>
  2.Put Abstractions in Code, Details in Metadata

* 类之间的耦合

  1.<font style="color: red;">零耦合</font>关系

  2.<font style="color: red;">具体耦合</font>关系

  3.<font style="color: red;">抽象耦合</font>关系

* 依赖倒置原则要求客户端依赖于抽象耦合，<font style="color: red;">以抽象方式耦合是依赖倒置原则的关键。</font>

![image-20210916070420564](/Volumes/FILE_WORK/work/Code/SpringBoot/life-code/folder/image/image-20210916070420564.png)

图中看出，高层不要依赖底层，底层也不要依赖高层，他们依赖抽象。 

* 依赖注入	

  1.构造注入(ConstructorInjection):通过<font style="color: red;">构造函数</font>注入实例变量。
  
  2.设值注入(SetterInjection):通过<font style="color: red;">Setter方法</font>注入实例变量。
  
  3.接口注入(InterfaceInjection):通过<font style="color: red;">接口方法</font>注入实例变量。

* 实例说明
  1.某系统提供一个数据转换模块，可以将来自不同数据源的数据转换成多种格式，如可以转换来自数据库的数据(DatabaseSource)、也可以转换来自文本文件的数据(TextSource)，转换后的格式可以是 XML文件(XMLTransformer)、也可以是XLS文件(XLSTransformer)等。

![image-20210916072903973](folder/image/image-20210916072903973.png)

​	2.由于需求的变化，该系统可能需要<font style="color: red;">增加新的数据源或者新的文件格式</font>，每增加一个新的类型的数据源或者新的类型的文件格式，客户类MainClass都需要<font style="color: red;">修改源代码</font>以便使用新的类，但违背了开闭原则。现使用依赖倒置原则对其进行重构。

![image-20210916074614246](folder/image/image-20210916074614246.png)

抽象类（稳定的）提现了隔离的作用，把C和S进行隔离。  （C(MainClass) 依赖抽象 A(Abstract)， S(DataSource) 依赖抽象的 A）

#### 接口隔离原则

##### 接口隔志原别定义

* 接口隔离原则(Interface Segregation Principle,ISP)的定义如下:
  客户端<font style="color: red;">不应该依赖</font>那那些<font style="color: red;">它不需要的接口</font>。
* 其英文定义为:
  Clients should not be forced to depend upon interfaces that they do not usa.
* 注意，在该定义中韵接口指的是所定义的方法。
* 另一种定文方球如下:
  一旦一个接口太大，则需要将它分划成一在重细小的接口，使用该接口的客户端仅需知道一之相关的方法即可。
* 其英文定义为:
  Once an interface has gotten too 'fat' it neads to be split
  into smmaller and more specffic interfaces so that any clients of the interface will ony know about the methods that pertain to thom.

##### 接口隔离原则定义

* 接口隔离原则(Interface Segregation Principle,ISP)的定义如下:

  客户端<font style="color: red;">不应该依赖</font>那些它<font style="color: red;">不需要的接口</font>。

* 其英文定义为:
  Clients should not be forced to depend upon interfaces that
  they do not use.
  
* 注意，在该定义中的接口指的是所定义的方法。
  
* 另一种定义方法如下:
  一旦一个<font style="color: red;">接口太大</font>，则需要将它<font style="color: red;">分割成一些更细小的接口</font>，使用该接口的客户端仅需知道与之相关的方法即可。
  
* 其英文定义为:
   Once an interface has gotten too 'fat' it needs to be split
  into smaller and more specific interfaces so that any clients of the interface will only know about the methods that pertain to them.

* 接口隔离原则是指使<font style="color: red;">用多个专门的接口，而不使用单一的总接口</font>。每一个接口应该承担一种相对独立的角色，不多不少，不干不该干的事，该干的事都要干。
(1)一个接口就<font style="color: red;">只代表一个角色</font>，每个角色都有它特定的一个接口，此时这个原则可以叫做“角色隔离原则”。
(2)接口<font style="color: red;">仅仅提供客户端需要的行为</font>，即所需的方法，客户端不需要的行为则隐藏起来，应当为客户端提供尽可能小的单独的接口，而不要提供大的总接口。













### 23种设计模式

 







### 常用样式模板

#### 1.居中显示文字

<div style="width: 100%; text-align: center; font-size: 1rem">我是居中显示的文字<font></font></div>

#### 2.红色文字

<font style="color: red;">color</font>

```
<font style="color: red;"></font>
```

#### 3.评级

<svg t="1630848209711" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2547" width="20" height="20"><path d="M957.297 404.414a14.64 14.64 0 0 0-13.928-10.117H623.967L525.262 90.524a14.643 14.643 0 0 0-13.929-10.117 14.643 14.643 0 0 0-13.927 10.117l-98.698 303.773H79.305a14.645 14.645 0 0 0-13.929 10.117 14.648 14.648 0 0 0 5.32 16.373l258.399 187.736-98.698 303.767a14.647 14.647 0 0 0 13.927 19.168c3.017 0 6.043-0.93 8.61-2.796l258.398-187.743L769.74 928.662a14.633 14.633 0 0 0 17.216 0 14.644 14.644 0 0 0 5.32-16.372L693.57 608.523l258.407-187.736a14.649 14.649 0 0 0 5.32-16.373z" p-id="2548"></path></svg><svg t="1630848209711" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2547" width="20" height="20"><path d="M957.297 404.414a14.64 14.64 0 0 0-13.928-10.117H623.967L525.262 90.524a14.643 14.643 0 0 0-13.929-10.117 14.643 14.643 0 0 0-13.927 10.117l-98.698 303.773H79.305a14.645 14.645 0 0 0-13.929 10.117 14.648 14.648 0 0 0 5.32 16.373l258.399 187.736-98.698 303.767a14.647 14.647 0 0 0 13.927 19.168c3.017 0 6.043-0.93 8.61-2.796l258.398-187.743L769.74 928.662a14.633 14.633 0 0 0 17.216 0 14.644 14.644 0 0 0 5.32-16.372L693.57 608.523l258.407-187.736a14.649 14.649 0 0 0 5.32-16.373z" p-id="2548"></path></svg><svg t="1630848209711" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2547" width="20" height="20"><path d="M957.297 404.414a14.64 14.64 0 0 0-13.928-10.117H623.967L525.262 90.524a14.643 14.643 0 0 0-13.929-10.117 14.643 14.643 0 0 0-13.927 10.117l-98.698 303.773H79.305a14.645 14.645 0 0 0-13.929 10.117 14.648 14.648 0 0 0 5.32 16.373l258.399 187.736-98.698 303.767a14.647 14.647 0 0 0 13.927 19.168c3.017 0 6.043-0.93 8.61-2.796l258.398-187.743L769.74 928.662a14.633 14.633 0 0 0 17.216 0 14.644 14.644 0 0 0 5.32-16.372L693.57 608.523l258.407-187.736a14.649 14.649 0 0 0 5.32-16.373z" p-id="2548"></path></svg><svg t="1630848209711" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2547" width="20" height="20"><path d="M957.297 404.414a14.64 14.64 0 0 0-13.928-10.117H623.967L525.262 90.524a14.643 14.643 0 0 0-13.929-10.117 14.643 14.643 0 0 0-13.927 10.117l-98.698 303.773H79.305a14.645 14.645 0 0 0-13.929 10.117 14.648 14.648 0 0 0 5.32 16.373l258.399 187.736-98.698 303.767a14.647 14.647 0 0 0 13.927 19.168c3.017 0 6.043-0.93 8.61-2.796l258.398-187.743L769.74 928.662a14.633 14.633 0 0 0 17.216 0 14.644 14.644 0 0 0 5.32-16.372L693.57 608.523l258.407-187.736a14.649 14.649 0 0 0 5.32-16.373z" p-id="2548"></path></svg><svg t="1630848417784" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2698" width="20" height="20"><path d="M512 764.608l-316.416 233.728 120.832-379.328L0 384h391.104L512 6.144z" fill="#2c2c2c" p-id="2699"></path></svg>

