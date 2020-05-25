package kr.co.yhw.baseballgame_20200525;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;

import kr.co.yhw.baseballgame_20200525.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;

//    문제로 사용될 세자리 숫자 배열
    int[] questionArr= new int[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupEvents();
        setVaules();
    }

    @Override
    public void setupEvents() {

    }

    @Override
    public void setVaules() {
        makeQusetion();
    }

//    문제로 나올 3자리 숫자를 입력
    void makeQusetion() {
//        3자리 숫자를 채우기 위한 for문
        for (int i = 0;  i< questionArr.length; i++){
//            각 자리에 올바른 숫자가 들어갈때까지 무한 반복
            while (true){
//                0+1 <= (int)(Math.random*9+1) < 9+1=> 1~9사이의 정수만 뽑히게
                int randamNum = (int) (Math.random()*9 +1);

                boolean isDulpOk = true;

//                중복검사 로직 => 같은 숫자가 하나라도 있는지 조회
                for ( int num : questionArr ){
//                    문제에서 같은 숫자를 찾았다 => 중복검사 통과X
                    if (num ==  randamNum){
                        isDulpOk=false;
                        break;
                    }
                }
//                중복검사를 통과?
                if (isDulpOk){
//                    배열에 문제로 이 숫자를 채택
                    questionArr[i]=randamNum;
//                    올바른 숫자를 뽑았으니 무한 반복 종료 => 다음숫자 뽑으로 이동
                    break;
                }

            }
        }
        for (int num : questionArr){
            Log.d("문제숫자",num+"");
        }




    }

}
