import org.junit.Assert;
import org.junit.Test;

public class BoardTest {

  int[][] legalBoard =
      new int[][] {
        {1, 6, 8, 7, 3, 5, 2, 9, 4},
        {5, 4, 2, 1, 9, 8, 6, 7, 3},
        {3, 9, 7, 4, 2, 6, 5, 8, 1},
        {8, 5, 6, 2, 4, 9, 1, 3, 7},
        {4, 7, 1, 6, 5, 3, 9, 2, 8},
        {9, 2, 3, 8, 7, 1, 4, 5, 6},
        {7, 3, 5, 9, 1, 4, 8, 6, 2},
        {6, 1, 9, 3, 8, 2, 7, 4, 5},
        {2, 8, 4, 5, 6, 7, 3, 1, 9}
      };

  int[][] illegalBoard =
      new int[][] {
        {1, 6, 8, 5, 3, 5, 2, 9, 4},
        {5, 4, 2, 1, 9, 8, 6, 7, 3},
        {3, 9, 7, 4, 2, 6, 5, 8, 1},
        {8, 5, 6, 2, 4, 9, 1, 3, 7},
        {4, 7, 1, 6, 5, 3, 9, 2, 8},
        {9, 2, 3, 8, 7, 1, 4, 5, 6},
        {7, 3, 5, 9, 1, 4, 8, 6, 2},
        {6, 1, 9, 3, 8, 2, 7, 4, 5},
        {2, 8, 4, 5, 6, 7, 3, 1, 9}
      };

  int[][] trivialBoard =
      new int[][] {
        {1, 6, 8, 7, 3, 5, 2, 9, 0},
        {5, 4, 2, 1, 9, 8, 6, 7, 3},
        {3, 0, 7, 4, 2, 6, 5, 8, 1},
        {8, 5, 6, 2, 4, 9, 1, 3, 7},
        {4, 7, 1, 6, 5, 3, 9, 2, 8},
        {9, 2, 3, 8, 0, 1, 4, 5, 6},
        {7, 3, 5, 0, 0, 4, 8, 6, 2},
        {6, 1, 9, 3, 8, 2, 7, 4, 5},
        {2, 8, 4, 5, 6, 7, 3, 1, 9}
      };

  int[][] hardHaaretz =
      new int[][] {
        {0, 0, 1, 4, 0, 2, 6, 0, 5},
        {8, 0, 6, 0, 0, 0, 0, 0, 0},
        {0, 0, 4, 6, 0, 0, 1, 3, 0},
        {0, 0, 0, 0, 0, 9, 0, 0, 0},
        {0, 4, 0, 3, 1, 5, 0, 7, 0},
        {0, 0, 0, 7, 0, 0, 0, 0, 0},
        {0, 9, 5, 0, 0, 8, 7, 0, 0},
        {0, 0, 0, 0, 0, 0, 5, 0, 4},
        {3, 0, 7, 9, 0, 4, 2, 0, 0}
      };

  int[][] mediumHaaretz =
      new int[][] {
        {0, 7, 0, 6, 0, 4, 0, 2, 0},
        {2, 0, 0, 0, 0, 8, 9, 0, 0},
        {0, 0, 0, 0, 0, 0, 5, 0, 8},
        {0, 5, 0, 0, 6, 0, 0, 0, 2},
        {0, 0, 0, 3, 0, 5, 0, 0, 0},
        {3, 0, 0, 0, 2, 0, 0, 5, 0},
        {9, 0, 6, 0, 0, 0, 0, 0, 0},
        {0, 0, 3, 7, 0, 0, 0, 0, 4},
        {0, 8, 0, 1, 0, 9, 0, 3, 0}
      };

  int[][] easyHaaretz =
      new int[][] {
        {1, 0, 0, 7, 3, 0, 0, 0, 0},
        {5, 0, 0, 1, 0, 0, 6, 7, 3},
        {3, 9, 0, 0, 2, 0, 0, 8, 0},
        {0, 0, 0, 2, 0, 0, 1, 0, 0},
        {4, 7, 0, 6, 0, 3, 0, 2, 8},
        {0, 0, 3, 0, 0, 1, 0, 0, 0},
        {0, 3, 0, 0, 1, 0, 0, 6, 2},
        {6, 1, 9, 0, 0, 2, 0, 0, 5},
        {0, 0, 0, 0, 6, 7, 0, 0, 9}
      };

  int[][] hardHaaretz2 =
          new int[][] {
                  {0,0,0,0,6,2,0,7,3},
                  {0,0,0,9,0,0,8,0,0},
                  {9,6,3,0,0,0,0,0,0},
                  {0,0,0,0,0,8,0,0,2},
                  {0,0,8,2,0,5,1,0,0},
                  {1,0,0,4,0,0,0,0,0},
                  {0,0,0,0,0,0,5,3,4},
                  {0,0,2,0,0,7,0,0,0},
                  {4,1,0,3,5,0,0,0,0}
          };

  @Test
  public void legalBoard() {
    Board testBoard = new Board(legalBoard);
    Assert.assertTrue(testBoard.checkBoard());
  }

  @Test
  public void illegalBoard() {
    Board testBoard = new Board(illegalBoard);
    Assert.assertFalse(testBoard.checkBoard());
  }

  @Test
  public void emptyBoard() {
    Board testBoard = new Board();
    Assert.assertFalse(testBoard.checkBoard());
  }

  @Test
  public void firstMoveLegal() {
    Board testBoard = new Board();
    Assert.assertTrue(testBoard.insert(0, 0, 1));
    Assert.assertTrue(testBoard.insert(0, 1, 2));
    Assert.assertTrue(testBoard.insert(0, 2, 3));
    Assert.assertTrue(testBoard.insert(1, 0, 4));
    Assert.assertTrue(testBoard.insert(1, 1, 5));
    Assert.assertTrue(testBoard.insert(1, 2, 6));
    Assert.assertTrue(testBoard.insert(2, 0, 7));
    Assert.assertTrue(testBoard.insert(2, 1, 8));
    Assert.assertTrue(testBoard.insert(2, 2, 9));

    Assert.assertFalse(testBoard.insert(0, 0, 2));
  }

  @Test
  public void testInsert() {
    Board testBoard = new Board();

    Assert.assertTrue(testBoard.insert(0, 0, 1));
    // same row fails
    Assert.assertFalse(testBoard.insert(0, 5, 1));
    // same col fails
    Assert.assertFalse(testBoard.insert(5, 0, 1));
    // same cube fails
    Assert.assertFalse(testBoard.insert(1, 1, 1));
    // next cube ok
    Assert.assertTrue(testBoard.insert(5, 5, 1));
    // same value on same next cube fails
    Assert.assertFalse(testBoard.insert(3, 3, 1));
    // same value on different cube
    Assert.assertTrue(testBoard.insert(6, 6, 1));
    // same row fails
    Assert.assertFalse(testBoard.insert(1, 6, 1));
  }

  @Test
  public void testSolve() {
    Board board = new Board(hardHaaretz2);
    Solver solver = new Solver();
    solver.solve(0, 0, board);
    if (solver.getSolutions().isEmpty()) {
        System.out.println("No solutions found :-( ");
    } else {
        System.out.println(solver.getSolutions().size() + " solutions found:");
        for (Board b : solver.getSolutions()) {
            System.out.println();
            solver.printBoard(b);
      }
    }
  }
}
