package jp.ac.sist.classreport;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

// ファイルアクセスの制御クラス
public class FileCtrl {
    private Context m_cContext; // コンテキスト 実体はMainActivity

    /**
     * コンストラクタ 使わせない
     * 2018.07.13 R.Suzuki  新規作成
     */
    private FileCtrl() {
    }

    /**
     * コンストラクタ
     * @param cContext コンテキスト
     * 2018.07.13 R.Suzuki  新規作成
     */
    public FileCtrl(Context cContext) {
        m_cContext = cContext;
    }

    /**
     * 教務日誌保存ファイルパス取得
     * @param cDay 日付
     * @param cPeriod 時限数
     * @return 保存するパス
     * 2018.07.13 R.Suzuki  新規作成
     */
    private String getFilePath(String cDay, String cPeriod) {
        String cFileName = cDay + "_" + cPeriod + ".csv";   // ファイル名
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/" + cFileName;   // パス付
    }

    /**
     * クラス名(学科＋学年)保存ファイルパス取得
     * @return 保存するパス
     * 2018.07.13 R.Suzuki  新規作成
     */
    private String getClassFilePath() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/" + "class.txt";
    }

    /**
     * メディア情報更新(≒キャッシュフラッシュ)
     * @param cFilePath 更新したファイルのパス
     * 2018.07.13 R.Suzuki  新規作成
     */
    private void scanMedia(String cFilePath) {
        Uri uri = Uri.parse("file:/" + cFilePath);
        m_cContext.sendBroadcast(new Intent(    // Intentでフォルダ情報更新
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
    }

    /**
     * クラス名保存
     * @param cClassName クラス名
     * 2018.07.13 R.Suzuki  新規作成
     */
    public boolean writeClassName(String cClassName) {
        return writeFile(getClassFilePath(), cClassName);
    }

    /**
     * 教務日誌書き込み
     * @param cReportModel 日誌データ
     * @return 書き込み成功 or 失敗
     * 2018.07.13 R.Suzuki  新規作成
     */
    public boolean writeReportFile(ReportModel cReportModel) {
        String cFilePath = getFilePath(cReportModel.m_cDay, cReportModel.m_cPeriod);     // ファイルパス取得
        String cWriteStr = cReportModel.m_cTeacher + "," + cReportModel.m_cKamoku + "," + cReportModel.m_cDetails;   // 書き込み用データ作成
        return writeFile(cFilePath, cWriteStr);    // 書き込み
    }

    /**
     * ファイル書き込み 共通処理
     * @param cFilePath 書き込むパス
     * @param cWriteStr 書き込む文字列
     * @return 書き込み成功 or 失敗
     * 2018.07.13 R.Suzuki  新規作成
     */
    private boolean writeFile(String cFilePath, String cWriteStr) {
        boolean bRet = true;
        try {
            File cFile = new File(cFilePath);                                                   // ファイルオブジェクト作成
            FileOutputStream cFileOutputStream = new FileOutputStream(cFile, false);    // 追記なし設定で出力
            OutputStreamWriter cOutputStreamWriter = new OutputStreamWriter(cFileOutputStream, "UTF-8");
            BufferedWriter cBufWriter = new BufferedWriter(cOutputStreamWriter);
            cBufWriter.write(cWriteStr); // 書き込み
            cBufWriter.flush();          // フラッシュして反映
            cBufWriter.close();          // 閉じる
        } catch (Exception e) {
            e.printStackTrace();
            bRet = false;           // 失敗
        }
        scanMedia(cFilePath);       // メディア情報更新(≒キャッシュフラッシュ)
        return bRet;
    }

    /**
     * 日誌読み込み
     * @param cDay 日付
     * @param cPeriod 時限数
     * @return 日誌データ
     * 2018.07.13 R.Suzuki  新規作成
     */
    public ReportModel readFile(String cDay, String cPeriod) {
        ReportModel cReportModel = null;
        try {
            String cFilePath = getFilePath(cDay, cPeriod); // ファイルパス取得
            File cFile = new File(cFilePath);
            BufferedReader cReader = new BufferedReader(new FileReader(cFile));
            String cLine = cReader.readLine();  // 1行読み込み
            cReader.close();

            if (cLine.isEmpty() != true) {      // 空でなければ
                StringTokenizer cStrTokenizer = new StringTokenizer(cLine, ","); // ,まで読む
                cReportModel = new ReportModel(cDay,
                                               cPeriod,
                                               cStrTokenizer.nextToken(),   // Teacher
                                               cStrTokenizer.nextToken(),   // Kamoku
                                               cStrTokenizer.nextToken());  // Details
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cReportModel;
    }

    /**
     * クラス名読み込み
     * @return クラス名
     * 2018.07.13 R.Suzuki  新規作成
     */
    public String readClassName() {
        String cRet = "";
        try {
            String cFilePath = getClassFilePath(); // ファイルパス取得
            File cFile = new File(cFilePath);
            BufferedReader cReader = new BufferedReader(new FileReader(cFile));
            String cLine = cReader.readLine();  // 1行読み込み
            cReader.close();

            if (cLine.isEmpty() != true) {      // 空でなければ
                StringTokenizer cStrTokenizer = new StringTokenizer(cLine, ","); // ,まで読む
                cRet = cStrTokenizer.nextToken();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cRet;
    }
}
