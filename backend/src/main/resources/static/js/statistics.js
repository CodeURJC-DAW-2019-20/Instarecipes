$(document).ready(function(){
  var titles1 = document.getElementById("Grapilotes").value.slice(1);
  var likes1 = document.getElementById("idkwtp").value.slice(1);

  var lastC = titles1.indexOf("]");
  var lastE = likes1.indexOf("]");
  
  var titles2 = titles1.slice(titles1, lastC);
  var titles = titles2.split(",");

  var likes2 = likes1.slice(likes1, lastE);
  var likes = likes2.split(",");
  
  var dataPoints;

  var chart = new CanvasJS.Chart("canvasLikes", {
    animationEnabled: true,
    exportEnabled: true,
    theme: "light2",
    data: [{
      type: "pie", 
      //indexLabel: "{y}", //Shows y value on all Data Points
      indexLabelFontColor: "#5A5757",
      indexLabelPlacement: "outside",
      indexLabel: "{label} {y}",
      dataPoints: dataPoints
    }]
  });
  for(var i = 0; i < likes.length; i++) {
    chart.options.data[0].dataPoints.push(titles[i]);
    chart.options.data[0].dataPoints.push(likes[i]);
  }
  chart.render();
})