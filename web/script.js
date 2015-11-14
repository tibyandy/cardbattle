$(function () {
	$('iframe').on('load', loadedFrame);

	var _battleId = null;
	function loadedFrame() {
		if (_battleId != null) {
			$.get('CardBattle?status ' + _battleId + ' 0', function (data) {
				$('#battle_status').html(data);
			});
		}
	}

	var $battleStatus = $('#battle_status');
	$('ul a').on('click', function () {
		_battleId = $(this).closest('ul').attr('battleId');
	});
})
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