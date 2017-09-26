import java.util.Random;
import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
public class maxSum {
    public static void main(String[] args) throws IOException {
        Random rand = new Random();
        BufferedWriter bw = new BufferedWriter(new FileWriter("results.txt"));
        for (int j = 0; j < 10; j++) {
            int randomSize = rand.nextInt(300) + 1;
            int[] a = new int[randomSize];

            for (int i = 0; i < randomSize; i++) {
                a[i] = rand.nextInt(250) - 125;
            }
            long startTime = System.currentTimeMillis();
            maxSubsequenceSumN3(a);
            long endTime = System.currentTimeMillis();

            String str = "N^3 function timing: " + (endTime - startTime) + "\n";
            bw.write(str);

            startTime = System.currentTimeMillis();
            maxSubsequenceSumN2(a);
            endTime = System.currentTimeMillis();

            str = "N^2 function timing: " + (endTime - startTime) + "\n";
            bw.write(str);

            startTime = System.currentTimeMillis();
            maxSubsequenceSumN(a);
            endTime = System.currentTimeMillis();

            str = "N function timing: " + (endTime - startTime) + "\n";
            bw.write(str);
        }
    }

    public static int maxSubsequenceSumN3(int[] a) {
        int maxSum = 0;
        for (int i = 0; i < a.length; i++)
            for (int j = i; j < a.length; j++) {
                int thisSum = 0;
                for (int k = i; k <= j; k++)
                    thisSum += a[k];
                if (thisSum > maxSum) {
                    maxSum = thisSum;
                }
            }
        return maxSum;
    }

    public static int maxSubsequenceSumN2(int[] a) {
        int maxSum = 0;
        for (int i = 0; i < a.length; i++) {
            int thisSum = 0;
            for (int j = i; j < a.length; j++) {
                thisSum += a[j];
                if (thisSum > maxSum)
                    maxSum = thisSum;
            }
        }
        return maxSum;
    }

    public static int maxSubsequenceSumN(int[] a) {
        int maxSum = 0;
        int thisSum = 0;
        for (int i = 0, j = 0; j < a.length; j++) {
            thisSum += a[j];
            if (thisSum > maxSum) {
                maxSum = thisSum;
            } else if (thisSum < 0) {
                i = j + 1;
                thisSum = 0;
            }
        }
        return maxSum;
    }
}
