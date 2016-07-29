package com.ldoublem.flightseatselect;

import android.app.Activity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public abstract class MoveListerner implements OnTouchListener,
        OnGestureListener {
    public static final int MOVE_TO_LEFT = 0;
    public static final int MOVE_TO_RIGHT = MOVE_TO_LEFT + 1;
    public static final int MOVE_TO_UP = MOVE_TO_RIGHT + 1;
    public static final int MOVE_TO_DOWN = MOVE_TO_UP + 1;

    private static final int FLING_MIN_DISTANCE = 150;
    private static final int FLING_MIN_VELOCITY = 50;
    private boolean isScorllStart = false;
    private boolean isUpAndDown = false;
    GestureDetector mGestureDetector;
    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;

    public MoveListerner(Activity context) {
        super();
        mGestureDetector = new GestureDetector(context, this);
    }

    float startX = 0;
    float startY = 0;


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event)) {
            if (MotionEvent.ACTION_DOWN == event.getAction()) {
//                Touch(event.getX(), event.getY());
                startX = event.getX();
                startY = event.getY();
                return true;
            } else if (MotionEvent.ACTION_UP == event.getAction()) {
                if (Math.abs(event.getX() - startX) < 5 &&
                        Math.abs(event.getY() - startY) < 5) {
                    Touch(event.getX(), event.getY());
                    return true;

                }

            }
        }
        // 处理手势结束
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                endGesture();
                break;
        }
        return false;
    }

    private void endGesture() {
        isScorllStart = false;
        isUpAndDown = false;
//        Log.e("a", "AA:over");
        moveOver();
    }

    public abstract void moveDirection(View v, int direction, float distanceX, float distanceY);

    public abstract void moveUpAndDownDistance(MotionEvent event, int distance, int distanceY);

    public abstract void moveOver();

    public abstract void Touch(float x, float y);


    @Override
    public boolean onDown(MotionEvent e) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        float mOldY = e1.getY();
        int y = (int) e2.getRawY();
        if (!isScorllStart) {
            if (Math.abs(distanceX) / Math.abs(distanceY) > 2) {
                // 左右滑动
                isUpAndDown = false;
                isScorllStart = true;
            } else if (Math.abs(distanceY) / Math.abs(distanceX) > 3) {
                // 上下滑动
                isUpAndDown = true;
                isScorllStart = true;
            } else {
                isScorllStart = false;
            }
        } else {
            // 算滑动速度的问题了
            if (isUpAndDown) {
                // 是上下滑动，关闭左右检测
                if (mOldY + 5 < y) {
                    moveUpAndDownDistance(e2, -3, (int) distanceY);
                } else if (mOldY + 5 > y) {
                    moveUpAndDownDistance(e2, 3, (int) distanceY);
                }
            }
        }
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
//        Log.e("a", "AA:A" + velocityX + ":" + velocityY);
        if (isUpAndDown)
            return false;
        if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
                && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
            // Fling left
            moveDirection(null, MOVE_TO_LEFT, e1.getX() - e2.getX(), e1.getY() - e2.getY());
        } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
                && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
            // Fling right
            moveDirection(null, MOVE_TO_RIGHT, e2.getX() - e1.getX(), e2.getY() - e1.getY());
        } else if (e1.getY() - e2.getY() > FLING_MIN_DISTANCE
                && Math.abs(velocityY) > FLING_MIN_VELOCITY) {
            // Fling up
            moveDirection(null, MOVE_TO_UP, 0, e1.getY() - e2.getY());
        } else {
            // Fling down
            moveDirection(null, MOVE_TO_DOWN, 0, e2.getY() - e1.getY());
        }
        return false;
    }
}