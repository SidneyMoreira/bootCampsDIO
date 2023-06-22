import { BrowserRouter } from "react-router-dom";
import MainRoutes from "./routes";
//import Menu from "./components/Menu/Menu";

function App() {
  return (
    <BrowserRouter>
      
      <MainRoutes />
      
    </BrowserRouter>
  );
}

export default App;
