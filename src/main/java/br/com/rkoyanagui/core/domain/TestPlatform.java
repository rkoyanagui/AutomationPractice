package br.com.rkoyanagui.core.domain;

public enum TestPlatform {
	LOCAL, REMOTE;
	
	public static String valoresPermitidos() {
		String valoresPermitidos = "";
		TestPlatform[] valores = TestPlatform.values();
		for (TestPlatform valor : valores) {
			valoresPermitidos = valoresPermitidos + valor.toString() + ", ";
		}
		valoresPermitidos = valoresPermitidos.substring(0, valoresPermitidos.length() - 2);
		return valoresPermitidos;
	}
}
