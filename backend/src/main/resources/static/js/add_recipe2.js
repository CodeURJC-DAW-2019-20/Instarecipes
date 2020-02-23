

var add_Ing_Button = document.getElementById("add-ingredient");



add_Ing_Button.onclick = function insRowIngredient() {
    var table = document.getElementById("ingredientsTableAdd");
    var totalRows = table.rows.length;
    var new_row = table.rows[0].cloneNode(true);
    var inp1 = new_row.cells[0].getElementsByTagName('input')[0]; //ingredient
    
    inp1.id += totalRows;
    inp1.value = ''; //new slot empty!
    
    table.appendChild(new_row);
}

function delRowIngredientAdd(currentRow) {
    var table = document.getElementById("ingredientsTableAdd");

    if (table.rows.length > 1) {
    var parentRowIndex = currentRow.parentNode.parentNode.rowIndex; //parent will have the index row that we want to delete 
        document.getElementById("ingredientsTableAdd").deleteRow(parentRowIndex);
    }
}
