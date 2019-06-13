#language: pt
#utf-8

Funcionalidade: Primeira Compra
	Eu, como cliente pessoa física,
	Quero escolher um produto e me cadastrar na loja,
	Para finalizar minha primeira compra

	Contexto:
		Dado que esteja aberta a página de entrada do site "http://automationpractice.com/index.php"

	Cenário: Primeira Compra e Cadastro de Novo Usuário
		Quando escolho um produto qualquer na loja
		E adiciono o produto escolhido ao carrinho
		E prossigo para o checkout
		E valido que o produto foi adicionado ao carrinho
		E realizo meu cadastro preenchendo todos os campos obrigatórios dos formulários
		E valido que o endereço está correto
		E aceito os termos de serviço
		E valido o valor da compra
		E seleciono um método de pagamento
		E confirmo a compra
		Então o site deve finalizar a compra com sucesso
		