using System.Threading.Tasks;
using curso.api.Business.Entities;
using curso.api.Business.Repositories;
using curso.api.Configurations;
using curso.api.Filters;
using curso.api.Models;
using curso.api.Models.Usuarios;
using Microsoft.AspNetCore.Mvc;
using Swashbuckle.AspNetCore.Annotations;

namespace curso.api.Controllers
{

    [Route("api/v1/usuario")]
    [ApiController]
    
    public class UsuarioController : ControllerBase
    {
        private readonly IUsuarioRepository _usuarioRepository;
        private readonly IAuthenticationService _authenticationService;

        public UsuarioController(IUsuarioRepository usuarioRepository,
                                 IAuthenticationService authenticationService)
        {
            _usuarioRepository = usuarioRepository;
            _authenticationService = authenticationService;
        }

        /// <summary>
        /// Este serviço permite autenticar um usuario cadastrado e ativo
        /// </summary>
        /// <param name="loginViewModelInput">View Model Login</param>
        /// <returns>Retorna os Status ok, dados do usuário e o token em caso de sucesso</returns>
        [SwaggerResponse(statusCode: 200, description: "Sucesso ao autenticar", Type = typeof(LoginViewModelInput))]
        [SwaggerResponse(statusCode: 400, description: "Campos Obrigatórios", Type = typeof(ValidaCampoViewModelOutput))]
        [SwaggerResponse(statusCode: 500, description: "Erro Interno", Type = typeof(ErroGenericViewModel))]
        [HttpPost]
        [Route("logar")]
        [ValidacaoModelStateCustomizado]
        public async Task<IActionResult> Logar(LoginViewModelInput loginViewModelInput)
        {
            var usuario = await _usuarioRepository.ObterUsuarioAsync(loginViewModelInput.Login);

            if (usuario == null)
            {
                return BadRequest("Houve um erro ao tentar acessar.");
            }

            //if (usuario.Senha != loginViewModelInput.Senha.GerarSenhaCriptografada())
            //{
            //    return BadRequest("Houve um erro ao tentar acessar.");
            //}

            var usuarioViewModelOutput = new UsuarioViewModelOutput()
            {
                Codigo = usuario.Codigo,
                Login = loginViewModelInput.Login,
                Email = usuario.Email
            };



            var token = _authenticationService.GerarToken(usuarioViewModelOutput);

            return Ok(new
            {
                Token = token,
                Usuario = usuarioViewModelOutput
            });
        }


        /// <summary>
        /// Este serviço permite cadastrar usuarios não existentes
        /// </summary>
        /// <param name="registroViewModelInput">View Model Registro de login</param>
        /// <returns>Retorna os Status ok, dados do usuário em caso de sucesso</returns>
        [SwaggerResponse(statusCode: 200, description: "Sucesso ao autenticar", Type = typeof(LoginViewModelInput))]
        [SwaggerResponse(statusCode: 400, description: "Campos Obrigatórios", Type = typeof(ValidaCampoViewModelOutput))]
        [SwaggerResponse(statusCode: 500, description: "Erro Interno", Type = typeof(ErroGenericViewModel))]
        [HttpPost]
        [Route("registrar")]
        [ValidacaoModelStateCustomizado]
        public async Task<IActionResult> Registrar(RegistroViewModelInput registroViewModelInput)
        {

            var usuario = await _usuarioRepository.ObterUsuarioAsync(registroViewModelInput.Login);
            if(usuario != null)
            {
                return BadRequest("Usuário já cadastrado");
            }

            usuario = new Usuario();

            usuario.Login = registroViewModelInput.Login;
            usuario.Email = registroViewModelInput.Email;
            usuario.Senha = registroViewModelInput.Senha;

            _usuarioRepository.Adicionar(usuario);
            _usuarioRepository.Commit();

            return Created("", registroViewModelInput);
        }
    }
}
