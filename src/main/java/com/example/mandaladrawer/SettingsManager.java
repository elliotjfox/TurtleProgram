package com.example.mandaladrawer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class SettingsManager {

    public static final double ROTATION_ANIMATION_COEFFICIENT = 7.5;
    public static final double MOVEMENT_ANIMATION_COEFFICIENT = 10;



    public static final DoubleProperty animationSpeed = new SimpleDoubleProperty(0);
    public static final DoubleProperty rotationAnimationSpeed = new SimpleDoubleProperty(0);
    public static final DoubleProperty movementAnimationSpeed = new SimpleDoubleProperty(0);
}
