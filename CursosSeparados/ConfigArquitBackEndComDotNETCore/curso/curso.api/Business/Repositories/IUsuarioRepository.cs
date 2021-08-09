using System;
using System.Threading.Tasks;
using curso.api.Business.Entities;

namespace curso.api.Business.Repositories
{
    public interface IUsuarioRepository
    {
        void Adicionar(Usuario usuario);
        void Commit();
        Task<Usuario> ObterUsuarioAsync(string login);
    }
}
