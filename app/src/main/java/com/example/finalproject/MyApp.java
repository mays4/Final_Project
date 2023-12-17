package com.example.finalproject;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApp extends Application {

    ArrayList<Instructor> listOfInstructors = new ArrayList<>(0);
    static ExecutorService executorService = Executors.newFixedThreadPool(4);
    static Handler mainLooperHandler = new Handler(Looper.getMainLooper());





}
