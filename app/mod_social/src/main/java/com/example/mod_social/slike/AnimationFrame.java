package com.example.mod_social.slike;

import java.util.List;

public interface AnimationFrame {

    int getType();

    boolean isRunning();

    List<Element> nextFrame(long interval);

    boolean onlyOne();

    void setAnimationEndListener(AnimationEndListener animationEndListener);

    void reset();

    void prepare(int x, int y, BitmapProvider.Provider bitmapProvider);

}
