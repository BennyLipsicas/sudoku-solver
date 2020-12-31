import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {

  private static final int BOARD_SIZE = 9;
  private static final int CUBE_SIZE = 3;
  private static final Set<Integer> ALLOWED_VALUES =
      IntStream.rangeClosed(1, BOARD_SIZE).boxed().collect(Collectors.toSet());
  int[][] board;

  public Board() {
    board = new int[BOARD_SIZE][BOARD_SIZE];
  }

  public Board(int[][] board) {
    this.board = board;
  }

  public static int[][] copy(int[][] board) {
    int[][] copy = new int[BOARD_SIZE][BOARD_SIZE];
    for (int i = 0; i < BOARD_SIZE; i++) {
      for (int j = 0; j < BOARD_SIZE; j++) {
        copy[i][j] = board[i][j];
      }
    }
    return copy;
  }

  public boolean insert(int x, int y, int val) {
    if (!verify(x, y, val)) return false;

    if (isMoveLegal(x, y, val)) {
      board[x][y] = val;
      return true;
    }
    return false;
  }

  private boolean verify(int x, int y, int val) {
    if (val < 1 || val > BOARD_SIZE) {
      System.out.println("illegal value");
      return false;
    }

    if (x < 0 || x > BOARD_SIZE - 1) {
      System.out.println("illegal x coord");
      return false;
    }

    if (y < 0 || y > BOARD_SIZE - 1) {
      System.out.println("illegal y coord");
      return false;
    }

    return true;
  }

  public boolean isMoveLegal(int x, int y, int val) {
    // horizontal + vertical
    for (int i = 0; i < BOARD_SIZE; i++) {
      if (i != x && board[i][y] == val || i != y && board[x][i] == val) {
        return false;
      }
    }

    // cube
    int x_start = x - x % CUBE_SIZE;
    int y_start = y - y % CUBE_SIZE;
    for (int i = x_start; i < x_start + CUBE_SIZE; i++) {
      for (int j = y_start; j < y_start + CUBE_SIZE; j++) {
        if ((i != x || j != y) && board[i][j] == val) return false;
      }
    }
    return true;
  }

  public boolean checkBoard() {

    // horizontal + vertical
    for (int x = 0; x < BOARD_SIZE; x++) {
      HashSet<Integer> horizontal = new HashSet<>();
      HashSet<Integer> vertical = new HashSet<>();
      for (int y = 0; y < BOARD_SIZE; y++) {
        if (board[x][y] != 0) {
          horizontal.add(board[x][y]);
        }
        if (board[y][x] != 0) {
          vertical.add(board[y][x]);
        }
      }
      if (!horizontal.equals(ALLOWED_VALUES) || !vertical.equals(ALLOWED_VALUES)) {
        return false;
      }
    }

    // cubes
    for (int x = 0; x < CUBE_SIZE; x++) {
      for (int y = 0; y < CUBE_SIZE; y++) {
        HashSet<Integer> cube = new HashSet<>();
        for (int i = x * CUBE_SIZE; i < (x + 1) * CUBE_SIZE; i++) {
          for (int j = y * CUBE_SIZE; j < (y + 1) * CUBE_SIZE; j++) {
            if (board[i][j] != 0) {
              cube.add(board[i][j]);
            }
          }
        }
        if (!cube.equals(ALLOWED_VALUES)) {
          return false;
        }
      }
    }

    return true;
  }

  public int[][] getBoard() {
    return board;
  }

  public int getVal(int x, int y) {
    return board[x][y];
  }

  public static int getCubeSize() {
    return CUBE_SIZE;
  }

  public static int getBoardSize() {
    return BOARD_SIZE;
  }

  public static Set<Integer> getAllowedValues() {
    return ALLOWED_VALUES;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Board board1 = (Board) o;
    return Arrays.equals(board, board1.board);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(board);
  }

  // DEBUG
  public int countZeros() {
    int counter = 0;
    for (int x = 0; x < BOARD_SIZE; x++) {
      for (int y = 0; y < BOARD_SIZE; y++) {
        if (board[x][y] == 0) {
          counter++;
        }
      }
    }
    return counter;
  }
}
