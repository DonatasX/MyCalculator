package pucinskas.donatas.mycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView textView1, textView2;
    private int result;
    private int startIndex, endIndex;
    private ArrayList<Integer> numbers;
    private ArrayList<String> symbols;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        numbers = new ArrayList<Integer>();
        symbols = new ArrayList<String>();
        startIndex = 0;
    }


    // on button press functions.
    public void onClickNum(View view){
        Button btn = findViewById(view.getId());
        String str = textView1.getText() + btn.getText().toString();
        textView1.setText(str);
    }

    public void onClickSymbol(View view){
        String str = textView1.getText().toString();
        Button btn = findViewById(view.getId());
        String symbol = btn.getText().toString();
        char lastCh = str.charAt(str.length() - 1);

        if (lastCh == '+' || lastCh == '-' || lastCh == '*' || lastCh == '/'){
            str = str.substring(0,str.length()-1) + symbol;
            symbols.remove(symbols.size()-1);
            symbols.add(symbol);
        }
        else {
            endIndex = str.length();
            symbols.add(symbol);
            addNumber(startIndex,endIndex);
            str = str + symbol;
            startIndex = str.length();
        }
        textView1.setText(str);
    }

    public void clearScr(View view){
        textView1.setText("");
        textView2.setText("");
        numbers.clear();
        startIndex = 0;
    }

    public void printResult(View view){
        String str = textView1.getText().toString();
        addNumber(startIndex,str.length());
        result = numbers.get(0);
        while(!symbols.isEmpty()){
            switch (symbols.get(0)){
                case ("+"):
                    result += numbers.get(1);
                    break;
                case ("/"):
                    result /= numbers.get(1);
                    break;
                case ("*"):
                    result *= numbers.get(1);
                    break;
                case ("-"):
                    result -= numbers.get(1);
                    break;
            }
            numbers.remove(1);
            symbols.remove(0);
        }
        textView2.setText("=" + result);
    }

    // other methods
    private void addNumber(int start, int end){
        int number;
        if (textView1.getText().toString() == ""){
            number = Integer.parseInt(textView1.getText().toString());
        }
        else{
            String str = textView1.getText().subSequence(start,end).toString();
            number = Integer.parseInt(str);
        }
        numbers.add(number);
    }

}
