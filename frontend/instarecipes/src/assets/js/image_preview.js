
document.getElementById('file-input').onchange = function (evt) {
  var tgt = evt.target || window.event.srcElement,
    files = tgt.files;
  // FileReader support
  if (FileReader && files && files.length) {
    var fr = new FileReader();
    fr.onload = function () {
        document.getElementById("preview-img-file").src = fr.result;
    }
    fr.readAsDataURL(files[0]);
  }
}

document.getElementById('bg-input').onchange = function (evt) {
  var tar = evt.target || window.event.srcElement,
    files = tar.files;
  if (FileReader && files && files.length) {
    var fre = new FileReader();
    fre.onload = function () {
      document.getElementById("preview-bg-file").src = fre.result;
    }
    fre.readAsDataURL(files[0]);
  }

}
