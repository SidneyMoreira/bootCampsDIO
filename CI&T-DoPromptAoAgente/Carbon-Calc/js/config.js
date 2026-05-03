/**
 * CONFIG - Configurações globais da calculadora de CO2
 * Contém fatores de emissão, metadados de transporte e créditos de carbono
 */
var CONFIG = {
    /**
     * Fatores de emissão de CO2 em kg por km
     * Baseados em dados médios do IPCC e estudos brasileiros
     */
    EMISSION_FACTORS: {
        bicycle: 0,
        motocycle: 0.0711,
        gasCar: 0.12,
        electricCar: 0.053,
        hybridCar: 0.084,
        flexCar: 0.108,
        bus: 0.089,
        train: 0.041,
        truck: 0.96,
        plane: 0.255
    },

    /**
     * Metadados dos modos de transporte
     * Contém label, ícone e cor para UI
     */
    TRANSPORT_MODES: {
        bicycle: {
            label: "Bicicleta",
            icon: "🚲",
            color: "#22c55e"
        },
        motocycle: {
            label: "Moto",
            icon: "🏍️",
            color: "#f97316"
        },
        gasCar: {
            label: "Carro a Gasolina",
            icon: "⛽",
            color: "#3b82f6"
        },
        electricCar: {
            label: "Carro Elétrico",
            icon: "⚡",
            color: "#06b6d4"
        },
        hybridCar: {
            label: "Carro Híbrido",
            icon: "🔋",
            color: "#8b5cf6"
        },
        flexCar: {
            label: "Carro Flex",
            icon: "🌿",
            color: "#10b981"
        },
        bus: {
            label: "Ônibus",
            icon: "🚌",
            color: "#a855f7"
        },
        train: {
            label: "Trem/Metrô",
            icon: "🚇",
            color: "#6366f1"
        },
        truck: {
            label: "Caminhão",
            icon: "🚚",
            color: "#ef4444"
        },
        plane: {
            label: "Avião",
            icon: "✈️",
            color: "#f59e0b"
        }
    },

    /**
     * Configurações de créditos de carbono
     */
    CARBON_CREDIT: {
        KG_PER_CREDIT: 1000,
        PRICE_MIN_BRL: 50,
        PRICE_MAX_BRL: 150
    },

    /**
     * Popula o datalist com as cidades disponíveis
     * Obtém cidades de RoutesDB e cria options para o elemento datalist
     */
    populateDatalist: function() {
        // Verifica se RoutesDB está disponível
        if (typeof RoutesDB === "undefined" || typeof RoutesDB.getAllCities !== "function") {
            console.error("RoutesDB não está disponível");
            return;
        }

        // Obtém lista de cidades
        var cities = RoutesDB.getAllCities();

        // Obtém elemento datalist
        var datalist = document.getElementById("cities-list");

        if (!datalist) {
            console.error("Elemento datalist não encontrado");
            return;
        }

        // Limpa opções existentes
        datalist.innerHTML = "";

        // Cria opção para cada cidade
        for (var i = 0; i < cities.length; i++) {
            var option = document.createElement("option");
            option.value = cities[i];
            datalist.appendChild(option);
        }

        console.log("Datalist populado com " + cities.length + " cidades");
    },

    /**
     * Configura autofill de distância baseado nas cidades selecionadas
     * Adiciona listeners para origin, destination e checkbox manual
     */
    setupDistanceAutofill: function() {
        // Obtém elementos do DOM
        var originInput = document.getElementById("origin");
        var destinationInput = document.getElementById("destination");
        var distanceInput = document.getElementById("distance");
        var manualCheckbox = document.getElementById("manual-distance");
        var helperText = document.querySelector(".calculator-form__helper");

        // Verifica se todos os elementos existem
        if (!originInput || !destinationInput || !distanceInput || !manualCheckbox) {
            console.error("Elementos do formulário não encontrados");
            return;
        }

        // Função para buscar distância
        var findRouteDistance = function() {
            var origin = originInput.value.trim();
            var destination = destinationInput.value.trim();

            // Verifica se ambas as cidades estão preenchidas
            if (origin && destination) {
                // Verifica se RoutesDB está disponível
                if (typeof RoutesDB !== "undefined" && typeof RoutesDB.findDistance === "function") {
                    var distance = RoutesDB.findDistance(origin, destination);

                    if (distance !== null) {
                        // Distância encontrada
                        distanceInput.value = distance;
                        distanceInput.readOnly = true;

                        // Atualiza texto de ajuda com sucesso
                        if (helperText) {
                            helperText.textContent = "Distância encontrada automaticamente: " + distance + " km";
                            helperText.style.color = "#10b981";
                        }
                    } else {
                        // Distância não encontrada
                        distanceInput.value = "";
                        distanceInput.readOnly = true;

                        // Atualiza texto de ajuda sugerindo input manual
                        if (helperText) {
                            helperText.textContent = "Rota não encontrada. Marque a opção para inserir distância manualmente.";
                            helperText.style.color = "#f59e0b";
                        }
                    }
                }
            } else {
                // Limpa distância se campos não estiverem preenchidos
                distanceInput.value = "";
                distanceInput.readOnly = true;

                if (helperText) {
                    helperText.textContent = "A distância será preenchida automaticamente";
                    helperText.style.color = "#6b7280";
                }
            }
        };

        // Adiciona listeners de mudança para origin
        if (originInput) {
            originInput.addEventListener("change", findRouteDistance);
        }

        // Adiciona listeners de mudança para destination
        if (destinationInput) {
            destinationInput.addEventListener("change", findRouteDistance);
        }

        // Adiciona listener para checkbox de distância manual
        if (manualCheckbox) {
            manualCheckbox.addEventListener("change", function() {
                if (this.checked) {
                    // Remove readonly para permitir entrada manual
                    distanceInput.readOnly = false;
                    distanceInput.focus();

                    if (helperText) {
                        helperText.textContent = "Digite a distância em km manualmente";
                        helperText.style.color = "#3b82f6";
                    }
                } else {
                    // Tenta encontrar distância automaticamente
                    findRouteDistance();
                }
            });
        }

        console.log("Autofill de distância configurado");
    }
};