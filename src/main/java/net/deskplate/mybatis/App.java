package net.deskplate.mybatis;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import net.deskplate.mybatis.entity.*;

public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        // resources直下のmybatis-config.xmlをロード
        try (Reader r = Resources.getResourceAsReader("mybatis-config.xml");) {

            // 読み込んだ設定ファイルからSqlSessionFactoryを生成
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(r);

            // SQLセッションを取得
            try (SqlSession session = factory.openSession()) {

                // Employeeテーブルの独自Mapperを取得
                EmployeeMapper map = session.getMapper(EmployeeMapper.class);

                // Employee データ追加サンプル
                Employee employee = new Employee();
                employee.setName("Test");
                employee.setRole("CEO");
                Integer count = map.insert(employee);
                System.out.println(count.toString());
                session.commit();
                session.close();
                
            }
        } catch (IOException e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
}
