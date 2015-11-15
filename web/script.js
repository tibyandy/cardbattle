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
		$push_result.html(timestamp() + ' Logging Battle ' + battleId + ' updates.');
		_battleId = battleId;
		_polling = true;
		_nextPollTime = timestamp() + 1000;
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
			$push_result.append('\n' + timestamp() + ' [' + dataLines[0] + '] Autoupdate due to ' + dataLines[1]).scrollTop($push_result[0].scrollHeight);
			$battleStatus.html(data).addClass('changed');
			_clearChanged = setTimeout(function () {
				$battleStatus.removeClass('changed');
				_clearChanged = null;
			}, 3000);
		}, 100);		
	}

	function timestamp() {
		return new Date().getTime();
	}
	
	function requestBattleStatus(battleId) {
		$polling.show();
		$stop.hide();
		var time = timestamp();
		$push_result.append('\n' + time + ' Request battle ' + _battleId + ' status update queued.').scrollTop($push_result[0].scrollHeight);

		// IMPORANTE: É necessário enviar um timestamp no request ajax, pois
		// se forem dois request ajax assíncronos IDÊNTICOS em seguida, o
		// servidor só vai considerar um, e aí só um dos clientes vai
		// receber as notificações...
		var ajaxRequest = 'CardBattle?status ' + _battleId + ' ' + time;

		$.get(ajaxRequest, function (data) {
			$push_result.append('\n' + timestamp() + ' Battle status update received').scrollTop($push_result[0].scrollHeight);
			$polling.hide();
			$stop.show();
			if (battleId == _battleId) {
				updateBattleStatus(data);
				if (getPolling()) {
					var now = timestamp();
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
