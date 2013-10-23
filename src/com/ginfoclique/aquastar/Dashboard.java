package com.ginfoclique.aquastar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by vinothrkumar on 18/10/13.
 */
public class Dashboard extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
    }
    public void customer_list(View view)
    {
        Intent Customer_listIntent = new Intent(getApplicationContext(),CustomerList.class);
        startActivity(Customer_listIntent);

    }
    public void serviceList(View view)
    {
    	Intent service_listIntent = new Intent(getApplicationContext(),ServiceList.class);
        startActivity(service_listIntent);
    }
    public void issueList(View view)
    {
    	Intent issue_listIntent = new Intent(getApplicationContext(),IssueList.class);
        startActivity(issue_listIntent);
    }
}
