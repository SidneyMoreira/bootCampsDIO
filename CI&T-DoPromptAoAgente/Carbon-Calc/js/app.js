/**
 * app.js - Inicialização e manipulação de eventos da calculadora de CO2
 * Define a lógica principal da aplicação usando IIFE para isolamento.
 */
(function() {
    'use strict';

    /**
     * Inicializa a aplicação quando o DOM estiver pronto.
     */
    function initializeApp() {
        // Verifica se as dependências estão disponíveis
        if (typeof CONFIG === 'undefined' || typeof UI === 'undefined' || typeof Calculator === 'undefined') {
            console.error('Dependências não encontradas. Verifique se todos os scripts foram carregados.');
            return;
        }

        // 1. Popula o datalist com cidades para autocomplete
        CONFIG.populateDatalist();

        // 2. Configura o preenchimento automático de distância
        CONFIG.setupDistanceAutofill();

        // 3. Obtém o formulário e adiciona listener de submit
        var calculatorForm = document.getElementById('calculator-form');
        if (calculatorForm) {
            calculatorForm.addEventListener('submit', handleFormSubmit);
        } else {
            console.error('Formulário não encontrado');
            return;
        }

        // 4. Log de inicialização
        console.log('✅ Calculadora inicializada!');
    }

    /**
     * Manipula o envio do formulário.
     * @param {Event} event - Evento de submit
     */
    function handleFormSubmit(event) {
        // 1. Previne o comportamento padrão do formulário
        event.preventDefault();

        // 2. Obtém os valores dos campos
        var origin = document.getElementById('origin').value.trim();
        var destination = document.getElementById('destination').value.trim();
        var distance = parseFloat(document.getElementById('distance').value);
        var transportMode = document.querySelector('input[name="transport"]:checked').value;

        // 3. Validação dos dados
        if (!origin || !destination || !distance || distance <= 0) {
            alert('Por favor, preencha todos os campos corretamente. A distância deve ser maior que zero.');
            return;
        }

        // 4. Obtém o botão de submit e mostra estado de carregamento
        var submitButton = document.querySelector('.calculator-form__submit');
        UI.showLoading(submitButton);

        // 5. Oculta seções de resultados anteriores
        UI.hideElement('results');
        UI.hideElement('comparation');
        UI.hideElement('carbon-credits');

        // 6. Simula processamento com delay de 1.5 segundos
        setTimeout(function() {
            try {
                // Calcula emissão para o modo selecionado
                var selectedEmission = Calculator.calculateEmission(distance, transportMode);

                // Calcula emissão do carro a gasolina como baseline
                var carEmission = Calculator.calculateEmission(distance, 'gasCar');

                // Calcula economia em relação ao carro
                var savings = Calculator.calculateSavings(selectedEmission, carEmission);

                // Calcula comparação com todos os modos
                var allModesComparison = Calculator.calculateAllModes(distance);

                // Calcula créditos de carbono
                var carbonCredits = Calculator.calculateCarbonCredits(selectedEmission);

                // Calcula estimativa de preço dos créditos
                var creditPrice = Calculator.estimateCreditPrice(carbonCredits);

                // Monta objeto de dados para renderização
                var resultsData = {
                    origin: origin,
                    destination: destination,
                    distance: distance,
                    emission: selectedEmission,
                    mode: transportMode,
                    savings: savings
                };

                var creditsData = {
                    credits: carbonCredits,
                    price: creditPrice
                };

                // Renderiza e exibe os resultados
                document.getElementById('results-content').innerHTML = UI.renderResults(resultsData);
                document.getElementById('comparison-content').innerHTML = UI.renderComparison(allModesComparison, transportMode);
                document.getElementById('carbon-credits-content').innerHTML = UI.renderCarbonCredits(creditsData);

                // Mostra as seções de resultados
                UI.showElement('results');
                UI.showElement('comparation');
                UI.showElement('carbon-credits');

                // Rola suavemente para a seção de resultados
                UI.scrollToElement('results');

                // Remove estado de carregamento
                UI.hideLoading(submitButton);

            } catch (error) {
                // Trata erros durante o processamento
                console.error('Erro durante o cálculo:', error);
                alert('Ocorreu um erro durante o cálculo. Tente novamente.');
                UI.hideLoading(submitButton);
            }
        }, 1500);
    }

    // Inicializa a aplicação quando o DOM estiver pronto
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', initializeApp);
    } else {
        initializeApp();
    }

})();