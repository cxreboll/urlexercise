JotForm.init(function() {
    if (window.JotForm && JotForm.accessible) $('input_7').setAttribute('tabindex', 0);
    JotForm.setCustomHint('input_7', 'Please enter details of requested work and/or description of problem...');
    if (window.JotForm && JotForm.accessible) $('input_13').setAttribute('tabindex', 0);
    JotForm.setCustomHint('input_13', 'Please enter details of requested work and/or description of problem...');
    JotForm.newDefaultTheme = false;
    JotForm.extendsNewTheme = false;
    JotForm.newPaymentUIForNewCreatedForms = false; /*INIT-END*/
});
JotForm.prepareCalculationsOnTheFly([null, null, {
    "name": "submit",
    "qid": "2",
    "text": "Submit",
    "type": "control_button"
}, {
    "name": "submitYour",
    "qid": "3",
    "text": "Submit your extra long URL",
    "type": "control_head"
}, null, null, null, {
    "name": "youCan",
    "qid": "7",
    "text": "You can add up to 10 URLs",
    "type": "control_textarea"
}, null, null, null, null, {
    "name": "image",
    "qid": "12",
    "text": "Image",
    "type": "control_image"
}, {
    "description": "",
    "name": "yourShort",
    "qid": "13",
    "subLabel": "",
    "text": "Your short URLs",
    "type": "control_textarea"
}]);
setTimeout(function() {
    JotForm.paymentExtrasOnTheFly([null, null, {
        "name": "submit",
        "qid": "2",
        "text": "Submit",
        "type": "control_button"
    }, {
        "name": "submitYour",
        "qid": "3",
        "text": "Submit your extra long URL",
        "type": "control_head"
    }, null, null, null, {
        "name": "youCan",
        "qid": "7",
        "text": "You can add up to 10 URLs",
        "type": "control_textarea"
    }, null, null, null, null, {
        "name": "image",
        "qid": "12",
        "text": "Image",
        "type": "control_image"
    }, {
        "description": "",
        "name": "yourShort",
        "qid": "13",
        "subLabel": "",
        "text": "Your short URLs",
        "type": "control_textarea"
    }]);
}, 20);