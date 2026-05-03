/**
 * UI - Helpers para formatação, renderização e controle visual
 * Define um único objeto global para uso na aplicação.
 */
var UI = {
    /**
     * Formata um número com casas decimais e separadores de milhares em pt-BR.
     * @param {number} number
     * @param {number} decimals
     * @returns {string}
     */
    formatNumber: function(number, decimals) {
        var value = Number(number);
        if (Number.isNaN(value)) {
            value = 0;
        }
        return value.toLocaleString('pt-BR', {
            minimumFractionDigits: decimals,
            maximumFractionDigits: decimals
        });
    },

    /**
     * Formata um valor numérico como moeda brasileira.
     * @param {number} value
     * @returns {string}
     */
    formatCurrency: function(value) {
        var amount = Number(value);
        if (Number.isNaN(amount)) {
            amount = 0;
        }
        return amount.toLocaleString('pt-BR', {
            style: 'currency',
            currency: 'BRL'
        });
    },

    /**
     * Remove a classe hidden de um elemento por ID.
     * @param {string} elementId
     */
    showElement: function(elementId) {
        var element = document.getElementById(elementId);
        if (element) {
            element.classList.remove('hidden');
        }
    },

    /**
     * Adiciona a classe hidden a um elemento por ID.
     * @param {string} elementId
     */
    hideElement: function(elementId) {
        var element = document.getElementById(elementId);
        if (element) {
            element.classList.add('hidden');
        }
    },

    /**
     * Rola suavemente até o elemento identificado por ID.
     * @param {string} elementId
     */
    scrollToElement: function(elementId) {
        var element = document.getElementById(elementId);
        if (element) {
            element.scrollIntoView({ behavior: 'smooth', block: 'start' });
        }
    },

    /**
     * Gera o HTML de resultados principais da rota e emissão.
     * @param {Object} data
     * @returns {string}
     */
    renderResults: function(data) {
        var transportMeta = {};
        if (typeof CONFIG !== 'undefined' && CONFIG.TRANSPORT_MODES) {
            transportMeta = CONFIG.TRANSPORT_MODES[data.mode] || {};
        }

        // Função auxiliar para obter ícone com empilhamento para tipos de carro
        var getTransportIcon = function(mode) {
            switch (mode) {
                case 'gasCar':
                    return '<span class="results_card__icon--stack"><span>🚗</span></span>';
                case 'electricCar':
                    return '<span class="results_card__icon--stack"><span>⚡</span><span>🚗</span></span>';
                case 'hybridCar':
                    return '<span class="results_card__icon--stack"><span>🔋</span><span>🚗</span></span>';
                case 'flexCar':
                    return '<span class="results_card__icon--stack"><span>🌿</span><span>🚗</span></span>';
                default:
                    return transportMeta.icon || '';
            }
        };

        var routeCard = "<div class='results_card'>" +
            "<div class='results_icon'>📍</div>" +
            "<div class='results_data'>" +
            "<div class='results_label'>ROTA</div>" +
            "<div class='results_value'>" + data.origin + " → " + data.destination + "</div>" +
            "</div>" +
            "</div>";

        var distanceCard = "<div class='results_card'>" +
            "<div class='results_icon'>📏</div>" +
            "<div class='results_data'>" +
            "<div class='results_label'>DISTÂNCIA</div>" +
            "<div class='results_value'>" + this.formatNumber(data.distance, 0) + " km</div>" +
            "</div>" +
            "</div>";

        var emissionCard = "<div class='results_card'>" +
            "<div class='results_icon'>🌿</div>" +
            "<div class='results_data'>" +
            "<div class='results_label'>EMISSÃO CO₂</div>" +
            "<div class='results_value'>" + this.formatNumber(data.emission, 2) + " kg</div>" +
            "</div>" +
            "</div>";

        var transportCard = "<div class='results_card'>" +
            "<div class='results_icon'>" + getTransportIcon(data.mode) + "</div>" +
            "<div class='results_data'>" +
            "<div class='results_label'>TRANSPORTE</div>" +
            "<div class='results_value'>" + (transportMeta.label || data.mode) + "</div>" +
            "</div>" +
            "</div>";

        var savingsCard = '';
        if (data.mode !== 'gasCar' && data.savings && data.savings.savedKg > 0) {
            savingsCard = "<div class='results_card results_savings'>" +
                "<div class='results_icon'>💰</div>" +
                "<div class='results_data'>" +
                "<div class='results_label'>ECONOMIA</div>" +
                "<div class='results_value'>" + this.formatNumber(data.savings.savedKg, 2) + " kg CO₂</div>" +
                "<div class='results_label'>" + this.formatNumber(data.savings.percentage, 2) + "% vs Carro a Gasolina</div>" +
                "</div>" +
                "</div>";
        }

        return "<div class='results_cards'>" + routeCard + distanceCard + emissionCard + transportCard + savingsCard + "</div>";
    },

    /**
     * Gera o HTML da comparação entre modos de transporte.
     * @param {Array<Object>} modesArray
     * @param {string} selectedMode
     * @returns {string}
     */
    renderComparison: function(modesArray, selectedMode) {
        var maxEmission = 0;
        modesArray.forEach(function(item) {
            if (item.emission > maxEmission) {
                maxEmission = item.emission;
            }
        });

        if (maxEmission === 0) {
            maxEmission = 1;
        }

        // Função auxiliar para obter ícone com empilhamento para tipos de carro
        var getTransportIcon = function(mode) {
            switch (mode) {
                case 'gasCar':
                    return '<span class="comparation_item__icon--stack"><span>🚗</span></span>';
                case 'electricCar':
                    return '<span class="comparation_item__icon--stack"><span>⚡</span><span>🚗</span></span>';
                case 'hybridCar':
                    return '<span class="comparation_item__icon--stack"><span>🔋</span><span>🚗</span></span>';
                case 'flexCar':
                    return '<span class="comparation_item__icon--stack"><span>🌿</span><span>🚗</span></span>';
                default:
                    var meta = (typeof CONFIG !== 'undefined' && CONFIG.TRANSPORT_MODES && CONFIG.TRANSPORT_MODES[mode]) ? CONFIG.TRANSPORT_MODES[mode] : { icon: '' };
                    return meta.icon || '';
            }
        };

        var html = '';

        modesArray.forEach(function(item) {
            var meta = (typeof CONFIG !== 'undefined' && CONFIG.TRANSPORT_MODES && CONFIG.TRANSPORT_MODES[item.mode]) ? CONFIG.TRANSPORT_MODES[item.mode] : { label: item.mode, icon: '' };
            var isSelected = item.mode === selectedMode;
            var progress = (item.emission / maxEmission) * 100;
            var progressColor = 'green';

            if (progress > 100) {
                progressColor = 'red';
            } else if (progress > 75) {
                progressColor = 'orange';
            } else if (progress > 25) {
                progressColor = 'yellow';
            }

            html += "<div class='comparision_item" + (isSelected ? ' comparision_item--selected' : '') + "'>" +
                "<div class='comparision_header'>" +
                "<div class='comparision_mode'>" +
                "<span class='comparision_icon'>" + getTransportIcon(item.mode) + "</span>" +
                "<div>" +
                "<div class='comparision_label'>" + meta.label + "</div>" +
                "<div class='comparision_emission'>" + UI.formatNumber(item.emission, 2) + " kg CO₂</div>" +
                "</div>" +
                "</div>" +
                (isSelected ? "<div class='comparision_badge'>Selecionado</div>" : '') +
                "</div>" +
                "<div class='comparision_stats'>" +
                "<div class='comparision_percentage'>" + this.formatNumber(item.percentageVsCar, 2) + "% vs Carro</div>" +
                "</div>" +
                "<div class='comparision_bar-container'>" +
                "<div class='comparision_bar' style='width: " + progress + "%; background: " + progressColor + ";'></div>" +
                "</div>" +
                "</div>";
        }, this);

        html += "<div class='comparation_tip'>" +
            "<strong>Dica:</strong> Escolher modos menos intensivos em carbono pode reduzir significativamente sua pegada de CO₂." +
            "</div>";

        return html;
    },

    /**
     * Gera o HTML de créditos de carbono.
     * @param {Object} creditsData
     * @returns {string}
     */
    renderCarbonCredits: function(creditsData) {
        var credits = creditsData.credits || 0;
        var price = creditsData.price || { min: 0, max: 0, average: 0 };

        var creditsCard = "<div class='carbon-credits_card'>" +
            "<div class='carbon-credits_card__title'>CRÉDITOS NECESSÁRIOS</div>" +
            "<span class='carbon-credits_card__value'>" + this.formatNumber(credits, 4) + "</span>" +
            "<p class='carbon-credits_card__help'>1 crédito = 1000 kg CO₂</p>" +
            "</div>";

        var priceCard = "<div class='carbon-credits_card'>" +
            "<div class='carbon-credits_card__title'>PREÇO ESTIMADO</div>" +
            "<span class='carbon-credits_card__value'>" + this.formatCurrency(price.average) + "</span>" +
            "<p class='carbon-credits_card__help'>Faixa: " + this.formatCurrency(price.min) + " - " + this.formatCurrency(price.max) + "</p>" +
            "</div>";

        var infoBox = "<div class='carbon-credits_info'>" +
            "<p>Os créditos de carbono ajudam a compensar emissões investindo em projetos que removem ou evitam CO₂.</p>" +
            "</div>";

        var actionButton = "<button type='button' class='carbon-credits_button'>Compensar Emissões</button>";

        return "<div class='carbon-credits_grid'>" + creditsCard + priceCard + "</div>" + infoBox + actionButton;
    },

    /**
     * Mostra um estado de carregamento em um botão.
     * @param {HTMLElement} buttonElement
     */
    showLoading: function(buttonElement) {
        if (!buttonElement) {
            return;
        }

        if (!buttonElement.dataset.originalText) {
            buttonElement.dataset.originalText = buttonElement.innerHTML;
        }

        buttonElement.disabled = true;
        buttonElement.innerHTML = '<span class="spinner"></span> Calculando...';
    },

    /**
     * Restaura o botão após carregamento.
     * @param {HTMLElement} buttonElement
     */
    hideLoading: function(buttonElement) {
        if (!buttonElement) {
            return;
        }

        buttonElement.disabled = false;
        if (buttonElement.dataset.originalText) {
            buttonElement.innerHTML = buttonElement.dataset.originalText;
        }
    }
};
