$(function () {
	var $polling = $('#polling').hide();
	var $stop = $('#stop');
	var $push_result = $('#push_result');

	var _clearChanged = null;

	$('#poll1').on('click', function () { startPoll(1); });
	$('#poll2').on('click', function () { startPoll(2); });
	$('#poll0').on('click', function () { stopPoll(); });
	$('#preset_commands a').on('click', function () {
		$push_result.append('\n&gt; ' + $.trim($(this).html())).scrollTop($push_result[0].scrollHeight);
	});

	var _battleId = null;
	var _polling = false;
	var _nextPollTime;

	var $battleStatus = $('#battle_status');

	function startPoll(battleId) {
		$push_result.html('Logging Battle ' + battleId + ' updates.');
		_battleId = battleId;
		_polling = true;
		_nextPollTime = performance.now() + 1000;
		requestBattleStatus(battleId);
	}

	function stopPoll() {
		_polling = false;
	}
	
	function getPolling() {
		return _polling;
	}

	function updateBattleStatus(data) {
		if (_clearChanged != null) {
			clearTimeout(_clearChanged);
		}
		$battleStatus.removeClass('changed');
		setTimeout(function () {
			var dataLines = data.split('\n');
			$push_result.append('\n' + dataLines[0] + ' Autoupdate due to ' + dataLines[1]).scrollTop($push_result[0].scrollHeight);
			$battleStatus.html(data).addClass('changed');
			_clearChanged = setTimeout(function () {
				$battleStatus.removeClass('changed');
				_clearChanged = null;
			}, 3000);
		}, 100);		
	}

	function requestBattleStatus(battleId) {
		$polling.show();
		$stop.hide();
		$.get('CardBattle?status ' + _battleId + ' 0', function (data) {
			$polling.hide();
			$stop.show();
			if (battleId == _battleId) {
				updateBattleStatus(data);
				if (getPolling()) {
					var now = performance.now();
					var nextPollDelay = _nextPollTime - now;
					if (nextPollDelay <= 0) {
						nextPollDelay = 1;
					}
					_nextPollTime = now + 100;
					setTimeout(function () {
						requestBattleStatus(battleId);
					}, nextPollDelay);
				}
			}
		});
	}
});
