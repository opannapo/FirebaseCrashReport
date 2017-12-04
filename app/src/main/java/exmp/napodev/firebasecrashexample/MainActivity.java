package exmp.napodev.firebasecrashexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;

import exmp.napodev.firebasecrashexample.entity.Music;
import exmp.napodev.firebasecrashexample.entity.User;
import exmp.napodev.firebasecrashexample.exceptions.EntityNullException;

public class MainActivity extends AppCompatActivity {
    TextView t1;
    Music musicNull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = findViewById(R.id.t1);

        FirebaseCrash.log("Activity created");

        method1();
        method2();
        method3();
        userPrint();
        action();
    }


    public void method1() {
        Log.d("method1", "this is method1");
        FirebaseCrash.log("this is  method1");
    }

    public void method2() {
        Log.d("method2", "this is  method2");
        FirebaseCrash.log("this is  method2");
    }

    public void method3() {
        Log.d("method3", "this is  method3");
        FirebaseCrash.log("this is  method2");
    }

    public void userPrint() {
        User u = new User();
        u.setId(123);
        u.setName("Opannapo");

        Log.d("userPrint", String.valueOf(u.getId()));
        Log.d("userPrint", u.getName());

        FirebaseCrash.log("userPrint " + String.valueOf(u.getId()));
        FirebaseCrash.log("userPrint " + u.getName());
    }

    public void action() {
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Music m = new Music();
                m.setId(10);
                m.setGendre("Rock");
                musicNull = m;
                Toast.makeText(MainActivity.this, musicNull.getGendre(), Toast.LENGTH_SHORT).show();*/

                actionNullCase();
            }
        });
    }

    private void actionNullCase() {
        //Report Custom Exception EntityNullException
        if (musicNull == null) {
            FirebaseCrash.report(new EntityNullException(Music.class));
        }

        //NULL POINTER EXCP, report to firebase
        Toast.makeText(MainActivity.this, musicNull.getGendre(), Toast.LENGTH_SHORT).show();
    }
}
