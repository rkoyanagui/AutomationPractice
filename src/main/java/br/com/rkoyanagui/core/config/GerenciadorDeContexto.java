package br.com.rkoyanagui.core.config;

import java.util.HashMap;

import br.com.rkoyanagui.core.domain.PessoaFisica;
import br.com.rkoyanagui.core.domain.Usuario;

public class GerenciadorDeContexto {
	private static ThreadLocal<String> idCenario = new ThreadLocal<String>();
	
	private static ThreadLocal<Integer> atualTentativa = new ThreadLocal<Integer>();
	private static ThreadLocal<Integer> qtdeUsuarios = new ThreadLocal<Integer>();
	
	private static ThreadLocal<Usuario> usuario = new ThreadLocal<Usuario>();
	private static ThreadLocal<PessoaFisica> pessoaJuridica = new ThreadLocal<PessoaFisica>();
	
	private static final ThreadLocal<HashMap<String, Object>> info = new ThreadLocal<HashMap<String, Object>>();
	
	public static void setInfo( String key, Object value )
	{
		HashMap<String, Object> map = info.get();
		if ( map == null )
		{
			map = new HashMap<String, Object>();
		}
		map.put( key, value );
		info.set( map );
	}
	
	public static Object getInfo( String key )
	{
		return info.get().get( key );
	}
	
	public static PessoaFisica getPessoaJuridica() {
        return pessoaJuridica.get();
    }
 
    public static void setPessoaJuridica(PessoaFisica p) {
    	pessoaJuridica.set(p);
    }
	
	public static Usuario getUsuario() {
        return usuario.get();
    }
 
    public static void setUsuario(Usuario u) {
    	usuario.set(u);
    }
	
	public static int getQtdeUsuarios() {
        return qtdeUsuarios.get().intValue();
    }
 
    public static void setQtdeUsuarios(int q) {
    	qtdeUsuarios.set(Integer.valueOf(q));
    }
	
	public static int getAtualTentativa() {
        return atualTentativa.get().intValue();
    }
 
    public static void setAtualTentativa(int t) {
    	atualTentativa.set(Integer.valueOf(t));
    }
    
    public static void incrementAtualTentativa() {
    	int t = atualTentativa.get().intValue();
    	atualTentativa.set(Integer.valueOf(t + 1));
    }
	 
    public static String getIdCenario() {
        return idCenario.get();
    }
 
    public static void setIdCenario(String id) {
    	idCenario.set(id);
    }
}
