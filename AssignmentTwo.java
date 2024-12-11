import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Collections;
import java.util.Comparator;

// 抽象的Person类，作为人员信息的基础抽象类
abstract class Person {
    private String name;
    private int age;
    private String gender;

    public Person() {}

    public Person(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

// Employee类，表示主题公园员工信息，继承自Person类
class Employee extends Person {
    private String jobTitle;
    private int employeeId;

    public Employee() {
        super();
    }

    public Employee(String name, int age, String gender, String jobTitle, int employeeId) {
        super(name, age, gender);
        this.jobTitle = jobTitle;
        this.employeeId = employeeId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
}

// Visitor类，表示主题公园游客信息，继承自Person类
class Visitor extends Person {
    private String ticketType;
    private boolean isFirstVisit;

    public Visitor() {
        super();
    }

    public Visitor(String name, int age, String gender, String ticketType, boolean isFirstVisit) {
        super(name, age, gender);
        this.ticketType = ticketType;
        this.isFirstVisit = isFirstVisit;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {}

    public boolean isFirstVisit() {
        return isFirstVisit;
    }

    public void setFirstVisit(boolean isFirstVisit) {
        this.isFirstVisit = isFirstVisit;
    }
}

// RideInterface接口，定义游乐设施相关操作方法
interface RideInterface {
    void addVisitorToQueue(Visitor visitor);
    void removeVisitorFromQueue(Visitor visitor);
    void printQueue();
    void addVisitorToHistory(Visitor visitor);
    boolean checkVisitorFromHistory(Visitor visitor);
    int numberOfVisitors();
    void printRideHistory();
    void sortVisitors();
    void runOneCycle();
}

// Ride类，表示主题公园游乐设施信息，实现了RideInterface接口
class Ride implements RideInterface {
    private String rideName;
    private int rideCapacity;
    private Employee operator;
    private Queue<Visitor> queue;
    private LinkedList<Visitor> rideHistory;
    private int maxRider;
    private int numOfCycles;

    // 构造方法，接受游乐设施名称、容量和操作员
    public Ride(String rideName, int rideCapacity, Employee operator) {
        this.rideName = rideName;
        this.rideCapacity = rideCapacity;
        this.operator = operator;
        this.queue = new LinkedList<>();
        this.rideHistory = new LinkedList<>();
        this.maxRider = 1;
        this.numOfCycles = 0;
    }

    public Ride(String rideName, int rideCapacity, Employee operator, int maxRider) {
        this.rideName = rideName;
        this.rideCapacity = rideCapacity;
        this.operator = operator;
        this.queue = new LinkedList<>();
        this.rideHistory = new LinkedList<>();
        this.maxRider = maxRider;
        this.numOfCycles = 0;
    }

    @Override
    public void addVisitorToQueue(Visitor visitor) {
        queue.add(visitor);
        System.out.println(visitor.getName() + "已成功添加到队列中。");
    }

    @Override
    public void removeVisitorFromQueue(Visitor visitor) {
        if (queue.remove(visitor)) {
            System.out.println(visitor.getName() + "已成功从队列中移除。");
            addVisitorToHistory(visitor);
        } else {
            System.out.println(visitor.getName() + "未在队列中找到，移除失败。");
        }
    }

    @Override
    public void printQueue() {
        System.out.println("队列中的游客信息如下：");
        Iterator<Visitor> iterator = queue.iterator();
        while (iterator.hasNext()) {
            Visitor visitor = iterator.next();
            System.out.println("姓名：" + visitor.getName() + "，年龄：" + visitor.getAge() +
                    "，性别：" + visitor.getGender() + "，门票类型：" + visitor.getTicketType() +
                    "，是否首次游玩：" + visitor.isFirstVisit());
        }
    }

    @Override
    public void addVisitorToHistory(Visitor visitor) {
        rideHistory.add(visitor);
        System.out.println(visitor.getName() + "已添加到乘坐历史记录中。");
    }

    @Override
    public boolean checkVisitorFromHistory(Visitor visitor) {
        boolean result = rideHistory.contains(visitor);
        if (result) {
            System.out.println(visitor.getName() + "在乘坐历史记录中。");
        } else {
            System.out.println(visitor.getName() + "不在乘坐历史记录中。");
        }
        return result;
    }

    @Override
    public int numberOfVisitors() {
        int count = rideHistory.size();
        System.out.println("乘坐历史记录中的游客数量是：" + count);
        return count;
    }

    @Override
    public void printRideHistory() {
        System.out.println("乘坐历史记录中的游客：");
        Iterator<Visitor> iterator = rideHistory.iterator();
        while (iterator.hasNext()) {
            Visitor visitor = iterator.next();
            System.out.println("姓名：" + visitor.getName() + "，年龄：" + visitor.getAge() +
                    "，性别：" + visitor.getGender() + "，门票类型：" + visitor.getTicketType() +
                    "，是否首次游玩：" + visitor.isFirstVisit());
        }
    }

    @Override
    public void sortVisitors() {
        Collections.sort(rideHistory, new VisitorComparator());
        System.out.println("乘坐历史记录中的游客已完成排序。");
    }

    @Override
    public void runOneCycle() {
        if (operator == null) {
            System.out.println("没有分配游乐设施操作员，无法运行此次游乐设施周期。");
            return;
        }
        if (queue.isEmpty()) {
            System.out.println("队列中没有等待的游客，无法运行此次游乐设施周期。");
            return;
        }

        int numToRemove = Math.min(maxRider, queue.size());
        for (int i = 0; i < numToRemove; i++) {
            Visitor visitor = queue.poll();
            addVisitorToHistory(visitor);
            System.out.println(visitor.getName() + "已从队列移除并添加到乘坐历史记录中，本次游乐设施周期运行中...");
        }

        numOfCycles++;
        System.out.println("本次游乐设施周期已运行完毕，已运行周期次数：" + numOfCycles);
    }

    // 添加的将乘坐历史记录中的游客信息写入文件的方法
    public void exportRideHistory() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ride_history.txt"))) {
            Iterator<Visitor> iterator = rideHistory.iterator();
            while (iterator.hasNext()) {
                Visitor visitor = iterator.next();
                String line = visitor.getName() + "," + visitor.getAge() + "," + visitor.getGender() + "," +
                        visitor.getTicketType() + "," + visitor.isFirstVisit();
                writer.write(line);
                writer.newLine();
            }
            System.out.println("乘坐历史记录已成功导出到文件ride_history.txt。");
        } catch (IOException e) {
            System.err.println("导出乘坐历史记录到文件时出现错误: " + e.getMessage());
        }
    }

    public int getMaxRider() {
        return maxRider;
    }

    public void setMaxRider(int maxRider) {
        this.maxRider = maxRider;
    }

    public int getNumOfCycles() {
        return numOfCycles;
    }

    public String getRideName() {
        return rideName;
    }

    public void setRideName(String rideName) {
        this.rideName = rideName;
    }

    public int getRideCapacity() {
        return rideCapacity;
    }

    public void setRideCapacity(int rideCapacity) {
        this.rideCapacity = rideCapacity;
    }

    public Employee getOperator() {
        return operator;
    }

    public void setOperator(Employee operator) {
        this.operator = operator;
    }
}

// 自定义比较器类，实现Comparator接口，用于定义游客对象的比较规则
class VisitorComparator implements Comparator<Visitor> {
    @Override
    public int compare(Visitor v1, Visitor v2) {
        int ageCompare = Integer.compare(v1.getAge(), v2.getAge());
        if (ageCompare == 0) {
            return Boolean.compare(v1.isFirstVisit(), v2.isFirstVisit());
        }
        return ageCompare;
    }
}

// AssignmentTwo类，作为主程序类，包含main方法以及各部分作业要求对应的方法
public class AssignmentTwo {
    public static void main(String[] args) {
        AssignmentTwo assignmentTwo = new AssignmentTwo();

        assignmentTwo.partThree();
        assignmentTwo.partFourA();
        assignmentTwo.partFourB();
        assignmentTwo.partFive();
        assignmentTwo.partSix();
    }

    public void partThree() {
        Employee operator = new Employee("Operator1", 30, "Male", "Ride Operator", 1001);
        Ride ride = new Ride("过山车", 20, operator);

        Visitor visitor1 = new Visitor("游客1", 25, "Female", "全天票", true);
        Visitor visitor2 = new Visitor("游客2", 35, "Male", "半天票", false);
        Visitor visitor3 = new Visitor("游客3", 18, "Female", "全天票", true);
        Visitor visitor4 = new Visitor("游客4", 40, "Male", "两日票", false);
        Visitor visitor5 = new Visitor("游客5", 22, "Female", "全天票", true);

        ride.addVisitorToQueue(visitor1);
        ride.addVisitorToQueue(visitor2);
        ride.addVisitorToQueue(visitor3);
        ride.addVisitorToQueue(visitor4);
        ride.addVisitorToQueue(visitor5);

        ride.removeVisitorFromQueue(visitor3);

        ride.printQueue();
    }

    public void partFourA() {
        Employee operator = new Employee("Operator1", 30, "Male", "Ride Operator", 1001);
        Ride ride = new Ride("过山车", 20, operator);

        Visitor visitor1 = new Visitor("游客1", 25, "Female", "全天票", true);
        Visitor visitor2 = new Visitor("游客2", 30, "Male", "半天票", false);
        Visitor visitor3 = new Visitor("游客3", 18, "Female", "全天票", true);
        Visitor visitor4 = new Visitor("游客4", 40, "Male", "两日票", false);
        Visitor visitor5 = new Visitor("游客5", 22, "Female", "全天票", true);

        ride.addVisitorToHistory(visitor1);
        ride.addVisitorToHistory(visitor2);
        ride.addVisitorToHistory(visitor3);
        ride.addVisitorToHistory(visitor4);
        ride.addVisitorToHistory(visitor5);

        ride.checkVisitorFromHistory(visitor3);
        ride.numberOfVisitors();
        ride.printRideHistory();
    }

    public void partFourB() {
        Employee operator = new Employee("Operator1", 30, "Male", "Ride Operator", 1001);
        Ride ride = new Ride("过山车", 20, operator);

        Visitor visitor1 = new Visitor("游客1", 25, "Female", "全天票", true);
        Visitor visitor2 = new Visitor("游客2", 35, "Male", "半天票", false);
        Visitor visitor3 = new Visitor("游客3", 18, "Female", "全天票", true);
        Visitor visitor4 = new Visitor("游客4", 40, "Male", "两日票", false);
        Visitor visitor5 = new Visitor("游客5", 22, "Female", "全天票", true);

        ride.addVisitorToHistory(visitor1);
        ride.addVisitorToHistory(visitor2);
        ride.addVisitorToHistory(visitor3);
        ride.addVisitorToHistory(visitor4);
        ride.addVisitorToHistory(visitor5);

        System.out.println("排序前乘坐历史中的游客信息：");
        ride.printRideHistory();

        ride.sortVisitors();

        System.out.println("排序后乘坐历史中的游客信息：");
        ride.printRideHistory();
    }

    public void partFive() {
        Employee operator = new Employee("Operator1", 30, "Male", "Ride Operator", 1001);
        Ride ride = new Ride("过山车", 20, operator, 3);

        Visitor visitor1 = new Visitor("游客1", 25, "Female", "全天票", true);
        Visitor visitor2 = new Visitor("游客2", 35, "Male", "半天票", false);
        Visitor visitor3 = new Visitor("游客3", 18, "Female", "全天票", true);
        Visitor visitor4 = new Visitor("游客4", 40, "Male", "两日票", false);
        Visitor visitor5 = new Visitor("游客5", 22, "Female", "全天票", true);
        Visitor visitor6 = new Visitor("游客6", 28, "Male", "全天票", false);
        Visitor visitor7 = new Visitor("游客7", 32, "Female", "半天票", true);
        Visitor visitor8 = new Visitor("游客8", 45, "Male", "两日票", false);
        Visitor visitor9 = new Visitor("游客9", 19, "Female", "全天票", true);
        Visitor visitor10 = new Visitor("游客10", 24, "Male", "全天票", false);

        for (Visitor v : new Visitor[]{visitor1, visitor2, visitor3, visitor4, visitor5, visitor6, visitor7, visitor8, visitor9, visitor10}) {
            ride.addVisitorToQueue(v);
        }

        System.out.println("运行周期前队列中的游客信息：");
        ride.printQueue();

        ride.runOneCycle();

        System.out.println("运行一次周期后队列中的游客信息：");
        ride.printQueue();

        System.out.println("乘坐历史中的游客信息：");
        ride.printRideHistory();
    }

    public void partSix() {
        Employee operator = new Employee("Operator1", 30, "Male", "Ride Operator", 1001);
        Ride ride = new Ride("新游乐设施", 20, operator);

        Visitor visitor1 = new Visitor("游客11", 28, "Female", "全天票", true);
        Visitor visitor2 = new Visitor("游客12", 32, "Male", "半天票", false);
        Visitor visitor3 = new Visitor("游客13", 20, "Female", "全天票", true);
        Visitor visitor4 = new Visitor("游客14", 40, "Male", "两日票", false);
        Visitor visitor5 = new Visitor("游客15", 22, "Female", "全天票", true);

        ride.addVisitorToHistory(visitor1);
        ride.addVisitorToHistory(visitor2);
        ride.addVisitorToHistory(visitor3);
        ride.addVisitorToHistory(visitor4);
        ride.addVisitorToHistory(visitor5);

        ride.exportRideHistory();
    }

    public void partSeven() {}
}