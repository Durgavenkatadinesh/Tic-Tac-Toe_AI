import java.io.*;
import java.util.*;
public class Main
{
    static BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
    static Scanner sc=new Scanner(System.in);

    static int COMPUTER=1;
    static int HUMAN=2;
    static int  SIDE=3;
    static char COMPUTERMOVE='O';
    static char HUMANMOVE='X';

    static void showBoard(char board[][]){
        System.out.print(String.format("\t\t\t %c | %c | %c \n",board[0][0],board[0][1],board[0][2]));
        System.out.print("\t\t\t-------------\n");
        System.out.print(String.format("\t\t\t %c | %c | %c \n",board[1][0],board[1][1],board[1][2]));
        System.out.print("\t\t\t-------------\n");
        System.out.print(String.format("\t\t\t %c | %c | %c \n",board[2][0],board[2][1],board[2][2]));

    }

    static void showInstructions(){
        System.out.println("\nchoose a cell numbered from 1 to 9 as below and play\n\n");
        System.out.print("\t\t\t 1 | 2 | 3 \n");
        System.out.print("\t\t\t-----------\n");
        System.out.print("\t\t\t 4 | 5 | 6 \n");
        System.out.print("\t\t\t-----------\n");
        System.out.print("\t\t\t 7 | 8 | 9 \n");
    }

    static void initialize( char board[][]){
        for(int i=0;i<SIDE;i++){
            for(int j=0;j<SIDE;j++){
                board[i][j]='*';
            }
        }
    }

    static void declareWinner(int whoseTurn){
        if(whoseTurn == COMPUTER)
            System.out.println("COMPUTER has won");
        else
            System.out.println("HUMAN has won");
    }

    static boolean rowCrossed(char board[][]){
        for(int i=0;i<SIDE;i++){
            if(board[i][0] == board[i][1] &&
                    board[i][1] == board[i][2] &&
                    board[i][0] != '*')
                return (true);
        }return (false);
    }

    static boolean columnCrossed(char board[][]){
        for(int i=0;i<SIDE;i++){
            if(board[0][i] == board[1][i] &&
                    board[1][i] == board[2][i] &&
                    board[0][i] != '*')
                return (true);
        }return (false);
    }

    static boolean diagnolCrossed(char board[][]){
        if(board[0][0] == board[1][1] &&
                board[1][1] == board[2][2] &&
                board[0][0] != '*')
            return true;
        if(board[0][2] == board[1][1] &&
                board[1][1] == board[2][0] &&
                board[0][2] != '*')
            return true;
        return false;
    }

    static boolean gameOver(char board[][]){
        return(rowCrossed(board) || columnCrossed(board) || diagnolCrossed(board));
    }
    static int minimax(char board[][],int depth,boolean isAI){
        int score=0;
        int bestScore=0;
        if(gameOver(board)==true){
            if(isAI==true)
                return -10;
            if(isAI==false)
                return +10;
        }
        else{
            if(depth<9){
                if(isAI==true){
                    bestScore=-9999;
                    for(int i=0;i<SIDE;i++){
                        for(int j=0;j<SIDE;j++){
                            if(board[i][j]=='*'){
                                board[i][j]=COMPUTERMOVE;
                                score=minimax(board,depth+1,false);
                                board[i][j]='*';
                                if(score>bestScore){
                                    bestScore=score;
                                }
                            }
                        }
                    }return bestScore;
                }
                else{
                    bestScore=9999;
                    for(int i=0;i<SIDE;i++){
                        for(int j=0;j<SIDE;j++){
                            if(board[i][j]=='*'){
                                board[i][j]=HUMANMOVE;
                                score=minimax(board,depth+1,true);
                                board[i][j]='*';
                                if(score<bestScore){
                                    bestScore=score;
                                }
                            }
                        }
                    }return bestScore;
                }
            }
            else {
                return 0;
            }
        }return 0;
    }
    static int bestMove(char board[][],int moveIndex){
        int x=-1,y=-1;
        int score=0,bestScore=-9999;
        for(int i=0;i<SIDE;i++){
            for(int j=0;j<SIDE;j++){
                if(board[i][j]=='*'){
                    board[i][j]=COMPUTERMOVE;
                    score=minimax(board,moveIndex+1,false);
                    board[i][j]='*';
                    if(score>bestScore){
                        bestScore=score;
                        x=i;
                        y=j;
                    }
                }
            }
        }return x*3+y;
    }

    static void playtictactoe(int whoseTurn) throws IOException{

        char board[][]=new char[SIDE][SIDE];
        int moveIndex=0,x=0,y=0;

        initialize(board);
        showInstructions();

        while(gameOver(board) == false && moveIndex!=SIDE*SIDE){
            int n;
            if(whoseTurn==COMPUTER){
                n=bestMove(board,moveIndex);
                x=n/SIDE;
                y=n%SIDE;
                board[x][y]=COMPUTERMOVE;
                System.out.println("COMPUTER has put a "+ COMPUTERMOVE+" in cell "+(n+1)+"\n");
                showBoard(board);
                moveIndex++;
                whoseTurn=HUMAN;
            }
            else if(whoseTurn == HUMAN){
                System.out.print("You can insert int following positions : ");
                for(int i=0;i<SIDE;i++){
                    for(int j=0;j<SIDE;j++){
                        if(board[i][j]=='*') System.out.print((i*3+j)+1 +" ");
                    }
                }
                System.out.print("\n\nEnter the position = ");
                n=Integer.parseInt(br.readLine());
                n--;
                x=n/SIDE;
                y=n%SIDE;
                if(board[x][y]=='*' && n<9 && n>=0){
                    board[x][y]=HUMANMOVE;
                    System.out.println(String.format("\nHUMAN has put a %c in cell%d\n",HUMANMOVE,n+1));
                    showBoard(board);
                    moveIndex++;
                    whoseTurn=COMPUTER;

                }
                else if(board[x][y] != '*' && n<9 && n>=0){
                    System.out.println("position already completed , choose another");
                }
                else if(n<0 || n>8) System.out.println("INVALID position");
            }
        }
        if(gameOver(board)==false && moveIndex==SIDE*SIDE) System.out.println("*** It's a Draw ***");
        else{
            if(whoseTurn == COMPUTER) whoseTurn=HUMAN;
            else if(whoseTurn ==HUMAN) whoseTurn = COMPUTER;

            declareWinner(whoseTurn);
        }
    }

    public static void main(String[] args) throws IOException {

        System.out.println("\n---------------------------------------------------------\n\n");
        System.out.println("\t\t\t Tic-Tac-Toe\n");
        System.out.println("\n---------------------------------------------------------\n\n");
        char c='y';
        do{
            char choice;
            System.out.println("Do you want to start first?(y/n) : ");
            String ss=sc.next();
            choice=ss.charAt(0);
            if(choice=='n')
                playtictactoe(COMPUTER);
            else if(choice=='y')
                playtictactoe(HUMAN);
            else
                System.out.println("invalid choice\n");
            System.out.println("\n Do you want to quit(y/n) : ");
            String sss=br.readLine();
            c=sss.charAt(0);
        } while(c=='n');
        System.exit(0);
    }
}