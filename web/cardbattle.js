$(function () {
	var _tryingLogIn = false;
	var _playerName = null;
	var _characters;

	$('#growl').hide();
	$('#you, #players').hide();
	$.post('Login', checkLogin);
	$('#ver').html(CB.version);
	$('#logout').on('click', logout);
	$('#login').on('click', function (e) {
		focusPlayerId();
	});
	$('#password, #connect').on('click focus', function (e) {
		e.stopPropagation();
	});
	$('#cancel').on('click', cancelCharacter);

	$('#connect').on('click', tryLogin);
	$('body form').submit(function (e) {
		tryLogin();
		e.preventDefault();
		return false;
	});
	
	function growl(msg) {
		$('#growl').html(msg).hide().finish().slideDown(100).delay(2000).fadeOut(300);
	}
	
	function checkLogin(data) {
		if (data[0] == '0') {
			loginFailed(data);
		} else {
			loginSuccess(data);
		}
		$('body').removeClass('loading');
	};

	function loginFailed(data) {
		$('#logout').hide();
		if (data.length == 1) {
			growl('Invalid player ID or password.');
			$($('#playerId').val() ? '#password' : '#playerId').focus();
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
	}

	function loginSuccess(data) {
		_playerName = data.substr(1);
		$('.playerId').html(_playerName);
		$('#logout').show().html('Logout [' + _playerName + ']');
		if (_tryingLogIn) {
			growl('Welcome, ' + _playerName + '! You are now connected!');
			$('#login').fadeOut();
			_tryingLogIn = false;
		} else {
			growl('Welcome back, ' + _playerName + '!');
			$('#login').hide();
		}
		$('#you, #players').fadeIn();
		$.get('CardBattle?listCharacters', printCharacterList);
	}

	function printCharacterList(data) {
		var characters = data.split('\n');
		var i, characterData;
		_characters = {};
		var $li;
		var fn;
		var $ul = $('#characterList').attr('size', characters.length).off('change').on('change', pickCharacter);
		for (i in characters) {
			characterData = characters[i] = characters[i].split(';');
			_characters[characterData[0]] = characterData;
			$li = $('<option>' + characterData[0] + '</option>');
			$li.on('click', fn);
			$ul.append($li);
		}
	}

	function pickCharacter() {
		var characterData = _characters[$('#characterList').val()];
		var i = 0;
		$('#char_info li span').each(function () {
			$(this).html(characterData[i]);
			i++;
		});
		$('#battle_info button').removeAttr('disabled');
	}

	
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
		$('#you, #players').fadeOut();
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

	function cancelCharacter() {
		$('#battle_info button').attr('disabled', '');
		$('#char_info li span').html('');
		$('#characterList').val(null);
	}

	focusPlayerId();
});