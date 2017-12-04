# FirebaseCrashReport
Panduan implementasi Firebase CrashReport pada project Android


## Step 1, (Android Project)
- Buat Android Application Project

## Step 2, (Firebase Project)
- Buat Project di Firebase Console https://console.firebase.google.com/.
<p align="left"><img src="https://github.com/opannapo/FirebaseCrashReport/blob/master/images/step2_1.jpg" width="350"/></p>

- Lalu download, Konfigurasi Google Service (google-services.json).
<p align="left"><img src="https://github.com/opannapo/FirebaseCrashReport/blob/master/images/Step2_2.jpg" width="350"/></p>

- Setelah google-services.json di download, tempatkan file tersebut di directory Android Project.
<p align="left"><img src="https://github.com/opannapo/FirebaseCrashReport/blob/master/images/Step2_3.png" width="350"/></p> 

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

<p align="left"><img src="https://github.com/opannapo/FirebaseCrashReport/blob/master/images/Step4_1.png" width="500"/></p>

## Step 5, Monitoring Crash Report pada Firebase Console
- Login ke Akun Firebase
- Pilih Project
- Masuk pada menu STABILITY -> Crash Reporting (Kiri Menu)
- Dapat juga menggunakan filter berdasarkan Versi Applikasi untuk menampilkan list Crash Reporting

<p align="left"><img src="https://github.com/opannapo/FirebaseCrashReport/blob/master/images/Step5_1.png" width="500"/></p>

## Step 6, ProGuard
Apabila aplikasi menggunakan konfigurasi ProGuard, maka perlu dilakukan beberapa konfigurasi tambahan di dalam Firebase Project. Hal ini bertujuan agar Report yang didapatkan Sesuai dengan Code Applikasi yang sebenarnya. Sebagaimana diketahui salah satu fungsi dari ProGuard adalah sebagai tool proteksi yang digunakan untuk obfuscate code.
<br>
Untuk lebih jelasnya, gambar dibawah ini akan menunjukan bagaimana report yang didapat ketika applikasi menggunakan ProGuard

<p align="left"><img src="https://github.com/opannapo/FirebaseCrashReport/blob/master/images/Step6_1.png" width="500"/></p>
<p align="left"><img src="https://github.com/opannapo/FirebaseCrashReport/blob/master/images/Step6_2.png" width="500"/></p>

Firebase Crash Report akan mengelompokan/grouping laporan error berdasarkan jenis kesalahan yang sama.<br>
Pada gambar diatas terdapat 4 Group crash report.<br>
Padahal sebenarnya 4 group diatas mencangkup 2 jenis kesalahan yang sama. Hanya saja ketika project menggunakan ProGuard, code dari aplikasi akan diproteksi (obfuscate code). Sehingga Firebase akan menganggap kesalahan ini dari sumber kode yang berbeda, dan dikelompokan menjadi satu jenis kesalahan baru.
<br>
<br>
Untuk Membuat Code anda dikenali oleh Firebase setelah disamarkan dengan penggunakan ProGuard, anda perlu melakukan beberapa langkah dibawah ini.

- Upload File Pemetaan
File pemetaan yang dihasilkan oleh ProGuard terdapat pada directory app/build/outputs/mapping/release/mapping.txt.
<p align="left"><img src="https://github.com/opannapo/FirebaseCrashReport/blob/master/images/Step6_4.png" width="500"/></p>
<p align="left"><img src="https://github.com/opannapo/FirebaseCrashReport/blob/master/images/Step6_5.png" width="500"/></p>



## Step 7, Perbandingan
Perbandingan sebelum konfigurasi pemetaan dilakukan dan sesudahnya.
<br>
<br>
Pada kasus ini saya membuat Custom Exception Dengan nama EntityNullException, yand terdapat pada package <b>exmp.napodev.firebasecrashexample.exceptions</b>.<br>
Error terjadi ketika method <b>"actionNullCase"</b> di eksekusi melalui OnClick<br><br>

### Gambar1
<p align="left"><img src="https://github.com/opannapo/FirebaseCrashReport/blob/master/images/Step7_1.png" width="500"/></p> 

Gambar1, Sebelum dilakukan konfigurasi pemetaan ProGuard di dalam Firebase Project.
- Pada gambar pertama dilaporkan Exception yang berhasil ditangkap adalah class <b>"a"</b> didalam package <b>"exmp.napodev.firebasecrashexample.b"</b> . 
- Detail Exception <b>"Exception exmp.napodev.firebasecrashexample.b.a: Class"</b>.
- Kesalahan/Error terjadi pada code didalam method <b>"p()"</b>, dengan keterangan <b>exmp.napodev.firebasecrashexample.MainActivity.p ()</b>.
 
### Gambar2
<p align="left"><img src="https://github.com/opannapo/FirebaseCrashReport/blob/master/images/Step7_2.png" width="500"/></p>

Gambar2, Setelah file pemetaan ProGuard diupload ke Firebase Project.<br>
- Pada gambar kedua dilaporkan Exception yang berhasil ditangkap adalah class <b>"EntityNullException"</b> didalam package <b>"exmp.napodev.firebasecrashexample.exceptions"</b> .
- Detail Exception <b>"Exception exmp.napodev.firebasecrashexample.exceptions.EntityNullException: Class"</b>.
- Kesalahan/Error terjadi pada code didalam method <b>"actionNullCase()"</b>, dengan keterangan <b>exmp.napodev.firebasecrashexample.MainActivity.actionNullCase (MainActivity.java)</b>.



# Selesai
