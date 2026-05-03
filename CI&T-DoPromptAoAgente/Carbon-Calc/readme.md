# 🌱 Calculadora de Emissões de CO₂

Uma calculadora interativa para estimar emissões de dióxido de carbono (CO₂) de diferentes meios de transporte, desenvolvida como projeto do BootCamp CI&T - Do Prompt ao Agente da DIO.

![GitHub Pages](https://img.shields.io/badge/GitHub%20Pages-Deployed-green)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=flat&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=flat&logo=javascript&logoColor=black)

## 📋 Sobre o Projeto

Este projeto é uma calculadora web que ajuda usuários a entenderem o impacto ambiental de suas viagens, comparando emissões de CO₂ entre diferentes meios de transporte. Desenvolvido com tecnologias web modernas, oferece uma interface intuitiva e responsiva para promover a conscientização ambiental.

**BootCamp CI&T - Do Prompt ao Agente**  
Projeto desenvolvido durante o bootcamp da [Digital Innovation One (DIO)](https://www.dio.me/) em parceria com a [CI&T](https://www.ciandt.com/br/pt-br).

## ✨ Funcionalidades

- 🗺️ **Cálculo de Rotas**: Base de dados com mais de 40 rotas brasileiras
- 🚗 **10 Meios de Transporte**: Bicicleta, carros (gasolina, elétrico, híbrido, flex), ônibus, trem, caminhão e avião
- 📊 **Comparação Visual**: Barras de progresso mostrando eficiência energética
- 💰 **Compensação de Carbono**: Cálculo de créditos de carbono necessários
- 📱 **Design Responsivo**: Funciona perfeitamente em desktop e mobile
- 🎨 **Interface Moderna**: Tema eco-friendly com animações suaves

## 🚀 Como Usar

### Acesso Online
Acesse a calculadora diretamente através do GitHub Pages: [Link do Deploy](https://sidneymoreira.github.io/CalculatorCO2/)

### Uso Local
1. **Clone o repositório:**
   ```bash
   git clone https://github.com/SEU_USERNAME/NOME_DO_REPO.git
   cd NOME_DO_REPO
   ```

2. **Abra no navegador:**
   - Abra o arquivo `index.html` diretamente no seu navegador
   - Ou use um servidor local (recomendado):
     ```bash
     # Com Python 3
     python -m http.server 8000

     # Com Node.js
     npx serve .
     ```

3. **Como usar a calculadora:**
   - Selecione o meio de transporte desejado
   - Digite as cidades de origem e destino
   - Clique em "Calcular Emissões"
   - Visualize os resultados e comparações

## 🛠️ Tecnologias Utilizadas

- **HTML5**: Estrutura semântica e acessível
- **CSS3**: Estilização moderna com variáveis CSS e animações
- **JavaScript (ES5+)**: Lógica de cálculo e manipulação DOM
- **GitHub Actions**: Deploy automatizado para GitHub Pages

## 📁 Estrutura do Projeto

```
carbon-calc/
├── index.html          # Página principal
├── css/
│   └── style.css       # Estilos da aplicação
├── js/
│   ├── routes-data.js  # Base de dados de rotas brasileiras
│   ├── config.js       # Configurações e fatores de emissão
│   ├── calculator.js   # Lógica de cálculos
│   ├── ui.js           # Funções de interface
│   └── app.js          # Inicialização e eventos
├── .github/
│   └── workflows/
│       └── deploy.yml  # Workflow de deploy
└── README.md           # Este arquivo
```

## 🔧 Arquitetura

O projeto utiliza uma arquitetura modular em JavaScript puro:

- **RoutesDB**: Gerencia dados de rotas e distâncias
- **CONFIG**: Centraliza configurações e metadados
- **Calculator**: Realiza cálculos de emissões
- **UI**: Controla renderização e interações
- **App**: Coordena inicialização e eventos

## 🌍 Fatores de Emissão

Baseados em dados reais (kg CO₂/km):
- 🚲 **Bicicleta**: 0 kg
- 🚗 **Carro a Gasolina**: 0.12 kg
- ⚡🚗 **Carro Elétrico**: 0.053 kg
- 🔋🚗 **Carro Híbrido**: 0.084 kg
- 🌿🚗 **Carro Flex**: 0.108 kg
- 🚌 **Ônibus**: 0.089 kg
- 🚆 **Trem**: 0.041 kg
- 🚚 **Caminhão**: 0.96 kg
- ✈️ **Avião**: 0.255 kg

## 🤝 Como Contribuir

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

### Sugestões de Melhorias
- [ ] Adicionar mais rotas internacionais
- [ ] Implementar API de mapas para rotas customizadas
- [ ] Adicionar histórico de cálculos
- [ ] Suporte a múltiplos idiomas
- [ ] Integração com APIs de créditos de carbono reais

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 👥 Autores

- **Desenvolvedor**: [Sidnei Moreira](https://github.com/SidneyMoreira)
- **Bootcamp**: CI&T - Do Prompt ao Agente (DIO)

## 🙏 Agradecimentos

- [Digital Innovation One (DIO)](https://www.dio.me/) pela oportunidade
- [CI&T](https://www.ciandt.com/br/pt-br) pelo bootcamp incrível
- Comunidade open source pelas ferramentas utilizadas

---

<div align="center">
  <p>Feito com ❤️ para um futuro mais sustentável</p>
  <p>
    <a href="#-calculadora-de-emissões-de-co₂">Voltar ao topo</a>
  </p>
</div>
