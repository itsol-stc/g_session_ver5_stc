package jp.groupsession.v2.man.man491;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man440.Man440Biz;
import jp.groupsession.v2.struts.AbstractGsAction;
/**
 *
 * <br>[機  能] マイカレンダーインポート画面 Action
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man491Action extends AbstractGsAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man491Action.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "man491";

    @Override
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("START");
        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();
        //初期表示
        if (cmd.equals("man491")) {
            forward = __doInit(map, (Man491Form) form, req, con);
        } else if (cmd.equals("Man491_Back")) {
            // 戻るボタン押下
            forward = __doBack(map, (Man491Form) form, req, con);
        } else if (cmd.equals("Man491_Import")) {
            // インポートボタン押下
            forward = __doCheckImport(map, (Man491Form) form, req, con);
        } else if (cmd.equals("delete")) {
            // 削除ボタン押下
            forward = __doInit(map, (Man491Form) form, req, con);
        } else {
            forward = __doDsp(map, (Man491Form) form, req, con);
        }

        log__.debug("END");
        return forward;
    }
    /**
     *
     * <br>[機  能] 描画処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return 遷移先
     * @throws Exception 実行時例外
     */
    private ActionForward __doDsp(ActionMapping map, Man491Form form,
            HttpServletRequest req, Connection con) throws Exception {
        RequestModel reqMdl = getRequestModel(req);
        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);

        Man491ParamModel param = new Man491ParamModel();
        param.setParam(form);

        Man491Biz man491Biz = new Man491Biz(reqMdl, con);

        man491Biz.doDsp(param, tempDir);
        param.setFormData(form);

        ActionForward forward = map.getInputForward();

        return forward;
    }
    /**
     *
     * <br>[機  能] インポートボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return 遷移先
     * @throws Exception 実行時例外
     */
    private ActionForward __doCheckImport(ActionMapping map, Man491Form form,
            HttpServletRequest req, Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);

        ActionErrors errors = form.validateCheck(reqMdl, tempDir, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, con);
        }

        //トランザクショントークン設定
        saveToken(req);

        ActionForward forward = map.findForward("491_Import");

        return forward;
    }
    /**
     *
     * <br>[機  能] 戻る時の遷移処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return 遷移先
     * @throws Exception 実行時例外
     */
    private ActionForward __doBack(ActionMapping map, Man491Form form,
            HttpServletRequest req, Connection con) throws Exception {
        //テンポラリディレクトリパスを取得
        GSTemporaryPathModel tempMdl = GSTemporaryPathModel.getInstance(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);
        Man440Biz biz = new Man440Biz(getRequestModel(req));
        biz.doDeleteFile(tempMdl);
        ActionForward forward = map.findForward("491_back");
        return forward;
    }
    /**
     *
     * <br>[機  能] 初期表示時処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return 遷移先
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Man491Form form,
            HttpServletRequest req, Connection con) throws Exception {

        //テンポラリディレクトリパスを取得
        GSTemporaryPathModel tempMdl = GSTemporaryPathModel.getInstance(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);
        Man440Biz biz = new Man440Biz(getRequestModel(req));
        biz.doDeleteFile(tempMdl);
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.createTempDir(tempMdl);

        ActionForward forward = __doDsp(map, form, req, con);
        return forward;
    }

}
