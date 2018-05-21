package com.ramesh.polavarapu.fabactionmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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

    private ScrollView getRootView() {

        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        scrollView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < 15; i++) {
            TextView textView = new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setText(getString(R.string.app_name));
            linearLayout.addView(textView);
        }
        relativeLayout.addView(linearLayout);
        FabActionMenu fabActionMenu = new FabActionMenu(this, myActionDtos, this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        fabActionMenu.setId(R.id.fab);
        fabActionMenu.setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
        layoutParams.setMargins(0, 0, 16, 16);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        fabActionMenu.setLayoutParams(layoutParams);
        relativeLayout.addView(fabActionMenu);

        scrollView.addView(relativeLayout);

        return scrollView;
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
        String s = ((TextView) ((LinearLayout) findViewById(v.getId())).getChildAt(0)).getText().toString();
        Toast.makeText(MainActivity.this, "" + s, Toast.LENGTH_SHORT).show();
    }
}