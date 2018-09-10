package jp.ac.sist.classreport;

import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

// メイン制御クラス
public class MainCtrl implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private MainActivity m_cActivity;   // メインアクティビティ
    private FileCtrl     m_cFileCtrl;   // ファイル制御クラス

    /**
     * コンストラクタ
     * @param  cActivity アクティビティ
     * 2018.07.13 R.Suzuki  新規作成
     */
    MainCtrl(MainActivity cActivity) {
        m_cActivity = cActivity;
        m_cFileCtrl = new FileCtrl(m_cActivity);
        m_cActivity.setClassName(m_cFileCtrl.readClassName());
    }

    /**
     * ラジオボタンの変更イベント
     * @param  cGroup ラジオボタンのグループ
     * @param nCheckedId チェックされているボタンのID
     * 2018.07.13 R.Suzuki  新規作成
     */
    @Override
    public void onCheckedChanged(RadioGroup cGroup, int nCheckedId) {
        if (nCheckedId == -1) {
            Toast.makeText(m_cActivity, m_cActivity.getString(R.string.err_not_input_period), Toast.LENGTH_LONG).show();
        } else {
            ReportModel cReportModel = m_cFileCtrl.readFile(m_cActivity.getDay(), m_cActivity.getInputPeriod());   // ファイル読み込み

            String cTeacher = "";
            String cKamoku = "";
            String cDetails = "";
            if(cReportModel != null) {  // 中身がある場合
                cTeacher = cReportModel.m_cTeacher;
                cKamoku  = cReportModel.m_cKamoku;
                cDetails = cReportModel.m_cDetails;
            }
            m_cActivity.setTeacherName(cTeacher);
            m_cActivity.setKamokuName(cKamoku);
            m_cActivity.setDetails(cDetails);
        }
    }

    /**
     * ボタンのイベント
     * @param v ボタンのView
     * 2018.07.13 R.Suzuki  新規作成
     */
    @Override
    public void onClick(View v) {
        saveData();
    }

    /**
     * 教務日誌ファイル保存
     * 2018.07.13 R.Suzuki  新規作成
     */
    public void saveData() {
        boolean bResult = m_cFileCtrl.writeClassName(m_cActivity.getClassName()); // クラス名書き込み
        if (bResult == false) {
            Toast.makeText(m_cActivity, m_cActivity.getString(R.string.err_check_permission1), Toast.LENGTH_LONG).show();
        }
        ReportModel cReportModel = new ReportModel(m_cActivity.getDay(),          // 教務日誌書き込み
                                                    m_cActivity.getInputPeriod(),
                                                    m_cActivity.getTeacherName(),
                                                    m_cActivity.getKamokuName(),
                                                    m_cActivity.getDetails());
        if (cReportModel.isEmpty() == true) {
            // メッセージを表示して終了
            Toast.makeText(m_cActivity, m_cActivity.getString(R.string.err_not_input),  Toast.LENGTH_LONG).show();
            return;
        }

        bResult = m_cFileCtrl.writeReportFile(cReportModel);   // ファイル書き込み
        if (bResult == true) {
            Toast.makeText(m_cActivity, m_cActivity.getString(R.string.write_end), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(m_cActivity, m_cActivity.getString(R.string.err_check_permission2), Toast.LENGTH_LONG).show();
        }
    }
}
