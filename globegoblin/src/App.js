import LoginPage from "./components/LoginPage";
import Dashboard from "./components/Dashboard";
import ChallengeDetail from "./components/ChallengeDetails";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import "./App.css";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" exact element={<LoginPage/>} />
        <Route path="/dashboard" element={<Dashboard/>} />
        <Route path="/challenge/:id" element={<ChallengeDetail/>} />
      </Routes>
    </Router>
  );
}

export default App;
