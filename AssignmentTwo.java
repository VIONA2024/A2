import java.util.*;

// 抽象的Person类，作为人员信息的基础抽象类
abstract class Person {
    private String name;
    private int age;
    private String gender;

    public Person() {
    }

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

    public void setTicketType(String ticketType) {
    }

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
    void sortVisitors(); // 新增方法，用于对乘坐历史中的游客进行排序
}

// Ride类，表示主题公园游乐设施信息，实现了RideInterface接口
class Ride implements RideInterface {
    private String rideName;
    private int rideCapacity;
    private Employee operator;
    private Queue<Visitor> queue;
    private LinkedList<Visitor> rideHistory; // 用于存储乘坐过游乐设施游客的列表

    public Ride() {
        this.queue = new LinkedList<>();
        this.rideHistory = new LinkedList<>();
    }

    public Ride(String rideName, int rideCapacity, Employee operator) {
        this.rideName = rideName;
        this.rideCapacity = rideCapacity;
        this.operator = operator;
        this.queue = new LinkedList<>();
        this.rideHistory = new LinkedList<>();
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
            addVisitorToHistory(visitor); // 移除队列时添加到乘坐历史
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

    // 实现添加游客到乘坐历史的方法
    @Override
    public void addVisitorToHistory(Visitor visitor) {
        rideHistory.add(visitor);
        System.out.println(visitor.getName() + "已添加到乘坐历史记录中。");
    }

    // 实现检查游客是否在乘坐历史中的方法
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

    // 实现获取乘坐历史中游客数量的方法
    @Override
    public int numberOfVisitors() {
        int count = rideHistory.size();
        System.out.println("乘坐历史记录中的游客数量是：" + count);
        return count;
    }

    // 实现打印乘坐历史中游客信息的方法，按要求使用Iterator
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

    // 新增方法，用于对乘坐历史中的游客集合进行排序
    @Override
    public void sortVisitors() {
        Collections.sort(rideHistory, new VisitorComparator());
        System.out.println("乘坐历史记录中的游客已完成排序。");
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
        // 先按照年龄比较，如果年龄相同，再按照是否首次游玩比较
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
        Visitor visitor2 = new Visitor("游客2", 35, "Male", "半天票", false);
        Visitor visitor3 = new Visitor("游客3", 18, "Female", "全天票", true);
        Visitor visitor4 = new Visitor("游客4", 40, "Male", "两日票", false);
        Visitor visitor5 = new Visitor("游客5", 22, "Female", "全天票", true);

        // 添加游客到乘坐历史
        ride.addVisitorToHistory(visitor1);
        ride.addVisitorToHistory(visitor2);
        ride.addVisitorToHistory(visitor3);
        ride.addVisitorToHistory(visitor4);
        ride.addVisitorToHistory(visitor5);

        // 检查游客是否在乘坐历史中
        ride.checkVisitorFromHistory(visitor3);

        // 打印乘坐历史中的游客数量
        ride.numberOfVisitors();

        // 打印乘坐历史中的游客信息
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

        // 添加游客到乘坐历史
        ride.addVisitorToHistory(visitor1);
        ride.addVisitorToHistory(visitor2);
        ride.addVisitorToHistory(visitor3);
        ride.addVisitorToHistory(visitor4);
        ride.addVisitorToHistory(visitor5);

        // 打印排序前乘坐历史中的游客信息
        System.out.println("排序前乘坐历史中的游客信息：");
        ride.printRideHistory();

        // 对乘坐历史中的游客进行排序
        ride.sortVisitors();

        // 打印排序后乘坐历史中的游客信息
        System.out.println("排序后乘坐历史中的游客信息：");
        ride.printRideHistory();
    }

    public void partFive() {
        // 预留方法，可根据后续作业要求添加相应功能逻辑
    }

    public void partSix() {
        // 预留方法，可根据后续作业要求添加相应功能逻辑
    }

    public void partSeven() {
        // 预留方法，可根据后续作业要求添加相应功能逻辑
    }
}