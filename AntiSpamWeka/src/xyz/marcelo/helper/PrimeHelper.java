package xyz.marcelo.helper;

public class PrimeHelper
{
    private static Integer[] primes;

    public static Integer getNthPrime(int n)
    {
        if (primes.length < n)
        {
            initializePrimesArray(n);
        }

        return primes[n];
    }

    public static void initializePrimesArray(int amount)
    {
        primes = new Integer[amount];

        int n = 0, i = 0;

        do
        {
            if (isPrime(n))
            {
                primes[i] = n;
                i++;
            }

            n++;

        } while (i < amount);
    }

    private static boolean isPrime(int n)
    {
        if (n <= 1 || (n != 2 && (n % 2) == 0))
        {
            return false;
        }
        else if (n == 2)
        {
            return true;
        }
        else
        {
            for (int i = 3; i * i <= n; i += 2)
            {
                if ((n % i) == 0)
                {
                    return false;
                }
            }
            return true;
        }
    }
}
