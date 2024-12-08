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
// 根据要求，在这个阶段只涉及游客排队相关操作的接口方法，所以RideInterface接口中暂时只保留了addVisitorToQueue、
// removeVisitorFromQueue、printQueue这三个与队列操作直接相关的方法定义，用于后续Ride类实现，以管理游客排队情况。
interface RideInterface {
    // A method named AddVisitorToQueue to add a Visitor to the Queue (see the interface you created in Part 2).
    void addVisitorToQueue(Visitor visitor);
    // A method named RemoveVisitorFromQueue to remove a Visitor from the Queue (see the interface you created in Part 2).
    void removeVisitorFromQueue(Visitor visitor);
    // A method named PrintQueue that prints all the details for all Visitors in the Queue in the order they were added (see the interface you created in Part 2).
    void printQueue();
}

// Ride类 - 表示主题公园游乐设施信息，实现了RideInterface接口
class Ride implements RideInterface {
    private String rideName;
    private int rideCapacity;
    private Employee operator;
    private Queue<Visitor> queue;

    public Ride() {
        // 在无参构造方法中初始化游客队列，确保创建Ride对象时队列能正常使用，用于存储等待乘坐该游乐设施的游客。
        this.queue = new LinkedList<>();
    }

    public Ride(String rideName, int rideCapacity, Employee operator) {
        this.rideName = rideName;
        this.rideCapacity = rideCapacity;
        this.operator = operator;
        // 在有参构造方法中同样初始化游客队列，确保创建Ride对象时队列能正常使用，用于存储等待乘坐该游乐设施的游客。
        this.queue = new LinkedList<>();
    }

    // 实现接口中的添加游客到队列方法
    // A method named AddVisitorToQueue to add a Visitor to the Queue (see the interface you created in Part 2).
    @Override
    public void addVisitorToQueue(Visitor visitor) {
        queue.add(visitor);
        System.out.println(visitor.getName() + "已成功添加到队列中。");
    }

    // 实现接口中的从队列移除游客方法
    // A method named RemoveVisitorFromQueue to remove a Visitor from the Queue (see the interface you created in Part 2).
    @Override
    public void removeVisitorFromQueue(Visitor visitor) {
        if (queue.remove(visitor)) {
            System.out.println(visitor.getName() + "已成功从队列中移除。");
        } else {
            System.out.println(visitor.getName() + "未在队列中找到，移除失败。");
        }
    }

    // 实现接口中的打印队列方法
    // A method named PrintQueue that prints all the details for all Visitors in the Queue in the order they were added (see the interface you created in Part 2).
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
// AssignmentTwo类 - 主程序类，包含main方法以及其他方法框架
public class AssignmentTwo {
    public static void main(String[] args) {
        partThree();
    }

    public void partThree() {
        // 在partThree方法中进行以下操作演示，符合此部分作业要求中对操作演示的规定。

        // 创建一个Ride对象，用于后续添加游客到其对应的排队队列等操作。
        Employee operator = new Employee("Operator1", 30, "Male", "Ride Operator", 1001);
        Ride ride = new Ride("过山车", 20, operator);

        // 创建5个游客对象并添加到队列，模拟游客排队等待乘坐游乐设施的情况。
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

        // 从队列中移除一个游客，用于测试从队列中移除游客的功能是否正常。
        ride.removeVisitorFromQueue(visitor3);

        // 打印队列中的所有游客信息，展示当前排队游客的详细情况，方便查看整体排队状态。
        ride.printQueue();
    }

    // 其他partFourA等方法定义
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