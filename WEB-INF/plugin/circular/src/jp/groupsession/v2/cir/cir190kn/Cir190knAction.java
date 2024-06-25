package jp.groupsession.v2.cir.cir190kn;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cir.AbstractCircularSubAction;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 回覧板 アカウント基本設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir190knAction extends AbstractCircularSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir190knAction.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説] コントローラの役割を担います
     * <br>[備  考]
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
        log__.debug("START");

        ActionForward forward = null;
        Cir190knForm thisForm = (Cir190knForm) form;

        //管理者権限チェック
        if (!_checkAuth(map, req, con)) {
            return map.findForward("gf_power");
        }

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("decision")) {
            //確定ボタンクリック
            forward = __doDecision(map, thisForm, req, res, con);

        } else if (cmd.equals("backInput")) {
            //戻るボタンクリック
            forward = map.findForward("backInput");

        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Cir190knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 確定ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doDecision(ActionMapping map, Cir190knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        Cir190knParamModel paramMdl = new Cir190knParamModel();
        paramMdl.setParam(form);
        Cir190knBiz biz = new Cir190knBiz(con, getRequestModel(req));
        biz.setData(paramMdl);

        //ログ出力
        CirCommonBiz cirBiz = new CirCommonBiz(con);
        String msg = __getLogMessage(form, req);
        cirBiz.outPutLog(map, getRequestModel(req),
                getInterMessage(req, "cmn.change"), GSConstLog.LEVEL_INFO,
                msg);

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
        Cir190knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("cirAdmConf");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        msgState = "touroku.kanryo.object";
        cmn999Form.setMessage(msgRes.getMessage(msgState, getInterMessage(req, "cmn.preferences")));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());

        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     * <br>[機  能] ログ作成
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     * @throws Exception エラー
     */
    private String __getLogMessage(
        Cir190knForm form,
        HttpServletRequest req) throws Exception {

        GsMessage gsMsg = new GsMessage(req);

        String msg = "";
        //アカウントの作成
        msg += "[" + gsMsg.getMessage("cir.cir190kn.2") + "]";
        if (form.getCir190acntMakeKbn() == GSConstCircular.KANRI_USER_ONLY) {
            msg += gsMsg.getMessage("cir.cir190kn.3");
        } else if (form.getCir190acntMakeKbn() == GSConstCircular.KANRI_USER_NO) {
            msg += gsMsg.getMessage("man.no.limit");
        }
        //自動削除区分
        msg += "\r\n[" + gsMsg.getMessage("cmn.automatic.delete.categories") + "]";
        if (form.getCir190autoDelKbn() == GSConstCircular.CIR_DEL_KBN_ADM_SETTING) {
            msg += gsMsg.getMessage("cmn.set.the.admin");
        } else if (form.getCir190autoDelKbn() == GSConstCircular.CIR_DEL_KBN_USER_SETTING) {
            msg += gsMsg.getMessage("cmn.set.eachaccount");
        }
        //使用者
        msg += "\r\n[" + gsMsg.getMessage("cmn.employer") + "]";
        if (form.getCir190acntUser() == GSConstCircular.CIN_ACNT_USER_ONLY) {
            msg += gsMsg.getMessage("cmn.only.admin.setting");
        } else if (form.getCir190acntUser() == GSConstCircular.CIN_ACNT_USER_NO) {
            msg += gsMsg.getMessage("man.no.limit");
        }
        return msg;
    }
}
