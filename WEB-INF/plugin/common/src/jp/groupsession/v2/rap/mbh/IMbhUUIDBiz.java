package jp.groupsession.v2.rap.mbh;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.login.UserAgent;


public interface IMbhUUIDBiz {
    /**
     * <br>[機  能] 個体識別番号をチェックする
     * <br>[解  説]
     * <br>[備  考] エラーの場合はエラーメッセージリストが返る
     * <br>         正常の場合は空リストが返る
     *
     * @param smodel ユーザモデル
     * @param agent ユーザエージェント
     * @param con コネクション
     * @return パラメータチェック結果
     * @throws Exception 実行時例外
     */
    boolean checkUid(BaseUserModel smodel,
            UserAgent agent,
            Connection con,
            boolean isLogin) throws SQLException;

}
