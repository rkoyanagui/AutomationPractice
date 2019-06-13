package br.com.rkoyanagui.core.storage;

import java.io.File;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.com.rkoyanagui.core.config.ProjectProperties;
import br.com.rkoyanagui.core.domain.PessoaFisica;
import br.com.rkoyanagui.core.exceptions.DataSearchException;
import br.com.rkoyanagui.core.utils.UtilidadesDeDataTempo;

public class LeitorDaPlanilhaCadastroDeCliente extends SynchronizedExcelUtils
{
	private static final String LOCAL_DA_PASTA_EXCEL =
			ProjectProperties.getInstance().getPastaMassa() + File.separator + "CadastroDeCliente.xlsx";
	
	XSSFSheet planilhaUsuarios;
	
	private static final int COL_ID = 0;
	private static final int COL_DATA = 1;
	private static final int COL_HORA = 2;
	private static final int COL_NOME = 3;
	private static final int COL_SOBRENOME = 4;
	private static final int COL_EMAIL = 5;
	private static final int COL_SENHA = 6;
	private static final int COL_ENDERECO = 7;
	private static final int COL_CIDADE = 8;
	private static final int COL_ESTADO = 9;
	private static final int COL_CODIGOPOSTAL = 10;
	private static final int COL_PAIS = 11;
	private static final int COL_CELULAR = 12;
	private static final int COL_REFERENCIA = 13;
	
	public static XSSFWorkbook getWorkbook()
	{
		if ( wb == null )
		{
			carregar( LOCAL_DA_PASTA_EXCEL );
		}
		return wb;
	}
	
	public static synchronized void escreverDados( PessoaFisica pessoaFisica ) throws DataSearchException
	{
		if ( wb == null )
		{
			carregar( LOCAL_DA_PASTA_EXCEL );
		}
		XSSFSheet sheet = wb.getSheet("CadastroDeCliente");
		
		String textoCelulaDeCima;
		int linhaVaziaEncontrada = encontrarLinhaVazia( sheet );
		if ( linhaVaziaEncontrada > 0 )
		{
			if ( linhaVaziaEncontrada > 1 )
			{
				textoCelulaDeCima = sheet.getRow( linhaVaziaEncontrada - 1 )
						.getCell( COL_ID ).getStringCellValue();
			}
			else
			{
				textoCelulaDeCima = "0";
			}
		}
		else
		{
			throw new DataSearchException(
					"Não foi encontrada nenhuma linha vazia em CadastroDeCliente.xlsx ..." );
		}
		
		String id = String.valueOf( Integer.parseInt( textoCelulaDeCima ) + 1 );
		pessoaFisica.setId( id );
		
		escreveLinha(sheet, linhaVaziaEncontrada, pessoaFisica);
		salvaPastaDeTrabalho();
	}
	
	private static synchronized int encontrarLinhaVazia( XSSFSheet sheet )
	{
		int linhaVazia = -1;
		for ( Row row : sheet )
		{
			if ( row.getRowNum() > 0 )
			{
				Cell celulaID = row.getCell( COL_ID );
				if ( celulaID == null )
				{
					linhaVazia = row.getRowNum();
					break;
				}
				else
				{
					String textoCelula = celulaID.getStringCellValue();
					if ( textoCelula == null || textoCelula.isEmpty() )
					{
						linhaVazia = row.getRowNum();
						break;
					}
				}
			}
		}
		return linhaVazia;
	}
	
	private static synchronized void escreveLinha(
			XSSFSheet sheet, int linhaVazia, PessoaFisica pessoaFisica )
	{
		editarCelula( sheet, linhaVazia, COL_ID, pessoaFisica.getId() );
		UtilidadesDeDataTempo utilsData = new UtilidadesDeDataTempo();
		editarCelula( sheet, linhaVazia, COL_DATA, utilsData.obterDataDeHoje(0) );
		editarCelula( sheet, linhaVazia, COL_HORA, utilsData.obterHorarioAtual() );
		editarCelula( sheet, linhaVazia, COL_NOME, pessoaFisica.getNome() );
		editarCelula( sheet, linhaVazia, COL_SOBRENOME, pessoaFisica.getSobrenome() );
		editarCelula( sheet, linhaVazia, COL_EMAIL, pessoaFisica.getEmail() );
		editarCelula( sheet, linhaVazia, COL_SENHA, pessoaFisica.getSenha() );
		editarCelula( sheet, linhaVazia, COL_ENDERECO, pessoaFisica.getEndereco() );
		editarCelula( sheet, linhaVazia, COL_CIDADE, pessoaFisica.getCidade() );
		editarCelula( sheet, linhaVazia, COL_ESTADO, pessoaFisica.getEstado() );
		editarCelula( sheet, linhaVazia, COL_CODIGOPOSTAL, pessoaFisica.getCodigoPostal() );
		editarCelula( sheet, linhaVazia, COL_PAIS, pessoaFisica.getPais() );
		editarCelula( sheet, linhaVazia, COL_CELULAR, pessoaFisica.getCelular() );
		editarCelula( sheet, linhaVazia, COL_REFERENCIA, pessoaFisica.getReferencia() );
	}
}
