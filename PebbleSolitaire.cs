using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Kattis_tasks
{
    class PebbleSolitaire
    {
        static Dictionary<string, int> testedBoards = new Dictionary<string, int>();
        static int getLeast(string board, int nrOfPebbles)
        {
            
            if (isTested(board))
                return testedBoards[board];
            char[] newBoard;
            int tempResult = nrOfPebbles;
            int bestResult = tempResult;
            for (int i = 0; i < 21 && nrOfPebbles!=1; i++)
            {
                if (board[i] == 'o' && board[i + 1] == 'o' && board[i + 2] == '-')
                {
                    newBoard = board.ToCharArray();
                    newBoard[i] = '-';
                    newBoard[i + 1] = '-';
                    newBoard[i + 2] = 'o';
                    tempResult = getLeast(new string(newBoard), nrOfPebbles -1);
                    if (tempResult < bestResult)
                        bestResult = tempResult;
                }
                if (board[i] == '-' && board[i + 1] == 'o' && board[i + 2] == 'o')
                {
                    newBoard = board.ToCharArray();
                    newBoard[i] = 'o';
                    newBoard[i + 1] = '-';
                    newBoard[i + 2] = '-';
                    tempResult = getLeast(new string(newBoard), nrOfPebbles-1);  
                    if (tempResult < bestResult)
                        bestResult = tempResult;
                }
            }
            addBoard(board, bestResult);
            return bestResult;
        }

        private static bool isTested(string board)
        {
            return testedBoards.ContainsKey(board);
            
        }

        private static void addBoard(string board, int result)
        {
            testedBoards.Add(board, result);
        }

        static void Main(string[] args)
        {
            int nrOfRuns = Int32.Parse(Console.ReadLine());
            string[] runs = new string[nrOfRuns];
            for (int i = 0; i < nrOfRuns; i++)
            {
                runs[i] = Console.ReadLine();
            }
            int pebbles = 0;
            foreach (string run in runs)
            {
                pebbles = 0;
                for(int i = 0; i < 23; i++)
                {
                    if (run[i] == 'o')
                    {
                        pebbles++;
                    }
                }
                Console.WriteLine(PebbleSolitaire.getLeast(run,pebbles));
            }
        }
    }
}
