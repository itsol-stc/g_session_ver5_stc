<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="cmn.admin.setting.menu" /></title>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>
<body>
  <html:form action="/anpi/anp070">
    <!-- BODY -->
    <input type="hidden" name="CMD">
    <html:hidden property="backScreen" />
    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <div class="kanriPageTitle w80 mrl_auto">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
        </li>
        <li>
          <gsmsg:write key="cmn.admin.setting" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="anp.plugin" />
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp070back');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w80 mrl_auto">
      <div class="settingList">
        <dl>
          <dt onClick="return buttonPush('anp070base');">
            <span class="settingList_title">
              <gsmsg:write key="cmn.preferences" />
            </span>
          </dt>
          <dd>
            <div class="settingList_text">
              <gsmsg:write key="anp.anp070.01" />
            </div>
          </dd>
        </dl>
        <dl>
          <dt onClick="return buttonPush('anp070mailtemp');">
            <span class="settingList_title">
              <gsmsg:write key="anp.anp070.02" />
            </span>
          </dt>
          <dd>
            <div class="settingList_text">
              <gsmsg:write key="anp.anp070.03" />
            </div>
          </dd>
        </dl>
        <dl>
          <dt onClick="return buttonPush('anp070contact');">
            <span class="settingList_title">
              <gsmsg:write key="anp.anp070.04" />
            </span>
          </dt>
          <dd>
            <div class="settingList_text">
              <gsmsg:write key="anp.anp070.05" />
            </div>
          </dd>
        </dl>
        <dl>
          <dt onClick="return buttonPush('anp070allset');">
            <span class="settingList_title">
              <gsmsg:write key="anp.anp070.06" />
            </span>
          </dt>
          <dd>
            <div class="settingList_text">
              <gsmsg:write key="anp.anp070.07" />
            </div>
          </dd>
        </dl>
        <dl>
          <dt onClick="return buttonPush('anp070history');">
            <span class="settingList_title">
              <gsmsg:write key="anp.anp070.08" />
            </span>
          </dt>
          <dd>
            <div class="settingList_text">
              <gsmsg:write key="anp.anp070.09" />
            </div>
          </dd>
        </dl>
      </div>
    </div>
  </html:form>
  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>