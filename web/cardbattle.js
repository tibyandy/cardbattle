$(function () {
	var _tryingLogIn = false;
	var _playerName = null;
	
	$('#growl').hide();
	$.post('Login', checkLogin);
	$('#connect').on('click', tryLogin);
	$('#ver').html(CB.version);
	$('#logout').on('click', logout);
	$('#login').on('click', function (e) {
		focusPlayerId();
	});
	$('#password, #connect').on('click focus', function (e) {
		e.stopPropagation();
	});

	$('body form').submit(function (e) {
		console.log("submit");
		e.preventDefault();
		return false;
	});
	
	function growl(msg) {
		$('#growl').html(msg).hide().finish().slideDown(100).delay(2000).fadeOut(300);
	}
	
	function checkLogin(data) {
		if (data[0] == '0') {
			$('#logout').hide();
			if (data.length == 1) {
				growl('Invalid player ID or password.');
				$('#password').focus();
			} else {
				data = data.split(';');
				var i, n;
				var options = '<option></option>';
				for (i = 1, n = data.length; i < n; i++) {
					options += '<option>' + data[i] + '</option>';
				}
				$('#playerId').html(options);
				$('#login').hide().fadeIn();
			}
		} else {
			_playerName = data.substr(1);
			$('#logout').show().html('Logout [' + _playerName + ']');
			if (_tryingLogIn) {
				growl('Welcome, ' + _playerName + '! You are now connected!');
				$('#login').fadeOut();
				_tryingLogIn = false;
			} else {
				growl('Welcome back, ' + _playerName + '!');
				$('#login').hide();
			}
		}
		$('body').removeClass('loading');
	};

	function tryLogin() {
		var pwd = $('#password').val();
		$('#password').val('');
		_playerName = $('#playerId').val().trim();
		_tryingLogIn = true;
		$.post('Login', {q: _playerName, w: pwd}, checkLogin);
	}

	function logout() {
		growl('Disconnecting...');
		$('#logout').hide();
		$.post('Login', {q: 'logout'}, afterLogout);
	}

	function afterLogout(data) {
		growl('You were disconnected from the server.');
		_playerName = null;
		_tryingLogIn = false;
		$('#login').fadeIn(function () {
			focusPlayerId();
		});
		checkLogin(data);
	}
	
	function focusPlayerId() {
		setTimeout(function () {
			$('#playerId').focus();
		}, 50);
	}

	focusPlayerId();
});