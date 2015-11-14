package cardbattle.server;

/**
 * Resposta (sincronizada ou comum) de um serviço do CardBattle.
 *
 * Se for sincronizada, o Servlet que invocou o serviço ao qual a resposta
 * está retornando se encarrega de enviar um update para todos os clientes
 * conectados assincronamente a ele que pertençam a mesma batalha.
 *
 * Se não for sincronizada, é uma resposta comum que deve ser emitida
 * somente para a conexão que solicitou o serviço.
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
