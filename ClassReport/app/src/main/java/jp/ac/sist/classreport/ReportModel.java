package jp.ac.sist.classreport;

/**
 * 保存データの保持クラス
 * プロパティ風に使いたいのでセッターゲッターは作らないこと
 * 可読性重視のため、enumおよび配列にはしない
 * 2018.07.13 R.Suzuki  新規作成
 */
public class ReportModel {
    public String m_cDay;       // 日付
    public String m_cPeriod;    // 時限
    public String m_cTeacher;   // 担当教員
    public String m_cKamoku;    // 科目
    public String m_cDetails;   // 詳細

    /**
     * コンストラクタ 使わせない
     * 2018.07.13 R.Suzuki  新規作成
     */
    private ReportModel() {}

    /**
     * コンストラクタ 書き込み用
     * @param cDay      日付
     * @param cPeriod   時限
     * @param cTeacher  担当教員
     * @param cKamoku   科目
     * @param cDetails  詳細
     * 2018.07.13 R.Suzuki  新規作成
     */
    public ReportModel(String cDay,
                      String cPeriod,
                      String cTeacher,
                      String cKamoku,
                      String cDetails) {
        m_cDay      = cDay;
        m_cPeriod   = cPeriod;
        m_cTeacher  = cTeacher;
        m_cKamoku   = cKamoku;
        m_cDetails  = cDetails;
    }

    /**
     * 空のものがあるか
     * @return 空 or 空ではない
     * 2018.07.13 R.Suzuki  新規作成
     */
    public boolean isEmpty() {
        boolean bRet = false;
        if(m_cDay.isEmpty()
        || m_cPeriod.isEmpty()
        || m_cTeacher.isEmpty()
        || m_cKamoku.isEmpty()
        || m_cDetails.isEmpty()){
            bRet = true;
        }
        return bRet;
    }
}
