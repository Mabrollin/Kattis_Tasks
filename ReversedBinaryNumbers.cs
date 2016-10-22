using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Kattis_tasks
{
    class ReversedBinaryNumbers
    {
        public static int reverse(int decimalNumber)
        {
            return reverse(decimalNumber, 0);
        }
        private static int reverse(int decimalNumber, int sum)
        {
            sum = sum * 2 + decimalNumber % 2;
            decimalNumber /= 2;
            if (decimalNumber == 0)
                return sum;
            else
                return reverse(decimalNumber, sum);
        }
        static void Main(string[] args)
        {
            int input = Int32.Parse(Console.ReadLine());
            Console.WriteLine(ReversedBinaryNumbers.reverse(input));
        }

    }
}
