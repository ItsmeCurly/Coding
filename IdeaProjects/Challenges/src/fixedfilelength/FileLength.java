package fixedfilelength;

import java.io.*;

public class FileLength {

    public FileLength(String fileName) {
        readFile(new File(fileName));
    }

    private void readFile(File file) {
        Person person = new Person();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String str;
        assert br != null;
        try {
            Person newPerson = null;
            while ((str = br.readLine()) != null) {
                if (str.contains("EXT")) {
                    if (str.contains("SAL")) {
                        assert newPerson != null;
                        newPerson.setSalary(Integer.parseInt(str.substring(11, 28)));
                    }
                }
                else {
                    if(newPerson != null)
                        if (newPerson.compareTo(person) > 0)
                            person = newPerson;
                    newPerson = new Person(str.substring(0, 20).trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(person);
    }
}
