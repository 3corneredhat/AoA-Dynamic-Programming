/**
 * CS3433: Analysis of Algorithms
 * Project 3: Dynamic Programming
 * Name: Alan Cabrera
 * abc123: xex967
 * Date: 11/18/2021
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author xex967
 */
public class MaximizeBudget {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        File itemData = new File("items.csv"); // The items data file.
        ArrayList<Valuable> valuableList = new ArrayList<Valuable>(); // Structure that will hold the Valuable objects.
       
        /**
         * Read the data, populate the structure with it, and initialize the variables.
         */
        loadArray(itemData, valuableList);
        int numItems = valuableList.size();//Will consider all items in the input file, can also be hardcoded to consider only n items.
        int budget = 100; //Can be hardcoded to another value. 
        int [][] table = new int[numItems+1][budget+1];
        
        /*
         * Get the optimal solution for the problem.
         */
        int solutionValue = pickValuable(table, valuableList, numItems, budget);

        /**
         * Print the optimal value and then print the itmes that contributed to the solution.
         */
        System.out.printf("Optimal Value: %d\n", solutionValue);
        tableTraceback(table, valuableList, numItems, budget);
    }

    /**
     * This function will read the store data from a file, create Valuable objects, and
     * add them to an arraylist.
     * 
     * @param file         The input file
     * @param valuableList The structure that will hold the Valuable objects.
     */
    public static void loadArray(File file, ArrayList<Valuable> valuableList) {

        // Create store objects and add to list.
        try {
            BufferedReader input = new BufferedReader(new FileReader(file));
            String line = input.readLine();// Read first line to skip the table header.

            while ((line = input.readLine()) != null) {
                String[] data = line.split(","); //Use a comma as a delimiter. 
                Valuable valuable = new Valuable(data);
                valuableList.add(valuable);
            }
            input.close();
        } catch (Exception ex) {
            System.out.println("Error reading the file.");
        }
    }
    /**
     * Will populate the 2d array/table with the solutions to subproblems. 
     * @param table The array that will hold solutions to subproblems. 
     * @param valuableList The structure that holds the Valuable objects.
     * @param n The number of items being considered. 
     * @param budget The limit for the total price we can consider at one time.
     * @return The optimal solution located at [n][budget]
     */
    public static int pickValuable(int [][] table, ArrayList<Valuable> valuableList, int n, int budget){

        for(int i = 0; i < n; i++){//Base case
            table[i][0] = 0;
        }
        for(int j = 0; j < budget; j++){//Base case
            table[0][j] = 0;
        }
        
        int availBudget; //The amount remaining in our budget.
        int currItemPrice; //The price of the item under consideration.
        int totalSansCurr; //The total value of items not including the current item for consideration.
        int totalInclCurr; //The total value of items including the current item under consideration.
        
        for(int i = 1; i <= n; i++){//index for the item being considered.
            for(int j = 1; j <= budget; j++){ //index for the current budget considered.
                
                currItemPrice = valuableList.get(i-1).getPrice();//List indexing starts at 0.
                totalSansCurr = table[i-1][j];//Entry above the current index (our total value for the item before the current item considered.)
                totalInclCurr = 0; //Will place a 0 if an item isn't included. 
                
                /**
                 * If the The current item price (not value) is less than the budget being considered, then we calculate the running
                 * total of item values that include the value of the item we are currently considering.
                 */
                if(currItemPrice <= j){
                    totalInclCurr = valuableList.get(i-1).getValue();//Get the value of the current item(not price).
                    availBudget = j - currItemPrice; //The budget(index) afer subtracting the price of the current item from the current budget considered.                     
                    totalInclCurr = totalInclCurr + table[i-1][availBudget];//Calculate the total value with the value of the current item.
                }

                /**
                 * Find the maximum value between the running total of values that include the value of the current item considered and the total without.
                 * Will arbitrarily place the running total value of items up to the previous item in the table for the current item when the price of the 
                 * current item is greater than the budget.
                 */
                table[i][j] = Math.max(totalInclCurr, totalSansCurr);
                
            }
        }
        return table[n][budget];//Return the optimal solution.
    }

    /**
     * Works backwards from the index of the optimal solution to find and print that values
     * that constributed to it.
     * @param table The array that will hold solutions to subproblems. 
     * @param valuableList The structure that holds the Valuable objects.
     * @param i The index for the item being considered.
     * @param j The index for the budget being considered.
     */
    public static void tableTraceback(int[][]table,ArrayList<Valuable> valuableList, int i, int j){
        
        if( i == 0 )//Base case
            return;
        
        int prevTotalVal = table[i-1][j];//The running total value for all items before the current item.
        int currTotalVal = table[i][j]; //The running total value for all items including the current item.

        /**
         * Checks the running total value for all items before the current item. If the running total of values for all 
         * previous items is less than the total not including the current, then we find the previous item. Else, we don't
         * need the current item, move up a row. 
         */
        if(prevTotalVal< currTotalVal){
            
            int prevBudget = j - valuableList.get(i-1).getPrice();//The next budget (index) to consider.

            tableTraceback(table, valuableList, i-1, prevBudget);//Find the value that contributes to the optimal solution.
            System.out.println(valuableList.get(i-1).getDescription());//Print the current item description. 
        }

        else
            tableTraceback(table, valuableList, i-1,j);
            
    }
}
