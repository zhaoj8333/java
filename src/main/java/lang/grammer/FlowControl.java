package lang.grammer;

import java.util.Scanner;
import java.util.Arrays;

public class FlowControl {
    public static void main(String[] args) {
        // subject1();
        // subject2();
        // subject3();
        // subject4();
        subject5();
        // subject6();
    }

    public static void subject1() {
        int newPirce = 7988;
        int oldPirce = 1500;
        double discount = 0.8;

        double discountPrice = discount * newPirce;
        double exchangePrice = newPirce - oldPirce;

        if (discountPrice - exchangePrice < 0) {
            System.out.println("以旧换新");
        } else {
            System.out.println("不 以旧换新");
        }
    }

    public static void subject2() {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        if (str == "") {
            System.out.println("please input three number");
            System.exit(-1);
        }
        System.out.println(str);
        String[] strs = str.split("\\s+");
        System.out.println(Arrays.toString(strs));

        int a = Integer.parseInt(strs[0]);
        int b = Integer.parseInt(strs[1]);
        int c = Integer.parseInt(strs[2]);
        
        int max = 0;
        if (a > b) {
            max = a;
        } else {
            max = b;
        }
        if (max < c) {
            max = c;
        }
        System.out.println("max numberis : " + max);
    }
    
    public static void subject3() {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        if (str == "") {
            System.out.println("please input the amount of deposit and the duration: ");
            System.exit(-1);
        }
        String[] strs = str.split("\\s+");
        int amount = Integer.parseInt(strs[0]);
        int year   = Integer.parseInt(strs[1]);
        if (amount < 1000) {
            System.out.println("the amount of deposit has to be greater than 1000 ! ");
            System.exit(-1);
        }
        double one = 2.25;
        double two = 2.7;
        double three = 3.25;
        double five  = 3.6;

        double result = 0;
        if (year == 1) {
            result = amount * one / 100;
        } else if (year == 2) {
            result = amount * two / 100 * 2;
        } else if (year == 3) {
            result = amount * three / 100 * 3;
        } else if (year == 5) {
            result = amount * five / 100 * 5;
        } else {
            System.out.println("unknown");
        }

        result += amount;
        System.out.println("all capital and interest: " + result);
    }
    
    public static void subject4() {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        if (str == "") {
            System.out.println("please input type of customers, all amount");
            System.exit(-1);
        }
        System.out.println(str);
        String[] strs = str.split("\\s+");
        System.out.println(Arrays.toString(strs));

        int type   = Integer.parseInt(strs[0]);
        int amount = Integer.parseInt(strs[1]);
        
        double discount = 1;
        double payPrice = 0;
        if (type == 0) {
            if (amount > 100) {
                discount = 0.9;
            }
        } else if (type == 1) {
            if (amount < 200) {
                discount = 0.8;
            } else {
                discount = 0.75;
            }
        }
        payPrice = amount * discount;
        System.out.println("最终支付价格： " + payPrice);
    }

    public static void subject5() {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        if (str == "") {
            System.out.println("please input type of customers, all amount");
            System.exit(-1);
        }
        // System.out.println(str);
        String[] strs = str.split("\\s+");
        // System.out.println(Arrays.toString(strs));

        int salary = Integer.parseInt(strs[0]);
        double taxedSalary = salary * 0.9 - 5000;
        double tax  = 0;
        System.out.println("交税的部分：" + taxedSalary);
        if (taxedSalary > 0) {
            if (taxedSalary <= 3000) {
                tax = taxedSalary * 0.03;
            } else if (taxedSalary > 3000 && taxedSalary <= 12000) {
                tax = 3000 * 0.03 
                    + (taxedSalary - 3000 < 0 ? 0 : taxedSalary - 3000) * 0.1;
            } else if (taxedSalary > 12000 && taxedSalary <= 25000) {
                tax = 3000 * 0.03 
                    + (taxedSalary - 3000) * 0.1 
                    + (taxedSalary - 15000 < 0 ? 0 : taxedSalary - 15000) * 0.2;
            } else if (taxedSalary > 25000 && taxedSalary <= 35000) {
                tax = 3000 * 0.03 
                    + (taxedSalary - 3000) * 0.1 
                    + (taxedSalary - 15000) * 0.2 
                    + (taxedSalary - 40000 < 0 ? 0 : taxedSalary - 40000) * 0.25;
            } else if (taxedSalary > 35000 && taxedSalary <= 55000) {
                tax = 3000 * 0.03 
                    + (taxedSalary - 3000) * 0.1 
                    + (taxedSalary - 15000) * 0.2 
                    + (taxedSalary - 40000 < 0 ? 0 : taxedSalary - 40000) * 0.25 
                    + (taxedSalary - 75000 < 0 ? 0 : taxedSalary - 75000) * 0.3;
            } else if (taxedSalary > 55000 && taxedSalary <= 80000) {
                tax = 3000 * 0.03 
                    + (taxedSalary - 3000) * 0.1 
                    + (taxedSalary - 15000) * 0.2 
                    + (taxedSalary - 40000 < 0 ? 0 : taxedSalary - 40000) * 0.25 
                    + (taxedSalary - 75000 < 0 ? 0 : taxedSalary - 75000) * 0.3 
                    + (taxedSalary - 130000 < 0 ? 0 : taxedSalary - 130000) * 0.35;
            } else if (taxedSalary > 80000) {
                tax = 3000 * 0.03 
                    + (taxedSalary - 3000) * 0.1 
                    + (taxedSalary - 15000) * 0.2 
                    + (taxedSalary - 40000) * 0.25 
                    + (taxedSalary - 75000 < 0 ? 0 : taxedSalary - 75000) * 0.3 
                    + (taxedSalary - 130000 < 0 ? 0 : taxedSalary - 13000) * 0.35 
                    + (taxedSalary - 210000 < 0 ? 0 : taxedSalary - 210000) * 0.45;
            }
        }

        System.out.println("税收共计： " + tax);
        System.out.println("税后工资共计： " + ((salary - salary * 0.1) - tax));
    }

    public static void subject6() {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        if (str == "") {
            System.out.println("please input three number");
            System.exit(-1);
        }
        System.out.println(str);
        String[] strs = str.split("\\s+");
        System.out.println(Arrays.toString(strs));

        int a = Integer.parseInt(strs[0]);
        int b = Integer.parseInt(strs[1]);
        int type = Integer.parseInt(strs[2]);
        int result = 0;

        switch (type) {
            case 1:
                result = a * b;
                break;
            case 2:
                result = a - b;
                break;
            case 3:
                result = a * b;
                break;
            case 4: 
                result = 1 / b;
                break;
        }

        System.out.println("result: " + result);
        

    }
}

