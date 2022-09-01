package com.codestates.seb.calculator;
import java.util.Scanner;
public class Calculator {
  public static void main(String[] args) {
    System.out.println("===Java Calculator===");

    Scanner input = new Scanner(System.in);
        /*
            요구 사항에 따라 간단한 계산기를 만들어주세요.
            1. 사용자의 입력으로 첫 번째 숫자, 연산자, 두 번째 숫자를 받아야 합니다.
            2. 연산자의 종류는 +, -, *, / 입니다.
            3. 소수점 연산을 수행할 수 있어야 합니다.
            4. 연산 결과를 콘솔에 출력합니다.
        /
    // TODO...

         */

    System.out.println("첫번째 숫자를 입력하세요");
    String str1 = input.nextLine();

    System.out.println("사칙연산 기호중 하나를 입력하세요");
    String op = input.nextLine();

    System.out.println("두번째 숫자를 입력하세요");
    String str2 = input.nextLine();

    double num1 = Double.parseDouble(str1);
    double num2 = Double.parseDouble(str2);
    double num3;

    if(op.equals("+")) {
      num3 = num1 + num2;
    }
    else if(op.equals("-")) {
      num3 = num1 - num2;
    }
    else if(op.equals("/")) {
      num3 = num1 / num2;
    }
    else{
      num3 = num1 *num2;
    }

    System.out.println(str1 + op + str2 + "=" + num3);
  }
}
