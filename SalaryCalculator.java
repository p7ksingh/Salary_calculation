package com.java8.dises;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class Employee {
    private String department;
    private double salary;
    private boolean isFullTime;
}

public class SalaryCalculator {
    public static void main(String[] args) {
        Employee e1 = new Employee("IT", 10000, false);
        Employee e2 = new Employee("Commerce", 2000, true);
        Employee e3 = new Employee("Infra", 30000, true);
        Employee e4 = new Employee("Business", 40000, true);

        List<Employee> employees = new LinkedList<>();
        employees.add(e1);
        employees.add(e2);
        employees.add(e3);
        employees.add(e4);

        final double SALARY_THRESHOLD = 100000.0;
        final double DISCOUNT_FACTOR = 0.9; // 10% discount

        double totalSalary = employees.stream()
                // Step 1: Filter to include only full-time employees
                .filter(Employee::isFullTime)
                // Step 2 & 3: Group by department and sum the salaries
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.summingDouble(Employee::getSalary)))
                .entrySet().stream()
                // Step 4: Apply discount if salary exceeds threshold
                .map(entry -> entry.getValue() > SALARY_THRESHOLD ? entry.getValue() * DISCOUNT_FACTOR
                        : entry.getValue())
                // Step 5: Sum the totals
                .reduce(0.0, Double::sum);

        System.out.println("Total Salary Expenditure: " + totalSalary);
    }
}
