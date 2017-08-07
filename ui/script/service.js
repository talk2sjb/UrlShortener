function shortify() {
  var search_box = document.getElementById("search_box");
  var url_display = document.getElementById("url_display");
  var payload = search_box.value;
 
  url_display.style.backgroundColor = "#CEFCF5";
 
  if(!payload){
    url_display.style.visibility = "visible";
    url_display.style.backgroundColor = "#FFCDDC";
    url_display.innerHTML = "Please Enter a Valid URL.";
    return;
  }

  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      url_display.style.visibility = "visible";
      url_display.innerHTML = this.responseText;     
    }
  };
  xhttp.open("POST", "http://localhost:8080/shorten", true);
  xhttp.setRequestHeader("Content-type", "application/json");
  xhttp.send("{\"url\":\"" + payload + "\"}");
}
