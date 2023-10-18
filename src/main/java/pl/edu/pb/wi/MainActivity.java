package pl.edu.pb.wi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView questionTextView;
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;

    private Question[] questions = new Question[]{
            new Question(R.string.q_activity, true),
            new Question(R.string.q_find_resources, false),
            new Question(R.string.q_listener, true),
            new Question(R.string.q_resources, true),
            new Question(R.string.q_version, false)
    };

    private boolean answers[];

    private int currentIndex = 0;
    private int correctAnswers = 0;

    private void checkAnswerCorrectness(boolean userAnswer)
    {
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        if(correctAnswer == userAnswer)
        {
            resultMessageId = R.string.corrcet_answer;
            if(!answers[currentIndex])
            {
                correctAnswers++;
                answers[currentIndex] = true;
            }
        }
        else
        {
            resultMessageId = R.string.incorrcet_answer;
            if(answers[currentIndex])
            {
                correctAnswers--;
                answers[currentIndex] = false;
            }
        }
        if(currentIndex != questions.length - 1)
        {
            Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
        }
        else
        {
            String finalResultMessage = getString(R.string.final_result_message, correctAnswers, questions.length);
            Toast.makeText(this, finalResultMessage, Toast.LENGTH_LONG).show();
        }
    }

    private void setNextQuestion()
    {
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.question_text_view);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);

        answers = new boolean[questions.length];

        trueButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswerCorrectness(true);
                    }
                }
        );

        falseButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswerCorrectness(false);
                    }
                }
        );

        nextButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentIndex = (currentIndex + 1) % questions.length;
                        setNextQuestion();
                    }
                }
        );
        setNextQuestion();

    }
}