package Project1_170;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class play {
    private int start_row;
    private int start_col;
    private int count_food,round = 1,food=1;
    private String input;
    List<List<Character>> maze = new ArrayList<>();
    play(int s_row, int s_col, List<List<Character>> maze, int food){
        this.start_row = s_row;
        this.start_col = s_col;
        this.maze = maze;
        this.count_food = food;
    }
    public void play_game() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("User input " + round + " >> Enter move " );
            System.out.println("(U = up, D = down, L = left, R = right, A = Auto)");
            System.out.print("Input >> :");
            input = sc.nextLine();
            round ++;
            int row_2;
            int col_2;
            if (Objects.equals(input, "R") || Objects.equals(input, "r")) {
                row_2 = start_row;
                col_2 = start_col + 1;
                swap_position(row_2, col_2);
            }
            if (Objects.equals(input, "L") || Objects.equals(input, "l")) {
                row_2 = start_row;
                col_2 = start_col - 1;
                swap_position(row_2, col_2);
            }
            if (Objects.equals(input, "U") || Objects.equals(input, "u")) {
                row_2 = start_row - 1;
                col_2 = start_col;
                swap_position(row_2, col_2);
            }
            if (Objects.equals(input, "D") || Objects.equals(input, "d")) {
                row_2 = start_row + 1;
                col_2 = start_col;
                swap_position(row_2, col_2);
            }
            if(Objects.equals(input,"A") || Objects.equals(input,"a")) {
                while (count_food != 0) {
                    Deque<String> path = new ArrayDeque<>();
                    boolean foundPath = false; // Flag to track if a path to food is found
                    System.out.println("Rat path ->");
                    System.out.println("Start -> (row " + start_row + ", col " + start_col + ", R)");
                    for (int i = 0; i < maze.size(); i++) {
                        for (int j = 0; j < maze.get(i).size(); j++) {
                            if (maze.get(i).get(j) == 'F') {
                                if (findPath(start_row, start_col, i, j, path)) {
                                    while (!path.isEmpty()) {
                                        String poppedElement = path.pop();
                                        System.out.println("" + poppedElement);
                                    }
                                    System.out.println();
                                    System.out.println("==== Finding Food " + food + " ====");
                                    food++;

                                    start_row = i;
                                    start_col = j;
                                    printMaze(maze, Project1.count);
                                    System.out.println();
                                    count_food--;
                                    foundPath = true; // Set flag to true indicating path found
                                    if(count_food==0) Project1.main(null);
                                    break; // Exit inner loop
                                }
                            }
                        }
                        if (foundPath) break; // Exit outer loop if path found
                    }
                    if (!foundPath) {
                        System.out.println();
                        System.out.println("No solution !!!!");
                        System.out.println();
                        Project1.main(null);
                    }
                }
            }
        }
    }
    public void swap_position(int row_2, int col_2) {
        if (check(row_2, col_2)) {
            if (maze.get(row_2).get(col_2) != '0' && maze.get(row_2).get(col_2) != 'F') {
                char temp = maze.get(start_row).get(start_col);
                maze.get(start_row).set(start_col, maze.get(row_2).get(col_2));
                maze.get(row_2).set(col_2, temp);
                printMaze(maze, Project1.count);
                start_row = row_2;
                start_col = col_2;
            } else {
                System.out.println("Cannot move\n");
            }
            if (maze.get(row_2).get(col_2) == 'F') {
                maze.get(start_row).set(start_col, '1');
                maze.get(row_2).set(col_2, 'R');
                printMaze(maze, Project1.count);
                start_row = row_2;
                start_col = col_2;
                System.out.println("+++++ Find Food +++++");
                food++;
                printMaze(maze, Project1.count);
                count_food--;
                if (count_food == 0) {
                    System.out.println("---------------------------------------");
                    Project1.main(null);
                }
            }
        } else {
            System.out.println("Cannot move");
        }
    }
    public boolean check(int row, int col) {
        return row >= 0 && row < maze.size() && col >= 0 && col < maze.get(row).size();
    }
    public boolean findPath(int row, int col, int endRow, int endCol, Deque<String> path) {
        if (row == endRow && col == endCol && maze.get(row).get(col)=='F' ) {
            maze.get(row).set(col, 'R');
            path.push("End   -> (row " + row + ", col " + col  + ", F)" );
            return true;
        }
        //Condition to find food
        if ( check(row, col) && maze.get(row).get(col) != '0') {
            char originalCell = maze.get(row).get(col);
            maze.get(row).set(col, '0'); // Mark the current cell as visited
            if(findPath(row + 1, col, endRow, endCol, path)){
                if (originalCell != 'F') maze.get(row).set(col, '1'); // Unmark the current cell if it's not food
                path.push("Down  -> (row " + (row+1) + ", col " + col + ")");
                return true;
            }
            if(findPath(row, col+1, endRow, endCol, path)){
                if (originalCell != 'F') maze.get(row).set(col, '1'); // Unmark the current cell if it's not food
                path.push("Right -> (row " + row + ", col " + (col+1) + ")");
                return true;
            }
            if(findPath(row - 1, col, endRow, endCol, path)){
                if (originalCell != 'F') maze.get(row).set(col, '1'); // Unmark the current cell if it's not food
                path.push("Up    -> (row " + (row-1) + ", col " + col + ")");
                return true;
            }
            if(findPath(row ,col-1, endRow, endCol, path)){
                if (originalCell != 'F') maze.get(row).set(col, '1'); // Unmark the current cell if it's not food
                path.push("Left  -> (row " + row + ", col " + (col-1)+ ")");
                return true;
            }
            maze.get(row).set(col, originalCell); // Unmark the current cell if the path is not found
        }
        return false;
    }
    public static void printMaze(List<List<Character>> maze,int count) {
        int round = 0;
        System.out.print("        ");
        for (int i=0; i<count; i++){
            System.out.print("col_"+i+"  ");
        }
        System.out.println();
        for (List<Character> row : maze) {
            System.out.print("row_"+ round +"     ");
            round++;
            for (char cell : row) {
                System.out.print(cell + "      ");
            }
            System.out.println();  // Move to the next line after printing each row
        }
    }

}






