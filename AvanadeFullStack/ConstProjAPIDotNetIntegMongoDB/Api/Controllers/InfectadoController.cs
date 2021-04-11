using System;
using Api.Data.Collections;
using Microsoft.AspNetCore.Mvc;
using Models;
using MongoDB.Driver;

namespace Api.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class InfectadoController : ControllerBase
    {
        Data.MongoDB _mongoDB;
        IMongoCollection<Infectado> _infectadosColletions;
        public InfectadoController(Data.MongoDB mongoDB)
        {
            _mongoDB = mongoDB;
            _infectadosColletions = _mongoDB.DB.GetCollection<Infectado>(typeof(Infectado).Name.ToLower());
        }

        [HttpPost]
        public ActionResult SalvarInfectado([FromBody] InfectadoDto dto)
        {
            var infectado = new Infectado(dto.DataNascimento, dto.Sexo, dto.Latitude, dto.Longitude);
            _infectadosColletions.InsertOne(infectado);
            return StatusCode(201, "Infectado adicionado com sucesso!!");
        }

        [HttpGet]
        public ActionResult ObterInfenctado()
        {
            var infectados = _infectadosColletions.Find(Builders<Infectado>.Filter.Empty).ToList();
            return Ok(infectados);
        }

        [HttpPut]
        public ActionResult AtualizarInfenctado([FromBody] InfectadoDto dto)
        {
            _infectadosColletions.UpdateOne(Builders<Infectado>.Filter.Where(_ => _.DataNascimento == dto.DataNascimento), 
                                            Builders<Infectado>.Update.Set("sexo", dto.Sexo));
            return Ok("Atualizado com Sucesso");
        }

        [HttpDelete("{dataNasc}")]
        public ActionResult DeletarInfenctado(DateTime dataNasc)
        {
            _infectadosColletions.DeleteOne(Builders<Infectado>.Filter.Where(_ => _.DataNascimento == dataNasc));
            return Ok("Apagado com Sucesso");
        }
    }
}