package ru.msm.edu.lesson_3.OrgStructure;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OrgStructureParserImpl implements OrgStructureParser {

    private Map<Long, Employee> employees = new HashMap<>();
    private Employee generalBoss;

    @Override
    public Employee parseStructure(File csvFile) throws IOException {
        Scanner scanner = new Scanner(csvFile);
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(";");

            long id = Long.parseLong(line[0]);
            Long bossId = line[1].isEmpty() ? null : Long.parseLong(line[1]);
            String name = line[2];
            String position = line[3];
            Employee curEmployee = new Employee(id, bossId, name, position);

            if (bossId == null) {
                generalBoss = curEmployee;
            }
            employees.put(curEmployee.getId(), curEmployee);
        }
        scanner.close();

        for (Employee curEmployee : employees.values()) {
            Long curBossId = curEmployee.getBossId();
            if (curBossId != null) {
                curEmployee.setBoss(employees.get(curBossId));
                employees.get(curBossId).getSubordinate().add(curEmployee);
            }
        }

        return generalBoss;
    }
}
