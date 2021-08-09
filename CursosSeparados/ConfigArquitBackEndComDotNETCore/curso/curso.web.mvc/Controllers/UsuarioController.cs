using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Http;
using System.Net.Security;
using System.Security.Claims;
using System.Text;
using System.Threading.Tasks;
using curso.web.mvc.Models.Usuario;
using curso.web.mvc.Services;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using Refit;

namespace curso.web.mvc.Controllers
{
    public class UsuarioController : Controller
    {
        private readonly IUsuarioService _usuarioService;

        public UsuarioController(IUsuarioService usuarioService)
        {
            _usuarioService = usuarioService;
        }
        // GET: /<controller>/
        public IActionResult Cadastrar()
        {
            return View();
        }

        [HttpPost]
        public async Task<IActionResult> Cadastrar(RegistrarUsuarioViewModelInput registrarUsuarioViewModelInput)
        {
            try
            {
                var usuario = await _usuarioService.Registrar(registrarUsuarioViewModelInput);
                

                ModelState.AddModelError("", $"Os dados foram cadastrados com sucesso para o login {usuario.Login}");
            }
            catch (ApiException ex)
            {
                ModelState.AddModelError("", ex.Message);
            }
            catch (Exception ex)
            {
                ModelState.AddModelError("", ex.Message);
            }
            //var clientHandler = new HttpClientHandler();
            //clientHandler.ServerCertificateCustomValidationCallback = (sender, cert, chain, SslPolicyErrors) => { return true; };

            //var hhtpClient = new HttpClient(clientHandler);
            //var registrarUsuarioViewModelInputJson = JsonConvert.SerializeObject(registrarUsuarioViewModelInput);
            //var httpContent = new StringContent(registrarUsuarioViewModelInputJson, Encoding.UTF8, "application/json");
            //hhtpClient.BaseAddress = new Uri("https://localhost:5001/");

            //var httpPost = hhtpClient.PostAsync("/api/v1/usuario/registrar", httpContent).GetAwaiter().GetResult();

            //if(httpPost.StatusCode == HttpStatusCode.Created)
            //{
            //    ModelState.AddModelError("", "Os dados foram cadastrados com sucesso");
            //}
            //else
            //{
            //    ModelState.AddModelError("", "Erro ao cadastrar usuário")   ;
            //}
            return View();
        }

        public IActionResult Logar()
        {
            return View();
        }

        [HttpPost]
        public async Task<IActionResult> Logar(LoginViewModelInput loginViewModelInput)
        {
            try
            {
                await HttpContext.SignOutAsync(CookieAuthenticationDefaults.AuthenticationScheme);
                var usuario = await _usuarioService.Logar(loginViewModelInput);
                var claims = new List<Claim>
                {
                    new Claim(ClaimTypes.NameIdentifier, usuario.Usuario.Codigo.ToString()),
                    new Claim(ClaimTypes.Name, usuario.Usuario.Login),
                    new Claim(ClaimTypes.Email, usuario.Usuario.Email),
                    new Claim("token", usuario.Token),
                };

                var claimsidentity = new ClaimsIdentity(claims, CookieAuthenticationDefaults.AuthenticationScheme);
                var authProperties = new AuthenticationProperties
                {
                    ExpiresUtc = new DateTimeOffset(DateTime.UtcNow.AddDays(1))
                };
                await HttpContext.SignInAsync(CookieAuthenticationDefaults.AuthenticationScheme, new ClaimsPrincipal(claimsidentity));

                ModelState.AddModelError("", $"O usuário está autenticado {usuario.Token}");
            }
            catch (ApiException ex)
            {
                ModelState.AddModelError("", ex.Message);
            }
            catch (Exception ex)
            {
                ModelState.AddModelError("", ex.Message);
            }
            return View();
        }

        public IActionResult EfetuarLogOff()
        {
            return View();
        }

        [HttpPost]
        public async Task<IActionResult> LogOff()
        {
            await HttpContext.SignOutAsync(CookieAuthenticationDefaults.AuthenticationScheme);

            return RedirectToAction($"{nameof(Logar)}");
        }
    }
}
