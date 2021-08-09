using System;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using AutoBogus;
using curso.api.Models.Usuarios;
using curso.api.tests.Configuration;
using curso.web.mvc.Models.Usuario;
using Microsoft.AspNetCore.Mvc.Testing;
using Newtonsoft.Json;
using Xunit;
using Xunit.Abstractions;

namespace curso.api.tests.integrations.Controllers
{
    public class UsuarioControllerTests : IClassFixture<WebApplicationFactory<Startup>>, IAsyncLifetime
    {
        protected readonly WebApplicationFactory<Startup> _factory;
        protected readonly ITestOutputHelper _output;
        protected readonly HttpClient _httpClient;
        protected RegistroViewModelInput RegistroViewModelInput;
        protected LoginViewModelOutput LoginViewModelOutput;

        public UsuarioControllerTests(WebApplicationFactory<Startup> factory, ITestOutputHelper output)
        {
           _factory = factory;
           _output = output;
           _httpClient = _factory.CreateClient();
        }


        [Fact]
        public async Task Registrar_InformandoUsuarioSenha_DeveRetornarSucesso()
        {
            //Arrange
            RegistroViewModelInput = new AutoFaker<RegistroViewModelInput>(AutoBogusConfiguration.LOCALE)
                                            .RuleFor(p => p.Login, faker => faker.Person.UserName)
                                            .RuleFor(p => p.Email, faker => faker.Person.Email);

            StringContent content = new StringContent(JsonConvert.SerializeObject(RegistroViewModelInput), Encoding.UTF8, "application/json");

            //Act
            var httpClientRequest = await _httpClient.PostAsync("api/v1/usuario/registrar", content);

            //Asserts
            _output.WriteLine($"{nameof(UsuarioControllerTests)}_{nameof(Registrar_InformandoUsuarioSenha_DeveRetornarSucesso)} = {await httpClientRequest.Content.ReadAsStringAsync()}");
            Assert.Equal(HttpStatusCode.Created, httpClientRequest.StatusCode);
        }

        [Fact]

        public async Task Logar_InformandoUsuarioSenhaExistentes_DeveRetornarSucesso()
        {
            //Arrange
            var loginViewModelInput = new Models.Usuarios.LoginViewModelInput
            {
                Login = RegistroViewModelInput.Login,
                Senha = RegistroViewModelInput.Senha
            };

            StringContent content = new StringContent(JsonConvert.SerializeObject(loginViewModelInput), Encoding.UTF8, "application/json");

            //Act
            var httpClientRequest = await _httpClient.PostAsync("api/v1/usuario/logar", content);

            LoginViewModelOutput = JsonConvert.DeserializeObject<LoginViewModelOutput>(await httpClientRequest.Content.ReadAsStringAsync());

            //Asserts
            Assert.Equal(HttpStatusCode.OK, httpClientRequest.StatusCode);
            Assert.NotNull(LoginViewModelOutput.Token);
            Assert.Equal(loginViewModelInput.Login, LoginViewModelOutput.Usuario.Login);
            //_output.WriteLine(LoginViewModelOutput.Token);
            _output.WriteLine($"{nameof(UsuarioControllerTests)}_{nameof(Logar_InformandoUsuarioSenhaExistentes_DeveRetornarSucesso)} = {await httpClientRequest.Content.ReadAsStringAsync()}");

        }

        public async Task InitializeAsync()
        {
            await Registrar_InformandoUsuarioSenha_DeveRetornarSucesso();
            await Logar_InformandoUsuarioSenhaExistentes_DeveRetornarSucesso();
        }

        public async Task DisposeAsync()
        {
            _httpClient.Dispose();
        }
    }
        
    
}
