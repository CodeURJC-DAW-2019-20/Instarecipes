var Add_Ing_Button = document.getElementById("Ing-add-button");
var Add_Cat_Button = document.getElementById("Cat-add-button");
var Add_CSy_Button = document.getElementById("CSy-add-button"); /*Cooking Style*/
var Add_Alg_Button = document.getElementById("Alg-add-button"); /*Allergens*/

/*DELETE ALL VARIABLES*/
var Delete_Ing_Button = document.getElementById("Ing-delete-all-button");
var Delete_Cat_Button = document.getElementById("Cat-delete-all-button");
var Delete_CSy_Button = document.getElementById("CSy-delete-all-button");
var Delete_Alg_Button = document.getElementById("Alg-delete-all-button");


/* ADD AND DELETE INGREDIENT ROW */
Add_Ing_Button.onclick = function insRowIngredient() {
    var table = document.getElementById("IngredientsTable");
    var totalRows = table.rows.length;
    var new_row = table.rows[0].cloneNode(true);
    var inp1 = new_row.cells[0].getElementsByTagName('input')[0]; //ingredient

    inp1.id += totalRows;
    inp1.value = ''; //new slot empty!

    table.appendChild(new_row);
}

function delRowIngredient(currentRow) {
    var table = document.getElementById("IngredientsTable");

    if (table.rows.length > 1) {
    var parentRowIndex = currentRow.parentNode.parentNode.rowIndex; //parent will have the index row that we want to delete
        document.getElementById("IngredientsTable").deleteRow(parentRowIndex);
    }
}

Delete_Ing_Button.onclick = function delAll_IngredientRows() {
    var table = document.getElementById("IngredientsTable");
    var totalrows = table.rows.length;
    var tableRows = table.getElementsByTagName('tr');

    for (var i=totalrows-1; i>0; i--) {
        table.removeChild(tableRows[i]);
    }
}

/* ADD AND DELETE COOKING STYLE ROW */

Add_CSy_Button.onclick = function insRowCookingStyle() {
    var tableCookStyle = document.getElementById("CookingStyleTable");
    var totalrows = tableCookStyle.rows.length;
    var new_row = tableCookStyle.rows[0].cloneNode(true);
    var inpCS = new_row.cells[0].getElementsByTagName('input')[0];

    inpCS.id += totalrows;
    inpCS.value = '';

    tableCookStyle.appendChild(new_row);
}

function delRowCookingStyle(currentRow) {
    var tableCookStyle = document.getElementById("CookingStyleTable");

    if (tableCookStyle.rows.length > 1) {
        var parentRowIndex = currentRow.parentNode.parentNode.rowIndex;
        document.getElementById("CookingStyleTable").deleteRow(parentRowIndex);
    }
}

Delete_CSy_Button.onclick = function delAll_CookingStyleRows() {
    var table = document.getElementById("CookingStyleTable");
    var totalrows = table.rows.length;
    var tableRows = table.getElementsByTagName('tr');

    for (var i=totalrows-1; i>0; i--) {
        table.removeChild(tableRows[i]);
    }
}
/* ADD AND DELETE CATEGORIES ROW */

Add_Cat_Button.onclick = function insRowCategory() {
    var tableCat = document.getElementById("CategoriesTable");
    var totalrows = tableCat.rows.length;
    var new_row = tableCat.rows[0].cloneNode(true);
    var inpC = new_row.cells[0].getElementsByTagName('input')[0];

    inpC.id += totalrows;
    inpC.value = "";

    tableCat.appendChild(new_row);
}

function delRowCategory(currentRow) {
    var tableCat = document.getElementById("CategoriesTable");

    if (tableCat.rows.length > 1) {
        var parentRowIndex = currentRow.parentNode.parentNode.rowIndex;
        document.getElementById("CategoriesTable").deleteRow(parentRowIndex);
    }

}

Delete_Cat_Button.onclick = function delAll_CategoryRows() {
    var table = document.getElementById("CategoriesTable");
    var totalrows = table.rows.length;
    var tableRows = table.getElementsByTagName('tr');

    for (var i=totalrows-1; i>0; i--) {
        table.removeChild(tableRows[i]);
    }
}

/* ADD AND DELETE ALLERGENS ROW */

Add_Alg_Button.onclick = function delRowAllergen() {
    var tableAlg = document.getElementById("AllergensTable");
    var totalrows = tableAlg.rows.length;
    var new_row = tableAlg.rows[0].cloneNode(true);
    var inpA = new_row.cells[0].getElementsByTagName('input')[0];

    inpA.id += totalrows;
    inpA.value = '';

    tableAlg.appendChild(new_row);
}

function delRowAllergen(currentRow) {
    var tableAlg = document.getElementById("AllergensTable");

    if (tableAlg.rows.length > 1) {
    var parentRowIndex = currentRow.parentNode.parentNode.rowIndex;
        document.getElementById("AllergensTable").deleteRow(parentRowIndex);
    }

}

Delete_Alg_Button.onclick = function delAll_AllergensRows() {
    var table = document.getElementById("AllergensTable");
    var totalrows = table.rows.length;
    var tableRows = table.getElementsByTagName('tr');

    for (var i=totalrows-1; i>0; i--) {
        table.removeChild(tableRows[i]);
    }
}
