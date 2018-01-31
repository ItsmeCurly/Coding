package ASCII85;

import java.util.StringTokenizer;

public class ASCII85Encoding {

    public ASCII85Encoding(String input) {
        StringTokenizer st = new StringTokenizer(input);
        String command = st.nextToken();
        if(command.equals("e")) encode(st.nextToken());
        else if(command.equals("d")) decode(st.nextToken());
    }

    public String encode(String code) {
        String output = "";
        int[] asciiArray = new int[code.length()];
        String [] binaryArray = new String[code.length()];
        for(int i = 0; i < code.length(); i++) {
            asciiArray[i] = (int)code.charAt(i);
            binaryArray[i] = Integer.toBinaryString(asciiArray[i]);
        }
        String concat = "";
        for(String s : binaryArray) {
            concat += (s);
        }
        double bitValue = 0;
        for(int i = 0; i < concat.length(); i++) bitValue += (int)concat.charAt(i) * Math.pow(2, i);
        int maxPower = 0;
        for(;;maxPower++)
            if(bitValue / Math.pow(85, maxPower + 1) < 1) break;
        int [] decomposed = new int[maxPower+1];
        for(int i = maxPower; i > 0; i++) {
            //decomposed[]
        }

        return output;
    }

    public String decode(String code) {
        String output = "";

        return output;
    }
}
