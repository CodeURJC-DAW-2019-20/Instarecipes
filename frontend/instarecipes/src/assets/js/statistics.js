$(document).ready(function(){
  var titles1 = document.getElementById("Grapilotes").value.slice(1);
  var likes1 = document.getElementById("idkwtp").value.slice(1);

  var lastC = titles1.indexOf("]");
  var lastE = likes1.indexOf("]");
  
  var titles2 = titles1.slice(titles1, lastC);
  var titles = titles2.split(",");

  var likes2 = likes1.slice(likes1, lastE);
  var likes = likes2.split(",");
  
  var dataPoints = [];
  for(var i = 0; i < likes.length; i++) {
    var valuePoints={label:titles[i], y:likes[i]};
    dataPoints.push(valuePoints);
  }

  var chart = new CanvasJS.Chart("canvasLikes", {
      animationEnabled: true,
      exportEnabled: true,
	    theme: "light2", // "light1", "light2", "dark1", "dark2"
      data: [{
        type: "pie",
        indexLabelFontColor: "#5A5757",
        indexLabelPlacement: "outside",
		    dataPoints: dataPoints
	    }]
    });
  chart.render();
})