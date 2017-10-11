public class NestedLoop {
    public static void main (String [] args) {
        int userNum  = 3;
        int i = 0;
        int j = 0;


        while (i <= userNum) {
            j = 0;
            while (j < i) {
                ++j;
                System.out.print(" ");
            }
            System.out.println(i);
            i++;
        }

        return;
    }
}