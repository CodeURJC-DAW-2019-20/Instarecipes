//var botoncito = document.getElementById('open-stadistics-button');
var botoncito = document.getElementById('canvasFrame');
botoncito.onclick = function() {
    
    var chart = new CanvasJS.Chart("canvasLikes", {
      title:{
        text: "My First Chart in CanvasJS"              
      },
      data: [              
      {
        // Change type to "doughnut", "line", "splineArea", etc.
        type: "column",
        dataPoints: [
          { label: "apple",  y: 10  },
          { label: "orange", y: 15  },
          { label: "banana", y: 25  },
          { label: "mango",  y: 30  },
          { label: "grape",  y: 28  }
        ]
      }
      ]
    });
    chart.render();
 
}