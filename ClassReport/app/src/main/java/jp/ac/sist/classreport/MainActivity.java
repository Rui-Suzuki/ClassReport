package jp.ac.sist.classreport;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// メイン
public class MainActivity extends AppCompatActivity {
    private final int nPermissionReqId = 100;   // パーミッションのリクエスト
    private MainCtrl m_cMainCtrl;               // メインのコントロール IOアクセスはここ経由

    /**
     * 日付の文字列取得
     * @return 日付(文字列データ)
     * 2018.07.13 R.Suzuki  新規作成
     */
    private String getNowDay() {
        final DateFormat cDateFormat = new SimpleDateFormat("yyyyMMdd");    // フォーマット指定
        final Date cDate = new Date(System.currentTimeMillis());            // 現在に書き換え
        return cDateFormat.format(cDate);
    }

    /**
     * クラス生成時の処理
     * @param savedInstanceState 標準APIの仕様
     * 2018.07.13 R.Suzuki  デフォルトのものを改造 自前のコントロールクラスの生成を追加
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                                               Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // 何もしない
            } else {
                ActivityCompat.requestPermissions(this,
                                                   new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                   nPermissionReqId);
            }
        }

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);  // ナビゲーションにリスナーを設定

        m_cMainCtrl = new MainCtrl(this);               // メインコントロール生成
        RadioGroup cRadioGroup = findViewById(R.id.RadioPeriod);
        cRadioGroup.setOnCheckedChangeListener(m_cMainCtrl);       // ラジオボタン リスナー登録

        Button cButton = findViewById(R.id.button_save);
        cButton.setOnClickListener(m_cMainCtrl);                   // ボタン リスナー登録
    }

    /**
     * 標準のナビゲーション処理
     * 2018.07.13 R.Suzuki  新規作成　デフォルトのものを改造 Saveボタンに変更
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        // デフォルトのナビゲーションのイベント
        // 授業でネタにしたいのでナビのリスナーはメインに残す
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_save:
                    m_cMainCtrl.saveData();
                    return true;
            }
            return false;
        }
    };

    // IF関数=================================================================
    /**
     * 日付取得
     * @return 日付
     * 2018.07.13 R.Suzuki  新規作成
     */
    public String getDay() {
        EditText cEdit = findViewById(R.id.edit_day);
        return cEdit.getText().toString();
    }

    /**
     * 入力時限取得
     * @return 入力時限
     * 2018.07.13 R.Suzuki  新規作成
     */
    public String getInputPeriod() {
        String cStr = "";
        RadioGroup cRadioGroup = findViewById(R.id.RadioPeriod);
        int nCheckId = cRadioGroup.getCheckedRadioButtonId();
        if (nCheckId != -1) {    // 何か指定済み
            RadioButton cRadioButton = findViewById(nCheckId);   // 指定されているボタンを取得
            cStr = cRadioButton.getText().toString();
        }
        return cStr;
    }

    /**
     * クラス名取得
     * @return クラス名
     * 2018.07.13 R.Suzuki  新規作成
     */
    public String getClassName() {
        EditText cEdit = findViewById(R.id.edit_class);
        return cEdit.getText().toString();
    }

    /**
     * 担当名取得
     * @return 担当名
     * 2018.07.13 R.Suzuki  新規作成
     */
    public String getTeacherName() {
        EditText cEdit = findViewById(R.id.edit_tantou);
        return cEdit.getText().toString();
    }

    /**
     * 科目名取得
     * @return 科目名
     * 2018.07.13 R.Suzuki  新規作成
     */
    public String getKamokuName() {
        EditText cEdit = findViewById(R.id.edit_kamoku);
        return cEdit.getText().toString();
    }

    /**
     * 内容取得
     * @return 内容
     * 2018.07.13 R.Suzuki  新規作成
     */
    public String getDetails() {
        EditText cEdit = findViewById(R.id.edit_naiyou);
        return cEdit.getText().toString();
    }

    /**
     * クラス名設定
     * @param  cClassName クラス名
     * 2018.07.13 R.Suzuki  新規作成
     */
    public void setClassName(String cClassName) {
        EditText cEdit = findViewById(R.id.edit_class);
        cEdit.setText(cClassName);
    }

    /**
     * 担当名設定
     * @param  cTeacherName 担当名
     * 2018.07.13 R.Suzuki  新規作成
     */
    public void setTeacherName(String cTeacherName) {
        EditText cEdit = findViewById(R.id.edit_tantou);
        cEdit.setText(cTeacherName);
    }

    /**
     * 科目名設定
     * @param  cKamokuName 科目名
     * 2018.07.13 R.Suzuki  新規作成
     */
    public void setKamokuName(String cKamokuName) {
        EditText cEdit = findViewById(R.id.edit_kamoku);
        cEdit.setText(cKamokuName);
    }

    /**
     * 内容設定
     * @param  cDetails 内容
     * 2018.07.13 R.Suzuki  新規作成
     */
    public void setDetails(String cDetails) {
        EditText cEdit = findViewById(R.id.edit_naiyou);
        cEdit.setText(cDetails);
    }

    /**
     * 復帰時の処理
     * 2018.07.13 R.Suzuki  新規作成
     */
    @Override
    public void onResume() {
        super.onResume();
        EditText cEdit = findViewById(R.id.edit_day);   // 日の可変欄を取得
        cEdit.setText(getNowDay());                      // 日付を当日に変更
    }
}
