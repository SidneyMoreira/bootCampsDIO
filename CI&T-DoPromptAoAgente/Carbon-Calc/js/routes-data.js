/**
 * RoutesDB - Banco de dados de rotas brasileiras
 * Contém distâncias entre cidades do Brasil para cálculo de emissão de CO2
 */
var RoutesDB = {
    /**
     * Array de rotas com distância em km
     * Estrutura: { origin: "Cidade, UF", destination: "Cidade, UF", distanceKM: número }
     */
    routes: [
        // Capitais - Conexões principais
        { origin: "São Paulo, SP", destination: "Rio de Janeiro, RJ", distanceKM: 430 },
        { origin: "São Paulo, SP", destination: "Brasília, DF", distanceKM: 1015 },
        { origin: "Rio de Janeiro, RJ", destination: "Brasília, DF", distanceKM: 1148 },
        { origin: "São Paulo, SP", destination: "Belo Horizonte, MG", distanceKM: 584 },
        { origin: "Rio de Janeiro, RJ", destination: "Belo Horizonte, MG", distanceKM: 656 },
        { origin: "Belo Horizonte, MG", destination: "Brasília, DF", distanceKM: 716 },
        
        // São Paulo - Rotas regionais
        { origin: "São Paulo, SP", destination: "Campinas, SP", distanceKM: 95 },
        { origin: "São Paulo, SP", destination: "Santos, SP", distanceKM: 70 },
        { origin: "São Paulo, SP", destination: "São José dos Campos, SP", distanceKM: 88 },
        { origin: "São Paulo, SP", destination: "Sorocaba, SP", distanceKM: 100 },
        { origin: "São Paulo, SP", destination: "Ribeirão Preto, SP", distanceKM: 313 },
        { origin: "São Paulo, SP", destination: "São José do Rio Preto, SP", distanceKM: 442 },
        { origin: "São Paulo, SP", destination: "Presidente Prudente, SP", distanceKM: 558 },
        { origin: "Campinas, SP", destination: "Sorocaba, SP", distanceKM: 120 },
        { origin: "Campinas, SP", destination: "Ribeirão Preto, SP", distanceKM: 230 },
        { origin: "Santos, SP", destination: "São Paulo, SP", distanceKM: 70 },
        
        // Rio de Janeiro - Rotas regionais
        { origin: "Rio de Janeiro, RJ", destination: "Niterói, RJ", distanceKM: 13 },
        { origin: "Rio de Janeiro, RJ", destination: "Petrópolis, RJ", distanceKM: 68 },
        { origin: "Rio de Janeiro, RJ", destination: "Volta Redonda, RJ", distanceKM: 120 },
        { origin: "Rio de Janeiro, RJ", destination: "Cabo Frio, RJ", distanceKM: 156 },
        { origin: "Rio de Janeiro, RJ", destination: "Angra dos Reis, RJ", distanceKM: 150 },
        { origin: "Niterói, RJ", destination: "Cabo Frio, RJ", distanceKM: 180 },
        
        // Minas Gerais - Rotas regionais
        { origin: "Belo Horizonte, MG", destination: "Ouro Preto, MG", distanceKM: 100 },
        { origin: "Belo Horizonte, MG", destination: "Juiz de Fora, MG", distanceKM: 190 },
        { origin: "Belo Horizonte, MG", destination: "Uberlândia, MG", distanceKM: 550 },
        { origin: "Belo Horizonte, MG", destination: "Diamantina, MG", distanceKM: 260 },
        { origin: "Belo Horizonte, MG", destination: "Poços de Caldas, MG", distanceKM: 450 },
        { origin: "Juiz de Fora, MG", destination: "Rio de Janeiro, RJ", distanceKM: 200 },
        
        // Sul - Principais rotas
        { origin: "São Paulo, SP", destination: "Curitiba, PR", distanceKM: 408 },
        { origin: "São Paulo, SP", destination: "Porto Alegre, RS", distanceKM: 1135 },
        { origin: "Curitiba, PR", destination: "Porto Alegre, RS", distanceKM: 711 },
        { origin: "Curitiba, PR", destination: "Florianópolis, SC", distanceKM: 300 },
        { origin: "Curitiba, PR", destination: "Londrina, PR", distanceKM: 380 },
        { origin: "Porto Alegre, RS", destination: "Caxias do Sul, RS", distanceKM: 120 },
        
        // Nordeste - Principais rotas
        { origin: "São Paulo, SP", destination: "Salvador, BA", distanceKM: 1732 },
        { origin: "São Paulo, SP", destination: "Recife, PE", distanceKM: 2130 },
        { origin: "Rio de Janeiro, RJ", destination: "Salvador, BA", distanceKM: 1634 },
        { origin: "Salvador, BA", destination: "Recife, PE", distanceKM: 800 },
        { origin: "Salvador, BA", destination: "Fortaleza, CE", distanceKM: 1440 },
        { origin: "Recife, PE", destination: "Fortaleza, CE", distanceKM: 800 },
        
        // Centro-Oeste
        { origin: "Brasília, DF", destination: "Goiânia, GO", distanceKM: 209 },
        { origin: "Brasília, DF", destination: "Cuiabá, MT", distanceKM: 1135 },
        { origin: "Brasília, DF", destination: "Campo Grande, MS", distanceKM: 878 },
        { origin: "Goiânia, GO", destination: "Cuiabá, MT", distanceKM: 950 },
        
        // Norte
        { origin: "Brasília, DF", destination: "Manaus, AM", distanceKM: 3150 },
        { origin: "Brasília, DF", destination: "Belém, PA", distanceKM: 2100 },
        { origin: "São Paulo, SP", destination: "Manaus, AM", distanceKM: 3695 },
        
        // Sudeste adicional
        { origin: "São Paulo, SP", destination: "Vitória, ES", distanceKM: 846 },
        { origin: "Rio de Janeiro, RJ", destination: "Vitória, ES", distanceKM: 521 },
        { origin: "Belo Horizonte, MG", destination: "Vitória, ES", distanceKM: 524 }
    ],

    /**
     * Retorna array ordenado de todas as cidades únicas
     * Extrai de origin e destination, remove duplicatas
     * @returns {string[]} Array de nomes de cidades ordenados alfabeticamente
     */
    getAllCities: function() {
        var cities = [];
        
        // Extrai todas as cidades de origin
        for (var i = 0; i < this.routes.length; i++) {
            if (this.routes[i].origin) {
                cities.push(this.routes[i].origin);
            }
        }
        
        // Extrai todas as cidades de destination
        for (var j = 0; j < this.routes.length; j++) {
            if (this.routes[j].destination) {
                cities.push(this.routes[j].destination);
            }
        }
        
        // Remove duplicatas usando indexOf
        var uniqueCities = [];
        for (var k = 0; k < cities.length; k++) {
            if (uniqueCities.indexOf(cities[k]) === -1) {
                uniqueCities.push(cities[k]);
            }
        }
        
        // Ordena alfabeticamente
        uniqueCities.sort();
        
        return uniqueCities;
    },

    /**
     * Encontra distância entre duas cidades
     * Busca em ambas as direções (origin-destination e destination-origin)
     * @param {string} origin - Cidade de origem
     * @param {string} destination - Cidade de destino
     * @returns {number|null} Distância em km ou null se não encontrada
     */
    findDistance: function(origin, destination) {
        // Normaliza entrada: remove espaços extras e converte para minúsculas
        var normalizedOrigin = this.normalizeCityName(origin);
        var normalizedDestination = this.normalizeCityName(destination);
        
        // Busca rota direta (origin -> destination)
        for (var i = 0; i < this.routes.length; i++) {
            var routeOrigin = this.normalizeCityName(this.routes[i].origin);
            var routeDestination = this.normalizeCityName(this.routes[i].destination);
            
            if (routeOrigin === normalizedOrigin && routeDestination === normalizedDestination) {
                return this.routes[i].distanceKM;
            }
        }
        
        // Busca rota inversa (destination -> origin)
        for (var j = 0; j < this.routes.length; j++) {
            var reverseOrigin = this.normalizeCityName(this.routes[j].destination);
            var reverseDestination = this.normalizeCityName(this.routes[j].origin);
            
            if (reverseOrigin === normalizedOrigin && reverseDestination === normalizedDestination) {
                return this.routes[j].distanceKM;
            }
        }
        
        // Retorna null se não encontrada
        return null;
    },

    /**
     * Normaliza nome da cidade para comparação
     * @param {string} cityName - Nome da cidade
     * @returns {string} Nome normalizado em minúsculas e sem espaços extras
     */
    normalizeCityName: function(cityName) {
        if (!cityName) {
            return "";
        }
        return cityName.toString().trim().toLowerCase();
    }
};