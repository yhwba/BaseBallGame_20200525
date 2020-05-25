package kr.co.yhw.baseballgame_20200525.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import kr.co.yhw.baseballgame_20200525.R;
import kr.co.yhw.baseballgame_20200525.datas.Message;

public class MessageAdapter extends ArrayAdapter<Message> {

    Context mContext;
    List<Message> mList;
    LayoutInflater inf;

    public MessageAdapter(@NonNull Context context, int resource, @NonNull List<Message> objects) {
        super(context, resource, objects);

        mContext =context;
        mList=objects;
        inf= LayoutInflater.from(mContext);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;

        if( row == null){
            row = inf.inflate(R.layout.message_list_item,null);
        }
//      g화면에 컴포넌트들을 가져오는 코드들
        FrameLayout myMessageLayout = row.findViewById(R.id.myMessageLayout);
        FrameLayout cpuMessageLayout = row.findViewById(R.id.cpuMessageLayout);
        TextView myMessageTxt = row.findViewById(R.id.myMessageTxt);
        TextView cpuMessageTxt = row.findViewById(R.id.cpuMessageTxt);



//        이번에 보여줘야할 채팅 메세지
        Message data = mList.get(position);

        if (data.getWho().equals("Me")){
            myMessageLayout.setVisibility(View.VISIBLE);
            cpuMessageLayout.setVisibility(View.GONE);

            myMessageTxt.setText(data.getContent());
        }
        else {
            myMessageLayout.setVisibility(View.GONE);
            cpuMessageLayout.setVisibility(View.VISIBLE);
            cpuMessageTxt.setText(data.getContent());
        }

        return row;
    }
}
