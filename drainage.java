import java.util.*;



class drainage {
    public static boolean isDigit(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

    public static void printMatrix(int[][] mat) {
        for (int[] row : mat)
            System.out.println(Arrays.toString(row));

    }

    
    public static int drainagePath(int[][] mat) {
        // empty matrix
        if(mat.length == 0) {
            return 0;
        }

        // create memo cache
        int k = mat.length;
        int l = mat[0].length;
        int[][] memo = new int[k][l];

        // call dfs on every cell of matrix
        int retval = 0;
        for(int a = 0; a < k; a++) {
            for(int b = 0; b < l; b++) {
                retval = Math.max(retval, depthFirstSearch(a, b, mat, memo));
    
            }
        }

        return retval;
    }

    public static int depthFirstSearch(int i, int j, int[][] mat, int[][] memo) {
        // check bounds
        if(i < 0 || i >= mat.length || j < 0 || j >= mat[0].length) {
            return 0;
        }

        // return memoized
        if(memo[i][j] != 0) {
            return memo[i][j];
        }

        int currMax = 1;

        for(int a = 0; a < 4; a++) {
            //check up
            if(i > 0 && a == 0 && mat[i][j] > mat[i-1][j] ){
                currMax = Math.max(currMax, 1+ depthFirstSearch(i-1, j, mat, memo));
            }

            //check right
            if(j < mat.length-1 && a == 1 && mat[i][j] > mat[i][j+1] ){
                currMax = Math.max(currMax, 1+ depthFirstSearch(i, j+1, mat, memo));
            }

            //check down
            if(i < mat.length-1 && a == 2 && mat[i][j] > mat[i+1][j]){
                currMax = Math.max(currMax, 1+ depthFirstSearch(i+1, j, mat, memo));            
            }

            //check left
            if(j > 0 && a == 3 && mat[i][j] > mat[i][j-1] ){
                currMax = Math.max(currMax, 1+ depthFirstSearch(i, j-1, mat, memo));           
            }


        }
        //set memoized cell
        memo[i][j] = currMax;
        return currMax;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        
        String[] first = scanner.nextLine().split(" ");
        ArrayList<String> townNames = new ArrayList<String>();
        townNames.add(first[0]);


        int[][] currTown = new int[Integer.parseInt(first[1])][Integer.parseInt(first[2])];
        ArrayList<int[][]> towns = new ArrayList<int[][]>();
        int row = 0;
        
        while(scanner.hasNextLine()) {
            
            String in = scanner.nextLine();
            String[] line = in.split(" ");

            // check if new matrix
            if(!isDigit(line[0])) {

                // if new matrix, reset row count, add town name to list, add town matrix to list
                row = 0;
                townNames.add(line[0]);
                towns.add(currTown);
                if(line[0] == "") { // if end of file
                    break;
                } else { // reset matrix
                    currTown = new int[Integer.parseInt(line[1])][Integer.parseInt(line[2])];
                }
                

            } else {
                for(int i = 0; i < line.length; i++) { // add row to matrix
                    currTown[row][i] = Integer.parseInt(line[i]);
                }
                row++;
            }

        }
        
        for(int i = 0; i < towns.size(); i++) {
            System.out.println(townNames.get(i) + ": " + drainagePath(towns.get(i)));
            
        }
        scanner.close();
    }
}

