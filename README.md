# MyBatisExample_Maven

- MySQL

```
mysql> CREATE DATABASE `MyBatisExampleDB`;

mysql > use MyBatisExampleDB;

mysql> CREATE TABLE `Employee` (
    `id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(20) DEFAULT NULL,
    `role` varchar(20) DEFAULT NULL,
    PRIMARY KEY (`id`)
);
    
```

- pom.xml

```
  <dependencies>
  
    <!-- ADD -->
    <!-- http://mvnrepository.com/artifact/org.mybatis/mybatis -->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.4.0</version>
    </dependency>

    <dependency>
        <groupId>org.apache.ibatis</groupId>
        <artifactId>ibatis-sqlmap</artifactId>
        <version>3.0-beta-10</version>
    </dependency>

    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.23</version>
    </dependency>
    <!-- ADD END -->
        
  </dependencies>


  <build>
    <pluginManagement><!-- lock down plugins versions to avoid using Maven defaults (may be moved to parent pom) -->
      <plugins>
      
       <!-- ADD -->
        <plugin>
      	  <groupId>org.mybatis.generator</groupId>
      	  <artifactId>mybatis-generator-maven-plugin</artifactId>
          <version>1.3.0</version>
        </plugin>
       <!-- ADD END -->

      </plugins>
    </pluginManagement>
    
    <!-- ADD -->
    <plugins>
        <plugin>
            <artifactId>maven-assembly-plugin</artifactId>
            <version>2.2</version>
            <executions>
                <execution>
                    <id>make-assembly</id>
                    <phase>package</phase>
                    <goals>
                        <goal>single</goal>
                    </goals>
                </execution>
            </executions>
            <configuration>
              <!-- 作成するJarの名前を指定する -->
                <finalName>MyBatisExampleDB</finalName>
                <!-- -jar-with-dependenciesのようなサフィックスをつけない -->
                <appendAssemblyId>false</appendAssemblyId>
                <descriptorRefs>
                  <!--  参照しているライブラリを作成するJarに含める -->
                    <descriptorRef>jar-with-dependencies</descriptorRef>
                </descriptorRefs>
                <archive>
                  <!--  manifestファイルが作成され実行するときのMainクラスを指定できる -->
                    <manifest>
                      <mainClass>net.deskplate.mybatis.App</mainClass>
                    </manifest>
                </archive>
            </configuration>
        </plugin>
    </plugins>
    <!-- ADD END -->
    
  </build>
</project>
```

- resources/generatorConfig.xml

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE generatorConfiguration PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN" "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd" >
<generatorConfiguration >
  <!-- mysql connector -->
  <classPathEntry
      location=" PATH TO mysql-connector-java  例） /Users/[USER]/.m2/repository/mysql/mysql-connector-java/8.0.23/mysql-connector-java-8.0.23.jar" />

  <context id="context1" >
  
    <!-- JDBC -->
    <jdbcConnection driverClass="com.mysql.jdbc.Driver"
      connectionURL="jdbc:mysql://localhost:3306/TestDB"
      userId="root" password="">

      <!-- Table Configuration matched more than one table 対策 -->
      <!-- http://www.mybatis.org/generator/usage/mysql.html -->
      <property name="nullCatalogMeansCurrent" value="true"/>
      
    </jdbcConnection>
    
      <!-- 自動生成するエンティティ -->

      <javaModelGenerator
          targetPackage="net.deskplate.mybatis.entity"
          targetProject="src/main/java/"
      />
      <sqlMapGenerator
          targetPackage="net.deskplate.mybatis.entity"
          targetProject="src/main/java/"
      />
      <javaClientGenerator
          targetPackage="net.deskplate.mybatis.entity"
          targetProject="src/main/java/"
          type="XMLMAPPER"
      />

    <!-- 自動生成対象のテーブル名 -->
    <table tableName="Employee" domainObjectName="Employee">
      <generatedKey column="id" sqlStatement="JDBC"/> <!-- auto increament 余計なXMLが入ってしまうため不要かも？ -->
    </table>

  </context>
</generatorConfiguration>

```

- エンティティ生成

```
$ mvn mybatis-generator:generate
```

- エンティティ生成後
- resources/mybatis-config.xml

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

  <!-- JDBC -->
  <environments default="MyBatisExample">
    <environment id="MyBatisExample">
      <transactionManager type="JDBC" />
      <dataSource type="POOLED">
        <property name="driver" value="com.mysql.cj.jdbc.Driver" />
        <property name="url" value="jdbc:mysql://localhost/MyBatisExampleDB" />
        <property name="username" value="root" />
        <property name="password" value="" />
      </dataSource>
    </environment>
  </environments>

  <!-- エンティティのパッケージ -->
  <mappers>
    <package name="net.deskplate.mybatis.entity" />
  </mappers>

</configuration>
```
　
- Mapper.xml　にこういう項目ができてしまうので削除  config設定が悪いため

```
    <selectKey resultType="java.lang.Integer" keyProperty="id" >
      JDBC
    </selectKey>
```
　
- Mapper.xmlをコピー
    - net/deskplate/mybatis/entity フォルダごと resources フォルダにコピーする


