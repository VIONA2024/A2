import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Collections;
import java.util.Comparator;

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

class Ride implements RideInterface {
    private String rideName;
    private int rideCapacity;
    private Employee operator;
    private Queue<Visitor> queue;
    private LinkedList<Visitor> rideHistory;
    private int maxRider;
    private int numOfCycles;

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
        System.out.println(visitor.getName() + " has been successfully added to the queue.");
    }

    @Override
    public void removeVisitorFromQueue(Visitor visitor) {
        if (queue.remove(visitor)) {
            System.out.println(visitor.getName() + " has been successfully removed from the queue.");
            addVisitorToHistory(visitor);
        } else {
            System.out.println(visitor.getName() + " was not found in the queue and the removal failed.");
        }
    }

    @Override
    public void printQueue() {
        System.out.println("The visitor information in the queue is as follows:");
        Iterator<Visitor> iterator = queue.iterator();
        while (iterator.hasNext()) {
            Visitor visitor = iterator.next();
            System.out.println("Name: " + visitor.getName() + ", Age: " + visitor.getAge() +
                    ", Gender: " + visitor.getGender() + ", Ticket Type: " + visitor.getTicketType() +
                    ", Is First Visit: " + visitor.isFirstVisit());
        }
    }

    @Override
    public void addVisitorToHistory(Visitor visitor) {
        rideHistory.add(visitor);
        System.out.println(visitor.getName() + " has been added to the ride history record.");
    }

    @Override
    public boolean checkVisitorFromHistory(Visitor visitor) {
        boolean result = rideHistory.contains(visitor);
        if (result) {
            System.out.println(visitor.getName() + " is in the ride history record.");
        } else {
            System.out.println(visitor.getName() + " is not in the ride history record.");
        }
        return result;
    }

    @Override
    public int numberOfVisitors() {
        int count = rideHistory.size();
        System.out.println("The number of visitors in the ride history record is: " + count);
        return count;
    }

    @Override
    public void printRideHistory() {
        System.out.println("The visitors in the ride history record:");
        Iterator<Visitor> iterator = rideHistory.iterator();
        while (iterator.hasNext()) {
            Visitor visitor = iterator.next();
            System.out.println("Name: " + visitor.getName() + ", Age: " + visitor.getAge() +
                    ", Gender: " + visitor.getGender() + ", Ticket Type: " + visitor.getTicketType() +
                    ", Is First Visit: " + visitor.isFirstVisit());
        }
    }

    @Override
    public void sortVisitors() {
        Collections.sort(rideHistory, new VisitorComparator());
        System.out.println("The visitors in the ride history record have been sorted.");
    }

    @Override
    public void runOneCycle() {
        if (operator == null) {
            System.out.println("No amusement facility operator has been assigned, and the ride cannot be run.");
            return;
        }
        if (queue.isEmpty()) {
            System.out.println("There are no waiting visitors in the queue, and the ride cannot be run.");
            return;
        }

        int numToRemove = Math.min(maxRider, queue.size());
        for (int i = 0; i < numToRemove; i++) {
            Visitor visitor = queue.poll();
            addVisitorToHistory(visitor);
            System.out.println(visitor.getName() + " has been removed from the queue and added to the ride history record. The current ride cycle is running...");
        }

        numOfCycles++;
        System.out.println("The current ride cycle has been completed. The number of cycles that have been run is: " + numOfCycles);
    }

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
            System.out.println("The ride history record has been successfully exported to the file ride_history.txt.");
        } catch (IOException e) {
            System.err.println("An error occurred when exporting the ride history record to the file: " + e.getMessage());
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

    public void importRideHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader("ride_history.txt"))) {
            String line;
            while ((line = reader.readLine())!= null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String name = parts[0];
                    int age = Integer.parseInt(parts[1]);
                    String gender = parts[2];
                    String ticketType = parts[3];
                    boolean isFirstVisit = Boolean.parseBoolean(parts[4]);
                    Visitor visitor = new Visitor(name, age, gender, ticketType, isFirstVisit);
                    rideHistory.add(visitor);
                } else {
                    System.err.println("The data format of a certain line in the file is incorrect, and the visitor information cannot be parsed.");
                }
            }
            System.out.println("Visitor information has been successfully imported from the file to the ride history record.");
        } catch (IOException e) {
            System.err.println("An error occurred when reading the file to import the ride history record: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("An error occurred when parsing the visitor's age information: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Other unknown errors: " + e.getMessage());
        }
    }
}

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

public class AssignmentTwo {
    public static void main(String[] args) {
        AssignmentTwo assignmentTwo = new AssignmentTwo();
        
        System.out.println("------------------------------------------------------");
        System.out.println("Part 3:");
        System.out.println("------------------------------------------------------");
        assignmentTwo.partThree();
        System.out.println("------------------------------------------------------");
        System.out.println("Part 4A:");
        System.out.println("------------------------------------------------------");
        assignmentTwo.partFourA();
        System.out.println("------------------------------------------------------");
        System.out.println("Part 4B:");
        System.out.println("------------------------------------------------------");
        assignmentTwo.partFourB();
        System.out.println("------------------------------------------------------");
        System.out.println("Part 5:");
        System.out.println("------------------------------------------------------");
        assignmentTwo.partFive();
        System.out.println("------------------------------------------------------");
        System.out.println("Part 6:");
        System.out.println("------------------------------------------------------");
        assignmentTwo.partSix();
        System.out.println("------------------------------------------------------");
        System.out.println("Part 7:");
        System.out.println("------------------------------------------------------");
        assignmentTwo.partSeven();
    }

