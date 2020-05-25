package kr.co.yhw.baseballgame_20200525;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kr.co.yhw.baseballgame_20200525.adapters.MessageAdapter;
import kr.co.yhw.baseballgame_20200525.databinding.ActivityMainBinding;
import kr.co.yhw.baseballgame_20200525.datas.Message;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;

//    문제로 사용될 세자리 숫자 배열
    int[] questionArr= new int[3];

//    정답을 입력 시도 횟수
    int tryCount =0;

//     채팅내역으로 사용할 ArrayList
    List<Message> messages = new ArrayList<>();

//    af
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupEvents();
        setVaules();
    }

    @Override
    public void setupEvents() {
        binding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                전송버튼 누르면 => 타이핑된 값을 받아오기
                String inputValues = binding.numEdt.getText().toString();

//                3자리가 아니면 등록 거부
                if (inputValues.length() !=3){
                    Toast.makeText(mContext, "세 자리 숫자로 입력바랍니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

//                새로운 메세지로 등록
                messages.add(new Message(inputValues, "Me"));

//                리스트뷰의 내용물에 변화 발생
                messageAdapter.notifyDataSetChanged();

//                리스트뷰를 맨 밑으로 끌어 내려주자.
                binding.messageListView.smoothScrollToPosition(messages.size()-1);

//                올바르게 입력했으니 횟수를 증가 처리.
                tryCount++;

//              ?스트라이크 ?볼인지 계산하고 답장하자
                checkStrikeAndBalls(inputValues);


            }
        });
    }

    @Override
    public void setVaules() {

        messageAdapter =new MessageAdapter(mContext,R.layout.message_list_item,messages);
        binding.messageListView.setAdapter(messageAdapter);
        makeQusetion();
    }

//    문제로 나올 3자리 숫자를 입력
    void makeQusetion(){
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

        //컴퓨터가 사람한테 환영메세지

        messages.add(new Message("야구게임에오신걸 환영합니다.","Cpu"));

        messages.add(new Message("세 자리 숫자를 입력하세요.","Cpu"));

        messages.add(new Message("1 ~ 9 만 출제되며, 중복은 없습니다..","Cpu"));

//         어댑터가 사용하는 List의 내용변경 (메세지 추가)이 생겼으니 새로고침
        messageAdapter.notifyDataSetChanged();




    }
    void checkStrikeAndBalls(String inputVal){

//        String => int 변경 => int [] 3자리로 변경
        int inputNum = Integer.parseInt(inputVal);

        int[] myNumbers = new int[3];

//        100의자리 / 10의 자리 / 1의 자리 얻어내기
        myNumbers[0] = inputNum /100;
        myNumbers[1] = inputNum /10 % 10;
        myNumbers[2] = inputNum % 10;

        int strikeCount = 0;
        int ballCount = 0;
//        myNumbers와 questionArr 간의 비교
        for ( int i =0; i < myNumbers.length; i++){

            for (int j = 0; j < questionArr.length; j++){
//                같은 숫자 발견 => S/B이 될 가능성이 있다
                if (myNumbers[i] == questionArr[j]){

//                    index가 같으니 strike
                    if( i == j){
                        strikeCount++;
                    }
//                    숫자는 같지만 index가 다르니 ball발견
                    else {
                        ballCount++;
                    }
                }
            }
        }

//         컴퓨터가  ?S ?B인지 답장하고, 밑으로 끌어내리기
        messages.add(new Message(String.format("%dS %dB 입니다.",strikeCount ,ballCount),"Cpu"));

        messageAdapter.notifyDataSetChanged();
        binding.messageListView.smoothScrollToPosition(messages.size()-1);

//        3S 라면 축하메세지 + 몇번만에 맞췄는지 + 입력 불가하도록 막아주기
        if( strikeCount ==3 ){
            messages.add(new Message("정답입니다","Cpu"));
            messages.add(new Message(String.format("%d회만에 맞췄습니다",tryCount ),"Cpu"));
            messageAdapter.notifyDataSetChanged();
            binding.messageListView.smoothScrollToPosition(messages.size()-1);

//            EditText와 button을 더이상 사용하지 못하도록 막아주는 코드
            binding.numEdt.setEnabled(false);
            binding.sendBtn.setEnabled(false);

//            종료 메세지
            Toast.makeText(mContext, "이용해주셔서 감사합니다아~", Toast.LENGTH_SHORT).show();
        }


    }
}
