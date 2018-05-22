package com.ramesh.polavarapu.fabactionmenu;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ramesh.polavarapu.fabactionmenu.dto.MyActionDto;

public class MainActivity extends AppCompatActivity implements IFabMenuListener {
    private MyActionDto[] myActionDtos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        setContentView(getRootView());

    }

    private RelativeLayout getRootView() {

        RelativeLayout relativeLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams params  =   new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        relativeLayout.setLayoutParams(params);
        relativeLayout.setBackgroundColor(Color.WHITE);

        LinearLayout verticalLayout = new LinearLayout(this);
        verticalLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        verticalLayout.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < 35; i++) {
            LinearLayout horizontalLayout = new LinearLayout(this);
            horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            for (int j = 0; j < 3; j++) {
                TextView textView = new TextView(this);
                LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                textView.setText(getString(R.string.app_name));

                horizontalLayout.addView(textView, textParams);
            }
            verticalLayout.addView(horizontalLayout);
        }
        relativeLayout.addView(verticalLayout);

        FabActionMenu fabActionMenu = new FabActionMenu(this, myActionDtos, this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        fabActionMenu.setId(R.id.fab);
        fabActionMenu.setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
        layoutParams.setMargins(0, 0, 16, 16);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        fabActionMenu.setLayoutParams(layoutParams);

        relativeLayout.addView(fabActionMenu);

        return relativeLayout;
    }

    private void init() {
        MyActionDto myActionDto = new MyActionDto();
        myActionDto.setActionId(1);

        myActionDto.setLabel("FAB 1");
        MyActionDto myActionDto1 = new MyActionDto();
        myActionDto1.setActionId(2);

        myActionDto1.setLabel("FAB 2");
        MyActionDto myActionDto2 = new MyActionDto();
        myActionDto2.setLabel("0123456789");
        myActionDto2.setActionId(3);


        MyActionDto myActionDto3 = new MyActionDto();
        myActionDto3.setLabel("FAB 4");
        myActionDto3.setActionId(6);

        myActionDtos = new MyActionDto[]{myActionDto, myActionDto1, myActionDto2, myActionDto3};
    }

    @Override
    public void onFabItemClick(View v, Object objItem) {
        String s = ((TextView) (((CardView) ((LinearLayout) findViewById(v.getId())).getChildAt(0)).getChildAt(0))).getText().toString();
        Toast.makeText(MainActivity.this, "" + s, Toast.LENGTH_SHORT).show();
    }
}