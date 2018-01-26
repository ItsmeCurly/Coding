import java.util.Arrays;

public class JJ {
    private int[] pogoJumps;
    private int doorLength;

    public JJ(int doorLength, int[] pogoJumps) {
        this.pogoJumps = pogoJumps;
        this.doorLength = doorLength;
        move(new int[doorLength + 1], 0);
    }

    public void move(int[] movements, int currentIndex) {
        int sum = 0;
        for (int i : movements)
            sum += i;

        if (doorLength == sum)
            System.out.println(Arrays.toString(movements));
        if (doorLength <= sum)
            return;

        for (int i = 0; i < pogoJumps.length; i++) {
            int[] newMovements = Arrays.copyOf(movements, movements.length);
            newMovements[currentIndex] = pogoJumps[i];
            move(newMovements, currentIndex + 1);
        }
    }
}
