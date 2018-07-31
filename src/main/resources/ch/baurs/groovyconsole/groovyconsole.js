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
    var currentHistoryIndex;
    var maxHistoryIndex;

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
        var promptLength = (prompt.length + 1) * cm_prompt.defaultCharWidth();
        cm_prompt.setSize(promptLength + 'px', '100000px');


        //init status from server
        $.get(statusUrl)
            .fail(function(jqXHR, textStatus, errorThrown) {
                //console.log("Error: ", textStatus, errorThrown);
                alert("Server Error: " + textStatus)
            })
            .done(function(data) {
                saveStatus(data);
            });


        cm_input.on("keydown", function(cm, e) {

            if (!e.shiftKey) {

                //ENTER --> submit
                if (e.code == 'Enter') {
                    e.preventDefault();
                    $form.submit();
                }

                // navigate the history up
                if (e.code == 'ArrowUp') {
                    e.preventDefault();
                    navigateHistory(Math.max(0, currentHistoryIndex - 1));
                }
                // navigate the history down
                if (e.code == 'ArrowDown') {
                    e.preventDefault();
                    navigateHistory(Math.min(history.length - 1, currentHistoryIndex + 1));
                }

            }

            //ctrl + c will reset the current command
            if (e.ctrlKey && e.code == 'KeyC') {
                e.preventDefault();
                history[maxHistoryIndex] = '';
                cm_input.setValue('');
                navigateHistory(maxHistoryIndex);
            }
        });

        //set cursor width
        setCursorWidth(cm_input);
        cm_input.on("cursorActivity", function(cm, e) {
            setCursorWidth(cm_input);
        });

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
        cm_output.setValue(data.out);
        cm_output.scrollTo(0, 10000000000000); //scroll down to the end

        //init the history
        history = data.history;
        history.push(''); // add an entry for the current command
        currentHistoryIndex = history.length - 1;
        maxHistoryIndex = history.length - 1

        cm_input.setValue('');
        cm_input.focus();

        navigateHistory(maxHistoryIndex); //navigate to current history entry
    };

    var navigateHistory = function(newIndex) {
        var fixedNewIndex = Math.max(Math.min(newIndex, maxHistoryIndex), 0);

        //if moving away from current command, save it...
        if (currentHistoryIndex == maxHistoryIndex) {
            history[currentHistoryIndex] = cm_input.getValue();
        }
        currentHistoryIndex = fixedNewIndex;

        cm_input.setValue(history[currentHistoryIndex]);

        //set the cursor to the end
        cm_input.setCursor(cm_input.lineCount(), 0);
    };

    var setCursorWidth = function(cm) {
        setTimeout(function() {
            $('.CodeMirror-cursor', $(cm.getWrapperElement())).css('border-left-width', cm.defaultCharWidth() + 'px');
        }, 0);
    };


    return {
        init: init
    };

})(jQuery);



$(function() {
    GroovyConsole.init();
});