import java.util.Scanner;
public class DoubleTrouble {
    private static int[] COLORS = {3, 5, 7}; // sets the colors
    private static int TURN = 3; // TURN changes on a round-by-round basis
    private static final int USER_TURN = 1, COMP_TURN = 0, GREEN = 0, ORANGE =  1, YELLOW = 2; // does not change

    /********************************************************************************************
     * The name of the game is NIMS! The full strategy for Nim was discovered
     * by the mathematician Charles Bouton in 1902. Westinghouse Electrical Corporation
     * built the first Nim playing computer in 1940 and exhibited it at the New York Worlds Fair!
     ********************************************************************************************/
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printNims();
        boolean validInput = false;
        System.out.println("Hello! Welcome to a game of NIMS! Do you want to go first (1) or the computer (0) to?");

        // makes sure it's a valid input
        while(!validInput){
            TURN = scanner.nextInt();
            if(TURN == 1){ // user
                userMove();
                validInput = true;
            } else if (TURN == 0) { // computer
                computerMove();
                validInput = true;
            } else {
                System.out.println("Not a valid input... try again pleaseeee");
            }
        }

        // play game!
        while(!isGameFinished()){
            printNims();
            if(TURN == USER_TURN){
                System.out.println("Your turn! ");
                userMove();
            } else {
                System.out.println("Computer's turn! ");
                computerMove();
            }
        }

        // because even if you won at the end of your userMove(), it will set TURN = COMP_TURN
        if(TURN == COMP_TURN) {
            System.out.println("YOU WON!!!!!!!!!!!!!!!!!");
        } else {
            System.out.println("Computer won... try again!!");
        }
    }

    /**
     * Prompts a color number and how many you want to take for your move
     */
    private static void userMove(){
        Scanner scanner = new Scanner(System.in);
        int colors, numTake;

        System.out.println("Which color do you want to take from? Enter a number\n 1. Green\t2. Orange\t3.Yellow");
        colors = scanner.nextInt();

        //making sure it's a valid color
        boolean isColorValid = false;
        while(!isColorValid){
            if(colors < 1 || colors > 3 || COLORS[colors-1] == 0){
                System.out.println("Choose again! ");
                colors = scanner.nextInt();
            } else {
                isColorValid = true;
            }
        }

        // making sure it's a valid input
        boolean isInputValid = false;
        while(!isInputValid){
            System.out.println("How many do you want to take?");
            numTake = scanner.nextInt();

            if((numTake <= COLORS[colors - 1]) && (numTake > 0)){
                COLORS[colors-1] -= numTake;
                isInputValid = true;
            } else {
                System.out.println("NOT A VALID NUMBER, TRY AGAIN!!");
            }
        }
        TURN =  COMP_TURN;
    }

    /**
     * Decides whether it makes a calculated computer move or random computer move
     */
    private static void computerMove(){
        int nimSum = COLORS[GREEN] ^ COLORS[ORANGE] ^ COLORS[YELLOW];

        // there's a strategy
        if(nimSum != 0) {
            winningStrat(nimSum);
        } else { // there's not a strategy
            randomStrat();
        }
        TURN = USER_TURN;
    }

    /**
     * Makes a calculated game move
     */
    private static void winningStrat(int nimSum) {
        int xorAB = COLORS[YELLOW] ^ COLORS[ORANGE];
        int xorAC = COLORS[YELLOW] ^ COLORS[GREEN];
        int xorBC = COLORS[GREEN] ^ COLORS[ORANGE];

        // Check if C > A ^ B
        if (COLORS[GREEN] > xorAB) {
            int toRemove = COLORS[GREEN] - xorAB;
            COLORS[GREEN] -= toRemove;
            System.out.println("The computer took " + toRemove + " from green.");
        } // Check if B > A ^ C
        else if (COLORS[YELLOW] > xorBC) {
            int toRemove = COLORS[YELLOW] - xorBC;
            COLORS[YELLOW] -= toRemove;
            System.out.println("The computer took " + toRemove + " from yellow.");
        } // Check if A > B ^ C
        else if (COLORS[ORANGE] > xorAC) {
            int toRemove = COLORS[ORANGE] - xorAC;
            COLORS[ORANGE] -= toRemove;
            System.out.println("The computer took " + toRemove + " from orange.");
        } // use random strat
        else {
            randomStrat();
        }
    }

    /**
     * Makes a random game move
     */
    private static void randomStrat() {
        int randColor = (int)(Math.random() * 2);
        int randTake;

        // checking if the color is valid and has something
        while( (randColor == 1 && COLORS[GREEN] == 0) ||
                (randColor == 2 && COLORS[ORANGE] ==0) ||
                (randColor == 3 && COLORS[YELLOW] == 0) ){
            randColor = (int)(Math.random() * 2);
        }

        randTake = (int) (Math.random() * COLORS[randColor]) + 1;

        while(randTake < COLORS[randColor]){
            randTake = (int) (Math.random() * COLORS[randColor]) + 1;
        }
        COLORS[randColor] -= randTake;

        if(randColor == GREEN){
            System.out.println("The computer took " + randTake + " from green randomly.");
        } else if (randColor == ORANGE) {
            System.out.println("The computer took " + randTake + " from orange randomly.");
        } else {
            System.out.println("The computer took " + randTake + " from yellow randomly.");
        }
    }

    /**
     * Prints out the sticks!!
     */
    private static void printNims(){
        String RESET = "\u001B[0m";
        String ORANGE = "\u001B[33m";
        String GREEN = "\u001B[32m";
        String YELLOW = "\u001B[93m";

        // print green
        System.out.print(GREEN + "Green  (" + COLORS[0] + ") : ");
        for(int g = 0; g < COLORS[0]; g++){
            System.out.print(GREEN + " | " + RESET);
        }

        // print oranges
        System.out.print(ORANGE + "\nOrange (" + COLORS[1] + ") : ");
        for(int o = 0; o < COLORS[1]; o++){
            System.out.print(ORANGE + " | " + RESET);
        }

        // print yellow
        System.out.print(YELLOW + "\nYellow (" + COLORS[2] + ") : ");
        for(int y = 0; y < COLORS[2]; y++){
            System.out.print(YELLOW + " | " + RESET);
        }

        System.out.println(RESET);
    }

    /**
     * Returns true or false to indicate if the game is finished or not
     * @return true if the game is finished, or false if not
     */
    private static boolean isGameFinished(){
        return COLORS[GREEN] + COLORS[ORANGE] + COLORS[YELLOW] == 0;
    }
}