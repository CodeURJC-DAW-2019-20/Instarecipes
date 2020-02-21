
var botoncito = document.getElementById('canvasFrame');
var tira = ['Patatas', 'Pilotes','apple']
var likes =[3,10,22]
var datas = new Array(tira.length);
botoncito.onclick = function() {
  CreateLabels(tira,likes,datas);
    var chart = new CanvasJS.Chart("canvasLikes", {
      data: [              
      {
        type: "pie",
        dataPoints: datas,
      }
      ]
    });
    chart.render();
    console.log(dataPoints);
}
function CreateLabels (tira, likes, datas){
  n = tira.length();
  for ( i=0;i<n;i++) {
    name =tira[i];
    value = likes[i];
    CreateLabel(datas,name,value,pos);
  }
    return datas;
}
function CreateLabel (datas,name, value,pos){
  datas[pos]= {label: name, y: value};
  return datas;
}
