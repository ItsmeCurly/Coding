import java.util.Arrays;

public class JJ {
    private int[] pogoJumps;
    private int doorLength;

    private int counter;
    private String output;

    JJ(int doorLength, int[] pogoJumps) {
        this.pogoJumps = pogoJumps;
        this.doorLength = doorLength;
        this.counter = 0;
        this.output = "";

        Arrays.sort(pogoJumps);
        move(new int[doorLength], 0);
        System.out.println("# of Combinations: " + counter + " ");
        System.out.println(output);
    }
    //recursive method
    private void move(int[] movements, int currentIndex) {
        int sum = 0;
        for (int movement : movements) {
            sum += movement;
        }

        if (doorLength == sum) //the movements = doorlength(reached)
            printMinimalisticArray(movements, currentIndex);
        if (doorLength <= sum) //the movements is greater or equal to the doorlength, to end recursion
            return;

        for (int pogoJump : pogoJumps) { //to check all pogo jumps
            int[] newMovements = Arrays.copyOf(movements, movements.length);
            newMovements[currentIndex] = pogoJump;
            move(newMovements, currentIndex + 1);
        }
    }

    private void printMinimalisticArray(int[] movements, int range) { //to print the combinations and #of combos
        counter += 1; //add to combos
        int[] newArray = Arrays.copyOfRange(movements, 0, range);
        for (int i : newArray)
            output += i + " ";
        output += "\n";
    }

    public int getCombos() {
        return counter;
    }
}