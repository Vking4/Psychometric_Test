import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class ptct extends JFrame implements ActionListener {

    private static final String[] WORDS = { "RED", "BLUE", "GREEN", "YELLOW", "BLACK", "WHITE", "ORANGE", "PURPLE", "PINK", "BROWN" };
    private static final Color[] COLORS = { Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.BLACK, Color.WHITE, Color.ORANGE, new Color(128, 0, 128), Color.PINK, new Color(139, 69, 19) };
    private static final int TEST_TIME = 60; 
    private static final int NUM_QUESTIONS = 10;

    private JLabel wordLabel; 
    private JButton[] optionButtons;
    private int[] answers;
    private int questionIndex;
    private int numCorrect;
    private Timer timer;
    private int timeLeft;

    public ptct() {
        setTitle("Color Test");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(Color.BLACK);
        setContentPane(contentPane);

        wordLabel = new JLabel();
        wordLabel.setFont(new Font("Arial", Font.BOLD, 40));
        wordLabel.setHorizontalAlignment(JLabel.CENTER);
        wordLabel.setVerticalAlignment(JLabel.CENTER);
        wordLabel.setForeground(Color.WHITE);
        contentPane.add(wordLabel, BorderLayout.CENTER);

        JPanel optionPanel = new JPanel(new GridLayout(1, 2));
        optionButtons = new JButton[2];
for (int i = 0; i < optionButtons.length; i++) {
JButton button = new JButton();
button.setFont(new Font("Arial", Font.BOLD, 20));
button.addActionListener(this);
optionPanel.add(button);
optionButtons[i] = button;
}
contentPane.add(optionPanel, BorderLayout.SOUTH);

    // set up answers array
    answers = new int[NUM_QUESTIONS];

    // start test
    startTest();
}

private void startTest() {
    // shuffle WORDS array
    Collections.shuffle(Arrays.asList(WORDS));

    // reset variables
    questionIndex = 0;
    numCorrect = 0;
    timeLeft = TEST_TIME;

    // start timer
    timer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            timeLeft--;
            if (timeLeft == 0) {
                finishTest();
            } else {
                updateTimerLabel();
            }
        }
    });
    timer.start();

    // ask first question
    askQuestion();
}

private void askQuestion() {
    // shuffle COLORS array
    ArrayList<Color> colorList = new ArrayList<Color>(Arrays.asList(COLORS));
    Collections.shuffle(colorList);
    Color[] shuffledColors = colorList.toArray(new Color[0]);

    // set word and options
    wordLabel.setText(WORDS[questionIndex]);
    optionButtons[0].setText(shuffledColors[0].toString());
    optionButtons[1].setText(shuffledColors[1].toString());

    // record correct answer
    if (shuffledColors[0] == COLORS[Arrays.asList(WORDS).indexOf(wordLabel.getText())]) {
        answers[questionIndex] = 0;
    } else {
        answers[questionIndex] = 1;
    }
}

private void finishTest() {
    // stop timer
    timer.stop();

    // calculate score
    for (int i = 0; i < answers.length; i++) {
        if (answers[i] == 0) {
            numCorrect++;
        }
    }

    // show score
    JOptionPane.showMessageDialog(this, "You got " + numCorrect + " out of " + NUM_QUESTIONS + " correct!");

    // restart test
    startTest();
}

private void updateTimerLabel() {
    wordLabel.setText("Time left: " + timeLeft);
}

public void actionPerformed(ActionEvent e) {
    // record answer
    if (e.getSource() == optionButtons[0]) {
        answers[questionIndex] = 0;
    } else {
        answers[questionIndex] = 1;
    }

    // move to next question
    questionIndex++;
    if (questionIndex == NUM_QUESTIONS) {
        finishTest();
    } else {
        askQuestion();
    }
}

public static void main(String[] args) {
    ptct test = new ptct();
    test.setVisible(true);
}
}