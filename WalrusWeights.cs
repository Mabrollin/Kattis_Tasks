using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Kattis_tasks
{
    class Walrus
    {
        private int[] combinations = new int[2000];
        public Walrus()
        {
            for (int i = 0; i < 2000; i++)
                combinations[i] = 0;
        }
        public void addWeight(int newWeight)
        {
            for (int i = combinations.Length - 1; i >= 0; i--)
            {
                if (combinations[i] != 0)
                {
                    if (combinations[i] + newWeight < 2000)
                    {
                        combinations[combinations[i] + newWeight] = combinations[i] + newWeight;
                    }
                }

            }

            combinations[newWeight] = newWeight;

        }



        public int getClosest()
        {
            int closestMarginal = 1000;
            int closestValue = 0;
            foreach (int combination in combinations)
            {
                if (Math.Abs(1000 - combination) < closestMarginal)
                {
                    closestMarginal = Math.Abs(1000 - combination);
                    closestValue = combination;
                }
                if (Math.Abs(1000 - combination) == closestMarginal && combination > closestValue)
                {
                    closestMarginal = Math.Abs(1000 - combination);
                    closestValue = combination;
                }

            }
            return closestValue;
        }

        static void Main(string[] args)
        {
            Walrus walrus = new Walrus();
            int nrOfWeights = Int32.Parse(Console.ReadLine());
            for (int i = 0; i < nrOfWeights; i++)
            {
                walrus.addWeight(Int32.Parse(Console.ReadLine()));
            }
            Console.WriteLine(walrus.getClosest());

        }
    }
}
