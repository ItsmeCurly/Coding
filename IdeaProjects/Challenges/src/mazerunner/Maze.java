package mazerunner;


import java.awt.*;
import java.io.*;

public class Maze {
    private int [][] maze;
    private MazeMover mazeMover;
    private final int DEFAULTSIZE = 16;
    private Point exitLocation;

    public Maze() {
        this(null);
    }

    public Maze(String fileName) {
        init(fileName);
    }

    private void init(String fileName) {
        File file = new File(fileName);
        readMaze(file);
        System.out.println(solveMaze());
//        System.out.println(this);
    }

    private void readMaze(File file) {
        maze = new int[DEFAULTSIZE][DEFAULTSIZE];
        BufferedReader br = null;
        for(int x = 0; x < DEFAULTSIZE; x++)
            for(int y = 0; y < DEFAULTSIZE; y++)
                maze[x][y] = -1;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int i = 0, j;
        try {
            String line;
            while((line = br != null ? br.readLine() : null) != null) {
                j = 0;
                for(char c : line.toCharArray()) {
                    switch(c) {
                        case '<': case '>': case 'v': case '^':
                            mazeMover = new MazeMover(i, j, c);
                            maze[i][j] = 3;
                            break;
                        case '#':
                            maze[i][j] = 1;
                            break;
                        case 'E':
                            exitLocation = new Point(i, j);
                            maze[i][j] = 2;
                            break;
                        case ' ':
                            maze[i][j] = 0;
                            break;
                        default: System.err.println("Unknown character");
                    }
                    j++;
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(this);

    }
    private boolean solveMaze() {
        while(true) {
            mazeMover.move(this);
            if(mazeMover.getLocation().equals(getExitLocation())) return true;
            if(mazeMover.getTurnCounter() > 2) return false;
        }

    }

    int[][] getMaze() {
        return maze;
    }

    Point getExitLocation() {
        return exitLocation;
    }

    private String getMazeCharacter(int target) {
        switch(target) {
            case -1: break;
            case 0: return " ";
            case 1: return "#";
            case 2: return "E";
            case 3: return mazeMover.toString();
            default: System.err.println("Incorrect maze int value");
            System.exit(1);
        }
        return ""; //completely unreachable come on Java
    }
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(int x = 0; x < DEFAULTSIZE; x++) {
            for (int y = 0; y < DEFAULTSIZE; y++)
                result.append(getMazeCharacter(maze[x][y]));
            if(x < (DEFAULTSIZE - 1) && maze[x + 1][0] != -1)
                result.append("\n");
        }
        return result.toString();
    }
}
