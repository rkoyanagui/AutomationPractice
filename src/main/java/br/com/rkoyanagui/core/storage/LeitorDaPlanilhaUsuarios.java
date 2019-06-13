package br.com.rkoyanagui.core.storage;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.rkoyanagui.core.config.ProjectProperties;
import br.com.rkoyanagui.core.domain.Usuario;
import br.com.rkoyanagui.core.exceptions.UserSearchException;

public class LeitorDaPlanilhaUsuarios {
	
	private static final Logger LOG = LogManager.getLogger(LeitorDaPlanilhaUsuarios.class);
	
	private static final String LOCAL_DA_PASTA_EXCEL =
			ProjectProperties.getInstance().getPastaMassa() + "/Usuarios.xlsx";
	private static final int COL_ID_CENARIO = 1;
	
	private static final int COL_PRIMEIRO_USUARIO = 3;
	private static final int COL_ULTIMO_USUARIO = 3;
	
	private static final int LIN_NOMEACESSO = 1;
	private static final int LIN_SENHAACESSO = 2;
	private static final int LIN_ID_USUARIO = 3;
	
	public static synchronized List<Usuario> buscaUsuarios(
			String idCenario)
			throws UserSearchException
	{
		ExcelUtils excel = null;
		List<Usuario> usuarios = null;
		int linhaCenario = -1;
		String buscaLinha = null;
		
		try {
			excel = new ExcelUtils(LOCAL_DA_PASTA_EXCEL);
		}
		catch ( Exception e ) 
		{
			LOG.error( "Erro ao abrir a planilha de Usuários ...", e );
			throw new UserSearchException( "Erro ao abrir a planilha de Usuários ...", e );
		}
		
		try {
			buscaLinha = idCenario;
			linhaCenario = excel.achaLinhaPorTexto(buscaLinha, COL_ID_CENARIO);
		}
		catch ( Exception e ) 
		{
			LOG.error( "Erro ao buscar a linha na planilha de Usuários para: " + buscaLinha, e );
			throw new UserSearchException(
					"Erro ao buscar a linha na planilha de Usuários para: " + buscaLinha, e );
		}
		
		int colunaUsuario = -1;
		if ( linhaCenario > -1 )
		{
			usuarios = new ArrayList<Usuario>();
			for ( int colUsuario = COL_PRIMEIRO_USUARIO; colUsuario <= COL_ULTIMO_USUARIO; colUsuario++ )
			{
				String txtCell = excel.getTextoCelula(linhaCenario, colUsuario);
				if ( txtCell != null && !txtCell.trim().isEmpty() )
				{
					colunaUsuario = colUsuario;
					break;
				}
			}
			
			if ( colunaUsuario > -1 )
			{
				String id = excel.getTextoCelula( LIN_ID_USUARIO, colunaUsuario );
				String nomeAcesso = excel.getTextoCelula( LIN_NOMEACESSO, colunaUsuario );
				String senhaAcesso = excel.getTextoCelula( LIN_SENHAACESSO, colunaUsuario );
				usuarios.add( new Usuario( id, nomeAcesso, senhaAcesso ) );
			}
			else
			{
				throw new UserSearchException(
						"Erro de busca de usuários: não há usuários para o Cenário '" + idCenario + "' ...");
			}
		}
		else
		{
			throw new UserSearchException(
					"Erro de busca de usuários: não está listado o Cenário '" + idCenario + "' ...");
		}
		
		return usuarios;
	}
}
