import java.util.*;

public class TotalHand{
    public static void main(String[] args) {
        
        double[][] arr = new double[10][10];
        for (int i = 0; i<10; i++) {
            for (int j = 0; j<10; j++) {
                arr[i][j] = 0;
            }
        }

        for (int i = 0; i<1000000; i++) {
            int x = 0;
            int y = 0;
            
            ArrayList<Integer> a = Calculate.InitializeGame();
            ArrayList<Double> b = new ArrayList<>();
            for (int k = 0; k<a.size(); k++) {
                double Aint = a.get(k);
                b.add(Aint);
            }
            for (int j = 0; j<a.size(); j++) {
                if (j%3 == 1) {
                    y = a.get(j)-1;
                } else if (j%3 == 2) {
                    if (x >= 0){
                        arr[x][y] = arr[x][y] + ((b.get(j) - arr[x][y])/10);
                    }
                } else if (j%3 == 0){
                    x = a.get(j) - 12;
                }
            }
        }

        for (int i = 0; i<10; i++) {
            for (int j = 0; j<10; j++) {
                if (j == 9) {
                    System.out.println(arr[i][j] + " ; ");
                } else {
                    System.out.print(arr[i][j] + " ");
                }
            }
        }

    }

}