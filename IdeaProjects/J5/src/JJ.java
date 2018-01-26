import java.util.Arrays;

public class JJ {
    private int[] pogoJumps;
    private int doorLength;

    public JJ(int doorLength, int[] pogoJumps) {
        this.pogoJumps = pogoJumps;
        this.doorLength = doorLength;
        move(new int[doorLength], 0);
    }

    private void move(int[] movements, int currentIndex) {
        int sum = 0;
        for (int i : movements)
            sum += i;

        if (doorLength == sum)
            printMinimalisticArray(movements, currentIndex);
        if (doorLength <= sum)
            return;

        for (int pogoJump : pogoJumps) {
            int[] newMovements = Arrays.copyOf(movements, movements.length);
            newMovements[currentIndex] = pogoJump;
            move(newMovements, currentIndex + 1);
        }
    }

    private void printMinimalisticArray(int[] movements, int range) {
        int[] newArray = Arrays.copyOfRange(movements, 0, range);
        for (int i : newArray)
            System.out.print(i + " ");
        System.out.println();
    }
}
