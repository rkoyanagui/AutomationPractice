package br.com.rkoyanagui.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilidadesDeRegex {

	/**
	 * Converte um texto como "foo&nbsp&nbsp&nbsp&nbspbaa" em "foo baa",
	 * transformando múltiplos espaços seguidos em espaços simples.
	 * 
	 * @param texto
	 *            o texto a ser convertido
	 * @return o texto dado após a remoção de espaços múltiplos seguidos.
	 */
	public String reduzMultiplosEspacosEmUnicoEspaco(String texto) {
		String limpo = texto;
		Pattern pattern = Pattern.compile("(.*)(\\S)(\\s{2,})(\\S)(.*)");
		Matcher matcher = pattern.matcher(limpo);
		while (matcher.matches()) {
			String inicio = matcher.group(1);
			String meio1 = matcher.group(2);
			String meio2 = matcher.group(4);
			String fim = matcher.group(5);
			limpo = inicio + meio1 + " " + meio2 + fim;
			matcher = pattern.matcher(limpo);
		}
		return limpo;
	}
}
