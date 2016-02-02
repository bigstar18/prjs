package gnnt.MEBS.transformhq.server.test;

import java.io.PrintStream;

public class test
{
  public static void main(String[] args)
  {
    int[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 49, 51 };
    int sum = 100;
    

    int i = (arr.length - 1) / 2;
    int j = i + 1;
    while ((i >= 0) && (j < arr.length))
    {
      System.out.println("~~~~~" + arr[i] + ":" + arr[j]);
      if (arr[i] + arr[j] == sum) {
        System.out.println(arr[i] + " + " + arr[j] + " = " + sum);
      } else if (arr[i] + arr[j] > sum) {
        i--;
      } else {
        j++;
      }
    }
    System.out.println("未能找到!");
  }
}
