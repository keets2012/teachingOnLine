<<<<<<< HEAD
if ($.fn.pagination) {
    $.fn.pagination.defaults.beforePageText = 'Sida';
    $.fn.pagination.defaults.afterPageText = 'av {pages}';
    $.fn.pagination.defaults.displayMsg = 'Visar {from} till {to} av {total} poster';
}
if ($.fn.datagrid) {
    $.fn.datagrid.defaults.loadMsg = 'Bearbetar, vänligen vänta ...';
}
if ($.fn.treegrid && $.fn.datagrid) {
    $.fn.treegrid.defaults.loadMsg = $.fn.datagrid.defaults.loadMsg;
}
if ($.messager) {
    $.messager.defaults.ok = 'Ok';
    $.messager.defaults.cancel = 'Avbryt';
}
if ($.fn.validatebox) {
    $.fn.validatebox.defaults.missingMessage = 'Detta fält är obligatoriskt.';
    $.fn.validatebox.defaults.rules.email.message = 'Vänligen ange en korrekt e-post adress.';
    $.fn.validatebox.defaults.rules.url.message = 'Vänligen ange en korrekt URL.';
    $.fn.validatebox.defaults.rules.length.message = 'Vänligen ange ett nummer mellan {0} och {1}.';
    $.fn.validatebox.defaults.rules.remote.message = 'Vänligen åtgärda detta fält.';
}
if ($.fn.numberbox) {
    $.fn.numberbox.defaults.missingMessage = 'Detta fält är obligatoriskt.';
}
if ($.fn.combobox) {
    $.fn.combobox.defaults.missingMessage = 'Detta fält är obligatoriskt.';
}
if ($.fn.combotree) {
    $.fn.combotree.defaults.missingMessage = 'Detta fält är obligatoriskt.';
}
if ($.fn.combogrid) {
    $.fn.combogrid.defaults.missingMessage = 'Detta fält är obligatoriskt.';
}
if ($.fn.calendar) {
    $.fn.calendar.defaults.weeks = ['Sön', 'Mån', 'Tis', 'Ons', 'Tors', 'Fre', 'Lör'];
    $.fn.calendar.defaults.months = ['Jan', 'Feb', 'Mar', 'Apr', 'Maj', 'Jun', 'Jul', 'Aug', 'Sep', 'Okt', 'Nov', 'Dec'];
}
if ($.fn.datebox) {
    $.fn.datebox.defaults.currentText = 'I dag';
    $.fn.datebox.defaults.closeText = 'Stäng';
    $.fn.datebox.defaults.okText = 'Ok';
    $.fn.datebox.defaults.missingMessage = 'Detta fält är obligatoriskt.';
}
if ($.fn.datetimebox && $.fn.datebox) {
    $.extend($.fn.datetimebox.defaults, {
        currentText: $.fn.datebox.defaults.currentText,
        closeText: $.fn.datebox.defaults.closeText,
        okText: $.fn.datebox.defaults.okText,
        missingMessage: $.fn.datebox.defaults.missingMessage
    });
}
=======
if ($.fn.pagination) {
    $.fn.pagination.defaults.beforePageText = 'Sida';
    $.fn.pagination.defaults.afterPageText = 'av {pages}';
    $.fn.pagination.defaults.displayMsg = 'Visar {from} till {to} av {total} poster';
}
if ($.fn.datagrid) {
    $.fn.datagrid.defaults.loadMsg = 'Bearbetar, vänligen vänta ...';
}
if ($.fn.treegrid && $.fn.datagrid) {
    $.fn.treegrid.defaults.loadMsg = $.fn.datagrid.defaults.loadMsg;
}
if ($.messager) {
    $.messager.defaults.ok = 'Ok';
    $.messager.defaults.cancel = 'Avbryt';
}
if ($.fn.validatebox) {
    $.fn.validatebox.defaults.missingMessage = 'Detta fält är obligatoriskt.';
    $.fn.validatebox.defaults.rules.email.message = 'Vänligen ange en korrekt e-post adress.';
    $.fn.validatebox.defaults.rules.url.message = 'Vänligen ange en korrekt URL.';
    $.fn.validatebox.defaults.rules.length.message = 'Vänligen ange ett nummer mellan {0} och {1}.';
    $.fn.validatebox.defaults.rules.remote.message = 'Vänligen åtgärda detta fält.';
}
if ($.fn.numberbox) {
    $.fn.numberbox.defaults.missingMessage = 'Detta fält är obligatoriskt.';
}
if ($.fn.combobox) {
    $.fn.combobox.defaults.missingMessage = 'Detta fält är obligatoriskt.';
}
if ($.fn.combotree) {
    $.fn.combotree.defaults.missingMessage = 'Detta fält är obligatoriskt.';
}
if ($.fn.combogrid) {
    $.fn.combogrid.defaults.missingMessage = 'Detta fält är obligatoriskt.';
}
if ($.fn.calendar) {
    $.fn.calendar.defaults.weeks = ['Sön', 'Mån', 'Tis', 'Ons', 'Tors', 'Fre', 'Lör'];
    $.fn.calendar.defaults.months = ['Jan', 'Feb', 'Mar', 'Apr', 'Maj', 'Jun', 'Jul', 'Aug', 'Sep', 'Okt', 'Nov', 'Dec'];
}
if ($.fn.datebox) {
    $.fn.datebox.defaults.currentText = 'I dag';
    $.fn.datebox.defaults.closeText = 'Stäng';
    $.fn.datebox.defaults.okText = 'Ok';
    $.fn.datebox.defaults.missingMessage = 'Detta fält är obligatoriskt.';
}
if ($.fn.datetimebox && $.fn.datebox) {
    $.extend($.fn.datetimebox.defaults, {
        currentText: $.fn.datebox.defaults.currentText,
        closeText: $.fn.datebox.defaults.closeText,
        okText: $.fn.datebox.defaults.okText,
        missingMessage: $.fn.datebox.defaults.missingMessage
    });
}
>>>>>>> 51ac7a4e78cf43e26729d50406bb516e7ce0a18d
