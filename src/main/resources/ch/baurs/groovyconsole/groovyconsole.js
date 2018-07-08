"use strict";

var GroovyConsole = (function($) {
    var $mod;
    var cm_input;
    var cm_output;

    var init = function() {
        $mod = $(".groovyConsole");

        var $input = $("textarea.input", $mod),
            $output = $("textarea.output", $mod),
            $form = $("form", $mod),
            theme = $mod.data("theme"),
            statusUrl = $mod.data("status-url");

        // init input
        cm_input = CodeMirror.fromTextArea($input[0], {
            mode: "text/x-groovy",
            lineNumbers: false,
            matchBrackets: true,
            scrollbarStyle: null,
            cursorBlinkRate: 800,
            theme: theme,
            scrollbarStyle: "null",
            //value: "test",
            readOnly: false
        });
        // init output
        cm_output = CodeMirror.fromTextArea($output[0], {
            mode: "text/x-groovy",
            lineNumbers: false,
            matchBrackets: false,
            theme: theme,
            scrollbarStyle: "null",
            //value: "test",
            readOnly: true
        });

        $.get(statusUrl)
            .fail(function(a, b, c) {
                alert(a);
                alert(b);
                alert(c);
            })
            .done(function(data) {
                saveStatus(data);
            });

        cm_input.on("keydown", function(cm, event) {
            console.log(event);


            //ENTER --> submit
            if ( /*event.ctrlKey && */ event.keyCode == 13) {
                $form.submit();
            }
        });
        cm_input.on("beforeChange", function(cm, changeObj) {
            var typedNewLine = changeObj.origin == '+input' && typeof changeObj.text == "object" && changeObj.text.join("") == "";
            if (typedNewLine) {
                return changeObj.cancel();
            }

            var pastedNewLine = changeObj.origin == 'paste' && typeof changeObj.text == "object" && changeObj.text.length > 1;
            if (pastedNewLine) {
                var newText = changeObj.text.join(" ");

                // trim
                newText = $.trim(newText);

                return changeObj.update(null, null, [newText]);
            }

            return null;
        });

        cm_input.focus();

        //handle form submits to send ajax POST request
        $form.submit(handleSubmit);
    };

    var handleSubmit = function(e) {
        e.preventDefault();
        var url = e.target.action;

        $.post(url, {
                code: cm_input.getValue()
            })
            .fail(function(a, b, c) {
                alert(a);
                alert(b);
                alert(c);
            })
            .done(function(data) {
                saveStatus(data);
            });
    };

    var saveStatus = function(data) {
        cm_output.setValue(data);
        cm_output.scrollTo(0, 10000000000000);

        cm_input.setValue("");
        cm_input.focus();
    };


    return {
        init: init
    };

})(jQuery);



$(function() {
    GroovyConsole.init();
});