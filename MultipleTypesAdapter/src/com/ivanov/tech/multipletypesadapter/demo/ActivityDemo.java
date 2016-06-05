package com.ivanov.tech.multipletypesadapter.demo;

import com.ivanov.tech.multipletypesadapter.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;


/**
 * Created by Igor on 15.01.15.
 */
public class ActivityDemo extends AppCompatActivity {

	private String TAG=ActivityDemo.class.getSimpleName();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demo);                
        showDemoMD();
    }

    public void showDemo(){
   	 FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_container, new FragmentDemo())
                .commit();
    }
    
    public void showDemoMD(){
      	 FragmentManager fragmentManager = getSupportFragmentManager();
           fragmentManager.beginTransaction()
                   .replace(R.id.main_container, new FragmentDemoMD())
                   .commit();           
    }
    
}
