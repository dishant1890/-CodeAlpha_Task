import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GradeTrackerApp {

    // Inner class: Student
    static class Student {
        private String name;
        private ArrayList<Integer> grades;

        public Student(String name, ArrayList<Integer> grades) {
            this.name = name;
            this.grades = grades;
        }

        public String getName() {
            return name;
        }

        public ArrayList<Integer> getGrades() {
            return grades;
        }

        public double getAverage() {
            int sum = 0;
            for (int grade : grades) {
                sum += grade;
            }
            return grades.size() == 0 ? 0 : (double) sum / grades.size();
        }

        public int getHighest() {
            return grades.isEmpty() ? 0 : Collections.max(grades);
        }

        public int getLowest() {
            return grades.isEmpty() ? 0 : Collections.min(grades);
        }
    }

    // Main method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Student> studentList = new ArrayList<>();

        System.out.print("Enter number of students: ");
        int n = sc.nextInt();
        sc.nextLine();  // Consume newline

        for (int i = 0; i < n; i++) {
            System.out.print("\nEnter name of student " + (i + 1) + ": ");
            String name = sc.nextLine();

            ArrayList<Integer> grades = new ArrayList<>();
            System.out.print("Enter number of grades for " + name + ": ");
            int g = sc.nextInt();

            for (int j = 0; j < g; j++) {
                System.out.print("Enter grade " + (j + 1) + ": ");
                grades.add(sc.nextInt());
            }
            sc.nextLine(); // consume newline

            studentList.add(new Student(name, grades));
        }

        // Display report
        System.out.println("\n===== Student Summary Report =====");
        for (Student s : studentList) {
            System.out.println("Name    : " + s.getName());
            System.out.println("Grades  : " + s.getGrades());
            System.out.printf("Average : %.2f\n", s.getAverage());
            System.out.println("Highest : " + s.getHighest());
            System.out.println("Lowest  : " + s.getLowest());
            System.out.println("----------------------------------");
        }

        sc.close();
    }
}
