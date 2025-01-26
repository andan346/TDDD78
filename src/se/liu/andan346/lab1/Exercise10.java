package se.liu.andan346.lab1;

public class Exercise10
{
    public static void main(String[] args) {
	int number = 16777216;
	System.out.println(number);
	float decimal = number;
	System.out.println(decimal);
	int integerAgain = (int)decimal;
	System.out.println(integerAgain);
	double decimal2 = (double)decimal;
	System.out.println(decimal2);
	int big = 2147483647;
	System.out.println(big);
	int bigger = big+1;
	System.out.println(bigger);
	long bigger2 = (long)bigger;
	System.out.println(bigger2);
	long bigger3 = (long)big+1;
	System.out.println(bigger3);
    }
}
