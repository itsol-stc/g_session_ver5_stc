package jp.groupsession.v2.api.file.shortcutlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.fil.GSConstFile;

/**
 * <br>[機  能] WEBAPIファイル管理ショートカット一覧を取得するDAO
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiFileShortcutListDao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiFileShortcutListDao.class);

    /**
     * <p>デフォルトコンストラクタ
     * @param con DBコネクション
     */
    public ApiFileShortcutListDao(Connection con) {
        super(con);
    }

    /**
     * <p>ショートカット一覧ディレクトリ詳細情報
     * @param usModel BaseUserModel
     * @param cabType 0:共有キャビネット / 1: 個人キャビネット
     * @param errlKbn 電帳法対応区分 0:電帳法に対応していない, 1:電帳法に対応している 
     * @return FILE_SHORTCUT_CONFModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<ApiFileShortcutListModel> getApiShortCutList(
            BaseUserModel usModel, int cabType, int errlKbn)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ApiFileShortcutListModel> ret = new ArrayList<ApiFileShortcutListModel>();
        con = getCon();

        try {

            CommonBiz  commonBiz = new CommonBiz();
            boolean isAdmin = commonBiz.isPluginAdmin(con, usModel, GSConstFile.PLUGIN_ID_FILE);
            int usrSid = usModel.getUsrsid();

            //SQL文
            SqlBuffer sql = new SqlBuffer();


            sql.addSql("select");
            sql.addSql("    DIRECTORY.FDR_SID,");
            sql.addSql("    DIRECTORY.USR_SID,");
            sql.addSql("    DIRECTORY.FCB_SID,");
            sql.addSql("    DIRECTORY.FDR_PARENT_SID,");
            sql.addSql("    DIRECTORY.FDR_NAME,");
            sql.addSql("    DIRECTORY.FDR_VERSION,");
            sql.addSql("    DIRECTORY.FDR_KBN,");
            sql.addSql("    DIRECTORY.BIN_SID,");
            sql.addSql("    DIRECTORY.FFL_FILE_SIZE,");
            sql.addSql("    DIRECTORY.FDR_EDATE,");
            sql.addSql("    DIRECTORY.FFL_LOCK_KBN,");
            sql.addSql("    DIRECTORY.FFL_LOCK_USER,");
            sql.addSql("    DIRECTORY.FDR_EUID,");
            sql.addSql("    DIRECTORY.FDR_EGID,");
            sql.addSql("    DIRECTORY.USI_SEI,");
            sql.addSql("    DIRECTORY.USI_MEI,");
            sql.addSql("    DIRECTORY.USR_JKBN,");
            sql.addSql("    DIRECTORY.USR_UKO_FLG,");
            sql.addSql("    DIRECTORY.GRP_NAME,");
            sql.addSql("    DIRECTORY.GRP_JKBN,");
            sql.addSql("    DIRECTORY.FCB_ERRL,");
            if (!isAdmin) {
            sql.addSql("    case");
            sql.addSql("      when CABINET.FCB_SID is null then DIRECTORY.FDA_AUTH");
            sql.addSql("      else 0");
            sql.addSql("    end WRITEKBN");
            } else {
                sql.addSql(" 0 as WRITEKBN");
            }
            sql.addSql("  from");
            sql.addSql("    (");
            sql.addSql(" select");
            sql.addSql("        FILE_SHORTCUT_CONF.FDR_SID,");
            sql.addSql("        FILE_SHORTCUT_CONF.USR_SID,");
            sql.addSql("        FILE_SHORTCUT_CONF.FSC_ADATE,");
            sql.addSql("        FILE_DIRECTORY.FCB_SID,");
            sql.addSql("        FILE_DIRECTORY.FDR_PARENT_SID,");
            sql.addSql("        FILE_DIRECTORY.FDR_NAME,");
            sql.addSql("        FILE_DIRECTORY.FDR_VERSION,");
            sql.addSql("        FILE_DIRECTORY.FDR_KBN,");
            sql.addSql("        coalesce(FILE_FILE_BIN.BIN_SID, -1) as BIN_SID,");
            sql.addSql("        coalesce(FILE_FILE_BIN.FFL_FILE_SIZE, -1) as FFL_FILE_SIZE,");
            sql.addSql("        FILE_DIRECTORY.FDR_EDATE,");
            sql.addSql("        coalesce(FILE_FILE_BIN.FFL_LOCK_KBN, '0') as FFL_LOCK_KBN,");
            sql.addSql("        coalesce(FILE_FILE_BIN.FFL_LOCK_USER, -1) as FFL_LOCK_USER,");
            sql.addSql("        FILE_DIRECTORY.FDR_EUID,");
            sql.addSql("        FILE_DIRECTORY.FDR_EGID,");
            sql.addSql("        FILE_CABINET.FCB_ERRL,");
            sql.addSql("        CMN_USRM_INF.USI_SEI,");
            sql.addSql("        CMN_USRM_INF.USI_MEI,");
            sql.addSql("        CMN_USRM.USR_JKBN,");
            sql.addSql("        CMN_USRM.USR_UKO_FLG,");
            sql.addSql("        CMN_GROUPM.GRP_NAME,");
            sql.addSql("        CMN_GROUPM.GRP_JKBN");
            if (!isAdmin) {
                sql.addSql("        ,case");
                sql.addSql("          when FDA_AUTH is null then 0");
                sql.addSql("          when FDA_AUTH = 1  then 0");
                sql.addSql("          else 1");
                sql.addSql("        end FDA_AUTH");
            }
            sql.addSql("      from");
            sql.addSql("        FILE_SHORTCUT_CONF");
            sql.addSql("      left join");
            sql.addSql("        FILE_DIRECTORY");
            sql.addSql("      on");
            sql.addSql("        FILE_SHORTCUT_CONF.FDR_SID = FILE_DIRECTORY.FDR_SID");
            if (!isAdmin) {
                sql.addSql("      left join");
                sql.addSql("        (");
                sql.addSql("          select ");
                sql.addSql("            ? as FDR_SID,");
                sql.addSql("            ? as FDA_AUTH");
                sql.addIntValue(GSConstFile.DIRECTORY_ROOT);
                sql.addIntValue(Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE));
                sql.addSql("          union all");
                sql.addSql("          select");
                sql.addSql("            FILE_DACCESS_CONF.FDR_SID as FDR_SID,");
                sql.addSql("            max(FILE_DACCESS_CONF.FDA_AUTH) as FDA_AUTH");
                sql.addSql("          from");
                sql.addSql("            FILE_DACCESS_CONF");
                sql.addSql("          where");
                sql.addSql("            (");
                sql.addSql("              USR_KBN = ?");
                sql.addSql("              and");
                sql.addSql("              USR_SID = ?");
                sql.addSql("            )");
                sql.addIntValue(GSConstFile.USER_KBN_USER);
                sql.addIntValue(usrSid);
                sql.addSql("            or");
                sql.addSql("              (");
                sql.addSql("                USR_KBN = ?");
                sql.addSql("                and");
                sql.addSql("                USR_SID in (");
                sql.addSql("                  select");
                sql.addSql("                    GRP_SID");
                sql.addSql("                  from");
                sql.addSql("                    CMN_BELONGM");
                sql.addSql("                  where");
                sql.addSql("                    CMN_BELONGM.USR_SID = ?");
                sql.addSql("                )");
                sql.addSql("              )");
                sql.addIntValue(GSConstFile.USER_KBN_GROUP);
                sql.addIntValue(usrSid);
                sql.addSql("          group by");
                sql.addSql("            FILE_DACCESS_CONF.FDR_SID");
                sql.addSql("        ) DACCESS");
                sql.addSql("      on");
                sql.addSql("        FILE_DIRECTORY.FDR_ACCESS_SID = DACCESS.FDR_SID");
            }
            sql.addSql("      left join");
            sql.addSql("        FILE_FILE_BIN");
            sql.addSql("      on ");
            sql.addSql("        FILE_DIRECTORY.FDR_SID = FILE_FILE_BIN.FDR_SID");
            sql.addSql("      and ");
            sql.addSql("        FILE_DIRECTORY.FDR_VERSION = FILE_FILE_BIN.FDR_VERSION");
            sql.addSql("      left join");
            sql.addSql("        CMN_USRM");
            sql.addSql("      on");
            sql.addSql("         FILE_DIRECTORY.FDR_EUID = CMN_USRM.USR_SID");
            sql.addSql("      left join");
            sql.addSql("        CMN_USRM_INF");
            sql.addSql("      on");
            sql.addSql("         FILE_DIRECTORY.FDR_EUID = CMN_USRM_INF.USR_SID");
            sql.addSql("      left join");
            sql.addSql("         CMN_GROUPM");
            sql.addSql("      on");
            sql.addSql("        FILE_DIRECTORY.FDR_EGID = CMN_GROUPM.GRP_SID");
            sql.addSql("      inner join");
            sql.addSql("      (");
            sql.addSql("        select");
            sql.addSql("          FDR_SID,");
            sql.addSql("          max(FDR_VERSION) as MAXVERSION");
            sql.addSql("        from");
            sql.addSql("          FILE_DIRECTORY");
            sql.addSql("        group by");
            sql.addSql("          FDR_SID");
            sql.addSql("      ) DIR_MAXVERSION");
            sql.addSql("      on ");
            sql.addSql("       DIR_MAXVERSION.MAXVERSION = FILE_DIRECTORY.FDR_VERSION");
            sql.addSql("      and");
            sql.addSql("       DIR_MAXVERSION.FDR_SID = FILE_DIRECTORY.FDR_SID");
            sql.addSql("      left join");
            sql.addSql("         FILE_CABINET");
            sql.addSql("      on");
            sql.addSql("        FILE_DIRECTORY.FCB_SID = FILE_CABINET.FCB_SID");
            sql.addSql("      where");
            sql.addSql("        FILE_DIRECTORY.FDR_JTKBN = ?");
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);
            sql.addSql("      and");
            sql.addSql("        FILE_CABINET.FCB_JKBN = ?");
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);
            sql.addSql("      and");
            sql.addSql("        FILE_SHORTCUT_CONF.USR_SID = ?");
            sql.addIntValue(usrSid);
            if (!isAdmin) {
                sql.addSql("      and");
                sql.addSql("      (");
                sql.addSql("         FILE_DIRECTORY.FDR_ACCESS_SID = DACCESS.FDR_SID");
                sql.addSql("         or");
                sql.addSql("         FILE_DIRECTORY.FCB_SID in(");
                sql.addSql("                     select");
                sql.addSql("                       FCB_SID");
                sql.addSql("                     from");
                sql.addSql("                       FILE_CABINET_ADMIN");
                sql.addSql("                     where");
                sql.addSql("                       USR_SID = ?");
                sql.addSql("                   )");
                sql.addSql("      )");
                sql.addIntValue(usrSid);
            }
            sql.addSql("      and");
            sql.addSql("      FILE_DIRECTORY.FCB_SID in(");
            sql.addSql("        select ");
            sql.addSql("          FCB_SID");
            sql.addSql("        from");
            sql.addSql("          FILE_CABINET");
            sql.addSql("        where");
            sql.addSql("          FILE_CABINET.FCB_PERSONAL_FLG = ?");
            sql.addIntValue(cabType);
            sql.addSql("        and");
            sql.addSql("          FILE_CABINET.FCB_ERRL = ?");
            sql.addIntValue(errlKbn);
            sql.addSql("      )");
            sql.addSql("    ) DIRECTORY");
            if (!isAdmin) {
                sql.addSql("  left join");
                sql.addSql("    (   ");
                sql.addSql("      select");
                sql.addSql("        FCB_SID");
                sql.addSql("      from");
                sql.addSql("        FILE_CABINET_ADMIN");
                sql.addSql("      where");
                sql.addSql("        FILE_CABINET_ADMIN.USR_SID = ?");
                sql.addIntValue(usrSid);
                sql.addSql("    ) CABINET  ");
                sql.addSql("  on");
                sql.addSql("    DIRECTORY.FCB_SID = CABINET.FCB_SID");
            }
            sql.addSql(" order by");
            sql.addSql("   DIRECTORY.FDR_PARENT_SID,");
            sql.addSql("   DIRECTORY.FDR_NAME,");
            sql.addSql("   DIRECTORY.FSC_ADATE");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileShortCutApiFromRs(rs));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <p>Create ApiFileShortcutList Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created FileDirectoryModel
     * @throws SQLException SQL実行例外
     */
    private ApiFileShortcutListModel __getFileShortCutApiFromRs(ResultSet rs) throws SQLException {
        ApiFileShortcutListModel bean = new ApiFileShortcutListModel();
        bean.setFdrSid(rs.getInt("FDR_SID"));
        bean.setFcbSid(rs.getInt("FCB_SID"));
        bean.setFdrParentSid(rs.getInt("FDR_PARENT_SID"));
        bean.setFdrKbn(rs.getInt("FDR_KBN"));
        bean.setFdrName(rs.getString("FDR_NAME"));
        bean.setFdrJtkbn(rs.getInt("FDR_KBN"));
        bean.setFdrEuid(rs.getInt("FDR_EUID"));
        bean.setFdrEgid(rs.getInt("FDR_EGID"));
        bean.setFdrEdate(UDate.getInstanceTimestamp(rs.getTimestamp("FDR_EDATE")));
        bean.setBinSid(rs.getLong("BIN_SID"));
        bean.setFflFileSizeLg(rs.getLong("FFL_FILE_SIZE"));
        bean.setFflLockKbn(rs.getInt("FFL_LOCK_KBN"));
        bean.setFflLockUser(rs.getInt("FFL_LOCK_USER"));
        bean.setGrpName(rs.getString("GRP_NAME"));
        bean.setUsrSei(rs.getString("USI_SEI"));
        bean.setUsrMei(rs.getString("USI_MEI"));
        bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
        bean.setWriteKbn(rs.getInt("WRITEKBN"));
        bean.setCabinetKbn(rs.getInt("FCB_ERRL"));

        return bean;
    }

}