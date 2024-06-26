package jp.groupsession.v2.ntp.ntp082;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.ntp.AbstractNippouAdminAction;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 日報 自動データ削除設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp082Action extends AbstractNippouAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp082Action.class);

    /**
     * <br>アクション実行
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        ActionForward forward = null;

        Ntp082Form ntpForm = (Ntp082Form) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("ntp082kakunin")) {
            //確認
            forward = __doKakunin(map, ntpForm, req, res, con);
        } else if (cmd.equals("ntp082commit")) {
            //登録
            forward = __doCommit(map, ntpForm, req, res, con);
        } else if (cmd.equals("ntp082cancel")) {
            //キャンセル
            forward = __doRedraw(map, ntpForm, req, res, con);
        } else if (cmd.equals("ntp082back")) {
            //戻るボタンクリック
            forward = __doBack(map, ntpForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, ntpForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>登録処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doCommit(ActionMapping map, Ntp082Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("登録処理");

        //不正な画面遷移
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //DB更新
        BaseUserModel umodel = getSessionUserModel(req);
        Ntp082Biz biz = new Ntp082Biz(con, getRequestModel(req));
        Ntp082ParamModel paramMdl = new Ntp082ParamModel();
        paramMdl.setParam(form);
        biz.setAutoDeleteSetting(paramMdl, umodel);
        paramMdl.setFormData(form);

        GsMessage gsMsg = new GsMessage();
        String change = gsMsg.getMessage(req, "cmn.change");
        //ログ出力処理
        NtpCommonBiz ntpBiz = new NtpCommonBiz(con, getRequestModel(req));
        ntpBiz.outPutLog(
                map, req, res,
                change, GSConstLog.LEVEL_INFO, "");

        //共通メッセージ画面(OK)を表示
        __setCompPageParam(map, req, form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Ntp082Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("ktool");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "touroku.kanryo.object";
        String key1 = "自動データ削除設定";
        cmn999Form.setMessage(msgRes.getMessage(msgState, key1));

        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);

    }

    /**
     * <br>確認処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doKakunin(ActionMapping map, Ntp082Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("確認処理");
        

        ActionErrors errors = form.validateCheck(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doSetShowData(map, form, req, res, con);
        }

        //トランザクショントークン設定
        saveToken(req);

        //共通メッセージ画面(OK キャンセル)を表示
        __setKakuninPageParam(map, req, form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 確認メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setKakuninPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Ntp082Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
//        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(map.findForward("changeOk").getPath());
        cmn999Form.setUrlCancel(map.findForward("changeCancel").getPath());

        //メッセージセット
        String msgState = "setting.kakunin.data";
        String mkey1 = "日報 自動データ削除";
        String mkey2 = null;
        if (form.getNtp082AtdelFlg() ==  GSConstNippou.AUTO_DELETE_OFF) {
            mkey2 = GSConstNippou.AUTO_DELETE_OFF_TEXT;
        } else {
            mkey2 = GSConstNippou.AUTO_DELETE_ON_TEXT + "("
                    + form.getNtp082AtdelYear() + "年"
                    + form.getNtp082AtdelMonth() + "ヶ月)";
        }
        cmn999Form.setMessage(msgRes.getMessage(msgState, mkey1, mkey2));

        cmn999Form.addHiddenParam("cmd", "ok");
        cmn999Form.addHiddenParam("ntp082AtdelFlg", form.getNtp082AtdelFlg());
        cmn999Form.addHiddenParam("ntp082AtdelYear", form.getNtp082AtdelYear());
        cmn999Form.addHiddenParam("ntp082AtdelMonth", form.getNtp082AtdelMonth());

        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     * <br>戻る処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doBack(ActionMapping map, Ntp082Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {
        log__.debug("戻る");
        return map.findForward("ntp082back");
    }

    /**
     * <br>初期表処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Ntp082Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("初期表示");

        //初期値をセット
        Ntp082Biz biz = new Ntp082Biz(con, getRequestModel(req));

        Ntp082ParamModel paramMdl = new Ntp082ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(form);

        return __doSetShowData(map, form, req, res, con);
    }

    /**
     * <br>確認画面でキャンセル時再描画処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doRedraw(ActionMapping map, Ntp082Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("再表示");

        return __doSetShowData(map, form, req, res, con);
    }

    /**
     * <br>表示用データを取得、表示
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doSetShowData(ActionMapping map, Ntp082Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        //表示用データを取得する
        Ntp082Biz biz = new Ntp082Biz(con, getRequestModel(req));
        Ntp082ParamModel paramMdl = new Ntp082ParamModel();
        paramMdl.setParam(form);
        biz.setShowData(paramMdl, con);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }
}