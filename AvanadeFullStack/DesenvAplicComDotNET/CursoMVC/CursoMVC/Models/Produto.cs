using System;
using System.ComponentModel.DataAnnotations;

namespace CursoMVC.Models
{
    
    public class Produto
    {
        public int Id { get; set; }

        [Display(Name ="Descrição")]
        public string Descricao { get; set; }

        [Range(1,50, ErrorMessage = "A quantidade deve estar entre 1 e 50")]
        public int Quantidade { get; set; }

        public int CategoriaId { get; set; }

        public Categoria Categoria { get; set; }
    }
}
