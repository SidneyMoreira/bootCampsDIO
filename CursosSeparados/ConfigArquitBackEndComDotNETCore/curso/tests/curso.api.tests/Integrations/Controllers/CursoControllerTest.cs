
using System.Collections.Generic;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using AutoBogus;
using curso.api.Models.Cursos;
using curso.api.tests.integrations.Controllers;
using Microsoft.AspNetCore.Mvc.Testing;
using Newtonsoft.Json;
using Xunit;
using Xunit.Abstractions;

namespace curso.api.tests.Integrations.Controllers
{
    public class CursoControllerTest : UsuarioControllerTests
    {
        public CursoControllerTest(WebApplicationFactory<Startup> factory, ITestOutputHelper output)
            : base(factory, output)
        {
           
        }

        [Fact]

        public async Task Registrar_InformandoDadosDeUmCursoValidoComUmUsuarioAutenticado_DeveRetornarSucesso()
        {
            //Arrange
            var cursoViewModelInput = new AutoFaker<CursoViewModelInput>();
            //var cursoViewModelInputFaker = cursoViewModelInput.Generate();
            StringContent content = new StringContent(JsonConvert.SerializeObject(cursoViewModelInput.Generate()), Encoding.UTF8, "application/json");

            //Act
            _httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", LoginViewModelOutput.Token);
            var httpClientRequest = await _httpClient.PostAsync("api/v1/cursos", content);

            //Asserts
            _output.WriteLine($"{nameof(CursoControllerTest)}_{nameof(Registrar_InformandoDadosDeUmCursoValidoComUmUsuarioAutenticado_DeveRetornarSucesso)} = {await httpClientRequest.Content.ReadAsStringAsync()}");
            Assert.Equal(HttpStatusCode.Created, httpClientRequest.StatusCode);
        }

        [Fact]

        public async Task Registrar_InformandoDadosDeUmCursoValidoComUmUsuarioNaoAutenticado_DeveRetornarSucesso()
        {
            //Arrange
            var cursoViewModelInput = new AutoFaker<CursoViewModelInput>();
            //var cursoViewModelInputFaker = cursoViewModelInput.Generate();
            StringContent content = new StringContent(JsonConvert.SerializeObject(cursoViewModelInput.Generate()), Encoding.UTF8, "application/json");

            //Act
            
            var httpClientRequest = await _httpClient.PostAsync("api/v1/cursos", content);

            //Asserts
            _output.WriteLine($"{nameof(CursoControllerTest)}_{nameof(Registrar_InformandoDadosDeUmCursoValidoComUmUsuarioNaoAutenticado_DeveRetornarSucesso)} = {await httpClientRequest.Content.ReadAsStringAsync()}");
            Assert.Equal(HttpStatusCode.Unauthorized, httpClientRequest.StatusCode);
        }


        [Fact]

        public async Task Obter_InformandoUmUsuarioAutenticado_DeveRetornarSucesso()
        {
            //Arrange
            await Registrar_InformandoDadosDeUmCursoValidoComUmUsuarioAutenticado_DeveRetornarSucesso();
            //Act
            _httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", LoginViewModelOutput.Token);
            var httpClientRequest = await _httpClient.GetAsync("api/v1/cursos");

            //Asserts
            _output.WriteLine($"{nameof(CursoControllerTest)}_{nameof(Obter_InformandoUmUsuarioAutenticado_DeveRetornarSucesso)} = {await httpClientRequest.Content.ReadAsStringAsync()}");

            var cursos = JsonConvert.DeserializeObject<IList<CursoViewModelOutput>>(await httpClientRequest.Content.ReadAsStringAsync());

            Assert.NotNull(cursos);

            Assert.Equal(HttpStatusCode.OK, httpClientRequest.StatusCode);
        }
    }
}
