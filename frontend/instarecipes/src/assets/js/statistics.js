$(document).ready(function() {
    var titles1 = document.getElementById("titlesArray").value;
    var likes1 = document.getElementById("likesArray").value;

    var titles = titles1.split(",");

    var likes = likes1.split(",");

    var dataPoints = [];
    for (var i = 0; i < likes.length; i++) {
        var valuePoints = { label: titles[i], y: likes[i]};
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
