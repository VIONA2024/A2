import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

// 抽象的Person类 - 永远不能被实例化（即不能从该类创建对象）
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

// Employee类 - 表示主题公园员工信息，继承自Person类
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

// Visitor类 - 表示主题公园游客信息，继承自Person类
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

// RideInterface接口 - 定义游乐设施操作相关的方法
// 名为addVisitorToQueue的接口方法：用于将一名游客添加到队列中。它有一个Visitor类型的参数。见第3部分。
interface RideInterface {
    void addVisitorToQueue(Visitor visitor);
    // 名为removeVisitorFromQueue的接口方法：用于从队列中移除一名游客。见第3部分。
    void removeVisitorFromQueue(Visitor visitor);
    // 名为printQueue的接口方法：用于打印队列中等待的游客列表。见第3部分。
    void printQueue();
    // 名为runOneCycle的接口方法：用于运行游乐设施一次循环。见第5部分。
    void runOneCycle();
    // 名为addVisitorToHistory的接口方法：用于将一名游客添加到游乐设施的乘坐历史记录中。它有一个Visitor类型的参数。见第4部分。
    void addVisitorToHistory(Visitor visitor);
    // 名为checkVisitorFromHistory的接口方法：用于检查该游客是否在游乐设施的乘坐历史记录中。它有一个Visitor类型的参数。见第4部分。
    boolean checkVisitorFromHistory(Visitor visitor);
    // 名为numberOfVisitors的接口方法：用于返回乘坐历史记录中的游客数量。见第4部分。
    int numberOfVisitors();
    // 名为printRideHistory的接口方法：用于打印乘坐过游乐设施的游客列表。见第4部分。
    void printRideHistory();
}

// Ride类 - 表示主题公园游乐设施信息，实现了RideInterface接口
class Ride implements RideInterface {
    private String rideName;
    private int rideCapacity;
    private Employee operator;
    private Queue<Visitor> queue;
    private LinkedList<Visitor> rideHistory;

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

    // 名为addVisitorToQueue的接口方法：用于将一名游客添加到队列中。它有一个Visitor类型的参数。见第3部分。
    @Override
    public void addVisitorToQueue(Visitor visitor) {
        queue.add(visitor);
        System.out.println(visitor.getName() + "已成功添加到队列中。");
    }

    // 名为removeVisitorFromQueue的接口方法：用于从队列中移除一名游客。见第3部分。
    @Override
    public void removeVisitorFromQueue(Visitor visitor) {
        if (queue.remove(visitor)) {
            System.out.println(visitor.getName() + "已成功从队列中移除。");
        } else {
            System.out.println(visitor.getName() + "未在队列中找到。");
        }
    }

    // 名为printQueue的接口方法：用于打印队列中等待的游客列表。见第3部分。
    @Override
    public void printQueue() {
        System.out.println("队列中的游客：");
        for (Visitor visitor : queue) {
            System.out.println("姓名：" + visitor.getName() + "，年龄：" + visitor.getAge() +
                    "，性别：" + visitor.getGender() + "，门票类型：" + visitor.getTicketType() +
                    "，是否首次游玩：" + visitor.isFirstVisit());
        }
    }

    // 名为runOneCycle的接口方法：用于运行游乐设施一次循环。见第5部分。
    @Override
    public void runOneCycle() {
        // 在此处你可以实现运行游乐设施一次循环的逻辑
        // 例如，如果队列中有游客且游乐设施还有空位
        if (!queue.isEmpty() && rideHistory.size() < rideCapacity) {
            Visitor visitor = queue.poll();
            addVisitorToHistory(visitor);
            System.out.println(visitor.getName() + "已乘坐了游乐设施。");
        } else {
            System.out.println("游乐设施已满或者队列中无人。");
        }
    }

    // 名为addVisitorToHistory的接口方法：用于将一名游客添加到游乐设施的乘坐历史记录中。它有一个Visitor类型的参数。见第4部分。
    @Override
    public void addVisitorToHistory(Visitor visitor) {
        rideHistory.add(visitor);
        System.out.println(visitor.getName() + "已添加到乘坐历史记录中。");
    }

    // 名为checkVisitorFromHistory的接口方法：用于检查该游客是否在游乐设施的乘坐历史记录中。它有一个Visitor类型的参数。见第4部分。
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

    // 名为numberOfVisitors的接口方法：用于返回乘坐历史记录中的游客数量。见第4部分。
    @Override
    public int numberOfVisitors() {
        int count = rideHistory.size();
        System.out.println("乘坐历史记录中的游客数量是：" + count);
        return count;
    }

    // 名为printRideHistory的接口方法：用于打印乘坐过游乐设施的游客列表。见第4部分。
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

// AssignmentTwo类 - 主程序类，包含main方法以及其他方法框架
// AssignmentTwo class - 主程序类，包含 main 方法和其他方法框架
public class AssignmentTwo {
    // The main method, which is the entry point of the Java application
    public static void main(String[] args) {
        // 这里可以创建各个类的实例并进行相关测试等操作
    }

    public void partThree() {
    }

    public void partFourA() {
    }

    public void partFourB() {
    }

    public void partFive() {
    }

    public void partSix() {
    }

    public void partSeven() {
    }
}