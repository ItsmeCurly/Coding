public class Runner {
    public Runner(String input) {
//        StringTokenizer st = new StringTokenizer(input);
//        int doorDistance = Integer.parseInt(st.nextToken());
//        String [] pogos = input.split(" ");
//        int [] pogoDistances = new int[pogos.length - 1];
//        for(int i = 1; i < pogos.length; i++) {
//            pogoDistances[i - 1] = Integer.parseInt(pogos[i]);
//        }
        int doorDistance = 5; //REMOVE AFTER
        int[] pogoDistances = {10, 5, 3, 1};
        new JJ(doorDistance, pogoDistances);
    }

    public static void main(String[] args) {
//        Scanner scan = new Scanner(System.in);
//        System.out.print("Enter the distance to the door, followed by JJ's pogo sticks' distances: ");
//        new Runner(scan.nextLine());
        new Runner("");
    }
}
