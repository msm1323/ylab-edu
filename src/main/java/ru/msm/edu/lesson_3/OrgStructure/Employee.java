package ru.msm.edu.lesson_3.OrgStructure;

import java.util.ArrayList;
import java.util.List;

public class Employee {

    private Long id;
    private Long bossId;
    private String name;
    private String position;
    private Employee boss;
    private List<Employee> subordinate = new ArrayList<>();

    Employee() {
    }

    Employee(Long id, Long bossId, String name, String position) {
        this.id = id;
        this.bossId = bossId;
        this.name = name;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBossId() {
        return bossId;
    }

    public void setBossId(Long bossId) {
        this.bossId = bossId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Employee getBoss() {
        return boss;
    }

    public void setBoss(Employee boss) {
        this.boss = boss;
    }

    public List<Employee> getSubordinate() {
        return subordinate;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "\nid=" + id +
                "\nbossId=" + bossId +
                "\nname='" + name + '\'' +
                "\nposition='" + position + '\'' +
                "\nboss" + (boss == null ? ("=" + null) : (".name='" + boss.name + '\'')) +
                "\nsubordinate.size()=" + subordinate.size() +
                "\n}";
    }
}
