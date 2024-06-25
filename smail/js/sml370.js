$(function() {

    //グラフの描画
    drawTotalizationGraph();

    /* メニュー 格納用縦線 hover*/
    $('.js_toukei_optionClose').hover(
            function () {
                $(this).addClass("toukei_optionOpen");
              },
              function () {
                  $(this).removeClass("toukei_optionOpen");
              }
    );


    /* メニュー 格納用縦線 click*/
    $(document).on("click", ".toukei_optionClose", function(){
        if ($("#sel_menu_wrapper").css('display') == "none") {
            $('#sel_menu_wrapper').removeClass('display_n');
            $('#menu_length_area').removeClass("menu_length_border_none");
        } else {
            $('#sel_menu_wrapper').addClass('display_n');
            $('#menu_length_area').addClass("menu_length_border_none");
        }
        drawTotalizationGraph();
    });


    /*統計情報画面選択メニュー*/
    var adminFlg = $('input:hidden[name=sml370GsAdminFlg]').val();
    var wmlCtrlFlg = $('input:hidden[name=sml370CtrlFlgWml]').val();
    if (adminFlg == 'false' && wmlCtrlFlg == 'false') {
      $('.menu_select_text').addClass('menu_select_text_no_top');
      $('.menu_select_text').removeClass('menu_select_text');
      $('.toukei_pluginIcon-select').addClass('menu_select_icon_no_top');
      $('.toukei_pluginIcon-select').removeClass('toukei_pluginIcon-select');
    }

    /*月週日切り替え*/
    $('input[name=sml370DateUnit]:checked').attr('onclick','').unbind('click');

/*
      メニュー格納
     $('#sel_menu_wrapper').addClass('display_n');
     $('#menu_length_area').addClass("menu_length_border_none");*/
});

//年月コンボ変更
function changeYearMonthCombo(flg) {

  var frYear = Number(getDateComboValue('sml370DateMonthlyFrYear'));
  var frMonth = Number(getDateComboValue('sml370DateMonthlyFrMonth'));
  var toYear = Number(getDateComboValue('sml370DateMonthlyToYear'));
  var toMonth = Number(getDateComboValue('sml370DateMonthlyToMonth'));

  if (frYear > toYear || ((frYear == toYear && frMonth > toMonth))) {
    if (flg == 'from') {
      $('select[name=sml370DateMonthlyToYear]').val(getDateComboValue('sml370DateMonthlyFrYear'));
      $('select[name=sml370DateMonthlyToMonth]').val(getDateComboValue('sml370DateMonthlyFrMonth'));
    } else {
      $('select[name=sml370DateMonthlyFrYear]').val(getDateComboValue('sml370DateMonthlyToYear'));
      $('select[name=sml370DateMonthlyFrMonth]').val(getDateComboValue('sml370DateMonthlyToMonth'));
    }
  }

  document.forms[0].CMD.value='pageDate';
  document.forms[0].submit();
  return false;
}

function getDateComboValue(paramName) {
  return $("select[name='" + paramName + "']").val();
}

function onSelectFromEvent() {
    document.forms[0].CMD.value='pageDate';
    document.forms[0].submit();
    return false;
}

function onSelectToEvent() {
    document.forms[0].CMD.value='pageDate';
    document.forms[0].submit();
    return false;
}

//表示件数変更
function changeDspNumCombo() {
    document.forms[0].CMD.value='dspNumChange';
    document.forms[0].submit();
    return false;
}

//システムメール除去チェックボックスクリック
function clickSysMailKbn() {
    document.forms[0].CMD.value='sysMailChange';
    document.forms[0].submit();
    return false;
}

function changePage(cmbObj) {
    document.forms[0].sml370NowPage.value=cmbObj.options[cmbObj.selectedIndex].value;
    document.forms[0].CMD.value='pageChange';
    document.forms[0].submit();
}

//表示項目変更
function changeDspItem(nextItem) {
  var currItem = $('input:hidden[name=sml370GraphItem]').val();
  if (currItem != nextItem) {
    $('#' + currItem ).removeClass('toukei_option-select');
    $('#' + nextItem ).addClass('toukei_option-select')
    document.forms[0].sml370GraphItem.value=nextItem;
    drawTotalizationGraph();
  }
}

//集計グラフ
function drawTotalizationGraph() {

  var tmp = document.getElementById('smlCntGraph');
  for (var i=tmp.childNodes.length-1; i>=0; i--) {
    tmp.removeChild(tmp.childNodes[i]);
  }

    var cntData = null;

    var sumKadouTime = 0;

    var jsonDateData = $('input:hidden[name=jsonDateData]').val();
    var jsonJmailData = $('input:hidden[name=jsonJmailData]').val();
    var jsonSmailData = $('input:hidden[name=jsonSmailData]').val();

    var jsmlObject = eval(jsonJmailData);
    var ssmlObject = eval(jsonSmailData);
    var tick = eval(jsonDateData);

    var dayAddFlg = 0;
    var ticksOpsAngle = 0;
    if (tick.length > 8) {
      ticksOpsAngle = -30;
    }
    var ticksOpsSize = '7pt';

    var graphItem = $('input:hidden[name=sml370GraphItem]').val();
    var smlObject = null;
    var graphLabel = null;
    var graphColor = null;
    if (graphItem == 'sml_graph_smail') {
      smlObject = [ssmlObject];
      graphLabel = msglist["numTransmitSml"];
      graphColor = ['#eaa228'];
    } else {
      smlObject = [jsmlObject];
      graphLabel = msglist["numReceiveSml"];
      graphColor = ['#4bb2c5'];
    }

    var smlSeries = [];
    for (var i=0; i<smlObject.length; i++) {
      var srsElm = {label:graphLabel, yaxis:'yaxis', rendererOptions: {animation: {speed:1000}}};
      smlSeries.push(srsElm);
    }

    var ankenPlot = $.jqplot('smlCntGraph', smlObject, {
       animate: true,
       animateReplot: true,
       seriesColors:graphColor,
       legend: {
         show: true,
         location: 'nw',
         renderer: jQuery . jqplot . EnhancedLegendRenderer,
         rendererOptions:{ numberColumns: 3}
       },

       highlighter: {
         show: true,
         showMarker: false,
         sizeAdjust: 0,
         tooltipLocation: 'n',
         tooltipAxes: 'y',
         formatString: '%s'
       },

       series:smlSeries,
       axes: {
         xaxis: {
           renderer: $.jqplot.CategoryAxisRenderer,
           ticks: tick,
           label: '',
           tickRenderer: jQuery . jqplot . CanvasAxisTickRenderer ,
           tickOptions: {angle:ticksOpsAngle,fontSize:ticksOpsSize}
         },
         yaxis: {
           label: '',
           min:0
         }
       }
    });
}