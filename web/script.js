/* TODO
var messagesWaiting = false;

function getMessages() {
	if (!messagesWaiting) {
		messagesWaiting = true;
		var xr = new XMLHttpRequest();
		xr.onreadystatechange = function() {
			if (xr.readyState == 4 && xr.status == 200) {
				messagesWaiting = false;
				var e = document.getElementById("push_result");
				e.innerHTML = ((~~performance.now()) / 1000) + ': ' + xr.responseText + '\n' + e.innerHTML;
			}
		}
		xr.open("GET", "CardBattle?updateStatus", true);
		xr.send();
	}
	setTimeout(getMessages, 5000);
}

getMessages();
*/