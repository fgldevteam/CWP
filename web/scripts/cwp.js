function openWindow(aURL, aName) {
	imgWindow = window.open(aURL, aName ,'scrollbars=yes,resizable=yes,height=500,width=700');
	imgWindow.opener = window;
	imgWindow.focus();
}

function openLink(url, name, confirmText){
  var answer = confirm(confirmText);
  if (answer) {
	  var w = window.open(url,name,'titlebar=1,toolbar=1,location=1,menubar=1,scrollbars=1,resizable=1,status=1');
      w.opener = window;
      w.focus();
      return false;
  }
  return true;
}


function setInitialFocus(){
    theForm = document.forms[0];
    if (theForm != null){
        for (var i=0; i<theForm.elements.length; ++i) {
            var theElement = theForm.elements[i];
            // set focus on the first form element that is not hidden
            if (theElement != null && theElement.type != 'hidden') {
                theElement.focus();
                break;
            }
        }
    }
}