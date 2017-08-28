/*
Description: $.messager
Author: lichunlei@yiniu.com
*/

jQuery(function($) {
	$('[data-rel=tooltip]').tooltip({container:'body'});
});

$.messager = (function() {

  var alert = function(title, message) {
    var model = $.messager.model;

    if (arguments.length < 2) {
      message = title || "";
      title   = "&nbsp;"
    }

    $("<div>" + message + "</div>").dialog({
        title:   title
        // override destroy methods;
      , onClose: function() {
          $(this).dialog("destroy");
        }
      , buttons: [{
            text: model.ok.text
          , classed: model.ok.classed || "btn-sm btn-success"
          , click: function() {
              $(this).dialog("destroy");
            }
        }]
    });
  };

  var confirm = function(title, message, callbackTrue, callbackFalse, callbackClose, okMsg, cancelMsg) {
	  if(okMsg == null) {
		  okMsg = "确定";
	  }
	  if(cancelMsg == null) {
		  cancelMsg = "取消";
	  }
	  var model = {
    	    ok: { text : okMsg, classed : 'btn-sm btn-primary' },
    	    cancel: { text : cancelMsg, classed : 'btn-sm btn-default' }
    	};

    $("<div>" + message + "</div>").dialog({
        title:   title
        // override destroy methods;
      , onClose: function() {
          $(this).dialog("destroy");
          callbackClose && callbackClose();
        }
      , buttons: [{
            text: model.ok.text
          , classed: model.ok.classed || "btn-sm btn-success"
          , click: function() {
              $(this).dialog("destroy");
              callbackTrue && callbackTrue();
            }
        },
        {
            text: model.cancel.text
          , classed : model.cancel.classed || "btn-sm btn-danger"
          , click: function() {
              $(this).dialog("destroy");
              callbackFalse && callbackFalse();
            }
        }]
    });
  };

  /*
  * popup message
  */
  var msghtml
    = ''
    + '<div class="dialog modal fade msg-popup">'
    + '<div class="modal-dialog modal-sm">'
    +   '<div class="modal-content">'
    +     '<div class="modal-body text-center"></div>'
    +   '</div>'
    + '</div>'
    + '</div>'
    ;

  var $msgbox
    , offTimer
    ;

  var popup = function(message) {
    if (!$msgbox) {
      $msgbox = $(msghtml);
      $('body').append($msgbox);
    }

    $msgbox.find(".modal-body").html(message);
    $msgbox.modal({show: true, backdrop: false});

    clearTimeout(offTimer);
    offTimer = setTimeout(function() {
      $msgbox.modal('hide');
    }, 1500);
  };

  return {
      alert:   alert
    , popup:   popup
    , confirm: confirm
  };

})();


$.messager.model = {
    ok: { text : "确定", classed : 'btn-sm btn-primary' },
    cancel: { text : "取消", classed : 'btn-sm btn-default' }
};