    public void partThree() {
        Employee operator = new Employee("Operator1", 30, "Male", "Ride Operator", 1001);
        Ride ride = new Ride("Roller Coaster", 20, operator);

        Visitor visitor1 = new Visitor("Visitor 1", 25, "Female", "All-day Ticket", true);
        Visitor visitor2 = new Visitor("Visitor 2", 35, "Male", "Half-day Ticket", false);
        Visitor visitor3 = new Visitor("Visitor 3", 18, "Female", "All-day Ticket", true);
        Visitor visitor4 = new Visitor("Visitor 4", 40, "Male", "Two-day Ticket", false);
        Visitor visitor5 = new Visitor("Visitor 5", 22, "Female", "All-day Ticket", true);

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
        Ride ride = new Ride("Roller Coaster", 20, operator);

        Visitor visitor1 = new Visitor("Visitor 1", 25, "Female", "All-day Ticket", true);
        Visitor visitor2 = new Visitor("Visitor 2", 30, "Male", "Half-day Ticket", false);
        Visitor visitor3 = new Visitor("Visitor 3", 18, "Female", "All-day Ticket", true);
        Visitor visitor4 = new Visitor("Visitor 4", 40, "Male", "Two-day Ticket", false);
        Visitor visitor5 = new Visitor("Visitor 5", 22, "Female", "All-day Ticket", true);

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
        Ride ride = new Ride("Roller Coaster", 20, operator);

        Visitor visitor1 = new Visitor("Visitor 1", 25, "Female", "All-day Ticket", true);
        Visitor visitor2 = new Visitor("Visitor 2", 35, "Male", "Half-day Ticket", false);
        Visitor visitor3 = new Visitor("Visitor 3", 18, "Female", "All-day Ticket", true);
        Visitor visitor4 = new Visitor("Visitor 4", 40, "Male", "Two-day Ticket", false);
        Visitor visitor5 = new Visitor("Visitor 5", 22, "Female", "All-day Ticket", true);

        ride.addVisitorToHistory(visitor1);
        ride.addVisitorToHistory(visitor2);
        ride.addVisitorToHistory(visitor3);
        ride.addVisitorToHistory(visitor4);
        ride.addVisitorToHistory(visitor5);

        System.out.println("The visitor information in the ride history record before sorting:");
        ride.printRideHistory();

        ride.sortVisitors();

        System.out.println("The visitor information in the ride history record after sorting:");
        ride.printRideHistory();
    }

    public void partFive() {
        Employee operator = new Employee("Operator1", 30, "Male", "Ride Operator", 1001);
        Ride ride = new Ride("Roller Coaster", 20, operator, 3);

        Visitor visitor1 = new Visitor("Visitor 1", 25, "Female", "All-day Ticket", true);
        Visitor visitor2 = new Visitor("Visitor 2", 35, "Male", "Half-day Ticket", false);
        Visitor visitor3 = new Visitor("Visitor 3", 18, "Female", "All-day Ticket", true);
        Visitor visitor4 = new Visitor("Visitor 4", 40, "Male", "Two-day Ticket", false);
        Visitor visitor5 = new Visitor("Visitor 5", 22, "Female", "All-day Ticket", true);
        Visitor visitor6 = new Visitor("Visitor 6", 28, "Male", "All-day Ticket", false);
        Visitor visitor7 = new Visitor("Visitor 7", 32, "Female", "Half-day Ticket", true);
        Visitor visitor8 = new Visitor("Visitor 8", 45, "Male", "Two-day Ticket", false);
        Visitor visitor9 = new Visitor("Visitor 9", 19, "Female", "All-day Ticket", true);
        Visitor visitor10 = new Visitor("Visitor 10", 24, "Male", "All-day Ticket", false);

        for (Visitor v : new Visitor[]{visitor1, visitor2, visitor3, visitor4, visitor5, visitor6, visitor7, visitor8, visitor9, visitor10}) {
            ride.addVisitorToQueue(v);
        }

        System.out.println("The visitor information in the queue before running a cycle:");
        ride.printQueue();

        ride.runOneCycle();

        System.out.println("The visitor information in the queue after running a cycle:");
        ride.printQueue();

        System.out.println("The visitor information in the ride history record:");
        ride.printRideHistory();
    }

    public void partSix() {
        Employee operator = new Employee("Operator1", 30, "Male", "Ride Operator", 1001);
        Ride ride = new Ride("New Amusement Facility", 20, operator);

        Visitor visitor1 = new Visitor("Visitor 11", 28, "Female", "All-day Ticket", true);
        Visitor visitor2 = new Visitor("Visitor 12", 32, "Male", "Half-day Ticket", false);
        Visitor visitor3 = new Visitor("Visitor 13", 20, "Female", "All-day Ticket", true);
        Visitor visitor4 = new Visitor("Visitor 14", 40, "Male", "Two-day Ticket", false);
        Visitor visitor5 = new Visitor("Visitor 15", 22, "Female", "All-day Ticket", true);

        ride.addVisitorToHistory(visitor1);
        ride.addVisitorToHistory(visitor2);
        ride.addVisitorToHistory(visitor3);
        ride.addVisitorToHistory(visitor4);
        ride.addVisitorToHistory(visitor5);

        ride.exportRideHistory();
    }

    public void partSeven() {
        Employee operator = new Employee("Operator1", 30, "Male", "Ride Operator", 1001);
        Ride ride = new Ride("Test Amusement Facility", 20, operator); 
        ride.importRideHistory();  
        int visitorCount = ride.numberOfVisitors();  
        System.out.println("The number of visitors in the ride history record after import is: " + visitorCount); 
        ride.printRideHistory();
    }
}