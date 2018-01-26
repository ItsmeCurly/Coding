package mazerunner;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class MazeMover {
    private int turnCounter;
    private final int maxStep = 6;
    private Point location;
    private static Map<Character, int []> moveMap = null;
    private static Map<Character, char []> turnMap = null;
    private char dir;

    MazeMover(int x, int y, char dir) {
        turnCounter = 0;
        if(moveMap == null || turnMap == null) {
            moveMap = new LinkedHashMap<>();
            turnMap = new LinkedHashMap<>();
            initMaps();
        }
        location = new Point(x,y);
        this.dir = dir;
    }

    private void initMaps() {
        moveMap.put('>', new int []{0, 1});
        moveMap.put('^', new int []{-1, 0});
        moveMap.put('<', new int []{0, -1});
        moveMap.put('v', new int []{1, 0});

        turnMap.put('>', new char[]{'v', '^', '<'});
        turnMap.put('^', new char[]{'>', '<', 'v'});
        turnMap.put('<', new char[]{'^', 'v', '>'});
        turnMap.put('v', new char[]{'<', '>', '^'});
    }

    private boolean canMove(Maze maze, int xDir, int yDir) {
        return (maze.getMaze()[getX() + xDir][getY() + yDir] != 1);
    }

    void move(Maze maze) {

        for(int i = 0; i < maxStep; i++) {
            //System.out.println();
            //System.out.println(maze);

            if(location.equals(maze.getExitLocation())) return;

            if (canMove(maze, moveMap.get(dir)[0], moveMap.get(dir)[1])) {
                maze.getMaze()[getX()][getY()] = 0;
                maze.getMaze()[getX() + moveMap.get(dir)[0]][getY() + moveMap.get(dir)[1]] = 3;
                location = new Point((int)location.getX() + moveMap.get(dir)[0], (int)location.getY() + moveMap.get(dir)[1]);
            }
            else {
                turn(maze);
                maze.getMaze()[getX()][getY()] = 0;
                maze.getMaze()[getX() + moveMap.get(dir)[0]][getY() + moveMap.get(dir)[1]] = 3;
                location = new Point((int)location.getX() + moveMap.get(dir)[0], (int)location.getY() + moveMap.get(dir)[1]);
            }
        }
        turn(maze, 2);
    }

    private void turn(Maze maze) {
        turn(maze, -1);
    }

    private void turn(Maze maze, int turn) {
        int i;
        if(turn != -1) i = turn;
        else i = 0;
        for(; i < 3; i++) {
            char newDir = turnMap.get(dir)[i];

            if(canMove(maze, moveMap.get(newDir)[0], moveMap.get(newDir)[1])) {
                dir = newDir;
                if(i==2) turnCounter+=1;
                else turnCounter = 0;
                return;
            }
        }
    }

    Point getLocation() {
        return location;
    }

    private int getX() {
        return (int)location.getX();
    }

    private int getY() {
        return (int)location.getY();
    }

    public String toString() {
        return Character.toString(dir);
    }

    public int getTurnCounter() {
        return turnCounter;
    }
}
