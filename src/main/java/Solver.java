import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solver {

  Set<Board> solutions = new HashSet<>();

  public void solve(int x, int y, Board board) {

    // circuit breaker for too many solutions
    if (!solutions.isEmpty()) {
      return;
    }

    if (board.checkBoard()) {
      solutions.add(board);
      return;
    }

    // Board limits
    if (x > Board.getBoardSize() - 1 || y > Board.getBoardSize() - 1 || x < 0 || y < 0) {
      return;
    }

    // Recursive step
    if (board.getVal(x, y) == 0) {
      Set<Integer> candidates = findCandidates(x, y, board);
      for (Integer c : candidates) {
        if (candidates.size() > 1) {
          Board newBoard = new Board(Board.copy(board.getBoard()));
          if (newBoard.insert(x, y, c)) {
            goRecursive(x, y, newBoard);
          }
        } else {
          if (board.insert(x, y, c)) {
            goRecursive(x, y, board);
          }
        }
      }
    } else {
      goRecursive(x, y, board);
    }
  }

  private void goRecursive(int x, int y, Board board) {
    if (x == Board.getBoardSize() - 1) {
      x=-1;
      y++;
    }
    solve(x + 1, y, board);

  }

  private Set<Integer> findCandidates(int x, int y, Board board) {
    Set<Integer> candidates = IntStream.rangeClosed(1, Board.getBoardSize()).boxed().collect(Collectors.toSet());

    for (int i = 0; i < Board.getBoardSize(); i++) {
      if (board.getVal(i, y) != 0) {
        candidates.remove(board.getVal(i, y));
      }

      if (board.getVal(x, i) != 0) {
        candidates.remove(board.getVal(x, i));
      }
    }

    int cube_x = x - x % Board.getCubeSize();
    int cube_y = y - y % Board.getCubeSize();
    for (int i = cube_x; i < cube_x + Board.getCubeSize(); i++) {
      for (int j = cube_y; j < cube_y + Board.getCubeSize(); j++) {
        if (board.getVal(i, j) != 0) {
          candidates.remove(board.getVal(i, j));
        }
      }
    }

    return candidates;
  }

  public void printBoard(Board board) {
    System.out.println("----------------------------");
    for (int a = 0; a < Board.getBoardSize(); a++) {
      for (int b = 0; b < Board.getBoardSize(); b++) {
        if (b % Board.getCubeSize() == 0 && b > 0) {
          System.out.print("|");
        }

        System.out.print(" " + board.getVal(a,b) + " ");
      }

      if (a % Board.getCubeSize() == Board.getCubeSize() - 1 && a > 0) {
        System.out.println("\n----------------------------");
      } else {
        System.out.println();
      }
    }
  }

  public Set<Board> getSolutions() {
    return solutions;
  }
}
