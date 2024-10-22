package Project1_170;
import java.util.*;
import java.io.*;

public class Project1{
    public static int count=0;
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean opensuccess = false;
        String map;
        String path = "src/main/java/Project1_170/";

        System.out.println("=====                                    Welcome to Rat in a Maize game                                       =====");
        System.out.println("<<<                                               How to play                                                   >>>");
        System.out.println("      In the maize will have a rat 'R' you need to move a rat to find the food ");
        System.out.println("      We have 4 file for you to choose layout of maize 'maize_1.txt' 'maize_2.txt' 'maize_3.txt' 'maize_4.txt'");
        System.out.println("      You need to choose 1 file before play this game Ex. >> Input filename = maize_1.txt");
        System.out.println("      In a game input 'L' to move left, 'R' to move right, 'U' to move up and 'D' to move down");
        System.out.println("      If you are lazy Input 'A' to play auto Good Luck!!!");
        System.out.println();
        System.out.print("Input file name = ");
        map = sc.nextLine();
        String file_name = path+map;

        int count_row=0,count_food=0;
        char temp ;
        int start_row=0,start_col=0;
        int end_row=0, end_col=0;

        List<List<Character>> maze = new ArrayList<>();

        while(!opensuccess) {
            try{
                File file = new File(file_name);
                Scanner filescan = new Scanner(file);
                opensuccess = true;
                System.out.print( "\n");
                while(filescan.hasNext()){
                    List<Character> row = new ArrayList<>();
                    String line = filescan.nextLine();
                    String[] col = line.split(",");
                    count = col.length;
                    for(int i=0;i<count;i++){
                       temp = col[i].trim().charAt(0);
                        if(temp=='R'){
                            start_col = i;
                            start_row = count_row;
                        }
                        if(temp=='F'){
                            end_col = i;
                            end_row = count_row;
                            count_food++;
                        }
                        row.add(temp);
                    }
                    maze.add(row);
                    count_row++;
                }
                play.printMaze(maze,count);

                play play_auto  = new play(start_row, start_col, maze,count_food );
                play_auto.play_game();
                filescan.close();

            }catch (FileNotFoundException e) {
                System.out.println("Not Found File!!!");
                System.out.print("Please Input New file name  = ");
                map = sc.nextLine();
                file_name = path + map;
            }
        }
    }
}


