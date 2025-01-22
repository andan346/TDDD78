package se.liu.andan346.lab1;

public class Exercise6
{
    public static void main(String[] args) {
	for (int i = 2; i < 100; i++) {
	    if (isPrime(i)) System.out.println(i);
	}
    }

    public static boolean isPrime(int n) {
	for (int i = 2; i < n; i++) {
		if (n % i == 0) return false;
	}
	return true;
    }
}
