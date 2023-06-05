import java.util.Random;
import java.util.Scanner;

class TicTacToe_minMax1
{
    static class Move
    {
        int row, col;
    };

    char board [][];

    static char player = 'x', opponent = 'o';

    char tura;

    boolean lose=false;

    public TicTacToe_minMax1() {
        this.board = new char[3][3];

        for(int i=0; i<3; i++)
        {
            for(int j=0; j<3; j++)
            {
                board[i][j]='_';
            }
        }
    }

    public void show(){

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                System.out.print("|"+board[i][j]+"|" + " ");
            }
            System.out.println();
        }
    }

    public void win() {

        if (board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] != '_') {
            lose = true;
            return;
        }
        if (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] != '_') {
            lose = true;
            return;
        }
        for (int i = 0; i < board.length; i++) {
            if (board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] != '_') {
                lose = true;
                return;
            }
            if (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] != '_') {
                lose = true;
                return;
            }
        }
    }

    public void play(){

        Random rand = new Random();
        int mov = rand.nextInt(2);

        switch(mov) {
            case 0:
                tura = player;
                break;

            case 1:
                tura = opponent;
                break;
        }

        Scanner scan = new Scanner(System.in);
        int pozycja;
        Move mov1 = new Move();

        for(int i=0; i<9 && lose==false; i++)
        {
            if(tura==player){
                System.out.println("\nChoose your move [1-9] :");

                while(true)
                {
                    pozycja=scan.nextInt()-1;
                    if(pozycja>=0 && pozycja<9 && board[pozycja/3][pozycja%3]=='_'){
                        board[pozycja/3][pozycja%3]=tura;
                        tura=opponent;
                        show();
                        win();
                        if(lose){
                            System.out.println("\nGame over \nYou lost");
                        }
                        break;
                    }
                }
            }
            else{
                mov1=findBestMove(board);
                board[mov1.row][mov1.col]=tura;
                tura = player;
                show();
                win();
                if(lose){
                    System.out.println("\nYou won!");
                }
            }
            if(i==8 && lose==false){
                System.out.println("\nGG\nDraw");
            }
        }
    }

    static Boolean isMovesLeft(char board[][])
    {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == '_')
                    return true;
        return false;
    }

    static int evaluate(char b[][])
    {

        for (int row = 0; row < 3; row++)
        {
            if (b[row][0] == b[row][1] &&
                    b[row][1] == b[row][2])
            {
                if (b[row][0] == player)
                    return +10;
                else if (b[row][0] == opponent)
                    return -10;
            }
        }

        for (int col = 0; col < 3; col++)
        {
            if (b[0][col] == b[1][col] &&
                    b[1][col] == b[2][col])
            {
                if (b[0][col] == player)
                    return +10;

                else if (b[0][col] == opponent)
                    return -10;
            }
        }

        if (b[0][0] == b[1][1] && b[1][1] == b[2][2])
        {
            if (b[0][0] == player)
                return +10;
            else if (b[0][0] == opponent)
                return -10;
        }
        if (b[0][2] == b[1][1] && b[1][1] == b[2][0])
        {
            if (b[0][2] == player)
                return +10;
            else if (b[0][2] == opponent)
                return -10;
        }
        return 0;
    }

    static int minimax(char board[][],
                       int depth, Boolean isMax)
    {
        int score = evaluate(board);

        if (score == 10)
            return score;
        if (score == -10)
            return score;
        if (isMovesLeft(board) == false)
            return 0;
        if (isMax)
        {
            int best = -1000;

            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    if (board[i][j]=='_')
                    {
                        board[i][j] = opponent;
                        best = Math.max(best, minimax(board,
                                depth + 1, !isMax));
                        board[i][j] = '_';
                    }
                }
            }
            return best;
        }

        else
        {
            int best = 1000;

            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    if (board[i][j] == '_')
                    {
                        board[i][j] = player;
                        best = Math.min(best, minimax(board,
                                depth + 1, !isMax));
                        board[i][j] = '_';
                    }
                }
            }
            return best;
        }
    }

    static Move findBestMove(char board[][])
    {
        int bestVal = -1000;
        Move bestMove = new Move();
        bestMove.row = -1;
        bestMove.col = -1;

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (board[i][j] == '_')
                {
                    board[i][j] = opponent;

                    int moveVal = minimax(board, 0, false);

                    board[i][j] = '_';
                    if (moveVal > bestVal)
                    {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }

        System.out.printf("\nThe value of the best Move " +
                "is : %d\n\n", bestVal);

        return bestMove;
    }

    public static void main(String[] args)
    {
        System.out.printf("\nPrzegrywa ten, kto umieści swoje 3 znaki w jednej linii\nPowodzenia!\n");
        TicTacToe_minMax1 nazwa = new TicTacToe_minMax1();

        nazwa.play();

    }

}