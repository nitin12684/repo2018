package com.jaba.eight.examples;

import java.util.Arrays;
import java.util.List;

public class Employee {

    String id;
    String name;
    int salary;
    String dept;

    public Employee(String id, String name, int salary, String dept) {
        super();
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.dept = dept;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", salary=" + salary + ", dept=" + dept + "]";
    }

    public static final List<Employee> createListOfEmployee() {
        Employee e1 = new Employee("1", "Ramesh", 9000, "A");
        Employee e2 = new Employee("2", "Suresh", 3000, "A");
        Employee e3 = new Employee("3", "Rajesh", 1000, "B");
        Employee e4 = new Employee("4", "Mukesh", 5000, "A");
        Employee e5 = new Employee("5", "Naresh", 8000, "B");
        return Arrays.asList(e1, e2, e3, e4, e5);
    }

    public Employee getEmployee() {
        return this;
    }

}
