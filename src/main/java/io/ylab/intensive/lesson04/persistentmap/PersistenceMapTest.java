package io.ylab.intensive.lesson04.persistentmap;

import java.sql.SQLException;
import javax.sql.DataSource;

import io.ylab.intensive.lesson04.DbUtil;

public class PersistenceMapTest {

  public static void main(String[] args) throws SQLException{
    DataSource dataSource = initDb();
    PersistentMap persistentMap = new PersistentMapImpl(dataSource);
    persistentMap.init("map_1");    //work with map_1_________________
    persistentMap.put("key1", "11");
    persistentMap.put("key2", "222");
    persistentMap.put("key3", "3333");
    persistentMap.put("key2", "222222222");

    persistentMap.init("map_2");    //work with map_2_________________
    persistentMap.put("key1", "v");
    persistentMap.put("key2", "va");
    persistentMap.put("key3", "val");
    persistentMap.put("key4", "valu");
    System.out.println(persistentMap.containsKey("key1"));  //true
    persistentMap.remove("key1");
    persistentMap.remove("key9");
    System.out.println(persistentMap.containsKey("key1"));  //false

    persistentMap.init("map_1");  //work with map_1_________________
    persistentMap.put("key4", "444");

    PersistentMap persistentMap2 = new PersistentMapImpl(dataSource);
    persistentMap2.init("map_1");  //work with map_1_________________
    System.out.println(persistentMap2.get("key2"));  //222222222
    System.out.println(persistentMap2.getKeys());  // ~ [key1, key3, key2, key4]
    persistentMap.clear();
    System.out.println(persistentMap2.getKeys());  //[]

    persistentMap2.init("map_2");    //work with map_2_________________
    System.out.println(persistentMap2.getKeys());  // ~ [key2, key3, key4]

  }
  
  public static DataSource initDb() throws SQLException {
    String createMapTable = "" 
                                + "drop table if exists persistent_map; " 
                                + "CREATE TABLE if not exists persistent_map (\n"
                                + "   map_name varchar,\n"
                                + "   KEY varchar,\n"
                                + "   value varchar\n"
                                + ");";
    DataSource dataSource = DbUtil.buildDataSource();
    DbUtil.applyDdl(createMapTable, dataSource);
    return dataSource;
  }
}
