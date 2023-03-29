package ru.msm.edu.lesson_3.OrgStructure;

import java.io.File;
import java.io.IOException;

public class OrgStructureParserTest {

    public static void main(String[] args) {
        OrgStructureParserImpl orgStructureParser = new OrgStructureParserImpl();
        File orgStructureData = new File("src/main/java/ru/msm/edu/lesson_3/OrgStructure/example.csv");
        try {
            System.out.println(orgStructureParser.parseStructure(orgStructureData));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

}
