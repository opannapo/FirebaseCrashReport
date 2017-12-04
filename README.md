# FirebaseCrashReport
Panduan implementasi Firebase CrashReport pada project Android


## Step 1, (Android Project)
- Buat Android Application Project

## Step 2, (Firebase Project)
- Buat Project di Firebase Console https://console.firebase.google.com/.
<p align="center"><img src="https://github.com/opannapo/FirebaseCrashReport/blob/master/images/step2_1.jpg" width="350"/></p>

- Lalu download, Konfigurasi Google Service (google-services.json).
<p align="center"><img src="https://github.com/opannapo/FirebaseCrashReport/blob/master/images/Step2_2.jpg" width="350"/></p>

- Setelah google-services.json di download, tempatkan file tersebut di directory Android Project.
<p align="center"><img src="https://github.com/opannapo/FirebaseCrashReport/blob/master/images/Step2_3.png" width="350"/></p> 

## Step 3, Gradle
Pada kasus ini saya masih menggunakan gradle 2.3

- Konfigurasi Gradle pada root project
```gradle
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.3.3'
        classpath 'com.google.gms:google-services:3.1.0'
        classpath 'com.google.firebase:firebase-plugins:1.1.1'
    }
}

allprojects {
    repositories {
        jcenter()
        maven {
            url 'https://maven.google.com'
        }
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}
```

- Gradle App Module
```gradle
...

dependencies {
    ...
    compile fileTree(include: ['*.jar'], dir: 'libs')
    compile 'com.android.support:appcompat-v7:26.+' 
    compile 'com.google.firebase:firebase-crash:11.6.2'
}

apply plugin: 'com.google.gms.google-services'
apply plugin: 'com.google.firebase.firebase-crash'
...
```

## Step 4, Java
- Firebase Log
```java
...
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         
        FirebaseCrash.log("Activity created"); //LOG
    }
    
...

```

- Report Exception
```java
...
    try {
        JSONObject obj = new JSONObject("{\"id\": 1,\"name\": \"opannapo\"}");
        int id = obj.getInt("id");
        String name = obj.getString("username"); //JSONException: No value for username

        Toast.makeText(this, "Data:" + String.valueOf(id) + "|" + name, Toast.LENGTH_SHORT).show();
    } catch (JSONException e) {
        e.printStackTrace();
        FirebaseCrash.log("JSON PARSING ERROR");
        FirebaseCrash.report(e);
    }
...
```
