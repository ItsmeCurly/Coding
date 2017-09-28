import java.util.Random;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
public class maxSum {
    public static void main(String[] args) throws IOException {
        Random rand = new Random();
        BufferedWriter bw = new BufferedWriter(new FileWriter("results.txt"));
        for (int j = 0; j < 100000; j++) {
            int size = 100 + j;
            int[] a = new int[size];

            for (int i = 0; i < size; i++) {
                a[i] = rand.nextInt(1250) - 625;
            }
            long startTime = System.currentTimeMillis();
            maxSubsequenceSumN(a);
            long endTime = System.currentTimeMillis();

            String str = size + " " + (endTime - startTime) + "\n";
            bw.write(str);
        }
        for (int j = 0; j < 5000; j++) {
            int size = 100 + j;
            int[] a = new int[size];

            for (int i = 0; i < size; i++) {
                a[i] = rand.nextInt(1250) - 625;
            }

            long startTime = System.currentTimeMillis();
            maxSubsequenceSumN2(a);
            long endTime = System.currentTimeMillis();

            String str = size + " " + (endTime - startTime) + "\n";
            bw.write(str);
        }
        for (int j = 0; j < 1000; j++) {
            int size = 100 + j;
            int[] a = new int[size];

            for (int i = 0; i < size; i++) {
                a[i] = rand.nextInt(1250) - 625;
            }
            long startTime = System.currentTimeMillis();
            maxSubsequenceSumN3(a);
            long endTime = System.currentTimeMillis();

            String str = size + " " + (endTime - startTime) + "\n";
            bw.write(str);
        }
        bw.close();
    }

    private static int maxSubsequenceSumN3(int[] a) {
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

    private static int maxSubsequenceSumN2(int[] a) {
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

    private static int maxSubsequenceSumN(int[] a) {
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
