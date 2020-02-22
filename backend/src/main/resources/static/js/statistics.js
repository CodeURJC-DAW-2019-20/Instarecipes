$(document).ready(function(){

var titles = document.getElementById("Grapilotes").value.split(",");
var likes = document.getElementById("idkwtp").value.split(",");
var i=0; 

console.log(titles);
console.log(likes);
console.log(titles[0]);
console.log(titles[1]);
console.log(titles[2]);
console.log(likes[1]);
console.log(likes[2]);
var num = parseInt(likes[0], 10);
console.log(num);
//for(i=0;i<titles.length;i++){
  //console.log("el nombre de la receta es "+ titles[i]);
  //console.log("putos likes de mierda: "+ likes);
  //console.log("putos likes de mierda2: "+ Object.values(mayonesa)[i]);


var dataPoints = [
  { label: titles[0], y: num },
  { label: titles[1], y: num },
  { label: titles[2], y: num }
];
    var chart = new CanvasJS.Chart("canvasLikes", {
      animationEnabled: true,
      exportEnabled: true,
	    theme: "light2", // "light1", "light2", "dark1", "dark2"
      data: [{
        type: "pie", 
        //indexLabel: "{y}", //Shows y value on all Data Points
        indexLabelFontColor: "#5A5757",
        indexLabelPlacement: "outside",
    
		    dataPoints: dataPoints
	    }]
    });
    chart.render();
})