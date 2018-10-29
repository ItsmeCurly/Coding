import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    private static final int CAPACITY = 24;

    public static void main(String[] args) {
        Scanner fScan = null;
        try {
            fScan = new Scanner(new FileReader("grades.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Student [] students = new Student[CAPACITY];
        for(int i = 0; i < CAPACITY; i++) {
            students[i] = new Student();
        }
        int studentCounter = 0;
        assert fScan != null;
        while(fScan.hasNextLine()) {
            String line = fScan.nextLine();
            System.out.println(line);
            String [] lineSplit = line.split("[ \t]+");
            students[studentCounter].setID(lineSplit[0]);
            students[studentCounter].setLastName(lineSplit[1]);
            students[studentCounter].setFirstName(lineSplit[2]);
            students[studentCounter].setMtScore(Integer.parseInt(lineSplit[5]));
            students[studentCounter].setFnScore(Integer.parseInt(lineSplit[6]));
            studentCounter += 1;
        }
        double mtM = mean(students, 'M');
        double mtS = stdDev(students, 'M', mtM);

        double fnM = mean(students, 'F');
        double fnS = stdDev(students, 'F', fnM);

        String input = "";
        Scanner uScan = new Scanner(System.in);

        System.out.println("Midterm: Mean: " + mtM + " Standard Deviation: " + mtS);
        System.out.println("Final: Mean " + fnM + " Standard Deviation: " + fnS);

        do {
            if(!input.equals("")) {
                Student s = getStudent(input, students);
                if(s!= null)
                    System.out.printf("%s %s has midterm %d with grade %c and final exam %d with grade %c\n",
                            s.getFirstName(), s.getLastName(), s.getMtScore(), letter(s.getMtScore(), mtM, mtS),
                            s.getFnScore(), letter(s.getFnScore(), fnM, fnS));
                else
                    System.out.println("Did not find" + input);
            }
            System.out.print("Enter code(quit to end): ");
            input = uScan.next();

        } while(!input.equals("quit"));
    }

    public static Student getStudent(String code, Student[] students) {
        for(Student s : students) {
            if(code.equals(s.getCode())) return s;
        }
        return null;
    }

    public static double mean(Student []s, char c) {
        double sum = 0;
        for(Student student : s) {
            if(c == 'M') {
                sum += student.getMtScore();
            } else {
                sum += student.getFnScore();
            }
        }
        return sum/s.length;
    }

    public static double stdDev(Student []s, char c, double m) {
        double temp = 0;
        for(Student student : s) {
            if(c == 'M')
                temp += Math.pow(student.getMtScore() - m, 2);
            else
                temp += Math.pow(student.getFnScore() - m, 2);
        }
        temp = temp / (s.length - 1);
        return Math.sqrt(temp);
    }

    public static char letter(int score, double m, double s) {
        if(score < (m - 1.5*s))
            return 'F';
        else if(score < (m - 0.5*s))
            return 'D';
        else if(score < (m + 0.5*s))
            return 'C';
        else if(score < (m + 1.5*s))
            return 'B';
        else
            return 'A';
    }

    public static int search(Student []s, String code) {
        return 0;
    }
}
