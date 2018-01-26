package mazerunner;

public class Runner {
    public Runner() {
        new Maze("assets\\mazerunner\\maze1.txt");
        new Maze("assets\\mazerunner\\maze2.txt");
        new Maze("assets\\mazerunner\\maze3.txt");
        new Maze("assets\\mazerunner\\maze4.txt");
        new Maze("assets\\mazerunner\\maze5.txt");
        //new Maze("assets\\mazerunner\\maze6.txt");
        new Maze("assets\\mazerunner\\maze7.txt");
    }

    public static void main(String [] args) {
        new Runner();
    }
}
