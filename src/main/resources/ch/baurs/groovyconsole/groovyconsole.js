"use strict";

var GroovyConsole = (function($) {
    var $mod;
    var cm_input;
    var cm_output;
    var prompt;
    var history;

    var init = function() {
        $mod = $(".groovyConsole");
        prompt = $mod.data("prompt");
        if (!prompt.endsWith(" ")) {
            prompt += " ";
        }

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
            //console.log(event);

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

        cm_input.on("change", function(cm, changeObj) {
            assertPrompt();
        });
        cm_input.on("focus", function(cm, changeObj) {
            assertPrompt();
        });
        cm_input.on("cursorActivity", function(cm, changeObj) {
            assertPrompt();
        });

        cm_input.setSize('100%', '10000px');
        cm_input.focus();

        //handle form submits to send ajax POST request
        $form.submit(handleSubmit);
    };

    var handleSubmit = function(e) {
        e.preventDefault();
        var url = e.target.action,
            value = cm_input.getValue().removeStart(prompt);

        $.post(url, {
                code: value
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
        history = data.history;

        cm_output.setValue(data.out);
        cm_output.scrollTo(0, 10000000000000); //scroll down to the end

        //assert the content of cm_input starts with the prompt
        cm_input.setValue(prompt);
        cm_input.focus();
    };

    var assertPrompt = function() {
        var promptChars = prompt.split(''),
            promptLength = prompt.length,
            currentSelections = cm_input.listSelections();


        //make sure the cursor is never placed inside the prompt
        currentSelections.forEach(function(selection) {
            console.log("selection: ", selection);

            if (selection.anchor.line == 0 && selection.anchor.ch < promptLength) {
                selection.anchor.ch = promptLength;
            }
            if (selection.head.line == 0 && selection.head.ch < promptLength) {
                selection.head.ch = promptLength;
            }

        });

        console.log("'" + cm_input.getValue() + "'");

    };


    return {
        init: init
    };

})(jQuery);



$(function() {
    GroovyConsole.init();
});