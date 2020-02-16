/*//var template = document.getElementById('search-page').innerHTML;
//var rendered = Mustache.render(template, { name: 'cookingStyles' });

allCheckBox.forEach((c) => {
    if (c.value === cStyle.toString) {
        c.checked = true;
    }
})*/

$(document).ready(function () {
const allCheckBox = document.querySelectorAll(".ks-cboxtags input");

function test(data) {
    allCheckBox.forEach((c) => {
    if (c.value === data) {
        c.checked = true;
    }
})
}


})