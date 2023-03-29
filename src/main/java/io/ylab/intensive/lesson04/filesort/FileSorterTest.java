package io.ylab.intensive.lesson04.filesort;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javax.sql.DataSource;

import io.ylab.intensive.lesson04.DbUtil;
import static io.ylab.intensive.lesson04.filesort.Validator.*;

public class FileSorterTest {

  public static void main(String[] args) throws SQLException, IOException {
    File dataFile = new Generator().generate("data.txt", 1_000_000);  //1_000_000
    System.out.println(isSortedDESC(dataFile)); //false

    DataSource dataSource = initDb();
    File data = new File("data.txt");
    FileSorter fileSorter = new FileSortImpl(dataSource);
    File res = fileSorter.sort(data);

    System.out.println(isSortedDESC(res));   //true
    System.out.println(calculateHashSum(dataFile).equals(calculateHashSum(res))); //true

  }

  /*
   * with batch-processing:
   * 0min 9sec ~BATCH_SIZE = 50
   * 0min 8sec ~BATCH_SIZE = 85 - 100
   * 0min 7sec ~BATCH_SIZE = 150 - 290 !
   * 0min 8sec ~BATCH_SIZE = 300 - 500
   * without: 2min 4sec
   */
  
  public static DataSource initDb() throws SQLException {
    String createSortTable = "" 
                                 + "drop table if exists numbers;" 
                                 + "CREATE TABLE if not exists numbers (\n"
                                 + "\tval bigint\n"
                                 + ");";
    DataSource dataSource = DbUtil.buildDataSource();
    DbUtil.applyDdl(createSortTable, dataSource);
    return dataSource;
  }
}
