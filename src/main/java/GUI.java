import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class GUI {

  private static final int BOARD_SIZE = 9;
  JTextField[][] inputArr = new JTextField[BOARD_SIZE][BOARD_SIZE];

  public void run() {
    JFrame frame = new JFrame("Soduku Solver");
    Container contentPane = frame.getContentPane();
    contentPane.setLayout(new BorderLayout());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(300, 400);

    contentPane.add(addText(), BorderLayout.NORTH);
    contentPane.add(addSodukuBoard(), BorderLayout.CENTER);
    contentPane.add(addSolveButton(), BorderLayout.SOUTH);

    frame.setVisible(true);
  }

  private JPanel addText() {
    JPanel textPanel = new JPanel();
    JTextArea instructions =
        new JTextArea(
            "Instructions:\n1. use <tab> or mouse to move between cells\n2. enter the known numbers then press solve\n\nNote: in case of multiple solutions, the first solution found will be presented");
    instructions.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 10));
    instructions.setLineWrap(true);
    instructions.setEditable(false);
    instructions.setWrapStyleWord(true);
    instructions.setColumns(28);
    textPanel.add(instructions);
    return textPanel;
  }

  private JPanel addSolveButton() {
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());

    JButton solve = new JButton("Solve");
    solve.addActionListener(e -> onSolve(solve, e));
    buttonPanel.add(solve);

    JButton clear = new JButton("Clear");
    clear.addActionListener(new ActionListener(){
                              @Override
                              public void actionPerformed(ActionEvent e) {
                                if(e.getSource() == clear) {
                                  for (int i=0; i < BOARD_SIZE; i++) {
                                    for (int j=0; j < BOARD_SIZE; j++) {
                                      inputArr[i][j].setText("");
                                      inputArr[i][j].setBackground(Color.white);
                                    }
                                  }
                                }
                              }
                            }
    );
    buttonPanel.add(clear);

    return buttonPanel;
  }

  private void onSolve(JButton solve, java.awt.event.ActionEvent e) {
    Board board = new Board();
    boolean boardReady = true;

    if (e.getSource() == solve) {
      for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
          if (!inputArr[i][j].getText().isEmpty()) {
            int val = Integer.parseInt(inputArr[i][j].getText());
            if (!board.insert(i, j, val)) {
              inputArr[i][j].setBackground(Color.RED);
              boardReady = false;
            } else {
              inputArr[i][j].setBackground(Color.GREEN);
            }
          }
        }
      }

      if (boardReady) {
        Solver solver = new Solver();
        fillBoardIfEmpty(board);
        solver.solve(0, 0, board);
        if (solver.getSolutions().isEmpty()) {
          JOptionPane.showMessageDialog(solve, "There is no solution to this one!");
        } else {
          showResults(solver);
        }
      }
    }
  }

  private void showResults(Solver solver) {
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        if (inputArr[i][j].getText().isEmpty()) {
          inputArr[i][j].setText(
              "" + new ArrayList<>(solver.getSolutions()).get(0).getVal(i, j));
          inputArr[i][j].setBackground(Color.LIGHT_GRAY.brighter());
        }
      }
    }
  }

  private void fillBoardIfEmpty(Board board) {
    // If board empty avoid repeating same solution by generating random value at (0,0)
    // and a second (different) random value on a random location as seed
    if (board.countZeros() == BOARD_SIZE * BOARD_SIZE) {
      Random random = new Random();
      int rand_val_0_0 = random.nextInt(BOARD_SIZE) + 1;
      board.insert(0, 0, rand_val_0_0);
      int rand_val_2 = random.nextInt(BOARD_SIZE) + 1;
      while (rand_val_2 == rand_val_0_0) {
        rand_val_2 = random.nextInt(BOARD_SIZE) + 1;
      }
      board.insert(random.nextInt(BOARD_SIZE), random.nextInt(BOARD_SIZE), rand_val_2);
    }
  }

  private JPanel addSodukuBoard() {
    JPanel boardPanel =
        new JPanel() {
          @Override
          protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawLine(
                inputArr[0][3].getX(),
                inputArr[0][3].getY(),
                inputArr[8][3].getX(),
                inputArr[8][3].getY() + inputArr[8][2].getHeight());
            g.drawLine(
                inputArr[0][6].getX(),
                inputArr[0][6].getY(),
                inputArr[8][6].getX(),
                inputArr[8][6].getY() + inputArr[8][6].getHeight());
            g.drawLine(
                inputArr[3][0].getX(),
                inputArr[3][0].getY(),
                inputArr[3][8].getX() + inputArr[3][8].getWidth(),
                inputArr[3][8].getY());
            g.drawLine(
                inputArr[6][0].getX(),
                inputArr[6][0].getY(),
                inputArr[6][8].getX() + inputArr[6][8].getWidth(),
                inputArr[6][8].getY());
          }
        };

    GridLayout gridLayout = new GridLayout(9, 9);
    boardPanel.setLayout(gridLayout);

    for (int row = 0; row < gridLayout.getRows(); row++) {
      for (int col = 0; col < gridLayout.getColumns(); col++) {
        inputArr[row][col] = new JTextField();
        configText(inputArr[row][col]);
        boardPanel.add(inputArr[row][col]);
      }
    }

    return boardPanel;
  }

  public void configText(JTextField field) {
    AbstractDocument d = (AbstractDocument) field.getDocument();
    d.setDocumentFilter(
        new DocumentFilter() {
          final int max = 1;

          @Override
          public void replace(
              DocumentFilter.FilterBypass fb,
              int offset,
              int length,
              String text,
              AttributeSet attrs)
              throws BadLocationException {
            int documentLength = fb.getDocument().getLength();
            if (documentLength - length + text.length() <= max && (text.length() == 0 || Character.isDigit(text.charAt(0))))
              super.replace(fb, offset, length, text, attrs);
          }
        });
    field.setHorizontalAlignment(JTextField.CENTER);
  }

  public static void main(String args[]) {
    GUI gui = new GUI();
    gui.run();
  }
}
