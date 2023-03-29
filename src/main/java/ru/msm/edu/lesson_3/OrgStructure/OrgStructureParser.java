package ru.msm.edu.lesson_3.OrgStructure;

import java.io.File;
import java.io.IOException;

public interface OrgStructureParser {

    Employee parseStructure(File csvFile) throws IOException;

}
