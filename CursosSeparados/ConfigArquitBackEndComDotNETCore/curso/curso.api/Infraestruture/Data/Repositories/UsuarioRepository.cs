using System;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Threading.Tasks;
using curso.api.Business.Entities;
using curso.api.Business.Repositories;
using curso.api.Models.Usuarios;
using Microsoft.EntityFrameworkCore;

namespace curso.api.Infraestruture.Data.Repositories
{
    public class UsuarioRepository : IUsuarioRepository
    {
        private readonly CursoDbContext _contexto;

        public UsuarioRepository(CursoDbContext contexto)
        {
            _contexto = contexto;
        }

        public void Adicionar(Usuario usuario)
        {
            _contexto.Add(usuario);
        }

        public void Commit()
        {
            _contexto.SaveChanges();
        }

        public async Task<Usuario> ObterUsuarioAsync(string login)
        {
            return await _contexto.Usuario.FirstOrDefaultAsync(u => u.Login == login);
        }

    }
}
