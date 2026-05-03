/**
 * Calculator - Funções de cálculo de emissão e crédito de carbono
 * Define um único objeto global para ser usado na aplicação.
 */
var Calculator = {
    /**
     * Calcula a emissão de CO2 para um modo de transporte.
     * @param {number} distanceKM - Distância em quilômetros
     * @param {string} transportMode - Chave do modo de transporte em CONFIG.EMISSION_FACTORS
     * @returns {number} Emissão em kg de CO2 arredondada em 2 casas
     */
    calculateEmission: function(distanceKM, transportMode) {
        var factor = 0;

        if (typeof CONFIG !== 'undefined' && CONFIG.EMISSION_FACTORS && CONFIG.EMISSION_FACTORS[transportMode] !== undefined) {
            factor = CONFIG.EMISSION_FACTORS[transportMode];
        }

        // Emissão = distância x fator de emissão
        var emission = Number(distanceKM) * Number(factor);

        // Arredonda para 2 casas decimais
        return Number(emission.toFixed(2));
    },

    /**
     * Calcula emissão para todos os modos e compara com o carro base.
     * @param {number} distanceKm - Distância em quilômetros
     * @returns {Array<Object>} Array ordenado por emissão crescente
     */
    calculateAllModes: function(distanceKm) {
        var results = [];
        var carEmission = null;

        if (typeof CONFIG === 'undefined' || !CONFIG.EMISSION_FACTORS) {
            return results;
        }

        // Calcula emissões para todos os modos e guarda a do carro a gasolina como baseline
        for (var mode in CONFIG.EMISSION_FACTORS) {
            if (CONFIG.EMISSION_FACTORS.hasOwnProperty(mode)) {
                var emission = this.calculateEmission(distanceKm, mode);

                if (mode === 'gasCar') {
                    carEmission = emission;
                }

                results.push({
                    mode: mode,
                    emission: emission,
                    percentageVsCar: 0
                });
            }
        }

        // Se não houve baseline para carro, usa o menor valor disponível para evitar divisão por zero
        if (carEmission === null || carEmission === 0) {
            carEmission = results.length > 0 ? results[0].emission || 1 : 1;
        }

        // Calcula percentual em relação ao carro a gasolina e ordena por emissão crescente
        results = results.map(function(item) {
            var percentage = 0;

            if (carEmission > 0) {
                percentage = (item.emission / carEmission) * 100;
            }

            return {
                mode: item.mode,
                emission: item.emission,
                percentageVsCar: Number(percentage.toFixed(2))
            };
        });

        results.sort(function(a, b) {
            return a.emission - b.emission;
        });

        return results;
    },

    /**
     * Calcula economia de emissão em relação à baseline.
     * @param {number} emission - Emissão atual em kg
     * @param {number} baselineEmission - Emissão de referência em kg
     * @returns {Object} Objeto com savedKg e percentage
     */
    calculateSavings: function(emission, baselineEmission) {
        var savedKg = Number(baselineEmission) - Number(emission);

        if (savedKg < 0) {
            savedKg = 0;
        }

        var percentage = 0;

        if (baselineEmission > 0) {
            percentage = (savedKg / Number(baselineEmission)) * 100;
        }

        return {
            savedKg: Number(savedKg.toFixed(2)),
            percentage: Number(percentage.toFixed(2))
        };
    },

    /**
     * Calcula quantos créditos de carbono correspondem a uma emissão.
     * @param {number} emissionKg - Emissão em kg de CO2
     * @returns {number} Créditos de carbono arredondados para 4 casas decimais
     */
    calculateCarbonCredits: function(emissionKg) {
        if (typeof CONFIG === 'undefined' || !CONFIG.CARBON_CREDIT) {
            return 0;
        }

        var credits = Number(emissionKg) / Number(CONFIG.CARBON_CREDIT.KG_PER_CREDIT);

        return Number(credits.toFixed(4));
    },

    /**
     * Estima o preço dos créditos de carbono com base na faixa mínima e máxima.
     * @param {number} credits - Quantidade de créditos de carbono
     * @returns {Object} Objeto com min, max e average em reais
     */
    estimateCreditPrice: function(credits) {
        if (typeof CONFIG === 'undefined' || !CONFIG.CARBON_CREDIT) {
            return {
                min: 0,
                max: 0,
                average: 0
            };
        }

        var min = Number(credits) * Number(CONFIG.CARBON_CREDIT.PRICE_MIN_BRL);
        var max = Number(credits) * Number(CONFIG.CARBON_CREDIT.PRICE_MAX_BRL);
        var average = (min + max) / 2;

        return {
            min: Number(min.toFixed(2)),
            max: Number(max.toFixed(2)),
            average: Number(average.toFixed(2))
        };
    }
};
