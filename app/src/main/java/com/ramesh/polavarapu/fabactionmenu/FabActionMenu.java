package com.ramesh.polavarapu.fabactionmenu;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ramesh.polavarapu.fabactionmenu.dto.MyActionDto;


public class FabActionMenu extends FloatingActionButton implements View.OnClickListener {

    private IFabMenuListener fabMenuListener;
    private Context context;
    private boolean isFABOpen = false;
    private Object[] actionArray = null;
    private byte byFirst = -1;
    private Animation rotate_forward = null;
    private Animation rotate_backward = null;


    public FabActionMenu(Context context, Object[] arrItems, IFabMenuListener fabMenuListener) {
        super(context);
        this.fabMenuListener = fabMenuListener;
        this.context = context;
        this.actionArray = arrItems;
        setOnClickListener(this);

        rotate_forward = AnimationUtils.loadAnimation(context, R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(context, R.anim.rotate_backward);
    }

    public FabActionMenu(Context context) {
        super(context);
    }

    private void addMenuToFAB(Context context) {
        RelativeLayout fabMenuHolder = new RelativeLayout(context);
        fabMenuHolder.setId(R.id.fab_layout_holder);
        RelativeLayout.LayoutParams fabMenuLHldParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        fabMenuHolder.setLayoutParams(fabMenuLHldParams);

        for (int i = 0; i < actionArray.length; i++) {
            LinearLayout fabMenuLayout = getFabMenuView(context, (MyActionDto) actionArray[i], R.drawable.ic_add, i);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, ((RelativeLayout.LayoutParams) getLayoutParams()).rightMargin + toDip(context, 8), ((RelativeLayout.LayoutParams) getLayoutParams()).bottomMargin + toDip(context, 5));
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            fabMenuHolder.setLayoutParams(params);
            fabMenuHolder.addView(fabMenuLayout);
        }
        if (getParent() != null) {
            ViewGroup mainLayout = ((ViewGroup) getParent());
            mainLayout.removeView(this);
            mainLayout.addView(fabMenuHolder);
            mainLayout.addView(this);
        }
    }

    private LinearLayout getFabMenuView(Context context, MyActionDto myActionDto, int iFabDrawableId, int iPosition) {
        LinearLayout fabHolder = new LinearLayout(context);
        fabHolder.setClipToPadding(false);
        fabHolder.setId(iPosition);
        fabHolder.setVisibility(View.INVISIBLE);
        fabHolder.setGravity(Gravity.CENTER_VERTICAL);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        fabHolder.setLayoutParams(layoutParams);
        fabHolder.setOnClickListener(this);
        fabHolder.setClipToPadding(false);

        TextView menuLabel = new TextView(context);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 3);
        menuLabel.setGravity(Gravity.CENTER_VERTICAL);
        menuLabel.setLayoutParams(textParams);
        menuLabel.setText(myActionDto.getLabel());

        FloatingActionButton fabItem = new FloatingActionButton(context);
        LinearLayout.LayoutParams fabParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        fabItem.setSize(FloatingActionButton.SIZE_MINI);
        fabItem.setImageDrawable(context.getResources().getDrawable(iFabDrawableId));

        fabParams.setMargins(toDip(context, 10), 0, 0, 0);
        fabItem.setLayoutParams(fabParams);
        fabHolder.addView(menuLabel);
        fabHolder.addView(fabItem);

        return fabHolder;
    }

    public void closeFABMenu(Activity context) {
        isFABOpen = false;
        if (rotate_backward != null)
            startAnimation(rotate_backward);
        setImageDrawable(context.getResources().getDrawable(R.drawable.ic_add));
        final RelativeLayout fabHolder = ((ViewGroup) getParent()).findViewById(R.id.fab_layout_holder);
        final int iChildCount = fabHolder.getChildCount();
        for (int i = 0; i < iChildCount; i++) {
            final LinearLayout fabMenuLayout = (LinearLayout) fabHolder.findViewById(i);
            fabMenuLayout.setClickable(false);
            if (i == iChildCount - 1) {
                fabMenuLayout.animate().translationY(0).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        setEnabled(false);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        setEnabled(true);
                        if (!isFABOpen) {

                            for (int i = 0; i < iChildCount; i++) {
                                ((LinearLayout) fabHolder.findViewById(i)).setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                    }
                });
            } else {
                fabMenuLayout.animate().translationY(0);
            }
        }
    }

    private void showFABMenu(Activity context) {
        LinearLayout fabMenuLayout;
        isFABOpen = true;
        if (rotate_forward != null)
            startAnimation(rotate_forward);
        RelativeLayout fabHolder = ((ViewGroup) getParent()).findViewById(R.id.fab_layout_holder);
        setImageDrawable(context.getResources().getDrawable(R.drawable.ic_close));
        int iChildCount = fabHolder.getChildCount();
        int iCount = 1;

        for (int i = 0; i < iChildCount; i++) {
            fabMenuLayout = (LinearLayout) fabHolder.findViewById(i);
            fabMenuLayout.setClickable(true);
            fabMenuLayout.setVisibility(View.VISIBLE);
            if (i == 0) {

                /*
                 56 is Default FAB SIZE and 45 is Animating FAB for pushing up from current position
                 */

                fabMenuLayout.animate().translationY(-toDip(context, 56));
            } else {
                fabMenuLayout.animate().translationY(-toDip(context, 56) + (-toDip(context, 45) * iCount));
                iCount = iCount + 1;
            }
        }
    }

    private short toDip(Context context, int pixels) {
        return (short) ((int) ((float) pixels * context.getResources().getDisplayMetrics().density + 0.5F));
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == getId()) {
            if (byFirst == -1) {
                addMenuToFAB(getContext());
                byFirst = 1;
            }

            if (!isFABOpen) {
                showFABMenu((Activity) getContext());
            } else {
                closeFABMenu((Activity) getContext());
            }
        } else {
            closeFABMenu((Activity) context);
            fabMenuListener.onFabItemClick(v, actionArray[v.getId()]);
        }
    }
}

interface IFabMenuListener {
    public void onFabItemClick(View v, Object actionItem);
}