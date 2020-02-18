$(document).ready(function () {
    var allCheckBox = document.querySelectorAll(".ks-cboxtags input");
    var allLabels = document.querySelectorAll(".ks-cboxtags label");
    var i = 0;
    var j = 0;
    allCheckBox.forEach((c) => {
        if (c.className === "toCheck") {
            c.checked = true;
        }
        c.setAttribute("id", "checkbox"+i);
        i++;
    })
    allLabels.forEach((c) => {
        c.setAttribute("for", "checkbox"+j);
        j++;
    })
})