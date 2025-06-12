package com.example.mandaladrawer;

import com.example.mandaladrawer.event.EndAnimationEvent;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.util.Duration;


public class Animation {

    private final double target;
    private final DoubleProperty progress;
    private final Timeline timeline;
    private EventHandler<EndAnimationEvent> whenFinished;
    private DrawingManager manager;

    public Animation(DoubleProperty initial, double target, double time) {
        this.target = target;
        progress = initial;
        timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(time),
                        new KeyValue(progress, target)
                )
        );
        timeline.setOnFinished(actionEvent -> {
            if (manager != null && whenFinished != null) {
                whenFinished.handle(new EndAnimationEvent(manager.getPosition(), false));
            }
        });
    }

    public void play() {
        timeline.play();
    }

    public void pause() {
        timeline.pause();
    }

    public void finish(TurtlePosition position) {
        timeline.pause();
        progress.set(target);
        whenFinished.handle(new EndAnimationEvent(position, true));
    }

    public void onFinished(DrawingManager manager, EventHandler<EndAnimationEvent> handler) {
        this.manager = manager;
        whenFinished = handler;
    }
}
