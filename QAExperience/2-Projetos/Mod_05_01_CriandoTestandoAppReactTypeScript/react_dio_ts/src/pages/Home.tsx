import { useState, MouseEvent } from "react";
import { useNavigate } from "react-router-dom";
import Layout from "../components/Layout/Layout";
import gitApi from "../api/github";

const Home = () => {

  const [user, setUser] = useState('')
  const [invalid, setInvalid] = useState(false)
  const history = useNavigate()

  const handleClick = async (event: MouseEvent) => {
    event.preventDefault()
    if(user.length === 0){
      return alert('Por favor informe um usuário')
    }

    gitApi.getUser(user)
      .then (response => history(`/${response.login}`))
      .catch(error => {
        setInvalid(true)
      })
  }

  return (
    <Layout>
      <div className="col-4 m-auto">
        <h1>Login</h1>
        <div className="mb-3">
          <label htmlFor="gitUser" className="form-label">Usuário do gitHub</label>
          <input 
              type="text" 
              className="form-control" 
              id="gitUser" 
              aria-label= 'User' 
              aria-describedby="userHelp" 
              value={user}
              onChange={event =>  setUser(event.target.value)}
          />
          <div id="userHelp" className="form-text">
            Informe seu usuario do gitHub!
          </div>
          {
            invalid && (
              <div id="userHelp" className="form-text text-danger">
                 Usuário inválido!
              </div>
            )
          }
          
        </div>
        <button onClick={handleClick} type="button" className="btn btn-primary">Entrar</button>
      </div>
    </Layout>
  );
}

export default Home;