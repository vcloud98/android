package com.example.a20240804

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() { // 앱 화면 정의

    // 변수 선언하되 초기화는 나중에 할 수 있도록 설정
    private lateinit var myDBHelper: MyDBHelper
    private lateinit var edtName: EditText
    private lateinit var edtNumber: EditText
    private lateinit var edtResultName: EditText
    private lateinit var edtResultNumber: EditText
    private lateinit var btnInit: Button
    private lateinit var btnInsert: Button
    private lateinit var btnSelect: Button
    // 데이터베이스 객체 저장
    private lateinit var sqlDB: SQLiteDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // sqlite3
        //  - 데이터베이스 중 가장 간단하게 사용해 데이터를 저장하는 역할
        //  - 스마트폰 안에 내용을 저장하는 방식

        // 1. 뷰 초기화
        edtName = findViewById(R.id.edtName)
        edtNumber = findViewById(R.id.edtNumber)
        edtResultName = findViewById(R.id.edtResultName)
        edtResultNumber = findViewById(R.id.edtResultNumber)
        btnInit = findViewById(R.id.btnInit)
        btnInsert = findViewById(R.id.btnInsert)
        btnSelect = findViewById(R.id.btnSelect)

        // 데이터베이스 헬퍼 초기화
        myDBHelper  = MyDBHelper(this)

        // 초기화 버튼에 이벤트 작성
        btnInit.setOnClickListener {
        }

        // 입력 버튼
        btnInsert.setOnClickListener {
            try {
                val name = edtName.text.toString()
                val number = edtNumber.text.toString().toInt()

                // 추가, 삽입 가능한 데이터베이스 객체 열기
                sqlDB = myDBHelper.writableDatabase
                // sql 실행
                sqlDB.execSQL(
                    "insert into groupTBL values( " +
                            "$name, $number)"
                )
                // sql 닫기
                sqlDB.close()
                Toast.makeText(applicationContext, "입력됨", Toast.LENGTH_SHORT).show()
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

        // 조회 버튼
        btnSelect.setOnClickListener {

        }
    }


    // sqlite를 사용하기 위해 SQListeOpenHelper 상속
    // inner 내부 클래스
    //  - 내부 클래스 멤버들은 외부 클래스 멤버에 접근 가능
    //  - class 안에 class가 들어간 모습
    //  - 생략하고 중첩 클래스로 사용 해도 무방
    //      > 내부 클래스로 선언되는 db들이 외부 클래스의 멤버를 사용하지 않기 때문

    // 위 데이터를 확인하고 싶을 경우 경로
    // view / tool windows / device explorer / data / 패키지명 / databases / db파일
    // files 폴더도 함께 있음

    class MyDBHelper(context: Context): SQLiteOpenHelper
        (context, "groupDB", null, 1) {
        override fun onCreate(db: SQLiteDatabase) {
            // 데이터베이스가 처음 생성될 때 호출
            db.execSQL("create table groupTBL (" +
                    "gName char(30) primary key, " +
                    "gNumber integer);")
        }

        override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
            // 데이터베이스가 업그레이드 될 때 호출
            db.execSQL("DROP TABLE IF EXISTS groupTBL")
            onCreate(db)
        }
    }
}

