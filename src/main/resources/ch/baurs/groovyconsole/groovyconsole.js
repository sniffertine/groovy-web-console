"use strict";

var GroovyConsole = (function($) {
    var theme;
    var statusUrl;
    var formAction;
    var prompt;
    var $mod;
    var $form;
    var $output;
    var $prompt;
    var $input;

    var cm_output;
    var cm_prompt;
    var cm_input;
    var history;

    var init = function() {
        $mod = $(".groovyConsole");
        initMarkup($mod);

        // init output
        cm_output = CodeMirror.fromTextArea($output[0], {
            mode: "text/x-groovy",
            scrollbarStyle: "null",
            lineNumbers: false,
            theme: theme,

            matchBrackets: false,
            viewportMargin: Infinity,
            readOnly: true
        });

        // init prompt
        cm_prompt = CodeMirror.fromTextArea($prompt[0], {
            mode: "text/x-groovy",
            scrollbarStyle: "null",
            lineNumbers: false,
            theme: theme,

            matchBrackets: false,
            readOnly: true
        });

        // init input
        cm_input = CodeMirror.fromTextArea($input[0], {
            mode: "text/x-groovy",
            scrollbarStyle: "null",
            lineNumbers: false,
            theme: theme,

            lineWrapping: true,
            matchBrackets: true,
            cursorBlinkRate: 800,
            readOnly: false
        });


        //set value and size of prompt
        cm_prompt.setValue(prompt);
        var pixels = (prompt.length + 1) * cm_prompt.defaultCharWidth();
        console.log("pixels: ", pixels);
        cm_prompt.setSize(pixels +'px', '100000px');

        //set cursor width
        $('.cm-s-vibrant-ink .CodeMirror-cursor', $mod).css('border-left-width', cm_prompt.defaultCharWidth()+ 'px');


        //set status
        $.get(statusUrl)
            .fail(function(a, b, c) {
                alert(a);
                alert(b);
                alert(c);
            })
            .done(function(data) {
                saveStatus(data);
            });

        //submit form on ENTER
        cm_input.on("keydown", function(cm, e) {
            //console.log(e);

            if (!e.shiftKey) {

                //ENTER --> submit
                if(e.code == 'Enter'){
                    e.preventDefault();
                    $form.submit();
                }
                if(e.code == 'ArrowUp'){
                    e.preventDefault();
                    console.log(history);
                }
                if(e.code == 'ArrowDown'){
                    e.preventDefault();
                    console.log(history);
                }
            }
        });
        cm_input.on("beforeChange___", function(cm, e) {
            var typedNewLine = e.origin == '+input' && typeof e.text == "object" && e.text.join("") == "";
            if (typedNewLine) {
                return e.cancel();
            }

            var pastedNewLine = e.origin == 'paste' && typeof e.text == "object" && e.text.length > 1;
            if (pastedNewLine) {
                var newText = e.text.join(" ");

                // trim
                newText = $.trim(newText);

                return e.update(null, null, [newText]);
            }

            return null;
        });

        cm_input.on("change___", function(cm, e) {
            assertPrompt();
        });
        cm_input.on("focus___", function(cm, e) {
            assertPrompt();
        });
        cm_input.on("cursorActivity___", function(cm, e) {
            assertPrompt();
        });

        //cm_input.setSize('100%', '10000px');
        cm_input.focus();

        //handle form submits to send ajax POST request
        $form.submit(handleSubmit);
    };

    var initMarkup = function($mod) {
        theme = $mod.data("theme");
        statusUrl = $mod.data("status-url");
        formAction = $mod.data("form-action");
        prompt = $mod.data("prompt");

        $form = $("<form method='post'></form>").appendTo($mod).attr('action', formAction);
        var $row1 = $("<div class='row1'></div>").appendTo($form);
        var $row2 = $("<div class='row2'></div>").appendTo($form);

        $output = $("<textarea class='output'></textarea>").appendTo($row1);
        $prompt = $("<textarea class='prompt'></textarea>").appendTo($row2);
        $input = $("<textarea class='input'></textarea>").appendTo($row2);
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
        cm_input.setValue("");
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