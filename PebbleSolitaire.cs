using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Kattis_tasks
{
    class PebbleSolitaire
    {
        static int getLeast(string board)
        {
            string newBoard;
            int nrOfPebbles = board.Length - board.Replace("o", "").Length;
            int tempResult = nrOfPebbles;
            int bestResult = tempResult;
            for (int i = 0; i < 21; i++)
            {
                if (board[i] == 'o' && board[i + 1] == 'o' && board[i + 2] == '-')
                {
                    newBoard = board.Substring(0, i) + "--o" + board.Substring(i + 3);
                    tempResult = getLeast(newBoard);
                    if (tempResult < bestResult)
                        bestResult = tempResult;
                }
                if (board[i] == '-' && board[i + 1] == 'o' && board[i + 2] == 'o')
                {
                    newBoard = board.Substring(0, i) + "o--" + board.Substring(i + 3);
                    tempResult = getLeast(newBoard);
                    if (tempResult < bestResult)
                        bestResult = tempResult;
                }
            }
        
            return bestResult;
        }
        static void Main(string[] args)
        {
            int nrOfRuns = Int32.Parse(Console.ReadLine());
            string[] runs = new string[nrOfRuns];
            for(int i = 0; i < nrOfRuns; i++) {
                runs[i] = Console.ReadLine();
            }
            
            foreach (string run in runs)
            {
                Console.WriteLine(PebbleSolitaire.getLeast(run));
            }
        }
    }
}
