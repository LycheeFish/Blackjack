import java.util.*;

public class OptimalRun{
    public static void main(String[] args){
        double[][] arr = new double[10][10];
        for (int i = 0; i<10; i++) {
            for (int j = 0; j<10; j++) {
                arr[i][j] = 0;
            }
        }

        double[][] arrHasAce = new double[10][10];
        for (int i = 0; i<10; i++) {
            for (int j = 0; j<10; j++) {
                arr[i][j] = 0;
            }
        }

        for (int i = 0; i<1000000; i++) {
            int x = 0;
            int y = 0;
            int AorNA = 0;
            //1 = has ace , 0 = has no ace

            ArrayList<Integer> a = Optimal.InitializeGame(arr, arrHasAce);
            ArrayList<Double> b = new ArrayList<>();
            for (int k = 0; k<a.size(); k++) {
                double Aint = a.get(k);
                b.add(Aint);
            }
            for (int j = 0; j<a.size(); j++) {
                if (j%4 == 1) {
                    y = a.get(j)-1;
                } else if (j%4 == 2) {
                    if (a.get(j) == 1) {
                        AorNA = 1;
                    } else {
                        AorNA = 0;
                    }
                } else if (j%4 == 3){
                    if (AorNA == 0){
                        if (x >= 0) {
                            arr[x][y] = arr[x][y] + ((b.get(j) - arr[x][y])/10);
                        }
                    } else {
                        if (x>=0){
                            arrHasAce[x][y] = arrHasAce[x][y] + ((b.get(j) - arrHasAce[x][y])/10);
                        }
                    }
                } else if (j%4 == 0){
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

        for (int i = 0; i<10; i++) {
            for (int j = 0; j<10; j++) {
                if (j == 9) {
                    System.out.println(arrHasAce[i][j] + " ; ");
                } else {
                    System.out.print(arrHasAce[i][j] + " ");
                }
            }
        }

    }

}
