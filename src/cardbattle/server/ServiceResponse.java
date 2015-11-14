package cardbattle.server;

/**
 * Resposta (sincronizada ou comum) de um servi�o do CardBattle.
 *
 * Se for sincronizada, o Servlet que invocou o servi�o ao qual a resposta
 * est� retornando se encarrega de enviar um update para todos os clientes
 * conectados assincronamente a ele que perten�am a mesma batalha.
 *
 * Se n�o for sincronizada, � uma resposta comum que deve ser emitida
 * somente para a conex�o que solicitou o servi�o.
 *
 * @author Andy
 */
public class ServiceResponse {

	public final boolean synchedResponse;
	public final String message;

	private ServiceResponse(boolean updateStatusPolls, String string) {
		this.message = string;
		this.synchedResponse = updateStatusPolls;
	}

	public static ServiceResponse syncResponse(String s) {
		return new ServiceResponse(true, s);
	}

	public static ServiceResponse response(String s) {
		return new ServiceResponse(false, s);
	}
}
